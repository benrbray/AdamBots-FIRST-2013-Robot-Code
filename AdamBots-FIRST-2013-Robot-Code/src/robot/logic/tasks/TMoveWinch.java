/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotClimb;
import robot.logic.LogicTask;

/**
 *
 * @author Ben
 */
public class TMoveWinch extends LogicTask {
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private double _targetEncoderValue;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
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