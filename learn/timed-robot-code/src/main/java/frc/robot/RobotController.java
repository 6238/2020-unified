package frc.robot;

public interface RobotController {

    /**
     * Perform action will get called for each controller in the list of controllers
     * every time Periodic is called. You should make sure that the action only
     * happens in the controller settings you care about. E.g if you only want your
     * action to be enabled when a button is pressed, make sure to check for the
     * button.
     * 
     * @param properties
     * @return true if your action is perfomed, and false otherwise. todo - Its
     *         possible we can use suppliers and completable futures to handle this
     *         in a more complex manner.
     */
    
    boolean performAction(RobotProperties properties);
}
