package frc.team997.robot.commands;

import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRightRightScale extends CommandGroup {

        
   	public AutoRightRightScale() {
       	addSequential(new PDriveToDistance(RobotMap.Values.autoScaleDistance * RobotMap.Values.ticksPerFoot));
       	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorTopHeight)); //elevatorTopHeight
       	addSequential(new Timercommand(1));
       	addSequential(new PDriveToAngle(-90));
       	addSequential(new FlopDown());
       	addSequential(new Timercommand(1));
       	addSequential(new TimedUncollect(-1, -1, 3));
       }
    	
    
}
