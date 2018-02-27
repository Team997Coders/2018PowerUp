package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Timercommand extends Command {
	//About 0.3 seconds per foot at HALF speed.

	public Timer timer = new Timer();
	public double time;
	public double timeLimit;
    public Timercommand(double _timeLimit) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timeLimit = _timeLimit;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(timer.get() < timeLimit) {
        	return false;
        }
        else {
        	return true;
        }
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
