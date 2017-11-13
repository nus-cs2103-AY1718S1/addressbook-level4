//@@author danielbrzn
package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    private static final int INDEX_ALIAS = 0;
    private static final int INDEX_COMMAND = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public AliasCommand parse(String userInput) throws ParseException {
        String[] args = userInput.trim().split(" ");

        try {
            String userAlias = args[INDEX_ALIAS];
            String command = args[INDEX_COMMAND];

            return new AliasCommand(userAlias, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AliasCommand.MESSAGE_USAGE));
        }
    }
}
