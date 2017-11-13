package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TODAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_INTERNSHIP;
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
            .withDeadline("Thu, Oct 26, '17")
            .withStartTime("09:00")
            .withEndTime("23:00").build();
    public static final ReadOnlyTask QUIZ = new TaskBuilder().withDescription("Online quiz open")
            .withDeadline("Wed, Nov 1, '17")
            .withStartTime("10:20")
            .withEndTime("11:30").build();
    public static final ReadOnlyTask GYM = new TaskBuilder().withDescription("Start going to the gym")
            .withDeadline(VALID_DEADLINE_TODAY).withStartTime("13:01").withEndTime("14:09").withTags(VALID_TAG_URGENT)
            .build();
    public static final ReadOnlyTask BUY_TICKETS = new TaskBuilder()
            .withDescription("Get tickets two weeks before flight")
            .withStartTime("15:00").withEndTime("18:00").withDeadline("Fri, Nov 20, '17").build();
    public static final ReadOnlyTask SUBMISSION = new TaskBuilder().withDescription("Self evaluation submission due")
            .withStartTime("").withEndTime("13:09")
            .withDeadline("Sat, Nov 18, '17").build();
    public static final ReadOnlyTask PERSONAL_PROJECT = new TaskBuilder().withDescription("Finish art piece")
            .withStartTime("").withEndTime("")
            .withDeadline("").build();
    public static final ReadOnlyTask GROCERY = new TaskBuilder().withDescription("Go grocery shopping")
            .withStartTime("").withEndTime("")
            .withDeadline("Thu, Mar 8, '18").build();
    public static final ReadOnlyTask MEETUP = new TaskBuilder().withDescription("Meet friends")
            .withStartTime("16:00").withEndTime("")
            .withDeadline("Tue, Dec 12, '17").build();

    // Manually added
    public static final ReadOnlyTask SCHOOL = new TaskBuilder().withDescription("Semester two starts")
            .withStartTime("01:00").withEndTime("").build();
    public static final ReadOnlyTask BUY_PRESENTS = new TaskBuilder().withDescription("Get gifts for family")
            .withStartTime("").withEndTime("").withDeadline("")
            .withTags(VALID_TAG_URGENT).build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask INTERNSHIP = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
            .withDeadline(VALID_DEADLINE_INTERNSHIP).withStartTime(VALID_STARTTIME_INTERNSHIP)
            .withEndTime(VALID_ENDTIME_INTERNSHIP).withTags(VALID_TAG_URGENT).build();
    public static final ReadOnlyTask GRAD_SCHOOL = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
            .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withStartTime(VALID_STARTTIME_GRAD_SCHOOL)
            .withEndTime(VALID_ENDTIME_GRAD_SCHOOL).withTags(VALID_TAG_URGENT).build();

    public static final String KEYWORD_MATCHING_FINISH = "Finish"; // A keyword that matches Finish

    private TypicalTasks() {}

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT, QUIZ, GYM, BUY_TICKETS, SUBMISSION, PERSONAL_PROJECT));
    }
}
