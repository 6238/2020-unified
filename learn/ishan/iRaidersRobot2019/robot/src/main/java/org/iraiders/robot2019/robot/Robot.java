package org.iraiders.robot2019.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.iraiders.robot2019.robot.commands.feedback.DeviceListReporter;
import org.iraiders.robot2019.robot.subsystems.ClimbSubsystem;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;
import org.iraiders.robot2019.robot.subsystems.IntakeSubsystem;
import org.iraiders.robot2019.robot.subsystems.LiftSubsystem;

public class Robot extends TimedRobot {
  public static DriveSubsystem driveSubsystem;
  public static LiftSubsystem liftSubsystem;
  public static IntakeSubsystem intakeSubsystem;
  public static ClimbSubsystem climbSubsystem;
  public CanDeviceListFinder canDeviceListFinder;
  public static Preferences prefs = Preferences.getInstance();

  public static OI m_oi;
  public static final Compressor compressor = new Compressor();
  
  //Command m_autonomousCommand;
  //SendableChooser<Command> m_chooser = new SendableChooser<>();

  private boolean subsystemControlsStarted = false;


  @Override
  public void robotInit() {
    initCamera();
    compressor.start();

    m_oi = new OI();
    driveSubsystem = new DriveSubsystem();
    liftSubsystem = new LiftSubsystem();
    intakeSubsystem = new IntakeSubsystem();
    climbSubsystem = new ClimbSubsystem();

    DeviceListReporter deviceListReporter = new DeviceListReporter();
    deviceListReporter.start();
    //for(int y = 0; y<devices.size() ;y++){
     //System.out.println(devices.get(y));

    }
    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    //chooser.addOption("My Auto", new MyAutoCommand());
    //SmartDashboard.putData("Auto mode", m_chooser);
 // }

  private void initCamera() {
    CameraServer cs = CameraServer.getInstance();
    UsbCamera u = cs.startAutomaticCapture();
    u.setResolution(256, 144);
    u.setFPS(30);
  }


  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    //m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    //if (m_autonomousCommand != null) {
    //  m_autonomousCommand.start();
    //}

    initSubsystemControl();
  }
  
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }
  
  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    //if (m_autonomousCommand != null) {
    //  m_autonomousCommand.cancel();
    //}

    initSubsystemControl();
  }
  
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

  }

  @Override
  public void testPeriodic() {
  }

  private void startSubsystems() {
    driveSubsystem.initTeleop();
    liftSubsystem.initTeleop();
    intakeSubsystem.initTeleop();
    climbSubsystem.initTeleop();
  }

  private void initSubsystemControl() {
    startSubsystems();

    if (subsystemControlsStarted) return;

    driveSubsystem.initControls();
    liftSubsystem.initControls();
    intakeSubsystem.initControls();
    climbSubsystem.initControls();

    subsystemControlsStarted = true;
  }
  
  public static void initializeTalonDefaults(WPI_TalonSRX... talons) {
    for (WPI_TalonSRX talon : talons) {
      talon.enableCurrentLimit(true);
      talon.configContinuousCurrentLimit(RobotMap.MAX_MOTOR_STALL_AMPS);
      talon.configPeakCurrentLimit(RobotMap.MAX_MOTOR_FREE_AMPS);
      LiveWindow.add(talon);
    }
  }
  
  public static void initializeSparkDefaults(CANSparkMax... sparks) {
    for (CANSparkMax spark : sparks) {
      spark.setSmartCurrentLimit(RobotMap.MAX_MOTOR_STALL_AMPS, RobotMap.MAX_MOTOR_FREE_AMPS);
    }
  }
}
