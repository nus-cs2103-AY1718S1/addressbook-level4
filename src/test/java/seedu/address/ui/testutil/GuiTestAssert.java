package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        //@@author srishag
        assertEquals(expectedCard.getBirthday(), actualCard.getBirthday());
        //@@author
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    //@@author srishag
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertTaskCardEquals(TaskCardHandle expectedCard, TaskCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getHeader(), actualCard.getHeader());
        assertEquals(expectedCard.getDesc(), actualCard.getDesc());
        assertEquals(expectedCard.getDeadline(), actualCard.getDeadline());
    }
    //@@author

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        //@@author srishag
        assertEquals(expectedPerson.getBirthday().value, actualCard.getBirthday());
        //@@author
        assertEquals(expectedPerson.getFacebookAddress().value, actualCard.getFacebookAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    //@@author srishag
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTask}.
     */
    public static void assertCardDisplaysTask(ReadOnlyTask expectedTask, TaskCardHandle actualCard) {
        assertEquals(expectedTask.getHeader().value, actualCard.getHeader());
        assertEquals(expectedTask.getDesc().value, actualCard.getDesc());
        assertEquals(expectedTask.getDeadline().value, actualCard.getDeadline());
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

    //@@author srishag
    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code tasks} correctly and
     * in the correct order.
     */
    public static void assertTaskListMatching(TaskListPanelHandle taskListPanelHandle, ReadOnlyTask... tasks) {
        for (int i = 0; i < tasks.length; i++) {
            assertCardDisplaysTask(tasks[i], taskListPanelHandle.getTaskCardHandle(i));
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
