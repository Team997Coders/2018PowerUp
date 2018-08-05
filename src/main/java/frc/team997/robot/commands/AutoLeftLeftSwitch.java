package frc.team997.robot.commands;

import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/*
Okay, yeah, naming scheme is confusing. I know. What this means is we START on the LEFT side, and we DELIVER the cube to the LEFT side of the switch.
 */
public class AutoLeftLeftSwitch extends CommandGroup {

    public AutoLeftLeftSwitch() {
    	addSequential(new PDriveToDistance(RobotMap.Values.autoSwitchDistance * RobotMap.Values.ticksPerFoot));
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    	addSequential(new Timercommand(1));
    	addSequential(new PDriveToAngle(90));
    	addSequential(new PDriveToDistance(2 * RobotMap.Values.ticksPerFoot)); //TODO: MEASURE THIS
    	addSequential(new FlopDown());
    	addSequential(new Timercommand(1));
    	addSequential(new TimedUncollect(-1, -1, 3));
    }
}
