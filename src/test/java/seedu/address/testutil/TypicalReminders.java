package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.storage.XmlSerializableReminders;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final Reminder COFFEE_REMINDER = new ReminderBuilder().build();
    public static final Reminder HOMEWORK_REMINDER = new ReminderBuilder().withReminder("Do homework")
                                                        .withDueDate("01/01/2018", "0730").build();
    public static final Reminder DINNER_REMINDER = new ReminderBuilder().withReminder("Dinner with family")
                                                        .withDueDate("25/12/2017", "1800").build();

    // Manually added
    public static Reminder MEETING_REMINDER = new ReminderBuilder().withReminder("Meet with CS2103 group")
                                                  .withDueDate("09/09/2017", "1200").build();
    public static Reminder DENTIST_REMINDER = new ReminderBuilder().withReminder("Go for dental checkup")
            .withDueDate("10/10/2017", "1400").build();

    private TypicalReminders() {} //prevents instantiation

    public static List<Reminder> getTypicalReminders() {
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
