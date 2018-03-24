# Implementing Full PID

### For drive to distance
### and...Next steps for Lake O

---

### In This Installment...

- Setting PID constants...a cheat sheet
- PID Options
- Suggested modifications to drive to distance (adding I & D)
- Work left to do before Lake Oswego

---

### Setting PID Constants...a cheat sheet

- Use P to determine an error by which you want to cut power by a defined % (to slow while approaching target)
  - For example, to cut pfactor to .5 (50% power) when error reaches 4000 ticks, P = 0.5 / 4000 = 0.000125
- Tune P until you get close to target in reasonable time

---

### Setting PID Constants (cont)

- Add I of (0.01)P...does the extra boost get you there?
- Does the I overshoot or oscillate?  Add D of 10 to 100 times P.

---

### PID Options

- Roll your own (what we are doing)
  - Pro: You can incorporate multiple sensors
  - Con: Bugs & poor performance (locked into ~20ms scheduling); time consuming to debug

---

### PID Options (cont)

- WPILIB PID classes
  - PIDController, PIDSubsystem, PIDInterface, PIDOutput, etc...
    - [PID Screensteps](https://wpilib.screenstepslive.com/s/3120/m/7912/l/79828-operating-the-robot-with-feedback-from-sensors-pid-control)
  - Pro: Debugged, flexible, more performant (threading used to decouple from WPILIB scheduler)
  - Con: A bit more complex; not as performant as controller-based PID

---

### PID Options (cont)

- Talon PID
  - Pro: Can set constants on web interface for easy tuning; accurate
  - Con: Cannot dial in other sensors easily
  
---

### Drive to distance mods

- Keep roll-your-own PID strategy (tread lightly...)
- Define I & D constants and compute mods to error
- Average out distance remaining between sensors

+++?code=src/org/usfirst/frc/team997/robot/commands/PDriveToDistance.java&lang=java

@[75-88](Add I & D to pfactor calculation)
@[93](Change to call new pfactor calculation)
@[120-127](Change tick distance calc to average two encoders instead of using left encoder only)

###### Team 997: [PDriveToDistance.java](https://github.com/Team997Coders/2018PowerUp/src/org/usfirst/frc/team997/robot/commands/PDriveToDistance.java)

---

## March 23-24 Plan

- Roll out carpet and continue to test practice bot in library
- Check in code from last meeting (resolve merge errors)
- If new PID calc acceptable, regression test auto
- Tune I & D to make drive to distance auto even better

---

## March 23-24 Plan (cont)

- Merge motion profile code into master
- Create scale auto for opposite side paths (2)
- Test same side scale auto (lift amount...make adjustable)
- Regression test comp bot once mechanical finishes

---

## References

- [Talon Reference Manual](https://github.com/CrossTheRoadElec/Phoenix-Documentation/raw/master/Talon%20SRX%20Victor%20SPX%20-%20Software%20Reference%20Manual.pdf)
- [Roll Your Own PID](http://frc-pdr.readthedocs.io/en/latest/control/pid_control.html)
- [WPILIB PID Classes](http://frc-pdr.readthedocs.io/en/latest/control/using_WPILIB's_pid_controller.html)
