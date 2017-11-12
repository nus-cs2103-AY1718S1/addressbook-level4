package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
//@@author Jeremy
/**
 * Adds/Remove a remark from a person identified using it's last displayed index from the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. \n"
            + "Parameters: INDEX" + " "
            + PREFIX_REMARK
            + "Likes to drink coffee \n"
            + "Example 1: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to drink coffee \n"
            + "Removing Remarks: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK;

    // The first argument is referenced by "1$", the second by "2$"
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remark successfully added";
    public static final String MESSAGE_REMOVE_REMARK_SUCCESS = "Remark successfully deleted";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index personIndex;
    private final Remark remark;

    /**
     * Returns nothing. Setter class to set index and remark of contact.
     *
     * @param inputIndex Index on filtered list to add the remark to.
     * @param inputRemark Remark to give to the contact.
     */
    public RemarkCommand(Index inputIndex, Remark inputRemark) {
        requireNonNull(inputIndex);
        requireNonNull(inputRemark);

        this.personIndex = inputIndex;
        this.remark = inputRemark;
    }

    /**
     * Returns success message and adds a remark to a contact.
     *
     * @return Success Message.
     * @throws CommandException If index is invalid.
     * @throws CommandException If there is a duplicate person when updating data.
     * @throws AssertionError If person is missing from data.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(personIndex.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getBloodType(),
                personToEdit.getTags(), this.remark, personToEdit.getRelationship(), personToEdit.getAppointments());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.getFilteredPersonList();
        return new CommandResult(outputCorrectTypeOfSuccessMessage(editedPerson));
    }

    /**
     * Checks if 'other' is the same object or an instance of this object.
     *
     * @param other Another object for evaluation.
     * @return True if 'other' is the same object or an instance of this object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && this.personIndex.equals(((RemarkCommand) other).personIndex))
                && this.remark.equals(((RemarkCommand) other).remark); // state check
    }

    /**
     * Returns the appropriate success message depending on whether a remark is added or removed.
     *
     * @param editedPerson ReadOnlyPerson that is edited.
     * @return Remove Remark message if a remark is removed or success remark message if remark is added.
     */
    private String outputCorrectTypeOfSuccessMessage(ReadOnlyPerson editedPerson) {
        if (editedPerson.getRemark().toString().isEmpty()) {
            return String.format(MESSAGE_REMOVE_REMARK_SUCCESS, editedPerson);
        }
        return String.format(MESSAGE_REMARK_PERSON_SUCCESS, editedPerson);
    }

}
