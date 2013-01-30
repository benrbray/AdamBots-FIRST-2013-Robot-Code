/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import robot.sensors.FancyCounter;

/**
 *
 * @author Tyler
 */
public class PIDLogic {
    //// VARIABLES -------------------------------------------------------------
    // Victor instances
    private Victor _driveMotors;
    
    // PID constants
    private double _Ki = 0.0;
    private double _Kp = 0.0;
    private double _Kd = 0.0;
    
    public final double PID_TOLERANCE = 5.0;
    public final double MAX_INPUT = 10000;
    public final double MIN_INPUT = 0;
    public final double MAX_OUTPUT = 1.0;
    public final double MIN_OUTPUT = -1.0;
    public final int    PID_BUFFER = 5;
    
    // Buffer variables
    private int _targetBuffer;
    
    // Timer Variables
    //private Timer _timer = new Timer();
    
    // Port Constants
    //public final int COUNTER_PORT = 1;
    
    // Counter instances
    private FancyCounter _driveCounter;
    
    // PIDController instances
    private PIDController _pid;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    public PIDLogic(Victor driveMotor, FancyCounter driveCounter, double Ki, double Kp, double Kd) {
	pidsInit();
	_Ki = Ki;
	_Kp = Kp;
	_Kd = Kd;
	_driveMotors = driveMotor;
	_driveCounter = driveCounter;
	_targetBuffer = 0;
    }
    
    //// INIT METHOD -----------------------------------------------------------
    public void pidsInit() {
	//// INITIALIZING ------------------------------------------------------
	//_driveCounter = new FancyCounter(COUNTER_PORT);
	_pid = new PIDController(_Kp, _Ki, _Kd, _driveCounter, _driveMotors);
	
	//// STARTING ----------------------------------------------------------
	_driveCounter.start();
	_driveCounter.setMaxPeriod(100000);
	_driveCounter.setUpSourceEdge(true, true);
	_pid.setInputRange(MIN_INPUT, MAX_INPUT);
	_pid.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
	_pid.setPercentTolerance(PID_TOLERANCE);
	_pid.enable();
	//_timer.start();
    }
    
    //// PID METHODS -----------------------------------------------------------
    // sets the RPM
    public void setRPM(double targetRPM) {
	System.out.println("SETTING RPM");
	
	// enables the PID if it isn;t enabled
	if (!_pid.isEnable()) {
	    _pid.enable();
	}
	
	// sets the set point
	if (_pid.getSetpoint() != targetRPM) {
	    _pid.setSetpoint(targetRPM);
	}
	//System.out.println(timer.get() + "\t" + topPid.get() + "\t" + _topGear.pidGet() + "\t" + bottomPid.get() + "\t" + _bottomGear.pidGet() + "\t" + shooterToSpeed());
    }
    
    // checks if it's to speed
    public boolean toSpeed() {
	// checks if the PID is on target
	if (_pid.onTarget()) {
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

