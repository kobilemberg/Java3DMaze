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

	private static final String GET_IMAGE = "get image";
	private int port;
	private Executor executer;
	private ServerSocket server;
	private boolean killServer = true;
	public MyTCPIPServer(int port){
		this.port = port;
		try {
			server=new ServerSocket(this.port);
			System.out.println("Server is up");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startServer(int numOfClients){
		executer = Executors.newFixedThreadPool(numOfClients);
		try {
			
			while(killServer){
				Socket someClient = server.accept();
				ObjectInputStream input=new ObjectInputStream(someClient.getInputStream());
				ObjectOutputStream output=new ObjectOutputStream(someClient.getOutputStream());
				String line = (String) input.readObject();
				if(line.equals(GET_IMAGE)){
					//executer.execute(new Thread(new ASCIIArtClientHandler(someClient)));
				}
			}
			server.close();
		} catch (Exception e) {
			System.out.println("tiered of waiting for connection");
		}finally {
			((ExecutorService)executer).shutdown();
		}		
	}
	public void stopServer(){
		killServer = false;
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

	public boolean isKillServer() {
		return killServer;
	}

	public void setKillServer(boolean killServer) {
		this.killServer = killServer;
	}

	public static String getGetImage() {
		return GET_IMAGE;
	}
	
	
	
	
}