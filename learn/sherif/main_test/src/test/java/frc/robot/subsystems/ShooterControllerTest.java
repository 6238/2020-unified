package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.interfaces.CANSparkMaxInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ShooterControllerTest {
    @Mock Factory f;

    @Mock CANSparkMaxInterface leftSide;
    @Mock CANSparkMaxInterface rightSide;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(f.getSparkMotor(Constants.SHOOTER_LEFT)).thenReturn(leftSide);
        when(f.getSparkMotor(Constants.SHOOTER_RIGHT)).thenReturn(rightSide);
    }

    @Test
    public void testSpeed() {
        var shooterController = new ShooterController(this.f);
        shooterController.setSpeed(0.5);
        shooterController.periodic();

        verify(leftSide, times(1)).set(0.5);
        verify(rightSide, times(1)).set(-0.5);
        verifyNoMoreInteractions(rightSide);
        verifyNoMoreInteractions(leftSide);

        assertEquals(0.5, shooterController.getSpeed(), 0.02);
    }
}