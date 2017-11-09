package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.ParserUtil.isParsableInt;
import static seedu.address.logic.parser.ParserUtil.parseFirstInt;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstInt;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        try {
            String subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).orElse("");
            subject = subject.replaceAll(" ", "%20");
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new EmailCommand(index, subject);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns a formatted argument string given unformatted
     * {@code commandWord} and {@code rawArgs}
     * or a {@code null} {@code String} if not formattable.
     */
    public static String parseArguments(String commandWord, String rawArgs) {
        // Check if index (number) exists, removes Remark prefix (if it exists) and re-adds it before returning.
        if (isParsableInt(rawArgs)) {
            String indexString = Integer.toString(parseFirstInt(rawArgs));
            String subject = parseRemoveFirstInt(rawArgs).trim().replace(PREFIX_SUBJECT.toString(), "");
            return " " + indexString + " " + PREFIX_SUBJECT + subject;
        } else if (isParsableInt(commandWord)) {
            String indexString = Integer.toString(parseFirstInt(commandWord));
            String subject = rawArgs.trim().replace(PREFIX_SUBJECT.toString(), "");
            return " " + indexString + " " + PREFIX_SUBJECT + subject;
        }
        return null;
    }

}
