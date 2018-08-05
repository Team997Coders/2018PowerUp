package frc.team997.robot.commands;

import frc.team997.robot.Robot;
import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SmartCollect extends Command {
	
	double leftv,rightv;
	double rightspeed, leftspeed;
	double error;
	
	boolean gotCube = false;

    public SmartCollect() {
    	requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//GET THE READINGS FROM THE SENSORS
    	leftv = Robot.collector.getAvgLeftVoltage();
    	rightv = Robot.collector.getAvgRightVoltage();
    	
    	System.out.println("leftv: " + leftv);
    	System.out.println("rightv: " + rightv);
    	
    	if(leftv > 2.5 || rightv > 2.5) {
    		gotCube = true;
    	} else {
    		//PUT VALUES THROUGH DEADBAND, FILTERS, ETC. 
    			//TODO: filter values + convert to distances
    	
    		//COMPARE THE LEFT AND RIGHT SIDES
    			//TODO: find way to use error to calculate speed
    		error = leftv - rightv;
    		if (error > 0) {
    			//this means right side is closer, so left side speeds up
    			Robot.collector.collect(RobotMap.Values.fastcollectspeed, RobotMap.Values.collectspeed);
    		
    		} else if (error < 0) {
    			//this means left side is closer, so right side speeds up
    			Robot.collector.collect(RobotMap.Values.collectspeed, RobotMap.Values.fastcollectspeed);
    		
    		} else {
    			//this means both sides are basically equal, so both sides same speed
    			Robot.collector.collect(RobotMap.Values.collectspeed, RobotMap.Values.collectspeed);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (error == 0) || gotCube;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.collector.collect(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
