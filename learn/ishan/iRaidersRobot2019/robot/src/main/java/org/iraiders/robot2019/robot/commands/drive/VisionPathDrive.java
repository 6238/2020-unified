package org.iraiders.robot2019.robot.commands.drive;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;

public class VisionPathDrive extends PIDCommand {
  private DriveSubsystem ds;
  
  public NetworkTable cv = NetworkTableInstance.getDefault().getTable("ChickenVision");
  
  public boolean tapeDetected;
  public double targetYaw = 0;
  public VisionPathDrive(DriveSubsystem driveSubsystem) {
    super(.03,0,0);
    this.ds = driveSubsystem;
    requires(driveSubsystem);
    
    this.setSetpoint(0); // Where we are trying to go
    this.getPIDController().setPercentTolerance(2); // How close we want to be to setpoint
    this.getPIDController().setInputRange(-22, 22); // Max and min yaw we have ever detected
    this.getPIDController().setOutputRange(-.75, .75); // 75% max speed
  }
  
  @Override
  protected void initialize() {
    DriverStation.reportWarning("Started VisionDrive command", false);
    tapeDetected = cv.getEntry("tapeDetected").getBoolean(false);
  }
  
  @Override
  protected double returnPIDInput() {
    tapeDetected = cv.getEntry("tapeDetected").getBoolean(false);
    double distance = cv.getEntry("distance").getDouble(-1);
    double yaw = cv.getEntry("tapeYaw").getDouble(-1);
    boolean bankingLeft = cv.getEntry("bankingLeft").getBoolean(false);
    if (!tapeDetected || distance == -1 || yaw == -1){
      this.setSetpoint(targetYaw);
      return targetYaw; // If the tape isn't detected don't do anything
    }
    
    targetYaw = 0.628*(distance-15);
    if(targetYaw > 21){
      targetYaw = 21;
    }else if(targetYaw < 0){
      targetYaw = 0;
    }
    if(!bankingLeft){
      targetYaw *= -1;
    }
    this.setSetpoint(targetYaw);
    return yaw;
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
