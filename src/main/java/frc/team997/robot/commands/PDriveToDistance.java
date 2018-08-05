package frc.team997.robot.commands;

import frc.team997.robot.Robot;
import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PDriveToDistance extends Command {
	
	private double distSetpoint;
	private double minError = 3;
	public Timer timer = new Timer();
	private double lastTime = 0;
	private double lastVoltage = 0;
	private double deltaT = 0;
	private double speed = 0.5;
	private double initYaw = -999;
	private double Ktheta = 0.02;
	private double integral, previous_error = 0;

    public PDriveToDistance(double _speed, double _dist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	//requires(Robot.collector);
    	distSetpoint = _dist;
    	speed = _speed;
    	System.out.println("(PDTD-CONSTRUCTOR) Calling constructor!! :^)");
    }
    
    public PDriveToDistance(double _dist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	System.out.println("(PDTD-CONSTRUCTOR) Calling constructor!! :^)");
    	requires(Robot.drivetrain);
    	//requires(Robot.collector);
    	distSetpoint = _dist;
    	speed = 0.5;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	lastVoltage = 0;
    	Robot.drivetrain.resetEncoders();
    	Robot.drivetrain.setBrake();
    	initYaw = Robot.drivetrain.getAHRSAngle();
    	this.previous_error = this.piderror();
    	timer.reset();
    	timer.start();
    	System.out.println("(PDTD-INIT) OMG, I got initialized!!! :O");
    	lastTime = 0;
    }
    
    // current algorithm assumes that we are starting
    // from a stop
    private double linearAccel(double input) {
    	double Klin = 0.8;
    	double deltaT = timer.get() - lastTime;
    	lastTime = timer.get();
    	
    	double Volts = lastVoltage + Klin * (deltaT);
    	if (Volts > input) {
    		Volts = input;
    	}
    	lastVoltage = Volts;
    	return Volts;
    }

    
    public double pFactor() {
    	// Calculate full PID
    	// pfactor = (P × error) + (I × ∑error) + (D × δerrorδt)
    	double error = this.piderror();
    	// Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
    	this.integral += (error * .02);
    	// Derivative is change in error over time
    	double derivative = (error - this.previous_error) / .02;
        this.previous_error = error;
        return 
        	(RobotMap.Values.driveDistanceP * error) + 
        	(RobotMap.Values.driveDistanceI * this.integral) + 
        	(RobotMap.Values.driveDistanceD * derivative);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// compute the pid P value
    	double pfactor = speed * Robot.clamp(this.pFactor(), -1, 1);
    	double pfactor2 = linearAccel(pfactor);
    	double deltaTheta = Robot.drivetrain.getAHRSAngle() - initYaw;
    	deltaT = timer.get() - lastTime;
    	lastTime = timer.get();

    	// calculate yaw correction
    	double yawcorrect = deltaTheta * Ktheta;
    	
    	// set the output voltage
    	Robot.drivetrain.setVoltages(pfactor2 - yawcorrect, pfactor2 + yawcorrect); //TODO check these signs...
    	//Robot.driveTrain.SetVoltages(-pfactor, -pfactor); //without yaw correction, accel

    	// Debug information to be placed on the smart dashboard.
    	SmartDashboard.putNumber("Setpoint", distSetpoint);
    	SmartDashboard.putNumber("Encoder Distance", this.encoderDistance());
    	//SmartDashboard.putNumber("Encoder Rate", Robot.drivetrain.getEncoderRate());
    	SmartDashboard.putNumber("Distance Error", piderror());
    	SmartDashboard.putNumber("K-P factor", pfactor);
    	SmartDashboard.putNumber("K-P factor Accel", pfactor2);
    	SmartDashboard.putNumber("deltaT", deltaT);
    	SmartDashboard.putNumber("Theta Correction", yawcorrect);
    	SmartDashboard.putBoolean("On Target", onTarget());
    	SmartDashboard.putNumber("NavX Heading", Robot.drivetrain.getAHRSAngle());
    	SmartDashboard.putNumber("Init Yaw", initYaw);
    }

    private double piderror() {
    	// shouldn't we average this out between both of the encoders?
    	return distSetpoint - this.encoderDistance();
    }
    
    private double encoderDistance() {
    	return (Robot.drivetrain.getLeftEncoderTicks() + Robot.drivetrain.getRightEncoderTicks()) / 2;
    }
    
    private boolean onTarget() {
    	return piderror() < minError;
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.drivetrain.getleftVelocity() <= 0 && 
    			Robot.drivetrain.getrightvelocity() <= 0 &&
    			onTarget()) {
    		System.out.println("(PDTD-ISFINISHED) PDTD ended with isFinished!");
    		 return onTarget();
    	} else {
    		/*if (Robot.collector.getAvgLeftVoltage() > RobotMap.Values.autoIRthreshold ||
    			Robot.collector.getAvgRightVoltage() > RobotMap.Values.autoIRthreshold) {
    			return true;
    		} else {
    			return false;
    		}*/
    		return false;
    	}
    	
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println(this.encoderDistance());
    	System.out.println("PDrive End");
    	timer.stop();
    	Robot.drivetrain.setVoltages(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	System.out.println("(PDTD-INTERRUPTED) I got interrupted!! D:");
    }
}
