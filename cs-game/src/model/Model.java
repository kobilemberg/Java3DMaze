package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1>Model</h1>
 * Model interface represent a generally model layer of MVP
 */

import java.io.IOException;
import IO.MyCompressorOutputStream;

public interface Model {
	/**
	* This method will return a string of files and directories of given a local path
	* @param path String represrnt the local path
	*/

	public void dir(String path);


	/**
	* This method will generate 3d maze with given name, generator and dimensions.
	* @param name String represent the name of the maze
	* @param generator represent a maze generator, default is MyMaze3dGenerator
	* @param floors represent the number of floors as string
	* @param lines represent the number of lines as string
	* @param columns represent the number of columns
	*/
	public void generateMazeWithName(String name, String generator, String floors, String lines,String columns);


	/**
	* This method return a Maze3d object with given name.
	* @param name String represent the name of the Maze3d instance to return.
	*/
	public void getMazeWithName(String string);


	/**
	* This method return int[][] represent the cross section by some axe with given index of maze
	* @param axe String represent the axe, options are X,Y,Z.
	* @param index String represent the index of the axe
	* @param name String represent the name of the Maze3d instance
	*/
	public void getCrossSectionByAxe(String axe, String index, String mazeName);


	/**
	* This method save an maze object in file via simple compress algorithem
	* @see MyCompressorOutputStream API. 
	* @param mazeName String represent instance name to save
	* @param fileName String represent name of the file that the maze will be save to.
	* @throws IOException of problems with writing to files\open new files and etc'
	*/
	public void saveCompressedMazeToFile(String mazeName, String fileName) throws IOException;


	/**
	* This method will load an maze object from file as byte array via simple compress algorithem
	* @see MyCompressorOutputStream API. 
	* @param mazeName String represent instance name to save
	* @param fileName String represent name of the file that the maze will be save to.
	* @throws IOException of problems with writing to files\open new files and etc'
	*/
	public void loadAndDeCompressedMazeToFile(String fileName, String mazeName) throws IOException;


	/**
	* This method will return the size of maze with mazeName object in ramdisk
	* @param mazeName String represent instance name
	*/
	public void getSizeOfMazeInRam(String mazeName);

	
	/**
	* This method will return the size of file that contains maze
	* @param fileName String represent the file name to check
	*/
	public void getSizeOfMazeInFile(String fileName);


	/**
	* This method will solve maze with given maze and algorithm
	* @param mazeName String represent the name of the maze to solve.
	* @param algorithm String represent the algorithm to solve with
	*/
	public void solveMaze(String mazeName, String algorithm);


	/**
	* This method will return solution of maze with given maze name
	* @param mazeName String represent the name of the maze to solve.
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
	*/
	public boolean isLoaded(String mazeName);
	
	/**
	 * This method changes the properties settings instance of the model
	 * @param fileName represent a new properties XML to read
	 * @return true if the XML has been loaded successfully
	 */
	public boolean changePropertiesByFilename(String fileName); 
	
	/**
	 * This method returns a data to other application layer
	 * @return Object represent the data from the other layers
	 */
	public Object getData();
	
	/**
	 * This method sets a data to pass to other application layer
	 * @param o Object represent the data to set to pass for other layers
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
	 * This method update the model with current location for specific maze
	 * @param mazeName represent the name of the maze
	 * @param currentX represent the maze current location floor
	 * @param currentY represent current location rows
	 * @param currentZ represent current location columns
	 */
	public void setMazeWithCurrentLocationFromGui(String mazeName, String currentX, String currentY, String currentZ);

	/**
	 * This method changes model settings by runtime and not with reading XML off settings
	 * @param server represent the server address
	 * @param port represent the server port
	 * @param generator represent the generator type
	 * @param solver represent the solver to use.
	 */
	public void changeSettings(String server, String port, String generator, String solver);
}
