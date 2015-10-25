package model;

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
	
	public ClientHandler(Socket client, ObjectInputStream input, ObjectOutputStream output) {
		this.client = client;
		this.input = input;
		this.output = output;
		model = new MyModelServerSide();
	}

	@Override
	public void run() {
		try
		{
			@SuppressWarnings("unchecked")
			ArrayList<Object> problem = (ArrayList<Object>) input.readObject();
		//	model.setNumberOfClients(model.getNumberOfClients()+1);
			for(Object o:problem){
				System.out.println(o.toString());
			}
			String mazeName 	= (String) problem.get(0); 
			String algorithm 	= (String) problem.get(1);
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
			//System.out.println("Send to Client Solution: " + result.toString());
			model.setNumberOfClients(model.getNumberOfClients()-1);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
