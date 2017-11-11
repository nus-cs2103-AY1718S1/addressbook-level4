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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author junming403
public class DeleteRemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() throws CommandException {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_LESSON, "This is a sample remark");
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        remarkCommand.executeUndoableCommand();
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                "This is a sample remark");

        RemarkCommand deleteRemarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandSuccess(deleteRemarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredLessonList().size());
        RemarkCommand deleteRemarkCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRemarkCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index}.
     */
    private RemarkCommand prepareCommand(Index index) {
        RemarkCommand deleteRemarkCommand = new RemarkCommand(index);
        deleteRemarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteRemarkCommand;
    }
}
