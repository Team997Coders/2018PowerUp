package org.usfirst.frc.team997.loops;

/**
 * Runnable class with reports all uncaught throws to CrashTracker
 */
public abstract class CrashTrackingRunnable implements Runnable {

    @Override
    public final void run() {
        try {
            runCrashTracked();
        } catch (Throwable t) {
//           CrashTracker.logThrowableCrash(t);
        	System.out.println(t.getMessage());
        	System.out.println(t.getStackTrace());
            throw t;
        }
    }

    public abstract void runCrashTracked();
}
