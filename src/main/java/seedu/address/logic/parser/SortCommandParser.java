package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_ASCENDING_WORD;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_DEFAULT_ORDER;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_DESCENDING_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author k-l-a
/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * @return SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new SortCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_ASCENDING_WORD);
        } else if (isSortArgument(trimmedArgs)) {
            return new SortCommand(ARGUMENT_DEFAULT_ORDER, trimmedArgs);
        } else if (isSortablePrefix(trimmedArgs)) {
            return new SortCommand(trimmedArgs, ARGUMENT_ASCENDING_WORD);
        }

        String[] splitArgs = trimmedArgs.split("\\s+");
        if (splitArgs.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String prefix = splitArgs[0];
        String order = splitArgs[1];
        if (isSortablePrefix(prefix) && isSortArgument(order)) {
            return new SortCommand(prefix, order);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if the given string prefix is a sortable prefix (n/, p/, e/, a/, b/).
     */
    public boolean isSortablePrefix(String prefixString) {
        return prefixString.equals(PREFIX_NAME.getPrefix()) || prefixString.equals(PREFIX_PHONE.getPrefix())
                || prefixString.equals(PREFIX_ADDRESS.getPrefix()) || prefixString.equals(PREFIX_EMAIL.getPrefix())
                || prefixString.equals(PREFIX_BIRTHDAY.getPrefix());
    }

    /**
     * Checks if the given string is a sort order argument (asc, des)
     */
    public boolean isSortArgument(String sortOrder) {
        return sortOrder.equals(ARGUMENT_ASCENDING_WORD)
                || sortOrder.equals(ARGUMENT_DESCENDING_WORD);
    }
}
