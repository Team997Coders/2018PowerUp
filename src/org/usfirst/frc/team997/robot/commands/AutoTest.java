
package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTest extends CommandGroup {

    public AutoTest() {
    	//addSequential(new PDriveToDistance(2 * RobotMap.Values.ticksPerFoot)); //Drive forward to avoid exhchange zone.
    	//addSequential(new PDriveToAngle(65)); //Turn right to face switch.
		//addSequential(new PDriveToDistance(3.0 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch.
		//addSequential(new PDriveToAngle(-65)); //Turn left to face straight again.
		//addSequential(new PDriveToDistance(3.47 * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery.
    	addSequential(new PDriveToDistance(30 * RobotMap.Values.ticksPerFoot));
    	//addSequential(new Timercommand(2.0));
    	//addSequential(new PDriveToDistance(3 * RobotMap.Values.ticksPerFoot));
    	//addSequential(new PDriveToAngle(90));
    }
}
