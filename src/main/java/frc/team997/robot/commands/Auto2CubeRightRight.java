package main.java.frc.team997.robot.commands;

import main.java.frc.team997.robot.Robot;
import main.java.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto2CubeRightRight extends CommandGroup {

    public Auto2CubeRightRight() {
    	addSequential(new PDriveToDistance((RobotMap.Values.autoScaleDistance - 2.5)* RobotMap.Values.ticksPerFoot));
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorTopHeight)); //elevatorTopHeight
    	addSequential(new Timercommand(1));
    	addSequential(new PDriveToAngle(-45));
    	addSequential(new FlopDown());
    	addSequential(new Timercommand(1));
    	addSequential(new TimedUncollect(-0.6, -0.6, 3));
    	addSequential(new FlopUp());
    	addSequential(new Timercommand(1));
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorBottomHeight), 2);
    	//addSequential(new Timercommand(1));
    	addSequential(new PDriveToAngle(-135));
    	addSequential(new PDriveToDistance((2.667) * RobotMap.Values.ticksPerFoot));
    	addSequential(new PDriveToAngle(30), 3);
    	addSequential(new FlopDown(), 2);
    	
    	// CCB: Grouped collection and driving together
    	addSequential(new GoForthAndCollect(2), 2);
    	
    	addSequential(new PDriveToDistance(-0.5 * RobotMap.Values.ticksPerFoot), 2);
    	addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    	addSequential(new PDriveToDistance(0.8 * RobotMap.Values.ticksPerFoot), 2);
    	addSequential(new TimedUncollect(-0.6, -0.6, 2));
    	
    	/*addSequential(new GoForthAndCollect(3));
    	addSequential(new Conditional(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight), new AutoDoNothing()) {
    	    protected boolean condition() {return Robot.collector.gotCube;}});
    	addSequential(new Conditional(new PDriveToDistance(0.5 * RobotMap.Values.ticksPerFoot), new AutoDoNothing()){
    		protected boolean condition() {return Robot.collector.gotCube;}});
    	addSequential(new Conditional(new TimedUncollect(-1, -1, 2), new AutoDoNothing()) {
    		protected boolean condition() {return Robot.collector.gotCube;}});*/
    }
}
