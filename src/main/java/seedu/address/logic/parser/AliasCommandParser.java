package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    private String command;

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");

        switch(args.length) {
        case 1:
            if (args[0].equals("")) {
                return new AliasCommand();
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        case 2:
            if (args[0].equals("-d") || args[0].equals("--delete")) {
                return new AliasCommand(args[1], true);
            }
            String alias = args[0];
            try {
                ParserUtil.parseCommand(Optional.ofNullable(args[1])).ifPresent(cmd -> this.command = cmd);
            } catch (IllegalValueException ive) {
                throw new ParseException(ive.getMessage(), ive);
            }
            return new AliasCommand(alias, command);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
    }

}
