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
		
		RobotMap.Values.pf_path_ready = true;
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
	
	private String getLeftPath() {
		return "C:\\Users\\chsrobotics\\Motion-Profile-CSV\\profiletest_left";
	}
	
	private String getRightPath() {
		
		return "C:\\Users\\chsrobotics\\Motion-Profile-CSV\\profiletest_right";
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
		RobotMap.trajectory = Pathfinder.generate(points, config);

		// save the calculated trajectory (for later reference)
		File myFile = new File(getPath());
		Pathfinder.writeToCSV(myFile, RobotMap.trajectory);
	}

	// Load pre-generated data from csv file
	public void load_path() {
		File myLeftFile = new File(getLeftPath());
		RobotMap.leftTrajectory = Pathfinder.readFromCSV(myLeftFile);
		
		File myRightFile = new File(getRightPath());
		RobotMap.rightTrajectory = Pathfinder.readFromCSV(myRightFile);
	}
	
	public void setupTrajectory() { 
		//
		// remember that all distance parameters are in feet!
		//
		Robot.drivetrain.resetEncoders();
		
		// Create the Modifier Object
		TankModifier modifier = new TankModifier(RobotMap.trajectory);

		// Generate the Left and Right trajectories using the original trajectory
		// as the center of the path.  The main option is the wheelbase of the robot
		modifier.modify(RobotMap.Values.robotWheelBase/12.0);  // wheelbase is specified in inches
		
		RobotMap.leftTrajectory = modifier.getLeftTrajectory(); // Get the Left Side
		RobotMap.rightTrajectory = modifier.getRightTrajectory(); // Get the Right Side
		
		// The first argument is the proportional gain. Usually this will be quite high
		// The second argument is the integral gain. This is unused for motion profiling
		// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
		// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
		//      trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
		// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker
		// Do the same thing for the right hand side of the robot...
	}
}
