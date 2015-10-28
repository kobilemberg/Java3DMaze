package boot; 

import model.MyModelServerSide;
import presenter.PresenterServerSide;
import view.ServerBasicWindow;


public class StartServer {

	public static void main(String[] args) 
	{
		/*
			XMLDecoder decoder=null;
			try 
			{
				decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("External server files/properties.xml")));
			} catch (FileNotFoundException e) 
			{
				System.out.println("ERROR: File External server files/properties.xml not found");
			}
			
			PropertiesServerSide properties=(PropertiesServerSide)decoder.readObject();
			decoder.close();
			MyViewServerSide view = new MyViewServerSide(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		*/
		
		MyModelServerSide model = new MyModelServerSide();
		ServerBasicWindow view = new ServerBasicWindow("Solution Server", 188, 202); 

		PresenterServerSide presenter = new PresenterServerSide(view, model);
		view.addObserver(presenter);
		model.addObserver(presenter);
		
		
		view.run();
	}

}
