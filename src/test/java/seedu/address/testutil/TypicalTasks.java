package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask ASSIGNMENT = new TaskBuilder().withDescription("Finish CS2103T code enhancement")
            .withStartDate("Mon, Oct 23, '17")
            .withDeadline("Thu, Oct 26, '17").build();
    public static final ReadOnlyTask QUIZ = new TaskBuilder().withDescription("Online quiz open")
            .withStartDate("Wed, Oct 25, '17")
            .withDeadline("Wed, Nov 1, '17").build();
    public static final ReadOnlyTask GYM = new TaskBuilder().withDescription("Start going to the gym")
            .withStartDate("Fri, Nov 3, '17").withDeadline("").withTags(VALID_TAG_URGENT).build();
    public static final ReadOnlyTask BUY_TICKETS = new TaskBuilder()
            .withDescription("Get tickets two weeks before flight")
            .withDeadline("Fri, Nov 20, '17").build();
    public static final ReadOnlyTask SUBMISSION = new TaskBuilder().withDescription("Self evaluation submission due")
            .withStartDate("").withDeadline("Sat, Nov 18, '17").build();
    public static final ReadOnlyTask PERSONAL_PROJECT = new TaskBuilder().withDescription("Finish art piece").build();
    public static final ReadOnlyTask GROCERY = new TaskBuilder().withDescription("Go grocery shopping")
            .withDeadline("Thu, Mar 8, '18").build();
    public static final ReadOnlyTask MEETUP = new TaskBuilder().withDescription("Meet friends")
            .withDeadline("Tue, Dec 12, '17").build();

    // Manually added
    public static final ReadOnlyTask SCHOOL = new TaskBuilder().withDescription("Semester two starts")
            .withStartDate("Fri, Jan 12, '18").build();
    public static final ReadOnlyTask BUY_PRESENTS = new TaskBuilder().withDescription("Get gifts for family")
            .withStartDate("").withDeadline("").withTags(VALID_TAG_URGENT).build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask INTERNSHIP = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
            .withStartDate(VALID_STARTDATE_INTERNSHIP)
            .withDeadline(VALID_DEADLINE_INTERNSHIP)
            .withTags(VALID_TAG_URGENT)
            .build();
    public static final ReadOnlyTask GRAD_SCHOOL = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
            .withStartDate(VALID_STARTDATE_GRAD_SCHOOL)
            .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withTags(VALID_TAG_URGENT)
            .build();

    private TypicalTasks() {}

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT, QUIZ, GYM, BUY_TICKETS, SUBMISSION, PERSONAL_PROJECT));
    }
}
