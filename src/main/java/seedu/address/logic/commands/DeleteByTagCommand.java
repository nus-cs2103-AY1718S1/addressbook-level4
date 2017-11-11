package seedu.address.logic.commands;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordsPredicate;

//@@author marvinchin
/**
 * Deletes {@code Person}s from the list whose {@code Tag}s match the input keywords.
 */
public class DeleteByTagCommand extends DeleteCommand {
    public static final String COMMAND_OPTION = "tag";

    private Set<String> targetTags;

    public DeleteByTagCommand(Set<String> tags) {
        targetTags = tags;
    }

    /**
     * Returns the list of {@code Person}s in the last shown list whose {@code Tag}s match the input keywords.
     */
    private Collection<ReadOnlyPerson> mapPersonsToTags(Collection<String> keywords) {
        TagsContainKeywordsPredicate predicate = new TagsContainKeywordsPredicate(keywords);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> matchedPersons = lastShownList.stream().filter(predicate).collect(Collectors.toList());
        return matchedPersons;
    }

    @Override
    protected Collection<ReadOnlyPerson> getPersonsToDelete() {
        return mapPersonsToTags(targetTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByTagCommand // instanceof handles nulls
                && this.targetTags.equals(((DeleteByTagCommand) other).targetTags)); // state check
    }
}
