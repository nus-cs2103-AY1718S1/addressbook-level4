package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASCADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.stream.Stream;

import seedu.address.logic.commands.RemoveUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveUserCommand object
 */
public class RemoveUserCommandParser implements Parser<RemoveUserCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERID, PREFIX_PASSWORD, PREFIX_CASCADE);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERID, PREFIX_PASSWORD, PREFIX_CASCADE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveUserCommand.MESSAGE_USAGE));
        }
        String userName = argMultimap.getValue(PREFIX_USERID).get();
        String password = argMultimap.getValue(PREFIX_PASSWORD).get();
        String cascadeText = argMultimap.getValue(PREFIX_CASCADE).get();
        if (!cascadeText.equals("Y") && !(cascadeText.equals("N"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveUserCommand.MESSAGE_USAGE));
        }
        boolean cascade = (cascadeText.equals("Y")) ? true : false;
        return new RemoveUserCommand(userName, password, cascade);
    }
}
