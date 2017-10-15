package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tags contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByTagsCommand extends FindCommand {
    // Note: This regex pattern will be used by the FindCommandParser to identify if the input for a find
    // command is a FindByTagCommand. Please make sure that this regex does not clash with regexes
    // of any other classes that extend FindCommand.
    private static final String KEYWORDS_GROUP_NAME = "KEYWORDS";
    private static final String ARGS_REGEX = "^" + PREFIX_TAG + "(?<" + KEYWORDS_GROUP_NAME + ">.*)";

    public FindByTagsCommand(TagsContainKeywordsPredicate predicate) {
        super(predicate);
    }

    public static boolean matchArgs(String args) {
        return args.matches(ARGS_REGEX);
    }

    public static String getKeywordArgs(String args) {
        Pattern pattern = Pattern.compile(ARGS_REGEX);
        Matcher matcher = pattern.matcher(args);

        if (!matcher.matches()) {
            return null;
        }

        String keywordArgs = matcher.group(KEYWORDS_GROUP_NAME).trim();
        return keywordArgs;
    }
}
