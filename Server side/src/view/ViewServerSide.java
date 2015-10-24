package view;

/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> View </h1>
 * View interface represent a generally view layer of MVC
 */
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;


public interface ViewServerSide {
	/**
	 * Starting the View layer
	 */
	public void start();
	public void tellTheUserSolutionIsReady(String mazeName);
	/**
	 * Receive maze solution and print it to the user. 
	 * @param mazeName - maze name
	 * @param solution - maze solution
	 */
	public void printSolutionToUser(String mazeName,Solution<Position> solution);
	/**
	 * Set the commands hash map and cli.menu
	 * @param viewCommandMap - the command map as created in MyController
	 */
	public void setCommands(HashMap<String, Command> viewCommandMap);
	/**
	 * Set the cliMenu to be the menu itself. 
	 * @param cliMenu - The menu to be put in cliMenu
	 */
	public void setCommandsMenu(String cliMenu);
	/**
	 * Display a generic error message to the user 
	 * @param s - the error to display (String). 
	 */
	public void errorNoticeToUser(String s);

	public int getUserCommand();
	
	public void setUserCommand(int commandID);
	
	public void notifyObservers();
	
	//public void notifyObservers(Object o);

	public void displayData(Object data);
	
	

	
	public HashMap<String, Command> getViewCommandMap();
	
}
