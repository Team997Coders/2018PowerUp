package org.usfirst.frc.team997.robot;

public class utils {
	//x = clamp(x, -1, 1);
	public static double clamp(double x, double min, double max) {
		if (x > max) {
			return max;
		} else if(x < min) {
			return min;
		} else {
			return x;
		}
	}
}
