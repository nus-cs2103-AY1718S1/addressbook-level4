package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_PARAMS;
import static seedu.address.logic.commands.ExportCommand.BOOK_VALIDATION;

import java.util.Arrays;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kaiyu92
/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Returns true if a given string is a valid book name.
     */
    public static boolean isValidBookParameter(String targetBook) {
        return Arrays.stream(BOOK_VALIDATION).anyMatch(book -> book.equals(targetBook.toLowerCase()));
    }

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (!isValidBookParameter(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_BOOK_PARAMS, ExportCommand.MESSAGE_USAGE));
        }
        return new ExportCommand(trimmedArgs);
    }
}
