package frc.team997.robot.commands;

import frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimedUncollect extends Command {
	
	private Timer timer = new Timer();
	private double timelimit;
	private double rightspeed;
	private double leftspeed;
	
    public TimedUncollect(double _leftspeed, double _rightspeed, double _timelimit) {
    	
    	leftspeed = _leftspeed;
    	rightspeed = _rightspeed;
    	 timelimit = _timelimit;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.collector.collect(leftspeed, rightspeed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timer.get() > timelimit);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.collector.collect(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
