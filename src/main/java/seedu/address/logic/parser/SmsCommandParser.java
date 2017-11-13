package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SmsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagMatchingPredicate;


//@@author zameschua
/**
 * Parses input arguments and creates a new SmsCommand object
 */
public class SmsCommandParser implements Parser<SmsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SmsCommand
     * and returns an SmsCommand object for execution.
     */
    public SmsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SmsCommand(new TagMatchingPredicate(Arrays.asList(nameKeywords)));
    }
}
