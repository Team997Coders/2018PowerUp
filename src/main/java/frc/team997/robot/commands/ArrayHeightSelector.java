package frc.team997.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ArrayHeightSelector extends CommandGroup {

    public ArrayHeightSelector(boolean input) { //true = up, false = down.
    
    	if (input) {
			addSequential(new ArrayUp());
		} else {
			addSequential(new ArrayDown());
		}
    	
    	addSequential(new ElevatorArrayToHeight()); 
    	
    }
}
