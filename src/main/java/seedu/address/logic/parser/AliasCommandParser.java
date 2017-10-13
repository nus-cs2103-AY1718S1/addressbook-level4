package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");

        if (args.length == 1 && args[0].equals("")) {
            return new AliasCommand();
        }

        if (args.length == 2) {
            String alias = args[0];
            String command = ParserUtil.parseCommand(args[1]);

            if (command != null) {
                return new AliasCommand(alias, command);
            }
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }

}
