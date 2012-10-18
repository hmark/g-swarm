package pso;

public class Setup {

	public static final int PARTICLES = 200; // number of particles in swarm
	public static final int ITERATIONS = 15; // iterations number of particle swarm algorithm
	public static final int DIMENSIONS = 15; // dimensions number of search space
	public static final int XMIN = 0; // lower moving constraint of dimensions
	public static final int XMAX = 100; // upper moving constraint of dimensions 
	public static final int SPEED_MAX = 100; // maximum speed of particle
	public static final double C1 = 0.5; // accelerator coefficient 
	public static final double C2 = 0.5; // accelerator coefficient 
	public static final double S1 = 0.5; // stochastic coefficient 
	public static final double S2 = 0.5; // stochastic coefficient 
	public static final double WMIN = 0; // minimum inertia weight 
	public static final double WMAX = 1; // starting inertia weight
	
}
