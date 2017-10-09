package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.UniqueAddressPredicate;
import seedu.address.model.person.predicates.UniqueEmailPredicate;
import seedu.address.model.person.predicates.UniquePhonePredicate;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_PERSON_WITH_ADDRESS_SUCCESS = "Deleted Person(s) with address: %1$s";
    public static final String MESSAGE_DELETE_PERSON_WITH_EMAIL_SUCCESS = "Deleted Person(s) with email: %1$s";
    public static final String MESSAGE_DELETE_PERSON_WITH_PHONE_SUCCESS = "Deleted Person(s) with phone: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());

        switch (ListingUnit.getCurrentListingUnit()) {

        case ADDRESS:
            return deletePersonWithSpecifiedAddress(personToDelete.getAddress());

        case PHONE:
            return deletePersonWithSpecifiedPhone(personToDelete.getPhone());

        case EMAIL:
            return deletePersonWithSpecifiedEmail(personToDelete.getEmail());

        default:
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        }
    }

    /**
     * Delete all persons with specified address
     */
    private CommandResult deletePersonWithSpecifiedAddress(Address address) {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> personList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToDelete = new ArrayList<ReadOnlyPerson>();

        try {
            for (ReadOnlyPerson p : personList) {
                if (p.getAddress().equals(address)) {
                    personsToDelete.add(p);
                }
            }
            model.deletePersonSet(personsToDelete);
            model.updateFilteredPersonList(new UniqueAddressPredicate(model.getUniqueAdPersonSet()));

        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_WITH_ADDRESS_SUCCESS, address));
    }

    /**
     * Delete all persons with specified email.
     */
    private CommandResult deletePersonWithSpecifiedEmail(Email email) {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> personList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToDelete = new ArrayList<ReadOnlyPerson>();

        try {
            for (ReadOnlyPerson p : personList) {
                if (p.getEmail().equals(email)) {
                    personsToDelete.add(p);
                }
            }
            model.deletePersonSet(personsToDelete);
            model.updateFilteredPersonList(new UniqueEmailPredicate(model.getUniqueEmailPersonSet()));

        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_WITH_EMAIL_SUCCESS, email));
    }

    /**
     * Delete all persons with specified phone.
     */
    private CommandResult deletePersonWithSpecifiedPhone(Phone phone) {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> personList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToDelete = new ArrayList<ReadOnlyPerson>();

        try {
            for (ReadOnlyPerson p : personList) {
                if (p.getPhone().equals(phone)) {
                    personsToDelete.add(p);
                }
            }
            model.deletePersonSet(personsToDelete);
            model.updateFilteredPersonList(new UniquePhonePredicate(model.getUniquePhonePersonSet()));

        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_WITH_PHONE_SUCCESS, phone));
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
