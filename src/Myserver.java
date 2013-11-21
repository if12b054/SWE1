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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * The Myserver class provides the main and the start method
 * 
 * In the main method the Server is started
 * 
 * In the start method a listening port is created on the portnumber 8080
 * also an infinite loop is started so that there could theoretically be
 * infinite users
 * The new Request gets encapsulated into its own thread
 */

public class Myserver {    
  public static void main( String[] args ) throws Exception
  {
      Myserver Server = new Myserver();     
      Server.start();
  }
  
  public void start(){
   ServerSocket listener = null;
      try {
          listener = new ServerSocket(8080); /*Creating the Serversockets*/
      } catch (IOException ex) {
           ex.printStackTrace();
      }
      System.out.println("Server ist gestartet");
      
      try{
          for(;;){   
           Socket s = listener.accept(); /*Connection gets accepted*/
           System.out.println("---------------------------------------------------");
           System.out.println("Client Socket: "+ s.getInetAddress() +":"+ s.getPort()); 
           try {
               HttpRequest request = new HttpRequest(s); /*Request is created*/
               Thread thread = new Thread(request); /*Request Object -> Thread*/
               thread.start();
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          }
      } catch (IOException ex) {
           ex.printStackTrace();
      }
  }
};