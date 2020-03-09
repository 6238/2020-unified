package org.iraiders.robot2019.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class LineSensorAnalog {
  private AnalogInput lineSensorAnalog;

  public LineSensorAnalog(int channel) {
    lineSensorAnalog = new AnalogInput(channel);
  }


  public boolean getValue() {
    return lineSensorAnalog.getAverageVoltage() > .9;
  }
}
