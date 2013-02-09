package robot.logic;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Tyler
 */
public class FancyPIDController extends PIDController {
    
    // Buffer variables, keep track of # of iterations on target
    private int _targetBuffer = 0;
    
    // # of iterations of good readings where target is considered met
    public final int    PID_BUFFER = 5;
    

    //// CONSTRUCTOR -----------------------------------------------------------
    public FancyPIDController( double Kp, double Ki, double Kd, PIDSource source, PIDOutput output ) {
        super( Kp, Ki, Kd, source, output );     
    }
    
    //// PID METHODS -----------------------------------------------------------
    // sets the RPM
    public void setRPM(double targetRPM) {
	System.out.println("SETTING RPM");
	
	// enables the PID if it isn't enabled
	if (!super.isEnable()) {
	    super.enable();
	}
	
	// sets the set point (Micklas - need to look at PIDController 
        // source, even though this looks unnecessary I think we needed it
        // because setSetpoint has some additional logic that resets
        // accumulations?  or something like that
	if (super.getSetpoint() != targetRPM) {
	    super.setSetpoint(targetRPM);
	}
       // debug in case we need to tune PIDs
       // System.out.println(timer.get() + "\t" + topPid.get() + "\t" + _topGear.pidGet() + "\t" + bottomPid.get() + "\t" + _bottomGear.pidGet() + "\t" + shooterToSpeed());
    }
    
    // checks if it's to speed
    public boolean isAtSpeed() {
	// checks if the PID is on target
	if (super.onTarget()) {
	    if (_targetBuffer <= PID_BUFFER) {
		_targetBuffer++;
	    }
	} else {
	    _targetBuffer = 0;
	}
	
	// checks how long the _pid had been on target
	if (_targetBuffer > PID_BUFFER) {
	    return true;
	} else {
	    return false;
	}
    }
}

/*
// logic to move to sensor initialization
RobotSensors.counterShooterSpeed.start();
RobotSensors.counterShooterSpeed.setMaxPeriod(100000);
RobotSensors.counterShooterSpeed.setUpSourceEdge(true, false);

/**************** SHOOTER CLASS LOGIC ***************//*
// logic in shooter class to construct and initialize
double SHOOTER_KI = 0.001;
double SHOOTER_KP = 0.002;
double SHOOTER_KD = 0.000;
double SHOOTER_PID_TOLERANCE = 0.15;
double SHOOTER_MAX_INPUT = 10000;
double SHOOTER_MIN_INPUT = 0;
double SHOOTER_MAX_OUTPUT = 1.0;
double SHOOTER_MIN_OUTPUT = 0.0;

FancyPIDController shooterPID = new FancyPIDController(
    shooterKi, shooterKp, shooterKd, 
    RobotSensors.counterShooterSpeed, RobotActuators.shooterWheelMotor )

shooterPID.setInputRange(SHOOTER_MIN_INPUT, SHOOTER_MAX_INPUT);
shooterPID.setOutputRange(SHOOTER_MIN_OUTPUT, SHOOTER_MAX_OUTPUT);
shooterPID.setPercentTolerance(SHOOTER_PID_TOLERANCE);
shooterPID.enable();

// to set speed to desired RPM
shooterPID.setRPM();

// to check speed to determine if ready to shoot
shooterPID.isAtSpeed();
/**************** END SHOOTER CLASS LOGIC ***************/


