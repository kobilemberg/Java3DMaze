package model;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
				String line = (String) input.readObject();
				System.out.println(line.toString());
				output.writeObject("Got it.");
				if(line.toLowerCase().equals(SOLVE)){
					//executer.execute(new Thread(new ASCIIArtClientHandler(someClient)));
				}
			}
			server.close();
		} catch (Exception e) {
			System.out.println("ERROR: Connection timed out.");
		}finally {
			((ExecutorService)executer).shutdown();
		}		
	}
	public void stopServer(){
		serverIsRunning = false;
	}

	public static void main(String[] args) {
		MyTCPIPServer server = new MyTCPIPServer(12345);
		server.startServer(3);
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