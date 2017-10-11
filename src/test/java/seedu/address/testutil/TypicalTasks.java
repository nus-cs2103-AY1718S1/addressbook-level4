package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.TaskBook;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalTasks {

    private TypicalTasks() {} // prevents instantiation

    /**
     * get a typical task book, keep it empty for now
     * @return
     */
    public static TaskBook getTypicalTaskbook() {
        TaskBook tb = new TaskBook();
        return tb;
    }

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>();
    }
}
