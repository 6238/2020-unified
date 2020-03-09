import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;
import org.junit.jupiter.api.Test;

class DriveTests {
  
  /*
  @Test
  void slewSlowImmedietly() {
    double currentSpeed = .9;
    double targetSpeed = -.1;
    assert DriveSubsystem.slewLimit(targetSpeed, currentSpeed, .01) == -.1;
  }
  
  @Test
  void slewWillNotSurpassTarget() {
    double currentSpeed = .4;
    double targetSpeed = .6;
    for (int i = 0; i < 4; i++) {
      currentSpeed += .1;
      System.out.println("currentSpeed = " +currentSpeed);
      System.out.println(DriveSubsystem.slewLimit(targetSpeed, currentSpeed, .1));
      assert DriveSubsystem.slewLimit(targetSpeed, currentSpeed, .1) == targetSpeed;
    }
  }*/
  
  @Test
  void slewUsesNegativesCorrectly() {
    assert DriveSubsystem.slewLimit(-.6, -.4, .2) == -.6; // Negatives
    assert DriveSubsystem.slewLimit(-.2, -.4, .2) == -.2; // Negatives becoming more positive
  }
  
  @Test
  void deadbandRejectsBelowThreshold() {
    assert OI.getDeadband(0.05, .1) == 0;
    assert OI.getDeadband(-.05, .1) == 0;
  }
  
  @Test
  void deadbandAcceptsAboveThreshold() {
    assert OI.getDeadband(.45, .01) == .44;
    assert OI.getDeadband(-.45, .01) == -.44;
  }
}
