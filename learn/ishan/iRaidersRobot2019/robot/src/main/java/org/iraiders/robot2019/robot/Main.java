package org.iraiders.robot2019.robot;

import edu.wpi.first.wpilibj.RobotBase;


// Don't touch me or stuff will break :)
public final class Main {
  private Main() {
  }

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
