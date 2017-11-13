package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonDetailsPanelHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
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
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    // @@author johnweikangong
    /**
     * Asserts that {@code actualPersonDetailsPanel} is empty
     */
    public static void assertEmptyPersonDetailsPanel(PersonDetailsPanelHandle actualPersonDetailsPanel) {
        assertEquals("", actualPersonDetailsPanel.getName());
        assertEquals("", actualPersonDetailsPanel.getPhone());
        assertEquals("", actualPersonDetailsPanel.getBirthday());
        assertEquals("", actualPersonDetailsPanel.getEmail());
        assertEquals("", actualPersonDetailsPanel.getAddress());
        assertEquals("", actualPersonDetailsPanel.getPostalCode());
    }

    /**
     * Asserts that {@code actualPersonDetailsPanel} displays the details of {@code expectedPersonDetailsPanel}
     */
    public static void assertPersonDetailsPanelDisplaysPerson(ReadOnlyPerson expectedPerson,
                                                  PersonDetailsPanelHandle actualPersonDetailsPanel) {
        assertEquals(expectedPerson.getName().fullName, actualPersonDetailsPanel.getName());
        assertEquals(expectedPerson.getPhone().value, actualPersonDetailsPanel.getPhone());
        assertEquals(expectedPerson.getBirthday().value, actualPersonDetailsPanel.getBirthday());
        assertEquals(expectedPerson.getEmail().value, actualPersonDetailsPanel.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualPersonDetailsPanel.getAddress());
        assertEquals(expectedPerson.getPostalCode().value, actualPersonDetailsPanel.getPostalCode());
    }
    // @@author

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
