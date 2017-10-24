package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPersonToGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new CreateGroupCommand object
 */
public class AddPersonToGroupCommandParser implements Parser<AddPersonToGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public static final Prefix PREFIX_PERSON = new Prefix("p/");

    /** Parse AddPersonToGroupCommand Arguments */
    public AddPersonToGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP, PREFIX_PERSON)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPersonToGroupCommand.MESSAGE_USAGE));
        }

        try {
            Index groupIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_GROUP).get());
            Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
            return new AddPersonToGroupCommand(groupIndex, personIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
