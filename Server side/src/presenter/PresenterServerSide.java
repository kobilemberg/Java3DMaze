package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.ModelServerSide;
import view.ViewServerSide;

public class PresenterServerSide implements Observer {
	ViewServerSide view;
	ModelServerSide model;
	HashMap<String, Command> viewCommandMap;
	
	
	/**
	* Instantiates a new  my own controller with given view and model.
	* @param view View represent the view layer
	* @param model Model represent the model layer
	* @return new MyController as instance
	* */
		public PresenterServerSide(ViewServerSide view, ModelServerSide model) {
			super();
			this.view = view;
			this.model = model;
			
			HashMap<String, Command> viewCommandMap = new HashMap<String, Command>();
			
			viewCommandMap.put("init server",new Command() 
			{
				@Override
				public void doCommand(String[] args) {
					try {
						view.setUserCommand(1);
						((Observable)view).notifyObservers(args);
					} catch (Exception e) {
						e.printStackTrace();
						view.errorNoticeToUser("Exception: problem with args");
					}	
				}
			});
			
			viewCommandMap.put("exit",new Command() 
			{
				@Override
				public void doCommand(String[] args) {
					view.setUserCommand(-1);
					((Observable)view).notifyObservers(args);
					model.exit();}
			});
			
			viewCommandMap.put("stop server",new Command() 
			{
				@Override
				public void doCommand(String[] args) {
					view.setUserCommand(2);
					((Observable)view).notifyObservers(args);
					model.stopServer();}
			});
			
			String cliMenu=new String();
			cliMenu+= "1:	init server\n";
			cliMenu+= "-1:	exit\n";
			this.viewCommandMap = viewCommandMap;
			view.setCommands(viewCommandMap);
			view.setCommandsMenu(cliMenu);
			
		}

	//Getters and setters
		
		/**
	 * @return the viewCommandMap
	 */
	public HashMap<String, Command> getViewCommandMap() {
		return viewCommandMap;
	}

	/**
	 * @param viewCommandMap the viewCommandMap to set
	 */
	public void setViewCommandMap(HashMap<String, Command> viewCommandMap) {
		this.viewCommandMap = viewCommandMap;
	}

		/**
		 * This method will set controller view layer
		 * @param view View represent the view layer
		 */
		public void setView(ViewServerSide view){this.view = view;}
		/**
		* This method will set controller model layer
		* @param model Model represent the model layer
		*/
		public void setModel(ModelServerSide model){this.model = model;}
		/**
		* This method will return the controller view layer
		* @return View instance represent the view layer of the controller
		*/
		public ViewServerSide getView(){return view;}
		/**
		* This method will return the controller model layer
		* @return Model instance represent the Model layer of the controller
		*/
		public ModelServerSide getModel(){return model;}
		
		public void errorNoticeToViewr(String s) {view.errorNoticeToUser(s);}
	
		
		
		
	
	@SuppressWarnings("unchecked")
	@Override
	 public void update(Observable o, Object args) {
		if(o == view)
		{
			String[] argArr = ((String[])args).clone();
			
			int input = view.getUserCommand();
			switch (input) {
			
			case 1:
				//args[0] = numOfClients
				try {model.initServer();} catch (Exception e) {
					
					e.printStackTrace();
					view.errorNoticeToUser("Exception: Could not start server");}
				break;
			case 2:
				//args[0] = numOfClients
				try {model.stopServer();} catch (Exception e) {
					
					e.printStackTrace();
					view.errorNoticeToUser("Exception: Could not stop server");}
				break;
			case 4:
			{
				//args[0] = portNumber, args[1] = maximumClients
				try {
					if(args != null){
						String[] params = (String[]) args;
						model.changeSettings(params[0],params[1]);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			}	
			case -1: 
				model.exit();
				break;
			default:
				break;
			}
		}

		else if(o == model)
		{
			int modelCompletedNum = model.getModelCompletedCommand();
			Object[] dataToPassToView;
			switch (modelCompletedNum) 
			{
				case 1:
					//dir path;
					view.displayData(model.getData());
					break;
					
				case 2:
					//Stop Server
					view.displayData(model.getData());
					break;
					
				case 3:
					//Update number of clients
					System.out.println("Prestenter. ");
					view.displayData(model.getData());
					System.out.println("Prestenter 2. ");
					break;
					
				case 4:
					//Change Settings (Port)
					view.displayData(model.getData());
					break;
				/*
				case 5:
					//save maze <Maze name> <File name>
					dataToPassToView = (Object[]) model.getData();
					view.tellTheUserTheMazeIsSaved((String)dataToPassToView[0], (String)dataToPassToView[1]);
					break;	
				
				case 6:
					//load maze <File name> <Maze name>
					dataToPassToView = (Object[]) model.getData();
					view.tellTheUserTheMazeIsLoaded((String)dataToPassToView[0], (String)dataToPassToView[1]);
					break;
				
				case 7:
					//maze size <Maze name>
					dataToPassToView = (Object[]) model.getData();
					view.tellTheUsersizeOfMazeInRam((String)dataToPassToView[0], (double)dataToPassToView[1]);
					break;
				
				case 8:
					//file size <File name>
					dataToPassToView = (Object[]) model.getData();
					view.tellTheUsersizeOfMazeInFile((String)dataToPassToView[0], (double)dataToPassToView[1]);
					break;*/
							
				case 9:
					//solve <Maze name> <A*\BFS>
					view.tellTheUserSolutionIsReady((String)(model.getData()));
					break;
					
				case 10:
					//display solution <Maze name>   //Solution<Position>
					dataToPassToView = (Object[]) model.getData();
					view.printSolutionToUser((String)(dataToPassToView[0]), (Solution<Position>)(dataToPassToView[1]));
					break;
					
				case -2:
					//Error
					try {
						view.errorNoticeToUser((String) model.getData());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
					
				default:
					break;
			}
		}
	}
}


