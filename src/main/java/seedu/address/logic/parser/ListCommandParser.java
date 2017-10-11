package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.model.tag.Tag;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        try {
            if(!isPrefixPresent(argMultimap, PREFIX_TAG)) {
                return new ListCommand();
            }

            Set<Tag> inputTag = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            List<Tag> inputTagNames = new ArrayList<>(inputTag);

            return new ListCommand(new PersonContainsKeywordsPredicate(inputTagNames));

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the tag prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix tagPrefix) {
        return Stream.of(tagPrefix).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}