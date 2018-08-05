package frc.team997.robot.commands;

import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/*
Okay, yeah, naming scheme is confusing. I know. What this means is we START on the LEFT side, and we DELIVER the cube to the LEFT side of the switch.
 */
public class AutoLeftLeftScale extends CommandGroup {

    public AutoLeftLeftScale() {
    	addSequential(new PDriveToDistance(RobotMap.Values.autoScaleDistance * RobotMap.Values.ticksPerFoot));
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorTopHeight)); //elevatorTopHeight
    	addSequential(new Timercommand(1));
    	addSequential(new PDriveToAngle(90));
    	addSequential(new FlopDown());
    	addSequential(new Timercommand(1));
    	addSequential(new TimedUncollect(-1, -1, 3));
    }
}
