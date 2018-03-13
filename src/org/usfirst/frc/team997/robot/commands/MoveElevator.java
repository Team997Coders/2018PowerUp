package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevator extends Command {
	public double value;
	
	public MoveElevator(double _value) {
    	requires(Robot.elevator);
    	this.value = _value;
    }
    protected void initialize() {
    }
    protected void execute() {
    	Robot.elevator.setVoltage(value);
    }
    protected boolean isFinished() {
    	return false;
    }
    protected void end() {
    }

    protected void interrupted() {
    	//end();
    }
}
