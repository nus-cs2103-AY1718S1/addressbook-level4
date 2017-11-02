package seedu.address.logic.parser.alias;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_REPRESENTATION;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.alias.AliasCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.AliasToken;
import seedu.address.model.alias.Keyword;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.Representation;

//@@author deep4k
/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALIAS_KEYWORD, PREFIX_ALIAS_REPRESENTATION);
        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS_KEYWORD, PREFIX_ALIAS_REPRESENTATION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        try {
            Keyword keyword = ParserUtil.parseKeyword(
                    argMultimap.getValue(PREFIX_ALIAS_KEYWORD)).get();
            Representation representation = ParserUtil.parseRepresentation(
                    argMultimap.getValue(PREFIX_ALIAS_REPRESENTATION)).get();
            ReadOnlyAliasToken aliasToken = new AliasToken(keyword, representation);
            return new AliasCommand(aliasToken);
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
        return AliasCommand.COMMAND_WORD;
    }
}


