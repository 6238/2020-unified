package frc.robot;

import com.revrobotics.CANSparkMax;

public class Spark2 extends CANSparkMax {
	public Spark2(int deviceID, MotorType type) {
		super(deviceID, type);
	}
}