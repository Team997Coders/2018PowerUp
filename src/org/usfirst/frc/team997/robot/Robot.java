/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team997.robot;

import org.usfirst.frc.team997.robot.commands.Auto2CubeLeftLeft;
import org.usfirst.frc.team997.robot.commands.Auto2CubeLeftStart;
import org.usfirst.frc.team997.robot.commands.Auto2CubeRightRight;
import org.usfirst.frc.team997.robot.commands.Auto2CubeRightStart;
import org.usfirst.frc.team997.robot.commands.AutoCenterLeftSwitch;
import org.usfirst.frc.team997.robot.commands.AutoCenterRightSwitch;
import org.usfirst.frc.team997.robot.commands.AutoCenterSwitchDelivery;
import org.usfirst.frc.team997.robot.commands.AutoDoNothing;
import org.usfirst.frc.team997.robot.commands.AutoLeftLeftScale;
import org.usfirst.frc.team997.robot.commands.AutoLeftLeftSwitch;
import org.usfirst.frc.team997.robot.commands.AutoLeftRightScale;
import org.usfirst.frc.team997.robot.commands.AutoLeftScale;
import org.usfirst.frc.team997.robot.commands.AutoRightLeftScale;
import org.usfirst.frc.team997.robot.commands.AutoRightRightScale;
import org.usfirst.frc.team997.robot.commands.AutoRightRightSwitch;
import org.usfirst.frc.team997.robot.commands.AutoRightScale;
import org.usfirst.frc.team997.robot.commands.AutoTest;
import org.usfirst.frc.team997.robot.commands.CrossLine;
import org.usfirst.frc.team997.robot.commands.LeftScaleOrSwitch;
import org.usfirst.frc.team997.robot.commands.PDriveToAngle;
import org.usfirst.frc.team997.robot.commands.PDriveToDistance;
import org.usfirst.frc.team997.robot.commands.RightScaleOrSwitch;
import org.usfirst.frc.team997.robot.commands.SwitchSameSideDelivery;
import org.usfirst.frc.team997.robot.subsystems.Arduino;
import org.usfirst.frc.team997.robot.subsystems.Climber;
import org.usfirst.frc.team997.robot.subsystems.Collector;
import org.usfirst.frc.team997.robot.subsystems.DriveTrain;
import org.usfirst.frc.team997.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static Collector collector;
	public static Climber climber;
	public static DriveTrain drivetrain;
	public static Elevator elevator;
	public static OI m_oi;
	public static Logger logger;
	public static String gameData;
	public static PowerDistributionPanel pdp;
	public static Arduino arduino;  
	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<Command>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		collector = new Collector();
		climber = new Climber();
		drivetrain = new DriveTrain();
		elevator = new Elevator();
		m_oi = new OI();
		pdp = new PowerDistributionPanel();
		arduino = new Arduino();
		gameData = "";
		
		if(DriverStation.getInstance().getAlliance() == Alliance.Red) {
			arduino.sendRed();
		} else {
			arduino.sendBlue();
		}
		
		logger = Logger.getInstance();
		
		pdp.clearStickyFaults();
		LiveWindow.disableTelemetry(pdp); // turn-off the telemetry features in Livewindow to stop the CTRE Timeouts
		
		m_chooser.addDefault("Do nothing", new AutoDoNothing());
		
		m_chooser.addObject("Cross line", new CrossLine());
		//m_chooser.addObject("Same side switch", new SwitchSameSideDelivery());
		//m_chooser.addObject("Turn 90 degrees", new PDriveToAngle(90));
		//m_chooser.addObject("Drive forward 5 ft", new PDriveToDistance(0.5, RobotMap.Values.ticksPerFoot * ((60 - RobotMap.Values.robotLength) / 12)));
		//m_chooser.addObject("Conditionals Test 2/24/28", new AutoTest());
		
		//1 CUBE DELIVERY
		m_chooser.addObject("Center Switch", new AutoCenterSwitchDelivery());
		
		m_chooser.addObject("Left Scale or cross line", new AutoLeftScale());
		m_chooser.addObject("Right Scale or cross line", new AutoRightScale());
		
		m_chooser.addObject("Left Switch or cross line", new AutoLeftLeftSwitch());
		m_chooser.addObject("Right Switch or cross line", new AutoRightRightSwitch());
		
		m_chooser.addObject("Left Scale or left switch or cross line", new LeftScaleOrSwitch());
		m_chooser.addObject("Right Scale or right switch or cross line", new RightScaleOrSwitch());
		
		//2 CUBE DELIVERY
		m_chooser.addObject("2 Cube Left Scale/Switch Left Start", new Auto2CubeLeftStart());
		m_chooser.addObject("2 Cube Right Scale/Switch Right Start", new Auto2CubeRightStart());

		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		drivetrain.updateDashboard();
		collector.updateSmartDashboard();
		elevator.updateSmartDashboard();
		elevator.autozero();
		logger.close();
		gameData = "";
		//controlCurrent();
	}

	//noot noot
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		drivetrain.updateDashboard();
		collector.updateSmartDashboard();
		elevator.updateSmartDashboard();
		elevator.autozero();
		//controlCurrent();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		logger.openFile();
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		SmartDashboard.putString("gamedata", gameData);
		System.out.println("auto init game data: " + gameData);
		
		//AUTONOMOUS CHOSEN BASED ON GAMEDATA
		//this logic works. has been tested :)
		
		//TODO: add near side switch delivery auto!!
		
		//1 CUBE AUTO
		
		//AUTO CENTER SWITCH DELIVERY
		if((m_chooser.getSelected()).getName().equals("AutoCenterSwitchDelivery")) {
			if(gameData.charAt(0) == 'L') {
				m_autonomousCommand = new AutoCenterLeftSwitch();
				System.out.println("Autocommand center switch left");
			} else {
				m_autonomousCommand = new AutoCenterRightSwitch();
				System.out.println("Autocommand center switch right");
			}
			
		} else if((m_chooser.getSelected()).getName().equals("LeftScaleOrSwitch")) {
			if(gameData.charAt(1) == 'L') {
				m_autonomousCommand = new AutoLeftLeftScale();
				System.out.println("Autocommand scale left left");
			} else if(gameData.charAt(0) == 'L') {
				m_autonomousCommand = new AutoLeftLeftSwitch();
				System.out.println("Autocommand switch left left");
			} else {
				m_autonomousCommand = new CrossLine();
				System.out.println("Autocommand cross line");
			}
			
		} else if((m_chooser.getSelected()).getName().equals("RightScaleOrSwitch")) {
			if(gameData.charAt(1) == 'R') {
				m_autonomousCommand = new AutoRightRightScale();
				System.out.println("Autocommand scale right right");
			} else if(gameData.charAt(0) == 'R') {
				m_autonomousCommand = new AutoRightRightSwitch();
				System.out.println("Autocommand switch right right");
			} else {
				m_autonomousCommand = new CrossLine();
				System.out.println("Autocommand cross line");
			}
			
		} else if((m_chooser.getSelected()).getName().equals("AutoLeftScale")) {
			if(gameData.charAt(1) == 'L') {
				m_autonomousCommand = new AutoLeftLeftScale();
				System.out.println("Autocommand scale left left");
			} else {
				m_autonomousCommand = new CrossLine();
				System.out.println("Autocommand cross line");
			}
			
		} else if((m_chooser.getSelected()).getName().equals("AutoRightScale")) {
			if(gameData.charAt(1) == 'R') {
				m_autonomousCommand = new AutoRightRightScale();
				System.out.println("Autocommand scale right right");
			} else {
				m_autonomousCommand = new CrossLine();
				System.out.println("Autocommand cross line");
			}
		}
		
		//2 CUBE AUTO
		
		//AUTO LEFT SCALE/SWITCH DELIVERY
		else if((m_chooser.getSelected()).getName().equals("Auto2CubeLeftStart")) {
			if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L') {
				m_autonomousCommand = new Auto2CubeLeftLeft();
				System.out.println("Autocommand 2 cube left left scale and switch");
			} else if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'R') {
				m_autonomousCommand = new AutoLeftLeftSwitch();
				System.out.println("Autocommand 1 cube left left switch (2 CUBE NOT SUPPORTED)");
			} else if (gameData.charAt(0) == 'R' && gameData.charAt(1) == 'L') {
				m_autonomousCommand = new AutoLeftLeftScale();
				System.out.println("Autocommand 1 cube left left scale (2 CUBE NOT SUPPORTED)");
			} else if (gameData.charAt(0) == 'R' && gameData.charAt(1) == 'R'){
				m_autonomousCommand = new CrossLine();
				System.out.println("Autocommand crossline (2 CUBE NOT SUPPORTED");
			}
		}
		//AUTO RIGHT SCALE/SWITCH DELIVERY	
		else if((m_chooser.getSelected()).getName().equals("Auto2CubeRightStart")){
			if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'R') {
				m_autonomousCommand = new Auto2CubeRightRight();
				System.out.println("Autocommand 2 cube right right scale and switch");
			} else if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'L') {
				m_autonomousCommand = new AutoRightRightSwitch();
				System.out.println("Autocommand 1 cube right right switch (2 CUBE NOT SUPPORTED)");
			} else if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'R') {
				m_autonomousCommand = new AutoRightRightScale();
				System.out.println("2 CUBE NOT SUPPORTED");
			} else if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L') {
				m_autonomousCommand = new CrossLine();
				System.out.println("Autocommand crossline (2 CUBE NOT SUPPORTED)");
			}
		} else if ((m_chooser.getSelected()).getName().equals("AutoRightRightSwitch")) {
			if(gameData.charAt(0) == 'R') {
				m_autonomousCommand = new AutoRightRightSwitch();
				System.out.println("Autocommand right switch right");
			} else {
				m_autonomousCommand = new CrossLine();
				System.out.println("");
			}
			
		} else if ((m_chooser.getSelected()).getName().equals("AutoLeftLeftSwitch")) {
			if(gameData.charAt(0) == 'L') {
				m_autonomousCommand = new AutoLeftLeftSwitch();
				System.out.println("Autocommand left switch left");
			} else {
				m_autonomousCommand = new CrossLine();
				System.out.println("");
			}
			
		} 
			
		
		else {
			m_autonomousCommand = m_chooser.getSelected();
			System.out.println("Name is: " + (m_chooser.getSelected()).getName());
			System.out.println("autocommand is " + m_autonomousCommand);
		}
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		drivetrain.updateDashboard();
		collector.updateSmartDashboard();
		elevator.updateSmartDashboard();
		//controlCurrent();

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		drivetrain.updateDashboard();
		collector.updateSmartDashboard();
		elevator.updateSmartDashboard();
		logger.logAll();
		//controlCurrent();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		drivetrain.updateDashboard();
		collector.updateSmartDashboard();
		elevator.updateSmartDashboard();
		//controlCurrent();

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		drivetrain.updateDashboard();
		collector.updateSmartDashboard();
		elevator.updateSmartDashboard();
		//controlCurrent();
		elevator.autozero();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	/*public void controlCurrent() {
		if (pdp.getTotalCurrent() > 180) {
			RobotMap.Values.drivetrainLeftLimit = 61;
			RobotMap.Values.drivetrainRightLimit = 61;
		}
	}*/
	
	//x = clamp(x, -1, 1);
	public static double clamp(double x, double min, double max) {
		if (x > max) {
			return max;
		} else if(x < min) {
			return min;
		} else {
			return x;
		}
	}
		public static String getGameData() {
			return gameData;
	}
		
		public static void isAuto(boolean a) {
			SmartDashboard.putBoolean("isAuto", a);
		}

}
