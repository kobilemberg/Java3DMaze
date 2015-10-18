package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1>Model</h1>
 * Model interface represent a generally model layer of MVC
 */

import java.io.IOException;
import IO.MyCompressorOutputStream;

public interface ModelServerSide {
	

	/**
	* This method will solve maze with given maze and algorithm
	* @param mazeName String represent the name of the maze to solve.
	* @param algorithm String represent the algorithm to solve with
	*/
	public void solveMaze(String mazeName, String algorithm);


	/**
	* This method will return solution of maze with given maze name
	* @param mazeName String represent the name of the maze to solve.
	* @return the solution of the maze named mazeName
	*/
	public void getSolutionOfMaze(String mazeName);

	/**
	* This close all files and threads and will terminate the model activity
	*/
	public void exit();


	/**
	* This boolean method returns true if the maze with name mazeName has been loaded
	* @param mazeName String represent the mazeName to check if loaded
	* @return True if the maze exists in the ram disk
	* @return False if maze is not exists in the ram disk
	*/
	public boolean isLoaded(String mazeName);

	public boolean changePropertiesByFilename(String fileName); 

	public Object getData();
	public void setData(Object o);
	public int getModelCompletedCommand();
	public void setModelCommandCommand(int commandNum);
	public void setMazeWithCurrentLocationFromGui(String mazeName, String currentX, String currentY, String currentZ);
	public void initServer();
}
