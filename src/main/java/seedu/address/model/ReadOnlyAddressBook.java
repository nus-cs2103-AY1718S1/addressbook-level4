package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;

//@@author caoliangnus
/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the lesson list.
     * This list will not contain any duplicate lessons.
     */
    ObservableList<ReadOnlyLesson> getLessonList();

    /**
     * Returns an unmodifiable view of the lecturers list.
     * This list will not contain any duplicate lecturers.
     */
    ObservableList<Lecturer> getLecturerList();

    //@@author junming403
    /**
     * Returns an unmodifiable view of the remarks list.
     * This list will not contain any duplicate remarks.
     */
    ObservableList<Remark> getRemarkList();
    //@@author

}
