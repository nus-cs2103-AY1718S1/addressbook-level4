package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jin-ting

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of input arguments
     * and returns a new EmailCommand object
     * /@throws ParseException if the user input does not conform the expected format
     */

    public EmailCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] indicesInString = trimmedArgs.split("\\s+");


        try {
            Set<Index> indices = new HashSet<>();
            for (String indexString : indicesInString) {
                Index index = ParserUtil.parseIndex(indexString);
                indices.add(index);
            }

            return new EmailCommand(indices);

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }

}
