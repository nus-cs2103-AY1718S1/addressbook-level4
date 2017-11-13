package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ACCEPTED_FIELD_PARAMETERS;
import static seedu.address.logic.commands.SortCommand.ACCEPTED_LIST_PARAMETERS;
import static seedu.address.logic.commands.SortCommand.MESSAGE_INVALID_INPUT;

import java.util.List;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author charlesgoh
/**
 * Parses input arguments and creates a new SortCommand object based on the field and order parameters provided
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final int LIST_ARG_POSITION = 0;
    public static final int FIELD_ARG_POSITION = 1;
    public static final int ORDER_ARG_POSITION = 2;
    public static final int SIZE_OF_ARG_ARRAY = 3;

    public static final List<String> PERSON_FIELD_ARGS = ACCEPTED_FIELD_PARAMETERS.subList(0, 4);
    public static final List<String> TASK_FIELD_ARGS = ACCEPTED_FIELD_PARAMETERS.subList(4, 6);

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Eliminate spaces
        String[] argKeywords = trimmedArgs.split("\\s");

        // Converts arg arrays to lower case to account for caps entries
        for (int i = 0; i < argKeywords.length; i++) {
            argKeywords[i] = argKeywords[i].toLowerCase();
        }

        if (argKeywords.length != SIZE_OF_ARG_ARRAY
                || (argKeywords[0].equals(ACCEPTED_LIST_PARAMETERS.get(0))
                && TASK_FIELD_ARGS.contains(argKeywords[1]))
                || (argKeywords[0].equals(ACCEPTED_LIST_PARAMETERS.get(1))
                && PERSON_FIELD_ARGS.contains(argKeywords[1]))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!SortCommand.ACCEPTED_FIELD_PARAMETERS.contains(argKeywords[FIELD_ARG_POSITION])
                    || !SortCommand.ACCEPTED_ORDER_PARAMETERS.contains(argKeywords[ORDER_ARG_POSITION])
                    || !SortCommand.ACCEPTED_LIST_PARAMETERS.contains(argKeywords[LIST_ARG_POSITION])) {
            throw new ParseException(String.format(MESSAGE_INVALID_INPUT, SortCommand.MESSAGE_USAGE));
        }

        // If there are no problems with the input, return a new instance of SortCommand
        return new SortCommand(argKeywords[LIST_ARG_POSITION],
                argKeywords[FIELD_ARG_POSITION], argKeywords[ORDER_ARG_POSITION]);

    }

}
