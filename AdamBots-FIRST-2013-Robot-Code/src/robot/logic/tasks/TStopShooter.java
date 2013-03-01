/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

/**
 * Stops the shooter wheels by setting their target value to zero.
 * @author Ben
 */
public final class TStopShooter extends TSetShooterSpeed {
	
	public TStopShooter(){
		super(0.0);
	}
	
}
