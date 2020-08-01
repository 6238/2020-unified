package frc.robot.subsystems;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.CANSparkMaxInterface;
import frc.robot.helpers.PIDLoop;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class StabilizedDriveTrainTest {
    @Mock Factory f;
    @Mock
    CANSparkMaxInterface front;
    @Mock
    CANSparkMaxInterface back;
    @Mock Timer timer;
    @Mock Gyro gyro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(f.getSparkMotor(0)).thenReturn(front);
        when(f.getSparkMotor(1)).thenReturn(back);

        CANEncoder mocked_encoder = mock(CANEncoder.class);
        when(mocked_encoder.getPosition()).thenReturn(10.0);
        when(front.getEncoder()).thenReturn(mocked_encoder);

        when(front.get()).thenReturn(0.0);

        when(timer.get()).thenReturn(0.0);
    }

    private PIDLoop makePIDLoop() {
        return new PIDLoop(0.1, 0, 0.4);
    }

    @Test
    public void testEncoder() {
        assertEquals(10, front.getEncoder().getPosition(), 0.001);
    }

    @Test
    public void testSparkInterface() { // Tests if drive still functions(from superinterface SpeedController)
        assertEquals(0.0, front.get(), 0.001);
    }

    @Test
    public void testInit() {
        var dr = new StabilizedDriveTrain(f, gyro, timer, this.makePIDLoop());

        verify(timer, atLeast(1)).get();
    }

    @Test
    public void testPID() {
        when(gyro.getAngle()).thenReturn(0.0);
        when(timer.get()).thenReturn(0.5);
        timer.reset();

        var dr = new StabilizedDriveTrain(f, gyro, timer, this.makePIDLoop());
        dr.setAngle(20.0);
        dr.periodic();

        verify(gyro, atLeast(1)).getAngle();
    }
}
