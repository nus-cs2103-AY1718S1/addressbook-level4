package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Relationship;

//@@author Ernest
/**
 * Parses input arguments and creates a new RelationshipCommand object
 */
public class RelationshipCommandParser implements Parser<RelationshipCommand> {
    /**
      * Parses the given {@code String} of arguments in the context of the RelationshipCommand
      * and returns an RelationshipCommand object for execution.
      *
      * @throws ParseException if the user input does not conform the expected format
      */
    public RelationshipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RELATIONSHIP);

        Index index;

        if (!isPrefixPresent(argMultimap, PREFIX_RELATIONSHIP)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, RelationshipCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        String relation = argMultimap.getValue(PREFIX_RELATIONSHIP).orElse("");

        return new RelationshipCommand(index, new Relationship(relation));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
