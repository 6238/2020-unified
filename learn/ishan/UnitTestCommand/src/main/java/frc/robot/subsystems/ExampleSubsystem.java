/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {
    MotorController front;
    MotorController back;

    double speed;

    /**
     * Creates a new ExampleSubsystem.
     */
    public ExampleSubsystem(MotorControllerFactory m) {
        front = m.create(0);
        back = m.create(1);
    }

    public void start() {
        speed = 0.5;
    }
    
    public void stop() {
        speed = 0;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        front.set(speed);
        back.set(-speed);
    }
}