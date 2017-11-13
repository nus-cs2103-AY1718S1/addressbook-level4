package seedu.address.model.reminders;

import javafx.collections.ObservableList;

//@@author justinpoh
/**
 * Unmodifiable view of a UniqueReminderList
 */
public interface ReadOnlyUniqueReminderList {

    /**
     * Returns an unmodifiable view of the reminders list.
     * This list will not contain any duplicate reminders.
     */
    ObservableList<ReadOnlyReminder> asObservableList();
}
