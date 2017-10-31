package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.ListingUnit.LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.predicates.FixedCodePredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

public class UnmarkCommandTest {

    private Model model;

    @Test
    public void execute_validIndexUnfilteredList_success() throws DuplicateLessonException {
        model = prepareModel();
        ListingUnit.setCurrentListingUnit(LESSON);
        ReadOnlyLesson lessonToUnmark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        UnmarkCommand unmarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnmark);

        Model expectedModel = prepareModel();
        expectedModel.unBookmarkLesson(expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws DuplicateLessonException {
        model = prepareModel();
        model.updateFilteredLessonList(
                new FixedCodePredicate(model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        ListingUnit.setCurrentListingUnit(LESSON);
        ReadOnlyLesson lessonToUnmark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        UnmarkCommand unmarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnmark);

        Model expectedModel = prepareModel();
        expectedModel.updateFilteredLessonList(
                new FixedCodePredicate(
                        expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        expectedModel.unBookmarkLesson(expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model = prepareModel();
        model.updateFilteredLessonList(new FixedCodePredicate(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        Index invalidIndex = Index.fromZeroBased(model.getFilteredLessonList().size() + 1);
        UnmarkCommand unmarkCommand = prepareCommand(invalidIndex);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidListingTypeModule_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        model = prepareModel();
        model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
        UnmarkCommand unmarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandFailure(unmarkCommand, model, UnmarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }

    @Test
    public void execute_invalidListingTypeLocation_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        model = prepareModel();
        model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
        UnmarkCommand unmarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandFailure(unmarkCommand, model, UnmarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }

    private UnmarkCommand prepareCommand(Index index) {
        UnmarkCommand unmarkCommand = new UnmarkCommand(index);
        unmarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unmarkCommand;
    }

    /**
     * Prepare a model of all lessons inside being marked.
     */
    private Model prepareModel() throws DuplicateLessonException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        for (ReadOnlyLesson lesson : lessonList) {
            model.bookmarkLesson(lesson);
        }
        return model;
    }
}
