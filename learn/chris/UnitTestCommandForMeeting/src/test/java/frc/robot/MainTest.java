package frc.robot;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.commands.StartElevator;
import frc.robot.subsystems.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.sim.DriverStationSim;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

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

		HAL.initialize(500, 0);
		DriverStationSim dsSim = new DriverStationSim();
		dsSim.setDsAttached(true);
		dsSim.setAutonomous(false);
		dsSim.setEnabled(true);
        dsSim.setTest(true);

        CommandScheduler.getInstance().enable();
    }

    @After
	public void afterAll() {
		DriverStation.getInstance().release();
		HAL.releaseDSMutex();
	}

    @Test
    public void testElevatorCreatesMotorControllers() {
        new ElevatorSubsystem(factory);
        verify(factory, times(1)).create(13);
        verify(factory, times(1)).create(16);
    }

    @Test
    public void testElevatorStartThenStopCausesMotorsToStop() {
        ElevatorSubsystem subsystem = new ElevatorSubsystem(factory);

        subsystem.start();
        CommandScheduler.getInstance().run();

        verify(front).set(0.5);
        verify(back).set(-0.5);
        verify(front, times(0)).set(0.0);
        verify(back, times(0)).set(0.0);

        subsystem.stop();
        CommandScheduler.getInstance().run();

        verify(front).set(0.0);
        verify(back).set(-0.0);

        CommandScheduler.getInstance().unregisterSubsystem(subsystem);
    }

    @Test
    public void testElevatorStartCausesMotorsToStart() {
        ElevatorSubsystem subsystem = new ElevatorSubsystem(factory);

        subsystem.start();
        CommandScheduler.getInstance().run();

        verify(front).set(0.5);
        verify(back).set(-0.5);
    }

    @Test
    public void testElevatorStartCallsMotorSetEachSchedulerRunWhileEnabled() {
        ElevatorSubsystem subsystem = new ElevatorSubsystem(factory);

        subsystem.start();
        CommandScheduler.getInstance().run();

        verify(front, times(1)).set(0.5);
        verify(back, times(1)).set(-0.5);

        CommandScheduler.getInstance().run();

        verify(front, times(2)).set(0.5);
        verify(back, times(2)).set(-0.5);

        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().run();

        verify(front, times(2)).set(0.5);
        verify(back, times(2)).set(-0.5);
    }

    @Test
    public void testStartElevatorCommandRunsTheMotors() {
        ElevatorSubsystem subsystem = new ElevatorSubsystem(factory);
        StartElevator cmd = new StartElevator(subsystem);

        CommandScheduler.getInstance().run();

        verify(front, times(1)).set(0.0);
        verify(back, times(1)).set(-0.0);

        cmd.schedule();
        verify(front, times(1)).set(0.0);
        verify(back, times(1)).set(-0.0);
        verify(front, times(0)).set(0.5);
        verify(back, times(0)).set(-0.5);

        CommandScheduler.getInstance().run();

        verify(front, times(1)).set(0.0);
        verify(back, times(1)).set(-0.0);
        verify(front, times(1)).set(0.5);
        verify(back, times(1)).set(-0.5);
        assertEquals(cmd.isScheduled(), false);
    }

}