/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.auton;

import java.util.Vector;
import robot.RobotMain;
import robot.control.FancyJoystick;
import robot.logic.LogicPhase;
import robot.logic.LogicTask;
import robot.logic.tasks.TAwaitStatus;
import robot.logic.tasks.TMoveWinch;
import robot.logic.tasks.TStopWinch;

/**
 * Performs logic during the autonomous period of gameplay.
 * @author Ben
 */
public class AutonLogic extends LogicPhase {
    //// TASK LIST -------------------------------------------------------------
    
    private Vector _tasks;
    private int _currentIndex = 0;
    private LogicTask _currentTask;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public AutonLogic(){
		super();
    }
    
    //// INITIALIZATION --------------------------------------------------------
    
    public void initPhase() {
		// Populate Tasks Array
		_tasks = new Vector();

		// Begin First Task
		_currentIndex = 0;
		setCurrentTask((LogicTask)_tasks.elementAt(_currentIndex));
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void updatePhase() {
		// Check for Emergency Stop (START and BACK on primary joystick)
		if(RobotMain.primaryJoystick.getRawButton(FancyJoystick.BUTTON_START)
		&& RobotMain.primaryJoystick.getRawButton(FancyJoystick.BUTTON_START)){
			emergencyStop();
		}

		// Update Current Task
		_currentTask.updateTask();

		if(_currentTask.isDone()){
			println("Task Reported DONE, moving to next Task...");
			nextTask();
		}
    }

    //// FINISH ----------------------------------------------------------------
    
    /**
     * Stops the current Task and transitions to the TeleopLogic Phase.
     */
    public void finishPhase() {
		_currentTask.finishTask();
		_currentTask = null;
		RobotMain.getInstance().segueToLogicPhase(LogicPhase.TELEOP);
    }
    
    /**
     * Emergency stops Climbing.
     * @see #finishPhase()
     */
    public void emergencyStop(){
		finishPhase();  // TODO:  Additional Logic Here?
    }
    
    //// TASK LOGIC ------------------------------------------------------------
    
    /**
     * Transitions to the next Task in the sequence.
     * @see #setCurrentTask(robot.logic.LogicTask) 
     */
    public void nextTask(){
		setCurrentTask((LogicTask)_tasks.elementAt(++_currentIndex));
    }
    
    /**
     * Transitions to the Task specified by first finishing the old Task and
     * then initializing the new Task.
     * @param newTask The Task to transition to.
     * @see LogicTask#finishTask() 
     * @see LogicTask#initializeTask() 
     */
    public void setCurrentTask(LogicTask newTask){
		// Finish Old Task
		if(_currentTask != null){
			int status = _currentTask.finishTask();
			if(status == LogicTask.SUCCESS){
				println("Task Finished Successfully.");
			} else {
				println("Task Failed.");
			}
		}
		
		// Begin New Task
		_currentTask = newTask;
		_currentTask.initializeTask();
    }
}