package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.TaskBook;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;
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
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Food";

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

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditTaskCommand.EditTaskDescriptor DESC_DEMO;
    public static final EditTaskCommand.EditTaskDescriptor DESC_HOTPOT;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    public static final String VALID_NAME_HOTPOT = "Hotpot dinner";
    public static final String VALID_NAME_DEMO = "Project demo";
    public static final String VALID_DESCRIPTION_HOTPOT = "have a hotpot dinner with few friends in Chinatown";
    public static final String VALID_DESCRIPTION_DEMO = "demo our 2103 project to Stark Industry";
    public static final String VALID_START_HOTPOT = "06-12-2017 10:00am";
    public static final String VALID_START_DEMO = "11-11-2017 09:00am";
    public static final String VALID_END_HOTPOT = "06-12-2017 12:00am";
    public static final String VALID_END_DEMO = "11-11-2017 11:00am";
    public static final String VALID_TAG_HOTPOT = " social";
    public static final String VALID_TAG_DEMO = " important";

    public static final String NAME_DESC_HOTPOT = " " + PREFIX_NAME + VALID_NAME_HOTPOT;
    public static final String NAME_DESC_DEMO = " " + PREFIX_NAME + VALID_NAME_DEMO;
    public static final String DESC_DESC_HOTPOT = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_HOTPOT;
    public static final String DESC_DESC_DEMO = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_DEMO;
    public static final String START_DESC_HOTPOT = " " + PREFIX_START_DATE_TIME + VALID_START_HOTPOT;
    public static final String START_DESC_DEMO = " " + PREFIX_START_DATE_TIME + VALID_START_DEMO;
    public static final String END_DESC_HOTPOT = " " + PREFIX_END_DATE_TIME + VALID_END_HOTPOT;
    public static final String END_DESC_DEMO = " " + PREFIX_END_DATE_TIME + VALID_END_DEMO;
    public static final String TAG_DESC_HOTPOT = " " + PREFIX_TAG + VALID_TAG_HOTPOT;
    public static final String TAG_DESC_DEMO = " " + PREFIX_TAG + VALID_TAG_DEMO;

    //@@author yuzu1209
    //public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Hotpot&"; // '&' not allowed in names
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + " "; // ' ' blank space not allowed
    public static final String INVALID_START_DESC = " " + PREFIX_START_DATE_TIME + "19981209"; // not the correct style
    public static final String INVALID_END_DESC = " " + PREFIX_END_DATE_TIME; // empty string not allowed
    //public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags


    static {
        DESC_DEMO = new EditTaskDescriptorBuilder().withName(VALID_NAME_DEMO)
                .withDescription(VALID_DESCRIPTION_DEMO).withStart(VALID_START_DEMO).withEnd(VALID_END_DEMO)
                .withTags(VALID_TAG_DEMO).build();
        DESC_HOTPOT = new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT)
                .withDescription(VALID_DESCRIPTION_HOTPOT).withStart(VALID_START_HOTPOT).withEnd(VALID_END_HOTPOT)
                .withTags(VALID_TAG_HOTPOT).build();
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

    //@@author yuzu1209
    /**
     * Executes the given {@code taskCommand}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the TaskCommandException message matches {@code expectedMessage} <br>
     * - the task book and the filtered task list in the {@code actualModel} remain unchanged
     */
    public static void assertTaskCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaskBook expectedTaskBook = new TaskBook(actualModel.getTaskBook());
        List<ReadOnlyTask> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTaskBook, actualModel.getTaskBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
        }
    }

    //@@author 1moresec
    /**
     * Updates {@code model}'s filtered person list to show only the first person in the {@code model}'s 3W.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new ContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered task list to show only the first task in the {@code model}'s 3W.
     */
    public static void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getTaskBook().getTaskList().get(0);
        final String[] name = task.getName().toString().split("\\s+");
        model.updateFilteredTaskList(new TaskNameContainsKeywordsPredicate(Arrays.asList(name[0])));

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
