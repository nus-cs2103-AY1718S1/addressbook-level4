//@@author quangtdn
package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsPhonePredicate;

/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class FindPhoneCommandParser implements Parser<FindPhoneCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneCommand
     * and returns an FindPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FindPhoneCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
        }

        String[] phoneNumbers = trimmedArgs.split("\\s+");

        return new FindPhoneCommand(new NameContainsPhonePredicate(Arrays.asList(phoneNumbers)));
    }
}
//@@author
