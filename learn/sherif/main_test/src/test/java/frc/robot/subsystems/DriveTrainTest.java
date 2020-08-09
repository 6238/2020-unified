package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Mockito.*;

public class DriveTrainTest {
    @Mock Factory f;

    @Mock WPI_TalonSRX left_front;
    @Mock WPI_TalonSRX left_mid;
    @Mock WPI_TalonSRX left_end;

    @Mock WPI_TalonSRX right_front;
    @Mock WPI_TalonSRX right_mid;
    @Mock WPI_TalonSRX right_end;
    DriveTrain driveTrain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(f.getTalonMotor(Constants.DRIVE_LEFT_MOTOR_A)).thenReturn(left_front);
        when(f.getTalonMotor(Constants.DRIVE_LEFT_MOTOR_B)).thenReturn(left_mid);
        when(f.getTalonMotor(Constants.DRIVE_LEFT_MOTOR_C)).thenReturn(left_end);

        when(f.getTalonMotor(Constants.DRIVE_RIGHT_MOTOR_A)).thenReturn(right_front);
        when(f.getTalonMotor(Constants.DRIVE_RIGHT_MOTOR_B)).thenReturn(right_mid);
        when(f.getTalonMotor(Constants.DRIVE_RIGHT_MOTOR_C)).thenReturn(right_end);

        this.driveTrain = new DriveTrain(f);
    }

    @Test
    public void TestDrive() {
        this.driveTrain.drive(0.5, 0.2);
        left_front.set(and(leq(0.51), geq(0.49)));
        right_front.set(and(leq(-0.29), geq(-0.31)));
        reset(this.left_front);
        reset(this.right_front);

        this.driveTrain.brake();
        verify(left_front).set(0.0);
        verify(right_front).set(-0.0);
    }
}
