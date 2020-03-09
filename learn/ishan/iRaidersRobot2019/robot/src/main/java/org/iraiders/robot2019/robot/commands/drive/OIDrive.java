package org.iraiders.robot2019.robot.commands.drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.Robot;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;

import java.io.Writer;

import static org.iraiders.robot2019.robot.commands.drive.EngageTurbo.REGULAR_SPEED;

public class OIDrive extends Command {
  private DriveSubsystem driveSubsystem;
  private XboxController xbox = OI.xBoxController;
  private boolean useTankInsteadOfBradford = false;

  private double lastLeftStickVal = 0;
  private double lastRightStickVal = 0;

  //Ultrasonic ultra = new Ultrasonic(RobotMap.ultraSonicPing,RobotMap.ultraSonicEcho);

  private double joystickChangeLimit;

  Writer writer;

  public OIDrive(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    requires(driveSubsystem);
    SmartDashboard.putNumber("JoystickChangeLimit",Robot.prefs.getDouble("JoystickChangeLimit", .03));

  }

  @Override
  protected void initialize() {
    DriverStation.reportWarning("Starting OIDrive", false);
    joystickChangeLimit = SmartDashboard.getNumber("JoystickChangeLimit", Robot.prefs.getDouble("JoystickChangeLimit", .03));
    driveSubsystem.roboDrive.setMaxOutput(Robot.prefs.getFloat("OIMaxSpeed", REGULAR_SPEED));
    /*
    try {
      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Timer.getFPGATimestamp() + ".txt"), StandardCharsets.UTF_8));
    } catch (IOException e) {
      e.printStackTrace();
    }*/
    //ultra.setAutomaticMode(true);
  }

  @Override
  protected void execute() {
    double measuredLeft;
    double measuredRight;

    if (xbox.getRawButtonPressed(7)) {
      useTankInsteadOfBradford = !useTankInsteadOfBradford;
      lastRightStickVal = 0;
      lastLeftStickVal = 0;
      OI.rumbleController(xbox, .5, 500);
    }

    if (useTankInsteadOfBradford) {
      /*
      measuredLeft = DriveSubsystem.slewLimit(xbox.getY(GenericHID.Hand.kLeft), lastLeftStickVal, joystickChangeLimit);
      measuredRight = DriveSubsystem.slewLimit(xbox.getY(GenericHID.Hand.kRight), lastRightStickVal, joystickChangeLimit);
      driveSubsystem.roboDrive.tankDrive(measuredLeft, measuredRight, true);*/
      measuredLeft = xbox.getY(GenericHID.Hand.kLeft);
      measuredRight = -xbox.getX(GenericHID.Hand.kRight);
      driveSubsystem.roboDrive.curvatureDrive(-measuredLeft, measuredRight, false);
    } else {
      measuredLeft =  DriveSubsystem.slewLimit(xbox.getY(GenericHID.Hand.kLeft), lastLeftStickVal, joystickChangeLimit);
      measuredRight = DriveSubsystem.slewLimit(xbox.getX(GenericHID.Hand.kRight), lastRightStickVal, joystickChangeLimit);
      driveSubsystem.roboDrive.arcadeDrive(-measuredLeft, measuredRight, true);

      /*
      try {
        writer.write(Timer.getFPGATimestamp() + "measuredLeft" + measuredLeft + " measuredRight" + measuredRight + " rawXRight" + -xbox.getX(GenericHID.Hand.kRight));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      */
    }

    lastLeftStickVal = measuredLeft;
    lastRightStickVal = measuredRight;
  }

  @Override
  protected void end() {
    driveSubsystem.roboDrive.stopMotor();
    DriverStation.reportWarning("Stopped OIDrive", false);
    //try {writer.close();} catch (Exception ex) {/*ignore*/}
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
