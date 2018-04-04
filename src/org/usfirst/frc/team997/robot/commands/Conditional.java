package org.usfirst.frc.team997.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PublicCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import java.util.Enumeration;

public abstract class Conditional extends Command {
    private final PublicCommand _ifTrue,_ifFalse;
    private PublicCommand _running = null;
    
    public Conditional(final Command ifTrue,final Command ifFalse) {
        // Wrap the Commands to expose protected methods
        if(ifTrue != null) {
            _ifTrue  = new PublicCommand(ifTrue);
            for(Enumeration e = _ifTrue.getRequirements();e.hasMoreElements();) {
                requires((Subsystem) e.nextElement());
            }
        } else {
            _ifTrue = null;
        }
        if(ifFalse != null) {
            _ifFalse  = new PublicCommand(ifFalse);
            for(Enumeration e = _ifFalse.getRequirements();e.hasMoreElements();) {
                requires((Subsystem) e.nextElement());
            }
        } else {
            _ifFalse = null;
        }
    }
    
    protected abstract boolean condition();

    protected void initialize() {
        if(condition()) {
            _running = _ifTrue;
        } else {
            _running = _ifFalse;
        }
        if(_running != null) {
            _running._initialize();
            _running.initialize();
        }
    }

    protected void execute() {
        if(_running != null) {
            _running._execute();
            _running.execute();
        }
    }

    protected boolean isFinished() {
        return _running == null || _running.isFinished();
    }

    protected void end() {
        if(_running != null) {
            _running._end();
            _running.end();
        }
    }

    protected void interrupted() {
        if(_running != null) {
            _running._interrupted();
            _running.interrupted();
        }
    }
}