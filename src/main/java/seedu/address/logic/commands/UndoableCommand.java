package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.YearMonth;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PopulateMonthEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

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


    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() throws CommandException {
        requireAllNonNull(model, previousAddressBook);
        //@@author archthegit
        if (model.ifSelectedPerson()) {
            model.unselectPerson();
        }
        //@@author
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        EventsCenter.getInstance().post(new PopulateMonthEvent(YearMonth.now()));
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
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        EventsCenter.getInstance().post(new PopulateMonthEvent(YearMonth.now()));
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
