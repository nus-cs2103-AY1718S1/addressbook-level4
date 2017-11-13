package seedu.address.commons.util;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility that suggests possible command choices based on the string that the user
 * typed into the command bar
 *
 */
public class AutoCompleteUtil {
    //@@author lancehaoh
    /**
     * Gets the system commands that start with a particular prefix
     *
     * @param commandText an arbitrary string
     * @return commandList a list of system commands whose prefix is commandText
     */
    public static List<String> autoCompleteCommand(String commandText, List<String> commandList) {
        return commandList
                .stream()
                .filter(command -> command.toString().toLowerCase().startsWith((commandText.toLowerCase())))
                .collect(Collectors.toList());
    }
}
