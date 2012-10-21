package log;

import pso.*;

public class PSOLogger {
	
	static public void logIteration(int iter, Swarm swarm){
		System.out.println("------------------------");
		Particle bestParticle = swarm.getGlobalBestParticle();
		System.out.println("ITER#" + iter + ": " + bestParticle.getLocalBestFitness() + " with distance " + bestParticle.getBestDistance());
	}
	
	static public void logEarlyEnd(int iter, Swarm swarm){
		System.out.println("------------------------");
		System.out.println("TARGET SOLUTION WERE FOUND IN ITER #" + iter + ".");
		System.out.println("DISTANCE: " + swarm.getGlobalBestParticle().getBestDistance());
		logBestLocation(swarm);
	}

	static public void logBestLocation(Swarm swarm){
		Particle bestParticle = swarm.getGlobalBestParticle();
		
		int dims = Setup.DIMENSIONS;
		for (int i = 0; i < dims; i++){
			System.out.println("BEST-DIM[" + i + "]: " + bestParticle.getBestLocationValueAt(i));
		}	
	}
	
	static public void logBestParticleLocation(Particle particle){
		int dims = Setup.DIMENSIONS;
		for (int i = 0; i < dims; i++){
			System.out.println("PART-DIM[" + i + "]: " + particle.getBestLocationValueAt(i));
		}	
	}
	
	static public void logParticleLocation(Particle particle){
		int dims = Setup.DIMENSIONS;
		for (int i = 0; i < dims; i++){
			System.out.println("PART-DIM[" + i + "]: " + particle.getLocationValueAt(i));
		}	
	}
}
