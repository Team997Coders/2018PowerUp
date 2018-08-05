package frc.team997.robot.commands;

import frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Flop extends Command {
	
	int _state;
	
    public Flop() {
    	requires(Robot.elevator);
    }
    
    public Flop(int state) {
    	requires(Robot.elevator);
    	_state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.elevator.flop == 1) {
    		Robot.elevator.flopUp();
    	} else if (Robot.elevator.flop == 0){
    		Robot.elevator.flopDown();
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
