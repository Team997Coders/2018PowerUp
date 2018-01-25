package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {
	
	private DoubleSolenoid elevatorSolenoid;
	private TalonSRX Motor;
	public SensorCollection sensorCollection;
	public static final double absoluteTolerance = 0.01;
	public boolean isZeroed = false;
	public int absolutePosition;
	public int flop; //whether the collector is "flopped" down or not
	
    // Initialize your subsystem here
    public Elevator() {
    	
    	elevatorSolenoid = new DoubleSolenoid(RobotMap.Ports.elevatorSolenoidPort, RobotMap.Ports.elevatorSolenoidPort2);
    	
    	Motor = new TalonSRX(RobotMap.Ports.elevatorTalonPort);
    	
    	absolutePosition = Motor.getSelectedSensorPosition(0); // & 0xFFF;
    	Motor.setSelectedSensorPosition(absolutePosition, 0, 10);
    	
    	Motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    	Motor.setSensorPhase(false);
    	Motor.clearStickyFaults(10);
    	Motor.enableCurrentLimit(false);
    	Motor.configNominalOutputForward(0, 10);
    	Motor.configNominalOutputReverse(0, 10);
    	Motor.configPeakOutputForward(1, 10);
    	Motor.configPeakOutputReverse(-1, 10);
    	Motor.configAllowableClosedloopError(0, 0, 10);
    	Motor.selectProfileSlot(0, 0);
    	Motor.config_kP(0, RobotMap.Values.elevatorPidP, 10);
    	Motor.config_kI(0, RobotMap.Values.elevatorPidI, 10);
    	Motor.config_kD(0, RobotMap.Values.elevatorPidD, 10);
    	Motor.config_kF(0, 0, 10);
    	
    	sensorCollection = new SensorCollection(Motor);
    	
    	flop = 0;
    	elevatorSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void flop(int p) {
    	if (flop != p && flop != 0) {
    		elevatorSolenoid.set(DoubleSolenoid.Value.kForward);
    		flop = 0;
    	} else if (flop != p) {
    		elevatorSolenoid.set(DoubleSolenoid.Value.kReverse);
    		flop = 1;
    	}
    }
    
    public void autozero() {
    	if (sensorCollection.isRevLimitSwitchClosed() && !isZeroed) {
    		isZeroed = true;
    		Motor.setSelectedSensorPosition(0, 0, 10);
    		System.out.println("Zeroed " + Motor.getSelectedSensorPosition(0));
    	}
    }
    
    public double getPosition() {
    	return Motor.getSelectedSensorPosition(0);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void stop() {
    	//System.out.println("Stop Arm");
    	Motor.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void setPosition(double height) {
    	Motor.set(ControlMode.Position, height);
    }
    
    public double getError() {
    	return Motor.getClosedLoopError(0);
    }
    
    public double getVoltage() {
    	return Motor.getOutputCurrent();
    }
    
    public void setVoltage(double volts) {
    	Motor.set(ControlMode.PercentOutput, volts);
    }
    
    public void updateSmartDashboard() {
    	absolutePosition = Motor.getSelectedSensorPosition(0);// & 0xFFF;
    	
       	SmartDashboard.putNumber("TalonSRX Mode", Motor.getControlMode().value);
    	SmartDashboard.putNumber("Absolute Position", absolutePosition);
    	SmartDashboard.putNumber("Elevator Voltage", Motor.getMotorOutputVoltage());
    	SmartDashboard.putBoolean("Holo1", sensorCollection.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("Holo2", sensorCollection.isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("Elevator Zeroed", Robot.elevator.isZeroed);
    	SmartDashboard.putNumber("ElevatorPIDError", Motor.getClosedLoopError(0));
    	SmartDashboard.putNumber("Elevator Position ", Motor.getSelectedSensorPosition(0));
    }
}
