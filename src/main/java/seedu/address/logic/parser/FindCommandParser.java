package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL);

        String trimmedArgsName;
        String trimmedArgsTag;
        String trimmedArgsEmail;
        String[] keywordNameList;
        String[] keywordTagList;
        String[] keywordEmailList;
        HashMap<String, List<String>> mapKeywords = new HashMap<>();

        try {
            if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
                trimmedArgsName = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_NAME)).get().trim();
                if (trimmedArgsName.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordNameList = trimmedArgsName.split("\\s+");
                mapKeywords.put(PREFIX_NAME.toString(), Arrays.asList(keywordNameList));
            }

            if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
                trimmedArgsTag = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_TAG)).get().trim();
                if (trimmedArgsTag.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordTagList = trimmedArgsTag.split("\\s+");
                mapKeywords.put(PREFIX_TAG.toString(), Arrays.asList(keywordTagList));
            }

            if (argumentMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                trimmedArgsEmail = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_EMAIL)).get().trim();
                if (trimmedArgsEmail.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordEmailList = trimmedArgsEmail.split("\\s+");
                mapKeywords.put(PREFIX_EMAIL.toString(), Arrays.asList(keywordEmailList));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (mapKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(mapKeywords));
    }

}
