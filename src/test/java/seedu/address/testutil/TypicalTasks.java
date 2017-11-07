package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_SHOPPING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.task.ReadOnlyTask;


//@@author zhangshuoyang
/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask HOMEWORK = new TaskBuilder().withDescription("CS2103 Homework")
            .withPriority("2")
            .withDueDate("25/10/2017").build();

    public static final ReadOnlyTask SURVEY = new TaskBuilder().withDescription("Complete Peer Review Survey")
            .withPriority("1")
            .withDueDate("01/11/2017").build();


    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask ASSIGNMENT = new TaskBuilder().withDescription(VALID_DESCRIPTION_ASSIGNMENT)
            .withPriority(VALID_PRIORITY_ASSIGNMENT).withDueDate(VALID_DUEDATE_ASSIGNMENT).build();

    public static final ReadOnlyTask SHOPPING = new TaskBuilder().withDescription(VALID_DESCRIPTION_SHOPPING)
            .withPriority(VALID_PRIORITY_SHOPPING).withDueDate(VALID_DUEDATE_SHOPPING).build();

    private TypicalTasks() {} // prevents instantiation

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(HOMEWORK, SURVEY));
    }

}

