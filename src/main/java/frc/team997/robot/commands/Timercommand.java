package frc.team997.robot.commands;

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
    	timeLimit = _timeLimit;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	System.out.println("Timer command initialized to " + timeLimit + " sec");
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
        	System.out.println("...Timer command finished.");
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
