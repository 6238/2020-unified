# 2020-unified

**One combined repository for Popcorn Penguins 6238 in the 2020 Infinite Recharge Season**

Run `./update` in the root (2020-unified) directory to update all submodules (macOS/Linux only). To allow running the command without the `./`, copy the `update` executable to `/usr/local/bin/`.

## Table of Contents
1. [Installations](#installations)
2. [Setup for Vision](#setup-for-vision)
3. [Sample Repositories](#sample-repositories)
4. [Using WPILib and Visual Studio Code](#using-wpilib-and-visual-studio-code)


## Installations
Install tools in this order:
1. [WPILib](#wpilib) 
2. [LabVIEW](#labview) 
3. [FRC Game Tools](#frc-game-tools) 
4. [FRC Radio Configuration](#frc-radio-configuration-2000) 
5. [3rd Party Libraries](#3rd-party-libraries) 
6. [Spark MAX Client](#spark-max-client) 

### WPILib
#### (macOS/Windows/Linux, required)
Necessary to write & deploy code.  
https://docs.wpilib.org/en/latest/docs/getting-started/getting-started-frc-control-system/wpilib-setup.html

### LabVIEW
#### (Windows only, not recommended)
Necessary to do LabVIEW coding. Completely unnecessary for the Popcorn Penguins. If you decide to do this, you must do it **before** installing the FRC Game Tools and the 3rd Party Libraries.
https://docs.wpilib.org/en/latest/docs/getting-started/getting-started-frc-control-system/labview-setup.html

### FRC Game Tools
#### (Windows only, optional)
Necessary if you want to *control* the robot.
https://docs.wpilib.org/en/latest/docs/getting-started/getting-started-frc-control-system/frc-game-tools.html

### FRC Radio Configuration 20.0.0
#### (Windows only, optional)
Necessary to program the robot radios.
https://firstfrc.blob.core.windows.net/frc2020/Radio/FRC_Radio_Configuration_20_0_0.zip

### 3rd Party Libraries
#### (macOS/Windows/Linux, required)
- Analog Devices ADIS16470 IMU
  - https://github.com/juchong/ADIS16470-RoboRIO-Driver/releases/latest
  - Go to releases tab
  - Download the latest version (unless it says otherwise)
- CTRE Phoenix Toolsuite 
  - http://www.ctr-electronics.com/hro.html#product_tabs_technical_resources
  - Download the latest version
    - "Installer" for Windows
    - "No Installer" for macOS/Linux
- REV Robotics Color Sensor v3
  - https://www.revrobotics.com/rev-31-1557/
  - Scroll down to "Software Libraries"
  - "Java/C++ SDK Direct Download"
- REV Robotics Spark MAX
  - https://www.revrobotics.com/sparkmax-software/#java-api
  - "Download Latest Java API"
- **How to install:** https://docs.wpilib.org/en/latest/docs/software/wpilib-overview/3rd-party-libraries.html#the-mechanism-c-java

Offline installers are preferred (allows you to install once for all the robot projects you create)  
https://docs.wpilib.org/en/latest/docs/software/wpilib-overview/3rd-party-libraries.html

### Spark MAX Client
#### (Windows only, optional)
Necessary to configure Spark MAX's.
https://www.revrobotics.com/sparkmax-software/#spark-max-client-application

## Sample Repositories
List of repositories with example code:
- http://github.com/wpilibsuite/frc-docs
- http://github.com/wpilibsuite/allwpilib
- http://github.com/REVrobotics/SPARK-MAX-Examples
- http://github.com/REVrobotics/Color-Sensor-v3-Examples
- http://github.com/CrossTheRoadElec/Phoenix-Examples-Languages

## Setup for Vision
The recommended method to run vision code is on a Raspberry Pi. However, if this is not a viable option, it is possible to run your code on a Mac or PC. To setup run environments, continue to the section corresponding to your use case.
- [Raspberry Pi](#raspberry-pi)
- Further instructions to install on macOS, Windows, and Linux have been removed for now, as it is not clear whether those are efficient solutions to run the necessary tools for vision.

### Raspberry Pi
Use balenaEtcher to flash a microSD card with the FRC Vision image, then connect to the Raspberry Pi.
https://docs.wpilib.org/en/stable/docs/software/vision-processing/raspberry-pi/installing-the-image-to-your-microsd-card.html

## Using WPILib and Visual Studio Code

### Adding libraries to a WPILib project
- Open an existing WPILib project or create a new one
- Open the Command Palette (Cmd + Shift + P on macOS, Ctrl + Shift + P on Windows/Linux, or click the hexagon "W" icon in the top right)
- Search for and run "WPILib: Manage Vendor Libraries"
- Click "Install new libraries (Offline)"
- Check the boxes next to the libraries you want to install (ex. "CTRE-Phoenix")
- Click the blue "OK" button at the top right of the command palette dialog
- Click "Yes" at the dialog asking to build the project
- **WARNING:** In 2020, the WPILib-New-Commands library is added to command-based projects by default. Do not install both the WPILib-New-Commands and WPILib-Old Commands libraries in the same project.

### Creating a WPILib project with unit tests using Visual Studio Code
- WPILib > Create a new project
  - Cmd-Shift-P on macOS
  - Ctrl-Shift-P on Windows
- Project attributes:
  - type: Template > Java > Command Robot
  - Select your folder in the "learn" directory
  - Ensure "Create a new folder" is checked
  - Project name: UnitTestCommand
  - Team number: 6238
  - Enable simulation and unit testing by clicking checkbox
  - Click "Generate Project"
- Add the path to the test directory to the `.classpath` file

The `.classpath` file is in the topmost folder of the project. Since the filename starts with a `.` the file is hidden by default.  It does not appear when you list the files using the `ls` command in the terminal or when browsing for files in VS Code. To make it appear in the terminal invoke `ls` with the `-a` flag: `ls -a`.  To make it appear in the VS Code File open dialog box hit `Cmd-Shift+.`. Open the file and edit it to include the section below referencing the `src/test/java`.
```
echo '<?xml version="1.0" encoding="UTF-8"?>
<classpath>
        <classpathentry kind="src" output="bin/main" path="src/main/java">
                <attributes>
                        <attribute name="gradle_scope" value="main"/>
                        <attribute name="gradle_used_by_scope" value="main,test"/>
                </attributes>
        </classpathentry>
        <classpathentry kind="src" output="bin/test" path="src/test/java">
                <attributes>
                        <attribute name="gradle_scope" value="test"/>
                        <attribute name="gradle_used_by_scope" value="test"/>
                        <attribute name="test" value="true"/>
                </attributes>
        </classpathentry>
        <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-11/"/>
        <classpathentry kind="con" path="org.eclipse.buildship.core.gradleclasspathcontainer"/>
        <classpathentry kind="output" path="bin/default"/>
</classpath>' > .classpath
```
- Close and reopen the folder in VS Code
- Add the file src/test/java/frc/robot/MainTest.java:
```
package frc.robot;

import org.junit.*;

public class MainTest {

    @Test
    public void name() {
        Assert.assertEquals(2, 2);
    }
}
```
- Add support for Mockito by adding the following lines to the build.gradle file
```
repositories { jcenter() }
dependencies {
  testImplementation 'org.mockito:mockito-core:3.+'
}
```
