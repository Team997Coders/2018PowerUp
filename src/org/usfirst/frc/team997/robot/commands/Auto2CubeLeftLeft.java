package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/* Starts 
 */
public class Auto2CubeLeftLeft extends CommandGroup {

    public Auto2CubeLeftLeft() {
    	addSequential(new PDriveToDistance(RobotMap.Values.autoScaleDistance * RobotMap.Values.ticksPerFoot));
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorTopHeight)); //elevatorTopHeight
    	addSequential(new Timercommand(1));
    	addSequential(new PDriveToAngle(90));
    	addSequential(new FlopDown());
    	addSequential(new Timercommand(1));
    	addSequential(new TimedUncollect(-1, -1, 3));
    	addSequential(new FlopUp());
    	addSequential(new Timercommand(1));
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorBottomHeight));
    	addSequential(new PDriveToAngle(90));
    	addSequential(new PDriveToDistance(7 * RobotMap.Values.ticksPerFoot));
    	addSequential(new PDriveToAngle(-45));
    	addSequential(new FlopDown());
    	
    	//I had a merge conflict?? 
    	//Wasn't sure what to do so I commented out what I think is outdated
    	//Still here in case I was wrong, though
/*<<<<<<< HEAD
    	addParallel(new Collect(1, 1));
    	addParallel(slowForward);
    	//Cube collect and deposit based on conditionals
=======*/
    	// CCB: Grouped collection and driving together
    	addSequential(new GoForthAndCollect(3));
//>>>>>>> df33eafb00248e82ff4ff51a7e019e1e5f1b664e
    	addSequential(new Conditional(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight), new AutoDoNothing()) {
    	    protected boolean condition() {return Robot.collector.gotCube;}});
    	addSequential(new Conditional(new PDriveToDistance(0.5 * RobotMap.Values.ticksPerFoot), new AutoDoNothing()){
    		protected boolean condition() {return Robot.collector.gotCube;}});
    	addSequential(new Conditional(new TimedUncollect(-1, -1, 2), new AutoDoNothing()) {
    		protected boolean condition() {return Robot.collector.gotCube;}});
    }
}
