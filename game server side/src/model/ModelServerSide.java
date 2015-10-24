package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1>Model</h1>
 * Model interface represent a generally model layer of MVC
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
	public Object getData();
	public void setData(Object o);
	public int getModelCompletedCommand();
	public void setModelCommandCommand(int commandNum);
}
