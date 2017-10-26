package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ColourTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code ColourTagCommand} object.
 */
public class ColourTagCommandParser implements Parser<ColourTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ColourTagCommand
     * and returns an ColourTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ColourTagCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColourTagCommand.MESSAGE_USAGE));
        }

        String[] args = trimmedArgs.split(" ");
        if (args.length != 2 && Tag.isValidTagName(args[0])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColourTagCommand.MESSAGE_USAGE));
        }

        try {
            return new ColourTagCommand(new Tag(args[0]), args[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColourTagCommand.MESSAGE_USAGE));
        }
    }
}
