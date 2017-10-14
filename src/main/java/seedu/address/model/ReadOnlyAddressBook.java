package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.module.ReadOnlyModule;
import seedu.address.model.tag.Lecturer;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<ReadOnlyModule> getModuleList();

    /**
     * Returns an unmodifiable view of the lecturers list.
     * This list will not contain any duplicate lecturers.
     */
    ObservableList<Lecturer> getLecturerList();

}
