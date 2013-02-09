/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotClimb;
import robot.behavior.RobotShoot;
import robot.logic.LogicTask;

/**
 *
 * @author Ben
 */
public final class TAwaitStatus extends LogicTask {
    //// CONSTANTS -------------------------------------------------------------
    
    public static final int WINCH_IN_POSITION = 0;
    public static final int SHOOTER_IN_POSITION = 1;
    public static final int SHOOTER_UP_TO_SPEED = 2;
    public static final int WINCH_HOOK_LIMIT_SWITCHES_PRESSED = 3;
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private int _status;
    private double _value;
    
    private boolean _done = false;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public TAwaitStatus(int status){
	this(status, 4.0);
    }
    
    public TAwaitStatus(int status, double value){
	_status = status;
	_value = value;
	initializeTask();
    }

    //// INITIALIZATION --------------------------------------------------------
    
    /**
     * Performs any necessary initialization for this task and optionally sends
     * an expected value to a behavior class.  For example, if this task is set
     * to wait for the WINCH_IN_POSITION status, initializeTask() will call
     * RobotClimb.setWinchTarget() with the value given to this task's
     * constructor.
     * @param useValue Should the Task send a target value to the Behavior class
     * that it is waiting on?
     */
    public void initializeTask() {
	if(_value != Double.NaN){
	    switch(_status){
		case WINCH_IN_POSITION:
		    RobotClimb.setWinchTarget(_value);
		    break;
		case SHOOTER_IN_POSITION:
		    RobotShoot.setAngleDegrees(_value);
		    break;
		case SHOOTER_UP_TO_SPEED:
		    RobotShoot.setSpeed(_value);
		    break;
		case WINCH_HOOK_LIMIT_SWITCHES_PRESSED:
		    break;
		default:
			throw new IllegalArgumentException();
	    }
	}
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void updateTask() {
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
	    case WINCH_HOOK_LIMIT_SWITCHES_PRESSED:
		_done = RobotSensors;
		break;
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
    public int finishTask() {
	return isDone() ? SUCCESS : FAILURE;
    }
    
    //// COMPLETION STATUS -----------------------------------------------------
    
    /**
     * Has the desired status been reached (thus completing the Task)?
     * @return Task completion status.
     */
    public boolean isDone(){
	return _done;
    }
    
}
