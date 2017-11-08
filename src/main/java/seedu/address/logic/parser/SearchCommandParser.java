package seedu.address.logic.parser;
//@@author RonakLakhotia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Name;
import seedu.address.model.person.SearchContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    public static final String INVALID_DETAILS = "You might have entered invalid date or name with invalid characters!";
    private String name;
    private String date;


    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DOB);


        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DOB)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            DateOfBirth date = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB)).get();
            this.name = name.toString();
            this.date = date.toString();

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(INVALID_DETAILS));
        }
        String [] keywords = new String[2];
        keywords[0] = this.name;
        keywords[1] = this.date;

        return new SearchCommand(new SearchContainsKeywordsPredicate(Arrays.asList(keywords)));

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
