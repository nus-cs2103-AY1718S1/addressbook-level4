package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.reminders.UniqueReminderList;

//@@author justinpoh
/** Indicates the reminders in the model have changed. */
public class RemindersChangedEvent extends BaseEvent {
    public final UniqueReminderList reminderList;

    public RemindersChangedEvent(UniqueReminderList reminderList) {
        this.reminderList = reminderList;
    }

    @Override
    public String toString() {
        return "number of reminders " + reminderList.size();
    }
}
