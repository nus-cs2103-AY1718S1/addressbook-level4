package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstLesson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model modelHelper = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private DeleteCommand deleteCommandOne;
    private DeleteCommand deleteCommandTwo;

    @Before
    public void setUp() throws CommandException {
        ListingUnit.setCurrentPredicate(new UniqueModuleCodePredicate(modelHelper.getUniqueCodeSet()));
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        deleteCommandOne = new DeleteCommand(INDEX_FIRST_LESSON);
        deleteCommandTwo = new DeleteCommand(INDEX_FIRST_LESSON);

        deleteCommandOne.setData(modelHelper, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(modelHelper, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();
    }

    @Test
    public void execute() throws CommandException {

        final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);

        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstLesson(expectedModel);

        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstLesson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
