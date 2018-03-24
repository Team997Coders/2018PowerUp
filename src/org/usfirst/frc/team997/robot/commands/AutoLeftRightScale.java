package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLeftRightScale extends CommandGroup {

	public AutoLeftRightScale() {
    	
		addSequential(new PDriveToDistance(((239) / 12) * RobotMap.Values.ticksPerFoot));
		addSequential(new PDriveToAngle(90));
		addSequential(new PDriveToDistance((274 / 12) * RobotMap.Values.ticksPerFoot));
		addSequential(new PDriveToAngle(-90));
		addSequential(new PDriveToDistance((117 / 12) * RobotMap.Values.ticksPerFoot));
		addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
		addSequential(new PDriveToAngle(-90));
		addSequential(new FlopDown());
		addSequential(new Timercommand(1));
		addSequential(new TimedUncollect(-1, -1, 3));
		
    }
}
