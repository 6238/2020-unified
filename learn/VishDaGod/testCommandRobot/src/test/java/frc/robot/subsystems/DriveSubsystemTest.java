package frc.robot.subsystems;

import org.junit.*;
import org.mockito.*;

import frc.robot.Constants.Drive;
import frc.robot.dashboard.Dashboard;

import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DriveSubsystemTest {
    @Mock Factory f;
    @Mock Dashboard d;

    @Mock WPI_TalonSRX m_leftTalon1;
    @Mock WPI_TalonSRX m_leftTalon2;
    @Mock WPI_TalonSRX m_leftTalon3;

    @Mock WPI_TalonSRX m_rightTalon1;
    @Mock WPI_TalonSRX m_rightTalon2;
    @Mock WPI_TalonSRX m_rightTalon3;
    
    @Mock ADIS16470_IMU kIMU;

    DriveSubsystem m_drive;

    double insanityFactor;
    double sensitivity;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        when(f.createTalon(Drive.kLeftTalon1)).thenReturn(m_leftTalon1);
        when(f.createTalon(Drive.kLeftTalon2)).thenReturn(m_leftTalon2);
        when(f.createTalon(Drive.kLeftTalon3)).thenReturn(m_leftTalon3);
        
        when(f.createTalon(Drive.kRightTalon1)).thenReturn(m_rightTalon1);
        when(f.createTalon(Drive.kRightTalon2)).thenReturn(m_rightTalon2);
        when(f.createTalon(Drive.kRightTalon3)).thenReturn(m_rightTalon3);

        when(f.getIMU()).thenReturn(kIMU);

        this.m_drive = new DriveSubsystem(f, d);
    }

    @Test
    public void testDriving() {
        this.m_drive.drive(0.5, 0.1);
        insanityFactor = this.m_drive.getInsanityFactor();
        sensitivity = this.m_drive.getSensitivity();
        verify(m_leftTalon1).set(and(leq(0.5 * insanityFactor + 0.02), geq(0.5 * insanityFactor - 0.02)));
        verify(m_rightTalon1).set(and(leq(-0.5 * insanityFactor + 0.1 * sensitivity + 0.02), geq(-0.5 * insanityFactor + 0.1 * sensitivity - 0.02)));
        
        this.m_drive.drive(0.7, -0.5);
        insanityFactor = this.m_drive.getInsanityFactor();
        sensitivity = this.m_drive.getSensitivity();
        verify(m_leftTalon1).set(and(leq(0.7 * insanityFactor - 0.5 * sensitivity + 0.02), geq(0.5 * insanityFactor - 0.5 * sensitivity - 0.02)));
        verify(m_rightTalon1).set(and(leq(-0.7 * insanityFactor + 0.02), geq(-0.7 * insanityFactor - 0.02)));
        
        this.m_drive.drive(1, 0.3);
        insanityFactor = this.m_drive.getInsanityFactor();
        sensitivity = this.m_drive.getSensitivity();
        verify(m_leftTalon1).set(and(leq(1 * insanityFactor + 0.02), geq(1 * insanityFactor - 0.02)));
        verify(m_rightTalon1).set(and(leq(-1 * insanityFactor + 0.3 * sensitivity + 0.02), geq(-1 * insanityFactor + 0.3 * sensitivity - 0.02)));
        
        this.m_drive.drive(0.0, 0.0);
        insanityFactor = this.m_drive.getInsanityFactor();
        sensitivity = this.m_drive.getSensitivity();
        verify(m_leftTalon1).set(and(leq(0.02), geq(-0.02)));
        verify(m_rightTalon1).set(and(leq(0.02), geq(-0.02)));
    }
}