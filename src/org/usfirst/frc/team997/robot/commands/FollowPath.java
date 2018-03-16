package org.usfirst.frc.team997.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import jaci.pathfinder.*;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class FollowPath extends Command {

	private Waypoint[] _points;
	private Trajectory _leftTrajectory, _rightTrajectory;
	private EncoderFollower _leftEncoderFollower, _rightEncoderFollower;

	private static final Trajectory.FitMethod _FIT_METHOD = Trajectory.FitMethod.HERMITE_CUBIC;

	private static final int _SAMPLES = Trajectory.Config.SAMPLES_FAST;

	private Timer _timer;
	private double _timeout = 5.0;
	private double velocity_ratio = 1.0 / RobotMap.Values.pf_max_vel;

	private boolean _done = false;

	public FollowPath(Waypoint[] points, double timeout) {
		requires(Robot.drivetrain);

		this._timer = new Timer();
		this._timeout = timeout;

		this._points = points.clone(); // Don't want a reference of points, I want a copy of it

		Trajectory.Config baseTrajectoryConfig = new Trajectory.Config(_FIT_METHOD, _SAMPLES,
				RobotMap.Values.pf_timestep, RobotMap.Values.pf_max_vel, RobotMap.Values.pf_max_acc,
				RobotMap.Values.pf_max_jerk);

		Trajectory baseTrajectory = Pathfinder.generate(this._points, baseTrajectoryConfig);

		TankModifier modifier = new TankModifier(baseTrajectory);

		modifier.modify(RobotMap.Values.robotWidth);

		this._leftTrajectory = modifier.getLeftTrajectory();
		this._rightTrajectory = modifier.getRightTrajectory();

		this._leftEncoderFollower = new EncoderFollower(this._leftTrajectory);
		this._rightEncoderFollower = new EncoderFollower(this._rightTrajectory);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		_leftEncoderFollower.configureEncoder(Robot.drivetrain.getLeftEncoderPosition(), RobotMap.Values.ticksPerRev,
				RobotMap.Values.robotWheelDia / 12.0);
		_rightEncoderFollower.configureEncoder(Robot.drivetrain.getRightEncoderPosition(), RobotMap.Values.ticksPerRev,
				RobotMap.Values.robotWheelDia / 12.0);

		this._leftEncoderFollower.configurePIDVA(RobotMap.Values.pf_Kp, RobotMap.Values.pf_Ki, RobotMap.Values.pf_Kd,
				velocity_ratio, RobotMap.Values.pf_Ka);

		this._rightEncoderFollower.configurePIDVA(RobotMap.Values.pf_Kp, RobotMap.Values.pf_Ki, RobotMap.Values.pf_Kd,
				velocity_ratio, RobotMap.Values.pf_Ka);

		this._timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double leftPosition =  Robot.drivetrain.getLeftEncoderPosition() / RobotMap.Values.ticksPerFoot;
		double rightPosition = Robot.drivetrain.getRightEncoderPosition() / RobotMap.Values.ticksPerFoot;
		
		double left = _leftEncoderFollower.calculate((int) leftPosition);
		double right= _leftEncoderFollower.calculate((int) rightPosition);
		
		double desiredHeading = Pathfinder.r2d((_leftEncoderFollower.getHeading() + _rightEncoderFollower.getHeading()) / 2);
		double angleDiff = (desiredHeading - Robot.drivetrain.getAHRSAngle());
		double turn = RobotMap.Values.pf_Kt * angleDiff;
		
		if (this._timer.get() >= this._timeout) {
			this._done = true;
			return;
		} else if (left == 0 && right == 0) {
			this._done = true;
			return;
		}

	    Robot.drivetrain.setVoltages(left + turn, right - turn);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return this._done;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.setVoltages(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.end();
	}
}
