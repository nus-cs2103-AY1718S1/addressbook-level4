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
 * Command class for Remark
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String SAMPLE_COMMAND = COMMAND_WORD + " 1 " + PREFIX_REMARK + "Likes to code.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_PARAMETERS = "INDEX" + PREFIX_REMARK + "[REMARK]\n";

    public static final String MESSAGE_ADD_REMARK = "Added remark to person: %1$s";
    public static final String MESSAGE_DELETE_REMARK = "Deleted remark from person:  %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists";

    private final Index index;
    private final Remark remark;

    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.remark = remark;
        this.index = index;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        //If given index is out of bounds
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson initialPerson = lastShownList.get(index.getZeroBased());
        ReadOnlyPerson newPerson = new Person(initialPerson.getName(), initialPerson.getPhone(),
                initialPerson.getEmail(), initialPerson.getAddress(), initialPerson.getTags(), remark);

        try {
            model.updatePerson(initialPerson, newPerson);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            throw new CommandException("The target person is not found");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateMessageSuccess(newPerson));
    }

    @Override
    public boolean equals(Object compare) {
        if (compare == this) {
            return true;
        } else if (!(compare instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand other = (RemarkCommand) compare;
        return other.index.equals(index) && other.remark.equals(remark);
    }

    /**
     * Generates a message for successful operations
     * @param newPerson
     * @return success message
     */
    private String generateMessageSuccess(ReadOnlyPerson newPerson) {
        if (!remark.remark.isEmpty()) {
            return String.format(MESSAGE_DELETE_REMARK, newPerson);
        } else {
            return String.format(MESSAGE_ADD_REMARK, newPerson);
        }
    }
}
