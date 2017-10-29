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

    private boolean invalidListTagArgs(List<String> evaluateList) {
        String previousString = "";
        boolean multipleAndOrCluster = false;
        boolean startWithAndOr;
        boolean endWithAndOr;
        for (String str : evaluateList) {
            if (("and".equalsIgnoreCase(previousString) || "or".equalsIgnoreCase(previousString)) &&
                    ("and".equalsIgnoreCase(str) || "or".equalsIgnoreCase(str))) {
                multipleAndOrCluster = true;
            }
            previousString = str;
        }
        startWithAndOr = "and".equalsIgnoreCase(evaluateList.get(0)) || "or".equalsIgnoreCase(evaluateList.get(0));
        endWithAndOr = "and".equalsIgnoreCase(evaluateList.get(evaluateList.size() - 1)) ||
                "or".equalsIgnoreCase(evaluateList.get(evaluateList.size() - 1));
        return multipleAndOrCluster || startWithAndOr || endWithAndOr;
    }

}
