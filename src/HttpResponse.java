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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The HttpResponse class consists of the printHTML method
 */

public class HttpResponse {
    /*
     * The printHTML method gets the current socket and the content it has
     * to print, the method sends a the site in form of HTML Code to the user
     * 
     */
    
	public void printHTML(Socket socket, String content){
		FileReader fr = null;
		String text = null;
		String menu = null;
		String[] pluginname;
		List<String> pluginList = new ArrayList<String>();
      
		try {
			File theDir = new File("Plugins");
    	  
			/*if the directory does not exist, create it*/
			if (!theDir.exists()) {
				System.out.println("creating directory: Plugins");
      	      	boolean result = theDir.mkdir();  

      	      	if(result) {    
      	      		System.out.println("Plugin Directory Created.");  
      	      	}
      	  	}
      	  	else
      		{
      	  		File[] listOfFiles = theDir.listFiles();
      	  		for (int i = 0; i < listOfFiles.length; i++) {
      	  			if (listOfFiles[i].isFile()) {
      	  				pluginname = listOfFiles[i].getName().split("\\.");
      	  				pluginList.add(pluginname[0]);
      	  			}
      	  		}
      		}
    	  
			menu =	"<style> *{margin:0;padding:0}"+
					"ul{float:left; width:100%; background:rgba(0,0,0,0.3);height:30px;overflow:hidden;}" + 
					"li{list-style-type:none;padding: 4px, 6px;display:inline;}" + 
					"li a {text-decoration:none;color:black;line-height:30px;padding:15px;}"+ 
					"li a:hover {background:rgba(0,0,0,0.6); color:white;} </style>\n" +
					"<html> \n" +
					"<head><title>WEBSERVER</title></head>\n" +
					"<body>\n" +
					"<div style=\"width:800px;background:#EEE;border:1px solid #AAA;border-top:0;min-height:700px;margin:auto;\">\n" +
					"<h1 style=\"font-size: 76px; width:100%; background:rgba(0,0,0,0.15);line-height:110px;\">Our Plugins</h1><ul>";
         
         /*add available plugins to list*/
         for(int i = 0; i < pluginList.size(); i++)
         {
             menu += "<li><a href=\"" + pluginList.get(i) + "\">" + pluginList.get(i) + "</a></li>\n";
         }
         menu+="</ul>\n";
         
         content = menu + content + "</div>\n" + "</body>\n" + "</html>";         

         BufferedWriter out = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream()));
         
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("host:webserver\r\n");
        out.write("Content-Type: text/html\r\n");
        out.write("Content-Length:" + content.length() +  "\r\n");
        out.write("Connection:close\r\n");
        out.write("\n\n");
        out.write(content);
        out.write("\r\n");
        out.flush(); 
          
      } catch (FileNotFoundException ex) {
          ex.printStackTrace();
      } catch (IOException ex) {
          ex.printStackTrace();
      }  
  }
}
