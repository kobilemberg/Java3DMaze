package view;

/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyViewServerSide </h1>
 * MyViewServerSide class implements View interface, 
 * class goal is to act as MVP View layer and to display server side.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;
import presenter.Controller;
import java.util.Observable;

public class MyViewServerSide extends Observable implements ViewServerSide {
	Controller controller;
	CLI cli;
	HashMap<String, Command> viewCommandMap;
	private String cliMenu;
	BufferedReader in;
	PrintWriter out;
	int userCommand=0;

	

	//Constructors
	/**
	 * Instantiates a new  my own MyViewServerSide.
	 */
	public MyViewServerSide()
	{
		super();
	}
	/**
	 * Instantiates a new  my own MyViewServerSide with given: BufferedReader in, PrintWriter out
	 * @param in BufferedReader represent the input source
	 * @param out PrintWriter represent the output source
	 */
	public MyViewServerSide(BufferedReader in, PrintWriter out)
	{
		super();
		this.in = in;
		this.out=out;
	}
	/**
	 * Instantiates a new  my own MyViewServerSide with given controller layer as instance
	 * @param controller Controller represent the controller layer as instance
	 */
	public MyViewServerSide(Controller controller)
	{
		super();
		this.controller = controller;
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
	}
	/**
	 * Instantiates a new  my own MyViewServerSide with given: controller layer as instance, BufferedReader in and PrintWriter out
	 * @param controller Controller represent the controller layer as instance
	 * @param in BufferedReader represent the input source
	 * @param out PrintWriter represent the output source
	 * @param viewCommandMap HashMap<String, Command> represent a commands to run
	 */
	public MyViewServerSide(Controller controller, BufferedReader in, PrintWriter out,HashMap<String, Command> viewCommandMap)
	{
		super();
		this.controller = controller;
		cli = new CLI(in, out,viewCommandMap);
	}
	/**
	 * Instantiates a new  my own MyViewServerSide with given controller layer as instance
	 * @param controller Controller represent the controller layer as instance
	 * @param in BufferedReader represent the input source
	 * @param out PrintWriter represent the output source
	 */
	public MyViewServerSide(Controller controller, BufferedReader in, PrintWriter out)
	{
		super();
		this.controller = controller;
		this.in = in;
		this.out = out;
	}
	/**
	* this method will set the controller to work with
	* @param controller Controller represent the controller layer to work with
	*/
	public void setController(Controller controller){this.controller = controller;}
	
	@Override
	/**
	* this method will start to run the view layer
	*/
	public void start() {
		cli.start();
		
	}


	/**
	* this method will print int[][] array
	*/
	public void printArr(int[][] arr)
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
	 * @return the cli
	 */
	public CLI getCli() {
		return cli;
	}
	/**
	 * @param cli the cli to set
	 */
	public void setCli(CLI cli) {
		this.cli = cli;
	}
	/**
	 * @return the viewCommandMap
	 */
	public HashMap<String, Command> getViewCommandMap() {
		return viewCommandMap;
	}

	/**
	 * @return the cliMenu
	 */
	public String getCliMenu() {
		return cliMenu;
	}
	/**
	 * @param cliMenu the cliMenu to set
	 */
	public void setCliMenu(String cliMenu) {
		this.cliMenu = cliMenu;
	}
	/**
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}
	/**
	 * @param in the in to set
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	/**
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	/**
	 * @return the controller
	 */
	public Controller getController() {
		return controller;
	}
	
	
}
