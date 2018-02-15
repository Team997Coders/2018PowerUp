package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;
import org.usfirst.frc.team997.robot.commands.ElevatorToHeight;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {
	
	private DoubleSolenoid elevatorSolenoid;
	private TalonSRX Motor;
	private VictorSPX follower;
	public SensorCollection sensorCollection;
	public static final double absoluteTolerance = 0.01;
	public boolean isZeroed = false;
	public int absolutePosition;
	Preferences setpointPrefs;

	public int index = 0;
	public double[] heightList = new double[] {RobotMap.Values.elevatorTopHeight, 
			RobotMap.Values.elevatorHighMidHeight, RobotMap.Values.elevatorLowMidHeight, 
			RobotMap.Values.elevatorBottomHeight, RobotMap.Values.elevatorSwitchHeight};

	public int flop; //whether the collector is "flopped" down or not
	public double elevatorCurrent;
	
    // Initialize your subsystem here
    public Elevator() {
    	
    	elevatorSolenoid = new DoubleSolenoid(RobotMap.Ports.elevatorSolenoidPort, RobotMap.Ports.elevatorSolenoidPort2);
    	
    	Motor = new TalonSRX(RobotMap.Ports.elevatorTalonPort);
    	follower = new VictorSPX(RobotMap.Ports.elevatorVictorPort);
    	
    	follower.follow(Motor);
    	
    	follower.setInverted(false);
    	
    	absolutePosition = Motor.getSelectedSensorPosition(0); // & 0xFFF;
    	Motor.setSelectedSensorPosition(absolutePosition, 0, 10);
    	
    	Motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    	Motor.setSensorPhase(false);
    	Motor.clearStickyFaults(10);
    	Motor.enableCurrentLimit(false);
    	Motor.configNominalOutputForward(0, 10);
    	Motor.configNominalOutputReverse(0, 10);
    	Motor.configPeakOutputForward(0.8, 10);
    	Motor.configPeakOutputReverse(-0.25, 10); //TODO: Test reverse (and forward) value because it takes little power to lower it all the way.
    	Motor.configAllowableClosedloopError(0, 0, 10);
    	Motor.selectProfileSlot(0, 0);
    	Motor.config_kP(0, RobotMap.Values.elevatorPidP, 10);
    	Motor.config_kI(0, RobotMap.Values.elevatorPidI, 10);
    	Motor.config_kD(0, RobotMap.Values.elevatorPidD, 10);
    	Motor.config_kF(0, 0, 10);
    	
    	sensorCollection = new SensorCollection(Motor);
    	
    	setpointPrefs = Preferences.getInstance();
    	
    	setpointPrefs.putDouble("Elevator Bottom Height", RobotMap.Values.elevatorBottomHeight);
    	setpointPrefs.putDouble("Elevator Switch Height", RobotMap.Values.elevatorSwitchHeight);
    	setpointPrefs.putDouble("Elevator Low Mid Height", RobotMap.Values.elevatorLowMidHeight);
    	setpointPrefs.putDouble("Elevator High Mid Height", RobotMap.Values.elevatorHighMidHeight);
    	setpointPrefs.putDouble("Elevator Top Height", RobotMap.Values.elevatorTopHeight);
    	
    	flop = 1;
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
    	if (sensorCollection.isRevLimitSwitchClosed() /*&& !isZeroed*/) {
    		isZeroed = true;
    		Motor.setSelectedSensorPosition(0, 0, 10);
    		System.out.println("Zeroed " + Motor.getSelectedSensorPosition(0));
    	}
    }
    
    public double getCurrent() {
    	elevatorCurrent = Robot.pdp.getCurrent(RobotMap.PDPPorts.elevatorTalon);
    	return elevatorCurrent;
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
    
    public void incrementIndex() {
    	index++;
    	if (index > heightList.length - 1) {
    		index = heightList.length - 1;
    	}
    }
    
    public void decrementIndex() {
    	index--;
    	if(index < 0) {
    		index = 0;
    	}
    }
    
    public double getHeightFromArray() {
    	return heightList[index];
    }
    
    public void safeSetVoltage(double volts) {
    	if (getCurrent() > RobotMap.Values.elevatorLimit) {
    		Motor.set(ControlMode.PercentOutput, 0);
    	} else {
    		Motor.set(ControlMode.PercentOutput, volts);
    	}
    }
    
    public void updateSmartDashboard() {
    	absolutePosition = Motor.getSelectedSensorPosition(0);// & 0xFFF;
    	
    	//DISPLAYED DATA
       	SmartDashboard.putNumber("TalonSRX Mode", Motor.getControlMode().value);
    	SmartDashboard.putNumber("Absolute Position", absolutePosition);
    	SmartDashboard.putBoolean("Top limit switch", sensorCollection.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("Bottom limit switch", sensorCollection.isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("Elevator Zeroed", Robot.elevator.isZeroed);
    	SmartDashboard.putNumber("ElevatorPIDError", Motor.getClosedLoopError(0));
    	SmartDashboard.putNumber("Elevator Position ", Motor.getSelectedSensorPosition(0));
    	SmartDashboard.putNumber("Elevator Current", getCurrent());
    	SmartDashboard.putNumber("Elevator Voltage", Motor.getMotorOutputVoltage());
    	
    	//PREFERENCES SETTER
    	RobotMap.Values.elevatorBottomHeight = setpointPrefs.getDouble("Elevator Bottom Height", 0);
    	RobotMap.Values.elevatorSwitchHeight = setpointPrefs.getDouble("Elevator Switch Height", 0);
    	RobotMap.Values.elevatorLowMidHeight = setpointPrefs.getDouble("Elevator Low Mid Height", 0);
    	RobotMap.Values.elevatorHighMidHeight = setpointPrefs.getDouble("Elevator High Mid Height", 0);
    	RobotMap.Values.elevatorTopHeight = setpointPrefs.getDouble("Elevator Top Height", 0);
    	
    	//POSITION SETPOINTS COMMANDS
    	SmartDashboard.putData("Elevator Top Height", new ElevatorToHeight(RobotMap.Values.elevatorTopHeight));
    	SmartDashboard.putData("Elevator High Mid Height", new ElevatorToHeight(RobotMap.Values.elevatorHighMidHeight));
    	SmartDashboard.putData("Elevator Switch Height", new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    	SmartDashboard.putData("Elevator Low Mid Height", new ElevatorToHeight(RobotMap.Values.elevatorLowMidHeight));
    	SmartDashboard.putData("Elevator Bottom Height", new ElevatorToHeight(RobotMap.Values.elevatorBottomHeight));
    	
    	
    	
    	
    }
}
