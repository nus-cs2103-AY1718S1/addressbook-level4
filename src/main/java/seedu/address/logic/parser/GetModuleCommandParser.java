package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.GetModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new GetModuleCommand object
 */
public class GetModuleCommandParser implements Parser<GetModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetModuleCommand
     * and returns an GetModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetModuleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || trimmedArgs.contains(" ")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetModuleCommand.MESSAGE_USAGE));
        }

        return new GetModuleCommand(trimmedArgs);
    }

}
