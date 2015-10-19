package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.demo.SearchableMaze3d;

public class MyTCPIPServer {

	private static final String SOLVE = "solve maze";
	private int port;
	private Executor executer;
	private ServerSocket server;
	private boolean Running = true;

	public MyTCPIPServer(int port){
		this.port = port;
	}

	public void startServer(int maxClients){
		executer = Executors.newFixedThreadPool(maxClients);
		try {
			/* Start the server. */
			server=new ServerSocket(this.port);
			
			/* Get Clients */ 
			while(Running){
				Socket someClient = server.accept();
				System.out.println("Accepted connection.");
				ObjectInputStream input = new ObjectInputStream(someClient.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(someClient.getOutputStream());
				String line = (String) input.readObject(); 
				if (line.equals(SOLVE))
				{
					System.out.println(line + "accepted.");
					executer.execute(new Thread(new ClientHandler(someClient,input,output)));
				}
				else
				{
					System.out.println("Command not understood. First String must be "+SOLVE);
				}

			}
			
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Problem establishing connection or Running Server.");
		}finally {
			((ExecutorService)executer).shutdown();
		}		
	}
	public void stopServer(){
		Running = false;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Executor getExecuter() {
		return executer;
	}

	public void setExecuter(Executor executer) {
		this.executer = executer;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}
}