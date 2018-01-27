package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;
import org.usfirst.frc.team997.robot.commands.ArcadeDrive;
import org.usfirst.frc.team997.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private TalonSRX leftTalon;
	private TalonSRX rightTalon;
	private VictorSPX leftVictor;
	private VictorSPX leftVictor2;
	private VictorSPX rightVictor;
	private VictorSPX rightVictor2;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private AHRS ahrs;
	
	public boolean decellOn;  //Default is false. 
	public boolean gyropresent;
	public double decellSpeed;
	public double decellDivider;
	
	public static double totalLeftCurrent;
	public static double totalRightCurrent;
	
	public DriveTrain() {
		
		leftTalon = new TalonSRX(RobotMap.Ports.leftTalonPort);
		rightTalon = new TalonSRX(RobotMap.Ports.rightTalonPort);
		leftVictor = new VictorSPX(RobotMap.Ports.leftVictorPort);
		leftVictor2 = new VictorSPX(RobotMap.Ports.leftVictorPort2);
		rightVictor = new VictorSPX(RobotMap.Ports.rightVictorPort);
		rightVictor2 = new VictorSPX(RobotMap.Ports.rightVictorPort2);
		
		leftVictor.follow(leftTalon);
		leftVictor2.follow(leftTalon);
		rightVictor.follow(rightTalon);
		rightVictor2.follow(rightTalon);
		
		leftEncoder = new Encoder(RobotMap.Ports.leftEncoderPort1, RobotMap.Ports.leftEncoderPort2);
		rightEncoder = new Encoder(RobotMap.Ports.rightEncoderPort1, RobotMap.Ports.rightEncoderPort2);
		
		try {
			ahrs = new AHRS(RobotMap.Ports.AHRS);
		} catch(RuntimeException e) {
			System.out.println("DT - The AHRS constructor do a bad.");
		}
		
		ahrs.reset();
		
		leftEncoder.reset();
		rightEncoder.reset();
		
		decellSpeed = 0.2;
		decellDivider = 1.2;
		decellOn = false;
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	//setDefaultCommand(new TankDrive());
    	setDefaultCommand(new ArcadeDrive());
    }
    
    private double getDecell(double velocity, double prevVelocity) { //copied from 2017 :I
    	
    	if (!decellOn) {
    		return velocity;
    	} else {
    	
    		if ((velocity >= 0 && prevVelocity >= 0) || (velocity >= 0 && prevVelocity >= 0)) {
    			prevVelocity = velocity;
    		} else {
    		
    			if (Math.abs(prevVelocity) <= decellSpeed) {
    				prevVelocity = velocity;
    			} else {
    				prevVelocity = prevVelocity / decellDivider; 
    			}
    		
    		}
    	
    		return prevVelocity;
    	}
    }
    
    public void driveDecell(double leftSpeed, double rightSpeed) {
    	
    }
    
    public double getLeftMasterVoltage() {
    	return leftTalon.getMotorOutputVoltage();
    }
    
    public double getRightMasterVoltage() {
    	return rightTalon.getMotorOutputVoltage();
    }
    
    public void setVoltages(double leftSpeed, double rightSpeed) {
    	leftTalon.set(ControlMode.PercentOutput, leftSpeed);
    	rightTalon.set(ControlMode.PercentOutput, rightSpeed);
    }
    
    public double getLeftEncoderTicks() {
    	return leftEncoder.get();
    }
    
    public double getRightEncoderTicks() {
    	return rightEncoder.get();
    }
    
    public void resetEncoders() {
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public void updateDashboard() {
    	SmartDashboard.putNumber("DT - Left master voltage", leftTalon.getMotorOutputVoltage());
    	SmartDashboard.putNumber("DT - Right master voltage", rightTalon.getMotorOutputVoltage());
    	SmartDashboard.putNumber("DT - Left Encoder", leftEncoder.get());
    	SmartDashboard.putNumber("DT - Right Encoder", rightEncoder.get());
    	SmartDashboard.putNumber("total left current", totalLeftCurrent);
    	SmartDashboard.putNumber("total right current", totalRightCurrent);
    }
    
    public double getAHRSAngle() {
    	return ahrs.getAngle();
    }
    
    public double getTotalLeftCurrent() {
    	totalLeftCurrent = (Robot.pdp.getCurrent(RobotMap.Ports.leftTalonPort) + 
    				Robot.pdp.getCurrent(RobotMap.Ports.leftVictorPort) + 
    				Robot.pdp.getCurrent(RobotMap.Ports.leftVictorPort2));
    	return totalLeftCurrent;
    }
    
    public double getTotalRightCurrent() {
    	totalRightCurrent = (Robot.pdp.getCurrent(RobotMap.Ports.rightTalonPort) + 
    				Robot.pdp.getCurrent(RobotMap.Ports.rightVictorPort) + 
    				Robot.pdp.getCurrent(RobotMap.Ports.rightVictorPort2));
    	return totalRightCurrent;
    }
    
    public void safeVoltages(double leftSpeed, double rightSpeed) {
    	
    	if (getTotalLeftCurrent() > RobotMap.Values.drivetrainLeftLimit) {
    		leftTalon.set(ControlMode.PercentOutput, 0);
    	} else {
    		leftTalon.set(ControlMode.PercentOutput, leftSpeed);
    	}
    	
    	if(getTotalRightCurrent() > RobotMap.Values.drivetrainRightLimit) {
    		rightTalon.set(ControlMode.PercentOutput, 0);
    	} else {
    		rightTalon.set(ControlMode.PercentOutput, rightSpeed);
    	}
    }
    
}

