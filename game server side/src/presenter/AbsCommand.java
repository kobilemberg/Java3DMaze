package presenter;

import model.ModelServerSide;

public abstract class AbsCommand implements Command {
	
	
	protected ModelServerSide model;
	Controller controller;
	
	
	
	public AbsCommand(ModelServerSide model, Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
	}



	@Override
	public abstract void doCommand(String[] args);

}
