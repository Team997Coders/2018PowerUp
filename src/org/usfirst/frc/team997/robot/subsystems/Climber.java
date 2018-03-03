package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
	public class Climber extends Subsystem {
	
	private VictorSP climberMotor;
	private VictorSP climberMotor2;
	private boolean climbing = false;
	
	public Climber() {
		climberMotor = new VictorSP(RobotMap.Ports.climberVictorPort);
	}
		
	public void safeClimb() {
		if (Robot.pdp.getCurrent(RobotMap.PDPPorts.climber) > RobotMap.Values.climberLimit) {
			climberMotor.set(0);
		} else {
			climberMotor.set(RobotMap.Values.climbspeed);
			climberMotor2.set(RobotMap.Values.climbspeed);
			if (Robot.drivetrain.getElevation() > 5.0) {
				this.climbing = true;
			}
		}
	}

	public void safeUnclimb() {
		if (Robot.pdp.getCurrent(RobotMap.PDPPorts.climber) > RobotMap.Values.climberLimit) {
			climberMotor.set(0);
		} else {
			climberMotor.set(-RobotMap.Values.climbspeed);
		}
	}
	
	public void stopclimb() {
		climberMotor.set(0);
	}
	
	// The 775Pro can run indefinetly at 2V @ 0.19N.m up to 4V @ 0.34N.m torque for each motor.
	// http://motors.vex.com/vexpro-motors/775pro
	public void holdclimb() {
		climberMotor.set(0.20); // about 2.4V can go as high as 0.25 which would be 4V safely
		climberMotor2.set(0.20);
	}
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void updateSmartDashboard() {
    	//DISPLAYED DATA
       	SmartDashboard.putBoolean("Climbing?", climbing);
       	SmartDashboard.putNumber("Robot Height: ", Robot.drivetrain.getElevation());
    }
}

