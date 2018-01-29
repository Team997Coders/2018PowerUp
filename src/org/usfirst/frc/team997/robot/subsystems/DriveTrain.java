package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;
import org.usfirst.frc.team997.robot.commands.ArcadeDrive;
import org.usfirst.frc.team997.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.SensorCollection;
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
	public SensorCollection leftSensorCollection;
	public SensorCollection rightSensorCollection;

	private AHRS ahrs;
	private double init_angle;

	public boolean decellOn; // Default is false.
	public boolean gyropresent;
	public double decellSpeed;
	public double decellDivider;

	public static double totalLeftCurrent;
	public static double totalRightCurrent;

	public DriveTrain() {

		/*
		 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/SixTalonArcadeDrive
		 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop
		 */
		
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
		
		/* drive robot forward and make sure all 
		 * motors spin the correct way.
		 * Toggle booleans accordingly.... */
		leftTalon.setInverted(false);
		leftVictor.setInverted(false);
		leftVictor2.setInverted(false);
		
		rightTalon.setInverted(false);
		rightVictor.setInverted(false);
		rightVictor2.setInverted(false);

		/*
		 * CTRE encoder on each Talon on the drivetrain, mechanically connected to the front wheels (i.e. 1:1 ratio)
		 */
		leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftTalon.setSensorPhase(true);
		rightTalon.setSensorPhase(true);
		
		/* set the peak, nominal outputs */
		leftTalon.configNominalOutputForward(0, 10);
		leftTalon.configNominalOutputReverse(0, 10);
		leftTalon.configPeakOutputForward(1, 10);
		leftTalon.configPeakOutputReverse(-1, 10);
		
		rightTalon.configNominalOutputForward(0, 10);
		rightTalon.configNominalOutputReverse(0, 10);
		rightTalon.configPeakOutputForward(1, 10);
		rightTalon.configPeakOutputReverse(-1, 10);

		/* set closed loop gains in slot0 */
		leftTalon.config_kF(0, 0.1097, 10);
		leftTalon.config_kP(0, 0.113333, 10);
		leftTalon.config_kI(0, 0, 10);
		leftTalon.config_kD(0, 0, 10);		

		rightTalon.config_kF(0, 0.1097, 10);
		rightTalon.config_kP(0, 0.113333, 10);
		rightTalon.config_kI(0, 0, 10);
		rightTalon.config_kD(0, 0, 10);	
		
		leftSensorCollection = new SensorCollection(leftTalon);
		rightSensorCollection = new SensorCollection(rightTalon);

		/*
		 * Set-up the gyro
		 */
		try {
			ahrs = new AHRS(RobotMap.Ports.AHRS);
		} catch (RuntimeException e) {
			System.out.println("DT - The AHRS constructor do a bad.");
		}
		ahrs.reset();
		init_angle = ahrs.getAngle();
		
    	//Motor.changeControlMode(TalonControlMode.PercentVbus);
		leftTalon.setSelectedSensorPosition(0, 0, 10);
		rightTalon.setSelectedSensorPosition(0, 0, 10);
    	leftTalon.set(ControlMode.PercentOutput, 0.0);
    	rightTalon.set(ControlMode.PercentOutput, 0.0);

		decellSpeed = 0.2;
		decellDivider = 1.2;
		decellOn = false;

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new TankDrive());
		setDefaultCommand(new ArcadeDrive());
	}

	private double getDecell(double velocity, double prevVelocity) { // copied from 2017 :I

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
		return leftTalon.getSelectedSensorPosition(0);
	}

	public double getRightEncoderTicks() {
		return rightTalon.getSelectedSensorPosition(0);
	}

	public void resetEncoders() {
		leftTalon.setSelectedSensorPosition(0, 0, 10);
		rightTalon.setSelectedSensorPosition(0, 0, 10);
	}
	
	public double getHeading() {
		return( ahrs.getAngle() - init_angle );
	}

	public void updateDashboard() {
		SmartDashboard.putNumber("DT - Left master voltage", leftTalon.getMotorOutputVoltage());
		SmartDashboard.putNumber("DT - Right master voltage", rightTalon.getMotorOutputVoltage());
		SmartDashboard.putNumber("DT - Left Encoder", getLeftEncoderTicks());
		SmartDashboard.putNumber("DT - Right Encoder", getRightEncoderTicks());
		SmartDashboard.putNumber("DT - Left Encoder Velocity", leftTalon.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("DT - Right EncoderVelocity", rightTalon.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("DT - Heading", getHeading());
		SmartDashboard.putNumber("total left current", totalLeftCurrent);
		SmartDashboard.putNumber("total right current", totalRightCurrent);
	}

	public double getAHRSAngle() {
		return ahrs.getAngle();
	}

	public double getTotalLeftCurrent() {
		totalLeftCurrent = (Robot.pdp.getCurrent(RobotMap.Ports.leftTalonPort)
				+ Robot.pdp.getCurrent(RobotMap.Ports.leftVictorPort)
				+ Robot.pdp.getCurrent(RobotMap.Ports.leftVictorPort2));
		return totalLeftCurrent;
	}

	public double getTotalRightCurrent() {
		totalRightCurrent = (Robot.pdp.getCurrent(RobotMap.Ports.rightTalonPort)
				+ Robot.pdp.getCurrent(RobotMap.Ports.rightVictorPort)
				+ Robot.pdp.getCurrent(RobotMap.Ports.rightVictorPort2));
		return totalRightCurrent;
	}

	public void safeVoltages(double leftSpeed, double rightSpeed) {

		if (getTotalLeftCurrent() > RobotMap.Values.drivetrainLeftLimit) {
			leftTalon.set(ControlMode.PercentOutput, 0);
		} else {
			leftTalon.set(ControlMode.PercentOutput, leftSpeed);
		}

		if (getTotalRightCurrent() > RobotMap.Values.drivetrainRightLimit) {
			rightTalon.set(ControlMode.PercentOutput, 0);
		} else {
			rightTalon.set(ControlMode.PercentOutput, rightSpeed);
		}
	}

}
