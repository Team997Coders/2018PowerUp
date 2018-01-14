package org.usfirst.frc.team997.robot.subsystems;

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
	
	public boolean gyropresent;
	
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
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new TankDrive());
    	//setDefaultCommand(new ArcadeDrive());
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
    }
    
    public double getAHRSAngle() {
    	return ahrs.getAngle();
    }
    
}

