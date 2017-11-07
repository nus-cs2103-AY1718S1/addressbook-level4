package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;


import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {

    //@@author LeeYingZheng
    public static final String MESSAGE_DUPLICATE_FIELD = "This person's %1$s is already in use."
            + "Would you like to continue? YES or NO?";
    public static final String NAME_FIELD = "name";
    public static final String PHONE_FIELD = "phone";
    public static final String ADDRESS_FIELD = "address";
    public static final String EMAIL_FIELD = "email";

    protected static boolean isWaitingforReply;
    protected CommandResult result;
    //@@author

    private ReadOnlyAddressBook previousAddressBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
    }

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }

    //@@author LeeYingZheng
    /**
     * Prepare to reply prompt. Sets isWaitingforReply to false.
     */
    public static void reply() {
        isWaitingforReply = false;
    }

    //@@author
}
