package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1>ModelServerSide</h1>
 * ModelServerSide interface represent a generally model layer of MVP
 */

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface ModelServerSide {
	/**
	* This method will solve maze with given maze and algorithm
	* @param mazeName String represent the name of the maze to solve.
	* @param algorithm String represent the algorithm to solve with
	* @param maze 
	* @return 
	*/
	public Solution<Position> solveMaze(String mazeName, String algorithm, Maze3d maze);
	/**
	* This close all files and threads and will terminate the model activity
	*/
	public void exit();
	/**
	 * This method goal is to return some data that model hold
	 * @return Object represent the data
	 */
	public Object getData();
	/**
	 * This method goal is to set some data that model hold
	 * @param o Object represent some data that model hold
	 */
	public void setData(Object o);
	/**
	 * Returns the number of command represent the action that should be done by presenter
	 * @return int number of command represent the action that should be done by presenter
	 */
	public int getModelCompletedCommand();
	/**
	 * Sets the number of command represent the action that should be done by presenter
	 * @param commandNum int represent the number of command represent the action that should be done by presenter
	 */
	public void setModelCommandCommand(int commandNum);
	/**
	 * This method starts the TCP server 
	 * This should to be done on a new thread
	 */
	public void initServer();
	/**
	 * This method will close the thread and all his resources
	 */
	public void stopServer();
	/**
	 * This method will set the number of concurrent TCP connections
	 * @param num represent number of server client
	 */
	public void setNumberOfClients(int num);
	/**
	 * This method will return the number of concurrent TCP connections
	 * @return int represent the number of concurrent TCP connections
	 */
	public int getNumberOfClients();
	/**
	 * This methodd will change model settings
	 * @param string represent server port
	 * @param string2 represent the maximum allowed concurrent TCP connections
	 */
	public void changeSettings(String string, String string2);

	

}
