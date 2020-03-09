package org.iraiders.robot2019.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltraSonicSensorAnalog {
  private AnalogInput ultrasonic;

  public UltraSonicSensorAnalog(int channel) {
    ultrasonic = new AnalogInput(channel);

  }

  public double distanceCalc() {
    // voltage / 2 / 0.00666667 = distance

    return ultrasonic.getAverageVoltage() / 4 / 0.00666667;
  }
}
