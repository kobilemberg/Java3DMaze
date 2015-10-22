package view;

/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1>MazeDisplayer</h1>
 * MazeDisplayer abstract class extends Canvas
 * This class represent a canvas who know to draw a maze represent by int[][]
 */


import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally

public abstract class MazeDisplayer extends Canvas{
	
	// just as a stub...
	int[][] mazeData
		/*
		={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
			{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
			{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
			{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
			{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
			{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
			{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
		}*/;

	/**
	 * Instantiates a new  MazeDisplayer with given: shell and style
	 * @param parent represent a shell
	 * @param style int represent the SWT constant style
	 */
	public MazeDisplayer(Composite parent, int style) {super(parent, style);}

	/**
	 * This method will set the int[][] represent the maze to draw
	 * @param mazeData int[][] represent the maze to draw
	 */
	public void setMazeData(int[][] mazeData){this.mazeData=mazeData;}
	/**
	 * This method will set the charecter position on the maze canvas in order to draw it.
	 * @param row represent the maze character row position
	 * @param col represent the maze character col position
	 */
	public abstract  void setCharacterPosition(int row,int col);
	
	/**
	 * This method will move the character position to next line on the int[][] of the maze
	 */
	public abstract void moveForward();
	/**
	 * This method will move the character position to previous line on the int[][] of the maze
	 */
	public abstract  void moveBackward();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public  abstract void moveDown();
	
	public  abstract void moveUp();
	
	public abstract void moveCharacter(int x,int y);
	
	/**
	 * @return the characterX
	 */
	public abstract int getCharacterX();
	/**
	 * @param characterX the characterX to set
	 */
	public abstract void setCharacterX(int characterX);
	/**
	 * @return the characterY
	 */
	public abstract int getCharacterY();
	/**
	 * @param characterY the characterY to set
	 */
	public abstract void setCharacterY(int characterY);
	/**
	 * @return the exitX
	 */
	public abstract int getExitX();
	/**
	 * @param exitX the exitX to set
	 */
	public abstract void setExitX(int exitX);
	/**
	 * @return the exitY
	 */
	public abstract  int getExitY();
	/**
	 * @param exitY the exitY to set
	 */
	public abstract void setExitY(int exitY);

	public abstract void setExitFloor(int xPosition);

}