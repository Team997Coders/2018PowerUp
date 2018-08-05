package frc.team997.robot.commands;

import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCenterRightSwitch extends CommandGroup {

    public AutoCenterRightSwitch() {
    	//addSequential(new FlopDown());
    	addSequential(new Timercommand(0.5));
		addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSafeDriveHeight));	
		addSequential(new PDriveToDistance(2 * RobotMap.Values.ticksPerFoot)); 
		addSequential(new PDriveToAngle(65), 2); //Turn to face switch.
		addSequential(new PDriveToDistance(4.4710 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch. Prev value: 5.5 and 3.887
		addSequential(new PDriveToAngle(-65), 2); //Turn to face straight again.
		addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
		addSequential(new PDriveToDistance(5.0063 * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery. Prev value: 4.15
		addSequential(new FlopDown());
		addSequential(new TimedUncollect(-0.4, -0.4, 1.5) );
    }
}
