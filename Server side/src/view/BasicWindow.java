package view;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> BasicWindow </h1>
 * This class represent an instance of BasicWindow
 * The class implements Observable in order to participate at the game MVP infrastructure
 * The class implements Runnable in order to run in a diffrent thread
 */
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public abstract class BasicWindow extends Observable implements Runnable {
	
	Display display;
	Shell shell;
	/**
	 * Instantiate a new BasicWindow with given title, width and height 
	 * @param title represent the title for the shell
	 * @param width represent the width of the shell
	 * @param height represent the height of the shell
	 */
 	public BasicWindow(String title, int width,int height) {
 		display=new Display();
 		int trim = SWT.TITLE | SWT.CLOSE | SWT.BORDER;
 		shell  = new Shell(display, trim);
 		shell.setSize(width,height);
 		shell.setText(title);
 		
	}
 	
 	abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		while(!shell.isDisposed()){ // while window isn't closed
			// 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }
		} // shell is disposed
		exit();
	}
	
	public void exit(){
		display.dispose(); // dispose OS components
		
	}

}
