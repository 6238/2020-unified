package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IntakeControlTest {
    @Mock Factory f;
    @Mock WPI_TalonSRX er;
    @Mock WPI_TalonSRX el;
    @Mock WPI_TalonSRX tr;
    @Mock WPI_TalonSRX tl;
    @Mock Solenoid sol;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(f.getTalonMotor(Constants.THROAT_BACK)).thenReturn(tr);
        when(f.getTalonMotor(Constants.THROAT_FRONT)).thenReturn(tl);
        when(f.getTalonMotor(Constants.ELEVATOR_BACK)).thenReturn(er);
        when(f.getTalonMotor(Constants.ELEVATOR_FRONT)).thenReturn(el);
        when(f.getSolenoid(Constants.INTAKE_SOLENOID)).thenReturn(sol);
    }

    @Test
    public void TestThroat() {
        var controller = new IntakeControl(this.f);
        controller.setThroatSpeed(0.5);

        verify(tr).set(-0.5);
        verify(tl).set(0.5);

        controller.stop();

        verify(tr).set(-0.0);
        verify(tl).set(0.0);
    }

    @Test
    public void testElevate() {
        var controller = new IntakeControl(this.f);
        controller.setElevatorSpeed(0.3);

        verify(er).set(0.3);
        verify(el).set(0.3);

        controller.stop();

        verify(er).set(0.0);
        verify(el).set(0.0);
    }

    @Test
    public void testSolenoid() {
        var controller = new IntakeControl(this.f);
        controller.activateSolenoid();

        verify(sol).set(true);

        controller.deactivateSolenoid();

        verify(sol).set(false);
    }
}
