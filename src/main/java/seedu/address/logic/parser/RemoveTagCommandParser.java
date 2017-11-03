//@@author arturs68
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        if (!isPrefixPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        try {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG)).get();
            return new RemoveTagCommand(tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }

    private boolean isPrefixPresent(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getValue(prefix).isPresent();
    }
}
