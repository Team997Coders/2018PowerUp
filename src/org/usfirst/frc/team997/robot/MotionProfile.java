/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team997.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class MotionProfile {
	Trajectory trajectory;
	
	public void init() {
	// 3 Waypoints
	Waypoint[] points = new Waypoint[] {
	    new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
	    new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
	    new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
	};

	// Create the Trajectory Configuration
	//
	// Arguments:
	// Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
	// Sample Count:        SAMPLES_HIGH (100 000)
    //	                      SAMPLES_LOW  (10 000)
    //	                      SAMPLES_FAST (1 000)
	// Time Step:           0.05 Seconds
	// Max Velocity:        1.7 m/s
	// Max Acceleration:    2.0 m/s/s
	// Max Jerk:            60.0 m/s/s/s
	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);

	// Generate the trajectory
	trajectory = Pathfinder.generate(points, config);
	}
	
	public void runProfile() {
		// The distance between the left and right sides of the wheelbase is 0.6m


		// Create the Modifier Object
		TankModifier modifier = new TankModifier(trajectory);

		// Generate the Left and Right trajectories using the original trajectory
		// as the center
		modifier.modify(RobotMap.Values.robotWheelBase);
		
		/*
		 * for (int i = 0; i < trajectory.length(); i++) {
		 *   Trajectory.Segment seg = trajectory.get(i);
		 *   
		 *   System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
		 *       seg.dt, seg.x, seg.y, seg.position, seg.velocity, 
		 *           seg.acceleration, seg.jerk, seg.heading);
		 * }
		 * 
		 */

		Trajectory left  = modifier.getLeftTrajectory();       // Get the Left Side
		Trajectory right = modifier.getRightTrajectory();      // Get the Right Side
	}
}
