package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LED extends Subsystem {
	
	private boolean state = false;
	
	public LED() {
		//Robot.m_oi.LaunchPad.setOutput(1, true);
	}
	
	public void Toggle() {
		state = !state;
		Robot.m_oi.LaunchPad.setOutput(1, state);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

