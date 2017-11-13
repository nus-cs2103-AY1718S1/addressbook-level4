package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;

import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author frozventus
/**
 * An extension of AddressBook to hold deleted contacts
 * Duplicates are not allowed (by .equals comparison)
 */
public class RecycleBin extends AddressBook {

    public RecycleBin() {}

    /**
     * Creates a RecycleBin using the Persons and Tags in the {@code toBeCopied}
     */
    public RecycleBin(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    @Override
    /**
     * Resets the existing data of this {@code RecycleBin} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }
}
