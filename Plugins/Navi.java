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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Navi implements Plugin {
    //Konstruktor
    public String run(String option){
    	String content;
    	
    	content = "Naviiiii!";
    	
        return content;
    }
    
    /*converts filestring to an HTML-string that will display the file in the browser*/
    public String convertStrToHTML(String filename) throws IOException{
    	File curFile = new File("Files/" + filename);
    	File curDir = new File(".");
    	String filepath = "";
    	String content = "";
    	/*get file extension*/
    	String extension = "";
		int j = filename.lastIndexOf('.');
		if (j > 0) {
		    extension = filename.substring(j+1);
		}
		
		System.out.println("extension: "+ extension);
		
		if (curFile.isFile()) {
			/*load file as image*/
			if(extension.equals("gif") ||
				extension.equals("png") ||
				extension.equals("jpg") ||
				extension.equals("bmp"))
			{
				filepath = curDir.getCanonicalPath();
				content="<img src=\"file:///" + filepath + "/" + filename + "\" /><br>";
			}
			else
			{
				content="Format for "+ filename +" is not supported.<br>";
			}
    	}
		else
		{
			content="404 ERROR!<br>";
		}
    	return content;
    }
}
