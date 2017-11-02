package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.ExtendedPersonCardHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatisticsPanelHandle;
import javafx.collections.ObservableList;
import seedu.address.logic.statistics.Statistics;
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
        assertEquals(expectedCard.getFormclass(), actualCard.getFormclass());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getFormClass().value, actualCard.getFormclass());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, ExtendedPersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getFormClass().value, actualCard.getFormclass());
        assertEquals(expectedPerson.getGrades().value, actualCard.getGrades());
        assertEquals(expectedPerson.getPostalCode().value, actualCard.getPostalCode());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getRemark().value, actualCard.getRemark());
    }

    /**
     * Asserts that {@code statisticsPanelHandle} displays the statistics correctly
     */
    public static void assertPanelDisplaysStatistics(ObservableList<ReadOnlyPerson> actualList,
                                               StatisticsPanelHandle actualPanelHandle) {
        Statistics expectedStatistics = new Statistics(actualList);

        assertEquals(expectedStatistics.getMeanString(), actualPanelHandle.getMeanLabel());
        assertEquals(expectedStatistics.getMedianString(), actualPanelHandle.getMedianLabel());
        assertEquals(expectedStatistics.getModeString(), actualPanelHandle.getModeLabel());
        assertEquals(expectedStatistics.getVarianceString(), actualPanelHandle.getVarianceLabel());
        assertEquals(expectedStatistics.getStdDevString(), actualPanelHandle.getStandardDeviationLabel());
        assertEquals(expectedStatistics.getQuartile3String(), actualPanelHandle.getQuartile3Label());
        assertEquals(expectedStatistics.getQuartile1String(), actualPanelHandle.getQuartile1Label());
        assertEquals(expectedStatistics.getInterquartileRangeString(), actualPanelHandle.getInterquartileLabel());
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
     * Replace all occurrence of "/" with ":" and capitalise first letter of student and parent.
     */
    public String changeToAppropriateUiFormat(String value) {

        value = value.replace("/", ": ");
        value = value.replace("s", "S");
        value = value.replace("p", "P");
        return value;
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
