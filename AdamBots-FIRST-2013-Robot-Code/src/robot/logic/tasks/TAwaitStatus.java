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
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private int _status;
    private boolean _done = false;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public TAwaitStatus(int status){
	_status = status;
	initializeTask();
    }

    //// INITIALIZATION --------------------------------------------------------
    
    public void initializeTask() {
	switch(_status){
	    case WINCH_IN_POSITION:
		RobotClimb.setWinchTarget(_status);
		break;
	    case SHOOTER_IN_POSITION:
		RobotShoot.setAngleDegrees(_status);
		break;
	    case SHOOTER_UP_TO_SPEED:
		RobotShoot.setAngleDegrees(_status);
		break;
	    default:
		throw new IllegalArgumentException();
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
	}
    }

    //// FINISH ----------------------------------------------------------------
    
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
