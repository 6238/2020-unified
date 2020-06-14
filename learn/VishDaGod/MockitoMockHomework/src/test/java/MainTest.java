import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


import org.junit.*;

import frc.robot.subsystems.*;

public class MainTest {

    private WPI_TalonSRX feeder;
    private WPI_TalonSRX frontMagazine;
    private WPI_TalonSRX backMagazine;

    private WPI_TalonFactory factory;

    @Before
    public void setup(){
        factory = mock(WPI_TalonFactory.class);
        
        feeder = mock(WPI_TalonSRX.class);
        frontMagazine = mock(WPI_TalonSRX.class);
        backMagazine = mock(WPI_TalonSRX.class);

        when(factory.create(0)).thenReturn(feeder);
        when(factory.create(1)).thenReturn(frontMagazine);
        when(factory.create(2)).thenReturn(backMagazine);
    }


    //Test to make sure that the MotorControllers were cleanly created
    @Test
    public void testMotorsCreated(){
        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(factory);

        verify(factory).create(0);
        verify(factory).create(1);
        verify(factory).create(2);

        verify(backMagazine, times(1)).follow(frontMagazine);
        verify(backMagazine, times(1)).setInverted(InvertType.FollowMaster);
    }

    @Test
    public void testThroatStarted(){
        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(factory);

        intakeSubsystem.start();
        
        verify(feeder).set(0.5);
        verify(frontMagazine).set(0.5);
    }

    @Test
    public void testThroatStopped(){
        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(factory);

        intakeSubsystem.stop();

        verify(feeder).set(0.0);
        verify(frontMagazine).set(0.0);
    }
    
}