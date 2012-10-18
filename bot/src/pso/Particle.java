package pso;

public class Particle {
	
	private int[] _locationVector;
	private int[] _velocityVector;
	private int _dimensions;

	public Particle(int dimensions){
		_locationVector = new int[dimensions];
		_dimensions = dimensions;
	}
	
	public void setLocationValueAt(int dim, int value){
		_locationVector[dim] = value;
	}
	
	public int getLocationValueAt(int dim){
		return _locationVector[dim];
	}
	
	public void setVelocityValueAt(int dim, int value){
		_velocityVector[dim] = value;
	}
	
	public int getVelocityValueAt(int dim){
		return _velocityVector[dim];
	}
	
	public void updateLocationVector(){
		for (int i = 0; i < _dimensions; i++){
			_locationVector[i] = _locationVector[i] + _velocityVector[i];
		}
	}
	
}
