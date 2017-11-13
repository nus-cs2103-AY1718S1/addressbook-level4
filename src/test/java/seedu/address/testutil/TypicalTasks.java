package seedu.address.testutil;
//@@author Esilocke
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PENCIL;

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
    public static final ReadOnlyTask ACCEPT = new TaskBuilder().withTaskName("Acceptance Testing")
            .withDescription("Perform acceptance testing on application")
            .withDeadline("04-04-2017").withPriority("3").withAssignees("4")
            .withTaskAddress("21 Heng Mui Keng Terrace, #02-01-01 I-Cube Building").build();
    public static final ReadOnlyTask BUY = new TaskBuilder().withTaskName("Buy pencil")
            .withDescription("Buy pencils for tomorrow's test").withState(true)
            .withDeadline("04-04-2017").withPriority("5").withTaskAddress("Tampines Mall").build();
    public static final ReadOnlyTask COOK = new TaskBuilder().withTaskName("Cook Paella")
            .withDescription("Cook Paella for 4 people tonight")
            .withDeadline("11-04-2016").withPriority("5").withState(true).withTaskAddress("27 Prince George's Park")
            .build();
    public static final ReadOnlyTask DATE = new TaskBuilder().withTaskName("Date with Lucy")
            .withDescription("Sunday, 10am at Central Park")
            .withDeadline("21-05-2015").withPriority("5").withTaskAddress("Central Park").build();
    public static final ReadOnlyTask ESCAPE = new TaskBuilder().withTaskName("Escape dungeon")
            .withDescription("Escape dungeon group formation")
            .withDeadline("04-04-2017").withPriority("1").withTaskAddress("16 Gemmill Ln").build();
    public static final ReadOnlyTask FREE = new TaskBuilder().withTaskName("Free memory space")
            .withDescription("Implement new version of free()")
            .withDeadline("21-08-2019").withPriority("2").withState(true).withAssignees("1", "2", "3")
            .withTaskAddress("NUS School of Computing, COM1, 13 Computing Drive, 117417").build();
    public static final ReadOnlyTask GRADLE = new TaskBuilder().withTaskName("Resolve gradle")
            .withDescription("Resolve gradle problems when building project")
            .withDeadline("06-06-2016").withPriority("5")
            .withTaskAddress("Changi Airport").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask PENCIL = new TaskBuilder().withTaskName(VALID_TASK_NAME_PENCIL)
            .withDescription(VALID_DESCRIPTION_PENCIL)
            .withDeadline(VALID_DEADLINE_PENCIL).withPriority(VALID_PRIORITY_PENCIL)
            .withTaskAddress(VALID_TASK_ADDRESS_PENCIL).build();
    public static final ReadOnlyTask PAPER = new TaskBuilder().withTaskName(VALID_TASK_NAME_PAPER)
            .withDescription(VALID_DESCRIPTION_PAPER)
            .withDeadline(VALID_DEADLINE_PAPER).withPriority(VALID_PRIORITY_PAPER)
            .withTaskAddress(VALID_TASK_ADDRESS_PAPER).build();

    public static final String KEYWORD_MATCHING_LUCY = "Lucy"; // A keyword that matches LUCY

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalTasksOnlyAddressBook() {
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
        return new ArrayList<>(Arrays.asList(ACCEPT, BUY, COOK, DATE, ESCAPE, FREE, GRADLE));
    }
}
