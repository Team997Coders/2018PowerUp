package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {
	private VictorSP leftmotor, rightmotor;
	
	public Collector() {
		leftmotor = new VictorSP(RobotMap.Ports.leftCollectorPort);
		rightmotor = new VictorSP(RobotMap.Ports.rightCollectorPort);
	}
	
	public void collect(double leftspeed,double rightspeed) { 
		leftmotor.set(leftspeed);
		rightmotor.set(rightspeed);
	}
	
	public void smartcollect() {
		
		//TODO: infrared sensor code :)
	}
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

