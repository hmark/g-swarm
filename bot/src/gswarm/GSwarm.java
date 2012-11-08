package gswarm;

import log.PSOLogger;
import pso.PSO;
import pso.Particle;
import pso.Setup;
import utils.DateUtils;
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
					particle.setFitness(result);
				}
				else
					particle.setFitness(0);
			}
			
			//PSOLogger.logGSwarmIteration(_filePrefix, i, _swarm);
			
			executeIteration(i);
			
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
}
