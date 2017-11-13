//@@author cqhchan
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;

/**
 *
 */
public class CreateAccountCommandParser implements Parser<CreateAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateAccountCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
        }

        try {
            Password userPassword = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();
            Username userName = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            ReadOnlyAccount account = new Account(userName, userPassword);
            return new CreateAccountCommand(account);
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
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
