package view;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;

public class ServerBasicWindow extends BasicWindow implements ViewServerSide{
	int userCommand = 0; 
	boolean serverIsOn = false; 
	Label serverStatus;
	Label serverAddress;
	Label numOfClients; 
	Button startStopButton;
	Text portNumberText;
	Combo maximumClients;
	               
	public ServerBasicWindow(String title, int width, int height) {
		super(title, width, height);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	void initWidgets(){
		shell.setLayout(new GridLayout(2, false));
		TabFolder folder = new TabFolder(shell, SWT.NULL); 
		TabItem serverTab = new TabItem(folder, SWT.NULL);
		serverTab.setText("Control Panel");
		Composite serverForm = new Composite(folder, SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.justify = false;
		rowLayout.pack = true;
		rowLayout.type = SWT.VERTICAL;
		serverForm.setLayout(rowLayout);
		serverTab.setControl(serverForm);
		String address;
		try {
			address = InetAddress.getLocalHost().getHostAddress().toString();
			address += ":12345";
		} catch (UnknownHostException e) {
			address = "Could not resolve IP.";
		} 
		
		serverAddress = createLabel(serverForm, SWT.NULL, address);
		serverStatus = createLabel(serverForm, SWT.NULL , "Status: Off");
		numOfClients = createLabel(serverForm, SWT.NULL , "\u00A9 Kobi and Alon");
		startStopButton = createButton(serverForm, "Start Server", "Resources/Menu/power.png",160,30);
		
		TabItem propertiesTab = new TabItem(folder, SWT.NULL);
		propertiesTab.setText("Properties");
		Composite propertiesForm = new Composite(folder, SWT.NONE);
		propertiesForm.setLayout(rowLayout);
		propertiesTab.setControl(propertiesForm);
		createLabel(propertiesForm, SWT.NONE, "Server Port:", 110, 15);
		portNumberText = createText(propertiesForm, SWT.SINGLE | SWT.BORDER, "12345", 147, 15);
		createLabel(propertiesForm, SWT.NONE, "Max Clients:", 110, 15);
		String[] maxClients = {"3", "4", "5", "6", "7", "8", "9", "10"};
		maximumClients = createCombo(propertiesForm, SWT.NULL, maxClients, "8",132,15);
		createLabel(propertiesForm, SWT.NONE, "", 110, 10);
		Button submitButton = createButton(propertiesForm, " Update    ", "Resources/Menu/save.png",160,30);
		
		/* What happens when a user clicks "[Start/Stop Server]". */ 
		startStopButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (serverIsOn){
					setUserCommand(2);
					String[] params = {};
					notifyObservers(params);
				}
				else
				{
					setUserCommand(1);
					String[] params = {};
					notifyObservers(params);
				}
				
				//serverStatus.setText("Status: On.");
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		/* What happens when a user clicks "[submit]". */ 
		submitButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String portNumber = portNumberText.getText();
				String maxClients = maximumClients.getText();
				String[] args = {portNumber, maxClients}; 
				setUserCommand(4);
				notifyObservers(args);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		shell.layout();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void start() {
		this.run();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void tellTheUserSolutionIsReady(String mazeName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void printSolutionToUser(String mazeName, Solution<Position> solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setCommands(HashMap<String, Command> viewCommandMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setCommandsMenu(String cliMenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void exit() {
		setUserCommand(-1);
		String[] params = {};
		notifyObservers(params);
		display.dispose();
		System.exit(0);
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void errorNoticeToUser(String s) {
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	MessageBox messageBox = new MessageBox(new Shell(display),SWT.ICON_INFORMATION|SWT.OK);
				messageBox.setMessage(s);
				messageBox.setText("Notification");
				messageBox.open();
		    }
		});		
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getUserCommand() {
		return this.userCommand;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setUserCommand(int commandID) {
		this.setChanged();
		this.userCommand = commandID;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void displayData(Object data) {
		
		try {
			if (data.equals("Off"))
			{
				toggleServerStatus(false);
			}
			else if (data.equals("On")){
				toggleServerStatus(true);
			}
			else if (data.toString().toLowerCase().endsWith(" Clients.")){
				//numOfClients.setText(data.toString());
			}
			else
			{
				String address;
				try {
					address = InetAddress.getLocalHost().getHostAddress().toString();
					address += ":"+data;
				} catch (UnknownHostException e) {
					address = "Could not resolve IP:"+data;
				} 
				serverAddress.setText(address);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HashMap<String, Command> getViewCommandMap() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * This method create a button with the following parameters
	 * @param parent represent the Composite to being added to
	 * @param text represent the text value of thr button
	 * @param image represent the image that will be added to button
	 * @return button
	 */
	private Button createButton(Composite parent, String text, String image) {
	    Button button = new Button(parent, SWT.PUSH);
	    button.setImage(new Image(Display.getCurrent(), image));
	    button.setLayoutData(new RowData(120, 30));
	    button.setText(text);	    
	    return button;
	}
	/**
	 * This method create a button with the following parameters
	 * @param parent represent the Composite to being added to 
	 * @param text represent the text value of thr button
	 * @param image image represent the image that will be added to button
	 * @param width represent the width
	 * @param height represent the height
	 * @return button
	 */
	private Button createButton(Composite parent, String text, String image, int width, int height) {
		Button button = createButton(parent, text, image);
    	button.setLayoutData(new RowData(width, height));
    	return button; 		
	}
	
	@SuppressWarnings("unused")
	/**
	 * Thie method will change server XML settings
	 * @param server represent the server address as string
	 * @param port represent server port
	 * @param generator represent the maze generation algorithm 
	 * @param solver represent the solve algorithm
	 */
	private void changeSettings(String server,String port,String generator,String solver)
	{
		String[] args = {server,port,generator,solver};
		setUserCommand(14);
		notifyObservers(args);
	}
	/**
	 * This method will create label with\by the following values
	 * @param parent represent the composite to be added to
	 * @param style represent the style
	 * @param placeholder represent the text value of the label
	 * @param width represent label width
	 * @param height represent label height
	 * @return label
	 */
	private Label createLabel(Composite parent, int style, String placeholder, int width, int height){
		Label label = new Label(parent, style);
		label.setLayoutData(new RowData(width, height));
		label.setText(placeholder);
		return label; 		
	}
	
	/**
	 * This method will create label with\by the following values
	 * @param parent represent the composite to be added to
	 * @param style represent the style
	 * @param placeholder represent the text value of the label
	 * @return label
	 */
	private Label createLabel(Composite parent, int style, String placeholder){
		return createLabel(parent, style, placeholder, 120, 30); 		
	}
	/**
	 * This method will create text box with\by the following values
	 * @param parent represent the composite to be added to
	 * @param style represent the style
	 * @param placeholder represent the text value of the label
	 * @param width represent Text width
	 * @param height represent Text height
	 * @return the text box
	 */
	private Text createText(Composite parent, int style, String placeholder, int width, int height){
		Text text = new Text(parent, style);
	    text.setText(placeholder);
	    text.setLayoutData(new RowData(width, height));
	    return text; 
	}
	
	@SuppressWarnings("unused")
	/**
	 * This method will create text box with\by the following values
	 * @param parent represent the composite to be added to
	 * @param style represent the style
	 * @param placeholder represent the text value of the label
	 * @return the text box
	 */
	private Text createText(Composite parent, int style, String placeholder){
		return createText(parent, style, placeholder, 120, 15); 
	}
	/**
	 * This method will create combo box with\by the following values
	 * @param parent represent the parent to be added to
	 * @param style represent the style
	 * @param options represent the String[] of strings to put at the combo
	 * @param placeholder represent the first value of the combo
	 * @param width represent the combo width
	 * @param height represent the combo height
	 * @return combo 
	 */
	private Combo createCombo(Composite parent, int style, String[] options, String placeholder, int width, int height){
		Combo combo = new Combo(parent, style);
		for (int i = 0; i < options.length; i++) {
			combo.add(options[i]);
		}
		combo.setText(placeholder);
		combo.setLayoutData(new RowData(width, height));
		return combo;
	}
	
	@SuppressWarnings("unused")
	/**
	 * This method will create combo box with\by the following values
	 * @param parent represent the parent to be added to
	 * @param style represent the style
	 * @param options represent the String[] of strings to put at the combo
	 * @param placeholder represent the first value of the combo
	 * @return combo 
	 */
	private Combo createCombo(Composite parent, int style, String[] options, String placeholder){
		return createCombo(parent, style, options, placeholder, 90, 20);
	}
	/**
	 * Thie method changes the server button
	 * @param status boolean represent the server status to set
	 */
	private void toggleServerStatus(boolean status){
		if (!status){

			serverIsOn = false; 
			serverStatus.setText("Status: Off");
			startStopButton.setText("Start Server");
		}
		else
		{
			serverIsOn = true; 
			serverStatus.setText("Status: On");
			startStopButton.setText("Stop Server");
		}
		
	}
}
