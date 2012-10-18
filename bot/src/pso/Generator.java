package pso;

import pso.*;
import java.util.Random;

public class Generator {
	
	private Swarm _swarm;

	/**
	 * @param args
	 */
	public Generator(){
		start();
	}
	
	private void start(){
		generateInitialSwarm();
		
		for (int iter = 0; iter < Setup.ITERATIONS; iter++){
			executeIteration();
		}
	}
	
	private void generateInitialSwarm(){
		_swarm = new Swarm(Setup.PARTICLES);
		
		Particle particle;
		Random generator = new Random();
		int randValue;
		for (int i = 0; i < Setup.PARTICLES; i++){
			// generate particle
			particle = new Particle(Setup.DIMENSIONS);
			for (int j = 0; j < Setup.DIMENSIONS; j++){
				// generate random position value
				randValue = generator.nextInt(Setup.XMAX) + Setup.XMIN;
				particle.setLocationValueAt(j, randValue);
				
				randValue = generator.nextInt(Setup.SPEED_MAX);
				particle.setVelocityValueAt(j, randValue);
			}
		}
	}
	
	private void executeIteration(){
		
	}

}
