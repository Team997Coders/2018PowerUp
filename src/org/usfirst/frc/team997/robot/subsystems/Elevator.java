package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.RobotMap;
import org.usfirst.frc.team997.robot.commands.ElevatorTest;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

	private TalonSRX elevatormotor;
	
	public Elevator() {
		elevatormotor = new TalonSRX(RobotMap.Ports.elevatorTalonPort);
		
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setvoltage(double elevatorspeed) {
		elevatormotor.set(ControlMode.PercentOutput, elevatorspeed);
	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ElevatorTest());
    }
}

