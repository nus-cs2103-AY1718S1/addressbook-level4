package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.InfoPanelHandle;
import guitests.guihandles.PersonCardHandle;
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
        assertEquals(expectedCard.getDebt(), actualCard.getDebt());
        assertEquals(expectedCard.getTotalDebt(), actualCard.getTotalDebt());
        assertEquals(expectedCard.getDeadline(), actualCard.getDeadline());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getDebt().toString(), actualCard.getDebt());
        assertEquals(expectedPerson.getTotalDebt().toString(), actualCard.getTotalDebt());
        assertEquals(expectedPerson.getDeadline().valueToDisplay, actualCard.getDeadline());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    //@@author khooroko
    /**
     * Asserts that {@code actualInfo} displays the details of {@code expectedPerson}.
     */
    public static void assertInfoDisplaysPerson(ReadOnlyPerson expectedPerson, InfoPanelHandle actualInfo) {
        assertEquals(expectedPerson.getName().fullName, actualInfo.getName());
        assertEquals(expectedPerson.getHandphone().value, actualInfo.getHandphone());
        assertEquals(expectedPerson.getHomePhone().value, actualInfo.getHomePhone());
        assertEquals(expectedPerson.getOfficePhone().value, actualInfo.getOfficePhone());
        assertEquals(expectedPerson.getEmail().value, actualInfo.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualInfo.getAddress());
        assertEquals(expectedPerson.getPostalCode().value, actualInfo.getPostalCode());
        assertEquals(expectedPerson.getCluster().value, actualInfo.getCluster());
        assertEquals(expectedPerson.getInterest().value, actualInfo.getInterest());
        assertEquals(expectedPerson.getDebt().toString(), actualInfo.getDebt());
        assertEquals(expectedPerson.getTotalDebt().toString(), actualInfo.getTotalDebt());
        assertEquals(expectedPerson.getDateBorrow().value, actualInfo.getDateBorrow());
        assertEquals(expectedPerson.getDeadline().valueToDisplay, actualInfo.getDeadline());
        assertEquals(expectedPerson.getDateRepaid().value, actualInfo.getDateRepaid());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualInfo.getTags());
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
