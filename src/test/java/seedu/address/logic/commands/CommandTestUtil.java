package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_AT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.persons.EditCommand;
import seedu.address.logic.commands.tasks.EditTaskCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.DateTimeFormatter;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_BIRTHDAY_AMY = "11-12-1995";
    public static final String VALID_BIRTHDAY_BOB = "12-12-1995";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_COLLEAGUE = "colleague";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_COLLEAGUE = " " + PREFIX_TAG + VALID_TAG_COLLEAGUE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    //empty String not allowed for birthday
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "11121995";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_DESCRIPTION_INTERNSHIP = " " + "First day of internship" + " ";
    public static final String VALID_DESCRIPTION_GRAD_SCHOOL = " " + "Grad school orientation" + " ";
    public static final String UNQUOTED_DESCRIPTION_PAPER = "Finish paper on team behaviour";
    public static final String VALID_DESCRIPTION_GYM = " " + "Start going to the gym" + " ";
    public static final String VALID_DEADLINE_INTERNSHIP = "Wed, Oct 25, '17";
    public static final String VALID_DEADLINE_GRAD_SCHOOL = "Thu, Oct 26, '17";
    public static final String VALID_DEADLINE_PAPER = "Fri, Oct 27, '17";
    public static final String VALID_DEADLINE_TODAY = DateTimeFormatter.formatDate(new Date());
    public static final String VALID_STARTTIME_INTERNSHIP = "09:00";
    public static final String VALID_STARTTIME_GRAD_SCHOOL = "10:00";
    public static final String VALID_STARTTIME_GYM = "13:01";
    public static final String VALID_ENDTIME_GYM = "14:09";
    public static final String VALID_ENDTIME_INTERNSHIP = "17:00";
    public static final String VALID_ENDTIME_GRAD_SCHOOL = "12:00";
    public static final String VALID_ENDTIME_PAPER = "23:59";
    public static final String VALID_TAG_URGENT = "urgent";
    public static final String VALID_TAG_NOT_URGENT = "notUrgent";
    public static final String VALID_TAG_GROUP = "projectGroup";

    public static final String INVALID_DESCRIPTION = " " + "///??::!!";
    public static final String INVALID_STARTTIME = "29 pm";
    public static final String INVALID_ENDTIME = "30 pm";
    public static final String INVALID_DASHED_DATE = "13-02-2015";

    public static final String DESCRIPTION_QUOTED_PAPER = " " + "\"" + UNQUOTED_DESCRIPTION_PAPER + "\"";
    public static final String DEADLINE_DESC_INTERNSHIP = " " + PREFIX_DEADLINE_ON + " " + VALID_DEADLINE_INTERNSHIP;
    public static final String DEADLINE_DESC_GRAD_SCHOOL = " " + PREFIX_DEADLINE_BY + " " + VALID_DEADLINE_GRAD_SCHOOL;
    public static final String DEADLINE_DESC_PAPER = " " + PREFIX_DEADLINE_ON + " " + VALID_DEADLINE_PAPER;
    public static final String STARTTIME_DESC_INTERNSHIP = " " + PREFIX_TIME_AT + " " + VALID_STARTTIME_INTERNSHIP;
    public static final String ENDTTIME_DESC_INTERNSHIP = " " + PREFIX_TIME_AT + " " + VALID_ENDTIME_INTERNSHIP;
    public static final String TIME_DESC_INTERNSHIP = " " + PREFIX_TIME_AT + " " + VALID_STARTTIME_INTERNSHIP + " "
            + PREFIX_ENDTIME_TO + " " + VALID_ENDTIME_INTERNSHIP;
    public static final String TIME_DESC_GRAD_SCHOOL = " " + PREFIX_TIME_AT + " " + VALID_STARTTIME_GRAD_SCHOOL + " "
            + PREFIX_ENDTIME_TO + " " + VALID_ENDTIME_GRAD_SCHOOL;
    public static final String MIXED_TIME_DESC_INTERNSHIP = " " + PREFIX_TIME_AT + " " + VALID_STARTTIME_INTERNSHIP +
            " to " + VALID_ENDTIME_GRAD_SCHOOL;
    public static final String MIXED_TIME_DESC_GRAD_SCHOOL = " " + PREFIX_TIME_AT + " " + VALID_STARTTIME_GRAD_SCHOOL +
            " to " + VALID_ENDTIME_INTERNSHIP;
    public static final String ENDTIME_DESC_PAPER = " " + PREFIX_TIME_AT + " "
            + VALID_ENDTIME_PAPER;
    public static final String TIME_DESC_GYM = " " + PREFIX_TIME_AT + " "
            + VALID_STARTTIME_GYM + " "
            + PREFIX_ENDTIME_TO + " " + VALID_ENDTIME_GYM;
    public static final String TAG_DESC_URGENT = " " + PREFIX_TAG + VALID_TAG_URGENT;
    public static final String TAG_DESC_GROUP = " " + PREFIX_TAG + VALID_TAG_GROUP;
    public static final String TAG_DESC_NOT_URGENT = " " + PREFIX_TAG + VALID_TAG_NOT_URGENT;
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE_ON + " " + INVALID_DASHED_DATE;
    public static final String INVALID_STARTTIME_DESC = " " + PREFIX_TIME_AT + " " + INVALID_STARTTIME;
    public static final String INVALID_STARTTIME_VALID_ENDTIME_DESC = " " + PREFIX_TIME_AT + " " + INVALID_STARTTIME
            + " " + PREFIX_ENDTIME_TO + " " + VALID_ENDTIME_INTERNSHIP;
    public static final String INVALID_TIME_DESC_CORRECT_ORDER = " " + PREFIX_TIME_AT + " " + INVALID_STARTTIME
            + " until " + INVALID_ENDTIME;
    public static final String INVALID_TIME_DESC_INCORRECT_ORDER = " " + PREFIX_TIME_AT + " " + VALID_ENDTIME_INTERNSHIP
            + " " + PREFIX_ENDTIME_TO + " " + VALID_STARTTIME_INTERNSHIP;


    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditTaskCommand.EditTaskDescriptor DESC_INTERNSHIP;
    public static final EditTaskCommand.EditTaskDescriptor DESC_PAPER;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_INTERNSHIP = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withEventTimes(VALID_STARTTIME_INTERNSHIP + " " + VALID_ENDTIME_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();
        DESC_PAPER = new EditTaskDescriptorBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).withEventTimes(VALID_ENDTIME_PAPER)
                .withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }
    //@@author eryao95
    /**
     * Updates {@code model}'s filtered list to show only the first task in the {@code model}'s address book.
     */
    public static void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getAddressBook().getTaskList().get(0);
        final String[] splitDescription = task.getDescription().taskDescription.split("\\s+");
        model.updateFilteredTaskList(new TaskContainsKeywordsPredicate(Arrays.asList(splitDescription[3])));

        assert model.getFilteredTaskList().size() == 1;
    }
    //@@author
    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
}
