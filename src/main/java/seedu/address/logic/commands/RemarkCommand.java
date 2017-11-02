package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.person.Remark.REMARK_IF_EMPTY;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author nahtanojmil
/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from person: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : edits the remarks of a person "
            + "by the index from the last shown list. Old remarks will be overwritten by new "
            + "ones entered.\n"
            + "parameters: INDEX (positive integer)" + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + "3 " + PREFIX_REMARK + "likes to eat!";

    private Index index;
    private Remark stringRemark;


    public RemarkCommand(Index index, Remark remark) {
        if (index != null && remark != null) {
            this.index = index;
            this.stringRemark = remark;
        }
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson toBeEdited = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(toBeEdited.getName(), toBeEdited.getPhone(),
                toBeEdited.getEmail(), toBeEdited.getAddress(), toBeEdited.getFormClass(),
                toBeEdited.getGrades(), toBeEdited.getPostalCode(), stringRemark, toBeEdited.getTags());

        try {
            model.updatePerson(toBeEdited, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException f) {
            throw new AssertionError("Person must be from last filtered list");
        }
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * @param editedPerson takes in a edited person and
     * @return if the remark changed is successful
     */
    private String generateSuccessMessage(ReadOnlyPerson editedPerson) {
        if (!stringRemark.value.equals(REMARK_IF_EMPTY)) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, editedPerson);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit
            return true;
        }
        if (!(other instanceof RemarkCommand)) { // if other is not RemarkCommand type; return false
            return false;
        }

        //state check: checks if both are the same object in each class
        RemarkCommand e = (RemarkCommand) other;
        return stringRemark.equals(e.stringRemark) && index.equals(e.index);
    }
}
