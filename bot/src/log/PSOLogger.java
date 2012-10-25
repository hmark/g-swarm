package log;

import pso.*;
import utils.FileUtils;

public class PSOLogger {
	
	static public void logGSwarmIteration(String prefix, int iter, Swarm swarm){
		String data = "";
		Particle particle;
		
		for (int j = 0; j < Setup.PARTICLES; j++){
			particle = swarm.getParticleAt(j);
			data += particle.getSrc() + " : " + particle.getFitness() + "\n";
			data += particle.getBestSrc() + " : " + particle.getLocalBestFitness() + "\n";
			data += "----------\n";
		}
		
		FileUtils.saveStringToFile(prefix + "results/iter" + (10000 + iter), data);
	}
	
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
	
	static public void logGSwarmParticle(Particle particle){
		System.out.println("------------------------");
		System.out.println(particle.getSrc() + " : " + particle.getFitness());
		System.out.println(particle.getBestSrc() + " : " + particle.getLocalBestFitness());
	}
}
