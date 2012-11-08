package gui.commands;

import external.ThreadManager;
import gswarm.GSwarm;

public class StopTestCommand {

	public StopTestCommand(){
		ThreadManager.stopThreads();
	}
}
