package model;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.demo.SearchableMaze3d;

public class MyTCPIPServer {

	private static final String SOLVE = "test";
	private int port;
	private Executor executer;
	private ServerSocket server;
	private boolean serverIsRunning = true;

	public MyTCPIPServer(int port){
		this.port = port;
		try {
			server=new ServerSocket(this.port);
			System.out.println("Server is up");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer(int numOfClients){
		executer = Executors.newFixedThreadPool(numOfClients);
		try {
			while(serverIsRunning){
				Socket someClient = server.accept();
				System.out.println("Accepted connection.");
				ObjectInputStream input=new ObjectInputStream(someClient.getInputStream());
				ObjectOutputStream output=new ObjectOutputStream(someClient.getOutputStream());
				SearchableMaze3d line = (SearchableMaze3d) input.readObject();
				System.out.println(line.toString());
				output.writeObject("Got it.");
				/*if(line.toLowerCase().equals(SOLVE)){
					//executer.execute(new Thread(new ASCIIArtClientHandler(someClient)));
				}*/
			}
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Connection timed out.");
		}finally {
			((ExecutorService)executer).shutdown();
		}		
	}
	public void stopServer(){
		serverIsRunning = false;
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
	
	public boolean isServerIsRunning() {
		return serverIsRunning;
	}

	public void setServerIsRunning(boolean serverIsRunning) {
		this.serverIsRunning = serverIsRunning;
	}
}