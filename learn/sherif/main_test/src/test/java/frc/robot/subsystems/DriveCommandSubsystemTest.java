package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Mockito.*;

public class DriveCommandSubsystemTest {
    @Mock Factory f;

    @Mock WPI_TalonSRX left_front;
    @Mock WPI_TalonSRX left_mid;
    @Mock WPI_TalonSRX left_end;

    @Mock WPI_TalonSRX right_front;
    @Mock WPI_TalonSRX right_mid;
    @Mock WPI_TalonSRX right_end;
    DriveSubsystem driveSubsystem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(f.getTalonMotor(Constants.DRIVE_LEFT_MOTOR_A)).thenReturn(left_front);
        when(f.getTalonMotor(Constants.DRIVE_LEFT_MOTOR_B)).thenReturn(left_mid);
        when(f.getTalonMotor(Constants.DRIVE_LEFT_MOTOR_C)).thenReturn(left_end);

        when(f.getTalonMotor(Constants.DRIVE_RIGHT_MOTOR_A)).thenReturn(right_front);
        when(f.getTalonMotor(Constants.DRIVE_RIGHT_MOTOR_B)).thenReturn(right_mid);
        when(f.getTalonMotor(Constants.DRIVE_RIGHT_MOTOR_C)).thenReturn(right_end);

        this.driveSubsystem = new DriveSubsystem(f);
    }

    @Test
    public void TestDrive() {
        this.driveSubsystem.drive(0.5, 0.2);
        this.driveSubsystem.periodic();
        // These count as sets
        verify(left_front).set(and(leq(0.52), geq(0.48)));
        verify(left_mid).set(and(leq(0.52), geq(0.48)));
        verify(left_end).set(and(leq(0.52), geq(0.48)));

        verify(right_front).set(and(leq(-0.28), geq(-0.32)));
        verify(right_mid).set(and(leq(-0.28), geq(-0.32)));
        verify(right_end).set(and(leq(-0.28), geq(-0.32)));

        this.driveSubsystem.brake();
        verify(left_front).set(0.0);
        verify(right_front).set(-0.0);
    }
}
