package seedu.room.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.commons.core.index.Index;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.AddImageCommand;
import seedu.room.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the AddImageCommand
 * and returns an AddImageCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddImageCommandParser implements Parser<AddImageCommand> {
    @Override
    public AddImageCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] individualArgs = args.split(" ");

        Index index;
        try {
            index = ParserUtil.parseIndex(individualArgs[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddImageCommand.MESSAGE_USAGE));
        }

        return new AddImageCommand(index, individualArgs[2]);
    }
}
