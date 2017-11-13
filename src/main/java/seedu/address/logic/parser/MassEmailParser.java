package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.MassEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagMatchingPredicate;


//@@author ReneeSeet

/**
 * Parses input arguments and creates a new MassEmailCommand object
 */

public class MassEmailParser implements Parser<MassEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MassCommand
     * and returns an MassCommand object for execution.
     */

    public MassEmailCommand parse(String args)throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new MassEmailCommand(new TagMatchingPredicate(Arrays.asList(nameKeywords)));
    }
}
