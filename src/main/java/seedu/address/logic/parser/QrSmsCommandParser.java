package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.QrSmsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new QrSmsCommand object
 */
//@@author danielweide
public class QrSmsCommandParser implements Parser<QrSmsCommand> {
    /**
     * Parses input arguments and creates a new QrSmsCommand object
     */
    public QrSmsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new QrSmsCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QrSmsCommand.MESSAGE_USAGE));
        }
    }
}
