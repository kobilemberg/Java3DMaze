package presenter;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> PropertiesServerSide </h1>
 * This class represent an instance of settings that the program will use.
 */
import java.io.Serializable;


public class PropertiesServerSide implements Serializable
{


	private static final long serialVersionUID = 1L;
	protected int numOfThreads;
	protected  int numOfClients;
	protected  String defaultSolver;
	protected int port;
	/**
	 * Instantiate a new PropertiesServerSide
	 */
	public PropertiesServerSide() {}
	/**
	 * Instantiate a new PropertiesServerSide with given parameters
	 * @param numOfThreads represent the maximum allowed thread to use.
	 * @param numOfClients represent the maximum allowed concurrent TCP connections
	 * @param defaultSolver represent default solving algorithm for problems
	 * @param port represent server port
	 */
	public PropertiesServerSide(int numOfThreads, int numOfClients, String defaultSolver, int port) {
		super();
		this.numOfThreads = numOfThreads;
		this.numOfClients = numOfClients;
		this.defaultSolver = defaultSolver;
		this.port = port;
	}
	/**
	 * @return maximum allowed thread to use.
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}
	/**
	 * This method will change the maximum allowed thread to use parameter
	 * @param numOfThreads represent the maximum allowed thread to use and set
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	/**
	 * @return maximum allowed concurrent TCP connections
	 */
	public int getNumOfClients() {
		return numOfClients;
	}
	/**
	 * This method will set the maximum allowed concurrent TCP connections
	 * @param numOfClients represent maximum allowed concurrent TCP connections
	 */
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
	/**
	 * @return default solving algorithm for problems
	 */
	public String getDefaultSolver() {
		return defaultSolver;
	}
	/**
	 * This method will set default solving algorithm for problems
	 * @param defaultSolver represent the default solving algorithm for problems
	 */
	public void setDefaultSolver(String defaultSolver) {
		this.defaultSolver = defaultSolver;
	}
	/**
	 * @return server TCP port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * This method will set server port
	 * @param port represent the new port for listening
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * {@inheritDoc}
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}