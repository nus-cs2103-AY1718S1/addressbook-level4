package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstLesson;
import static seedu.address.logic.commands.CommandTestUtil.showFirstLessonOnly;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.LessonNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstLesson(expectedModel);
        assertEquals(expectedModel, model);

        showFirstLessonOnly(model);

        // undo() should cause the model's filtered list to show all persons
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showFirstLessonOnly(model);
        showFirstLessonOnly(expectedModel);
        for (int i = 0; i < model.getFilteredLessonList().size(); i++) {
            System.out.println(model.getFilteredLessonList().get(i));
        }

        dummyCommand.redo();
        deleteFirstLesson(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first person in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(0);
            try {
                model.deleteLesson(lessonToDelete);
            } catch (LessonNotFoundException pnfe) {
                fail("Impossible: lessonToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
