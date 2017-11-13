package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskContainsKeywordPredicate;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CHRIS = "Chris Chan";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_CHRIS = "33333333";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_CHRIS = "chris@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_CHRIS = "Block 132, Chris Street 5";
    public static final String VALID_REMARK_AMY = "She sells sea shells on the sea shore";
    public static final String VALID_REMARK_BOB = "He eats, shoots and leaves";
    public static final String VALID_REMARK_CHRIS = "He likes swimming";
    public static final String VALID_AVATAR_AMY = "amyavatar";
    public static final String VALID_AVATAR_BOB = "bobavatar";
    public static final String VALID_AVATAR_CHRIS = "chirsavatar";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_FAVOURITE_ALICE = "false";
    public static final String VALID_FAVOURITE_BOB = "true";
    public static final String VALID_TAG_COLLEAGUE = "colleague";
    public static final String VALID_TAG_CHRISTAG = "special";

    public static final String VALID_TASK_NAME_PENCIL = "Buy pencil";
    public static final String VALID_TASK_NAME_PAPER = "Buy paper";
    public static final String VALID_DESCRIPTION_PENCIL = "Buy mechanical pencil from ABS";
    public static final String VALID_DESCRIPTION_PAPER = "Buy 500 pieces of paper";
    public static final String VALID_DEADLINE_PENCIL = "04-04-2017 12pm";
    public static final String VALID_DEADLINE_PAPER = "05-04-2017 12pm";
    public static final String VALID_PRIORITY_PENCIL = "1";
    public static final String VALID_PRIORITY_PAPER = "3";
    public static final String VALID_TASK_ADDRESS_PENCIL = "12 Kent Ridge Cres";
    public static final String VALID_TASK_ADDRESS_PAPER = "Bedok Mall";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_CHRIS = " " + PREFIX_NAME + VALID_NAME_CHRIS;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_CHRIS = " " + PREFIX_PHONE + VALID_PHONE_CHRIS;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMAIL_DESC_CHRIS = " " + PREFIX_EMAIL + VALID_EMAIL_CHRIS;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_CHRIS = " " + PREFIX_ADDRESS + VALID_ADDRESS_CHRIS;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String REMARK_DESC_CHRIS = " " + PREFIX_REMARK + VALID_REMARK_CHRIS;

    public static final String AVATAR_DESC_AMY = " " + PREFIX_AVATAR + VALID_AVATAR_AMY;
    public static final String AVATAR_DESC_BOB = " " + PREFIX_AVATAR + VALID_AVATAR_BOB;
    public static final String AVATAR_DESC_CHRIS = " " + PREFIX_AVATAR + VALID_REMARK_CHRIS;

    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String TASK_NAME_DESC_PENCIL = " " + PREFIX_NAME + VALID_TASK_NAME_PENCIL;
    public static final String TASK_NAME_DESC_PAPER = " " + PREFIX_NAME + VALID_TASK_NAME_PAPER;
    public static final String DESCRIPTION_DESC_PENCIL = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_PENCIL;
    public static final String DESCRIPTION_DESC_PAPER = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_PAPER;
    public static final String DEADLINE_DESC_PENCIL = " " + PREFIX_DEADLINE + VALID_DEADLINE_PENCIL;
    public static final String DEADLINE_DESC_PAPER = " " + PREFIX_DEADLINE + VALID_DEADLINE_PAPER;
    public static final String PRIORITY_DESC_PENCIL = " " + PREFIX_PRIORITY + VALID_PRIORITY_PENCIL;
    public static final String PRIORITY_DESC_PAPER = " " + PREFIX_PRIORITY + VALID_PRIORITY_PAPER;
    public static final String TASK_ADDRESS_DESC_PENCIL = " " + PREFIX_ADDRESS + VALID_TASK_ADDRESS_PENCIL;
    public static final String TASK_ADDRESS_DESC_PAPER = " " + PREFIX_ADDRESS + VALID_TASK_ADDRESS_PAPER;

    public static final String INVALID_TASK_NAME_DESC = " " + PREFIX_NAME + " "; // task name cannot be blank
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "TIMELESS AGES"; // not a date
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "6"; // priority is out of bounds
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS + " "; // priority is out of bounds

    public static final String TASK_SEPARATOR = " " + PREFIX_TASK + " ";

    public static final EditPersonCommand.EditPersonDescriptor DESC_AMY;
    public static final EditPersonCommand.EditPersonDescriptor DESC_BOB;
    public static final EditTaskCommand.EditTaskDescriptor DESC_PENCIL;
    public static final EditTaskCommand.EditTaskDescriptor DESC_PAPER;

    static {
        //TODO: Change avatar filename to a real filename
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withRemark(VALID_REMARK_AMY).withAvatar(VALID_AVATAR_AMY).withFavourite(VALID_FAVOURITE_ALICE)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withRemark(VALID_REMARK_BOB).withAvatar(VALID_AVATAR_BOB).withFavourite(VALID_FAVOURITE_ALICE)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_PENCIL = new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_PENCIL)
                .withDescription(VALID_DESCRIPTION_PENCIL)
                .withDeadline(VALID_DEADLINE_PENCIL).withPriority(VALID_PRIORITY_PENCIL).build();
        DESC_PAPER = new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_PAPER)
                .withDescription(VALID_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).withPriority(VALID_PRIORITY_PAPER).build();
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
        final String[] splitName = person.getName().value.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
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

    /**
     * Updates {@code model}'s filtered list to show only the first task in the {@code model}'s address book.
     */
    public static void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getAddressBook().getTasksList().get(0);
        final String[] splitName = task.getTaskName().taskName.split("\\s+");
        model.updateFilteredTaskList(new TaskContainsKeywordPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredTaskList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first task in the {@code model}'s address book.
     */
    public static void showSecondTaskOnly(Model model) {
        ReadOnlyTask task = model.getAddressBook().getTasksList().get(1);
        final String[] splitName = task.getTaskName().taskName.split("\\s+");
        model.updateFilteredTaskList(new TaskContainsKeywordPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredTaskList().size() == 1;
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstTask(Model model) {
        ReadOnlyTask firstTask = model.getFilteredTaskList().get(0);
        try {
            model.deleteTask(firstTask);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("Task in filtered list must exist in model.", pnfe);
        }
    }

    //@@author wangyiming1019
    /**
     * Favourites the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void favouriteFirstPerson(Model model) {
        ReadOnlyPerson firstPerson = model.getAddressBook().getPersonList().get(0);
        try {
            model.favouritePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
    //@@author
}
