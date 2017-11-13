//@@author srishag
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SendEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SendEmailCommand object
 */
public class SendEmailCommandParser implements Parser<SendEmailCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SendEmailCommand
     * and returns an SendEmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SendEmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_BODY);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_BODY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendEmailCommand.MESSAGE_USAGE));
        }

        try {
            //parse args to find index entered until PREFIX_EMAIL_SUBJECT
            Index index = ParserUtil.parseIndex(args.substring(0, args.indexOf("e")));

            Optional<String> subjectOptional = argMultimap.getValue(PREFIX_EMAIL_SUBJECT);
            String emailSubject = new String("");
            if (subjectOptional.isPresent()) {
                emailSubject = ParserUtil.parseEmailSubject(argMultimap.getValue(PREFIX_EMAIL_SUBJECT)).get();
            }

            Optional<String> bodyOptional = argMultimap.getValue(PREFIX_EMAIL_BODY);
            String emailBody = new String("");
            if (bodyOptional.isPresent()) {
                emailBody = ParserUtil.parseEmailBody(argMultimap.getValue(PREFIX_EMAIL_BODY)).get()
                        .replaceAll("//", "\n");
            }
            return new SendEmailCommand(index, emailSubject, emailBody);
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
