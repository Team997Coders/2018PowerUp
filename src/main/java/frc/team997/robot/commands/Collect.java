package frc.team997.robot.commands;

import frc.team997.robot.Robot;
import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class Collect extends Command {

	double leftspeed, rightspeed;
	
	
    public Collect(double _leftspeed, double _rightspeed) {
    	requires(Robot.collector);
    	
    	leftspeed = _leftspeed;
    	rightspeed = _rightspeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.collector.gotCube = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.collector.collect(leftspeed, rightspeed);
    	if (Robot.collector.getAvgLeftVoltage() > 1.5 || Robot.collector.getAvgRightVoltage() > 1.5) {
    		Robot.collector.gotCube = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.collector.gotCube;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.collector.collect(0,0);
    	if (Robot.collector.gotCube) {
    		Scheduler.getInstance().add(new ElevatorToHeight((RobotMap.Values.elevatorSafeDriveHeight)));
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
