package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyTCPIPServer </h1>
 * This class represent a small TCP server
 * When new client request, it is being done via thread
 */
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Instantiate a new MyTCPIPServer according to Reasources/properties.XML file
 */
public class MyTCPIPServer {

	private static final String SOLVE = "solve maze";
	private int port;
	private Executor executer;
	private ServerSocket server;
	private boolean Running = true;
	public MyTCPIPServer(int port){
		this.port = port;
	}
	/**
	 * This method starts the main server listener socket
	 * @param maxClients represent the maximum allowed concurrent connections.
	 */
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
	/**
	 * This method will stop server from keep listening and will shutdown the server ThreadPool
	 */
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
	/**
	 * This method will return integer represent the current listening port
	 * @return integer represent the current listening port
	 */
	public int getPort() {return port;}
	/**
	 * This method will set integer represent the current listening port
	 * @param port represent the current listening port to set
	 */
	public void setPort(int port) {this.port = port;}
	/**
	 * This method will return server TP in order to being able to control it from outside
	 * @return Executor represent server TP in order to being able to control it from outside
	 */
	public Executor getExecuter() {return executer;}
	/**
	 * This method will set server TP in order to being able to control it from outside
	 * @param Executor represent server client executer
	 */
	public void setExecuter(Executor executer) {this.executer = executer;}
	/**
	 * @return Main listening socket
	 */
	public ServerSocket getServer() {return server;}
	/**
	 * This method will set the server socket
	 * @param server ServerSocket, represent the TCPIPSERVER
	 */
	public void setServer(ServerSocket server) {this.server = server;}
}