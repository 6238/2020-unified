package org.iraiders.robot2019.robot.commands.feedback;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A simple command to report encoder values to SmartDashboard
 * Useful for debugging / stats
 */
public class EncoderReporter extends Command {
  private WPI_TalonSRX talons[];
  private final FeedbackDevice feedbackDevice;
  
  public EncoderReporter(WPI_TalonSRX... talons) {
    this(FeedbackDevice.QuadEncoder, talons);
  }
  
  public EncoderReporter(FeedbackDevice device, WPI_TalonSRX... talons) {
    this.talons = talons;
    this.setRunWhenDisabled(true);
    feedbackDevice = device;
  }
  
  @Override
  protected void initialize() {
    // This won't work if another command reconfigured the talon's selected sensor
    for (WPI_TalonSRX talon : talons) talon.configSelectedFeedbackSensor(feedbackDevice, 0, 0);
  }
  
  @Override
  protected void execute() {
    for (WPI_TalonSRX talon : talons) {
      if (talon.isAlive())
        SmartDashboard.putNumber(talon.getDescription(), talon.getSelectedSensorPosition(0));
    }
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
