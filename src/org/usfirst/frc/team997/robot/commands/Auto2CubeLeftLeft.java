package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/* Starts 
 */
public class Auto2CubeLeftLeft extends CommandGroup {

    public Auto2CubeLeftLeft() {
    	PDriveToDistance cube2SwitchDistance = new PDriveToDistance(5 * RobotMap.Values.ticksPerFoot);
    	Timer timeout = new Timer();
    	boolean isTimedout = false;
    	
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
    	addParallel(new Collect(1, 1));
    	addParallel(cube2SwitchDistance);
    	timeout.reset();
    	timeout.start();
    	while (Robot.collector.gotCube == false || timeout.get() > 3) {}
    	if (Robot.collector.gotCube == true) {
    		cube2SwitchDistance.cancel();
    		addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    		addSequential(new PDriveToDistance(0.5 * RobotMap.Values.ticksPerFoot));
    		addSequential(new TimedUncollect(-1, -1, 2));
    	} else {
    		cube2SwitchDistance.cancel();
    	}
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
