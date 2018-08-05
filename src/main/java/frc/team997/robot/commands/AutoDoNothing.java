package frc.team997.robot.commands;

import frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

//The best auto command!
public class AutoDoNothing extends Command {

    public AutoDoNothing() {
    	requires(Robot.drivetrain);
    }
    
    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
