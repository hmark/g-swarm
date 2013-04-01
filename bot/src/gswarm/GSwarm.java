package gswarm;

import pso.PSO;
import pso.Particle;
import pso.Setup;
import utils.DateUtils;
import utils.FileUtils;
import external.*;
import gui.Window;

public class GSwarm extends PSO implements Runnable {
	
	public String name = "fire";
	private String _filePrefix = "test/test" + DateUtils.getCurrentDateTime("yyyy-MM-dd-HH-mm-ss") + "/";
	public GSwarmRobotGenerator robotGen;
	
	public int iteration;
	
	public GSwarm(){
		init();
	}

	public GSwarm(String name){
		this.name = name;
		init();
	}
	
	protected void init(){
		RobotTester.loadTestScriptPath();
		loadTemplate(Conf.TEMPLATE);
	}
	
	public void loadTemplate(String templatePath){
		robotGen = new GSwarmRobotGenerator(templatePath);
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
			
			Window.getInstance().update();
		}
	}
	
	protected void generateRobots(){
		Particle particle;
		
		for (int j = 0; j < Setup.PARTICLES; j++){
			particle = _swarm.getParticleAt(j);
			
			setParticleId(particle, j);
			
			robotGen.generateRobot(particle);
		}
	}
	
	protected void testRobots(){
		RobotTester.startTest(Setup.PARTICLES, iteration, _filePrefix, name);
	}
	
	protected void calculateFitness(){
		Particle particle;
		double result;
		
		for (int j = 0; j < Setup.PARTICLES; j++){
			particle = _swarm.getParticleAt(j);
			
			if (particle.isValid()){
				result = RobotTester.loadTotalScore(particle);
				particle.setFitness(iteration, result);
			}
			else
				particle.setFitness(iteration, 0);
		}
	} 
	
	private void setParticleId(Particle particle, int pos){
		String particleName, filepath, id, dir;
		
		id = Integer.toString(10000 + pos);
		particleName = "particle" + id;
		dir = _filePrefix + "iter" + (10000 + iteration) + "/" + name + "_" + particleName + "/";
		filepath = dir + "GSwarmRobot" + id + ".java";
		
		particle.setId(id);
		particle.setDir(dir);
		particle.setName(particleName);
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
		String logPath = _filePrefix + "iter" + (10000 + iteration) + "/logs/" + name  + "_data.log";
		
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
		log += "Iteration #" + iteration + "\n";
		log += "gbest: " + gbest.getName() + " (from iteration " + gbest.getBestIteration() + ") with fitness " + gbest.getLocalBestFitness() +"\n";
		log += "actual max: " + actualMax + " (" + particleActualMax.getName() + ")\n";
		
		log += "lbest mean: " + lbestMean / Setup.PARTICLES + "\n";
		log += "actual mean: " + actualMean / validParticlesNum + " (only valid particles are counted)\n";
		
		log += "valid particles: " + validParticlesNum + "\n";
		log += "invalid particles: " + (Setup.PARTICLES - validParticlesNum) + "\n";
		
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
