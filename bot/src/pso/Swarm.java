package pso;

/**
 * Swarm represents all particles and also set of solutions.
 * 
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 * 
 */
public class Swarm {
	
	private Particle _particles[];
	private Particle _globalBestParticle;
	
	/**
	 * Constructor generates pool for particles.
	 * 
	 * @param particlesNum		swarm size
	 */
	public Swarm(int particlesNum){
		_particles = new Particle[particlesNum];
	}
	
	/**
	 * Add particle to pool at concrete position.
	 * 
	 * @param particle		specific particle
	 * @param position		pool position
	 */
	public void addParticleAt(Particle particle, int position){
		_particles[position] = particle;
	}
	
	/**
	 * Get particle from pool.
	 * 
	 * @param position		pool position
	 * @return particle at position in pool
	 */
	public Particle getParticleAt(int position){
		return _particles[position];
	}

	/**
	 * Get particle which has best fitness in swarm (global best fitness).
	 * 
	 * @return particle with global best fitness
	 */
	public Particle getGlobalBestParticle() {
		return _globalBestParticle;
	}

	/**
	 * Set particle which has global best fitness in swarm (global best fitness).
	 * 
	 * @param _globalBestParticle	particle with global best fitness
	 */
	public void setGlobalBestParticle(Particle _globalBestParticle) {
		this._globalBestParticle = _globalBestParticle;
	}
}
