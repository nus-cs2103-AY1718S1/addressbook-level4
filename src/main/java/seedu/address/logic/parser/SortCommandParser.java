package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String SORTBYNAME = "name";
    public static final String SORTBYEMAIL = "email";
    public static final String SORTBYPHONE = "phone";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an RemoveTagCommand ob   ject for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        switch (trimmedArgs) {
            case SORTBYNAME:
                return new SortCommand(ReadOnlyPerson.NAMESORT);
            case SORTBYEMAIL:
                return new SortCommand(ReadOnlyPerson.EMAILSORT);
            case SORTBYPHONE:
                return new SortCommand(ReadOnlyPerson.PHONESORT);
            default:
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

}
