package main.java.frc.team997.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.I2C;

/**
 *
 */
public class Arduino extends Subsystem {
	private I2C i2c;
	private ArrayList<byte[]> commands;
	public Arduino(){
		i2c = new I2C(I2C.Port.kOnboard, 84);
		commands = new ArrayList<byte[]>();
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        
    }
    private int rank(byte input){
    	switch(input){
    	case 'C':
    	return 5;
    	case 'A':
    	return 4;
    	case 'F':
    	return 3;
    	case 'Z':
    	return 2;
    	default:
    	return 1;
    	}
    }
    private boolean isGreater(byte one, byte two){
    	return (rank(one) > rank(two));
    	
    }
    private void addCommand(byte[] command){
    	int position;
    	for(position = 0;
    		commands.size() > position && isGreater(commands.get(position)[0], command[0]);
    		position++){}
    	commands.add(position, command);
    	if(commands.size() == position){
    		i2c.transaction(command, command.length, null, 0);
    	}
    }
    public void sendCancel(byte command){
    	for(int position = 0;
    		commands.size() > position;
    		position++){
    		if(commands.get(position)[0] ==  command){
    			commands.remove(position);
    			if(commands.size() == 0){
    				byte[] byteArray = new byte[1];
    				byteArray[0] = 'K'; 
    				i2c.transaction(byteArray, 1, null, 0);
    			}
    			else if(commands.size() == position){
    				i2c.transaction(commands.get(position -1), 1, null, 0);
    			}
    			break;
    		}
    	}
    }
    public void sendRed(){
		byte[] byteArray = new byte[1];
		byteArray[0] = 'R'; 
		addCommand(byteArray);
	}
    public void sendWhite(){
		byte[] byteArray = new byte[1];
		byteArray[0] = 'W'; 
		addCommand(byteArray);
	}
    public void sendBlue(){
		byte[] byteArray = new byte[1];
		byteArray[0] = 'B'; 
		addCommand(byteArray);
	}
    public void sendBlackout(){
		byte[] byteArray = new byte[1];
		byteArray[0] = 'K'; 
		addCommand(byteArray);
	}
    public void sendGreen(){
		byte[] byteArray = new byte[1];
		byteArray[0] = 'G'; 
		addCommand(byteArray);
	}
    public void sendYellow(){
		byte[] byteArray = new byte[1];
		byteArray[0] = 'Y'; 
		addCommand(byteArray);
	}
    
    public void sendDownChase(byte x){
    	byte[] downChase = new byte[2];
    	downChase[0] = 'C';
    	downChase[1] = x;
    	addCommand(downChase);
    }
    public void sendChase(byte x){
    	byte[] chase = new byte[2];
    	chase[0] = 'C';
    	chase[1] = x;
    	addCommand(chase);
    }
    public void sendFade(byte x){
    	byte[] fade = new byte[2];
    	fade[0] = 'A';
    	fade[1] = x;
    	addCommand(fade);
    }
    public void sendFlash(byte x){
    	byte[] flash = new byte[2];
    	flash[0] = 'F';
    	flash[1] = x;
    	addCommand(flash);
    }
    public void sendSparkle(byte x, byte y){
    	byte[] sparkle = new byte[3];
    	sparkle[0] = 'Z';
    	sparkle[1] = x;
    	sparkle[2] = y;
    	addCommand(sparkle);
    }
    public void sendShift(byte x, byte y){
    	byte[] shift = new byte[3];
    	shift[0] = 'Z';
    	shift[1] = x;
    	shift[2] = y;
    	addCommand(shift);
    }
    
}



