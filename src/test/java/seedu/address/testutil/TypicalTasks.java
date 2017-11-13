//@@author srishag
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_HOMEWORK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask PRESENTATION = new TaskBuilder().withHeader("Prepare for presentation")
            .withDesc("Complete slides and script").withDeadline("31/12/2017").build();
    public static final ReadOnlyTask CODE = new TaskBuilder().withHeader("CS2103T")
            .withDesc("Complete testing").withDeadline("12/11/2017").build();

    // Manually added
    public static final ReadOnlyTask MILK = new TaskBuilder().withHeader("Buy milk")
            .withDesc("From NTUC").withDeadline("25/12/2017").build();
    public static final ReadOnlyTask EMAIL = new TaskBuilder().withHeader("Send email")
            .withDesc("Regarding interview").withDeadline("01/11/2017").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask HOMEWORK = new TaskBuilder().withHeader(VALID_HEADER_HOMEWORK)
            .withDesc(VALID_DESC_HOMEWORK).withDeadline(VALID_DEADLINE_HOMEWORK).build();
    public static final ReadOnlyTask ASSIGNMENT = new TaskBuilder().withHeader(VALID_HEADER_ASSIGNMENT)
            .withDesc(VALID_DESC_ASSIGNMENT).withDeadline(VALID_DEADLINE_ASSIGNMENT).build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyTask task : getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(PRESENTATION, CODE, MILK, EMAIL, HOMEWORK, ASSIGNMENT));
    }
}
