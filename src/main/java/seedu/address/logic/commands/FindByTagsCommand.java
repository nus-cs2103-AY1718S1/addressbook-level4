package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordsPredicate;

//@@author marvinchin
/**
 * Finds and lists all persons in address book whose tags contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByTagsCommand extends FindCommand {
    public static final String COMMAND_OPTION = "tag";

    private TagsContainKeywordsPredicate predicate;

    public FindByTagsCommand(TagsContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    protected Predicate<ReadOnlyPerson> getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByTagsCommand // instanceof handles nulls
                && this.predicate.equals(((FindByTagsCommand) other).predicate)); // state check
    }
}
