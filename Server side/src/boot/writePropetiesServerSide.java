package boot;


import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import presenter.PropertiesServerSide;


public class writePropetiesServerSide {

	public static void main(String[] args) {
		//Properties p = new Properties(3, "MyMaze3dGenerator", "A*");
	//	System.out.println(p.toString());
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("External files/properties.xml")));
			String deafultAlgorithm = "MyMaze3dGenerator";
			String defaultSolver ="A*";
			
			encoder.writeObject(new PropertiesServerSide(20, 20, "A*", 12345));
			//encoder.flush();
			encoder.close();
		} catch (Exception e) {
			System.out.println("problem with writing XML");
		}
		
		
		
	}
	
}
