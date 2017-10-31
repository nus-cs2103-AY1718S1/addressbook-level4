package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author goweiwen
/**
 * Parses input arguments and creates a new UnaliasCommand object
 */
public class UnaliasCommandParser implements Parser<UnaliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnaliasCommand
     * and returns an UnaliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnaliasCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");

        if (args.length != 1 || args[0].equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }

        return new UnaliasCommand(args[0]);
    }

}
