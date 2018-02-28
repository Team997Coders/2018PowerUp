package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team997.robot.utils;

/**
 *
 */
public class PDriveToAngle extends Command {
	private double angSetpoint;
	private double minError = 12;
	private double initYaw = -999; 
	public Timer timer = new Timer();
	private double lastTime = -999;
	private double lastError = 0;
	private double accumError = 0;
	private double startTime = -1;
	private double speed = 0.5;
	
    public PDriveToAngle(double _speed, double _ang) {
    	requires(Robot.drivetrain);
    	speed = _speed;
    	angSetpoint = _ang;
    }
    
    public PDriveToAngle(double _ang) {
    	requires(Robot.drivetrain);
    	speed = 0.5;
    	angSetpoint = _ang;
    }


    protected void initialize() {
    	initYaw = Robot.drivetrain.getAHRSAngle();
    	Robot.drivetrain.setBrake();
    	timer.reset();
    	lastTime = 0;
    	lastError = 0;
    	accumError = 0;
    	timer.start();
    	startTime = timer.get();
    	System.out.println("PDriveAngle - Init PAngle" + initYaw);
    }

    protected void execute() {
    	// calculate time variables
    	double deltaT = timer.get() - lastTime;

    	// calculate the control variables
    	double deltax = (piderror() - lastError) / deltaT;
    	accumError += piderror() * (timer.get() - startTime); 
    	
    	// compute the pid P component values
    	double pfactor = RobotMap.Values.driveAngleP * piderror();
    	double ifactor = RobotMap.Values.driveAngleI * accumError;
    	double dfactor = RobotMap.Values.driveAngleD * deltax;
    	   	
    	double output = speed * utils.clamp(pfactor + ifactor + dfactor, -1.0, 1.0);

    	// set power to the drive motors
    	Robot.drivetrain.setVoltages(utils.clamp(output, -1, 1), utils.clamp(-output, -1, 1)); 
    	// Debug information to be placed on the smart dashboard.
    	SmartDashboard.putNumber("PDriveToAngle: Angle Error", piderror());
    	SmartDashboard.putNumber("PDriveToAngle: Theta Angle Correction", output);
    	SmartDashboard.putBoolean("PDriveToAngle: On Angle Target", onTarget());
    	SmartDashboard.putNumber("PDriveToAngle: Init Angle Yaw", initYaw);
    	
    	lastError = piderror();
    	lastTime = timer.get();
    }

    private double piderror() {
    	return initYaw + angSetpoint - Robot.drivetrain.getAHRSAngle();
    }
    
    private boolean onTarget() {
    	return Math.abs(piderror()) < minError;
    }

    protected boolean isFinished() {
        return onTarget();   
    }

    protected void end() {
    	Robot.drivetrain.setVoltages(0, 0);
    	System.out.println("PDriveAngle - PAngle End");
    }

    protected void interrupted() {
    	end();
    }
}
