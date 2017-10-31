package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
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
    private static final String SAMPLE_VERY_LONG_REMARK = "This module introduces the necessary conceptual and "
            + "analytical tools for systematic and rigorous development of software systems. It covers four main "
            + "areas of software development, namely object-oriented system analysis, object-oriented system modelling"
            + " and design, implementation, and testing, with emphasis on system modelling and design and "
            + "implementation of software modules that work cooperatively to fulfill the requirements of the system. "
            + "Tools and techniques for software development, such as Unified Modelling Language (UML), program "
            + "specification, and testing methods, will be taught. Major software engineering issues such as "
            + "modularisation criteria, program correctness, and software quality will also be covered.";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Code code = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode();
        Remark remarkToAdd = new Remark(SAMPLE_REMARK, code);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_LESSON, SAMPLE_REMARK);
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
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_LESSON, SAMPLE_VERY_LONG_REMARK);

        assertCommandFailure(remarkCommand, model, Remark.MESSAGE_REMARK_CONSTRAINTS);
    }

    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index}.
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }


}
