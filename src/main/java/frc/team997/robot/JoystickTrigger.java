package frc.team997.robot;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class JoystickTrigger extends Button{
	GenericHID m_joystick;
	int m_axisNum;
	private double THRESHOLD = 0.5;
	
	public JoystickTrigger(GenericHID joystick, int axisNumber) {
		m_joystick = joystick;
		m_axisNum = axisNumber;
	}
	
	public boolean get() {
		if (THRESHOLD < 0) {
			return m_joystick.getRawAxis(m_axisNum) < THRESHOLD;
		} else {
			return m_joystick.getRawAxis(m_axisNum) > THRESHOLD;
		}
	}
}
