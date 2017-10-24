package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindByNameCommand;
import seedu.address.logic.commands.FindByTagsCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private String[] parseKeywords(String keywordArgs) {
        String[] keywords = keywordArgs.split("\\s+");
        return keywords;
    }

    /**
     * Utility function to check that the input arguments is not empty.
     * Throws a parse exception if it is empty.
     */
    private void checkArgsNotEmpty(String args) throws ParseException {
        if (args == null || args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        OptionBearingArgument opArgs = new OptionBearingArgument(args);
        String filteredArgs = opArgs.getFilteredArgs();

        checkArgsNotEmpty(filteredArgs);

        if (opArgs.getOptions().contains(FindByTagsCommand.COMMAND_OPTION)) {
            String[] tagKeywords = parseKeywords(filteredArgs);
            TagsContainKeywordsPredicate predicate = new TagsContainKeywordsPredicate(Arrays.asList(tagKeywords));
            return new FindByTagsCommand(predicate);
        } else {
            checkArgsNotEmpty(opArgs.getFilteredArgs());
            String[] nameKeywords = parseKeywords(filteredArgs);
            NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
            return new FindByNameCommand(predicate);
        }
    }

}
