package org.iraiders.robot2019.robot.commands.feedback;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.CanDeviceListFinder;

import java.util.List;

public class DeviceListReporter extends Command {
  private String[] deviceArray;

  public DeviceListReporter(){
    CanDeviceListFinder canDeviceListFinder = new CanDeviceListFinder();
    List<String> devices = canDeviceListFinder.getDeviceList();
    deviceArray = devices.toArray(new String[devices.size()]);
  }
  @Override
  protected void execute() {
    SmartDashboard.putStringArray("CAN devices", deviceArray);
  }
  @Override
  protected boolean isFinished() {
    return false;
  }
}
