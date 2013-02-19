/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.sensors;

import edu.wpi.first.wpilibj.SafePWM;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Tyler
 */
public class FancyCounterExtended {
    
    public FancyCounter fancy;
    private SpeedController motor;
    private int count;
    private int currentCount;
    private int direction;
    private double distancePerTick;
    
    public FancyCounterExtended(FancyCounter f, SpeedController v) {
	fancy = f;
	motor = v;
	count = 0;
	currentCount = 0;
	direction = 0;
	f.start();
    }
	
	public void reset()
	{
		fancy.reset();
		count = 0;
	}
    
    public void set(double speed) {
	count += fancy.get() * direction;
	motor.set(speed);
	direction = getDirection();
	fancy.reset();
    }
    
    private int getDirection() {
	if (motor.get() < 0) {
	    return -1;
	} else if (motor.get() > 0) {
	    return 1;
	} else {
	    return 0;
	}
    }
    
    public int get() {
	return count + fancy.get() * direction;
    }
    
    public void setDistancePerTick(double distance) {
	distancePerTick = distance;
    }
	
	public void setMaxPeriod(int period)
	{
		fancy.setMaxPeriod(period);
	}
	
	public void setUpSourceEdge(boolean x,boolean y) {
		fancy.setUpSourceEdge(x, y);
	}
    
    public double getDistance() {
	return (double) get() * distancePerTick;
    }
}
