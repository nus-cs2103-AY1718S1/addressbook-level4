package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
    }

    //@@author hthjthtrh
    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */

    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        if (!(this instanceof  GroupTypeUndoableCommand)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } else {
            Index temp = ((GroupTypeUndoableCommand) this).undoGroupIndex;
            EventsCenter.getInstance().post(new JumpToListRequestEvent(temp, true));

        }
    }


    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() throws CommandException {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            if (!(this instanceof GroupTypeUndoableCommand)) {
                throw new AssertionError("The command has been successfully executed previously; "
                        + "it should not fail now");
            } else {
                throw new CommandException(ce.getExceptionHeader(), constructNewCommandExceptionMsg(ce));
            }
        }
        if (!(this instanceof GroupTypeUndoableCommand)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    /**
     * constructs new body message for command exception
     * @param ce
     * @return
     */
    private String constructNewCommandExceptionMsg(CommandException ce) {
        return ce.getMessage() + "\n\nCommand you tried to redo: "
                + ((EditGroupCommand) this).reconstructCommandString();
    }
    //@@author

    /**
     * Creates a string representing the list of person of concern.
     */
    public static void appendPersonList(StringBuilder sb, List<ReadOnlyPerson> persons) {
        for (int i = 0; i < persons.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(persons.get(i));
            if (i != persons.size() - 1) {
                sb.append("\n");
            }
        }
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
