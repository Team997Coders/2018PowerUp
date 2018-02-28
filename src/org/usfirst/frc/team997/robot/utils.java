package org.usfirst.frc.team997.robot;

public class utils {
	
	/**
	 * Returns the value which is between the specified minimum and
	 * maximum value.  If the input is greater than the maximum then 
	 * return the max configured value, if the input is less than the
	 * minimum value then return it instead, otherwise just return 
	 * the value itself.
	 * 
	 * @param  x    input value
	 * @param  min  the minimum value allowed out of the method
	 * @param  max  the maximum value allowed out of the method
	 * @return      the result of the clamp
	 */
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
