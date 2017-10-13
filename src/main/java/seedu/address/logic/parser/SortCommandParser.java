package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private String sortType;
    private Boolean isDescending;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        if (argMultimap.size() > 2) {
            throw new ParseException(String.format(SortCommand.MESSAGE_MULTIPLE_ATTRIBUTE, SortCommand.MESSAGE_USAGE));
        }

        if (argMultimap.size() < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        assert argMultimap.size() == 2;

        argMultimap.getValue(PREFIX_NAME).ifPresent(setSortingOrder(PREFIX_NAME));
        argMultimap.getValue(PREFIX_PHONE).ifPresent(setSortingOrder(PREFIX_PHONE));
        argMultimap.getValue(PREFIX_EMAIL).ifPresent(setSortingOrder(PREFIX_EMAIL));
        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(setSortingOrder(PREFIX_ADDRESS));

        if (isDescending == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(sortType, isDescending);
    }

    private Consumer<String> setSortingOrder(Prefix prefix) {
        return s -> {

            sortType = prefix.toString();

            if (s.equals(SortCommand.BY_ASCENDING)) {
                isDescending = new Boolean(false);
                return;
            }
            if (s.equals(SortCommand.BY_DESCENDING)) {
                isDescending = new Boolean(true);
                return;
            }


        };
    }
}
