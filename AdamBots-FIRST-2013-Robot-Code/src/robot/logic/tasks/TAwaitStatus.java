/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotClimb;
import robot.behavior.RobotShoot;
import robot.logic.LogicTask;
import robot.logic.TargetLogic;
import robot.sensors.RobotSensors;

/**
 * This task will wait for the specified status to be achieved.  For example, it
 * may be configured to wait until the shooter is up to speed before telling the
 * current LogicPhase to move on to the next task.
 * @author Ben
 */
public final class TAwaitStatus extends LogicTask {
    //// CONSTANTS -------------------------------------------------------------
    
    public static final int WINCH_IN_POSITION = 0;
    public static final int SHOOTER_IN_POSITION = 1;
    public static final int SHOOTER_UP_TO_SPEED = 2;
    public static final int WINCH_HOOK_LIMIT_SWITCHES_PRESSED = 3;
    public static final int TARGETING_COMPLETED = 98;
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private int _status;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * This task will wait for the specified status to be achieved.  For example, it
     * may be configured to wait until the shooter is up to speed before telling the
     * current LogicPhase to move on to the next task.
     * @param status The status to wait for.
     */
    public TAwaitStatus(int status){
	_status = status;
    }

    //// INITIALIZATION --------------------------------------------------------
    
    public void initialize() {
	
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void update() {
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
		_done = RobotSensors.limitHookLeftArm.get() && RobotSensors.limitHookRightArm.get();
            case TARGETING_COMPLETED:
                _done = TargetLogic.isTargeted();
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
    public int finish() {
	return _done ? SUCCESS : FAILURE;
    }
    
}
