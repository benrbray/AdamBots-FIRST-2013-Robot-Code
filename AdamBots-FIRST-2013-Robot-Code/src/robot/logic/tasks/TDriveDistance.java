/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import edu.wpi.first.wpilibj.Timer;
import robot.behavior.RobotDrive;
import robot.logic.LogicTask;
import robot.sensors.RobotSensors;
import utils.MathUtils;

/**
 *
 * @author Curtis Fenner
 */
public class TDriveDistance extends LogicTask {

    double _targetDistanceInches;
    double _initialEncoder;
    boolean _iFailed = false;
    Timer _timer = new Timer();
    //TODO: VERIFY THAT THE ENCODERS BOTH GO FORWARD WHEN THE ROBOT GOES FORWARD
    //OTHERWISE THIS IS USELESS.

    /**
     *
     * @param distanceinches Determines the distance that it will go forward.
     * Completely unintelligent by the way, it will not fail even if it gets
     * stuck
     */
    TDriveDistance(double distanceinches) {
        _targetDistanceInches = distanceinches;
        //TODO: Insure that encoder s on wheels have accurate real world distance from get distance.
        _timer.start();
    }

    public void initialize() {
        _initialEncoder = -(RobotSensors.encoderDriveLeft.getDistance() + RobotSensors.encoderDriveRight.getDistance()) / 2.0;
        RobotSensors.encoderDriveLeft.start();
        RobotSensors.encoderDriveRight.start();
    }

    public void update() {
        RobotDrive.driveStraight(MathUtils.sign(_targetDistanceInches) * 0.1);
        double encodertarget = _initialEncoder + _targetDistanceInches - MathUtils.sign(_targetDistanceInches);
        double currentencoder = -(RobotSensors.encoderDriveLeft.getDistance() + RobotSensors.encoderDriveRight.getDistance()) / 2.0;
        if ((int) MathUtils.sign(encodertarget - currentencoder) != (int) MathUtils.sign(_targetDistanceInches)) {
            RobotDrive.drive(0, 0); //Stop the robot
            _done = true;
        } else if (_timer.get() - 0.5 > Math.abs(_targetDistanceInches) / 5.0) {
            _done = true;
            _iFailed = true;
        }
    }

    public int finish() {
        if (_iFailed) {
            return FAILURE;
        } else {
            return SUCCESS;
        }
    }
}
