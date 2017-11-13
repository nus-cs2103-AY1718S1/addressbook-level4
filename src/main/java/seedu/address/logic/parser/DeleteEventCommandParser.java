package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author cjianhui
/**
 * Parses input arguments and creates a new DeleteEventCommand object
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {



    public static final Prefix PREFIX_PERSON = new Prefix("p/");
    public static final Prefix PREFIX_EVENT = new Prefix("e/");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns an DeleteEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_EVENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_EVENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteEventCommand.MESSAGE_USAGE));
        }

        Index[] eventIndexes;

        try {
            Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
            String[] arguments = argMultimap.getValue(PREFIX_EVENT).get().trim().split(" ");
            eventIndexes = new Index[arguments.length];
            for (int i = 0; i < eventIndexes.length; i++) {
                eventIndexes[i] = ParserUtil.parseIndex(arguments[i]);
            }
            return new DeleteEventCommand(personIndex, eventIndexes);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
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
