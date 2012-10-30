package external;

import gswarm.GSwarmRobotGenerator;

import java.lang.InterruptedException;
import pso.Particle;

public class ParticleTestTask implements Runnable {
	
	private Particle _particle;
	//private Thread _thread; 
	
	public ParticleTestTask(Particle particle){
		_particle = particle;
		//_thread = new Thread(this);
		//_thread.start();
	}

	@Override
	public void run() {
		GSwarmRobotGenerator robotGen = new GSwarmRobotGenerator("conf/bot.tmpl");
	    //try {
	     // while (true) {
				robotGen.generateRobot(_particle);
				
				if (_particle.isValid())
					RobotTester.testRobot(_particle);
				else 
					_particle.setFitness(0);
	        //System.out.println(message);
	        //Thread.sleep(0);
	      //}
	   // } catch (InterruptedException iex) {}
	  }

}
