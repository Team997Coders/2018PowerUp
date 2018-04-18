package org.usfirst.frc.team997.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/* Starts action to move forward while attempting to collect a cube.
 * Pass in a timeout in seconds for which you wish to wait before giving up.
 */

// CCB: New class to group parallel commands for fetching a cube while driving forward.
public class GoForthAndCollect extends CommandGroup {

    public GoForthAndCollect(double timeout) {
    	addParallel(new TimedUncollect(1, 1, timeout));
    	addParallel(new SlowForwardUntilHaveCube(timeout));
    }
}