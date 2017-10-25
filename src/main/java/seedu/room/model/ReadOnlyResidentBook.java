package seedu.room.model;

import javafx.collections.ObservableList;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.tag.Tag;

/**
 * Unmodifiable view of an resident book
 */
public interface ReadOnlyResidentBook {

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

}
