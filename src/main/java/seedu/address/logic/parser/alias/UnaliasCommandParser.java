package seedu.address.logic.parser.alias;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_KEYWORD;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.alias.UnaliasCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Keyword;

/**
 * Parses input arguments and creates a new UnaliasCommand object
 */
public class UnaliasCommandParser implements Parser<UnaliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnaliasCommand
     * and returns an UnaliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UnaliasCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALIAS_KEYWORD);
        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS_KEYWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }
        try {
            Keyword keyword = ParserUtil.parseKeyword(
                    argMultimap.getValue(PREFIX_ALIAS_KEYWORD)).get();
            return new UnaliasCommand(keyword);
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

    @Override
    public String getCommandWord() {
        return UnaliasCommand.COMMAND_WORD;
    }
}

