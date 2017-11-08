package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Relationship;

/**
 * Parses input arguments and creates a new RelationshipCommand object
 */
public class RelationshipCommandParser implements Parser<RelationshipCommand> {
    /**
      * Parses the given {@code String} of arguments in the context of the RelationshipCommand
      * and returns an RelationshipCommand object for execution.
      * @throws ParseException if the user input does not conform the expected format
      */
    public RelationshipCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RELATIONSHIP);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelationshipCommand.MESSAGE_USAGE));
        }

        String relation = argMultimap.getValue(PREFIX_RELATIONSHIP).orElse("");
        return new RelationshipCommand(index, new Relationship(relation));
    }
}
