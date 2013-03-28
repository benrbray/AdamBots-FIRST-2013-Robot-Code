/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import edu.wpi.first.wpilibj.Timer;
import robot.behavior.RobotDrive;
import robot.logic.LogicTask;
import robot.sensors.RobotSensors;
import utils.MathUtils;

/**
 *
 * @author Ben Bray
 */
public class TDriveMillis extends LogicTask {
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private long _initialTimeMillis;
	private int _driveMillis;
	
	private double _driveLeftSpeed;
	private double _driveRightSpeed;
	
    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * Drives the Robot at a specified constant speed and direction for a
	 * specified period of time.
     */
    public TDriveMillis(int millis, double rightSpeed, double leftSpeed) {
        _driveMillis = millis;
		_driveLeftSpeed = leftSpeed;
		_driveRightSpeed = rightSpeed;
    }

    //// INITIALIZE ------------------------------------------------------------
    
    public void initialize() {
        _initialTimeMillis = System.currentTimeMillis();
		RobotDrive.drive(_driveLeftSpeed, _driveRightSpeed);
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void update() {
        if(System.currentTimeMillis() - _initialTimeMillis >= _driveMillis){
			_done = true;
			RobotDrive.stop();
		}
    }

    //// FINISH ----------------------------------------------------------------
    
    public int finish() {
		RobotDrive.stop();
        return _done ? SUCCESS : FAILURE;
    }
}
