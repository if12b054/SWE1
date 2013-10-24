import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;


public class Myserver {    
  public static void main( String[] args ) throws Exception
  {
      //Neues Serverobjekt wird erstellt
      Myserver Server = new Myserver();     
      //Server wird gestartet
      Server.start();
  }
  
  public void start(){
   ServerSocket listener = null;
      try {
          //Erstellung eines Serversockets
          listener = new ServerSocket(8080); 
      } catch (IOException ex) {
           ex.printStackTrace();
      }
      System.out.println("Server ist gestartet");
      
      try{
          //Endlosschleife, dadurch mehrere Verbindungen
          for(;;){ 
           //Socket wird gebindet    
           Socket s = listener.accept(); 
           //Ausgabe des verbundenen Clients
           System.out.println("Client Socket: "+ s.getInetAddress() +":"+ s.getPort()); 
           
              try {
                  //Object das die Httprequests verarbeitet
                  HttpRequest request = new HttpRequest(s);
                  //Neuer Thread wird erzeugt
                  Thread thread = new Thread(request);
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


  
  /*public void writeHTML(Class plugins){
      String name = "index.html";
      File f = new File(name);
      
      if(f.exists() ){
          f.delete();
      }
      
    try {
      f.createNewFile();
      System.out.println("created new file " + f.getName());
    } catch (IOException ex) {
      System.out.println("Fehler beim Erstellen des Files" + ex);
    }
      try {
          FileWriter writer = new FileWriter(name, true);
          writer.write("<html> "
                  + "<head><title>WEBSÖRVA</title></head>"
                  + "<body>"
                  + "<a href=\"" + plugins.getName() + "\">"
                  + plugins.getName() + "</a>"
                  + "</body>"
                  + "</html>");
          writer.flush();
          writer.close();
          
      } catch (FileNotFoundException ex) {
          Logger.getLogger(Myserver.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(Myserver.class.getName()).log(Level.SEVERE, null, ex);
      }
  }*/
   