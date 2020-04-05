# 2020-unified

**One combined repository for Popcorn Penguins 6238 in the 2020 Infinite Recharge Season**

## Creating a WPILib project with unit tests using VS Code

- Cmd-Shift-P; WPILib Create a new project

- Project attributes:
  - type: template > java > Command Robot
  - select your folder in the learn directory
  - ensure Create a new folder is checked
  - project name: UnitTestCommand
  - team number: 6238
  - enable simulation and unitesting by clicking checkbox
  - click generate project

- From the terminal edit the classpath to include the test directory:
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
