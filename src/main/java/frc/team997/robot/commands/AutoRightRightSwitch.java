package frc.team997.robot.commands;

import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRightRightSwitch extends CommandGroup {

        
   	public AutoRightRightSwitch() {
       	addSequential(new PDriveToDistance(RobotMap.Values.autoSwitchDistance * RobotMap.Values.ticksPerFoot));
       	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight)); //elevatorTopHeight
       	addSequential(new Timercommand(1));
       	addSequential(new PDriveToAngle(-90));
       	addSequential(new PDriveToDistance(2 * RobotMap.Values.ticksPerFoot)); //TODO: MEASURE THIS
       	addSequential(new FlopDown());
       	addSequential(new Timercommand(1));
       	addSequential(new TimedUncollect(-1, -1, 3));
       }
    	
    
}
