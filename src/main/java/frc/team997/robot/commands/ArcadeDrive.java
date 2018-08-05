package frc.team997.robot.commands;

import frc.team997.robot.Robot;
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
    	initAngle = Robot.drivetrain.getAHRSAngle();
    }

    protected void execute() {
    	//leftEncoderDist = Robot.drivetrain.getLeftEncoderTicks()*RobotMap.Values.inchesPerTick;
    	//rightEncoderDist = Robot.drivetrain.getRightEncoderTicks()*RobotMap.Values.inchesPerTick;
    	
    	
    	if (Robot.drivetrain.decellOn) {
    		//using decell code
    		//simple correction w/ multiplier and squared
    		Robot.drivetrain.driveDecell(((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX()))*Math.abs(((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX()))), 
    				((Robot.m_oi.getLeftY()*0.85) - Robot.m_oi.getRightX()*0.85)*Math.abs(((Robot.m_oi.getLeftY()*0.85) - Robot.m_oi.getRightX()*0.85)));
    		
    		//correction using gyro
        	/*if (Robot.m_oi.joystickDeadband(Math.abs(Robot.m_oi.getRightX())) != 0) {
        		Robot.drivetrain.setVoltages(Robot.m_oi.getLeftY() + Robot.m_oi.getRightX(), 
    					Robot.m_oi.getLeftY() - Robot.m_oi.getRightX());
        		initAngle = Robot.drivetrain.getAHRSAngle();
        		
        	} else {
        		
        		deltaTheta = Robot.drivetrain.getAHRSAngle() - initAngle;
        		correction = deltaTheta*kTheta;
        		Robot.drivetrain.setVoltages((Robot.m_oi.getLeftY() + Robot.m_oi.getRightX()) - correction, 
    					(Robot.m_oi.getLeftY() - Robot.m_oi.getRightX()) + correction); //TODO: check these signs     
        	}
        	
        	SmartDashboard.putNumber("Arcade drive initAngle", initAngle);
        	SmartDashboard.putNumber("Arcade drive deltatheta", deltaTheta);
        	SmartDashboard.putNumber("Arcade drive correction", correction);*/
        	
    	} else {
    		//simple correction w/ multiplier and squared
        	Robot.drivetrain.setVoltages(((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX()))*Math.abs((Robot.m_oi.getLeftY()) + (Robot.m_oi.getRightX())), 
    				((Robot.m_oi.getLeftY()*0.85) - (Robot.m_oi.getRightX()*0.85))*Math.abs((Robot.m_oi.getLeftY()*0.85) - (Robot.m_oi.getRightX()*0.85)));
    	}
    	
    	//no correction
    	/*Robot.drivetrain.setVoltages(Robot.m_oi.getLeftY() + Robot.m_oi.getRightX(), 
				Robot.m_oi.getLeftY() - Robot.m_oi.getRightX());*/
    	
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
					(Robot.m_oi.getLeftY() - Robot.m_oi.getRightX()) + correction); //TODO: check these signs     
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
