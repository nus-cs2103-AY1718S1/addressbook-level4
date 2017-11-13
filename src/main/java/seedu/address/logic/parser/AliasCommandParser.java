package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author goweiwen
/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String arguments) throws ParseException {

        if (arguments.length() == 0) {
            return new AliasCommand();
        }

        int delimiterPosition = arguments.trim().indexOf(' ');

        if (delimiterPosition == -1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        final String alias = arguments.trim().substring(0, delimiterPosition).trim();
        final String command = arguments.trim().substring(delimiterPosition + 1).trim();
        return new AliasCommand(alias, command);

    }

}
