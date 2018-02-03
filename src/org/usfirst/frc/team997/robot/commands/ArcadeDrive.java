package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDrive extends Command {

    public ArcadeDrive() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    }

    protected void execute() {
    	Robot.drivetrain.setVoltages(Robot.m_oi.getLeftY() + Robot.m_oi.getRightX(), 
    								Robot.m_oi.getLeftY() - Robot.m_oi.getRightX());
    }

    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	Robot.drivetrain.setVoltages(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
