package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDrive extends Command {

	double initAngle;
	double deltaTheta;
	double correction;
	double kTheta = 0.01;
	
	double leftEncoderDist;
	double rightEncoderDist;
	
    public ArcadeDrive() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	//Robot.drivetrain.resetEncoders();
    	Robot.drivetrain.setCoast();
    }

    protected void execute() {
    	if (Robot.drivetrain.decellOn) {
    		//using decell code
    		//simple correction w/ multiplier and squared
    		Robot.drivetrain.driveDecell(((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX()))*Math.abs(((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX()))), 
    				((Robot.m_oi.getLeftY()*0.85) - Robot.m_oi.getRightX()*0.85)*Math.abs(((Robot.m_oi.getLeftY()*0.85) - Robot.m_oi.getRightX()*0.85)));
    	} else {
    		//simple correction w/ multiplier and squared
        	Robot.drivetrain.setVoltages(((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX()))*Math.abs((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX())), 
    				((Robot.m_oi.getLeftY()*0.85) - (Robot.m_oi.getRightX()*0.85))*Math.abs((Robot.m_oi.getLeftY()*0.85) - (Robot.m_oi.getRightX()*0.85)));
    	}
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
