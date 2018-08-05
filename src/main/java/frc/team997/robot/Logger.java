package frc.team997.robot;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
public class Logger {
   
    private BufferedWriter writer;
    private boolean logging =false; 
    private final String loggerBoolean = "2_Logging";
    private static Logger instance;
    private String fileName ="";
    private final String SDFileName = "2_File Name: ";
    private long startTime =0;
    private double timeSinceStart; 
    DriverStation ds;
    
    private int max = 0;
    
    private String path;
    
    public static Logger getInstance() {
        if(instance == null) {
            instance = new Logger();
        }
        return instance;
    }
 
    private Logger() {
        this.ds = DriverStation.getInstance();
        SmartDashboard.putBoolean(this.loggerBoolean, this.logging);
        this.logging= SmartDashboard.getBoolean(this.loggerBoolean,false);
        SmartDashboard.putString(this.SDFileName, this.fileName);
        this.fileName = SmartDashboard.getString(SDFileName, "NO NAME");
        File f = new File("/home/lvuser/spartanlogs");
        if(!f.exists()) {
        	f.mkdir();
        }
        
    	File[] files = new File("/spartanlogs").listFiles();
    	if(files != null) {
	        for(File file : files) {
	            if(file.isFile()) {
	                System.out.println(file.getName());
	                try {
	                    int index = Integer.parseInt(file.getName().split("_")[0]);
	                    if(index > max) {
	                        max = index;
	                    }
	                } catch (Exception e){
	                    e.printStackTrace();
	                }
	            }
	        }
    	} else {
    		max = 0;
    	}
    }
	    
    public void openFile() {
    	if((this.wantToLog() || this.ds.isFMSAttached())){
	        try{
	            path = this.getPath();
	            this.writer = new BufferedWriter(new FileWriter(path));
	            this.writer.write("time,voltage,current,left_tics,left_vel,right_tics,right_vel");
	            this.writer.newLine();
	            this.startTime = System.currentTimeMillis();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
    }
    
    private String getPath() {
    	this.fileName = SmartDashboard.getString(SDFileName, "NO NAME");
        if(this.ds.isFMSAttached()) {
            return String.format("/home/lvuser/spartanlogs/%d_%s_%d_log.txt", ++this.max, this.ds.getAlliance().name(), this.ds.getLocation());
        }else if(this.fileName != null){ 
        	return String.format("/home/lvuser/spartanlogs/%d_%s.txt",++this.max,this.fileName);
        }else {
            return String.format("/home/lvuser/spartanlogs/%d_log.txt", ++this.max);
        }
    }
   
    public void logAll() {
    	if(this.wantToLog()){
	        try {
	        	this.timeSinceStart = (System.currentTimeMillis() - this.startTime) / 1000.0; 
	        	
	            this.writer.write(String.format("%.3f", this.timeSinceStart));
	            this.writer.write(String.format(",%.3f", Robot.pdp.getVoltage()));
	            this.writer.write(String.format(",%.3f", Robot.pdp.getTotalCurrent()));
	            this.writer.write(String.format(",%.3f", Robot.drivetrain.getLeftEncoderTicks()));
	            this.writer.write(String.format(",%.3f", Robot.drivetrain.getLeftEncoderRate()));
	            this.writer.write(String.format(",%.3f", Robot.drivetrain.getRightEncoderTicks()));
	            this.writer.write(String.format(",%.3f", Robot.drivetrain.getRightEncoderRate()));
	            
	            this.writer.newLine();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
    }
    
    public boolean wantToLog(){
    	this.logging= SmartDashboard.getBoolean(this.loggerBoolean, false);
    	return this.logging;
    }
    
    
    
    public void close() {
    	if(this.wantToLog()){
	    	if(this.writer != null) {
	            try {
	                this.writer.close();
	            }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	    	}
    	}
    }
}
