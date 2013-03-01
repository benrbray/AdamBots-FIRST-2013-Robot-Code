/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.auton;

import edu.wpi.first.wpilibj.networktables2.util.List;
import robot.RobotMain;
import robot.control.MagicBox;
import robot.logic.LogicPhase;
import robot.logic.LogicTask;
import robot.logic.tasks.TAwaitStatus;
import robot.logic.tasks.TFeedDisc;
import robot.logic.tasks.TSetShooterSpeed;

/**
 * Performs logic during the autonomous period of gameplay.
 * @author Ben
 */
public class AutonLogic extends LogicPhase {
    //// TASK LIST -------------------------------------------------------------
    
    protected List _tasks;
    protected int _currentIndex = 0;
    protected LogicTask _currentTask;
	
	protected boolean _done = false;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public AutonLogic(){
		super();
    }
    
    //// INITIALIZATION --------------------------------------------------------
    
	/**
	 * Called before the autonomous phase begins.  Populates the task list and
	 * calls the first task in the sequence.
	 */
    public final void initPhase() {
		// Populate Tasks Array
		_tasks = AutonType.Simple.SIMPLE_THREE_SHOTS;

		// Begin First Task
		_currentIndex = 0;
		setCurrentTask((LogicTask)_tasks.get(_currentIndex));
    }

    //// UPDATE ----------------------------------------------------------------
    
	/**
	 * Called periodically during the autonomous phase.  Performs central logic
	 * and controls task flow.
	 */
    public final void updatePhase() {
		if(_done) return;
		
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
    public final void finishPhase() {
		_currentTask.finishTask();
		_currentTask = null;
		RobotMain.getInstance().segueToLogicPhase(LogicPhase.TELEOP);
    }
    
    //// TASK LOGIC ------------------------------------------------------------
    
    /**
     * Transitions to the next Task in the sequence.
     * @see #setCurrentTask(robot.logic.LogicTask) 
     */
    public final void nextTask(){
		if(_currentIndex < _tasks.size() - 1){
			setCurrentTask((LogicTask)_tasks.get(++_currentIndex));
		} else {
			println("AutonLogic finished.  No more tasks left to run.");
			_done = true;
		}
    }
    
    /**
     * Transitions to the Task specified by first finishing the old Task and
     * then initializing the new Task.
     * @param newTask The Task to transition to.
     * @see LogicTask#finishTask() 
     * @see LogicTask#initializeTask() 
     */
    public final void setCurrentTask(LogicTask newTask){
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
