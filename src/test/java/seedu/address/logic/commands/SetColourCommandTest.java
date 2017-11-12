package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;
import seedu.address.testutil.TypicalStorage;

//@@author eldonng
public class SetColourCommandTest {

    private static final String VALID_TAG_FRIEND = "friends";
    private static final String INVALID_TAG = "asd123";

    private Model model;
    private Storage storage;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        storage = new TypicalStorage().setUp();
    }

    @Test
    public void execute_setColourTag_success() throws Exception {
        SetColourCommand setColourCommand = new SetColourCommand(VALID_TAG_FRIEND, "red");
        setColourCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setTagColour(VALID_TAG_FRIEND, "red");

        assertCommandSuccess(setColourCommand, model,
                String.format(SetColourCommand.SETCOLOUR_SUCCESS, VALID_TAG_FRIEND, "red"), expectedModel);
    }

    @Test
    public void execute_setInvalidColour_failure() throws Exception {
        SetColourCommand setColourCommand = new SetColourCommand(VALID_TAG_FRIEND, "nocolour");
        setColourCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        String expectedMessage = String.format(SetColourCommand.SETCOLOUR_INVALID_COLOUR, "nocolour");

        assertCommandFailure(setColourCommand, model, expectedMessage);
    }

    @Test
    public void execute_setInvalidTag_failure() throws Exception {
        SetColourCommand setColourCommand = new SetColourCommand(INVALID_TAG, "red");
        setColourCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        String expectedMessage = SetColourCommand.SETCOLOUR_INVALID_TAG;

        assertCommandFailure(setColourCommand, model, expectedMessage);
    }
}
