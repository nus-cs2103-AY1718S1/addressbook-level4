package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RetagCommand.MESSAGE_INVALID_ARGS;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RetagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RetagCommand object
 */
public class RetagCommandParser implements Parser<RetagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RetagCommand
     * and returns a RetagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RetagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] splittedArgs = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || splittedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetagCommand.MESSAGE_USAGE));
        }

        if (splittedArgs[0].equals(splittedArgs[1])) {
            throw new ParseException(String.format(MESSAGE_INVALID_ARGS, RetagCommand.MESSAGE_USAGE));
        }

        try {
            Tag targetTag = new Tag(splittedArgs[0]);
            Tag newTag = new Tag(splittedArgs[1]);
            return new RetagCommand(targetTag, newTag);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
