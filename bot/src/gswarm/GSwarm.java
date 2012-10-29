package gswarm;

import log.PSOLogger;
import pso.PSO;
import pso.Particle;
import pso.Setup;
import utils.DateUtils;
import external.RobotTester;

public class GSwarm extends PSO {
	
	private String _filePrefix = "test/test" + DateUtils.getCurrentDateTime("yyyy-MM-dd-HH-mm-ss") + "/";

	public GSwarm(){
	}
	
	public void start(){
		generateInitialSwarm();
		
		GSwarmRobotGenerator robotGen = new GSwarmRobotGenerator("conf/bot.tmpl");
		Particle particle;
		
		for (int i = 0; i < Setup.ITERATIONS; i++){
			for (int j = 0; j < Setup.PARTICLES; j++){
				particle = _swarm.getParticleAt(j);
				setParticleId(particle, i, j);
				
				robotGen.generateRobot(particle);
				
				if (particle.isValid())
					RobotTester.testRobot(particle);
				else 
					particle.setFitness(0);
			}
			
			PSOLogger.logGSwarmIteration(_filePrefix, i, _swarm);
			
			executeIteration(i);
		}
	}
	
	private void setParticleId(Particle particle, int iter, int pos){
		String name, filepath;
		
		name = "particle" + (10000 + pos);
		filepath = _filePrefix + "iter" + (10000 + iter) + "/" + name;
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
