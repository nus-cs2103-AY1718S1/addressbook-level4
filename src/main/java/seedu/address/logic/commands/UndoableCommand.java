package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.Address.DEFAULT_ADDRESS;
import static seedu.address.model.person.Email.DEFAULT_EMAIL;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

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

    /**
     * Check for duplicate fields shared with {@code toAdd} in current UniCity contacts. Set isWaitingforReply to true
     * to proceed with prompting user of edit/add command.
     */
    protected void checkDuplicateField(Person toAdd) {
        List<ReadOnlyPerson> currentContacts = model.getFilteredPersonList();
        for (ReadOnlyPerson contact: currentContacts) {
            if (toAdd.getName().toString().trim().equals(contact.getName().toString().trim())) {
                isWaitingforReply = true;
                result = new CommandResult(String.format(MESSAGE_DUPLICATE_FIELD, NAME_FIELD));

            } else if (toAdd.getPhone().toString().trim().equals(contact.getPhone().toString().trim())) {
                isWaitingforReply = true;
                result = new CommandResult(String.format(MESSAGE_DUPLICATE_FIELD, PHONE_FIELD));

            } else if ((toAdd.getAddress().toString().trim().equals(contact.getAddress().toString().trim()))
                    && (!toAdd.getAddress().toString().trim().equals(DEFAULT_ADDRESS))) {
                isWaitingforReply = true;
                result = new CommandResult(String.format(MESSAGE_DUPLICATE_FIELD, ADDRESS_FIELD));

            } else if ((toAdd.getEmail().toString().trim().equals(contact.getEmail().toString().trim()))
                    && (!toAdd.getEmail().toString().trim().equals(DEFAULT_EMAIL))) {
                isWaitingforReply = true;
                result = new CommandResult(String.format(MESSAGE_DUPLICATE_FIELD, EMAIL_FIELD));

            } else {
                continue;
            }
        }
    }
    //@@author
}
