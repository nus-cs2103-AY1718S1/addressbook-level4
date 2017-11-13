//@@author sunarjo-denny
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_FOR_SORTING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_FOR_SORTING;

import java.util.function.Consumer;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private String attribute;
    private Boolean isReversed;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME_FOR_SORTING, PREFIX_PHONE_FOR_SORTING);

        if (argMultimap.size() > 2) {
            throw new ParseException(
                    String.format(SortCommand.MESSAGE_MULTIPLE_ATTRIBUTE_ERROR, SortCommand.MESSAGE_USAGE)
            );
        }

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME_FOR_SORTING)
                || arePrefixesPresent(argMultimap, PREFIX_PHONE_FOR_SORTING))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        /**
         * Invalid command arguments would result in a loaded preamble
         */
        //@@author sunarjo-denny-reused
        if (!argMultimap.getPreamble().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (argMultimap.size() == 1) {
            attribute = PREFIX_NAME_FOR_SORTING.getPrefix();
        }

        argMultimap.getValue(PREFIX_NAME_FOR_SORTING).ifPresent(setOrder(PREFIX_NAME_FOR_SORTING));
        argMultimap.getValue(PREFIX_PHONE_FOR_SORTING).ifPresent(setOrder(PREFIX_PHONE_FOR_SORTING));

        return new SortCommand(attribute, isReversed);
    }
    private Consumer<String> setOrder(Prefix prefix) {
        return s-> {
            attribute = prefix.toString();

            if (s.equals(SortCommand.REVERSE_SEQUENCE)) {
                isReversed = new Boolean(true);
                return;
            } else {
                isReversed = new Boolean(false);
                return;
            }
        };
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
