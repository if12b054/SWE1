
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StatischeDateien {
    //Konstruktor
    public void run(){
            FileReader fr = null;
        try {
            System.out.println("Es hat funktioniert!!");
            System.out.println("Liste aller plugins:");
            fr = new FileReader("plugins.txt");
            //BufferedReader für das Lesen aus der Datei wird erstellt
            BufferedReader br = new BufferedReader(fr);
            //Variable für die einzelnen Zeilen der Datei
            String line;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
            
            
            fr.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
