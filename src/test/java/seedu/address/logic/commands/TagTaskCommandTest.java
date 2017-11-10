package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.tasks.TagTaskCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TaskBuilder;

public class TagTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index[] indices;
    private Set<Tag> tags;

    @Test
    public void execute_unfilteredList_success() throws Exception {
        indices = new Index[]{INDEX_FIRST_TASK, INDEX_SECOND_TASK};
        tags = SampleDataUtil.getTagSet(VALID_TAG_URGENT);
        TagTaskCommand tagTaskCommand = prepareCommand(indices, tags);

        //testing for change in person tags in first index
        ReadOnlyTask taskInUnfilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInUnfilteredList).build();
        model.updateTaskTags(editedTask, tags);

        String expectedMessage = String.format(TagTaskCommand.MESSAGE_TAG_TASKS_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(tagTaskCommand, model, expectedMessage, expectedModel);

        //testing for change in person tags in second index
        ReadOnlyTask taskInUnfilteredListTwo = model.getFilteredTaskList()
                .get(INDEX_SECOND_TASK.getZeroBased());
        Task editedTaskTwo = new TaskBuilder(taskInUnfilteredListTwo).build();
        model.updateTaskTags(editedTaskTwo, tags);

        Model expectedModelTwo = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelTwo.updateTask(model.getFilteredTaskList().get(1), editedTaskTwo);

        assertCommandSuccess(tagTaskCommand, model, expectedMessage, expectedModelTwo);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstTaskOnly(model);

        indices = new Index[]{INDEX_FIRST_TASK};
        tags = SampleDataUtil.getTagSet(VALID_TAG_NOT_URGENT);
        TagTaskCommand tagTaskCommand = prepareCommand(indices, tags);

        ReadOnlyTask taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).build();
        model.updateTaskTags(editedTask, tags);

        String expectedMessage = String.format(TagTaskCommand.MESSAGE_TAG_TASKS_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(tagTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        indices = new Index[]{outOfBoundIndex};
        tags = SampleDataUtil.getTagSet(VALID_TAG_URGENT);
        TagTaskCommand tagTaskCommand = prepareCommand(indices, tags);

        assertCommandFailure(tagTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of task manager
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() throws Exception {
        showFirstTaskOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        indices = new Index[]{outOfBoundIndex};
        tags = SampleDataUtil.getTagSet(VALID_TAG_URGENT);
        TagTaskCommand tagTaskCommand = prepareCommand(indices, tags);

        assertCommandFailure(tagTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        indices = new Index[]{INDEX_FIRST_TASK, INDEX_SECOND_TASK};
        tags = SampleDataUtil.getTagSet(VALID_TAG_URGENT);
        final TagTaskCommand standardCommand = new TagTaskCommand(indices, tags);

        //same values -> returns true
        Index[] copyIndices = new Index[]{INDEX_FIRST_TASK, INDEX_SECOND_TASK};
        Set<Tag> copyTags = SampleDataUtil.getTagSet(VALID_TAG_URGENT);
        TagTaskCommand commandWithSameValue = new TagTaskCommand(copyIndices, copyTags);
        assertTrue(standardCommand.equals(commandWithSameValue));

        //same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        //differed types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        //different index -> returns false
        Index[] differentIndex = new Index[]{INDEX_FIRST_TASK, INDEX_THIRD_TASK};
        TagTaskCommand commandWithDifferentIndex = new TagTaskCommand(differentIndex, tags);
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        //different tag -> returns false
        Set<Tag> differentTag = SampleDataUtil.getTagSet(VALID_TAG_NOT_URGENT);
        TagTaskCommand commandWithDifferentTag = new TagTaskCommand(indices, differentTag);
        assertFalse(standardCommand.equals(commandWithDifferentTag));
    }

    /**
     * Returns an {@code TagCommand} with parameters {@code indices} and {@code tagList}
     */
    private TagTaskCommand prepareCommand(Index[] indices, Set<Tag> tagList) {
        TagTaskCommand tagTaskCommand = new TagTaskCommand(indices, tagList);
        tagTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagTaskCommand;
    }
}
