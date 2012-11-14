package gswarm;

import pso.PSO;
import pso.Particle;
import pso.Setup;
import utils.DateUtils;
import utils.FileUtils;
import external.*;
import gui.Window;

public class GSwarm extends PSO implements Runnable {
	
	private String _filePrefix = "test/test" + DateUtils.getCurrentDateTime("yyyy-MM-dd-HH-mm-ss") + "/";
	private GSwarmRobotGenerator _robotGen = new GSwarmRobotGenerator("conf/bot.tmpl");
	
	private int _iter;

	public GSwarm(){
	}
	
	public void run(){
		generateInitialSwarm();
		
		for (int i = 0; i < Setup.ITERATIONS; i++){
			_iter = i;
			generateRobots();
			
			testRobots();
			
			calculateFitness();
			updateGlobalFitness();
			updateVelocities(_iter);
			
			logIteration();
			
			updateLocations();
			
			Window.getInstance().update();
		}
	}
	
	protected void generateRobots(){
		Particle particle;
		
		for (int j = 0; j < Setup.PARTICLES; j++){
			particle = _swarm.getParticleAt(j);
			
			setParticleId(particle, j);
			
			_robotGen.generateRobot(particle);
		}
	}
	
	protected void testRobots(){
		RobotTester.startTest(Setup.PARTICLES, _iter, _filePrefix);
	}
	
	protected void calculateFitness(){
		Particle particle;
		int result;
		
		for (int j = 0; j < Setup.PARTICLES; j++){
			particle = _swarm.getParticleAt(j);
			
			if (particle.isValid()){
				result = RobotTester.loadTotalScore(particle);
				particle.setFitness(_iter, result);
			}
			else
				particle.setFitness(_iter, 0);
		}
	} 
	
	private void setParticleId(Particle particle, int pos){
		String name, filepath, id, dir;
		
		id = Integer.toString(10000 + pos);
		name = "particle" + id;
		dir = _filePrefix + "iter" + (10000 + _iter) + "/" + name + "/";
		filepath = dir + "GSwarmRobot" + id + ".java";
		
		particle.setId(id);
		particle.setDir(dir);
		particle.setName(name);
		particle.setSrc(filepath);
	}
	
	public void logIteration(){
		Particle particle;
		
		// log particles
		for (int j = 0; j < Setup.PARTICLES; j++){
			particle = _swarm.getParticleAt(j);
			
			if (particle.isValid())
				logParticle(particle);
		}
		
		Particle gbest = _swarm.getGlobalBestParticle();
		String logPath = _filePrefix + "iter" + (10000 + _iter) + "/logs/data.log";
		
		Double actualMax = 0.0;
		Particle particleActualMax = _swarm.getParticleAt(0);
		Double lbestMean = 0.0;
		Double actualMean = 0.0;
		int validParticlesNum = 0;
		
		for (int i = 0; i < Setup.PARTICLES; i++){
			particle = _swarm.getParticleAt(i);
			
			lbestMean += particle.getLocalBestFitness();
			
			if (particle.isValid()){
				actualMean += particle.getFitness();
				
				if (particle.getFitness() > actualMax){
					actualMax = particle.getFitness();
					particleActualMax = particle;
				}
				
				validParticlesNum++;
			}
		}
		
		String log = "";
		log += "Iteration #" + _iter + "\n";
		log += "gbest: " + gbest.getName() + " (from iteration " + gbest.getBestIteration() + ") with fitness " + gbest.getLocalBestFitness() +"\n";
		log += "actual max: " + actualMax + " (" + particleActualMax.getName() + ")\n";
		
		log += "lbest mean: " + Math.floor(lbestMean / Setup.PARTICLES) + "\n";
		log += "actual mean: " + Math.floor(actualMean / validParticlesNum) + " (only valid particles are counted)\n";
		
		log += "invalid particles: " + validParticlesNum + "\n";
		
		FileUtils.saveStringToFile(logPath, log);
	}
	
	public void logParticle(Particle particle){
		String locations = "";
		String velocities = "";
		for (int i = 0; i < Setup.DIMENSIONS; i++){
			locations += particle.getLocationValueAt(i) + " ";
			velocities += particle.getVelocityValueAt(i) + " ";
		}
		
		String log = "Particle #" + particle.getId() + "\n";
		log += "lbest: " + particle.getLocalBestFitness() + "\n";
		log += "fitness: " + particle.getFitness() + "\n";
		log += "treesize: " + particle.getTreeSize() + "\n\n";
		log += "locations: \n" + locations + "\n\n";
		log += "velocities: \n" + velocities + "\n";
		
		FileUtils.saveStringToFile(particle.getDir() + "data.log", log);
	}
}
