/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.climb;

import java.util.Vector;
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
	LogicTask firstTask = (LogicTask) _tasks.elementAt(0);
	firstTask.initializeTask();
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void updatePhase() {
	// Update Current Task
	_currentTask.updateTask();
	
	if(_currentTask.isDone()){
	    nextTask();
	}
    }

    //// FINISH ----------------------------------------------------------------
    
    public void finishPhase() {
	
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
