package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.enablingkeyword.EnablingKeyword;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WelcomeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author CT15
/**
 * Parses input arguments and creates a new WelcomeCommand object
 */
public class WelcomeCommandParser implements Parser<WelcomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WelcomeCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public WelcomeCommand parse(String args) throws ParseException {
        if (args.trim().contains(" ")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WelcomeCommand.MESSAGE_USAGE));
        }

        try {
            EnablingKeyword enablingKeyword = ParserUtil.parseEnablingKeyword(args);
            return new WelcomeCommand(enablingKeyword);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
