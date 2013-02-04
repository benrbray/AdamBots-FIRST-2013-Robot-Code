package robot.behavior;

/**
 * Contains methods that should be implemented by each of the robot's behaviors.
 * @author Steven
 */
public abstract class RobotBehavior {
    
    /**
     * Called when the Behavior is created.
     */
    public abstract void init();
    
    /**
     * Called periodically by RobotMain.
     */
    public abstract void update();
    
    /**
     * Called when we want all the behaviors to shut down.
     */
    public abstract void finish();
}
