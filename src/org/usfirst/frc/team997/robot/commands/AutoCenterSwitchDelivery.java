package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

//This auto command is for when we are positioned on the inner side of the exchange
//zone and we want to deliver our preloaded cube to either of the sides.
public class AutoCenterSwitchDelivery extends CommandGroup {

    public AutoCenterSwitchDelivery() {
    	String gameData;
    	
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	addSequential(new PDriveToDistance(2 * PDriveToDistance.ticksPerFoot));
    	if(gameData.charAt(0) == 'L') {
    		
    		addSequential(new PDriveToAngle(-65));
    		addSequential(new PDriveToDistance(4.6 * PDriveToDistance.ticksPerFoot));
    		addSequential(new PDriveToAngle(65));
    		addSequential(new PDriveToDistance(3.470 * PDriveToDistance.ticksPerFoot));
    		//NEED TO DROP CUBE AFTERWARDS IN SWITCH.
    		//NEEDS TESTING!!
    		//When our side of the switch is on the left, this will deliver the cube to
    		//that side.
    	}
    	else {
    		addSequential(new PDriveToAngle(65));
    		addSequential(new PDriveToDistance(4.6 * PDriveToDistance.ticksPerFoot));
    		addSequential(new PDriveToAngle(-65));
    		addSequential(new PDriveToDistance(3.470 * PDriveToDistance.ticksPerFoot));
    		//NEEDS TESTING!!
    		//When our side of the switch is on the right, this will deliver the cube to
    		//that side.
    	}
    }
}

