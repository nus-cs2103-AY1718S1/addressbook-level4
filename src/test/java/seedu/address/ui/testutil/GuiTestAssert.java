package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.BirthdayReminderCardHandle;
import guitests.guihandles.DetailsPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.PopularContactCardHandle;
import guitests.guihandles.ReminderCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;
import seedu.address.model.reminders.Reminder;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getNickname(), actualCard.getNickname());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertDetailsCardEquals(DetailsPanelHandle expectedCard, DetailsPanelHandle actualCard) {
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getNickname(), actualCard.getNickname());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        assertEquals(expectedCard.getBirthday(), actualCard.getBirthday());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertPopularCardEquals(PopularContactCardHandle expectedCard,
                                               PopularContactCardHandle actualCard) {
        assertEquals(expectedCard.getRank(), actualCard.getRank());
        assertEquals(expectedCard.getName(), actualCard.getName());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getTags().size(), actualCard.getTags().size());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPersonDetailsPanel(ReadOnlyPerson expectedPerson,
                                                            DetailsPanelHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getNickname().value, actualCard.getNickname());
        assertEquals(expectedPerson.getBirthday().value, actualCard.getBirthday());
        assertEquals(expectedPerson.getTags().size(), actualCard.getTags().size());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPopularPerson(ReadOnlyPerson expectedPerson,
                                                       PopularContactCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());

    }

    //@@author justinpoh
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertBirthdayReminderCardDisplaysPerson(ReadOnlyPerson expectedPerson,
                                                                BirthdayReminderCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getBirthday().value, actualCard.getBirthday());
        assertEquals(expectedPerson.getNickname().value, actualCard.getNickname());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedReminder}.
     */
    public static void assertReminderCardDisplaysReminder(Reminder expectedReminder,
                                                                ReminderCardHandle actualCard) {
        assertEquals(expectedReminder.getReminder(), actualCard.getReminder());
        assertEquals(expectedReminder.getDate().toString(), actualCard.getDate());
        assertEquals(expectedReminder.getTime().toString(), actualCard.getTime());
    }
    //@@author

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, ReadOnlyPerson... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<ReadOnlyPerson> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new ReadOnlyPerson[0]));
    }
    //@@author tshradheya
    /**
     * Asserts that the list in {@code uniqueReminderList} displays the details of {@code reminders} correctly and
     * in the correct order.
     */
    public static void assertListMatchingReminders(ReadOnlyUniqueReminderList uniqueReminderList, Reminder... reminders) {
        for (int i = 0; i < reminders.length; i++) {
            assertEquals(uniqueReminderList.asObservableList().get(i), reminders[i]);

        }
    }
    //@@author

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
