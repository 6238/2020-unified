package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import frc.robot.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DriveTrainTest {
    @Mock Factory f;

    @Mock PWMTalonSRX left_front;
    @Mock PWMTalonSRX left_mid;
    @Mock PWMTalonSRX left_end;

    @Mock PWMTalonSRX right_front;
    @Mock PWMTalonSRX right_mid;
    @Mock PWMTalonSRX right_end;
    DriveTrain driveTrain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(f.getTalonMotor(Constants.LEFT_FRONT)).thenReturn(left_front);
        when(f.getTalonMotor(Constants.LEFT_MID)).thenReturn(left_mid);
        when(f.getTalonMotor(Constants.LEFT_END)).thenReturn(left_end);

        when(f.getTalonMotor(Constants.RIGHT_FRONT)).thenReturn(right_front);
        when(f.getTalonMotor(Constants.RIGHT_MID)).thenReturn(right_mid);
        when(f.getTalonMotor(Constants.RIGHT_END)).thenReturn(right_end);

        this.driveTrain = new DriveTrain(f);
    }

    @Test
    public void TestDrive() {
        this.driveTrain.drive(0.5, 0.2);

        verify(left_front).set(0.5);
        verify(right_front).set(0.3);
    }
}
