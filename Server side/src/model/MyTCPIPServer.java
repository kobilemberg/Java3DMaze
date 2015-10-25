package model;

import java.io.IOException;
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

	public void startServerNew(int maxClients){
		Running = true;
		executer = Executors.newFixedThreadPool(maxClients);
				try {
						server=new ServerSocket(port);
							while(Running)
							{
								if(!server.isClosed())
								{
									Socket someClient = server.accept();
									ObjectInputStream input = new ObjectInputStream(someClient.getInputStream());
									ObjectOutputStream output = new ObjectOutputStream(someClient.getOutputStream());
									String line = (String) input.readObject(); 
									if (line.equals(SOLVE))
									{
										executer.execute(new Thread(new ClientHandler(someClient,input,output)));
									}
								}
							}
							server.close();	
							((ExecutorService)executer).shutdownNow();
						}
					
						catch (Exception e) {
							
						}finally {((ExecutorService)executer).shutdownNow();}	
	}
	public void stopServer()
	{
		Running = false;
		try {
				((ExecutorService)executer).shutdownNow();
				if (server != null)
					if(!server.isClosed())
						server.close();
			} catch (Exception e) {
					//e.printStackTrace();
			}
	}

	public int getPort() {return port;}

	public void setPort(int port) {this.port = port;}

	public Executor getExecuter() {return executer;}

	public void setExecuter(Executor executer) {this.executer = executer;}

	public ServerSocket getServer() {return server;}

	public void setServer(ServerSocket server) {this.server = server;}
}