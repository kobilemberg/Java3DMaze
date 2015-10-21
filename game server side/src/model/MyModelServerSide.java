package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyModel </h1>
 * MyModel class implements Model interface, 
 * class goal is to act as MVC Model and perform all business logic calculations.
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
	public PropertiesServerSide getProperties() {return properties;}

	public void setProperties(PropertiesServerSide properties) {this.properties = properties;}
	
//Functionality
	@Override
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	@Override
	public void exit() {
		try {
			modelCompletedCommand =4;
			TP.shutdownNow();
			ObjectOutputStream mapSave = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File("External files/solutionMap.txt"))));
			mapSave.writeObject(this.solutionMap);
			mapSave.flush();
			mapSave.close();
		} catch (Exception e) {
			errorNoticeToController("ThreadPool exit error");
			e.printStackTrace();
		}	
	}	
	
	/**
	* Ihis method will notice to controller an error messege
	* @param s String represent the error to notice
	*/
	public void errorNoticeToController(String s)
	{
		modelCompletedCommand=-1;
		data = s;
		this.setChanged();
		notifyObservers();
	}
	
	@Override
	public Object getData() {return data;}
	
	@Override
	public void setData(Object o) {this.data = o;}
	
	public int getModelCompletedCommand(){return modelCompletedCommand;}
	
	public void setModelCommandCommand(int commandNum){modelCompletedCommand=commandNum;}
	
	public static PropertiesServerSide read(String filename) throws Exception {
        XMLDecoder decoder =new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        PropertiesServerSide properties = (PropertiesServerSide)decoder.readObject();
        decoder.close();
        return properties;
    }
	
	private Solution<Position> solveWithAstar(String mazeName,Demo d,Maze3d maze)
	{
		Solution<Position> solutionToAdd = d.solveSearchableMazeWithAstarByManhatenDistance(new SearchableMaze3d(maze));
		solutionMap.put(maze, solutionToAdd);
		return solutionMap.get(maze);
	}
	
	private Solution<Position> solveWithBFS(String mazeName,Demo d,Maze3d maze)
	{
		Solution<Position> solutionToAdd = d.solveSearchableMazeWithBFS(new SearchableMaze3d(maze));
		solutionMap.put(maze, solutionToAdd);
		return solutionMap.get(maze);
	}
	
	@Override
	public void initServer() {
		try {
			server.startServer(properties.getNumOfClients());
			//this.server.getServer().setSoTimeout(60000*60);
			modelCompletedCommand=1;
			setChanged();
			setData("Server is up");
			notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
