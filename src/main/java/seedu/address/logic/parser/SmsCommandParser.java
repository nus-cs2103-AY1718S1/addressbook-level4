package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SMS_TEXT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SmsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input index and argument and creates a new SMSCommand object to specified person
 */
//@@author justuswah
public class SmsCommandParser implements Parser<SmsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SMSCommand
     * and returns an SMSCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SmsCommand parse(String args) throws ParseException {
        Index index;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SMS_TEXT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SMS_TEXT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(firstWord(args));

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
        }

        String text = argMultimap.getValue(PREFIX_SMS_TEXT).toString();
        return new SmsCommand(index, text);

    }

    public static String firstWord(String input) {
        return input.split(" ")[1]; // Create array of words and return the 1st word
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}



