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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The PluginManager Class consists of the initializePlugins method
 */

public class PluginManager {
	
	/*
     * The initializePlugins method looks into to plugins.txt file and
     * loads all the class which names are written in the file
     * it then creates a list with those classes and sends them back to the
     * Httprequest
     * 
     */
    
    public List<Class> initializePlugins() throws IOException{
		Class aClass = null;
		ClassLoader classLoader = PluginManager.class.getClassLoader();
		List <Class> cList = new ArrayList();
    	File theDir = new File("Plugins");
    	String[] pluginName;
    	String filename;

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
    	    		filename = listOfFiles[i].getName();
    	    		pluginName = filename.split("\\.");
    	    		System.out.println("File " + pluginName[0]);
    	    		try {
                        //Gefundenes File wird als Klasse geladen
                        aClass = classLoader.loadClass(pluginName[0]);
                        cList.add(aClass);
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
    	    	} else if (listOfFiles[i].isDirectory()) {
    	    		System.out.println("Directory " + listOfFiles[i].getName());
    	    	}
    	    }
    	}
        return cList;
    }

}
