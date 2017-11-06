package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskBook;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask PICNIC = new Task("picnic",
            "Have a good time with my best friends",
            "20/05/2018-12:00pm",
            "20/05/2018-13:00pm", 2).withTags("Friends", "Fun");

    public static final ReadOnlyTask MEETING = new Task("meeting",
            "Have a CS2101 group meeting for oral presentation 2",
            "20/05/2017-12:00pm",
            "20/05/2017-13:00pm", 3).withTags("Study");

    public static final ReadOnlyTask EXAM = new Task("CS2103 exam",
            "Have a final exam for CS2103T",
            "20/05/2017-15:00pm",
            "20/05/2017-16:00pm", 1).withTags("Study");


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
