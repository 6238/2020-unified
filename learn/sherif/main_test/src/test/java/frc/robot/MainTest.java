package frc.robot;

import frc.robot.interfaces.CANSparkMaxInterface;
import frc.robot.subsystems.DriveTrain;
import org.junit.*;

import frc.robot.subsystems.Factory;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MainTest {
    Factory f;

    @Mock CANSparkMaxInterface third;

    @Before
    public void setup() {
        this.f = mock(Factory.class);
        CANSparkMaxInterface front = mock(CANSparkMaxInterface.class);
        CANSparkMaxInterface back = mock(CANSparkMaxInterface.class);

        when(this.f.getSparkMotor(0)).thenReturn(front);
        when(this.f.getSparkMotor(1)).thenReturn(back);
    }

    @Test
    public void testInitialization() {
        var driveSystem = new DriveTrain(this.f);
        // Two sides must be initialized
        verify(f).getSparkMotor(0);
        verify(f).getSparkMotor(1);
        // Right Side must be inverted
        verify(f.getSparkMotor(1)).getInverted();
    }

    @Test
    public void testDrive() {
        var driveSystem = new DriveTrain(this.f);
        driveSystem.drive(0.5, 0.3);

        verify(f.getSparkMotor(0)).set(0.2);
        verify(f.getSparkMotor(1)).set(0.8);
    }

    @Test
    public void testBrake() {
        var driveSystem = new DriveTrain(this.f);
        driveSystem.brake();

        verify(f.getSparkMotor(0)).set(0.0);
        verify(f.getSparkMotor(1)).set(0.0);
    }
}