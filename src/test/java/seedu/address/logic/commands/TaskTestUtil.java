package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.task.DescContainsKeywordsPredicate;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class TaskTestUtil {

    public static final String VALID_DESC_AMY = "Task Amy";
    public static final String VALID_DESC_BOB = "Task Bob";
    public static final String VALID_PRIORITY_AMY = "1";
    public static final String VALID_PRIORITY_BOB = "0";
    public static final String VALID_PRIORITY_AMY_STRING = "1";
    public static final String VALID_PRIORITY_BOB_STRING = "0";
    public static final String VALID_DUEDATE_AMY = "01/01/2001";
    public static final String VALID_DUEDATE_BOB = "12/12/2002";

    public static final String DESC_AMY = " " + PREFIX_DESCIPTION + VALID_DESC_AMY;
    public static final String DESC_BOB = " " + PREFIX_DESCIPTION + VALID_DESC_BOB;
    public static final String PRIORITY_AMY = " " + PREFIX_PRIORITY + VALID_PRIORITY_AMY;
    public static final String PRIORITY_BOB = " " + PREFIX_PRIORITY + VALID_PRIORITY_BOB;
    public static final String DUEDATE_AMY = " " + PREFIX_DUEDATE + VALID_DUEDATE_AMY;
    public static final String DUEDATE_BOB = " " + PREFIX_DUEDATE + VALID_DUEDATE_BOB;

    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "-1"; // 'a' not allowed in phones
    public static final String INVALID_DUEDATE_DESC = " " + PREFIX_DUEDATE + "01/13/2002"; // missing '@' symbol

    public static final EditTaskCommand.EditTaskDescriptor TASK_AMY;
    public static final EditTaskCommand.EditTaskDescriptor TASK_BOB;

    static {
        TASK_AMY = new EditTaskDescriptorBuilder().withDescription(VALID_DESC_AMY)
                .withPriority(VALID_PRIORITY_AMY_STRING).withDueDate(VALID_DUEDATE_AMY).build();
        TASK_BOB = new EditTaskDescriptorBuilder().withDescription(VALID_DESC_BOB)
                .withPriority(VALID_PRIORITY_BOB_STRING).withDueDate(VALID_DUEDATE_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered task list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyTask> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first task in the {@code model}'s address book.
     */
    public static void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getAddressBook().getTaskList().get(0);
        final String[] splitName = task.getDescription().descriptionName.split("\\s+");
        model.updateFilteredTaskList(new DescContainsKeywordsPredicate(Arrays.asList(splitName[0])));

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
}
