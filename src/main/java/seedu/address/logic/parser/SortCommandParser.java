package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author sofarsophie
/**
 * Parses arguments and returns a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * If there are no arguments, returns a SortCommand object with the name prefix by default.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || trimmedArgs.equalsIgnoreCase(PREFIX_NAME.toString())) {
            return new SortCommand(PREFIX_NAME);
        } else if (trimmedArgs.equalsIgnoreCase(PREFIX_ADDRESS.toString())) {
            return new SortCommand(PREFIX_ADDRESS);
        } else if (trimmedArgs.equalsIgnoreCase(PREFIX_EMAIL.toString())) {
            return new SortCommand(PREFIX_EMAIL);
        } else if (trimmedArgs.equalsIgnoreCase(PREFIX_PHONE.toString())) {
            return new SortCommand(PREFIX_PHONE);
        } else if (trimmedArgs.equalsIgnoreCase(PREFIX_GROUP.toString())) {
            return new SortCommand(PREFIX_GROUP);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
//@@author
