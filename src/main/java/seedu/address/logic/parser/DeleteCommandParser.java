package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteRemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<Command> {

    static final Pattern DELETE_INDEX_PATTERN = Pattern.compile("-r\\s*(\\d+)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Matcher matcherDeleteRmk = DELETE_INDEX_PATTERN.matcher(trimmedArgs);

        try {
            if (matcherDeleteRmk.find()) {
                Index index = ParserUtil.parseIndex(matcherDeleteRmk.group(1));
                return new DeleteRemarkCommand(index);
            }
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
