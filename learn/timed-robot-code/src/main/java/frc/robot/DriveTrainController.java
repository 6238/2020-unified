package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainController implements RobotController {

    // robot drive object (drop six/west coast)
    private DifferentialDrive robotDrive;

    // left and right joysticks
    private JoystickController l_Stick;
    private JoystickController r_Stick;

    // speed multiplier/reducer
    double insanityFactor = 0.5;

    // inverted drive for hatch panel
    boolean reverseDrive = false;

    // joystick drive or no?
    boolean joyDrive = true;

    // left joystick values
    // private double l_JoyX;
    private double l_JoyY;
    // private double l_JoyZ;

    // right joystick values
    // private double r_JoyX;
    private double r_JoyY;
    // private double r_JoyZ;

    DriveTrainController(RobotProperties properties) {
        robotDrive = properties.getRobotDrive();

        l_Stick = properties.getL_Stick();
        r_Stick = properties.getR_Stick();

        SmartDashboard.putNumber("insanityFactor", insanityFactor);
        SmartDashboard.putBoolean("reverseDrive", reverseDrive);
        SmartDashboard.putBoolean("joyDrive", joyDrive);
    }

    @Override
    public boolean performAction(RobotProperties properties) {

        insanityFactor = SmartDashboard.getNumber("insanityFactor", 0.5);
        reverseDrive = SmartDashboard.getBoolean("reverseDrive", false);
        joyDrive = SmartDashboard.getBoolean("joyDrive", true);

        // l_JoyX = l_Stick.getX();
        l_JoyY = l_Stick.getY();
        // l_JoyZ = l_Stick.getZ();
        
        // r_JoyX = r_Stick.getX();
        r_JoyY = r_Stick.getY();
        // r_JoyZ = r_Stick.getZ();
        
        if (joyDrive) {
            if (reverseDrive) {
                robotDrive.tankDrive(-1 * insanityFactor * l_JoyY, -1 * insanityFactor * r_JoyY);
            } else {
                robotDrive.tankDrive(insanityFactor * l_JoyY, insanityFactor * r_JoyY);
            }
        }

        return true;
    }

}