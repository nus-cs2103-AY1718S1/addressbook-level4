package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

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
 * Adds remarks to a person in the address book.
 */
public class AddRemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addremark";
    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a remark to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARK\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Get charger back from him";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_ADD_REMARK_FAILURE = "Failed to add remark to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final ArrayList<Remark> remarkArrayList;

    /**
     * @param index of the person in the filtered person list to edit
     * @param remarkArrayList details to add remarks
     */
    public AddRemarkCommand(Index index, ArrayList<Remark> remarkArrayList) {
        requireNonNull(index);
        requireNonNull(remarkArrayList);

        this.index = index;
        this.remarkArrayList = remarkArrayList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        ArrayList<Remark> tempRemarkList;
        if(!remarkArrayList.get(0).equals("") || !remarkArrayList.get(0).equals(null)){
            tempRemarkList = new ArrayList<>(personToEdit.getRemark());
        } else {
            tempRemarkList = new ArrayList<>();
        }

        for(int i =0; i<remarkArrayList.size(); i++) {
            tempRemarkList.add(remarkArrayList.get(i));
        }
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), tempRemarkList, personToEdit.getFavouriteStatus(), personToEdit.getTags(),
                personToEdit.getLink());

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
     * @return String that shows whether add was successfully done
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remarkArrayList.isEmpty() && !remarkArrayList.get(0).value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_ADD_REMARK_FAILURE, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddRemarkCommand)) {
            return false;
        }
        // state check
        AddRemarkCommand e = (AddRemarkCommand) other;
        return index.equals(e.index)
                && remarkArrayList.equals(e.remarkArrayList);
    }
}
