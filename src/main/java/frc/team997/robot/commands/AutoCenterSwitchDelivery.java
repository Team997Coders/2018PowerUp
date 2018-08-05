package frc.team997.robot.commands;

import frc.team997.robot.Robot;
import frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

//This auto command is for when we are positioned on the inner side of the exchange
//zone and we want to deliver our preloaded cube to either of the sides.
public class AutoCenterSwitchDelivery extends CommandGroup {

    public AutoCenterSwitchDelivery() {
    	if(Robot.gameData != null && !Robot.gameData.isEmpty()) {
    		Robot.isAuto(true);
    		addParallel(new FlopDown());
    		addParallel(new ElevatorToHeight(RobotMap.Values.elevatorSafeDriveHeight));
    		//addParallel(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    		//Drive forward to avoid exhchange zone. Always 2 feet
    		addSequential(new PDriveToDistance(2 * RobotMap.Values.ticksPerFoot)); 
    	
    		if(Robot.gameData.charAt(0) == 'L') {
    			addSequential(new PDriveToAngle(-65)); //Turn to face switch.
    			addSequential(new PDriveToDistance(3.887 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch. Prev value: 5.5
    			addSequential(new PDriveToAngle(60)); //Turn to face straight again.
    			addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    			addSequential(new PDriveToDistance((RobotMap.Values.autoLeftSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery. Prev value: 4.15
    			//PRETTY ACCURATE NOW!!:^)
    			//When our side of the switch is on the left, this will deliver the cube to
    			//that side.
    		}
    		else {
    			addSequential(new PDriveToAngle(65)); //Turn right to face switch.
    			addSequential(new PDriveToDistance(3.887 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch. Prev value: 5.5
    			addSequential(new PDriveToAngle(-60)); //Turn left to face straight again.
    			addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    			addSequential(new PDriveToDistance((RobotMap.Values.autoRightSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery. Prev value: 4.15
    			//PRETTY ACCURATE NOW :^)
    			//When our side of the switch is on the right, this will deliver the cube to
    			//that side.
    			
    		}
    		//addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
    		addSequential(new FlopDown());
    		addSequential(new TimedUncollect(-0.5, -0.5, 3));
    	} else {
    		System.out.println("Game data is null or empty");
    		Robot.isAuto(false);
    	}
    }
}

