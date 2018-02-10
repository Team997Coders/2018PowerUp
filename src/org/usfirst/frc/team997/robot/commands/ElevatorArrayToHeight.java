package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ElevatorArrayToHeight extends Command {

	private double height; 

    public ElevatorArrayToHeight() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	height = Robot.elevator.getHeightFromArray();
    	Robot.elevator.setPosition(height);
    	System.out.println("setting elevator to height " + height);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double closedLoopError = Robot.elevator.getError();
    	return !Robot.elevator.isZeroed || (Math.abs(closedLoopError) < 60);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
