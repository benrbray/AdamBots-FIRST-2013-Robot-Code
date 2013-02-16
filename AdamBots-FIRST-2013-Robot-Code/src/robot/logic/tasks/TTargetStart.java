/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.logic.LogicTask;
import robot.logic.targeting.TargetShooterAngleLogic;
import robot.logic.targeting.TargetShooterSpeedLogic;
import robot.logic.targeting.TargetSpinLogic;

/**
 * Begins targeting.  
 * @author Blue
 */
public class TTargetStart extends LogicTask {
    
    //// INITIALIZE ------------------------------------------------------------
    
    public void initialize()
    {
        TargetShooterSpeedLogic.setIsTargeting(true);
		TargetShooterAngleLogic.setIsTargeting(true);
		TargetSpinLogic.setIsTargeting(true);
    }
    
    //// UPDATE ----------------------------------------------------------------
    
    public void update()
    {
        _done = true;
    }
    
    //// FINISH ----------------------------------------------------------------
    
    public int finish()
    {
        return _done ? SUCCESS : FAILURE;
    }
}
