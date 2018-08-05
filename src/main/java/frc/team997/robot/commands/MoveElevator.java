package frc.team997.robot.commands;

import frc.team997.robot.Robot;
import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

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
    	if (Robot.elevator.getPosition() >= RobotMap.Values.elevatorTopHeight && value > 0) {
    		Scheduler.getInstance().add(new LockElevator());
    	} else {
    		Robot.elevator.setVoltage(value);
    	}
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
