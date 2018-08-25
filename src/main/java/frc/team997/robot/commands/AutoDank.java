/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package main.java.frc.team997.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.java.frc.team997.robot.*;

public class AutoDank extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoDank(String gameData) {
    if(gameData != null && !gameData.isEmpty()) {
      Robot.isAuto(true);
      addParallel(new FlopDown());
      addParallel(new ElevatorToHeight(RobotMap.Values.elevatorSafeDriveHeight));
      //addParallel(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
      //Drive forward to avoid exhchange zone. Always 2 feet
      addSequential(new PDriveToDistance(2 * RobotMap.Values.ticksPerFoot)); 
    
      if(gameData.charAt(0) == 'L') {
        addSequential(new PDriveToAngle(-65)); //Turn to face switch.
        addSequential(new PDriveToDistance(3.887 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch. Prev value: 5.5
        addSequential(new PDriveToAngle(60)); //Turn to face straight again.
        addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
        addSequential(new PDriveToDistance((RobotMap.Values.autoLeftSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery. Prev value: 4.15

        addSequential(new FlopDown());
        addSequential(new TimedUncollect(-0.5, -0.5, 3));

        addSequential(new PDriveToDistance(-(RobotMap.Values.autoLeftSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot));
        addSequential(new ElevatorToHeight(RobotMap.Values.elevatorBottomHeight));
        addSequential(new PDriveToAngle(-65));
        addSequential(new PDriveToDistance(-(3.887 * RobotMap.Values.ticksPerFoot)));
        addSequential(new PDriveToAngle(65));

        addSequential(new GoForthAndCollect(2), 2);
        addSequential(new PDriveToAngle(-65));
        addSequential(new PDriveToDistance(-(3.887 * RobotMap.Values.ticksPerFoot))); //Diagonal length towards our switch. Prev value: 5.5
        addSequential(new PDriveToAngle(60)); //Turn left to face straight again.
        addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
        addSequential(new PDriveToDistance((RobotMap.Values.autoRightSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery. Prev value: 4.15
        //PRETTY ACCURATE NOW!!:^)
        //When our side of the switch is on the left, this will deliver the cube to
        //that side.
        addSequential(new FlopDown());
        addSequential(new TimedUncollect(-0.5, -0.5, 3));
      }
      else {
        addSequential(new PDriveToAngle(65)); //Turn right to face switch.
        addSequential(new PDriveToDistance(3.887 * RobotMap.Values.ticksPerFoot)); //Diagonal length towards our switch. Prev value: 5.5
        addSequential(new PDriveToAngle(-60)); //Turn left to face straight again.
        addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
        addSequential(new PDriveToDistance((RobotMap.Values.autoRightSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery. Prev value: 4.15

        addSequential(new FlopDown());
        addSequential(new TimedUncollect(-0.5, -0.5, 3));

        addSequential(new PDriveToDistance(-(RobotMap.Values.autoLeftSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot));
        addSequential(new ElevatorToHeight(RobotMap.Values.elevatorBottomHeight));
        addSequential(new PDriveToAngle(65));
        addSequential(new PDriveToDistance(-(3.887 * RobotMap.Values.ticksPerFoot)));
        addSequential(new PDriveToAngle(-65));

        addSequential(new GoForthAndCollect(2), 2);
        addSequential(new PDriveToDistance(-(3.887 * RobotMap.Values.ticksPerFoot))); //Diagonal length towards our switch. Prev value: 5.5
        addSequential(new PDriveToAngle(65)); //Turn left to face straight again.
        addSequential(new PDriveToDistance((3.887 * RobotMap.Values.ticksPerFoot)));
        addSequential(new PDriveToAngle(-60));
        addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
        addSequential(new PDriveToDistance((RobotMap.Values.autoRightSwitchTotal - 5.887) * RobotMap.Values.ticksPerFoot)); //Drive to reach switch for cube delivery. Prev value: 4.15

        addSequential(new TimedUncollect(-0.5, -0.5, 3));
        //PRETTY ACCURATE NOW :^)
        //When our side of the switch is on the right, this will deliver the cube to
        //that side.
        /*addSequential(new FlopDown());
        addSequential(new TimedUncollect(-0.5, -0.5, 3));*/
      }
      //addSequential(new ElevatorToHeight(RobotMap.Values.elevatorSwitchHeight));
      
    } else {
      System.out.println("Game data is null or empty");
      Robot.isAuto(false);
    }
  }
}
