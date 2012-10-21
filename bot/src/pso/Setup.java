package pso;

/**
 * PSO variables configuration.
 * 
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 * 
 */
public class Setup {
	
	// PSO size properties

	/**
	 * Number of particles in swarm.
	 */
	public static final int PARTICLES = 200;
	
	/**
	 * Dimensions number of search space.
	 */
	public static final int DIMENSIONS = 15;
	
	// PSO initial location and velocity coefficients
	
	/**
	 * Lower constraint of dimension value.
	 */
	public static final int XMIN = 0;
	
	/**
	 * Upper constraint of dimension value.
	 */
	public static final int XMAX = 100; 
	
	/**
	 * Maximum speed of particle for dimension.
	 */
	public static final int SPEED_MAX = 100;
	
	// PSO velocity update coefficients
	
	/**
	 * Accelerator coefficient of cognitive component.
	 */
	public static final double C1 = 0.25;
	
	/**
	 * Accelerator coefficient of social component.
	 */
	public static final double C2 = 0.75; 
	
	/**
	 * Minimum inertia weight coefficient.
	 */
	public static final double WMIN = 0.5;
	
	/**
	 * Starting inertia weight coefficient.
	 */
	public static final double WMAX = 1;
	
	// PSO stop condition limits
	
	/**
	 * Iterations number of particle swarm algorithm.
	 */
	public static final int ITERATIONS = 1000;
	
	/**
	 * Fitness tolerance of acceptable solution.
	 */
	public static final int FITNESS_TOLERANCE = 50; 
}
