package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TO;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
//@@author sidhmads
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user's input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL_TO, PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_BODY);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL_TO)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }

        try {

            String[] toList = ParserUtil.parseEmailToCommand(argMultimap.getAllValues(PREFIX_EMAIL_TO));
            String subject = String.join("", argMultimap.getAllValues(PREFIX_EMAIL_SUBJECT)).replace(" ", "%20");
            String body = String.join("", argMultimap.getAllValues(PREFIX_EMAIL_BODY)).replace(" ",
                    "%20").replace("\"", "\'");

            return new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(toList)), subject, body);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
