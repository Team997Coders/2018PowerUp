/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team997.robot;


import java.io.File;

import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class MotionProfile {
	private Trajectory trajectory, left, right;
	private EncoderFollower leftEncoderFollower, rightEncoderFollower;
	private static MotionProfile instance;
	private String fileName = "";
	private String path = "/home/lvuser/spartanpath";
	DriverStation ds;

	private int max = 0;

	public static MotionProfile getInstance() {
		if (instance == null) {
			instance = new MotionProfile();
		}
		return instance;
	}

	private MotionProfile() {
		this.ds = DriverStation.getInstance();
		fileName = "NO_NAME";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		File[] files = new File(path).listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					System.out.println(file.getName());
					try {
						int index = Integer.parseInt(file.getName().split("_")[0]);
						if (index > max) {
							max = index;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			max = 0;
		}

		generatePath();
		setupTrajectory();
	}

	private String getPath() {
		fileName = "";
		if (DriverStation.getInstance().isFMSAttached()) {
			return String.format("/home/lvuser/spartanpath/%d_%s_%d.csv", ++max, ds.getAlliance().name(),
					ds.getLocation());
		} else if (this.fileName != null) {
			return String.format("/home/lvuser/spartanpath/%d_%s.csv", ++max, fileName);
		} else {
			return String.format("/home/lvuser/spartanpath/%d.csv", ++this.max);
		}
	}

	// Create the way point list
	// I used the motion-profile-generator app to generate the paths:
	// 	git@github.com:Endoman123/motion-profile-generator.git
	// Note the coordinate system:
	public void generatePath() {
		// 3 Waypoints: (x, y, heading)
		// Left path from center
		Waypoint[] points = new Waypoint[] { new Waypoint(0, 14, 0), new Waypoint(8, 18, 0), new Waypoint(12, 18, 0) };

		// Create the Trajectory Configuration
		//
		// Arguments:
		// Fit Method: HERMITE_CUBIC or HERMITE_QUINTIC
		// Sample Count: 
		//		SAMPLES_HIGH (100 000)
		// 		SAMPLES_LOW (10 000)
		// 		SAMPLES_FAST (1 000)
		// Time Step: 0.05 Seconds
		// Max Velocity: 1.7 m/s
		// Max Acceleration: 2.0 m/s/s
		// Max Jerk: 60.0 m/s/s/s
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH, RobotMap.Values.pf_timestep, RobotMap.Values.pf_max_vel,
				RobotMap.Values.pf_max_acc, RobotMap.Values.pf_max_jerk);

		// Generate the trajectory
		trajectory = Pathfinder.generate(points, config);

		// save the calculated trajectory (for later reference)
		File myFile = new File(getPath());
		Pathfinder.writeToCSV(myFile, trajectory);
	}

	// Load pre-generated data from csv file
	public void load_path() {
		File myFile = new File(getPath());
		trajectory = Pathfinder.readFromCSV(myFile);
	}
	
	public void setupTrajectory() {
		Robot.drivetrain.resetEncoders();
		
		// Create the Modifier Object
		TankModifier modifier = new TankModifier(trajectory);

		// Generate the Left and Right trajectories using the original trajectory
		// as the center of the path.  The main option is the wheelbase of the robot
		modifier.modify(RobotMap.Values.robotWheelBase/12.0);  // wheelbase is specified in inches
		
		left = modifier.getLeftTrajectory(); // Get the Left Side
		right = modifier.getRightTrajectory(); // Get the Right Side
		
		int leftCurrentPosition  = Robot.drivetrain.getLeftEncoderTicks();
		int rightCurrentPosition = Robot.drivetrain.getRightEncoderTicks();
		
		leftEncoderFollower =  new EncoderFollower(left);
		leftEncoderFollower.configureEncoder(leftCurrentPosition,  RobotMap.Values.ticksPerRev, RobotMap.Values.robotWheelDia/12.0);
		
		// The first argument is the proportional gain. Usually this will be quite high
		// The second argument is the integral gain. This is unused for motion profiling
		// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
		// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
		//      trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
		// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker
		leftEncoderFollower.configurePIDVA(RobotMap.Values.pf_Kp, RobotMap.Values.pf_Ki, RobotMap.Values.pf_Kd, 
				1 / RobotMap.Values.pf_max_vel, RobotMap.Values.pf_Ka);
		
		// Do the same thing for the right hand side of the robot...
		rightEncoderFollower =  new EncoderFollower(right);
		rightEncoderFollower.configureEncoder(rightCurrentPosition,  RobotMap.Values.ticksPerRev, RobotMap.Values.robotWheelDia/12.0);
		rightEncoderFollower.configurePIDVA(RobotMap.Values.pf_Kp, RobotMap.Values.pf_Ki, RobotMap.Values.pf_Kd, 
				1 / RobotMap.Values.pf_max_vel, RobotMap.Values.pf_Ka);
	}

	// Using the preset trajectory file, run each point.
	public void profileLoop() {
		/*
		 * for (int i = 0; i < trajectory.length(); i++) { Trajectory.Segment seg =
		 * trajectory.get(i);
		 * 
		 * System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", seg.dt, seg.x, seg.y,
		 * seg.position, seg.velocity, seg.acceleration, seg.jerk, seg.heading); }
		 * 
		 * Need to convert the position to feet from encoder ticks
		 */
		int leftPosition =  Robot.drivetrain.getLeftEncoderTicks();
		int rightPosition = Robot.drivetrain.getRightEncoderTicks();
		
		double leftPath = leftEncoderFollower.calculate(leftPosition);
		double rightPath = leftEncoderFollower.calculate(rightPosition);
		
		double desiredHeading = Pathfinder.r2d(leftEncoderFollower.getHeading());
		double angleDiff = (desiredHeading - Robot.drivetrain.getAHRSAngle());
		double turn = RobotMap.Values.pf_Kt * angleDiff;
		
	    Robot.drivetrain.setVoltages(leftPath + turn, rightPath - turn);
	}
}
