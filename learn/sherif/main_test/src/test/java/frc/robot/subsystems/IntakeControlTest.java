package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import frc.robot.Constants;

public class IntakeControlTest {
    @Mock Factory f;
    @Mock PWMTalonSRX er;
    @Mock PWMTalonSRX el;
    @Mock PWMTalonSRX tr;
    @Mock PWMTalonSRX tl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(f.getTalonMotor(Constants.THROAT_RIGHT)).thenReturn(tr);
        when(f.getTalonMotor(Constants.THROAT_LEFT)).thenReturn(tl);
        when(f.getTalonMotor(Constants.ELEVATOR_RIGHT)).thenReturn(er);
        when(f.getTalonMotor(Constants.ELEVATOR_LEFT)).thenReturn(el);
    }

    @Test
    public void TestThroat() {
        var controller = new IntakeControl(this.f);
        controller.setThroatSpeed(0.5);

        verify(tr).set(-0.5);
        verify(tl).set(0.5);
    }

    @Test
    public void testElevate() {
        var controller = new IntakeControl(this.f);
        controller.setElevatorSpeed(0.3);

        verify(er).set(0.3);
        verify(el).set(0.3);
    }
}
