package pso;

public class Swarm {
	
	private Particle _particles[];
	
	public Swarm(int particlesNum){
		_particles = new Particle[particlesNum];
	}
	
	public void addParticleAt(Particle particle, int position){
		_particles[position] = particle;
	}
}
