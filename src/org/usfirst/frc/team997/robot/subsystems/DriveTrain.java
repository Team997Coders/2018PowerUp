package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.RobotMap;
import org.usfirst.frc.team997.robot.commands.ArcadeDrive;
import org.usfirst.frc.team997.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private TalonSRX leftTalon;
	private TalonSRX rightTalon;
	private VictorSPX leftVictor;
	private VictorSPX rightVictor;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DriveTrain() {
		leftTalon = new TalonSRX(RobotMap.Ports.leftTalonPort);
		rightTalon = new TalonSRX(RobotMap.Ports.rightTalonPort);
		leftVictor = new VictorSPX(RobotMap.Ports.leftVictorPort);
		rightVictor = new VictorSPX(RobotMap.Ports.rightVictorPort);
		
		leftVictor.follow(leftTalon);
		rightVictor.follow(rightTalon);
		
		leftEncoder = new Encoder(RobotMap.Ports.leftEncoderPort1, RobotMap.Ports.leftEncoderPort2);
		rightEncoder = new Encoder(RobotMap.Ports.rightEncoderPort1, RobotMap.Ports.rightEncoderPort2);
		
		leftEncoder.reset();
		rightEncoder.reset();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new TankDrive());
    	//setDefaultCommand(new ArcadeDrive());
    }
    
    public void getVoltages(IMotorController motor) {
    	motor.getMotorOutputVoltage();
    }
    
    public void setVoltages(double leftSpeed, double rightSpeed) {
    	//leftTalon.(); Not sure which method to use
    	//rightTalon.(); Not sure which method to use
    }
    
    public void resetEncoders() {
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
}

