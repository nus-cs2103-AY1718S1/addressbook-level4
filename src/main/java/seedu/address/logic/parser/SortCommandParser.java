package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author khooroko
/**
 * Parses input arguments and creates a new {@code EditCommand} object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
    * Parses the given {@code String} of arguments in the context of the EditCommand
    * and returns an EditCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim().toLowerCase();
        switch (trimmedArgs) {
        case "":
        case "name":
        case "debt":
        case "cluster":
        case "deadline":
            return new SortCommand(trimmedArgs);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
