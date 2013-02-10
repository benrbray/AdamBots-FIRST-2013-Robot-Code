/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.climb;

import java.util.Vector;
import robot.RobotMain;
import robot.control.FancyJoystick;
import robot.logic.LogicPhase;
import robot.logic.LogicTask;
import robot.logic.tasks.TAwaitStatus;

/**
 *
 * @author Ben
 */
public class ClimbLogic extends LogicPhase {
    //// CONSTANTS -------------------------------------------------------------
    
    //// TASK LIST -------------------------------------------------------------
    
    private Vector _tasks;
    private int _currentIndex = 0;
    private LogicTask _currentTask;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public ClimbLogic(){
	super();
    }

    //// INITIALIZATION --------------------------------------------------------
    
    public void initPhase() {
	// Populate Tasks Array
	_tasks = new Vector();
	_tasks.addElement(new TAwaitStatus(TAwaitStatus.WINCH_IN_POSITION, 0));
	
	// Begin First Task
	_currentIndex = 0;
	setCurrentTask((LogicTask)_tasks.elementAt(_currentIndex));
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void updatePhase() {
	// Check for Emergency Stop (START and BACK on primary joystick)
	if(RobotMain.primaryJoystick.getRawButton(FancyJoystick.BUTTON_START)
	&& RobotMain.primaryJoystick.getRawButton(FancyJoystick.BUTTON_START))
	{
	    emergencyStop();
	}
	
	// Update Current Task
	_currentTask.updateTask();
	
	if(_currentTask.isDone()){
	    nextTask();
	}
    }

    //// FINISH ----------------------------------------------------------------
    
    public void finishPhase() {
	_currentTask.finishTask();
	_currentTask = null;
	RobotMain.getInstance().segueToLogicPhase(LogicPhase.TELEOP);
    }
    
    public void emergencyStop(){
	finishPhase();  // TODO:  Additional Logic Here?
    }
    
    //// TASK LOGIC ------------------------------------------------------------
    
    public void nextTask(){
	setCurrentTask(_currentTask);
    }
    
    public void setCurrentTask(LogicTask newTask){
	if(_currentTask != null){
	    int status = _currentTask.finishTask();
	}
	
	_currentTask = newTask;
	_currentTask.initializeTask();
    }
    
}
