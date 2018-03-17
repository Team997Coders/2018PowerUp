package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FindTrackWidth extends Command {
	double tw = 0.0;
	double init_angle = 0.0;
	int count = 0;
	double rots = 0.0;

    public FindTrackWidth() {
    	requires(Robot.drivetrain);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	System.out.println("start find track width...");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	init_angle = Robot.drivetrain.getAHRSAngle();
    	Robot.drivetrain.setBrake();
    	Robot.drivetrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.setVoltages(1.0, -1.0);
    	rots = Math.abs(Robot.drivetrain.getAHRSAngle() - init_angle) / 360.0;
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (rots > 10) {
    		Robot.drivetrain.setVoltages(0, 0);
    		
    		// get average encoder distance in native encoder units - 4096 tics/rev
    		double ave_dist = (Math.abs(Robot.drivetrain.getLeftEncoderTicks()) + Math.abs(Robot.drivetrain.getRightEncoderTicks())) / 2.0;
    		ave_dist = ave_dist * RobotMap.Values.inchesPerTick / 12.0; // output should be in feet
    		tw = ave_dist / (rots * Math.PI);
    		
    		System.out.print("Robot moved " + ave_dist + " encoder tics in " + rots + " rotations.");
    		System.out.print("Robot effective track width is " + tw + " ft");
    		return true;
    	} else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.setVoltages(0, 0);
    	System.out.print("Spin interrupted after " + rots + " rotations.");
    }
}
