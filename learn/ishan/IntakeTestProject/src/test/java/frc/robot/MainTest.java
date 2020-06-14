package frc.robot;

import org.junit.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.sim.DriverStationSim;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TalonFactory;

import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MainTest {
    TalonFactory f;

    WPI_TalonSRX outer;

    WPI_TalonSRX throatLeft;
    WPI_TalonSRX throatRight;

    WPI_TalonSRX elevatorBack;
    WPI_TalonSRX elevatorFront;

    @Before
    public void setup() {
        f = mock(TalonFactory.class);

        outer = mock(WPI_TalonSRX.class);

        throatLeft = mock(WPI_TalonSRX.class);
        throatRight = mock(WPI_TalonSRX.class);

        elevatorBack = mock(WPI_TalonSRX.class);
        elevatorFront = mock(WPI_TalonSRX.class);

        when(f.create(Constants.kOuterPort)).thenReturn(outer);

        when(f.create(Constants.kThroatLeftPort)).thenReturn(throatLeft);
        when(f.create(Constants.kThroatRightPort)).thenReturn(throatRight);

        when(f.create(Constants.kElevatorBackPort)).thenReturn(elevatorBack);
        when(f.create(Constants.kElevatorFrontPort)).thenReturn(elevatorFront);

        HAL.initialize(500, 0);
        DriverStationSim dsSim = new DriverStationSim();
        dsSim.setDsAttached(true);
        dsSim.setAutonomous(false);
        dsSim.setEnabled(true);
        dsSim.setTest(true);
    }

    @After
    public void afterAll() {
        DriverStation.getInstance().release();
        HAL.releaseDSMutex();
    }

    @Test
    public void testSubsystemCreatesMotorControllers() {
        IntakeSubsystem i = new IntakeSubsystem(f);

        verify(f).create(Constants.kOuterPort);

        verify(f).create(Constants.kThroatLeftPort);
        verify(f).create(Constants.kThroatRightPort);

        verify(f).create(Constants.kElevatorBackPort);
        verify(f).create(Constants.kElevatorFrontPort);
    }

    @Test
    public void testThroatFollowedInvertedStarted() {
        IntakeSubsystem i = new IntakeSubsystem(f);

        verify(throatRight, times(1)).follow(throatLeft);
        verify(throatRight, times(1)).setInverted(true);

        i.startThroat();
        i.periodic();

        verify(throatLeft).set(0.5);
    }

    @Test
    public void testCommandThroatStartStop() {
        IntakeSubsystem i = new IntakeSubsystem(f);

        verify(throatRight, times(1)).follow(throatLeft);
        verify(throatRight, times(1)).setInverted(true);

        i.startThroat();
        CommandScheduler.getInstance().run();

        verify(throatLeft).set(0.5);
        verify(throatLeft, times(0)).set(0.0);

        i.stopThroat();
        CommandScheduler.getInstance().run();

        verify(throatLeft).set(0.0);

        CommandScheduler.getInstance().unregisterSubsystem(i);
    }

    @Test
    public void testThroatStopped() {
        IntakeSubsystem i = new IntakeSubsystem(f);

        i.stopThroat();
        i.periodic();

        verify(throatLeft).set(0.0);
    }

    @Test
    public void testOuterStarted() {
        IntakeSubsystem i = new IntakeSubsystem(f);

        i.startOuter();
        i.periodic();

        verify(outer).set(0.5);
    }

    @Test
    public void testOuterStopped() {
        IntakeSubsystem i = new IntakeSubsystem(f);

        i.stopOuter();
        i.periodic();

        verify(outer).set(0.0);
    }

    @Test
    public void testElevatorFollowedInvertedStarted() {
        IntakeSubsystem i = new IntakeSubsystem(f);

        verify(elevatorBack, times(1)).follow(elevatorFront);
        verify(elevatorBack, times(1)).setInverted(true);

        i.startElevator();
        i.periodic();

        verify(elevatorFront).set(0.5);
    }

    @Test
    public void testElevatorStopped() {
        IntakeSubsystem i = new IntakeSubsystem(f);

        i.stopElevator();
        i.periodic();

        verify(elevatorFront).set(0.0);
    }
}