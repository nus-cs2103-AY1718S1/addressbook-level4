package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DisconnectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DisconnectCommand object
 */
public class DisconnectCommandParser implements Parser<DisconnectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DisconnectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisconnectCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_EVENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_EVENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisconnectCommand.MESSAGE_USAGE));
        }

        try {
            Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
            Index eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());

            return new DisconnectCommand(personIndex, eventIndex);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisconnectCommand.MESSAGE_USAGE));
        }
    }

}
