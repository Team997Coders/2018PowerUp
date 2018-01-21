package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ArrayHeightSelector extends CommandGroup {

    public ArrayHeightSelector(boolean input) { //true = up, false = down.
    	
    	RobotMap.Arrays.heightList[0] = 9000; //TODO placeholders
    	RobotMap.Arrays.heightList[1] = 16239;
    	RobotMap.Arrays.heightList[2] = 22823;
    	
    	int selector = RobotMap.Ints.selectedHeight;
    	
    	if (input) {
			selector = selector + 1;
			if(selector > RobotMap.Arrays.heightList.length - 1) {
				selector = 0;
			}
			System.out.println("selector " + selector);
			System.out.println(RobotMap.Arrays.heightList[selector]);
		} else {
			selector = selector - 1;
			if(selector < 0) {
				selector = RobotMap.Arrays.heightList.length - 1;
			}
			
		}
    	
    	addSequential(new ElevatorToHeight(RobotMap.Arrays.heightList[selector])); 
    	
    	RobotMap.Ints.selectedHeight = selector;
    	
    }
}
