package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ARG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.ARG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.ARG_NAME;
import static seedu.address.logic.parser.CliSyntax.ARG_PHONE;


import java.util.Arrays;
import java.util.HashSet;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    HashSet<String> acceptedFilterTypes = new HashSet<>(Arrays.asList("default", ARG_NAME, ARG_EMAIL,
            ARG_PHONE, ARG_ADDRESS));

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, new Prefix(""));

        String filterType;
        filterType = argMultimap.getPreamble().toLowerCase();

        if (filterType.equals("sort")) {
            // No arguments, set to default
            filterType = "default";
        }

        if (!isValidFilterType(filterType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(filterType);
    }

    public boolean isValidFilterType(String filterType) {

        if (acceptedFilterTypes.contains(filterType)){
            return true;
        } else {
            return false;
        }
    }

}
