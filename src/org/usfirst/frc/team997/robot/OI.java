/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team997.robot;

import org.usfirst.frc.team997.robot.commands.ArrayHeightSelector;
import org.usfirst.frc.team997.robot.commands.Climb;
import org.usfirst.frc.team997.robot.commands.Flop;
import org.usfirst.frc.team997.robot.commands.LockElevator;
import org.usfirst.frc.team997.robot.commands.MoveElevator;
import org.usfirst.frc.team997.robot.commands.UnClimb;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick 
	GamePad1, 
	GamePad2;
	
	public JoystickButton
	elevatorManualUp,
	elevatorManualDown,
	elevatorArrayUp,
	elevatorArrayDown,
	climbbutton,
	unclimbbutton,
	flopButton;
	
	public OI() {
		//JOYSTICK INIT
		GamePad1 = new Joystick(RobotMap.Ports.GamePad1);
		GamePad2 = new Joystick(RobotMap.Ports.GamePad2);
		//CLIMBERCONTROLS
		climbbutton = new JoystickButton(GamePad2, RobotMap.Buttons.climbbutton);
		climbbutton.whenPressed(new Climb());
		
		unclimbbutton = new JoystickButton(GamePad2, RobotMap.Buttons.unclimbbutton);
		unclimbbutton.whenPressed(new UnClimb());
		//ELEVATOR MANUAL CONTROL BUTTONS
		elevatorManualUp = new JoystickButton(GamePad1, RobotMap.Buttons.elevatorManualUp);
		elevatorManualUp.whileHeld(new MoveElevator(0.5));
		elevatorManualUp.whenReleased(new LockElevator());
		
		elevatorManualDown = new JoystickButton(GamePad1, RobotMap.Buttons.elevatorManualDown);
		elevatorManualDown.whileHeld(new MoveElevator(-0.5));
		elevatorManualDown.whenReleased(new LockElevator());
		
		flopButton = new JoystickButton(GamePad1, RobotMap.Buttons.flopButton);
		flopButton.whenPressed(new Flop());
		
		//ELEVATOR ARRAY CONTROL BUTTONS
		elevatorArrayUp = new JoystickButton(GamePad2, RobotMap.Buttons.elevatorArrayUp);
		elevatorArrayUp.whenPressed(new ArrayHeightSelector(true));
		
		elevatorArrayDown = new JoystickButton(GamePad2, RobotMap.Buttons.elevatorArrayDown);
		elevatorArrayDown.whenPressed(new ArrayHeightSelector(false));
		
	}
	
	public double getLeftY() {
		return joystickDeadband(-GamePad1.getRawAxis(1));
	}
	
	public double getRightY() {
		return joystickDeadband(-GamePad1.getRawAxis(5));
	}
	
	public double getRightX() {
		return joystickDeadband(GamePad1.getRawAxis(4));
	}
	
	public static double joystickDeadband(double x) {
		if(Math.abs(x) < 0.05) {
			return 0;
		} else {
			return x;
		}
	}
	
}
