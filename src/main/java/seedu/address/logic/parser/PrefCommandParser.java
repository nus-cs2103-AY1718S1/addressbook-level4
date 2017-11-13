package seedu.address.logic.parser;
//@@author liuhang0213
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashMap;
import java.util.Objects;

import seedu.address.logic.commands.PrefCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PrefCommand object
 */
public class PrefCommandParser implements Parser<PrefCommand> {

    private HashMap<String, String> prefShortforms;
    {
        prefShortforms = new HashMap<>();
        prefShortforms.put("dp", "DefaultProfilePhoto");
        prefShortforms.put("theme", "Theme");
        prefShortforms.put("abpath", "AddressBookFilePath");
        prefShortforms.put("abname", "AddressBookName");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the PrefCommand
     * and returns an PrefCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public PrefCommand parse(String args) throws ParseException {
        String[] splitArgs = args.trim().split("\\s+");
        String prefKey;
        String newPrefValue;
        newPrefValue = "";

        if (splitArgs.length > 2 || (splitArgs.length == 1 && Objects.equals(splitArgs[0], ""))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrefCommand.MESSAGE_USAGE));
        } else if (splitArgs.length == 2) {
            // The second argument is optional
            newPrefValue = splitArgs[1].trim();
        }

        prefKey = parsePrefShortcut(splitArgs[0].trim());

        return new PrefCommand(prefKey, newPrefValue);
    }

    /**
     * Checks whether the given key is a short form for a preference key
     *
     * @param prefKey User's input value for preference key
     * @return the actual key name if the input was shortcut, otherwise returns the input itself
     */
    private String parsePrefShortcut (String prefKey) {
        if (prefShortforms.containsKey(prefKey)) {
            return prefShortforms.get(prefKey);
        } else {
            return prefKey;
        }
    }
}
