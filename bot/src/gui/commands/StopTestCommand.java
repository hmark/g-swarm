package gui.commands;

import external.ThreadManager;

public class StopTestCommand {

	public StopTestCommand(){
		ThreadManager.stopThreads();
	}
}
