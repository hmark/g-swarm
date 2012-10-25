package gswarm;

import log.PSOLogger;
import pso.PSO;
import pso.Particle;
import pso.Setup;
import utils.DateUtils;

public class GSwarm extends PSO {

	public GSwarm(){
	}
	
	public void start(){
		generateInitialSwarm();
		
		GSwarmRobotGenerator robotGen = new GSwarmRobotGenerator("conf/bot.tmpl");
		int iters = Setup.ITERATIONS;
		int particles = Setup.PARTICLES;
		Particle particle;
		String name, filepath;
		String filePrefix = "test/test" + DateUtils.getCurrentDateTime("yyyy-MM-dd-HH-mm-ss") + "/";
		
		for (int i = 0; i < iters; i++){
			for (int j = 0; j < particles; j++){
				particle = _swarm.getParticleAt(j);
				
				name = "iter" + (1000 + i) + "/particle." + (1000 + j);
				particle.setName(name);

				filepath = filePrefix + name;
				robotGen.generateRobot(particle, filepath);
				
				robotGen.testRobot(particle);
				robotGen.calculateFitness(particle);
			}
			
			executeIteration(i);
		}
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
			
		//PSOLogger.logIteration(iter, _swarm);
		//PSOLogger.logBestLocation(_swarm);
		//PSOLogger.logParticleLocation(_target);		
	}
}
