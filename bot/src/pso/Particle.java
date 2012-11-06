package pso;

/**
 * Particle represents one solution in search space.
 * 
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 * 
 */
public class Particle {
	
	private int _dimensions;
	
	private int[] _velocityVector;
	private int[] _locationVector;
	private int _distance;
	
	private int[] _bestLocationVector;
	private int _bestDistance = 1000000;
	
	private double _localBestFitness = 0;
	private double _fitness;
	
	private String _id;
	private String _name;
	private String _src;
	private String _dir;
	
	private String _bestSrc;
	
	private boolean _valid = true;

	/**
	 * Constructor creates location, velocity and best location vectors with specified dimension size.
	 * 
	 * @param dimensions	number of dimensions
	 */
	public Particle(int dimensions){
		_locationVector = new int[dimensions];
		_velocityVector = new int[dimensions];
		_bestLocationVector = new int[dimensions];
		
		_dimensions = dimensions;
	}
	
	/**
	 * Set location value to location vector at specified dimension.
	 * 
	 * @param dim		dimension number
	 * @param value		location value
	 */
	public void setLocationValueAt(int dim, int value){
		value = Math.max(Math.min(value, Setup.XMAX), Setup.XMIN);
		_locationVector[dim] = value;
	}
	
	/**
	 * Get location value at specified dimension.
	 * 
	 * @param dim		dimension number
	 * @return location value at specified dimension
	 */
	public int getLocationValueAt(int dim){
		return _locationVector[dim];
	}
	
	/**
	 * Get full location vector.
	 * 
	 * @return location vector
	 */
	public int[] getLocation() {
		return _locationVector;
	}
	
	/**
	 * Set velocity value to velocity vector at specified dimension.
	 * 
	 * @param dim		dimension number
	 * @param value		velocity value
	 */
	public void setVelocityValueAt(int dim, int value){
		value = Math.max(Math.min(value, Setup.SPEED_MAX), Setup.SPEED_MIN);
		_velocityVector[dim] = value;
	}
	
	/**
	 * Get velocity value at specified dimension.
	 * 
	 * @param dim		dimension number
	 * @return velocity value at specified dimension
	 */
	public int getVelocityValueAt(int dim){
		return _velocityVector[dim];
	}
	
	/**
	 * Get best location value at specified dimension.
	 * 
	 * @param dim		dimension number
	 * @return best location value at specified dimension
	 */
	public int getBestLocationValueAt(int dim) {
		return _bestLocationVector[dim];
	}
	
	/**
	 * Get full best location vector.
	 * 
	 * @return best location vector
	 */
	public int[] getBestLocation() {
		return _bestLocationVector;
	}

	/**
	 * Get fitness of actual search space location.
	 * 
	 * @return fitness of actual search space location
	 */
	public double getFitness() {
		return _fitness;
	}

	/**
	 * Update current fitness, local best fitness and best distance value if the condition is met.
	 * 
	 * @param _fitness 	new fitness
	 */
	public void setFitness(double _fitness) {
		this._fitness = _fitness;
		if (_localBestFitness < _fitness){
			_localBestFitness = _fitness;
			_bestSrc = _src;
			
			// save location of best fitness
			for (int i = 0; i < _dimensions; i++){
				_bestLocationVector[i] = _locationVector[i];
			}
			
			// save best distance
			_bestDistance = _distance;
		}
	}
	
	/**
	 * Set current distance to target particle.
	 * 
	 * @param _distance current distance to target particle
	 */
	public void setDistance(int _distance){
		this._distance = _distance;
	}
	
	/**
	 * Get value of best distance from execution start.
	 * 	
	 * @return value of best distance from execution start
	 */
	public int getBestDistance(){
		return _bestDistance;
	}

	/**
	 * Get value of best fitness from execution start.
	 * 	
	 * @return value of best fitness from execution start
	 */
	public double getLocalBestFitness() {
		return _localBestFitness;
	}
	
	public void setId(String id) {
		_id = id;
	}
	
	public String getId() {
		return _id;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setSrc(String src) {
		_src = src;
	}
	
	public String getSrc() {
		return _src;
	}
	
	public void setDir(String dir) {
		_dir = dir;
	}
	
	public String getDir() {
		return _dir;
	}
	
	public String getBestSrc() {
		return _bestSrc;
	}

	public boolean isValid() {
		return _valid;
	}

	public void setValid(boolean _valid) {
		this._valid = _valid;
	}
	
}
