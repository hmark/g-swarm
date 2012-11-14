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
	public static int PARTICLES = 30;
	
	/**
	 * Dimensions number of search space.
	 */
	public static int DIMENSIONS = 100;
	
	public static int CONSTRAINT = 500;
	
	// PSO initial location and velocity coefficients
	
	/**
	 * Lower constraint of dimension value.
	 */
	public static int XMIN = 0;
	
	/**
	 * Upper constraint of dimension value.
	 */
	public static int XMAX = 255;
	
	/**
	 * Minimum speed of particle for dimension.
	 */
	public static int SPEED_MIN = -128;
	
	/**
	 * Maximum speed of particle for dimension.
	 */
	public static int SPEED_MAX = 128;
	
	// PSO velocity update coefficients
	
	/**
	 * Accelerator coefficient of cognitive component.
	 */
	public static double C1 = 1.0;
	
	/**
	 * Accelerator coefficient of social component.
	 */
	public static double C2 = 1.0; 
	
	/**
	 * Minimum inertia weight coefficient.
	 */
	public static double WMIN = 0.4;
	
	/**
	 * Starting inertia weight coefficient.
	 */
	public static double WMAX = 0.9;
	
	// PSO stop condition limits
	
	/**
	 * Iterations number of particle swarm algorithm.
	 */
	public static int ITERATIONS = 1000;
	
	/**
	 * Fitness tolerance of acceptable solution.
	 */
	public static int FITNESS_TOLERANCE = 50; 
}
