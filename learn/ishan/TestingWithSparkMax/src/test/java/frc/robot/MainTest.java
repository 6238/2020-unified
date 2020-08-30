package frc.robot;

import org.junit.*;

import static org.mockito.Mockito.mock;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

public class MainTest {

	@Test
	public void name() {
		WPI_TalonSRX talon = mock(WPI_TalonSRX.class);
		CANSparkMax spark = mock(Spark2.class);
	}
}