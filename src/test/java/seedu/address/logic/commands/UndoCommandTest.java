package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.email.EmailManager;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.ImageInit;

public class UndoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_FIRST_PERSON);

    @BeforeClass
    public static void setUp() {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @Before
    public void init() {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @AfterClass
    public static void recovery() {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute(); //Delete Alice
        deleteCommandTwo.execute(); //Delete John

        //Check if Alice and Bob's photo are deleted.
        assertFalse(ImageInit.checkAlicePhoto());
        assertFalse(ImageInit.checkJohnPhoto());

        // multiple commands in undoStack
        ImageInit.initAlice();
        ImageInit.initJohn();

        //Check if Alice and Bob's photo are recovered.
        assertTrue(ImageInit.checkAlicePhoto());
        assertTrue(ImageInit.checkJohnPhoto());

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
        deleteFirstPerson(expectedModel);

        assertFalse(ImageInit.checkAlicePhoto()); //Check if Alice's photo is deleted again.
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        ImageInit.initAlice();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
