package h;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.DeathEvent;
import java.util.Random;
import java.awt.geom.Point2D;

public class Template extends AdvancedRobot {

	static private Random random;

	static private double myAbsoluteBearing;
	static private double myEnergy;
	static private double myHeading;
	static private double myX;
	static private double myY;

	static private double enemyBearing;
	static private double enemyDistance;
	static private double enemyEnergy;

	// every strategy has weight (0-100) and win rate (0-100)
	// score, randomMovement, linearMovement, goBackMovement, guessFactorGun, patternMatchingGun, visitStatsCountGun, headsOnGun, linearGun
	static private int[][] strategies = new int[5][9];
	static private int pickedStrategy;

	// strategy weights
	static private int movementWeightsSum;
	static private int gunWeightsSum;

	// movement vars
	static private double lastPositionX = 0.0D;
	static private double lastPositionY = 0.0D;
	static private double targetPositionX = 0.0D;
	static private double targetPositionY = 0.0D;

	private boolean loaded = false;
	
	public void run() {
		load();

		myX = getX();
		myY = getY();
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForRobotTurn(true);
		setAdjustRadarForGunTurn(true);

		random = robocode.util.Utils.getRandom();

		setStrategy();
		changeTarget();
		
		while (true) {
			turnRadarRightRadians(Double.POSITIVE_INFINITY);
		}
	}

	private void load(){
		strategies[0][1] = 0;
		strategies[0][2] = 0;
		strategies[0][3] = 100;

		strategies[1][1] = 0;
		strategies[1][2] = 0;
		strategies[1][3] = 100;

		strategies[2][1] = 0;
		strategies[2][2] = 0;
		strategies[2][3] = 100;

		strategies[3][1] = 0;
		strategies[3][2] = 0;
		strategies[3][3] = 100;

		strategies[4][1] = 0;
		strategies[4][2] = 0;
		strategies[4][3] = 100;

		loaded = true;
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		myAbsoluteBearing = getHeadingRadians() - getGunHeadingRadians();
		myHeading = getHeadingRadians();
		myEnergy = getEnergy();
		myX = getX();
		myY = getY();

		enemyEnergy = e.getEnergy();
		enemyDistance = e.getDistance();
		enemyBearing = e.getBearingRadians();

		movement();
		gun();
		radar();
		fire();
	}

	public void onWin(WinEvent e){
		strategies[pickedStrategy][0] += 1;
	}

	public void onDeath(DeathEvent e){
		strategies[pickedStrategy][0] -= 1;
	}

	public void setStrategy(){
		int bestScore = -100000;
		for (int i = 0; i < 5; i++){
			System.out.println("strategy: " + i + ", score: " + strategies[i][0] + ", best: " + bestScore);
			if (strategies[i][0] > bestScore){
				bestScore = strategies[i][0];
				pickedStrategy = i;
			}
		}

		movementWeightsSum = 0;
		for (int i = 1; i < 4; i++){
			movementWeightsSum += strategies[pickedStrategy][i];
		}

		gunWeightsSum = 0;
		for (int i = 4; i < 9; i++){
			//gunWeightsSum += strategies[pickedStrategy][i];
			gunWeightsSum += 100;
		}

		System.out.println("picked strategy:" + pickedStrategy);
	}

	/*
	* ROBOT MODULES
	*/

	private void movement(){
		//System.out.println("movement: " + Point2D.distance(myX, myY, targetPositionX, targetPositionY));
		if (Point2D.distance(myX, myY, targetPositionX, targetPositionY) < 5)
			changeTarget();

		goTo();
	}

	private void gun(){
		setTurnGunRightRadians(robocode.util.Utils.normalRelativeAngle(enemyBearing + myAbsoluteBearing)); // heads-on targeting
	}

	private void radar(){
		setTurnRadarRightRadians(robocode.util.Utils.normalRelativeAngle(enemyBearing + myHeading - getRadarHeadingRadians())); // lazy radar lock
	}

	private void fire(){
		setFireBullet(enemyEnergy * myEnergy / enemyDistance);
	}

	/*
	* MOVEMENT STRATEGY IMPLEMENTATIONS
	*/

	private void goTo() {
    	double a;
    	setTurnRightRadians(Math.tan(a = Math.atan2(targetPositionX - (int) myX, targetPositionY - (int) myY) - myHeading));
	    setAhead(Math.hypot(targetPositionX, targetPositionY) * Math.cos(a));
	}

	private void changeTarget(){
		System.out.println("change target: " + movementWeightsSum);
		double value = random.nextDouble() * movementWeightsSum;
		double border = strategies[pickedStrategy][1];

		System.out.println("value: " + value);
		if (value < border){
			setRandomTargetPosition();
			return;
		}
		border += strategies[pickedStrategy][2];
		System.out.println("border: " + border);

		if (value < border){
			setGoBackTargetPosition();
			return;
		}
		border += strategies[pickedStrategy][3];
		System.out.println("border: " + border);

		if (value < border){
			setLinearTargetPosition();
			return;
		}
		System.out.println("border: " + border);
	}

	private void setRandomTargetPosition(){
		double angle = random.nextDouble() * 50000.0D;
		lastPositionX = targetPositionX;
		lastPositionY = targetPositionY;
		targetPositionX = Math.min(750, Math.max(50, myX + Math.cos(angle) * 150.0D));
		targetPositionY = Math.min(550, Math.max(50, myY + Math.sin(angle) * 150.0D));

		System.out.println("setRandomTargetPosition: " + targetPositionX + ", " + targetPositionY);
	}

	private void setGoBackTargetPosition(){
		double help;

		help = targetPositionX;
		targetPositionX = lastPositionX;
		lastPositionX = help;

		help = targetPositionY;
		targetPositionY = lastPositionY;
		lastPositionY = help;

		System.out.println("setGoBackTargetPosition: " + targetPositionX + ", " + targetPositionY);
	}

	private void setLinearTargetPosition(){
		double angle = Math.cos(enemyBearing) + (160.0D - enemyDistance) * (getVelocity() / 3000.0D);
		lastPositionX = targetPositionX;
		lastPositionY = targetPositionY;
		targetPositionX = Math.min(750, Math.max(50, myX + Math.cos(angle) * 150.0D));
		targetPositionY = Math.min(550, Math.max(50, myY + Math.sin(angle) * 150.0D));

		System.out.println("setLinearTargetPosition: " + targetPositionX + ", " + targetPositionY);
	}

	/*
	* GUN STRATEGY IMPLEMENTATIONS
	*/

}
