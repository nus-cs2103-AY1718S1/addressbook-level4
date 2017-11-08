package seedu.room.commons.util;

//@@author sushinoya
/**
 * Utility methods related to Collections
 */
public class CommandUtil {

    /**
     * Returns true if the command parsed is an eventbook-related command
     */
    public static boolean isEventCommand(String command) {
        String[] commandComponents = command.split(" ");
        String commandKeyword = commandComponents[0];
        return commandKeyword.equals("deleteEvent") || commandKeyword.equals("addevent")
                || commandKeyword.equals("de") || commandKeyword.equals("ae");
    }
}
