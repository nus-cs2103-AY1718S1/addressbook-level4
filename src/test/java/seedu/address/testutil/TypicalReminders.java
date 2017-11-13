package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.storage.XmlSerializableReminders;

//@@author justinpoh
/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final ReadOnlyReminder COFFEE_REMINDER = new ReminderBuilder().build();
    public static final ReadOnlyReminder HOMEWORK_REMINDER = new ReminderBuilder().withReminder("Do homework")
                                                        .withDate("01/01/2018").withTime("07:30").build();
    public static final ReadOnlyReminder DINNER_REMINDER = new ReminderBuilder().withReminder("Dinner with family")
                                                        .withDate("25/12/2017").withTime("18:00").build();

    // Manually added
    public static final ReadOnlyReminder MEETING_REMINDER = new ReminderBuilder().withReminder("Meet with CS2103 group")
                                                  .withDate("09/09/2017").withTime("12:00").build();
    public static final ReadOnlyReminder DENTIST_REMINDER = new ReminderBuilder().withReminder("Go for dental checkup")
            .withDate("10/10/2017").withTime("14:00").build();

    private TypicalReminders() {} //prevents instantiation

    public static List<ReadOnlyReminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(COFFEE_REMINDER, HOMEWORK_REMINDER, DINNER_REMINDER));
    }

    /**
     * Utility method to return a UniqueReminderList containing a list of {@code Reminder} objects
     * to be used in tests.
     */
    public static UniqueReminderList getUniqueTypicalReminders() {
        return new UniqueReminderList(new XmlSerializableReminders(getTypicalReminders()));
    }
}
