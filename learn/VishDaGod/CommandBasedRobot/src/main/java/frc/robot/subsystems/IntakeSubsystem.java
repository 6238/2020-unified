package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  WPI_TalonSRX feeder;
  WPI_TalonSRX frontMagazine;
  WPI_TalonSRX backMagazine;

  public IntakeSubsystem(WPI_TalonFactory factory) {
    //Feeder wheel from the top
    feeder = factory.create(0);

    //Front wheel that takes in balls
    frontMagazine = factory.create(1);
    frontMagazine.setInverted(false);

    //Back wheel that lifts balls
    backMagazine = factory.create(2);

    //Set back and front dependent on each other
    backMagazine.follow(frontMagazine);
    backMagazine.setInverted(InvertType.FollowMaster); 
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  //Run to start all motors
  public void start(){
    frontMagazine.set(0.5); //Also sets backMagazine to 0.5
    feeder.set(0.5);
  }

  //Run to stop all motors
  public void stop(){
    frontMagazine.set(0.0);
    feeder.set(0.0);
  }
}