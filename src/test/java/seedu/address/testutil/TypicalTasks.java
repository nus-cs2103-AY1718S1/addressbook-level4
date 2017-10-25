package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INTERNSHIP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask ASSIGNMENT = new TaskBuilder().withDescription("Finish CS2103T code enhancement")
            .withStartDate("17-10-2017")
            .withDeadline("20-10-2017").build();
    public static final ReadOnlyTask QUIZ = new TaskBuilder().withDescription("Online quiz open")
            .withStartDate("21-10-2017")
            .withDeadline("28-10-2017").build();
    public static final ReadOnlyTask GYM = new TaskBuilder().withDescription("Start going to the gym")
            .withStartDate("20-05-2017")
            .withDeadline("23-10-2017").build();
    public static final ReadOnlyTask BUY_TICKETS = new TaskBuilder()
            .withDescription("Get tickets two weeks before flight")
            .withStartDate("11-11-2017")
            .withDeadline("01-11-2017").build();
    public static final ReadOnlyTask SUBMISSION = new TaskBuilder().withDescription("Self evaluation submission due")
            .withStartDate("08-08-2017")
            .withDeadline("30-11-2017").build();
    public static final ReadOnlyTask PERSONAL_PROJECT = new TaskBuilder().withDescription("Finish art piece")
            .withStartDate("01-01-2017")
            .withDeadline("12-12-2017").build();

    // Manually added
    public static final ReadOnlyTask SCHOOL = new TaskBuilder().withDescription("Semester two starts")
            .withStartDate("12-01-2018").build();
    public static final ReadOnlyTask BUY_PRESENTS = new TaskBuilder().withDescription("Get gifts for family").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask INTERNSHIP = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
            .withStartDate(VALID_STARTDATE_INTERNSHIP)
            .withDeadline(VALID_DEADLINE_INTERNSHIP)
            .build();
    public static final ReadOnlyTask GRAD_SCHOOL = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
            .withStartDate(VALID_STARTDATE_GRAD_SCHOOL)
            .withDeadline(VALID_DEADLINE_GRAD_SCHOOL)
            .build();

    public static final String KEYWORD_MATCHING_FINISH = "Finish"; // A keyword that matches Finish

    private TypicalTasks() {}

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT, QUIZ, GYM, BUY_TICKETS, SUBMISSION, PERSONAL_PROJECT));
    }
}
