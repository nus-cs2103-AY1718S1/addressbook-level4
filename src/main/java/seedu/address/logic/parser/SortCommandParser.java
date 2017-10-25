package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ARG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.ARG_ADDRESS_ALIAS;
import static seedu.address.logic.parser.CliSyntax.ARG_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.ARG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.ARG_EMAIL_ALIAS;
import static seedu.address.logic.parser.CliSyntax.ARG_NAME;
import static seedu.address.logic.parser.CliSyntax.ARG_NAME_ALIAS;
import static seedu.address.logic.parser.CliSyntax.ARG_PHONE;
import static seedu.address.logic.parser.CliSyntax.ARG_PHONE_ALIAS;

import java.util.Arrays;
import java.util.HashSet;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private HashSet<String> acceptedFilterTypes = new HashSet<>(Arrays.asList(ARG_DEFAULT,
            ARG_NAME, ARG_NAME_ALIAS, ARG_EMAIL, ARG_EMAIL_ALIAS,
            ARG_PHONE, ARG_PHONE_ALIAS, ARG_ADDRESS, ARG_ADDRESS_ALIAS));

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

        if ("".equals(filterType)) {
            // No arguments, set to default
            filterType = ARG_DEFAULT;
        }

        if (!isValidFilterType(filterType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        } else {
            switch (filterType) {
            case ARG_NAME_ALIAS:
            case ARG_NAME:
                filterType = ARG_NAME;
                break;
            case ARG_EMAIL:
            case ARG_EMAIL_ALIAS:
                filterType = ARG_EMAIL;
                break;
            case ARG_ADDRESS:
            case ARG_ADDRESS_ALIAS:
                filterType = ARG_ADDRESS;
                break;
            case ARG_PHONE:
            case ARG_PHONE_ALIAS:
                filterType = ARG_PHONE;
                break;
            case ARG_DEFAULT:
            default:
                // TODO: Make default sort by date added
                filterType = ARG_DEFAULT;
                break;
            }
            return new SortCommand(filterType);
        }
    }

    /**
     * Returns true if the input filterType is a valid filter for the list. Only name, phone,
     * email and address are allowed.
     * @param filterType
     * @return
     */
    public boolean isValidFilterType(String filterType) {
        return acceptedFilterTypes.contains(filterType);
    }

    public HashSet<String> getAcceptedFilterTypes() {
        return acceptedFilterTypes;
    }
}
