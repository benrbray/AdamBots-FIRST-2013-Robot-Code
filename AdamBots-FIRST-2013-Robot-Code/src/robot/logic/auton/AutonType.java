/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.auton;

import edu.wpi.first.wpilibj.networktables2.util.List;
import robot.control.MagicBox;
import robot.logic.tasks.TAwaitStatus;
import robot.logic.tasks.TDelay;
import robot.logic.tasks.TFeedDisc;
import robot.logic.tasks.TSetShooterSpeed;

/**
 *
 * @author Steven
 */
public class AutonType {
	//// INIT ------------------------------------------------------------------
	
	public static void init(){
		Fancy.init();
	}
	
	//// STATIC AUTONOMOUS TYPES -----------------------------------------------
	
	public static final class Simple {
		//// INITIALIZATION ----------------------------------------------------
		
		private static void init(){
			
		}
		
		//// SHOOT THREE DISCS -------------------------------------------------
		
		/**
		 * A simple autonomous program that shoots three discs.
		 * 
		 * <ol>
		 *	<li>Set shooter speed.</li>
		 *  <li>Shoot (x3).
		 *		<ul>
		 *			<li>Enable shooter feeder solenoid.
		 *			<li>Give the shooter feeder solenoid time to expand.
		 *			<li>Disable the shooter feeder solenoid.
		 *			<li>Wait for the shooter wheel to recover.
		 *		</ul>
		 *	<li>Shoot
		 *  <li>Shoot
		 * </ol>
		 * 
		 * @see Fancy#shootDiscs(int) 
		 */
		public static final List SIMPLE_THREE_SHOTS = Fancy.shootDiscs(3);
	}
	
	//// DYNAMIC AUTONOMOUS TYPES ----------------------------------------------
	
	public static final class Fancy {
		//// INITIALIZATION ----------------------------------------------------
		
		private static void init(){
			
		}
		
		//// FANCY SHOOT DISCS -------------------------------------------------
		
		/**
		 * Generates the task list for an Autonomous Phase that shoots a
		 * specified number of discs.  Remember that we can only hold four at 
		 * once, so it is silly to shoot more than four times in this mode,
		 * except, perhaps, to protect against flawed magazine design.
		 * 
		 * <ol>
		 *	<li>Set shooter speed.</li>
		 *  <li>Shoot X Times.
		 *		<ul>
		 *			<li>Enable shooter feeder solenoid.
		 *			<li>Give the shooter feeder solenoid time to expand.
		 *			<li>Disable the shooter feeder solenoid.
		 *			<li>Wait for the shooter wheel to recover.
		 *		</ul>
		 *	<li>Shoot
		 *  <li>Shoot
		 * </ol>
		 * 
		 * @param discs The number of discs to shoot.
		 * @return A list of LogicTasks.
		 * @see robot.logic.LogicTask
		 * @see Fancy#shootDiscs(int) 
		 */
		public static List shootDiscs(int discs){
			List tasks = new List();
			
			tasks.add(new TSetShooterSpeed(MagicBox.PYRAMID_SHOT_SPEED));
			tasks.add(new TDelay(4000));
		
			for(int i = 0; i < 3; i++){	// Shoot Three Times
				tasks.add(new TAwaitStatus(TAwaitStatus.SHOOTER_UP_TO_SPEED));
				tasks.add(new TFeedDisc());
			}
			
			return tasks;
		}
	}	
}
