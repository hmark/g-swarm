package gui.commands;

import external.ThreadManager;
import pso.Setup;
import gswarm.*;
//import pso.*;
import gui.Window;


public class RunTestCommand {

	public RunTestCommand(){
		Window window = Window.getInstance();
		
		Setup.PARTICLES = window.getParticlesNum();
		Setup.ITERATIONS = window.getIterationsNum();
		Setup.DIMENSIONS = window.getDimensionsNum();
		Setup.XMAX = window.getXMaxNum();
		Setup.SPEED_MIN = window.getSpeedMinNum();
		Setup.SPEED_MAX = window.getSpeedMaxNum();
		Setup.C1 = window.getC1Num();
		Setup.C2 = window.getC2Num();
		Setup.WMIN = window.getMinWeightNum();
		Setup.WMAX = window.getMaxWeightNum();
		
		System.out.println("Setup.PARTICLES: " + Setup.PARTICLES);
		System.out.println("Setup.ITERATIONS: " + Setup.ITERATIONS);
		System.out.println("Setup.DIMENSIONS: " + Setup.DIMENSIONS);
		System.out.println("Setup.XMAX: " + Setup.XMAX);
		System.out.println("Setup.SPEED_MIN: " + Setup.SPEED_MIN);
		System.out.println("Setup.SPEED_MAX: " + Setup.SPEED_MAX);
		System.out.println("Setup.C1: " + Setup.C1);
		System.out.println("Setup.C2: " + Setup.C2);
		System.out.println("Setup.WMIN: " + Setup.WMIN);
		System.out.println("Setup.WMAX: " + Setup.WMAX);
		
		
		Thread t = new Thread(new GSwarm());
		//Thread t = new Thread(new SymbolicRegressionGSwarm());
		//Thread t = new Thread(new ParallelGSwarm());
		ThreadManager.addThread(t);
		t.start();
	}
	
}
