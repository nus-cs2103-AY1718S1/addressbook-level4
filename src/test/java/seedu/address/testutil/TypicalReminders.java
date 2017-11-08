//@@author inGall
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_OFFICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SOFTCOPY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PROJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final ReadOnlyReminder BIRTHDAY = new ReminderBuilder().withTask("James birthday")
            .withPriority("Low").withDate("02/02/2017 16:00").withMessage("Buy present with others.")
            .withTags("Watch", "friends", "retrieveTester").build();
    public static final ReadOnlyReminder DATING = new ReminderBuilder().withTask("Dating with Joanne")
            .withPriority("Low").withDate("01/01/2017 15:00").withMessage("Meet at Clementi")
            .withTags("Present", "retrieveTester").build();
    public static final ReadOnlyReminder GATHERING = new ReminderBuilder().withTask("Gathering with friends")
            .withMessage("Gather at John's house").withDate("05/05/2017 12:00").withPriority("Medium").build();
    public static final ReadOnlyReminder LUNCH = new ReminderBuilder().withTask("Lunch with Joseph")
            .withMessage("Venue at JE").withDate("06/06/2017 12:00").withPriority("Medium").build();
    public static final ReadOnlyReminder MEETING = new ReminderBuilder().withTask("Group Meeting")
            .withMessage("Have all reports ready").withDate("04/04/2017 09:00").withPriority("High").build();
    public static final ReadOnlyReminder PARTY = new ReminderBuilder().withTask("Group Party")
            .withMessage("DressCode is black and white").withDate("03/03/2017 20:00").withPriority("High").build();

    // Manually added
    public static final ReadOnlyReminder DINNER = new ReminderBuilder().withTask("Dinner at home")
            .withMessage("Steamboat").withDate("07/07/2017 18:00").withPriority("Medium").build();
    public static final ReadOnlyReminder COLLECTION = new ReminderBuilder().withTask("Items Collection")
            .withMessage("Collect items at post office").withDate("08/08/2017 18:00").withPriority("Low").build();

    // Manually added - Reminder's details found in {@code CommandTestUtil}
    public static final ReadOnlyReminder PROJECT = new ReminderBuilder().withTask(VALID_TASK_PROJECT)
            .withMessage(VALID_MESSAGE_PROJECT).withDate(VALID_DATE_PROJECT).withPriority(VALID_PRIORITY_PROJECT)
            .withTags(VALID_TAG_OFFICE).build();
    public static final ReadOnlyReminder ASSIGNMENT = new ReminderBuilder().withTask(VALID_TASK_ASSIGNMENT)
            .withMessage(VALID_MESSAGE_ASSIGNMENT).withDate(VALID_DATE_ASSIGNMENT)
            .withPriority(VALID_PRIORITY_ASSIGNMENT).withTags(VALID_TAG_SOFTCOPY, VALID_TAG_OFFICE).build();

    public static final String KEYWORD_MATCHING_GROUP = "Group"; // A keyword that matches GROUP

    // A keyword that matches RETRIEVETESTER
    public static final String KEYWORD_MATCHING_RETRIEVETESTER = "retrieveTester";

    private TypicalReminders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyReminder reminder : getTypicalReminders()) {
            try {
                ab.addReminder(reminder);
            } catch (DuplicateReminderException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyReminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(BIRTHDAY, DATING, GATHERING, LUNCH, MEETING, PARTY));
    }
}
