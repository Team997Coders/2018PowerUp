/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team997.robot;

import org.usfirst.frc.team997.robot.commands.ArrayHeightSelector;
import org.usfirst.frc.team997.robot.commands.Climb;
import org.usfirst.frc.team997.robot.commands.Collect;
import org.usfirst.frc.team997.robot.commands.ElevatorToHeight;
import org.usfirst.frc.team997.robot.commands.Flop;
import org.usfirst.frc.team997.robot.commands.LockElevator;
import org.usfirst.frc.team997.robot.commands.MoveElevator;
import org.usfirst.frc.team997.robot.commands.UnClimb;
import org.usfirst.frc.team997.robot.commands.Uncollect;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public Joystick 
	GamePad1, 
	GamePad2;
	
	public JoystickButton
	//CLIMBER CONTROLS
	climbbutton,
	unclimbbutton,
	
	//ELEVATOR MANUAL CONTROLS
	elevatorManualUp,
	elevatorManualDown,
	
	//ELEVATOR ARRAY CONTROLS
	elevatorArrayUp,
	elevatorArrayDown,
	
	//ELEVATOR SET POSITIONS
	elevatorTop,
	elevatorHighMid,
	elevatorLowMid,
	elevatorSwitch,
	elevatorBottom,
	
	//COLLECT CONTROLS
	smartCollectButton,
	uncollectButton,
	collectButton,
	flopButton;
	
	public OI() {
		//JOYSTICK INIT
		GamePad1 = new Joystick(RobotMap.Ports.GamePad1);
		GamePad2 = new Joystick(RobotMap.Ports.GamePad2);
		
		//CLIMBER  CONTROLS
		climbbutton = new JoystickButton(GamePad2, RobotMap.Buttons.climbbutton);
		climbbutton.whenPressed(new Climb());
		
		unclimbbutton = new JoystickButton(GamePad2, RobotMap.Buttons.unclimbbutton);
		unclimbbutton.whenPressed(new UnClimb());
		
		//ELEVATOR MANUAL CONTROLS
		elevatorManualUp = new JoystickButton(GamePad1, RobotMap.Buttons.elevatorManualUp);
		elevatorManualUp.whileHeld(new MoveElevator(0.5));
		elevatorManualUp.whenReleased(new LockElevator());
		
		elevatorManualDown = new JoystickButton(GamePad1, RobotMap.Buttons.elevatorManualDown);
		elevatorManualDown.whileHeld(new MoveElevator(-0.5));
		elevatorManualDown.whenReleased(new LockElevator());
		
		//ELEVATOR ARRAY CONTROLS
		//elevatorArrayUp = new JoystickButton(GamePad2, RobotMap.Buttons.elevatorArrayUp);
		//elevatorArrayUp.whenPressed(new ArrayHeightSelector(true));
				
		//elevatorArrayDown = new JoystickButton(GamePad2, RobotMap.Buttons.elevatorArrayDown);
		//elevatorArrayDown.whenPressed(new ArrayHeightSelector(false));
		
		//ELEVATOR SET POSITIONS
		//elevatorTop = new JoystickButton(GamePad1, RobotMap.Buttons.topPosition);
		//elevatorTop.whenPressed(new ElevatorToHeight(RobotMap.Values.elevatorTopHeight));
		
	//	elevatorHighMid = new JoystickButton(GamePad1, RobotMap.Buttons.highMidPosition);
		//elevatorHighMid.whenPressed(new ElevatorToHeight(RobotMap.Values.elevatorHighMidHeight));
		
	//	elevatorLowMid = new JoystickButton(GamePad1, RobotMap.Buttons.lowMidPosition);
		/*elevatorLowMid.whenPressed(new ElevatorToHeight(RobotMap.Values.elevatorLowMidHeight));
		
		elevatorSwitch = new JoystickButton(GamePad1, RobotMap.Buttons.switchPosition);
		elevatorSwitch.whenPressed(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
		
		elevatorBottom = new JoystickButton(GamePad1, RobotMap.Buttons.bottomPosition);
		elevatorBottom.whenPressed(new ElevatorToHeight(RobotMap.Values.elevatorBottomHeight));*/
		
		//COLLECT CONTROLS
		collectButton = new JoystickButton(GamePad2, RobotMap.Buttons.collectButton);
		collectButton.whenPressed(new Collect(0.5, 0.5)); //VALUES ALREADY INVERTED IN COLLECTOR
		
		uncollectButton = new JoystickButton(GamePad2, RobotMap.Buttons.uncollectButton);
		uncollectButton.whileHeld(new Uncollect(-0.75,-0.75));
		
		//smartCollectButton = new JoystickButton(GamePad2, RobotMap.Buttons.smartCollectButton);
		//smartCollectButton.whileHeld(new SmartCollect());
		//COMMENTED OUT BECAUSE PROBABLY NOT NECESSARY.
		
		flopButton = new JoystickButton(GamePad1, RobotMap.Buttons.flopButton);
		flopButton.whenPressed(new Flop());
		
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
