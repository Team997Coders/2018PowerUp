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
		leftTalonPort = 3,
		rightTalonPort = 8,
		elevatorTalonPort = 2,
		
		//ANALOGINPUT
		leftCollectorSensorInput = 0,
		rightCollectorSensorInput = 1,
		
		//PWM
		leftVictorPort = 4,
		leftVictorPort2 = 5,
		rightVictorPort = 6,
		rightVictorPort2 = 7,
		climberVictorPort = 9,
		climberVictorPort2 = 10,
		elevatorVictorPort = 1,
		leftCollectorPort = 2,
		rightCollectorPort = 3,
		
		//PNEUMATICS
		elevatorSolenoidPort = 0,
		elevatorSolenoidPort2 = 1,
		
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
	
	public static class PDPPorts {
		public static int
		
		rightDriveTrain = 0,
		rightDriveTrain2 = 1,
		climber = 2,
		climber2 = 3,
		intake = 6,
		intake2 = 7,
		elevatorTalon = 10,
		elevatorVictor = 11,
		leftDriveTrain = 12,
		leftDriveTrain2 = 13,
		leftDriveTrainTalon = 14,
		rightDriveTrainTalon = 15;
	}
	
	public static class Values {
		public static int
		ticksPerRev = 4096;
		
		public static double
		inchesPerTick = (3.954*Math.PI)/4096,	//inches per encoder tick
		ticksPerFoot = ((49152/(3.97*Math.PI)))*0.9, //3940, //encoder ticks per foot
		
		robotLength = 33.25, //in inches
		robotWidth = 37.25,
		robotWheelBase = 30, // or 0.6 meters.  Use 0.0254 meters/in or 39.37in/m
		robotWheelDia = 4.0, // remember all pf variables are in ft.  Need to convert when used.
		
		elevatorTopHeight,
		elevatorHighMidHeight,
		elevatorLowMidHeight = 6135,
		elevatorSwitchHeight = 10672,
		elevatorBottomHeight,
		//TODO: Set values
		
		slowcollectspeed = 0.25,
		fastcollectspeed = 0.75,
		collectspeed = 0.5,
		
		driveDistanceP = 0.005,//completely arbitrary guesstimate value; needs tuning
		elevatorPidP = 0.5,
		elevatorPidI = 0,
		elevatorPidD = 0,
		climbspeed = 0.5,
		
		//Current limits in amps
		drivetrainLeftLimit = 81, //81
		drivetrainRightLimit = 81, //81
		collectorLeftLimit = 12,
		collectorRightLimit = 12,
		elevatorLimit = 55,
		climberLimit = 50,
		
		//Pathfinder configuration values  -- everything is in ft (and ft/sec, and ft/s/s)
		// a number of the initial values for these variables came from Team 2053 TigerTronics
		// https://github.com/team2053tigertronics/Robot2018/blob/master/Robot2018/src/Pathfinder/TestFollower.h
		// all pathfinder specific variables are prefaced with 'pf_'
		pf_timestep = 0.05,
		pf_max_vel = 15, // max velocity in ft/sec.
		pf_max_acc = 12,
		pf_max_jerk = 60,
		pf_Kp = 1,
		pf_Ki = 0,
		pf_Kd = 0.15,
		pf_Kv = (1/pf_max_vel),
		pf_Ka = 0.0856,
		pf_Kt = 0.35,
		
		// design pattern to make everything above end with a comma and not have to worry about...
		// "Do I end with a comma or a semicolon?"
		last=0.0;
		
	}

	public static class Buttons {
		public static int
		
		//ELEVATOR CONTROLS
		elevatorManualUp = 6, //Right Bumper, GamePad1
		elevatorManualDown = 5, //Left Bumper, GamePad1
		elevatorArrayUp = 4, //Square, GamePad2
		elevatorArrayDown = 1, //Triangle, GamePad2
		
		topPosition = 4, //Y, GamePad1
		highMidPosition = 2, //B, GamePad1
		switchPosition = 8, //Start, GamePad1
		lowMidPosition = 3, //X, Gamepad1
		bottomPosition = 1, //A, GamePad1
		
		//COLLECTOR CONTROLS
		collectButton = 8, //Right Trigger, Gamepad2
		//smartCollectButton = 7, //Left Trigger, Gamepad2 TODO: needs testing
		uncollectButton = 7, //Left Trigger, Gamepad2
		flopButton = 7, //Back, Gamepad1
		
		//CLIMBER CONTROLS
		climbbutton = 3, //X, Gamepad2
		unclimbbutton = 2; //Circle, Gamepad2

	}
}
