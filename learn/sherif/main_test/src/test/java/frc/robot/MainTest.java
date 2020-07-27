package frc.robot;

import frc.robot.subsystems.DriveTrain;
import org.junit.*;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.subsystems.Factory;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class MainTest {
    Factory f;

    @Mock CANSparkFunctions third;

    @Before
    public void setup() {
        this.f = mock(Factory.class);
        CANSparkFunctions front = mock(CANSparkFunctions.class);
        CANSparkFunctions back = mock(CANSparkFunctions.class);

        when(this.f.getMotor(0)).thenReturn(front);
        when(this.f.getMotor(1)).thenReturn(back);
    }

    @Test
    public void testInitialization() {
        var driveSystem = new DriveTrain(this.f);
        // Two sides must be initialized
        verify(f).getMotor(0);
        verify(f).getMotor(1);
        // Right Side must be inverted
        verify(f.getMotor(1)).getInverted();
    }

    @Test
    public void testDrive() {
        var driveSystem = new DriveTrain(this.f);
        driveSystem.drive(0.5f, 0.3f);

        verify(f.getMotor(0)).set(0.2);
        verify(f.getMotor(1)).set(0.8);
    }

    @Test
    public void testBrake() {
        var driveSystem = new DriveTrain(this.f);
        driveSystem.brake();

        verify(f.getMotor(0)).set(0.0);
        verify(f.getMotor(1)).set(0.0);
    }
}