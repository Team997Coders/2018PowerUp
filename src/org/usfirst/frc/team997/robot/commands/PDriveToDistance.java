package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;
import org.usfirst.frc.team997.robot.utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PDriveToDistance extends Command {
	
	private double distSetpoint;
	private double accumError;
	private double minError = 3;
	public Timer timer = new Timer();
	private double lastTime = 0;
	private double deltaT = 0;
	private double speed = 0.5;
	private double initYaw = -999;
	private double Ktheta = 0.02;
	private double startTime = -1;

    public PDriveToDistance(double _speed, double _dist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	distSetpoint = _dist;
    	speed = _speed;
    	System.out.println("(PDTD-CONSTRUCTOR) Calling constructor!! :^)");
    }
    
    public PDriveToDistance(double _dist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	System.out.println("(PDTD-CONSTRUCTOR) Calling constructor!! :^)");
    	requires(Robot.drivetrain);
    	distSetpoint = _dist;
    	speed = 0.5;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    	Robot.drivetrain.setBrake();
    	initYaw = Robot.drivetrain.getAHRSAngle();
    	timer.reset();
    	timer.start();
    	System.out.println("(PDTD-INIT) OMG, I got initialized!!! :O");
    	lastTime = 0;
    	accumError = 0;
    	startTime = timer.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	deltaT = timer.get() - lastTime;
    	lastTime = timer.get();
    	
    	// calculate the control variables
    	double deltax = piderror() / deltaT;
    	accumError += piderror() * (timer.get() - startTime); 
    	
    	// compute the pid P value
    	double pfactor = RobotMap.Values.driveDistanceP * piderror();
    	double ifactor = RobotMap.Values.driveDistanceI * accumError;
    	double dfactor = RobotMap.Values.driveDistanceD * deltax;
    	
    	double deltaTheta = Robot.drivetrain.getAHRSAngle() - initYaw;
    	
    	double output = speed * utils.clamp(pfactor + ifactor + dfactor, -1.0, 1.0);

    	// calculate yaw correction
    	double yawcorrect = deltaTheta * Ktheta;
    	
    	// set the output voltage
    	Robot.drivetrain.setVoltages(output - yawcorrect, output + yawcorrect);
    	//Robot.driveTrain.SetVoltages(-pfactor, -pfactor); //without yaw correction, accel

    	// Debug information to be placed on the smart dashboard.
    	SmartDashboard.putNumber("Setpoint", distSetpoint);
    	SmartDashboard.putNumber("Encoder Distance", Robot.drivetrain.getLeftEncoderTicks());
    	//SmartDashboard.putNumber("Encoder Rate", Robot.drivetrain.getEncoderRate());
    	SmartDashboard.putNumber("Distance Error", piderror());
    	SmartDashboard.putNumber("K-P factor", pfactor);
    	SmartDashboard.putNumber("K-I factor", ifactor);
    	SmartDashboard.putNumber("K-D factor", dfactor);
    	SmartDashboard.putNumber("PID Output", output);
    	SmartDashboard.putNumber("deltaT", deltaT);
    	SmartDashboard.putNumber("Theta Correction", yawcorrect);
    	SmartDashboard.putBoolean("On Target", onTarget());
    	SmartDashboard.putNumber("NavX Heading", Robot.drivetrain.getAHRSAngle());
    	SmartDashboard.putNumber("Init Yaw", initYaw);
    }

    private double piderror() {
    	return distSetpoint - Robot.drivetrain.getLeftEncoderTicks();
    }
    
    private boolean onTarget() {
    	return piderror() < minError;
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.drivetrain.getleftVelocity() <= 0 && 
    			Robot.drivetrain.getrightvelocity() <= 0 &&
    			onTarget()) {
    		System.out.println("(PDTD-ISFINISHED) PDTD ended with isFinished!");
    		 return onTarget();
    	} else {
    		return false;
    	}
    	//
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println(Robot.drivetrain.getLeftEncoderTicks());
    	System.out.println("PDrive End");
    	timer.stop();
    	Robot.drivetrain.setVoltages(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	System.out.println("(PDTD-INTERRUPTED) I got interrupted!! D:");
    }
}
