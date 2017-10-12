package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

/**
 * Parses input arguments and creates a new ChangeTagColorCommand object
 */
public class ChangeTagColorCommandParser implements Parser<ChangeTagColorCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeTagColorCommand
     * and returns an ChangeTagColorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeTagColorCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + args, PREFIX_TAG, PREFIX_COLOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG, PREFIX_COLOR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeTagColorCommand.MESSAGE_USAGE));
        }

        try {
            TagColor tagColor = ParserUtil.parseTagColor(argMultimap.getValue(PREFIX_COLOR)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            return new ChangeTagColorCommand(tagList, tagColor);
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
