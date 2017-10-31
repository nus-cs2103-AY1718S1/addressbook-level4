package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.Before;
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
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.predicates.FixedCodePredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

public class MarkCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        ReadOnlyLesson lessonToMark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(MarkCommand.MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.bookmarkLesson(expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.updateFilteredLessonList(new FixedCodePredicate(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        ReadOnlyLesson lessonToMark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(MarkCommand.MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.updateFilteredLessonList(new FixedCodePredicate(
                expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        expectedModel.bookmarkLesson(expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.updateFilteredLessonList(new FixedCodePredicate(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        Index invalidIndex = Index.fromZeroBased(model.getFilteredLessonList().size() + 1);
        MarkCommand markCommand = prepareCommand(invalidIndex);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidListingTypeModule_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandFailure(markCommand, model, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }

    @Test
    public void execute_invalidListingTypeLocation_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandFailure(markCommand, model, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }



    private MarkCommand prepareCommand(Index index) {
        MarkCommand markCommand = new MarkCommand(index);
        markCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return markCommand;
    }
}
