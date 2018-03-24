package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

//This auto routine is for when we are on the left or right starting position and our switch
//is on the opposite side.
public class CrossLine extends CommandGroup {

    public CrossLine() {
    	addSequential(new PDriveToDistance((11 - (RobotMap.Values.robotLength / 12)) * RobotMap.Values.ticksPerFoot)); //Drives forward 12 feet, crossing the 10 foot auto line.
    }
}