package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1>ClientHandler</h1>
 * ClientHandler implements Runnable.
 * The goal of this class is to being executed by thread for each client 
 */
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class ClientHandler implements Runnable {

	private ModelServerSide model; 
	private Socket client;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	/**
	 * Instantiate a new ClientHandler with given Client Socket client, ObjectInputStream input and ObjectOutputStream outpu
	 * @param client represent client socket
	 * @param input represent socket ObjectInputStream
	 * @param output represent socket ObjectOutputStream
	 */
	public ClientHandler(Socket client, ObjectInputStream input, ObjectOutputStream output) {
		this.client = client;
		this.input = input;
		this.output = output;
		model = new MyModelServerSide();
	}

	@Override
	/**
	 * This method override Runnable interface "Run()" method.
	 * The method read the problem from the socket and pass the problem to model layer for calculation.
	 * after the calculation the solution is being send and all resources are being closed
	 */
	public void run() {
		try
		{
			@SuppressWarnings("unchecked")
			ArrayList<Object> problem = (ArrayList<Object>) input.readObject();
			String mazeName = (String) problem.get(0); 
			String algorithm = (String) problem.get(1);
			Maze3d maze = (Maze3d) problem.get(2);
			Solution<Position> result = model.solveMaze(mazeName, algorithm, maze);
			output.writeObject(result);
			output.flush();
			this.input.close();
			this.output.close();
			this.client.close();
			try {
				finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
