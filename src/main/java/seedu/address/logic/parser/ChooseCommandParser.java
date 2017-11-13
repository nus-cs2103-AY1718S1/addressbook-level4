package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ARGUMENT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChooseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author fongwz
/**
 * Parses input arguments and creates a ChooseCommand Object
 */
public class ChooseCommandParser implements Parser<ChooseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChooseCommand
     * and returns a ChooseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChooseCommand parse(String args) throws ParseException {
        try {
            String browserType = ParserUtil.parseArgument(args.trim());
            return new ChooseCommand(browserType);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_ARGUMENT, ChooseCommand.MESSAGE_USAGE));
        }
    }
}
