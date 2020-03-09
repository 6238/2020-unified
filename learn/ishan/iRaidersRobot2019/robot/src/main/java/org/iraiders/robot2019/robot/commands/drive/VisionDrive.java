package org.iraiders.robot2019.robot.commands.drive;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;

public class VisionDrive extends PIDCommand {
  private DriveSubsystem ds;

  public NetworkTable cv = NetworkTableInstance.getDefault().getTable("ChickenVision");

  public boolean tapeDetected;

  public VisionDrive(DriveSubsystem driveSubsystem) {
    super(.03,0,0);
    this.ds = driveSubsystem;
    requires(driveSubsystem);

    this.setSetpoint(0); // Where we are trying to go
    this.getPIDController().setPercentTolerance(2); // How close we want to be to the setpoint
    this.getPIDController().setInputRange(-22, 22); // Max and min yaw we have ever detected
    this.getPIDController().setOutputRange(-.75, .75); // 75% max speed
  }
  
  @Override
  protected void initialize() {

    DriverStation.reportWarning("Started VisionDrive command", false);
  }

  @Override
  protected double returnPIDInput() {
    tapeDetected = cv.getEntry("tapeDetected").getBoolean(false);
    if (!tapeDetected) return 0; // If the tape isn't detected don't do anything

    return cv.getEntry("tapeYaw").getNumber(0).doubleValue();
  }

  @Override
  protected void usePIDOutput(double output) {
    ds.roboDrive.arcadeDrive(OI.xBoxController.getY(GenericHID.Hand.kLeft), output, false);
  }
  
  @Override
  protected void end() {
    DriverStation.reportWarning("Stopped VisionDrive command", false);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
