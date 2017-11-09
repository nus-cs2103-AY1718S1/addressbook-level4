package seedu.address.logic.commands;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
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

//@@author seankwekjk
/**
 * Adds, removes and edits remarks of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to a person in the address book. "
            + "Parameters: "
            + "INDEX "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 " + PREFIX_REMARK
            + "Likes to drink coffee.";

    public static final String REMARK_EDIT_SUCCESS = "Remark added.";
    public static final String REMARK_CLEAR_SUCCESS = "Remark cleared.";

    private Remark remark;
    private Index index;

    /**
     * @param remark text of the remark
     * @param index of the person whose remark is being modified
     */
    public RemarkCommand(Remark remark, Index index) {
        this.remark = remark;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                                personToEdit.getAddress(), personToEdit.getBirthday(), remark, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(successMessageType(editedPerson));
    }

    /**
     * Creates and returns a {@code String} based on the edited Remark of {@code personToEdit}
     */
    private String successMessageType(ReadOnlyPerson personToEdit) {
        if (!remark.getRemarkText().isEmpty()) {
            return String.format(REMARK_EDIT_SUCCESS, personToEdit);
        } else {
            return String.format(REMARK_CLEAR_SUCCESS, personToEdit);
        }
    }

    public Remark getRemark() {
        return remark;
    }

    public Index getIndex() {
        return index;
    }
}
