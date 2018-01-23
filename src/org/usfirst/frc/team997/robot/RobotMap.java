/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team997.robot;

import edu.wpi.first.wpilibj.SerialPort;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static class Ports {
		public static int
		//CAN
		leftTalonPort = 1,
		rightTalonPort = 2,
		elevatorTalonPort = 3,
		
		//PWM
		leftVictorPort = 1,
		leftVictorPort2 = 2,
		rightVictorPort = 3,
		rightVictorPort2 = 4,
		climberVictorPort = 5,
		leftCollectorPort = 6,
		rightCollectorPort = 7,
		
		//Other mechanisms we will use:
		//elevator - basically bunnybot arm
		//collector
		//??
		
		//DIO
		leftEncoderPort1 = 0,
		leftEncoderPort2 = 1,
		rightEncoderPort1 = 2,
		rightEncoderPort2 = 3,
		
		//GAMEPADS
		GamePad1 = 0,
		GamePad2 = 1;
		
		//USB
		public static final SerialPort.Port AHRS = SerialPort.Port.kUSB;
		
	}
	
	public static class Values {
		
		public static double
		
		driveDistanceP = 0,
		elevatorPidP = 0,
		elevatorPidI = 0,
		elevatorPidD = 0,
		climbspeed = 0.5;
		
	}
	
	public static class Buttons {
		public static int
		
		//ELEVATOR CONTROLS
		elevatorManualUp = 6, //Right Bumper, GamePad1
		elevatorManualDown = 5, //Left Bumper, GamePad1
		elevatorArrayUp = 4, //Y, GamePad2
		elevatorArrayDown = 1, //A, GamePad2
		climbbutton = 3, //not final
		unclimbbutton = 2; //not final
	}
}
