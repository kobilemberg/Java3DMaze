package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyModel </h1>
 * MyModel class implements Model interface, 
 * class goal is to act as MVC Model and perform all business logic calculations.
 */
 
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.demo.Demo;
import algorithms.demo.SearchableMaze3d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.Solution;
import presenter.PropertiesServerSide;


public class MyModelServerSide extends Observable implements ModelServerSide{
	
	Object data;
	int modelCompletedCommand=0;
	ExecutorService TP ;
	Map<String, Maze3d> maze3dMap = Collections.synchronizedMap(new HashMap<String, Maze3d>());
	HashMap<Maze3d, Solution<Position>> solutionMap = new HashMap<Maze3d, Solution<Position>>();
	HashMap<String, Thread> openThreads = new HashMap<String,Thread>();
	PropertiesServerSide properties;
	MyTCPIPServer server;
	//Constructors
	/**
	* Instantiates a new  my own model.
	*/
	@SuppressWarnings("unchecked")
	public MyModelServerSide(PropertiesServerSide p)
	{
		super();
		this.properties = p; 
		this.TP = Executors.newFixedThreadPool(p.getNumOfThreads());
		server = new MyTCPIPServer(p.getPort());
		File map = new File("External files/solutionMap.txt");
		if(map.exists())
		{
			ObjectInputStream mapLoader;
			try {
				mapLoader = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File("External files/solutionMap.txt"))));
				solutionMap = (HashMap<Maze3d, Solution<Position>>) mapLoader.readObject();
				mapLoader.close();
				this.properties = read("External files/Properties.xml");
			} 
			catch (FileNotFoundException e) {errorNoticeToController("Problam with solution map file");} 
			catch (IOException e) 
			{
				errorNoticeToController("Problam with solution map file(IO)");
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {errorNoticeToController("problam with class");} 
			catch (Exception e) 
			{
				errorNoticeToController("Problem with xml");
				e.printStackTrace();
			}
		}
		else
			solutionMap = new HashMap<Maze3d, Solution<Position>>();
	}
	/**
	* Instantiates a new  my own model with given controller
	* @param controller Controller represent the controller layer to work with
	* @return new MyModel as instance
	*/
	
	
//Getters and setters
	/**
	* this method will set the controller to work with
	* @param controller Controller represent the controller layer to work with
	*/

	public PropertiesServerSide getProperties() {return properties;}


	public void setProperties(PropertiesServerSide properties) {this.properties = properties;}
	
