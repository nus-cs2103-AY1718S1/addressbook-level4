// @@author tanchc
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT_EDIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MOVIE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask MEETING = new TaskBuilder().withAppointment("Meeting")
            .withDate("30/12/2017").withStartTime("18:00").build();

    public static final ReadOnlyTask SOCCER = new TaskBuilder().withAppointment("Soccer")
            .withDate("15/12/2017").withStartTime("09:00").build();

    public static final ReadOnlyTask BIRTHDAY = new TaskBuilder().withAppointment("Birthday")
            .withDate("30/11/2017").withStartTime("19:30").build();

    public static final ReadOnlyTask WORK = new TaskBuilder().withAppointment("Work")
            .withDate("01/01/2018").withStartTime("08:00").build();

    public static final ReadOnlyTask HOMEWORK = new TaskBuilder().withAppointment("Homework")
            .withDate("12/01/2018").withStartTime("23:59").build();

    public static final ReadOnlyTask EXAM = new TaskBuilder().withAppointment("Exam")
            .withDate("05/12/2017").withStartTime("17:00").build();

    public static final ReadOnlyTask COMPETITION = new TaskBuilder().withAppointment("Competition")
            .withDate("25/11/2017").withStartTime("12:00").build();

    // Manually added
    public static final ReadOnlyTask TUITION = new TaskBuilder().withAppointment("Tuition")
            .withDate("01/12/2017").withStartTime("15:00").build();

    public static final ReadOnlyTask CHECKUP = new TaskBuilder().withAppointment("Checkup")
            .withDate("12/02/2018").withStartTime("14:00").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask MOVIE = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MOVIE)
            .withDate(VALID_DATE_MOVIE).withStartTime(VALID_START_TIME_MOVIE).build();
    public static final ReadOnlyTask EVENT = new TaskBuilder().withAppointment(VALID_APPOINTMENT_EVENT)
            .withDate(VALID_DATE_EVENT).withStartTime(VALID_START_TIME_EVENT).build();
    public static final ReadOnlyTask EVENT_EDITED = new TaskBuilder().withAppointment(VALID_APPOINTMENT_EVENT)
            .withDate(VALID_DATE_EVENT).withStartTime(VALID_START_TIME_EVENT_EDIT).build();
    public static final ReadOnlyTask BADMINTON = new TaskBuilder().withAppointment(VALID_APPOINTMENT_BADMINTON)
            .withDate(VALID_DATE_BADMINTON).withStartTime(VALID_START_TIME_BADMINTON).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final String KEYWORD_MATCHING_OWESMONEY = "owesMoney"; // A keyword that matches friends

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBookTasks() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyTask task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(MEETING, SOCCER, BIRTHDAY, WORK, HOMEWORK, EXAM, COMPETITION));
    }
}
