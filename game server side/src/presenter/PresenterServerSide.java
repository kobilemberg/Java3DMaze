package presenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import algorithms.mazeGenerators.Maze3d;
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
			
			
			viewCommandMap.put("solve",new Command() 
			{
				@Override
				public void doCommand(String[] args) {
						try {
							view.setUserCommand(9);
							((Observable)view).notifyObservers(args);
						} catch (Exception e) {
							e.printStackTrace();
							view.errorNoticeToUser("Exception: problem with args");
						}	
				}
			});
			
			viewCommandMap.put("display solution",new Command() 
			{
				@Override
				//args[1] = mazeName
				public void doCommand(String[] args) {
						try {
							view.setUserCommand(10);
							((Observable)view).notifyObservers(args);
						} catch (NullPointerException e) {
							e.printStackTrace();
							view.errorNoticeToUser("Exception: unexisted solution");
						}
				}
			});
			
			viewCommandMap.put("exit",new Command() 
			{
				@Override
				public void doCommand(String[] args) {
					view.setUserCommand(11);
					((Observable)view).notifyObservers(args);
					model.exit();}
			});
			String cliMenu=new String();
			cliMenu+= "1:	init server\n";
			cliMenu+= "2:	generate 3d maze <Maze name> <MyMaze3dGenerator\\SimpleMaze3dGenerator> <X> <Y> <Z>\n";
			cliMenu+= "3:	display <Maze name>\n";
			cliMenu+= "4:	display cross section by {X,Y,Z} <index> for <Maze name>\n";
			cliMenu+= "5:	save maze <Maze name> <File name>\n";
			cliMenu+= "6:	load maze <File name> <Maze name>\n";
			cliMenu+= "7:	maze size <Maze name>\n";
			cliMenu+= "8:	file size <File name>\n";
			cliMenu+= "9:	solve <Maze name> <A*\\BFS>\n";
			cliMenu+= "10:	display solution <Maze name>\n";
			cliMenu+= "11:	exit\n";
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
		if(o==view)
		{
			String[] argArr = ((String[])args).clone();
			
			int input = view.getUserCommand();
			switch (input) {
			
			case 1:
				//args[0] = numOfClients
				try {model.initServer(argArr[0]);} catch (Exception e) {
					
					e.printStackTrace();
					view.errorNoticeToUser("Exception: problem with args");}
				
				break;
			
			
			
			case 9:
				try {model.solveMaze(argArr[0], argArr[1]);} catch (Exception e) {view.errorNoticeToUser("Exception: problem with args");}
				break;
				
			case 10:
				try {
					model.getSolutionOfMaze(argArr[0]);} catch (Exception e) {view.errorNoticeToUser("Exception: problem with args");}
				break;
				
			case 11:
				model.exit();
				break;
				
			case 12:
				try{
					model.changePropertiesByFilename(argArr[0]);					
				}catch (Exception e) { view.errorNoticeToUser("Exception: Open Properties failed, problem with file " + argArr[0]);
				break;
				}
				
			case 13:
				try {
					if(args!=null)
					{
						String[] params = (String[])args;
						model.setMazeWithCurrentLocationFromGui(params[0],params[1],params[2],params[3]); //NameOfMaze,CurrentX - floor, CurrentY - x, CurrentZ - y
					}
					
				} catch (Exception e) {
					System.out.println("Exception in case 13");
					e.printStackTrace();
				}
				
				
				
			default:
				break;
			}
		}

		else if(o==model)
		{
			int modelCompletedNum = model.getModelCompletedCommand();
			Object[] dataToPassToView;
			switch (modelCompletedNum) 
			{
				case 1:
					//dir path;
					view.displayData(model.getData());
					break;
					
				/*case 2:
					//maze is ready;
					String s =(String) ((Object[]) model.getData())[0];
					view.tellTheUserMazeIsReady(s);
					break;
					
				case 3:
					//display maze
					dataToPassToView = (Object[]) model.getData();
					view.printMazeToUser((Maze3d)dataToPassToView[0], (String)dataToPassToView[1]);
					break;
					
				case 4:
					//display cross section by {X,Y,Z} <index> for <Maze name>
					dataToPassToView = (Object[]) model.getData();
					view.printToUserCrossedArray((int[][])dataToPassToView[0], (String)dataToPassToView[1],  (String)dataToPassToView[2], (String)dataToPassToView[3]);
					break;
				
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
					
				case -1:
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


