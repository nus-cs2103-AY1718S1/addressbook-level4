package seedu.address.logic.parser;
//@@author RonakLakhotia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FaceBookCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MapCommand object
 */
public class FaceBookCommandParser implements Parser<FaceBookCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns an MapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FaceBookCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] Keywords = trimmedArgs.split("\\s+");

            if (trimmedArgs.isEmpty() || Keywords.length != 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE));
            }


            Index index = ParserUtil.parseIndex(Keywords[0]);

            return new FaceBookCommand(index);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE));
        }
    }
}
