/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotClimb;
import robot.behavior.RobotShoot;
import robot.logic.LogicTask;
import robot.logic.targeting.TargetSpinLogic;
import robot.sensors.RobotSensors;

/**
 * This task will wait for the specified status to be achieved.  For example, it
 * may be configured to wait until the shooter is up to speed before telling the
 * current LogicPhase to move on to the next task.
 * @author Ben
 */
public final class TAwaitStatus extends LogicTask {
    //// CONSTANTS -------------------------------------------------------------
    
	// Status Constants
    public static final int WINCH_IN_POSITION = 0;
    public static final int SHOOTER_IN_POSITION = 1;
    public static final int SHOOTER_UP_TO_SPEED = 2;
    public static final int TARGETING_COMPLETED = 98;
	
	// Timeout
	public static final int NO_TIMEOUT = -1;
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private int _status;
	private long _timeout;
	
	private long _initialTimeMillis;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * This task will wait for the specified status to be achieved.  For example, it
     * may be configured to wait until the shooter is up to speed before telling the
     * current LogicPhase to move on to the next task.
     * @param status The status to wait for.
     */
    public TAwaitStatus(int status){
		this(status, NO_TIMEOUT);
    }
	
	/**
	 * This task will wait for the specified status to be achieved.  For example, it
     * may be configured to wait until the shooter is up to speed before telling the
     * current LogicPhase to move on to the next task.  
	 * @param status The status to wait for.
	 * @param timeoutMillis If the status is not reached after the elapsed time
	 * has exceeded this amount, then the task reports that it has been successfully
	 * completed.  To remove the time restriction, set this value to TAwaitStatus.NO_TIMEOUT.
	 * @see TAwaitStatus#NO_TIMEOUT
	 */
	public TAwaitStatus(int status, int timeoutMillis){
		_status = status;
		_timeout =  timeoutMillis;
    }

    //// INITIALIZATION --------------------------------------------------------
    
	/**
	 * Initializes this task.  Stores the current system time, for timeout purposes.
	 */
    public void initialize() {
		_initialTimeMillis = System.currentTimeMillis();
    }

    //// UPDATE ----------------------------------------------------------------
    
	/**
	 * Checks to see if the status has been achieved or if the task has timed out.
	 */
    public void update() {
		// Check Status
		switch(_status){
			case WINCH_IN_POSITION:
				_done = RobotClimb.isWinchInPosition();
				break;
			case SHOOTER_IN_POSITION:
				_done = RobotShoot.isShooterInPosition();
				break;
			case SHOOTER_UP_TO_SPEED:
				_done = RobotShoot.isShooterUpToSpeed();
				break;
		}
		
		// Timeout
		if(_timeout > 0 && System.currentTimeMillis() - _initialTimeMillis >= _timeout){
			_done = true;
		}
    }

    //// FINISH ----------------------------------------------------------------
    
    /**
     * Stops this task, regardless of completion status.  Returns a status value
     * indicating success or failure.
     * @return Status value.  (SUCCES=0, FAILURE=1)
     * @see robot.logic.LogicTask#SUCCESS
     * @see robot.logic.LogicTask#FAILURE
     */
    public int finish() {
		return _done ? SUCCESS : FAILURE;
    }
    
}
