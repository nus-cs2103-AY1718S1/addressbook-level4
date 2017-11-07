package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author freesoup
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
        Index index = null;
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.split(" ").length >= 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));
        }

        try {
            if (trimmedArgs.contains(" ")) {
                index = ParserUtil.parseIndex(trimmedArgs.split(" ")[0]);
                trimmedArgs = trimmedArgs.split(" ")[1];
            }
            Tag toRemoved = new Tag(trimmedArgs);
            if (index == null) {
                return new RemoveTagCommand(toRemoved);
            } else {
                return new RemoveTagCommand(index, toRemoved);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }

}
