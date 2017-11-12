package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.TaskContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditReminderDescriptorBuilder;

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
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_BIRTHDAY_AMY = "01/01/1991";
    public static final String VALID_BIRTHDAY_BOB = "02/02/1992";
    public static final String VALID_USERNAME_PRIVATE = "private";
    public static final String VALID_PASSWORD_PASSWORD = "password";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String USERNAME_DESC_USERNAME = " " + PREFIX_USERNAME + VALID_USERNAME_PRIVATE;
    public static final String PASSWORD_DESC_PASSWORD = " " + PREFIX_PASSWORD + VALID_PASSWORD_PASSWORD;

    public static final String INVALID_USERNAME_DESC = " " + PREFIX_USERNAME + "prate12";
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD + "pasd123";
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_TASK_PROJECT = "Project Submission";
    public static final String VALID_TASK_ASSIGNMENT = "Task assignment";
    public static final String VALID_PRIORITY_PROJECT = "High";
    public static final String VALID_PRIORITY_ASSIGNMENT = "Medium";
    public static final String VALID_DATE_PROJECT = "09/09/2017 09:00";
    public static final String VALID_DATE_ASSIGNMENT = "10/10/2017 10:00";
    public static final String VALID_MESSAGE_PROJECT = "Submit to Manager";
    public static final String VALID_MESSAGE_ASSIGNMENT = "Submit by soft copy";
    public static final String VALID_TAG_OFFICE = "office";
    public static final String VALID_TAG_SOFTCOPY = "softcopy";

    public static final String TASK_DESC_PROJECT = " " + PREFIX_TASK + VALID_TASK_PROJECT;
    public static final String TASK_DESC_ASSIGNMENT = " " + PREFIX_TASK + VALID_TASK_ASSIGNMENT;
    public static final String PRIORITY_DESC_PROJECT = " " + PREFIX_PRIORITY + VALID_PRIORITY_PROJECT;
    public static final String PRIORITY_DESC_ASSIGNMENT = " " + PREFIX_PRIORITY + VALID_PRIORITY_ASSIGNMENT;
    public static final String DATE_DESC_PROJECT = " " + PREFIX_DATE + VALID_DATE_PROJECT;
    public static final String DATE_DESC_ASSIGNMENT = " " + PREFIX_DATE + VALID_DATE_ASSIGNMENT;
    public static final String MESSAGE_DESC_PROJECT = " " + PREFIX_MESSAGE + VALID_MESSAGE_PROJECT;
    public static final String MESSAGE_DESC_ASSIGNMENT = " " + PREFIX_MESSAGE + VALID_MESSAGE_ASSIGNMENT;

    public static final String TAG_DESC_OFFICE = " " + PREFIX_TAG + VALID_TAG_OFFICE;
    public static final String TAG_DESC_SOFTCOPY = " " + PREFIX_TAG + VALID_TAG_SOFTCOPY;
    public static final String INVALID_TASK_DESC = " " + PREFIX_TASK + "Submission&"; // '&' not allowed in tasks
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "low1"; // '1' not allowed in priorities
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "0!"; // '!' not allowed in dates

    public static final EditReminderCommand.EditReminderDescriptor DESC_PROJECT;
    public static final EditReminderCommand.EditReminderDescriptor DESC_ASSIGNMENT;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    static {
        DESC_PROJECT = new EditReminderDescriptorBuilder().withTask(VALID_TASK_PROJECT)
                .withPriority(VALID_PRIORITY_PROJECT).withDate(VALID_DATE_PROJECT).withMessage(VALID_MESSAGE_PROJECT)
                .withTags(VALID_TAG_OFFICE).build();
        DESC_ASSIGNMENT = new EditReminderDescriptorBuilder().withTask(VALID_TASK_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).withDate(VALID_DATE_ASSIGNMENT)
                .withMessage(VALID_MESSAGE_ASSIGNMENT).withTags(VALID_TAG_OFFICE, VALID_TAG_SOFTCOPY).build();
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

    //@@author inGall
    /**
     * @param command
     * @param actualModel
     * @param expectedMessage
     * @param expectedModel
     */
    public static void assertSortSuccess(Command command, Model actualModel, String expectedMessage,
                                         Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertNotEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
    //@@author

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first reminder in the {@code model}'s address book.
     */
    public static void showFirstReminderOnly(Model model) {
        ReadOnlyReminder reminder = model.getAddressBook().getReminderList().get(0);
        final String[] splitTask = reminder.getTask().taskName.split("\\s+");
        model.updateFilteredReminderList(new TaskContainsKeywordsPredicate(Arrays.asList(splitTask[0])));

        assert model.getFilteredReminderList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first and second persons
     * in the {@code model}'s address book.
     */
    public static void showFirstAndSecondPersonsOnly(Model model) {
        ReadOnlyPerson firstPerson = model.getAddressBook().getPersonList().get(0);
        final String[] splitFirstName = firstPerson.getName().fullName.split("\\s+");
        ReadOnlyPerson secondPerson = model.getAddressBook().getPersonList().get(1);
        final String[] splitSecondName = secondPerson.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(
                Arrays.asList(splitFirstName[0], splitSecondName[0])));

        assert model.getFilteredPersonList().size() == 2;
    }

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
