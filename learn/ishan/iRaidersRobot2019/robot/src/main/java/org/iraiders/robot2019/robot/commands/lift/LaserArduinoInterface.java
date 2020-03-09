package org.iraiders.robot2019.robot.commands.lift;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;

public class LaserArduinoInterface extends Command {
  private SerialPort port = new SerialPort(9600, SerialPort.Port.kUSB);

  public LaserArduinoInterface() {
    port.enableTermination();
  }

  @Override
  protected void execute() {
    double distanceInches;

    try {
       distanceInches = Double.parseDouble(port.readString().trim()) * 0.0393701;
    } catch (NumberFormatException e) {
       distanceInches = -1;
    }

    if (distanceInches != -1) System.out.println("This is the distance: " + distanceInches);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
