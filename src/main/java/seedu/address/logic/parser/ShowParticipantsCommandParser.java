package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ShowParticipantsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

// @@author HouDenghao
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ShowParticipantsCommandParser implements Parser<ShowParticipantsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowParticipantsCommand
     * and returns an ShowParticipantsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowParticipantsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShowParticipantsCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowParticipantsCommand.MESSAGE_USAGE));
        }
    }

}
