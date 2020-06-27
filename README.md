# 2020-unified

**One combined repository for Popcorn Penguins 6238 in the 2020 Infinite Recharge Season**

Run `./update` in the root (2020-unified) directory to update all submodules (macOS/Linux only). To allow running the command without the `./`, copy the `update` executable to `/usr/local/bin/`.

## Table of Contents
1. [Installations](#installations)
2. [Setup for Vision](#setup-for-vision)
3. [Using WPILib and Visual Studio Code](#using-wpilib-and-visual-studio-code)


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

## Setup for Vision
The recommended method to run vision code is on a Raspberry Pi. However, if this is not a viable option, it is possible to run your code on a Mac or PC. To setup run environments, continue to the section corresponding to your use case.
- [Raspberry Pi](#raspberry-pi)
- [macOS](#macos)
- [Windows](#windows)
- [Linux](#linux)

### Raspberry Pi
Use balenaEtcher to flash a microSD card with the FRC Vision image, then connect to the Raspberry Pi.
https://docs.wpilib.org/en/stable/docs/software/vision-processing/raspberry-pi/installing-the-image-to-your-microsd-card.html

### macOS
Use VMware Fusion to install and set up a Ubuntu virtual machine.
- Download VMware Fusion: https://www.vmware.com/go/getfusion
- Install VMware Fusion
  - Open the .dmg file
  - Open the VMware Fusion app on the disk image (requires admin)
  - After Fusion installs itself, accept the license agreement
  - Contact a member of the programming team for the license key
  - You should now be at the "Select the Installation Method" screen
- Download Ubuntu: https://ubuntu.com/download/desktop
- Drag the ubuntu-xx.xx-desktop-amd64.iso (where xx.xx is the version) into VMware and continue
- Uncheck "Use Easy Install"
- Select "UEFI" for the boot firmware
- Click "Customize Settings" instead of "Finish"
- Save the virtual machine file to your computer or to an external drive (this is a very large file that could reach 10-20GB)
- In the settings window that opens, configure the following options:
  - Hard Disk: under Advanced options, change Bus type to NVMe and click Apply
    - Optional: increase storage to 32GB
  - USB & Bluetooth: under Advanced USB options, change USB Compatibility to USB 3.1
  - Camera: change to preferred camera
- Close the settings window and click the large play button in the VMware Fusion window titled "Ubuntu 64-bit xx.xx"
- After Ubuntu boots up, choose English in the left sidebar and Install Ubuntu
- Ensure English (US) is selected in both the left and right lists and test your keyboard in the text box. Continue.
- Select Minimal installation, check the box next to "Download updates while installing Ubuntu", and uncheck "Install third-party software for graphics and Wi-Fi hardware and additional media formats". Continue.
- Select "Erase disk and install Ubuntu" (This will erase your virtual machine's disk, which is already empty, and will not affect your actual computer). Click "Install Now" and, in the popup, "Continue".
- Select your time zone on the map and continue.
- Enter the name on your user account, then a hostname for the VM, then your desired username and password. "Log in automatically" is recommended. Continue.
- Restart Now when prompted
- VMware disconnects the install "CD" automatically, so when "Please remove the installation medium, then press ENTER" appears, just press the enter key and ignore the error messages.
- Skip connecting online accounts.
- Livepatch is optional, but if set up, requires a Ubuntu One account.
- Sending system info to Canonical is optional.
- Location Services is optional, not necessary.
- Click Done and do not download any of the suggested software.
- If there are software updates available, a prompt to install them will be shown. Select Install Now.
  - Continue to the next step only when the updates have finished installing and you are asked to log back in.
- Continue to the Linux section of these install instructions.

### Windows
Install Ubuntu on Windows using Windows Subsystem for Linux (WSL)
- Install: https://ubuntu.com/wsl#install-ubuntu-on-wsl
- Enable WSL 2: https://ubuntu.com/blog/ubuntu-on-wsl-2-is-generally-available
  - Scroll down to "Enable WSL 2"
- Continue to the Linux section of these install instructions.

### Linux 
This process has only been tested on Ubuntu. For any other distro, you will have to do your own research.
- Open Terminal
- Run `cd Documents` (or path to desired directory) to change the directory to your Documents folder
  - Skip this step if on Windows
- Run `wget https://gist.githubusercontent.com/stevejobs3768/5610e767438a1bb1e38c88f41bcf04ac/raw/01b962e961c417eca8c8b5c69d5525beda912440/install-vim-cv-cscore.sh` to download the install script
- Run `chmod a+x install-vim-cv-cscore.sh` to make the install script executable by all users
- Run `sudo ./install-vim-cv-cscore.sh` to run the install script
- You will be asked multiple times, "Do you want to continue? [Y/n]" You must type y and click enter each time for the install to succeed.
- If a browser does not automatically open, open it yourself and go to http://0.0.0.0:8081
- In the terminal, "got frame at time..." indicates a frame successfully received from the camera and sent to the web server, but "error: timed out getting frame" indicates a dropped frame.

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
