package seedu.address.model;

import java.util.Map;
import java.util.UUID;

import javafx.collections.ObservableList;

import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    //@@author OscarWang114
    /**
     * Returns an unmodifiable view of the life insurances map.
     * This map will not contain any duplicate insurances.
     */
    Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap();
    //@@author
}
