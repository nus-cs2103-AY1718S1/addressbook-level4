package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.testutil.TypicalTasks;

//@@author charlesgoh
public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniquePersonList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }

    @Test
    public void sortTasks_byDeadline_bothOrders() {
        // Set up expected result
        List<ReadOnlyTask> taskList = TypicalTasks.getTypicalTasks();
        Comparator<ReadOnlyTask> deadlineComparator = new Comparator<ReadOnlyTask>() {
            @Override
            public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
                return o1.getDeadline().date.compareTo(o2.getDeadline().date);
            }
        };
        Collections.sort(taskList, deadlineComparator);
        ObservableList<ReadOnlyTask> expectedTaskList = FXCollections.observableList(taskList);

        // Set up actual result
        AddressBook addressBook = TypicalTasks.getTypicalTasksOnlyAddressBook();
        addressBook.sortTasksBy("deadline", "asc");
        ObservableList<ReadOnlyTask> actualTaskList = addressBook.getTasksList();

        // Check ascending order sort
        assertEquals(expectedTaskList, actualTaskList);

        // Set up descending order sort
        Collections.sort(taskList, Collections.reverseOrder(deadlineComparator));
        expectedTaskList = FXCollections.observableList(taskList);
        addressBook.sortTasksBy("deadline", "desc");
        actualTaskList = addressBook.getTasksList();

        // Check descending order sort
        assertEquals(expectedTaskList, actualTaskList);
    }

    @Test
    public void sortTasks_byPriority_bothOrders() {
        // Set up expected result
        List<ReadOnlyTask> taskList = TypicalTasks.getTypicalTasks();
        Comparator<ReadOnlyTask> deadlineComparator = new Comparator<ReadOnlyTask>() {
            @Override
            public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
                return Integer.compare(o1.getPriority().value, o2.getPriority().value);
            }
        };
        Collections.sort(taskList, deadlineComparator);
        ObservableList<ReadOnlyTask> expectedTaskList = FXCollections.observableList(taskList);

        // Set up actual result
        AddressBook addressBook = TypicalTasks.getTypicalTasksOnlyAddressBook();
        addressBook.sortTasksBy("priority", "asc");
        ObservableList<ReadOnlyTask> actualTaskList = addressBook.getTasksList();

        // Check ascending order sort
        assertEquals(expectedTaskList, actualTaskList);

        // Set up descending order sort
        Collections.sort(taskList, Collections.reverseOrder(deadlineComparator));
        expectedTaskList = FXCollections.observableList(taskList);
        addressBook.sortTasksBy("priority", "desc");
        actualTaskList = addressBook.getTasksList();

        // Check descending order sort
        assertEquals(expectedTaskList, actualTaskList);
    }
}
