package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchSameSideDelivery extends CommandGroup {

    public SwitchSameSideDelivery() {
       if(Robot.gameData != null && !Robot.gameData.isEmpty()) {
        	
        	addSequential(new PDriveToDistance(14 * RobotMap.Values.ticksPerFoot));
        	if (Robot.gameData.charAt(0) == 'L') {
        		addSequential(new PDriveToAngle(90)); //Turn right to face switch.
        	} else {
        		addSequential(new PDriveToAngle(-90)); //Turn left to face switch.
        	}
        	//TODO How far does the robot have to travel to get close enough to the switch? 
        }
    }
}
