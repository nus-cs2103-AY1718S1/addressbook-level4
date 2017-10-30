//@@author arturs68
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UngroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;

/**
 * Parses input arguments and creates a new UngroupCommand object
 */
public class UngroupCommandParser implements Parser<UngroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UngroupCommand
     * and returns an UngroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UngroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP_NAME)).get();
            return new UngroupCommand(index, group);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UngroupCommand.MESSAGE_USAGE));
        }
    }
}
