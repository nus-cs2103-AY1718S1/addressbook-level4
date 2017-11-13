package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_REDO_ASSERTION_ERROR;
import static seedu.address.commons.core.Messages.MESSAGE_UNDO_ASSERTION_ERROR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PORTRAIT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PortraitPath;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Adoby7
/**
 * A command that add an head portrait to a person
 */
public class PortraitCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "portrait";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Attach a head portrait to a person"
            + "Parameters: INDEX "
            + "[" + PREFIX_PORTRAIT + "PICTURE_FILE_NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PORTRAIT + "C:/user/images/sample.png";
    public static final String MESSAGE_ADD_PORTRAIT_SUCCESS = "Attached a head portrait to Person: %1$s";
    public static final String MESSAGE_DELETE_PORTRAIT_SUCCESS = "Removed head portrait from Person: %1$s";
    public static final String MESSAGE_PERSON_PARTICIPATE_EVENT_FAIL = "This person has participated some events,"
            + "please disjoin all events before editing his portrait";

    private Index index;
    private PortraitPath filePath;
    private ReadOnlyPerson personToEdit;
    private ReadOnlyPerson editedPerson;

    public PortraitCommand (Index index, PortraitPath filePath) {
        this.index = index;
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());

        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getBirthday(), filePath, personToEdit.getTags(),
            personToEdit.getParticipation());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The edited person cannot cause duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError(MESSAGE_PERSON_PARTICIPATE_EVENT_FAIL);
        }

        String feedback = filePath.toString().isEmpty()
            ? MESSAGE_DELETE_PORTRAIT_SUCCESS : MESSAGE_ADD_PORTRAIT_SUCCESS;
        return new CommandResult(String.format(feedback, personToEdit.getName()));
    }

    @Override
    protected void undo() {
        try {
            model.updatePerson(editedPerson, personToEdit);
        } catch (DuplicatePersonException | PersonNotFoundException | DeleteOnCascadeException e) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException | PersonNotFoundException | DeleteOnCascadeException e) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof PortraitCommand
                && this.index.equals(((PortraitCommand) other).index)
                && this.filePath.equals(((PortraitCommand) other).filePath));
    }
}
