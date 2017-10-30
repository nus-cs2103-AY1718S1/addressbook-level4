package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SetPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ProfPic;

/**
 * Parses input arguments and creates a new SetPictureCommand object
 */
public class SetPictureCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ResetPictureCommand
     * and returns an ResetPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetPictureCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPictureCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_FILEPATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPictureCommand.MESSAGE_USAGE));
        }

        try {
            ProfPic path = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILEPATH)).get();
            return new SetPictureCommand(index, path);
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
