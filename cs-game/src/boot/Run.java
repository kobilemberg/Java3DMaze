package boot; 

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.MazeBasicWindow;
import view.MyView;


public class Run {

	public static void main(String[] args) 
	{
		
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("External client files/properties.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File External client files/properties.xml not found");
		}
		Properties properties=(Properties)decoder.readObject();
		
		
		if(properties.getUI().equals("GUI"))
		{
			MyModel model = new MyModel(properties);
			MazeBasicWindow view=new MazeBasicWindow("3D Maze Game", 1000, 600,null);
			Presenter presenter = new Presenter(view, model);
			view.setCommands(presenter.getViewCommandMap());
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.start();
		} 
		
		else
		{
			MyView view = new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
			MyModel model = new MyModel(properties);
			Presenter presenter = new Presenter(view, model);
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.start();
		}
		

	}

}
