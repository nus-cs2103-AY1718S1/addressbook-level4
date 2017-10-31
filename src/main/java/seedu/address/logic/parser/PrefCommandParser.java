package seedu.address.logic.parser;
//@@author liuhang0213
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Objects;

import seedu.address.logic.commands.PrefCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PrefCommand object
 */
public class PrefCommandParser implements Parser<PrefCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PrefCommand
     * and returns an PrefCommand object for execution.
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

        prefKey = splitArgs[0].trim();

        return new PrefCommand(prefKey, newPrefValue);
    }
}
