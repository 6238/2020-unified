package frc.robot.subsystems;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.helpers.PIDLoop;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class StabilizedDriveTrainTest {
    @Mock Factory f;
    @Mock SpeedController front;
    @Mock SpeedController back;
    @Mock Timer timer;
    @Mock Gyro gyro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(f.getMotor(0)).thenReturn(front);
        when(f.getMotor(1)).thenReturn(back);
        when(timer.get()).thenReturn(0.0);
    }

    private PIDLoop makePIDLoop() {
        return new PIDLoop(0.1, 0, 0.4);
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

        System.out.println(dr.getAdjust());
    }
}
