package frc.team997.robot.commands;

import frc.team997.robot.Robot;
import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PDriveToAngle extends Command {
	private double angSetpoint;
	private double minError = 2;
	private double initYaw = -999; 
	private double integral = 0;
	private double previousError = 0;

    public PDriveToAngle(double _ang) {
    	requires(Robot.drivetrain);
    	angSetpoint = _ang;
    }

    protected void initialize() {
    	initYaw = Robot.drivetrain.getAHRSAngle();
    	Robot.drivetrain.setBrake();
    	System.out.println("PDriveAngle - Init PAngle" + initYaw);
    }
    
    public double yawCorrect() {
    	// Calculate full PID
    	// pfactor = (P × error) + (I × ∑error) + (D × δerrorδt)
    	double error = this.piderror();
    	// Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
    	this.integral += (error * .02);
    	// Derivative is change in error over time
    	double derivative = (error - this.previousError) / .02;
        this.previousError = error;
        return 
        	(RobotMap.Values.driveAngleP * error) + 
        	(RobotMap.Values.driveAngleI * this.integral) + 
        	(RobotMap.Values.driveAngleD * derivative);
    }

    protected void execute() {
    	// calculate yaw correction
    	double yawcorrect = this.yawCorrect();
    	Robot.drivetrain.setVoltages(Robot.clamp(yawcorrect, -1, 1), Robot.clamp(-yawcorrect, -1, 1)); 
    	// Debug information to be placed on the smart dashboard.
    	SmartDashboard.putNumber("PDriveToAngle: Angle Error", piderror());
    	SmartDashboard.putNumber("PDriveToAngle: Theta Angle Correction", yawcorrect);
    	SmartDashboard.putBoolean("PDriveToAngle: On Angle Target", onTarget());
    	SmartDashboard.putNumber("PDriveToAngle: Init Angle Yaw", initYaw);
    }

    private double piderror() {
    	return initYaw + angSetpoint - Robot.drivetrain.getAHRSAngle();
    }
    
    private boolean onTarget() {
    	return Math.abs(piderror()) < minError;
    }

    protected boolean isFinished() {
        return onTarget();   
    }

    protected void end() {
    	Robot.drivetrain.setVoltages(0, 0);
    	System.out.println("PDriveAngle - PAngle End");
    }

    protected void interrupted() {
    	end();
    }
}
