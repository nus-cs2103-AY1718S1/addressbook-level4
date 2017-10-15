package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tags contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByTagsCommand extends FindCommand {
    public static final String OPTION_PREFIX = PREFIX_TAG.toString();

    public FindByTagsCommand(TagsContainKeywordsPredicate predicate) {
        super(predicate);
    }

    public static boolean matchArgs(String args) {
        return args.indexOf(OPTION_PREFIX) == 0;
    }

    public static String getKeywordArgs(String args) {
        return args.substring(OPTION_PREFIX.length()).trim();
    }
}
