//@@author ShaocongDong
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskBook;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask PICNIC = new TaskBuilder().withName("picnic")
            .withDescription("Have a good time with my best friends")
            .withStart("20-05-2018 11:00pm")
            .withEnd("20-05-2018 12:00pm").withTags("Friends", "Fun").build();

    public static final ReadOnlyTask MEETING = new TaskBuilder().withName("meeting")
            .withDescription("Have a CS2101 group meeting for oral presentation 2")
            .withStart("20-05-2017 11:00pm")
            .withEnd("20-05-2017 12:00pm").withTags("Study").build();

    public static final ReadOnlyTask EXAM = new TaskBuilder().withName("CS2103 exam")
            .withDescription("Have a final exam for CS2103T")
            .withStart("20-05-2016 11:00pm")
            .withEnd("20-05-2016 12:00pm").withTags("Study").build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * get a typical task book, keep it empty for now
     * @return
     */
    public static TaskBook getTypicalTaskbook() {
        TaskBook tb = new TaskBook();
        for (ReadOnlyTask rot : getTypicalTasks()) {
            try {
                tb.addTask(rot);
            } catch (DuplicateTaskException e) {
                assert false : "There are duplicate tasks in this Taskbook";
            }
        }
        return tb;
    }

    /**
     * return the typical tasks inside this application - examples for users
     * @return the typical tasks as an arrayList
     */
    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(PICNIC, MEETING, EXAM));
    }


}
