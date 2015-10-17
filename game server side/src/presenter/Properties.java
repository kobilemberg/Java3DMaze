package presenter;

import java.io.Serializable;


public class Properties implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	protected int numOfThreads;
	protected  int numOfClients;
	protected  String defaultSolver;
	protected int port;
	
	public Properties() {}

	public Properties(int numOfThreads, int numOfClients, String defaultSolver, int port) {
		super();
		this.numOfThreads = numOfThreads;
		this.numOfClients = numOfClients;
		this.defaultSolver = defaultSolver;
		this.port = port;
	}

	public int getNumOfThreads() {
		return numOfThreads;
	}

	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	public int getNumOfClients() {
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	public String getDefaultSolver() {
		return defaultSolver;
	}

	public void setDefaultSolver(String defaultSolver) {
		this.defaultSolver = defaultSolver;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}