package boot;


import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import presenter.PropertiesServerSide;


public class writePropetiesServerSide {

	public static void main(String[] args) {

		
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("External server files/properties.xml")));
			encoder.writeObject(new PropertiesServerSide(20, 20, "A*", 12345));
			//encoder.flush();
			encoder.close();
		} catch (Exception e) {
			System.out.println("problem with writing XML");
		}
		
		
		
	}
	
}
