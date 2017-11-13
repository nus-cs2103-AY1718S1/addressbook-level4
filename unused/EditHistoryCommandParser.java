package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author sofarsophie
public class EditHistoryCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditHistoryCommand
     * and returns an EditHistoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public EditHistoryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EditHistoryCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditHistoryCommand.MESSAGE_USAGE));
        }
    }
}
//@@author
