package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;

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
        //@@author sebtsh
        assertEquals(expectedCard.getCompany(), actualCard.getCompany());
        assertEquals(expectedCard.getPosition(), actualCard.getPosition());
        assertEquals(expectedCard.getPriority(), actualCard.getPriority());
        //@@author
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        //@@author sebtsh
        assertEquals(expectedPerson.getPosition().value, actualCard.getPosition());
        assertEquals(expectedPerson.getCompany().value, actualCard.getCompany());
        assertEquals(expectedPerson.getPriority().value, actualCard.getPriority());
        //@@author
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }


    /**
     * Asserts that {@code actualCard} displays the details of {@code
     * expectedEvent}.
     */
    public static void assertCardDisplaysEvent(ReadOnlyEvent expectedEvent,
                                               EventCardHandle actualCard) {
        assertEquals(expectedEvent.getTitle().title, actualCard.getTitle());
        assertEquals(expectedEvent.getTiming().toString(), actualCard.getTiming());
        assertEquals(expectedEvent.getDate().toString(), actualCard.getDate());
        assertEquals(expectedEvent.getDescription().description, actualCard.getDescription());
    }

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
