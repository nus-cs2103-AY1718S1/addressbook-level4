package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private ReadOnlyAddressBook previousAddressBook;


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        model.resetData(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    protected void redo() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        model.resetData(new AddressBook());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
}
