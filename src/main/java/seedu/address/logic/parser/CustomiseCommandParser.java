package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FONT_SIZE;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CustomiseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.font.FontSize;


/**
 * Parses input arguments and creates a new CustomiseCommand object
 */
public class CustomiseCommandParser implements Parser<CustomiseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CustomiseCommand
     * and returns an CustomiseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomiseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FONT_SIZE);

        if (!arePrefixesPresent(argMultimap, PREFIX_FONT_SIZE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomiseCommand.MESSAGE_USAGE));
        }

        try {
            FontSize fontSize = ParserUtil.parseFontSize(argMultimap.getValue(PREFIX_FONT_SIZE)).get();

            return new CustomiseCommand(fontSize);
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
