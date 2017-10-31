package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_ASCENDING_WORD;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_DESCENDING_WORD;
import static seedu.address.logic.commands.SortCommand.OPTION_ALL_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;


import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        boolean sortAll = false;
        String trimmedArgs = args.trim();
        if (trimmedArgs.startsWith(OPTION_ALL_WORD)) {
            sortAll = true;
            trimmedArgs = trimmedArgs.replaceFirst(OPTION_ALL_WORD, "").trim();
        }

        if (trimmedArgs.isEmpty() || isSortArgument(trimmedArgs)) {
            return new SortCommand(sortAll, null, null);
        }

        String[] splitArgs = trimmedArgs.split("\\s+");
        String prefix = splitArgs[0];
        String order = ARGUMENT_ASCENDING_WORD;
        if (splitArgs.length > 1) {
            order = splitArgs[1];
        }
        if (isSortablePrefix(prefix) && isSortArgument(order)) {
            return new SortCommand(sortAll, prefix, order);
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
