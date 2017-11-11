package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordsPredicate;

//@@author marvinchin
/**
 * Finds and lists all {@code Person}s in address book whose {@code Tag}s contains any of the input keywords.
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
