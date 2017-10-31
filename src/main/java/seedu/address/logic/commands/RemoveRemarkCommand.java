package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Removes remarks to a person in the address book.
 */
public class RemoveRemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeremark";
    public static final String COMMAND_ALIAS = "rr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a remark from the person identified by the index number used in the last person listing."
            + "Index of remark to be removed also needs to be identified in the listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "REMARKINDEX\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "2";

    public static final String MESSAGE_REMOVE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_REMOVE_REMARK_FAILURE = "Failed to remove remark to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final ArrayList<Integer> remarkIndexArrayList;

    /**
     * @param index of the person in the filtered person list to edit
     * @param remarkIndexArrayList details to remove remarks
     */
    public RemoveRemarkCommand(Index index, ArrayList<Integer> remarkIndexArrayList) {
        requireNonNull(index);
        requireNonNull(remarkIndexArrayList);

        this.index = index;
        this.remarkIndexArrayList = remarkIndexArrayList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }


        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        ArrayList<Remark> tempRemarkList = new ArrayList<>(personToEdit.getRemark());
        ArrayList<Remark> emptyRemarkList = new ArrayList<>();
        Person editedPerson;
        if (remarkIndexArrayList.isEmpty()) { //clears all the remarks if no specific remark index is provided
            editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), emptyRemarkList, personToEdit.getFavouriteStatus(),
                    personToEdit.getTags(), personToEdit.getLink());
        } else {
            for (int i = 0; i < remarkIndexArrayList.size(); i++) {
                if (Index.fromOneBased(remarkIndexArrayList.get(i)).getZeroBased() >= tempRemarkList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT);
                }
                tempRemarkList.remove(Index.fromOneBased(remarkIndexArrayList.get(i)).getZeroBased());
            }
            editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), tempRemarkList, personToEdit.getFavouriteStatus(),
                    personToEdit.getTags(), personToEdit.getLink());
        }

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     *
     * @param personToEdit
     * @return String that shows whether remove was successfully done
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_REMOVE_REMARK_SUCCESS, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemoveRemarkCommand)) {
            return false;
        }
        // state check
        RemoveRemarkCommand e = (RemoveRemarkCommand) other;
        return index.equals(e.index)
                && remarkIndexArrayList.equals(e.remarkIndexArrayList);
    }
}
