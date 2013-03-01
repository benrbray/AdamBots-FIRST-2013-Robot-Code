/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.actuators.RobotActuators;
import robot.logic.LogicTask;

/**
 * Feeds a disc into the shooter by enabling the solenoid, waiting for the 
 * default period of time, then disabling the solenoid.
 * @author Ben
 */
public class TFeedDisc extends LogicTask {
	//// CONSTANTS -------------------------------------------------------------
	
	/** Default delay constant for feeding the shooter. */
	public static final int SOLENOID_ON_MILLIS = 500;
	
	//// PRIVATE VARIABLES -----------------------------------------------------
	
	/* The total elapsed time, recorded at the start of this task. */
	private long _initialTimeMillis;
	/* Milliseconds to wait before disabling the shooter feeder solenoid. */
	private int _delayMillis;
	
	//// CONSTRUCTOR -----------------------------------------------------------
	
	/**
	 * Feeds a disc into the shooter by enabling the solenoid, waiting for the 
	 * default period of time, then disabling the solenoid.
	 */
	public TFeedDisc(){
		this(SOLENOID_ON_MILLIS);
	}
	
	/**
	 * Feeds a disc into the shooter by enabling the solenoid, waiting for a 
	 * specified period of time, then disabling the solenoid.
	 * @param delay The number of milliseconds to wait before retracting the solenoid.
	 */
	public TFeedDisc(int delay){
		_delayMillis = delay;
	}
	
	//// INITIALIZATION --------------------------------------------------------
	
	protected void initialize() {
		_initialTimeMillis = System.currentTimeMillis();
		RobotActuators.shooterFeederSolenoid.set(true);
	}

	//// UPDATE ----------------------------------------------------------------
	
	protected void update() {
		if(System.currentTimeMillis() - _initialTimeMillis >= _delayMillis){
			_done = true;
			RobotActuators.shooterFeederSolenoid.set(false);
		}
	}

	//// FINISH ----------------------------------------------------------------
	
	protected int finish() {
		RobotActuators.shooterFeederSolenoid.set(false);
		return _done ? SUCCESS : FAILURE;
	}
	
}
