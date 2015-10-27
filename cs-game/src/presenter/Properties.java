package presenter;

/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> Properties </h1>
 * This class represent an instance of settings that the program will use.
 */

import java.io.Serializable;


public class Properties implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected String defaultAlgorithm;
	protected String defaultSolver;
	protected String UI;
	protected String serverAddress;
	protected int port; 
	protected int numOfThreads;
	/**
	 * Instantiate a new Properties with empty settings
	 */
	public Properties() {}
	/**
	 * Instantiate a new Properties with the following args
	 * @param defaultAlgorithm represent the maze generation algorithm (SimpleMaze3dGenerator\MyMaze3dGenerator)
	 * @param defaultSolver represent the solver algorithm (A*\\BFS)
	 * @param UI represent the user interface (CLI\GUI)
	 * @param serverAddress represent thr server Pv4 address 
	 * @param port represent thr server port
	 * @param numOfThreads represent the allowed threads to execute
	 */
	public Properties(String defaultAlgorithm, String defaultSolver, String UI, String serverAddress, int port, int numOfThreads) {
		super();
		this.defaultAlgorithm = defaultAlgorithm;
		this.defaultSolver = defaultSolver;
		this.UI = UI;
		this.serverAddress = serverAddress;
		this.port = port;
		this.numOfThreads = numOfThreads;
	}
	/**
	 * @return String represent the generation algorithm
	 */
	public String getDefaultAlgorithm() {
		return defaultAlgorithm;
	}
	/**
	 * This method will set the default of the maze generation
	 * @param defaultAlgorithm represent the generation algorithm (SimpleMaze3dGenerator\MyMaze3dGenerator)
	 */
	public void setDefaultAlgorithm(String defaultAlgorithm) {
		this.defaultAlgorithm = defaultAlgorithm;
	}

	/**
	 * @return the numOfThreads
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}

	/**
	 * @param numOfThreads the numOfThreads to set
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	/**
	 * @return the defaultSolver
	 */
	public String getDefaultSolver() {
		return defaultSolver;
	}

	/**
	 * @param defaultSolver the defaultSolver to set
	 */
	public void setDefaultSolver(String defaultSolver) {
		this.defaultSolver = defaultSolver;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 * @return Server Address 
	 */
	public String getServerAddress() {
		return serverAddress;
	}

	/** 
	 * Set Server Address 
	 * @param serverAddress
	 */
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	/**
	 * Get Port 
	 * @return
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Set Port
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}


	public String getUI() {
		return UI;
	}

	public void setUI(String uI) {
		UI = uI;
	} 
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}

	@Override
	public String toString() {
		return "Properties [defaultAlgorithm=" + defaultAlgorithm + ", defaultSolver=" + defaultSolver + ", UI=" + UI
				+ ", serverAddress=" + serverAddress + ", port=" + port + ", numOfThreads=" + numOfThreads + "]";
	}


	

}
