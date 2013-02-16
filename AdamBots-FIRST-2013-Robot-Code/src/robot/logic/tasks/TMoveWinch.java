/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotClimb;
import robot.logic.LogicTask;

/**
 * Sets a target encoder value for the winch.
 * @author Ben
 */
public class TMoveWinch extends LogicTask {
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private double _targetEncoderValue;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * Sets a target encoder value for the winch.
     * @param targetEncoderValue 
     * @see robot.behavior.RobotClimb
     */
    public TMoveWinch(double targetEncoderValue){
		_targetEncoderValue = targetEncoderValue;
    }

    //// INITIALIZATION --------------------------------------------------------
    
    public void initialize() {
		RobotClimb.setWinchTarget(_targetEncoderValue);
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void update() {
		_done = true;
    }

    //// FINISH ----------------------------------------------------------------
    
    public int finish() {
		return _done ? SUCCESS : FAILURE;
    }
    
}