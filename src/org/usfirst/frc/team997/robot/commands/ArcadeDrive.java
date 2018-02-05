package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArcadeDrive extends Command {

	double initAngle;
	double deltaTheta;
	double correction;
	double kTheta = 0.02;
	
	double leftEncoderDist;
	double rightEncoderDist;
	
    public ArcadeDrive() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    	initAngle = Robot.drivetrain.getAHRSAngle();
    }

    protected void execute() {
    	leftEncoderDist = Robot.drivetrain.getLeftEncoderTicks()*RobotMap.Values.inchesPerTick;
    	rightEncoderDist = Robot.drivetrain.getRightEncoderTicks()*RobotMap.Values.inchesPerTick;
    	
    	//no correction
    	/*Robot.drivetrain.setVoltages(Robot.m_oi.getLeftY() + Robot.m_oi.getRightX(), 
				Robot.m_oi.getLeftY() - Robot.m_oi.getRightX());*/
    	
    	//simple correction
    	Robot.drivetrain.setVoltages((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX()), 
				(Robot.m_oi.getLeftY()*0.9) - (Robot.m_oi.getRightX()*0.9));
    	
    	//correction using encoders (unfinished)
    	/*if (Math.abs(Robot.m_oi.getRightX()) != 0) {
    		Robot.drivetrain.setVoltages(Robot.m_oi.getLeftY() + Robot.m_oi.getRightX(), 
					Robot.m_oi.getLeftY() - Robot.m_oi.getRightX());
    	}*/
    	
    	
    	//correction using gyro
    	/*if (Math.abs(Robot.m_oi.getRightX()) != 0) {
    		Robot.drivetrain.setVoltages(Robot.m_oi.getLeftY() + Robot.m_oi.getRightX(), 
					Robot.m_oi.getLeftY() - Robot.m_oi.getRightX());
    		initAngle = Robot.drivetrain.getAHRSAngle();
    		
    	} else {
    		
    		deltaTheta = Robot.drivetrain.getAHRSAngle() - initAngle;
    		correction = deltaTheta*kTheta;
    		Robot.drivetrain.setVoltages((Robot.m_oi.getLeftY() + Robot.m_oi.getRightX()) - correction, 
					(Robot.m_oi.getLeftY() - Robot.m_oi.getRightX()) + correction);
    	}
    	
    	SmartDashboard.putNumber("Arcade drive initAngle", initAngle);
    	SmartDashboard.putNumber("Arcade drive deltatheta", deltaTheta);
    	SmartDashboard.putNumber("Arcade drive correction", correction);*/
    	
    }

    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	Robot.drivetrain.setVoltages(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
