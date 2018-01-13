/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team997.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick driverOne;
	
	public OI() {
		driverOne = new Joystick(0);
	}
	
	public double getLeftY() {
		return joystickDeadband(-driverOne.getRawAxis(1));
	}
	
	public double getRightY() {
		return joystickDeadband(-driverOne.getRawAxis(5));
	}
	
	public double getRightX() {
		return joystickDeadband(driverOne.getRawAxis(4));
	}
	
	public static double joystickDeadband(double x) {
		if(Math.abs(x) < 0.05) {
			return 0;
		} else {
			return x;
		}
	}
	
	public void updateDashboard() {
		
	}
}
