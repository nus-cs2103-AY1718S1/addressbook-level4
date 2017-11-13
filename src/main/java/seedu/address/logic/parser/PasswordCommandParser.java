package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_PASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASS;

import java.util.stream.Stream;

import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author syy94
/**
 * Parses input arguments and creates a new PasswordCommand object
 */
public class PasswordCommandParser implements Parser<PasswordCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public PasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PASS, PREFIX_NEW_PASS, PREFIX_CLEAR_PASS);

        if (!arePrefixesPresent(argMultimap, PREFIX_PASS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NEW_PASS)) {
            final String newPass = argMultimap.getValue(PREFIX_NEW_PASS).get();

            requireNonNull(newPass);

            if (newPass.length() == 0) {
                throw new ParseException("New Password Cannot be empty!");
            }

            return new PasswordCommand(new PasswordCommand.ChangePass(
                    argMultimap.getValue(PREFIX_PASS).get(),
                    newPass
            ));
        } else if (arePrefixesPresent(argMultimap, PREFIX_CLEAR_PASS)) {
            return new PasswordCommand(new PasswordCommand.ClearPass(argMultimap.getValue(PREFIX_PASS).get()));
        } else {
            return new PasswordCommand(new PasswordCommand.SetPass(argMultimap.getValue(PREFIX_PASS).get()));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
