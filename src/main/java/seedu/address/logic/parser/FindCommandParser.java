package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

//@@author KhorSL
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code argsMap} and stores the keywords in {@code mapKeywords}
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private void parseMultiMap(ArgumentMultimap argsMap, HashMap<String, List<String>> mapKeywords, Prefix prefix)
            throws ParseException {
        String trimmedArgs;

        try {
            if (argsMap.getValue(prefix).isPresent()) {
                trimmedArgs = ParserUtil.parseKeywords(argsMap.getValue(prefix)).get().trim();

                if (trimmedArgs.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }

                String[] keywordNameList = trimmedArgs.split("\\s+");
                mapKeywords.put(prefix.toString(), Arrays.asList(keywordNameList));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL,
                PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_COMMENT, PREFIX_APPOINT);

        HashMap<String, List<String>> mapKeywords = new HashMap<>();

        try {
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_NAME);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_TAG);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_EMAIL);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_PHONE);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_ADDRESS);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_COMMENT);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_APPOINT);
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
//@@author
