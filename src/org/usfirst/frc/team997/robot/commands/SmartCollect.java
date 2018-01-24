package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SmartCollect extends Command {
	
	double leftv,rightv;
	double rightspeed, leftspeed;
	double error;
	AnalogInput leftinput = new AnalogInput(RobotMap.Ports.leftCollectorSensorInput);
	AnalogInput rightinput = new AnalogInput(RobotMap.Ports.rightCollectorSensorInput);
	

    public SmartCollect() {
    	requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//GET THE READINGS FROM THE SENSORS
    	leftv = leftinput.getAverageVoltage();
    	rightv = rightinput.getAverageVoltage();
    	
    	System.out.println("leftv: " + leftv);
    	System.out.println("rightv: " + rightv);
    	//PUT VALUES THROUGH DEADBAND, FILTERS, ETC. 
    	
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

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (error == 0);
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
