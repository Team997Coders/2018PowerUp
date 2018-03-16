package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCenterLeftSwitch extends CommandGroup {

    public AutoCenterLeftSwitch() {
    	//addParallel(new FlopDown());
		//addParallel(new ElevatorToHeight(RobotMap.Values.elevatorSafeDriveHeight));
		addSequential(new PDriveToDistance(2 * RobotMap.Values.ticksPerFoot)); 
		addSequential(new PDriveToAngle(-65)); //Turn to face switch.
		addSequential(new PDriveToDistance(3.887 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch. Prev value: 5.5
		addSequential(new PDriveToAngle(60)); //Turn to face straight again.
		//addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
		addSequential(new PDriveToDistance((RobotMap.Values.autoLeftSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery. Prev value: 4.15
		//addSequential(new TimedUncollect(-.25, -.25, 3) );
    }
}
