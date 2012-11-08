package gswarm;

import log.PSOLogger;
import pso.PSO;
import pso.Particle;
import pso.Setup;
import utils.DateUtils;
import utils.FileUtils;
import external.*;
import gui.Window;

public class GSwarm extends PSO implements Runnable {
	
	private String _filePrefix = "test/test" + DateUtils.getCurrentDateTime("yyyy-MM-dd-HH-mm-ss") + "/";

	public GSwarm(){
	}
	
	public void run(){
		generateInitialSwarm();
		
		Particle particle;
		GSwarmRobotGenerator robotGen = new GSwarmRobotGenerator("conf/bot.tmpl");
		int result;
		
		for (int i = 0; i < Setup.ITERATIONS; i++){
			
			for (int j = 0; j < Setup.PARTICLES; j++){
				particle = _swarm.getParticleAt(j);
				
				setParticleId(particle, i, j);
				
				robotGen.generateRobot(particle);
			}
			
			RobotTester.startTest(Setup.PARTICLES, i, _filePrefix);
			
			for (int j = 0; j < Setup.PARTICLES; j++){
				particle = _swarm.getParticleAt(j);
				
				if (particle.isValid()){
					result = RobotTester.loadResult(particle.getDir() + "result.rsl");
					logParticle(particle);
					particle.setFitness(i, result);
				}
				else
					particle.setFitness(i, 0);
			}
			
			//PSOLogger.logGSwarmIteration(_filePrefix, i, _swarm);
			
			executeIteration(i);
			logIteration(i);
			
			Window.getInstance().update();
		}
	}
	
	private void setParticleId(Particle particle, int iter, int pos){
		String name, filepath, id, dir;
		
		id = Integer.toString(10000 + pos);
		name = "particle" + id;
		dir = _filePrefix + "iter" + (10000 + iter) + "/" + name + "/";
		filepath = dir + "GSwarmRobot" + id + ".java";
		
		particle.setId(id);
		particle.setDir(dir);
		particle.setName(name);
		particle.setSrc(filepath);
	}
	
	public void executeIteration(int iter){
		//calculateFitness();
		updateGlobalFitness();
			
		/*if (isEarlyEnd()){
			PSOLogger.logEarlyEnd(iter, _swarm);
			return;
		}*/
			
		updateVelocities(iter);
		updateLocations();
			
		//PSOLogger.logGSwarmIteration(iter, _swarm);
		//PSOLogger.logBestLocation(_swarm);
		//PSOLogger.logParticleLocation(_target);		
	}
	
	public void logIteration(int iter){
		Particle particle;
		Particle gbest = _swarm.getGlobalBestParticle();
		String logPath = _filePrefix + "iter" + (10000 + iter) + "/logs/data.log";
		
		Double actualMax = 0.0;
		Particle particleActualMax = null;
		Double lbestMean = 0.0;
		Double actualMean = 0.0;
		int validParticlesNum = 0;
		
		for (int i = 0; i < Setup.PARTICLES; i++){
			particle = _swarm.getParticleAt(i);
			
			if (particle.isValid()){
				lbestMean += particle.getLocalBestFitness();
				actualMean += particle.getFitness();
				
				if (particle.getFitness() > actualMax){
					actualMax = particle.getFitness();
					particleActualMax = particle;
				}
				
				validParticlesNum++;
			}
		}
		
		String log = "Iteration #" + iter;
		log += "gbest: " + gbest.getName() + "(from iteration " + gbest.getBestIteration() + ")\n";
		log += "actual max: " + actualMax + "(" + particleActualMax.getName() + ")\n";
		
		log += "lbest mean: " + Math.floor(lbestMean / validParticlesNum) + " (only valid particles are counted)\n";
		log += "actual mean: " + Math.floor(actualMean / validParticlesNum) + " (only valid particles are counted)\n";
		
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
