package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
     * Creates a RemarkCommand to add the remark
     */
    public RemarkCommand(Index inputIndex, Remark inputRemark) {
        requireNonNull(inputIndex);
        requireNonNull(inputRemark);

        this.personIndex = inputIndex;
        this.remark = inputRemark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(personIndex.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getBloodType(),
                personToEdit.getTags(), this.remark);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(outputCorrectTypeOfSuccessMessage(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // Check if
        // (a) Object is the same object
        // (b) Object is an instance of the object and that personIndex and remarkString are the same
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && this.personIndex.equals(((RemarkCommand) other).personIndex))
                && this.remark.equals(((RemarkCommand) other).remark); // state check
    }

    /**
     * Outputs success message based on whether a remark is added or removed
     */
    private String outputCorrectTypeOfSuccessMessage(ReadOnlyPerson editedPerson) {
        if (editedPerson.getRemark().toString().isEmpty()) {
            return String.format(MESSAGE_REMOVE_REMARK_SUCCESS, editedPerson);
        } else {
            return String.format(MESSAGE_REMARK_PERSON_SUCCESS, editedPerson);
        }
    }

}
