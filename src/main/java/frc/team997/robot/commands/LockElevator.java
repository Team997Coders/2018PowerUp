package frc.team997.robot.commands;

import frc.team997.robot.Robot;

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
	// CCB: So why would you want to finish this command if the PID closed loop error approaches zero?
	// Shouldn't you just keep executing until button is released or possibly if the elevator
	// is at the zero position?
    	//double closedLoopError = Robot.elevator.getError();
    	//return /*!Robot.elevator.isZeroed ||*/ (Math.abs(closedLoopError) < 60);
    	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
