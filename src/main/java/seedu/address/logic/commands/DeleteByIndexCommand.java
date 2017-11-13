package seedu.address.logic.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author marvinchin
/**
 * Deletes {@code Person}s from the address book identified by their indexes in the last displayed person list.
 */
public class DeleteByIndexCommand extends DeleteCommand {
    private Collection<Index> targetIndexes;

    public DeleteByIndexCommand(Collection<Index> indexes) {
        targetIndexes = indexes;
    }

    /**
     * Returns the list of {@code Person}s in the last shown list referenced by indexes provided.
     * @throws CommandException if any of the input indexes are invalid.
     */
    private Collection<ReadOnlyPerson> mapPersonsToIndexes(Collection<Index> indexes) throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        HashSet<ReadOnlyPerson> personSet = new HashSet<>();
        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
            personSet.add(person);
        }

        return personSet;
    }

    @Override
    protected Collection<ReadOnlyPerson> getPersonsToDelete() throws CommandException {
        return mapPersonsToIndexes(targetIndexes);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByIndexCommand // instanceof handles nulls
                && this.targetIndexes.equals(((DeleteByIndexCommand) other).targetIndexes)); // state check
    }
}
