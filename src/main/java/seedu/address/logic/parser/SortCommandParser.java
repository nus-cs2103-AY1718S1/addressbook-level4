//@@author Houjisan
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String DATA_FIELD_NAME = "name";
    public static final String DATA_FIELD_PHONE = "phone";
    public static final String DATA_FIELD_EMAIL = "email";
    public static final String DATA_FIELD_ADDRESS = "address";


    /**
     * Used to get the datafield to sort by and check if ignore favourite is inputted
     */
    private static final Pattern SORT_ARGUMENT_FORMAT =
            Pattern.compile("(?<dataField>\\S+)(?<options>.*)");

    private static final String IGNORE_FAV_INPUT = "-ignorefav";
    private static final boolean FAV_NOT_IGNORED = false;
    private static final boolean FAV_IGNORED = true;
    private static final String REVERSE_ORDER_INPUT = "-reverse";
    private static final boolean NOT_REVERSE_ORDER = false;
    private static final boolean REVERSE_ORDER = true;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        final Matcher matcher = SORT_ARGUMENT_FORMAT.matcher(trimmedArgs);

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String dataFieldToSortByFirst = matcher.group("dataField").toLowerCase();
        String options = matcher.group("options").trim();

        boolean isFavIgnored = FAV_NOT_IGNORED;
        boolean isReverseOrder = NOT_REVERSE_ORDER;

        if (!options.isEmpty()) {
            List<String> separateOptions = Arrays.asList(options.split("\\s+"));

            for (String option : separateOptions) {
                String trimmedOption = option.trim();
                if (trimmedOption.equals(IGNORE_FAV_INPUT)) {
                    isFavIgnored = FAV_IGNORED;
                } else if (trimmedOption.equals(REVERSE_ORDER_INPUT)) {
                    isReverseOrder = REVERSE_ORDER;
                } else if (!trimmedOption.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
                }
            }
        }

        switch (dataFieldToSortByFirst) {
        case DATA_FIELD_NAME:
        case DATA_FIELD_PHONE:
        case DATA_FIELD_EMAIL:
        case DATA_FIELD_ADDRESS:
            return new SortCommand(dataFieldToSortByFirst, isFavIgnored, isReverseOrder);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

    }

}

