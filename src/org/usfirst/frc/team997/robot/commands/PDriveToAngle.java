package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PDriveToAngle extends Command {
	private double angSetpoint;
	private double minError = 10;
	private double initYaw = -999; 
	private double Ktheta = 0.016; 

    public PDriveToAngle(double _ang) {
    	requires(Robot.drivetrain);
    	angSetpoint = _ang;
    }

    protected void initialize() {
    	initYaw = Robot.drivetrain.getAHRSAngle();
    	Robot.drivetrain.setBrake();
    	System.out.println("PDriveAngle - Init PAngle" + initYaw);
    }

    protected void execute() {
    	// calculate yaw correction
    	double yawcorrect = piderror() * Ktheta;
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
