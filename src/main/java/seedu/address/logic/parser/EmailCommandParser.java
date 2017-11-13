package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.ParserUtil.isParsableIndex;
import static seedu.address.logic.parser.ParserUtil.parseFirstIndex;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstIndex;
import static seedu.address.model.ModelManager.getLastRolodexSize;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseArgsException;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseArgsException if the user input does not conform the expected format, but is in a suggestible format
     */
    public EmailCommand parse(String args) throws ParseArgsException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        try {
            String subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).orElse("");
            subject = subject.replaceAll(" ", "%20");
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new EmailCommand(index, subject);
        } catch (IllegalValueException ive) {
            throw new ParseArgsException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns a formatted argument string given unformatted
     * {@code commandWord} and {@code rawArgs}
     * or a {@code null} {@code String} if not formattable.
     */
    public static String parseArguments(String commandWord, String rawArgs) {
        // Check if index (number) exists, removes Email prefix (if it exists) and re-adds it before returning.
        if (isParsableIndex(rawArgs, getLastRolodexSize())) {
            String indexString = Integer.toString(parseFirstIndex(rawArgs, getLastRolodexSize()));
            String subject = parseRemoveFirstIndex(rawArgs, getLastRolodexSize())
                    .trim().replace(PREFIX_SUBJECT.toString(), "");
            return " " + indexString + " " + PREFIX_SUBJECT + subject;
        } else if (isParsableIndex(commandWord, getLastRolodexSize())) {
            String indexString = Integer.toString(parseFirstIndex(commandWord, getLastRolodexSize()));
            String subject = rawArgs.trim().replace(PREFIX_SUBJECT.toString(), "");
            return " " + indexString + " " + PREFIX_SUBJECT + subject;
        }
        return null;
    }

}
