package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstLessonOnly;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.MarkedListPredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexMarkedList_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        model.bookmarkLesson(lessonToDelete);
        model.updateFilteredLessonList(new MarkedListPredicate());

        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredLessonList(new MarkedListPredicate());
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexModuleList_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexMarkedList_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.updateFilteredLessonList(new MarkedListPredicate());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexLocationList_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        showFirstLessonOnly(model);

        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(lessonToDelete);
        showNoLesson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        showFirstLessonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_LESSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_LESSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_LESSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_LESSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different delete command -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void execute_validIndexDeleteByModule_success() throws Exception {

        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS,
                lessonToDelete.getCode());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        List<ReadOnlyLesson> lastShownList = expectedModel.getFilteredLessonList();
        for (int i = 0; i < lastShownList.size(); i++) {
            ReadOnlyLesson lesson = lastShownList.get(i);
            if (lesson.getCode().equals(lessonToDelete.getCode())) {
                expectedModel.deleteLesson(lesson);
                i--;
            }
        }

        expectedModel.updateFilteredLessonList(new UniqueModuleCodePredicate(expectedModel.getUniqueCodeSet()));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexDeleteByLocation_success() throws Exception {

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS,
                lessonToDelete.getLocation());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);

        List<ReadOnlyLesson> lastShownList = expectedModel.getFilteredLessonList();

        for (int i = 0; i < lastShownList.size(); i++) {
            ReadOnlyLesson lesson = lastShownList.get(i);
            if (lesson.getLocation().equals(lessonToDelete.getLocation())) {
                expectedModel.deleteLesson(lesson);
                i--;
            }
        }

        expectedModel.updateFilteredLessonList(new UniqueLocationPredicate(expectedModel.getUniqueLocationSet()));
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoLesson(Model model) {
        model.updateFilteredLessonList(p -> false);

        assert model.getFilteredLessonList().isEmpty();
    }
}
