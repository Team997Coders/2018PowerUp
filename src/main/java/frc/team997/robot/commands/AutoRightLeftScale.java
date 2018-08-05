package frc.team997.robot.commands;

import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRightLeftScale extends CommandGroup {

    public AutoRightLeftScale() {
    	
    	addSequential(new PDriveToDistance(((251) / 12) * RobotMap.Values.ticksPerFoot));
		addSequential(new PDriveToAngle(-90));
		addSequential(new PDriveToDistance((280 / 12) * RobotMap.Values.ticksPerFoot));
		addSequential(new PDriveToAngle(90));
		addSequential(new PDriveToDistance((117 / 12) * RobotMap.Values.ticksPerFoot));
		addSequential(new ElevatorToHeight(RobotMap.Values.elevatorTopHeight));
		addSequential(new Timercommand(1));
		addSequential(new PDriveToAngle(90));
		addSequential(new FlopDown());
		addSequential(new Timercommand(1));
		addSequential(new TimedUncollect(-1, -1, 3));
       
    }
}
