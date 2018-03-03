package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Uncollect extends Command {

	double leftspeed, rightspeed;
	
    public Uncollect(double _leftspeed, double _rightspeed) {
    	requires(Robot.collector);
    	
    	leftspeed = _leftspeed;
    	rightspeed = _rightspeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.collector.collect(leftspeed, rightspeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.m_oi.uncollectButton.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.collector.gotCube = false;
    	Robot.collector.collect(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
