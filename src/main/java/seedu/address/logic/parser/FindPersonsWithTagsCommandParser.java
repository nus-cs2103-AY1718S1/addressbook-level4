//@@author PokkaKiyo
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.logic.commands.FindPersonsWithTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Parses input arguments and creates a new FindPersonWithTagsCommand object
 */
public class FindPersonsWithTagsCommandParser implements Parser<FindPersonsWithTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonWithTagsCommand
     * and returns an FindPersonWithTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonsWithTagsCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        checkForNonEmptyArguments(trimmedArgs);

        String[] tagKeywords = getListOfTagKeywords(trimmedArgs);

        ArrayList<String> tagKeywordsImproved = getImprovedList(tagKeywords);

        return new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(tagKeywordsImproved));
    }

    private String[] getListOfTagKeywords(String trimmedArgs) {
        return trimmedArgs.split("\\s+");
    }

    /**
     * Checks if the given String of arguments is empty
     * @param trimmedArgs
     * @throws ParseException if the provided String does not conform to expected format
     */
    private void checkForNonEmptyArguments(String trimmedArgs) throws ParseException {
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindPersonsWithTagsCommand.MESSAGE_USAGE));
        }
    }

    /**
     * From the original list of keywords, generate more keywords
     * corresponding to the singular/plural form of the word,
     * but might generate words that are not in proper English,
     * for example, "memory" to "memorys" instead of "memories".
     * @param originalKeywords
     * @return a new list of keywords that includes the original keywords and
     * singular/plural differences
     */
    private ArrayList<String> getImprovedList(String[] originalKeywords) {
        ArrayList<String> improvedListOfKeywords = new ArrayList<String>();
        for (String originalKeyword : originalKeywords) {
            improvedListOfKeywords.add(originalKeyword);
            if (originalKeyword.endsWith("s")) {
                improvedListOfKeywords.add(originalKeyword.substring(0, originalKeyword.length() - 1));
            } else {
                improvedListOfKeywords.add (originalKeyword.concat("s"));
            }
        }
        return improvedListOfKeywords;
    }
}
