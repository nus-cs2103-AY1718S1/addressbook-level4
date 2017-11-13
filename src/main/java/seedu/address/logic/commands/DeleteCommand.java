//@@author quangtdn
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * Deletes a list of persons identified using their last displayed indices from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the list of persons identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX [MORE_INDICES] (must be a positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 4";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Persons: %1$s";

    private final List<Index> listTargetIndices;

    public DeleteCommand(List<Index> listTargetIndices) {
        this.listTargetIndices = listTargetIndices;
    }

    public DeleteCommand(Index targetIndex) {
        this.listTargetIndices = new ArrayList<Index>();
        this.listTargetIndices.add(targetIndex);
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (Index targetIndex: listTargetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        List<ReadOnlyPerson> listPersonsToDelete = new ArrayList<ReadOnlyPerson>();
        for (Index targetIndex: listTargetIndices) {
            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
            listPersonsToDelete.add(personToDelete);
        }

        try {
            for (ReadOnlyPerson personToDelete: listPersonsToDelete) {
                model.deletePerson(personToDelete);
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, listPersonsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && (this.listTargetIndices.containsAll(((DeleteCommand) other).listTargetIndices)
                    && ((DeleteCommand) other).listTargetIndices
                    .containsAll(this.listTargetIndices))); // state check
    }
}
//@@author
