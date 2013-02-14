/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.logic.LogicTask;
import robot.logic.TargetLogic;

/**
 *
 * @author Blue
 */
public class TTarget extends LogicTask {
    public int finish()
    {
        return _done ? SUCCESS : FAILURE;
    }
    
    public void update()
    {
        _done = true;
    }
    
    public void initialize()
    {
        TargetLogic.beginTargeting();
    }
}
