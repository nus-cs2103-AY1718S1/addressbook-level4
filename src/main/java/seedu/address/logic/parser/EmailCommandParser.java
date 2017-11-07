package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHARE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author hanselblack
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String arguments) throws ParseException {
        requireNonNull(arguments);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_SHARE);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
        String share = argMultimap.getValue(PREFIX_SHARE).orElse("");
        String[] shareEmailArray = share.trim().split("\\s+");
        return new EmailCommand(index, shareEmailArray);
    }
}
