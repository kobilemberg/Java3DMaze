

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
	}

	public void startServer(int numOfClients){
		executer = Executors.newFixedThreadPool(numOfClients);
		try {
			server=new ServerSocket(this.port);
			while(killServer){
				Socket someClient = server.accept();
				ObjectInputStream input=new ObjectInputStream(someClient.getInputStream());
				ObjectOutputStream output=new ObjectOutputStream(someClient.getOutputStream());
				String line = (String) input.readObject();
				if(line.equals(GET_IMAGE)){
					executer.execute(new Thread(new ASCIIArtClientHandler(someClient)));
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
}