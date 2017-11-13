package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.ListByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainsKeywordsPredicate;

//@@author Jeremy

/**
 * Parses input arguments and creates a new ListByTagCommand object
 */
public class ListByTagCommandParser implements Parser<ListByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListByTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        String[] tagKeyWords = trimmedArgs.split("\\s+");
        List<String> evaluateList = Arrays.asList(tagKeyWords);

        if (trimmedArgs.isEmpty() || isInvalidArgs(evaluateList)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListByTagCommand.MESSAGE_USAGE));
        }
        return new ListByTagCommand(new TagContainsKeywordsPredicate(evaluateList));
    }

    /**
     * Checks if tag list argument is invalid.
     * Tag list is invalid if:
     * 1. List starts or ends with "AND" or "OR".
     * 2. "AND" or "OR" are clustered together.
     *
     * @param evaluateList list of input text to be evaluated.
     * @return True if list argument is invalid.
     */
    private boolean isInvalidArgs(List<String> evaluateList) {
        boolean multipleAndOrCluster = hasManyAndOrClustered(evaluateList);
        boolean startWithAndOr = startsWithAndOr(evaluateList);
        boolean endWithAndOr = endsWithAndOr(evaluateList);
        return multipleAndOrCluster || startWithAndOr || endWithAndOr;
    }

    /**
     * Checks if list starts with "AND" or "OR".
     *
     * @param evaluateList list of input text to be evaluated.
     * @return True if list starts with "AND" or "OR.
     */
    private boolean startsWithAndOr(List<String> evaluateList) {
        String firstString = evaluateList.get(0);
        boolean startWithAndOr = isAnd(firstString) || isOr(firstString);
        return startWithAndOr;
    }

    /**
     * Checks if list ends with "AND" or "OR".
     *
     * @param evaluateList list of input text to be evaluated.
     * @return True if list ends with "AND" or "OR.
     */
    private boolean endsWithAndOr(List<String> evaluateList) {
        String lastString = evaluateList.get(evaluateList.size() - 1);
        boolean hasAnd = isAnd(lastString);
        boolean hasOr = isOr(lastString);
        boolean endWithAndOr = hasAnd || hasOr;
        return endWithAndOr;
    }

    /**
     * Checks if "AND" or "OR" strings are clustered together.
     *
     * @param evaluateList list of input text to be evaluated.
     * @return True if "AND" or "OR" strings are clustered together.
     */
    private boolean hasManyAndOrClustered(List<String> evaluateList) {
        String previousString = "";
        boolean multipleAndOrCluster = false;
        for (String str : evaluateList) {
            if (areBothAndOr(previousString, str)) {
                multipleAndOrCluster = true;
                break;
            }
            previousString = str;
        }
        return multipleAndOrCluster;
    }

    /**
     * Checks if both input strings are "and" or "or".
     *
     * @param before Word before.
     * @param after  Word after.
     * @return True if both contains either "and" or "or".
     */
    private boolean areBothAndOr(String before, String after) {
        boolean isAndOrBefore = isAnd(before) || isOr(before);
        boolean isAndOrAfter = isAnd(after) || isOr(after);
        return isAndOrAfter && isAndOrBefore;
    }

    /**
     * Checks if string is "and".
     *
     * @param string String to be evaluated.
     * @return True if string is "and".
     */
    private boolean isAnd(String string) {
        return "and".equalsIgnoreCase(string);
    }

    /**
     * Checks if string is "or".
     *
     * @param string String to be evaluated.
     * @return True if string is "or".
     */
    private boolean isOr(String string) {
        return "or".equalsIgnoreCase(string);
    }
}
