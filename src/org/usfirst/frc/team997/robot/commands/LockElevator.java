package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LockElevator extends Command {

	public double position;
	
    public LockElevator() {
    	requires(Robot.elevator);
    }
    
    protected void initialize() {
    	position = Robot.elevator.getPosition();
    }
    
    protected void execute() {
    	Robot.elevator.setPosition(position);
    }

    protected boolean isFinished() {
    	double closedLoopError = Robot.elevator.getError();
    	return !Robot.elevator.isZeroed || (Math.abs(closedLoopError) < 60);
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
