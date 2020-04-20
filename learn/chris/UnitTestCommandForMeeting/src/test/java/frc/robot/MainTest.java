package frc.robot;

import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.subsystems.*;


public class MainTest {

    MotorControllerFactory factory;
    WPI_TalonSRX front;
    WPI_TalonSRX back;

    @Before
    public void setup() {
        factory = mock(MotorControllerFactory.class);
        front = mock(WPI_TalonSRX.class);
        back = mock(WPI_TalonSRX.class);

        when(factory.create(13)).thenReturn(front);
        when(factory.create(16)).thenReturn(back);
    }

    @Test
    public void testElevatorCreatesMotorControllers() {
        ElevatorSubsystem subsystem = new ElevatorSubsystem(factory);
        verify(factory).create(13);
        verify(factory).create(16);
    }

    @Test
    public void testElevatorStopCausesMotorsToStop() {
        ElevatorSubsystem subsystem = new ElevatorSubsystem(factory);
        subsystem.stop();
        subsystem.periodic();

        verify(front).set(0.0);
        verify(back).set(-0.0);
    }

    @Test
    public void testElevatorStartCausesMotorsToStart() {
        ElevatorSubsystem subsystem = new ElevatorSubsystem(factory);
        subsystem.start();
        subsystem.periodic();

        verify(front).set(0.5);
        verify(back).set(-0.5);
    }
}