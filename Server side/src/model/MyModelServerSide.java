package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyModelServerSide </h1>
 * MyModelServerSide class implements Model interface, 
 * class goal is to act as MVP Model and perform all business logic calculations and the game server side.
 */
 

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import algorithms.demo.Demo;
import algorithms.demo.SearchableMaze3d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.PropertiesServerSide;

public class MyModelServerSide extends Observable implements ModelServerSide{
	int numberOfClients = 0; 
	Object data;
	int modelCompletedCommand=0;
	ExecutorService TP ;
	HashMap<Maze3d, Solution<Position>> solutionMap = new HashMap<Maze3d, Solution<Position>>();
	HashMap<String, Thread> openThreads = new HashMap<String,Thread>();
	PropertiesServerSide properties;
	MyTCPIPServer server;
	//Constructors
	/**
	* Instantiates a new  my own model.
	*/
	@SuppressWarnings("unchecked")
	/**
	 * Instantiate a new MyModelServerSide
	 * @return new MyModelServerSide as instance represent the MyModelServerSide
	 */
	public MyModelServerSide()
	{
		super();

		/* Initialize Variables */ 
		ObjectInputStream mapLoader;
		try {
			this.properties = read("External files/Properties.xml");
			this.TP = Executors.newFixedThreadPool(properties.getNumOfThreads());
			server = new MyTCPIPServer(properties.getPort());
			File map = new File("External files/solutionMap.txt");
			if(map.exists())
			{
				mapLoader = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File("External files/solutionMap.txt"))));
				solutionMap = (HashMap<Maze3d, Solution<Position>>) mapLoader.readObject();
				mapLoader.close();
			}
			else
				solutionMap = new HashMap<Maze3d, Solution<Position>>();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
	
//Getters and setters
	/**
	 * This method will return instance represent additional properties (number of threads... ).
	 * @return PropertiesServerSide represent additional properties.
	 */
	public PropertiesServerSide getProperties() {return properties;}
	/**
	 * This method will set instance represent additional properties (number of threads... ).
	 * @param properties as instance represent additional properties (number of threads... ).
	 */
	public void setProperties(PropertiesServerSide properties) {this.properties = properties;}
	
//Functionality
	@Override
	/**
	 * {@inheritDoc}
	 */
	public Solution<Position> solveMaze(String mazeName, String algorithm, Maze3d maze) 
	{

		if(solutionMap.containsKey(maze))
		{
			errorNoticeToController("Model notification: I have this solution, i won't calculate it again!");
			return solutionMap.get(maze);
		}
		else
		{
			Future<Solution<Position>> f = TP.submit(new Callable<Solution<Position>>() 
			{
				@Override
				public Solution<Position> call() throws Exception 
				{
					Demo d = new Demo();
					if(algorithm.equals("bfs")||algorithm.equals("BFS"))
					{
						errorNoticeToController("Solving with BFS as your request.");
						return solveWithBFS(mazeName, d, maze);	
					}
					else
					{
						errorNoticeToController("Solving with A* as your request.");
						return solveWithAstar(mazeName, d, maze);
					}
				}
			});
			
			try {
				Solution<Position> result = f.get();
				result = f.get();
				return result;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			throw new NullPointerException();
		}
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void exit() {
		try {
			server.stopServer();
			TP.shutdownNow();
			ObjectOutputStream mapSave = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File("External files/solutionMap.txt"))));
			mapSave.writeObject(this.solutionMap);
			mapSave.flush();
			mapSave.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	
	
	/**
	* This method will notice to controller an error messege
	* @param s String represent the error to notice
	*/
	public void errorNoticeToController(String s)
	{
		modelCompletedCommand=-2;
		data = s;
		this.setChanged();
		notifyObservers();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public Object getData() {return data;}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setData(Object o) {this.data = o;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getModelCompletedCommand(){return modelCompletedCommand;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setModelCommandCommand(int commandNum){modelCompletedCommand=commandNum;}
	/**
	 * This static method reads the properties XML file and load the configuratuin
	 * @param filename represent the new configuration XML
	 */
	public static PropertiesServerSide read(String filename) throws Exception {
        XMLDecoder decoder =new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        PropertiesServerSide properties = (PropertiesServerSide)decoder.readObject();
        decoder.close();
        return properties;
    }
	/**
	 * This method will solve a specific maze and will solve the problem via Astar algorithm
	 * @param mazeName represent the name of the maze
	 * @param d represent the solver
	 * @param maze represent the maze as Object
	 * @return Solution<Position> represent the solution for the maxe
	 */
	private Solution<Position> solveWithAstar(String mazeName,Demo d,Maze3d maze)
	{
		Solution<Position> solutionToAdd = d.solveSearchableMazeWithAstarByManhatenDistance(new SearchableMaze3d(maze));
		solutionMap.put(maze, solutionToAdd);
		return solutionMap.get(maze);
	}
	/**
	 * This method will solve a specific maze and will solve the problem via BFS algorithm
	 * @param mazeName represent the name of the maze
	 * @param d represent the solver
	 * @param maze represent the maze as Object
	 * @return Solution<Position> represent the solution for the maxe
	 */
	private Solution<Position> solveWithBFS(String mazeName,Demo d,Maze3d maze)
	{
		Solution<Position> solutionToAdd = d.solveSearchableMazeWithBFS(new SearchableMaze3d(maze));
		solutionMap.put(maze, solutionToAdd);
		return solutionMap.get(maze);
	}


	@Override
	/**
	 * {@inheritDoc}
	 */
	public void initServer() {
		data = "On";
		TP = Executors.newFixedThreadPool(properties.getNumOfThreads());
		modelCompletedCommand = 1;
		setChanged();
		TP.execute(new Thread(new Runnable() {
			@Override
			public void run() {
				server.startServerNew(properties.getNumOfClients());
			}
		}, "Server Socket"));
		notifyObservers();
	}


	@Override
	/**
	 * {@inheritDoc}
	 */
	public void stopServer() {
		server.stopServer();
		TP.shutdownNow();
		data = "Off";
		modelCompletedCommand = 2;
		setChanged();
		notifyObservers();		
	}


	@Override
	/**
	 * {@inheritDoc}
	 */	public void setNumberOfClients(int num) {
		this.numberOfClients = num; 
		data = num + " Clients.";
		modelCompletedCommand = 3;
		setChanged();
		notifyObservers();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */	public int getNumberOfClients() {return this.numberOfClients;}

	@Override
	/**
	 * {@inheritDoc}
	 */
		public void changeSettings(String portNumber, String maxClients) {
		this.properties.setNumOfClients(Integer.parseInt(maxClients));
		this.properties.setPort(Integer.parseInt(portNumber));
		data = portNumber;
		modelCompletedCommand = 4;
		setChanged();
		notifyObservers();
	}
}
