package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Code;
import seedu.address.model.module.Remark;

//@@author junming403
public class RemarkCommandTest {

    private static final String SAMPLE_REMARK = "This is a sample remark";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private RemarkCommand remarkCommand;


    @Test
    public void execute_validIndex_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Code code = model.getFilteredLessonList().get(INDEX_SECOND_LESSON.getZeroBased()).getCode();
        Remark remarkToAdd = new Remark(SAMPLE_REMARK, code);
        RemarkCommand remarkCommand = prepareCommand(INDEX_SECOND_LESSON, SAMPLE_REMARK);
        expectedModel.addRemark(remarkToAdd);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_MODULE_SUCCESS, code);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredLessonList().size());
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, SAMPLE_REMARK);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_longRemark_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_LESSON, getLongString());

        assertCommandFailure(remarkCommand, model, Remark.MESSAGE_REMARK_CONSTRAINTS);
    }

    @Test
    public void execute_validIndex_deleteSuccess() throws Exception {
        remarkCommand = new RemarkCommand(INDEX_FIRST_LESSON, "This is a sample remark");
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        remarkCommand.executeUndoableCommand();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                "This is a sample remark");

        RemarkCommand deleteRemarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandSuccess(deleteRemarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeDelete_invalidIndex_throwsCommandException() throws Exception {
        remarkCommand = new RemarkCommand(INDEX_FIRST_LESSON, "This is a sample remark");
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        remarkCommand.executeUndoableCommand();

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


    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index}.
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

    /**
     * Get a String that is longger than 150 characters.
     */
    private String getLongString() {
        String ouput = "";
        for (int i = 0; i < 1000; i++) {
            ouput += "abc";
        }
        return ouput;
    }

}
