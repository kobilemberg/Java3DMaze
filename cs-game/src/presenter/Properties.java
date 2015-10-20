package presenter;

import java.io.Serializable;


public class Properties implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String defaultAlgorithm;
	protected String defaultSolver;
	protected String UI;
	protected String serverAddress;
	protected int port; 
	protected int numOfThreads;
	
	public Properties() {}

	public Properties(String defaultAlgorithm, String defaultSolver, String UI, String serverAddress, int port, int numOfThreads) {
		super();
		this.defaultAlgorithm = defaultAlgorithm;
		this.defaultSolver = defaultSolver;
		this.UI = UI;
		this.serverAddress = serverAddress;
		this.port = port;
		this.numOfThreads = numOfThreads;
	}

	public String getDefaultAlgorithm() {
		return defaultAlgorithm;
	}

	public void setDefaultAlgorithm(String defaultAlgorithm) {
		this.defaultAlgorithm = defaultAlgorithm;
	}

	/**
	 * @return the numOfThreads
	 */
	public int getNumOfThreads() {return numOfThreads;}

	/**
	 * @param numOfThreads the numOfThreads to set
	 */
	public void setNumOfThreads(int numOfThreads) {this.numOfThreads = numOfThreads;}

	/**
	 * @return the defaultSolver
	 */
	public String getDefaultSolver() {return defaultSolver;}

	/**
	 * @param defaultSolver the defaultSolver to set
	 */
	public void setDefaultSolver(String defaultSolver) {this.defaultSolver = defaultSolver;}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {return serialVersionUID;}

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
	public boolean equals(Object obj) {return this.toString().equals(obj.toString());}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {return "Properties [numOfThreads=" + numOfThreads + ", defaultAlgorith=" + defaultAlgorithm + ", defaultSolver="+ defaultSolver + ", UI=" + UI + "]";}

}
