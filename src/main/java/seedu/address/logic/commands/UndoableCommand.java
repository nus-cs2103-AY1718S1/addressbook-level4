package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ListingUnit;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.predicates.UniqueAddressPredicate;
import seedu.address.model.person.predicates.UniqueEmailPredicate;
import seedu.address.model.person.predicates.UniquePhonePredicate;


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
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        handleListingUnit();
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
        handleListingUnit();
    }


    /**
     * handle different ListingUnit after redo and undo
     */
    private  void handleListingUnit() {
        switch (ListingUnit.getCurrentListingUnit()) {

        case ADDRESS:
            UniqueAddressPredicate addressPredicate = new UniqueAddressPredicate(model.getUniqueAdPersonSet());
            model.updateFilteredPersonList(addressPredicate);
            break;

        case PHONE:
            UniquePhonePredicate phonePredicate = new UniquePhonePredicate(model.getUniquePhonePersonSet());
            model.updateFilteredPersonList(phonePredicate);
            break;

        case EMAIL:
            UniqueEmailPredicate emailPredicate = new UniqueEmailPredicate(model.getUniqueEmailPersonSet());
            model.updateFilteredPersonList(emailPredicate);
            break;

        default:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }


    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
