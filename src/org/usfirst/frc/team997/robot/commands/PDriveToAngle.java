package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team997.robot.utils;

/**
 *
 */
public class PDriveToAngle extends Command {
	private double angSetpoint;
	private double minError = 12;
	private double initYaw = -999; 
	private double Ktheta = 0.016;
	public Timer timer = new Timer();
	private double lastTime = -999;
	
    public PDriveToAngle(double _ang) {
    	requires(Robot.drivetrain);
    	angSetpoint = _ang;
    }

    protected void initialize() {
    	initYaw = Robot.drivetrain.getAHRSAngle();
    	Robot.drivetrain.setBrake();
    	timer.reset();
    	lastTime = 0;
    	timer.start();
    	System.out.println("PDriveAngle - Init PAngle" + initYaw);
    }

    protected void execute() {
    	// calculate yaw correction
    	double yawcorrect = piderror() * Ktheta;

    	// set power to the drive motors
    	Robot.drivetrain.setVoltages(utils.clamp(yawcorrect, -1, 1), utils.clamp(-yawcorrect, -1, 1)); 
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
