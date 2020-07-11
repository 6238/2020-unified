package frc.robot;

import org.junit.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.sim.DriverStationSim;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.commands.RunAllCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.ShooterSubsystem;
//import ler.mocks.rev.MockCANSparkMax;

import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

public class MainTest {

    Factory f;
    
    WPI_TalonSRX elevatorFront;
    WPI_TalonSRX elevatorBack;
    WPI_TalonSRX feeder;

 //   MockCANSparkMax mockShooterLeft;
 //   MockCANSparkMax mockShooterRight;

    CANSparkMax shooterLeft;
    CANSparkMax shooterRight;

    @Before
    public void setup() {
        f = mock(Factory.class);

        elevatorFront = mock(WPI_TalonSRX.class);
        elevatorBack = mock(WPI_TalonSRX.class);
        feeder = mock(WPI_TalonSRX.class);

        // mockShooterLeft = new MockCANSparkMax();
        // mockShooterRight = new MockCANSparkMax();

        // shooterLeft = mockShooterLeft.getMock();
        // shooterRight = mockShooterRight.getMock();

        when(f.createTalon(Constants.kFrontElevatorTalon)).thenReturn(elevatorFront);
        when(f.createTalon(Constants.kRearElevatorTalon)).thenReturn(elevatorBack);

        when(f.createTalon(Constants.kFeederTalon)).thenReturn(feeder);

        /* when(f.createSpark(Constants.kLeftShooterSpark)).thenReturn(shooterLeft);
        when(f.createSpark(Constants.kRightShooterSpark)).thenReturn(shooterRight); */

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
        ElevatorSubsystem e = new ElevatorSubsystem(f);

        verify(f).createTalon(Constants.kFrontElevatorTalon);
        verify(f).createTalon(Constants.kRearElevatorTalon);

        verify(f).createTalon(Constants.kFeederTalon);

        // ShooterSubsystem s = new ShooterSubsystem(f);

        /* verify(f).createSpark(Constants.kLeftShooterSpark);
        verify(f).createSpark(Constants.kRightShooterSpark); */
    }

    /* @Test
    public void testRunAllCommandRunsMotors() {
        ShooterSubsystem shooter = new ShooterSubsystem(f);
        ElevatorSubsystem elevator = new ElevatorSubsystem(f);
        RunAllCommand command = new RunAllCommand(shooter, elevator);

        CommandScheduler.getInstance().run();

        command.schedule();

        verify(elevatorFront, times(1)).set(0.25);
        verify(elevatorBack, times(1)).set(0.25);
        verify(feeder, times(1)).set(0.25);
    } */
}