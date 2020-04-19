# 2020-unified

**One combined repository for Popcorn Penguins 6238 in the 2020 Infinite Recharge Season**

Run `./update` in the root (2020-unified) directory to update all submodules. You could also copy the update executable to `/usr/local/bin/` to allow running the command without the `./`.

## Installations:
Install tools in this order
### WPILib (Mac/Windows/Linux, recommended):
Necessary to write & deploy code.  
https://docs.wpilib.org/en/latest/docs/getting-started/getting-started-frc-control-system/wpilib-setup.html

### LabVIEW (Windows only, not recommended):
Optional, only necessary to do LabVIEW coding. Completely unnecessary for the Popcorn Penguins.  
https://docs.wpilib.org/en/latest/docs/getting-started/getting-started-frc-control-system/labview-setup.html

### FRC Game Tools (Windows only, recommended):
Optional, only necessary if you want to _control_ the robot.  
https://docs.wpilib.org/en/latest/docs/getting-started/getting-started-frc-control-system/frc-game-tools.html

### 3rd Party Libraries (Mac/Windows/Linux, recommended):
Suggested libraries to install:
- Analog Devices ADIS16470 IMU 
  - Go to releases tab
  - Download the latest version (unless it says otherwise)
- CTRE Phoenix Toolsuite 
  - Download the latest version
    - "Installer" for Windows
    - "No Installer" for Mac/Linux
- REV Robotics Color Sensor v3 
  - Scroll down to "Software Libraries", "Direct Download" is offline installer, JSON link is online installer)
- REV Robotics Spark MAX 
  - Scroll down to LabVIEW API, Java API, or C++ API
  - Download correct offline installer or copy correct JSON link
  - Java and C++ have the same installer
  - Spark MAX client is useful but optional (allows you to configure & update Spark MAX motor controllers)

Offline installers are preferred (allows you to install once for all the robot projects you create)  
https://docs.wpilib.org/en/latest/docs/software/wpilib-overview/3rd-party-libraries.html

## Adding libraries to a WPILib project
- Open an existing WPILib project or create a new one
- Open the Command Palette (Cmd + Shift + P on Mac, Ctrl + Shift + P on Windows/Linux, or click the hexagon "W" icon in the top right)
- Search for and run "WPILib: Manage Vendor Libraries"
- Click "Install new libraries (Offline)"
- Check the boxes next to the libraries you want to install (ex. "CTRE-Phoenix")
- Click the blue "OK" button at the top right of the command palette dialog
- Click "Yes" at the dialog asking to build the project
- **WARNING:** In 2020, the WPILib-New-Commands library is added to command-based projects by default. Do not install both the WPILib-New-Commands and WPILib-Old Commands libraries in the same project.

## Creating a WPILib project with unit tests using VS Code
- WPILib > Create a new project
  - Cmd-Shift-P on Mac
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
}```
