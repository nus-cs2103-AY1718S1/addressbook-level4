package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author zacharytang

/**
 * Parses input arguments and creates a new TimetableCommand object
 */
public class TimetableCommandParser implements Parser<TimetableCommand> {

    private static final String DISPLAY_ONE_PERSON_VALIDATION_REGEX = "-?\\d+";

    private static final String DISPLAY_MULTIPLE_PERSON_COMMA_VALIDATION_REGEX =
            "((-?\\d([\\s+]*)\\,([\\s+]*)(?=-?\\d))|-?\\d)+";

    private static final String DISPLAY_MULTIPLE_PERSON_WHITESPACE_VALIDATION_REGEX =
            "(((-?\\d)([\\s]+)(?=-?\\d))|-?\\d)+";

    private static final String DISPLAY_ALL_LISTED_STRING = "";

    /**
     * Parses the given {@code String} of arguments in the context of the TimetableCommand
     * and returns a TimetableCommand object for execution
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TimetableCommand parse(String args) throws ParseException {

        String preamble = ArgumentTokenizer.tokenize(args).getPreamble();

        if (preamble.equals(DISPLAY_ALL_LISTED_STRING)) {
            // No arguments passed, display all listed users
            ArrayList<Index> indexList = new ArrayList<>();
            return new TimetableCommand(indexList);
        } else if (preamble.matches(DISPLAY_ONE_PERSON_VALIDATION_REGEX)) {
            // Only one argument passed
            try {
                ArrayList<Index> indexList = new ArrayList<>();
                Index index = ParserUtil.parseIndex(args);
                indexList.add(index);

                return new TimetableCommand(indexList);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableCommand.MESSAGE_USAGE));
            }
        } else if (preamble.matches(DISPLAY_MULTIPLE_PERSON_COMMA_VALIDATION_REGEX)) {
            // Multiple arguments passed in, separated by commas
            try {
                ArrayList<Index> indexesList = ParserUtil.parseIndexes(args, ",");
                return new TimetableCommand(indexesList);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableCommand.MESSAGE_USAGE));
            }
        } else if (preamble.matches(DISPLAY_MULTIPLE_PERSON_WHITESPACE_VALIDATION_REGEX)) {
            // Multiple arguments passed in, separated by spaces
            try {
                ArrayList<Index> indexesList = ParserUtil.parseIndexes(args, " ");
                return new TimetableCommand(indexesList);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableCommand.MESSAGE_USAGE));
            }
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableCommand.MESSAGE_USAGE));
    }
}
