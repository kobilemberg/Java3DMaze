package boot; 

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

import model.MyModelServerSide;
import presenter.PresenterServerSide;
import presenter.PropertiesServerSide;
import view.MazeBasicWindow;
import view.MyViewServerSide;
import view.ViewServerSide;


public class StartServer {

	public static void main(String[] args) 
	{
		
		XMLDecoder decoder=null;
		try 
		{
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("External files/properties.xml")));
		} catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: File External files/properties.xml not found");
		}
		PropertiesServerSide properties=(PropertiesServerSide)decoder.readObject();
		decoder.close();
		
		MyViewServerSide view = new MyViewServerSide(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		MyModelServerSide model = new MyModelServerSide();
		PresenterServerSide presenter = new PresenterServerSide(view, model);
		view.addObserver(presenter);
		model.addObserver(presenter);
		view.start();
		
		

	}

}
