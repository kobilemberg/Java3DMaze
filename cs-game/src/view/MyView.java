package view;

/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyView </h1>
 * MyView class implements View interface, 
 * class goal is to act as MVP View layer and to display applications to end-user.
 */


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;
import java.util.Observable;

public class MyView extends Observable implements View {
	CLI cli;
	HashMap<String, Command> viewCommandMap;
	private String cliMenu;
	BufferedReader in;
	PrintWriter out;
	int userCommand=0;

	

	//Constructors
	/**
	 * Instantiates a new  my own view layer.
	 */
	public MyView()
	{
		super();
	}
	/**
	 * Instantiates a new  my own view with given: BufferedReader in, PrintWriter out
	 * @param in BufferedReader represent the input source
	 * @param out PrintWriter represent the output source
	 * @return new MyView as instance with BufferedReader in and PrintWriter out
	 */
	public MyView(BufferedReader in, PrintWriter out)
	{
		super();
		this.in = in;
		this.out=out;
	}
	/**
	 * Instantiates a new  my own view generator with given: BufferedReader in and PrintWriter out and HashMap<String, Command> represent a commands to run
	 * @param in BufferedReader represent the input source
	 * @param out PrintWriter represent the output source
	 * @param viewCommandMap HashMap<String, Command> represent a commands to run
	 * @return new MyView as instance with controller layer, BufferedReader in, PrintWriter out and commands 
	 */
	public MyView(BufferedReader in, PrintWriter out,HashMap<String, Command> viewCommandMap)
	{
		super();
		cli = new CLI(in, out,viewCommandMap);
	}
	@Override
	/**
	* this method will start to run the view layer
	*/
	public void start() {cli.start();}


	/**
	* this method will print int[][] array
	*/
	private void printArr(int[][] arr)
	{
		String strOfMazeMatrix="";
		for (int i=0;i<arr.length;i++)
		{
			strOfMazeMatrix+="{";
			for(int j=0;j<arr[0].length;j++){strOfMazeMatrix+=arr[i][j];}
			strOfMazeMatrix+="}\n";
		}
		out.println(strOfMazeMatrix);
		out.flush();
	}
	

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void printFilesAndDirectories(String filesAndDirOfPath) {out.println(filesAndDirOfPath);}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void tellTheUserMazeIsReady(String name) {
		out.println("View: Maze "+name+" is Ready, you can take it!");
		out.flush();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void printMazeToUser(Maze3d mazeWithName,String name) {
		out.println("Maze: "+name+"\n"+mazeWithName.toString());
		out.flush();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name) {
		out.println("Crossed maze: "+name+ " by axe: "+axe+" with index: "+index);
		out.flush();
		printArr(crossedArr);
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void tellTheUserTheMazeIsSaved(String mazeName, String filename) {
		out.println("Maze: "+mazeName+ " saved to:"+ filename);
		out.flush();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void tellTheUserTheMazeIsLoaded(String fileName, String mazeName) {
		out.println("Maze: "+mazeName+ " has been loaded from:"+ fileName);
		out.flush();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void tellTheUsersizeOfMazeInRam(String mazeName,Double size) {
		out.println("The size of maze: "+mazeName+" in ram memory is:" +size+"b");
		out.flush();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void tellTheUsersizeOfMazeInFile(String fileName, double sizeOfFile) {
		out.println("The size of file: "+fileName+" is: "+sizeOfFile+"b");	
		out.flush();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void tellTheUserSolutionIsReady(String mazeName) {
		out.println("Solution for "+mazeName+" is Ready, you can take it!");
		out.flush();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void printSolutionToUser(String mazeName,Solution<Position> solution) {
		out.println("Solution of: "+mazeName+"\n");
		out.flush();
		for (State<Position> p: solution.getSolution()){
			out.println(p.getCameFromAction() + " To: "+p.toString());
			out.flush();
			}
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setCommands(HashMap<String, Command> viewCommandMap) 
	{
		this.viewCommandMap = viewCommandMap;
		cli = new CLI(in, out,viewCommandMap);
		if(cliMenu!=null)
			cli.cliMenu = cliMenu;
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setCommandsMenu(String cliMenu) {
		this.cliMenu = cliMenu;
		if(cli!=null){cli.setCLIMenu(cliMenu);}	
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void errorNoticeToUser(String s) {
		out.println("Notification:\n"+s);
		out.flush();	
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getUserCommand() {return this.userCommand;}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setUserCommand(int commandID) 
	{
		this.setChanged();
		this.userCommand = commandID;
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void displayData(Object data) {
		out.println(data);
		out.flush();		
	}
	
	/**
	 * This method will return the CLI as instance
	 * @return CLI instance represent the cli of the view
	 */
	public CLI getCli() {return cli;}
	
	/**
	 * @param cli the cli to set
	 */
	public void setCli(CLI cli) {this.cli = cli;}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public HashMap<String, Command> getViewCommandMap() {return viewCommandMap;}

	/**
	 * @return the cliMenu
	 */
	public String getCliMenu() {return cliMenu;}
	
	/**
	 * @param cliMenu the cliMenu to set
	 */
	public void setCliMenu(String cliMenu) {this.cliMenu = cliMenu;}
	
	/**
	 * @return BufferedReader represent the input source
	 */
	public BufferedReader getIn() {return in;}
	
	/**
	 * @param in BufferedReader represent the input source to set
	 */
	public void setIn(BufferedReader in) {this.in = in;}
	
	/**
	 * @return PrintWriter as instance represent the output source
	 */
	public PrintWriter getOut() {return out;}
	
	/**
	 * @param PrintWriter as instance represent the output source to set for the program.
	 */
	public void setOut(PrintWriter out) {this.out = out;}
	
	
}
