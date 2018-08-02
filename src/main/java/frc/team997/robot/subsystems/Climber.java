package main.java.frc.team997.robot.subsystems;

import main.java.frc.team997.robot.Robot;
import main.java.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
	public class Climber extends Subsystem {
	
	private VictorSP climberMotor;
	private VictorSP climberMotor2;
	
	public Climber() {
		climberMotor = new VictorSP(RobotMap.Ports.climberVictorPort);
		climberMotor2 = new VictorSP(RobotMap.Ports.climberVictorPort2);
	}
		
	public void safeClimb() {
		if (Robot.pdp.getCurrent(RobotMap.PDPPorts.climber) > RobotMap.Values.climberLimit) {
			climberMotor.set(0);
			climberMotor2.set(0);
		} else {
			climberMotor.set(RobotMap.Values.climbspeed);
			climberMotor2.set(RobotMap.Values.climbspeed);
		}
	}

	public void safeUnclimb() {
		if (Robot.pdp.getCurrent(RobotMap.PDPPorts.climber) > RobotMap.Values.climberLimit) {
			climberMotor.set(0);
			climberMotor2.set(0);
		} else {
			climberMotor.set(-RobotMap.Values.climbspeed);
			climberMotor2.set(-RobotMap.Values.climbspeed);
		}
	}
	
	public void stopclimb() {
		climberMotor.set(0);
		climberMotor2.set(0);
	}
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
}

