/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.logic.LogicTask;

/**
 *
 * @author Ben
 */
public class TExpandWinch extends LogicTask {
    
    
    private double targetEncoderValue;
    private boolean _requireConfirmation;
    
    
    public TExpandWinch(double targetEncoderValue, boolean requireOperatorConfirmation){
	
    }

    
    
    public void initializeTask() {
	
    }

    public void updateTask() {
	
    }

    public int finishTask() {
	return SUCCESS;
    }
    
    
}
