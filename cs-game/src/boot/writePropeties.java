package boot;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import presenter.Properties;

/**
 * This Main will generate a Properties.XML File, 
 * This will use the Properties Class from Presenter Package
 * And Create it based on the Client Needs. 
 * @author Alon and Kobi
 * @version 1.1
 */
public class writePropeties {

	public static void main(String[] args) {
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("External client files/properties.xml")));
			String defaultAlgorithm = "MyMaze3dGenerator";
			String defaultSolver ="A*";
			int port = 12345; 
			int maxThreads = 4;
			
			// Write the file.  
			encoder.writeObject(new Properties(defaultAlgorithm, defaultSolver, "GUI", "localhost", port, maxThreads)); 
			encoder.close();
		} catch (Exception e) {
			System.out.println("Error: Problem writing properties XML in Client Side.");
		}
		
		
		
	}
	
}
