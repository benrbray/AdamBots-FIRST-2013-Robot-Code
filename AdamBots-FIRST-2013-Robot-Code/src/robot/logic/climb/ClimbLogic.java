/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.climb;

import java.util.Vector;
import robot.logic.LogicPhase;
import robot.logic.LogicTask;

/**
 *
 * @author Ben
 */
public class ClimbLogic extends LogicPhase {
    //// CONSTANTS -------------------------------------------------------------
    
    //// TASK LIST -------------------------------------------------------------
    
    private Vector _tasks;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public ClimbLogic(){
	super();
    }

    //// INITIALIZATION --------------------------------------------------------
    
    public void initPhase() {
	// Populate Tasks Array
	_tasks = new Vector();
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void updatePhase() {
	
    }

    //// FINISH ----------------------------------------------------------------
    
    public void finishPhase() {
	
    }
    
}
