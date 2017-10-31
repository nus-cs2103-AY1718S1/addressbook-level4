//@@author Estois
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.function.Consumer;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private String parameter;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        this.parameter = "invalid";

        if (args.equals("")) {
            args = " n/";
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_AGE);

        if (argMultimap.size() > 2) {
            throw new ParseException(String.format(SortCommand.SORT_MULTIPLE_INPUT, SortCommand.MESSAGE_USAGE));
        }

        if (argMultimap.size() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argMultimap.getValue(PREFIX_NAME).ifPresent(updateParam(PREFIX_NAME));
        argMultimap.getValue(PREFIX_PHONE).ifPresent(updateParam(PREFIX_PHONE));
        argMultimap.getValue(PREFIX_EMAIL).ifPresent(updateParam(PREFIX_EMAIL));
        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(updateParam(PREFIX_ADDRESS));
        argMultimap.getValue(PREFIX_AGE).ifPresent(updateParam(PREFIX_AGE));

        if (parameter.equals("invalid")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand (parameter);
    }

    private Consumer<String> updateParam (Prefix prefix) {
        return s -> parameter = prefix.toString();
    }

}
