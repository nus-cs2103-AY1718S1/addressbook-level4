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

        if (trimmedArgs.isEmpty() || invalidListTagArgs(evaluateList)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListByTagCommand.MESSAGE_USAGE));
        }


        return new ListByTagCommand(new TagContainsKeywordsPredicate(evaluateList));

    }

    /**
     * Returns true if tag list argument is invalid.
     * Tag list is invalid if
     * 1. List starts or ends with "AND" or "OR"
     * 2. "AND" or "OR" are clustered together
     */
    private boolean invalidListTagArgs(List<String> evaluateList) {
        boolean multipleAndOrCluster = hasManyAndOrClustered(evaluateList);
        boolean startWithAndOr = startsWithAndOr(evaluateList);
        boolean endWithAndOr = endsWithAndOr(evaluateList);
        return multipleAndOrCluster || startWithAndOr || endWithAndOr;
    }

    /**
     * Returns true if list starts with "AND" or "OR"
     */
    private boolean startsWithAndOr(List<String> evaluateList) {
        boolean startWithAndOr = "and".equalsIgnoreCase(evaluateList.get(0))
                || "or".equalsIgnoreCase(evaluateList.get(0));
        return startWithAndOr;
    }

    /**
     * Returns true if list ends with "AND" or "OR"
     */
    private boolean endsWithAndOr(List<String> evaluateList) {
        boolean endWithAndOr = "and".equalsIgnoreCase(evaluateList.get(evaluateList.size() - 1))
                || "or".equalsIgnoreCase(evaluateList.get(evaluateList.size() - 1));
        return endWithAndOr;
    }

    /**
     * Returns true if "AND" or "OR" strings are clustered together
     */
    private boolean hasManyAndOrClustered(List<String> evaluateList) {
        String previousString = "";
        boolean multipleAndOrCluster = false;
        for (String str : evaluateList) {
            if (("and".equalsIgnoreCase(previousString) || "or".equalsIgnoreCase(previousString))
                    && ("and".equalsIgnoreCase(str) || "or".equalsIgnoreCase(str))) {
                multipleAndOrCluster = true;
                break;
            }
            previousString = str;
        }
        return multipleAndOrCluster;
    }
}
