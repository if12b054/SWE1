/*
 * RITZAL Roman
 * STABRAWA Victor
 * 
 * SWE1
 * 
 * Version 1.1
 * 
 * Das Programm ist ein Webserver welcher Multiuserfähig ist
 * und über ein dynamisches Pluginsystem verfügt
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

/**
 * 
 * The HttpRequest class provides the run, the processRequest and the
 * getURl methods
 * 
 */


class HttpRequest implements Runnable{

    private Socket socket;   
    
    public HttpRequest(Socket socket) throws Exception{
       this.socket = socket;
    }
    
    /* 
     * The run method is an abstract Method, its only use is to call the 
     * processRequest class
     */
    
    @Override
    public void run(){ /*No Programming in the run method -> no errorhandling*/
        try{
            processRequest();
        } catch(Exception ex){
            ex.printStackTrace();
        }   
    }
    /*
     * The processRequest method reads the URL and calls the getURL metho
     * to get a splittet URL, then it calls the pluginmanager to get a list of
     * all current classes, after that it looks through the list to find the
     * plugin which the user called and creates an instance of the plugincalls
     * and starts it
    */
    
    private void processRequest() throws Exception{
        String [] urlArray; /*Field for the splittet URL*/
        String content = null; /*Shows that the plugin was found*/
        boolean pluginFound = false;
        
        PluginManager pm = new PluginManager();
        HttpResponse hp = new HttpResponse();
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        
         /*
          * Reading of the first line of the Httprequest
          */
         String requestLine = in.readLine(); 
         urlArray = getURL(requestLine);

        List<Class> pluginList = new ArrayList(); /*List with the found Plugins*/
        pluginList = pm.initializePlugins();
        for(int i = 0; i < pluginList.size(); i++)
        {
            //splitArray = pluginList.get(i).toString().split(" ");
           if(urlArray[0].toString().equals(pluginList.get(i).getName())){
        	   
        	   Plugin plug = (Plugin)Class.forName(pluginList.get(i).getName()).newInstance();
        	   
        	   
               //StatischeDateien sd = (StatischeDateien) pluginList.get(i).newInstance();
               //Object sd = pluginList.get(i).newInstance();
               pluginFound = true;
               content = plug.run(urlArray[1]);
               break;
           }
        }
        
        if(pluginFound==false)
        {
            System.out.println("Plugin NICHT gefunden");
            content = "<br><font color=\"FF0000\"> Plugin wurde nicht gefunden</font> </br>";
        }
          hp.printHTML(socket, content);
          /*Schliessen*/ 
          socket.close();
          in.close();
          
    }
    /*
     * The getURL method gets the URL, it splits the URL, and stores it into an
     * array, the array gets send back to the httprequest class
     * The [0] consist the current plugin
     * The [1] consist the current chosen option
     */
    public String[] getURL(String request){
        String splitArray[]; /*Array for splitting the String*/
        String urlArray[] = {"",""}; /*Final Array*/
        
        System.out.println("requestLine: " + request);
        
        /*
         * "GET " before the URL and " HTTP/1.1" after the URL get split
         * away
         */
        splitArray = request.split(" ");
        /*
         * [0] = "GET " 
         * [1] = True URL
         * [2] = " HTTP/1.1"
         */
        String newrequestLine = splitArray[1];
        splitArray = newrequestLine.split("/");
        
        System.out.println("Length: " + splitArray.length);
        
        /*no path and option requested => "StatischeDateien" as index page*/
        if(splitArray.length <= 1)
        {
           	urlArray[0] = "StatischeDateien";
           	urlArray[1] = null;
        }
        /*path with no option requested*/
        else if(splitArray.length == 2)
        {
            System.out.println("Keine Option angegeben");
            urlArray[0] = splitArray[1];
            urlArray[1] = null;
        }
        /*path and option requested*/
        else
        {
            System.out.println("Option angegeben");
            urlArray[0] = splitArray[1];
            urlArray[1] = splitArray[2]; 
        }
       
        System.out.println("url " + urlArray[0]);
        System.out.println("option " + urlArray[1]);
        
        return urlArray;
    }


}
