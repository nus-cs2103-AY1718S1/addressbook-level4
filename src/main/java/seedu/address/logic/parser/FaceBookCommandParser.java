package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.FaceBookCommand;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author RonakLakhotia
/**
 * Parses input arguments and creates a new FacebookCommand object
 */
public class FaceBookCommandParser implements Parser<FaceBookCommand> {

    private static final Logger logger = LogsCenter.getLogger(FaceBookCommand.class);
    /**
     * Parses the given {@code String} of arguments in the context of the FacebookCommand
     * and returns an FacebookCommand object for execution.
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

            assert Keywords.length == 1;
            Index index = ParserUtil.parseIndex(Keywords[0]);

            return new FaceBookCommand(index);

        } catch (IllegalValueException ive) {
            logger.info("You have entered and invalid index");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE));
        }
    }
}
