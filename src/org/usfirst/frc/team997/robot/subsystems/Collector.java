package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Collector extends Subsystem {
	private VictorSP leftmotor, rightmotor;
	private AnalogInput leftinput = new AnalogInput(RobotMap.Ports.leftCollectorSensorInput);
	private AnalogInput rightinput = new AnalogInput(RobotMap.Ports.rightCollectorSensorInput);
	public static double leftVoltage;
	public static double rightVoltage;
	
	public Collector() {
		leftmotor = new VictorSP(RobotMap.Ports.leftCollectorPort);
		rightmotor = new VictorSP(RobotMap.Ports.rightCollectorPort);
	}
	
	public void collect(double leftspeed,double rightspeed) { 
		leftmotor.set(leftspeed);
		rightmotor.set(rightspeed);
	}
	
	public double getAvgLeftVoltage() {
		leftVoltage = leftinput.getAverageVoltage();
		return leftVoltage;
	}
	
	public double getAvgRightVoltage() {
		rightVoltage = rightinput.getAverageVoltage();
		return rightVoltage;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void updateSmartDashboard() {
    	SmartDashboard.putNumber("rightCollectorSensor", rightVoltage);
		SmartDashboard.putNumber("leftCollectorSensor", leftVoltage);
    }
    
}

