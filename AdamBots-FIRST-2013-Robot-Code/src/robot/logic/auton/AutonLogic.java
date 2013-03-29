/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.auton;

import edu.wpi.first.wpilibj.networktables2.util.List;
import java.util.Vector;
import robot.RobotMain;
import robot.control.FancyJoystick;
import robot.logic.LogicPhase;
import robot.logic.LogicTask;
import robot.logic.targeting.TargetShooterAngleLogic;
import robot.logic.tasks.TDriveMillis;
import robot.logic.tasks.TShiftGear;
import robot.sensors.RobotSensors;

/**
 * Performs logic during the autonomous period of gameplay. LEFT(23.1deg, 23.8)
 * @author Ben
 */
public class AutonLogic extends LogicPhase {
    //// TASK LIST -------------------------------------------------------------
    
    private List _tasks;
    private int _currentIndex = 0;
    private LogicTask _currentTask;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public AutonLogic(){
		super();
    }
    
    //// INITIALIZATION --------------------------------------------------------
    
	/**
	 * Initialize Autonomous.  Determine initial delay based on config switches,
	 * choose which AutonType we should be in, and start the task sequence.
	 */
    public void initPhase() {
		println("AutonLogic :: initPhase()");
		
		// Initial Delay (Switches B & C)
		// TODO:  Switches might be inverted...
		int switchMode = ((RobotSensors.configB.get()?1:0)<<1) | ((RobotSensors.configC.get()?1:0));
		int initialDelayMillis = (2 + switchMode) * 1000;
		
		println("\tSetting Auton Initial Delay to:  " + initialDelayMillis);
		
		// Determine Task Array
		_tasks = AutonType.Fancy.shootDiscs(4, initialDelayMillis);
		_tasks.add(new TShiftGear(TShiftGear.HIGH_GEAR));
		_tasks.add(new TDriveMillis(1000, 0.7, 0.7));

		// Begin First Task
		_currentIndex = 0;
		setCurrentTask((LogicTask)_tasks.get(_currentIndex));
    }

    //// UPDATE ----------------------------------------------------------------
    
	/**
	 * Manages task flow.  Should be called periodically while AutonLogic is
	 * active.
	 */
    public void updatePhase() {
		// Update Current Task
		_currentTask.updateTask();
		
		if(_currentTask.isDone()){
			println("AutonLogic :: Task Reported DONE, moving on...");
			nextTask();
		}
    }

    //// FINISH ----------------------------------------------------------------
    
    /**
     * Stops the current Task and any ongoing processes.  Not responsible for
	 * the segue to TeleopLogic.
     */
    public void finishPhase() {
		_currentTask.finishTask();
		_currentTask = null;
    }
    
    //// TASK LOGIC ------------------------------------------------------------
    
    /**
     * Transitions to the next Task in the sequence.
     * @see #setCurrentTask(robot.logic.LogicTask) 
     */
    public void nextTask(){
		if(_currentIndex < _tasks.size() - 1){
			setCurrentTask((LogicTask)_tasks.get(++_currentIndex));
		} else {
			println("AutonLogic :: No Tasks Remain.  Finishing...");
			RobotMain.getInstance().endPhase();
		}
    }
    
    /**
     * Transitions to the Task specified by first finishing the old Task and
     * then initializing the new Task.
     * @param newTask The Task to transition to.
     * @see LogicTask#finishTask() 
     * @see LogicTask#initializeTask() 
     */
    public void setCurrentTask(LogicTask newTask){
		println("AutonLogic :: setCurrentTask() : " + newTask.getClass().getName());
		// Finish Old Task
		if(_currentTask != null){
			int status = _currentTask.finishTask();
			if(status == LogicTask.SUCCESS){
				println("\tTask Finished Successfully.");
			} else {
				println("\tTask Failed.");
			}
		}
		
		// Begin New Task
		println("\tInitializing New Task...");
		_currentTask = newTask;
		_currentTask.initializeTask();
    }
}