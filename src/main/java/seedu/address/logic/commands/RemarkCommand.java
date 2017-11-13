package seedu.address.logic.commands;
//@@author yangminxingnus
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Add a remark for a specified person
 */
public class RemarkCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remark the module information of the person identified by the index. "
            + "Existing modulelist will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "MODULENAME1/MODULETYPE1/NUM1,MODULENAME2/MODULETYPE2/NUM2\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "CS2101/SEC/1,CS2104/LEC/1,CS2105/LEC/1,CS2102/LEC/1";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Integer index;
    private final Remark remark;

    /**
          * @param index of the person in the filtered person list to edit the remark
          * @param remark of the person
          */
    public RemarkCommand(Integer index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);
        this.index = index - 1;
        this.remark = remark;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (index < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getDateOfBirth(),
                remark, personToEdit.getImage(), personToEdit.getUsername(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_ADD_REMARK_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (this.remark.equals(((RemarkCommand) other).remark)
                && this.index == ((RemarkCommand) other).index);
    }
}
//@@author
