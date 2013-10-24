
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

class HttpRequest implements Runnable{
    //Konstruktor
    public HttpRequest(Socket socket) throws Exception{
       this.socket = socket;
    }
    
    public void setRequest(String request){
        this.request = request;
    }
    
    public void setURL(String url){
        this.url = url;
    }
    
    public void setResponse(String response){
        this.response = response;
    }
    
    @Override
    public void run(){
        try{
            //Verbindung zu processRequest, da im run keine
            processRequest();
        } catch(Exception ex){
            ex.printStackTrace();
        }   
    }
    private void processRequest() throws Exception{
        //Variable für die erhaltenen Messages
        String line;
        //Hilfsvariable für das splitten der URL
        String [] splitArray;
        //Objekt von Myserver wird erstellt
        //Pluginmanagerobjekt wird erstellt
        PluginManager pm = new PluginManager();
        
        //BufferedeReader für das lesen aus dem
        //Inputstream wird erstellt
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        
         printHTML(socket);
         //RequestLine wird gespeichert = erste Zeile im 
         //HTTP Request
         String requestLine = in.readLine();
         setURL(requestLine);
         
        splitArray = requestLine.split("/");
        /*System.out.println("1. " + splitArray[0]);
        System.out.println("2. " + splitArray[1]);*/
        
        String newrequestLine = splitArray[1];
        splitArray = newrequestLine.split(" ");
        System.out.println("URL:" + splitArray[0]);
        
        String newURL = splitArray[0];
        
        //Variable für das im plugins.txt gespeicherte Plugin
        Class plugins;
        //Methode die das Plugin aus der Liste zurückgibt
        plugins = pm.initializePlugins();
        //Kontrolle
        
        if(newURL.equals(plugins.getName())){
           StatischeDateien sd = (StatischeDateien) plugins.newInstance();
           sd.run();
        }
        else{
            System.out.println("not");
        }
         
          //Schliessen 
          socket.close();
          in.close();
          
    }
    
    public void printHTML(Socket socket){
      FileReader fr = null;
      String text = null;
      String content = null;
      
      try {
          /*fr = new FileReader("index.hmtl");
          //BufferedReader für das Lesen aus der Datei
          BufferedReader br = new BufferedReader(fr);
          String line;
          
          while((line = br.readLine()) != null){
              text += line;
          }*/
         content = "<html> \n" +
                   "	<head><title>WEBSERVER</title></head>\n" +
                   "		<body>\n" +
                   "                    <h1>Auswahl der Plugins</h1>" +
                   "			<a href=\"StatischeDateien\">StatischeDateien</a>\n" +
                   "			<a href=\"Navi\">Navi</a>\n" +
                   "		</body>\n" +
                   "</html>";
         

         BufferedWriter out = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream()));
         
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("host:webserver\r\n");
        out.write("Content-Type: text/html\r\n");
        out.write("Content-Length:" + content.length() +  "\r\n");
        out.write("Connection:close\r\n");
        out.write("\n");
        out.write(content);
        out.write("\r\n");
        out.flush(); 
          
      } catch (FileNotFoundException ex) {
          ex.printStackTrace();
      } catch (IOException ex) {
          ex.printStackTrace();
      }  
  }
    private String request;
    private String url;
    private String response;
    private Socket socket;   

}
