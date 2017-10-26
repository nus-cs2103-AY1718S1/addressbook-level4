package seedu.address.logic.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Deletes persons from the list identified by their indexes in the last displayed list in the address book.
 */
public class DeleteByTagCommand extends DeleteCommand {
    public static final String COMMAND_OPTION = "tag";

    private Collection<String> targetTags;

    public DeleteByTagCommand(Collection<String> tags) {
        targetTags = tags;
    }

    /**
     * Returns the list of persons in the last shown list referenced by the collection of tags provided.
     */
    private Collection<ReadOnlyPerson> mapPersonsToTags(Collection<String> tags) {
        TagsContainKeywordsPredicate predicate = new TagsContainKeywordsPredicate(tags);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> persons = lastShownList.stream().filter(predicate).collect(Collectors.toList());

        return persons;
    }

    @Override
    public Collection<ReadOnlyPerson> getPersonsToDelete() {
        return mapPersonsToTags(targetTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByTagCommand // instanceof handles nulls
                && this.targetTags.equals(((DeleteByTagCommand) other).targetTags)); // state check
    }
}
