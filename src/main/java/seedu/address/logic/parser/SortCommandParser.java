package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author freesoup

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String SORTBYNAMEASCENDING = "name asc";
    public static final String SORTBYEMAILASCENDING = "email asc";
    public static final String SORTBYPHONEASCENDING = "phone asc";
    public static final String SORTBYNAMEDESCENDING = "name dsc";
    public static final String SORTBYEMAILDESCENDING = "email dsc";
    public static final String SORTBYPHONEDESCENDING = "phone dsc";


    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        switch (trimmedArgs) {
        case SORTBYNAMEASCENDING:
            return new SortCommand(ReadOnlyPerson.NAMESORTASC);
        case SORTBYEMAILASCENDING:
            return new SortCommand(ReadOnlyPerson.EMAILSORTASC);
        case SORTBYPHONEASCENDING:
            return new SortCommand(ReadOnlyPerson.PHONESORTASC);
        case SORTBYNAMEDESCENDING:
            return new SortCommand(ReadOnlyPerson.NAMESORTDSC);
        case SORTBYEMAILDESCENDING:
            return new SortCommand(ReadOnlyPerson.EMAILSORTDSC);
        case SORTBYPHONEDESCENDING:
            return new SortCommand(ReadOnlyPerson.PHONESORTDSC);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

}
