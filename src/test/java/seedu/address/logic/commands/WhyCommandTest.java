package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author arnollim
/**
 * Contains Testcases for  {@code WhyCommand}.
 */
public class WhyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_why_success() {
        showFirstPersonOnly(model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        WhyCommand whyCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(whyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private WhyCommand prepareCommand(Index index) {
        WhyCommand whyCommand = new WhyCommand(index);
        whyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return whyCommand;
    }

}
