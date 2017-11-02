package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;

//@@author deep4k
/**
 * A utility class containing a list of {@code Tasks} objects to be used in tests.
 */
public class TypicalTasks {

    public static final LocalDateTime LUNCH_START_TIME = LocalDateTime.now().minusHours(6);
    public static final LocalDateTime LUNCH_END_TIME = LocalDateTime.now().minusHours(5);

    public static final LocalDateTime DINNER_START_TIME = LocalDateTime.now().minusHours(2);
    public static final LocalDateTime DINNER_END_TIME = LocalDateTime.now().minusHours(1);

    public static final LocalDateTime BREAKFAST_START_TIME = LocalDateTime.now().minusHours(9);
    public static final LocalDateTime BREAKFAST_END_TIME = LocalDateTime.now().minusHours(8);

    public static final LocalDateTime TOMORROW = LocalDateTime.now().plusDays(1);

    public static final ReadOnlyTask LUNCH = buildTask("LUNCH", LUNCH_START_TIME, LUNCH_END_TIME);
    public static final ReadOnlyTask DINNER = buildTask("DINNER", DINNER_START_TIME, DINNER_END_TIME);
    public static final ReadOnlyTask BREAKFAST =
            buildTask("BREAKFAST", BREAKFAST_START_TIME, BREAKFAST_END_TIME);
    public static final ReadOnlyTask WAKE_UP = buildTask("WAKE UP", TOMORROW);

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns Task object with completed start and end time using the TaskBuilder
     */
    private static ReadOnlyTask buildTask(String header, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return new TaskBuilder().withHeader(header).withStartTime(startTime)
                    .withEndTime(endTime).withCompletionStatus().build();
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
            return null;
        }
    }

    /**
     * Returns Task object with incomplete deadline only using the TaskBuilder
     */
    private static ReadOnlyTask buildTask(String header, LocalDateTime deadLine) {
        try {
            return new TaskBuilder().withHeader(header).withEndTime(deadLine).withIncompletionStatus().build();
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
            return null;
        }
    }

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
        return new ArrayList<>(Arrays.asList(LUNCH, DINNER, BREAKFAST, WAKE_UP));
    }

}
