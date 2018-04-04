package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto2CubeRightRight extends CommandGroup {

    public Auto2CubeRightRight() {
    	addSequential(new PDriveToDistance(RobotMap.Values.autoScaleDistance * RobotMap.Values.ticksPerFoot));
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorTopHeight)); //elevatorTopHeight
    	addSequential(new Timercommand(1));
    	addSequential(new PDriveToAngle(-90));
    	addSequential(new FlopDown());
    	addSequential(new Timercommand(1));
    	addSequential(new TimedUncollect(-1, -1, 3));
    	addSequential(new FlopUp());
    	addSequential(new Timercommand(1));
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorBottomHeight));
    	addSequential(new PDriveToAngle(-90));
    	addSequential(new PDriveToDistance(7 * RobotMap.Values.ticksPerFoot));
    	addSequential(new PDriveToAngle(45));
    	addSequential(new FlopDown());
    	addParallel(new Collect(1, 1));
    	addParallel(new SlowForwardUntilHaveCube(3));
    	addSequential(new Conditional(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight), new AutoDoNothing()) {
    	    protected boolean condition() {return Robot.collector.gotCube;}});
    	addSequential(new Conditional(new PDriveToDistance(0.5 * RobotMap.Values.ticksPerFoot), new AutoDoNothing()){
    		protected boolean condition() {return Robot.collector.gotCube;}});
    	addSequential(new Conditional(new TimedUncollect(-1, -1, 2), new AutoDoNothing()) {
    		protected boolean condition() {return Robot.collector.gotCube;}});
    }
}