//Functionality
	
	@Override
	public void solveMaze(String mazeName, String algorithm) 
	{
		if(maze3dMap.containsKey(mazeName))
		{
			if(solutionMap.containsKey(maze3dMap.get(mazeName)))
			{
				errorNoticeToController("Model notification: I have this solution, i won't calculate it again!");
				modelCompletedCommand=2;
				setData(mazeName);
				setChanged();
				notifyObservers();
			}
			else
			{
				Callable<Solution<Position>> mazeSolver =new Callable<Solution<Position>>() 
				{
					@Override
					public Solution<Position> call() throws Exception 
					{
						if(maze3dMap.containsKey(mazeName))
						{
							Demo d = new Demo();
							SearchableMaze3d searchableMaze = new SearchableMaze3d(maze3dMap.get(mazeName));
							if(algorithm.equals("bfs"))
							{
								errorNoticeToController("Solving with BFS as your request.");
								solveWithBFS(mazeName, d, searchableMaze);	
							}
							else if(algorithm.equals("a*"))
							{
								errorNoticeToController("Solving with A* as your request.");
								solveWithAstar(mazeName, d, searchableMaze);
							}
							
							else if (properties.getDefaultSolver().equals("A*"))
							{
								errorNoticeToController("Solving with A* as your properties file.");
								solveWithAstar(mazeName, d, searchableMaze);
							}
							else if (properties.getDefaultSolver().equals("BFS"))
							{
								errorNoticeToController("Solving with BFS as your properties file.");
								solveWithBFS(mazeName, d, searchableMaze);
							}
							else 
							{
								errorNoticeToController("Solving with A*.");
								solveWithAstar(mazeName, d, searchableMaze);
							}
						}
						return null;
					}
				};
				TP.submit(mazeSolver);
			}
		}
		else{errorNoticeToController("There is no maze named: "+mazeName);}
	}
			
	@Override
	public void getSolutionOfMaze(String mazeName) {
		
		if(maze3dMap.containsKey(mazeName))
		{
			if(solutionMap.containsKey(maze3dMap.get(mazeName)))
			{
				modelCompletedCommand = 3;
				Object[] dataToSet = new Object[2];
				dataToSet[0] = mazeName;
				dataToSet[1] = solutionMap.get(maze3dMap.get(mazeName));
				setChanged();
				this.data = dataToSet;
				notifyObservers();
			}
			else{errorNoticeToController("maze wasn't solve!");}
		}
		else{errorNoticeToController("maze wasn't solve!");}
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
	public boolean isLoaded(String mazeName) {return maze3dMap.containsKey(mazeName);}
	
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
	
	private Solution<Position> solveWithAstar(String mazeName,Demo d,SearchableMaze3d searchableMaze)
	{
		Solution<Position> solutionToAdd = d.solveSearchableMazeWithAstarByManhatenDistance(searchableMaze);
		solutionMap.put(maze3dMap.get(mazeName), solutionToAdd);
		modelCompletedCommand=2;
		setChanged();
		setData(mazeName);
		notifyObservers();
		return solutionMap.get(maze3dMap.get(mazeName));
	}
	
	
	private Solution<Position> solveWithBFS(String mazeName,Demo d,SearchableMaze3d searchableMaze)
	{
		Solution<Position> solutionToAdd = d.solveSearchableMazeWithBFS(searchableMaze);
		solutionMap.put(maze3dMap.get(mazeName), solutionToAdd);
		modelCompletedCommand=2;
		setChanged();
		setData(mazeName);
		notifyObservers();
		return solutionMap.get(maze3dMap.get(mazeName));
	}
	
	@SuppressWarnings("resource")
	@Override
	public boolean changePropertiesByFilename(String fileName) 
	{	 
		//Reading the file.
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
		} catch (FileNotFoundException e) {
			errorNoticeToController("ERROR: File " + fileName + " was not found.");
			return false; 
		}
		//Loading the file object
		properties=(PropertiesServerSide)decoder.readObject();
		setData(properties);
		
		System.out.println(properties);
		//Saving the new file to the file root directory
		XMLEncoder encoder=null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("External files/properties.xml")));
		} catch (FileNotFoundException e) {
			errorNoticeToController("ERROR: Writing file External files/properties.xml failed.");
			return false;
		}
		encoder.writeObject(properties);
		encoder.close();
		return true;
	}
	
	public Solution<Position> requestSolution(String mazeName,Maze3d maze, String ip, int port) throws Exception
	{
		InetAddress localaddr;
		try {
			InetAddress.getAllByName(ip);
			port = 12345;
			localaddr = InetAddress.getLocalHost();
			System.out.println("The ip of the server is: "+localaddr.getHostAddress());
			Socket myServerSocket = new Socket(localaddr.getHostAddress(), port);
			
			//Streams
			ObjectOutputStream output=new ObjectOutputStream(myServerSocket.getOutputStream());
			ObjectInputStream input=new ObjectInputStream(myServerSocket.getInputStream());
			
			String solveCommand = "Solve "+mazeName+" "+properties.getDefaultSolver();
			output.writeObject(solveCommand);
			output.flush();
			
			@SuppressWarnings("unchecked")
			Solution<Position> solution = (Solution<Position>)input.readObject();
			
			output.close();
			input.close();
			myServerSocket.close();
			
			if(solution.toString().contains("Solution:"))
			{
				return solution;
			}
			//System.out.println("message from server: "+messageFromServer);
			//output.writeObject("networking is so simple in java");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void initServer(String numOfClients) {
		
		try {
			this.server.getServer().setSoTimeout(60000*60);
			modelCompletedCommand=1;
			setChanged();
			setData("Server is up");
			notifyObservers();
		} catch (SocketException e) {
			e.printStackTrace();
		}	
	}
}
