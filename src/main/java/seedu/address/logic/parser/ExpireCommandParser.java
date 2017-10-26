package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ExpireCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ExpiryDate;

/**
 * Parses arguments of expire command
 */

public class ExpireCommandParser implements Parser<ExpireCommand> {
    /**
     * Parse given {@code String} of arguements into context of ExpireCommand
     * and returns an ExpireCommand object for execution.
     * @throws ParseException if user input does not conform the expected format
     */
    public ExpireCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXPIRE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String dateString = argMultimap.getValue(PREFIX_EXPIRE).orElse("");
            ExpiryDate date = new ExpiryDate(dateString);
            return new ExpireCommand(index, date);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpireCommand.MESSAGE_USAGE));
        }

    }
}
