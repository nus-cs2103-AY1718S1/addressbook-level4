package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT_SID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTH_TOKEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SENDING_NUMBER;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ReauthenticateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input index and argument and creates a new SMSCommand object to specified person
 */
//@@author justuswah
public class ReauthenticateCommandParser implements Parser<ReauthenticateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReauthenticateCommand
     * and returns a ReauthenticateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReauthenticateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACCOUNT_SID,
                PREFIX_AUTH_TOKEN, PREFIX_SENDING_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_ACCOUNT_SID, PREFIX_AUTH_TOKEN, PREFIX_SENDING_NUMBER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReauthenticateCommand.MESSAGE_USAGE));
        }

        try {
            String accountSid = ParserUtil.parseAuthenticator(argMultimap.getValue(PREFIX_ACCOUNT_SID).get());
            String authenticationToken = ParserUtil.parseAuthenticator(argMultimap.getValue(PREFIX_AUTH_TOKEN).get());
            String sendingNumber = ParserUtil.parseAuthenticator(argMultimap.getValue(PREFIX_SENDING_NUMBER).get());
            return new ReauthenticateCommand(accountSid, authenticationToken, sendingNumber);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReauthenticateCommand.MESSAGE_USAGE));
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
