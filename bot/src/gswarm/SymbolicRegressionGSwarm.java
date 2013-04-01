package gswarm;

import gui.Window;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pso.Particle;
import pso.Setup;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class SymbolicRegressionGSwarm extends GSwarm {
	
	ScriptEngineManager mgr;
    ScriptEngine engine;
    
    Map<Double, Double> fitnessMap;

	public SymbolicRegressionGSwarm(){
		super();
	}
	
	protected void init(){
		mgr = new ScriptEngineManager();
		engine = mgr.getEngineByName("JavaScript");
		
		fitnessMap = new HashMap<Double, Double>();
		fitnessMap.put(0.001, 1000.0);
		fitnessMap.put(0.01, 100.0);
		fitnessMap.put(0.1, 10.0);
		fitnessMap.put(1.0, 1.0);
		fitnessMap.put(10.0, 0.1);
		fitnessMap.put(100.0, 0.01);
		fitnessMap.put(1000.0, 0.001);
		
		loadTemplate("templates/bot_regression.tmpl");
	}
	
	protected void testRobots(){
	}
	
	protected void calculateFitness(){
		Particle particle;
		double result, fitness;
		String formula;
		
		for (int j = 0; j < Setup.PARTICLES; j++){
			particle = _swarm.getParticleAt(j);
			if (particle.isValid()){
				fitness = 0;
				try {
					Iterator it = fitnessMap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pairs = (Map.Entry)it.next();
						formula = robotGen.generateRobotToString(particle).replaceAll("a", pairs.getKey().toString());
					    result = new Double(engine.eval(formula).toString());
					    //System.out.println(result + " " + Double.parseDouble(pairs.getValue().toString()));
						fitness += Math.abs(result - Double.parseDouble(pairs.getValue().toString()));
					}
					
					particle.setFitness(iteration, (1 / fitness) * 10000);
					//System.out.println("fitness: " + particle.getFitness());
				} catch (ScriptException e) {
					e.printStackTrace();
					particle.setFitness(iteration, 0);
				}
			}
			else
				particle.setFitness(iteration, 0);
		}
	}
	
	public void run(){
		generateInitialSwarm();
		
		for (int i = 0; i < Setup.ITERATIONS; i++){
			iteration = i;
			generateRobots();
			
			testRobots();
			
			calculateFitness();
			updateGlobalFitness();
			updateVelocities(iteration);
			
			logIteration();
			
			updateLocations();
			
			output();
		}
	}
	
	private void output() {
		Particle particle = _swarm.getGlobalBestParticle();
		String formula = robotGen.generateRobotToString(particle);
		System.out.println(iteration + ": (" + particle.getLocalBestFitness() + ") " + formula);
		
		double result, fitness;
		Iterator it = fitnessMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			formula = robotGen.generateRobotToString(particle).replaceAll("a", pairs.getKey().toString());
		    try {
				result = new Double(engine.eval(formula).toString());
				System.out.println(result + " : " + Double.parseDouble(pairs.getValue().toString()));
			}catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
