package frc.team997.robot.commands;



import frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SlowForwardUntilHaveCube extends Command {
	private Timer timer;
	private double timeout;
	
    public SlowForwardUntilHaveCube(double timeout) {
    	requires(Robot.drivetrain);
    	this.timer = new Timer();
    	this.timeout = timeout;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("SlowForwardUntilCube Initialized");
    	// CCB: Reset and start the timer...it will never timeout otherwise!  Noticed upon code review.
    	this.timer.reset();
    	this.timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.setVoltages(0.4, 0.4);    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// CCB: Stop if we have the cube OR we timed out
    	if (this.timer.get() >= this.timeout || Robot.collector.gotCube == true) {
    		return true;
    	} else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	// CCB: Stop the timer if we end
    	this.timer.stop();
    	Robot.drivetrain.setVoltages(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.timer.stop();
    }
}
