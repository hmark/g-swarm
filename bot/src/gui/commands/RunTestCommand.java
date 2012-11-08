package gui.commands;

import gswarm.*;
//import pso.*;


public class RunTestCommand {

	public RunTestCommand(){
		new Thread(new GSwarm()).start();
	}
	
}
