package frc.team997.robot.commands;

import frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 Sets the elevator to a specific position.
 */
public class ElevatorToHeight extends Command {

	private final double height; //get high

    public ElevatorToHeight(double height) {
        // Use requires() here to declare subsystem dependencies
    	this.height = height;
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("ElevatorToHeight Initialized");
    	if (!Robot.elevator.isZeroed) {
    		System.out.println("Not zeroed, so won't run!");
    		end();
    	}

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.elevator.setPosition(height);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double closedLoopError = Robot.elevator.getError();
    	return !Robot.elevator.isZeroed || (Math.abs(closedLoopError) < 100);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Elevator To Height Ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//end();
    }
}
