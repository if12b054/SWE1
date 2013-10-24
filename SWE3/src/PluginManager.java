
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PluginManager {
    
    public Class initializePlugins() throws IOException{
        try {
            //Filereader wird erstellt
            //Falls File nicht gefunden Filenotfound exception
            FileReader fr = new FileReader("plugins.txt");
            //BufferedReader für das Lesen aus der Datei wird erstellt
            BufferedReader br = new BufferedReader(fr);
            //Variable für die einzelnen Zeilen der Datei
            String line;
            //Variable für die Klasse 
            Class aClass = null;
            //TODO: Vector aus Instanzen der Klassen
            //Vector temp = new Vector();
            //Classloader wird erstellt
            ClassLoader classLoader = PluginManager.class.getClassLoader();
            
            //Zeilenweises auslesen aus dem File
            while((line = br.readLine()) != null){
                    try {
                        //Die Klassen werden gesucht
                        aClass = classLoader.loadClass(line);
                        //System.out.println("aClass.getName() = " + aClass.getName());
                        //instance[i] = aClass.newInstance();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (SecurityException ex) {
                         ex.printStackTrace();
                    }
            }
            return aClass;

            
        } catch (FileNotFoundException ex) {
             ex.printStackTrace();
        }
        return null;
        
    }

}
