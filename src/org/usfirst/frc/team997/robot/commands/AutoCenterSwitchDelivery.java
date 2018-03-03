package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

//This auto command is for when we are positioned on the inner side of the exchange
//zone and we want to deliver our preloaded cube to either of the sides.
public class AutoCenterSwitchDelivery extends CommandGroup {

    public AutoCenterSwitchDelivery() {
    	addParallel(new FlopDown());
    	//addParallel(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    	addSequential(new PDriveToDistance(2 * RobotMap.Values.ticksPerFoot)); //Drive forward to avoid exhchange zone.
    	if(Robot.getGameData().charAt(0) == 'L') {
    		
    		addSequential(new PDriveToAngle(-65)); //Turn left to face switch.
    		addSequential(new PDriveToDistance(4.6 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch.
    		addSequential(new PDriveToAngle(60)); //Turn right to face straight again.
    		addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    		addSequential(new PDriveToDistance(4.30 * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery.
    		//PRETTY ACCURATE NOW!!:^)
    		//When our side of the switch is on the left, this will deliver the cube to
    		//that side.
    	}
    	else {
    		addSequential(new PDriveToAngle(65)); //Turn right to face switch.
    		addSequential(new PDriveToDistance(4.6 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch.
    		addSequential(new PDriveToAngle(-60)); //Turn left to face straight again.
    		addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    		addSequential(new PDriveToDistance(4.30 * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery.
    		//PRETTY ACCURATE NOW :^)
    		//When our side of the switch is on the right, this will deliver the cube to
    		//that side.
    		//I Love You
    	}
		//addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
		addSequential(new TimedUncollect(-1, -1, 3));
    }
}

