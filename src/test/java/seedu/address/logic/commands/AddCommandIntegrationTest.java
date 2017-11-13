package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Lesson;
import seedu.address.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newLesson_success() throws Exception {
        Lesson validLesson = new LessonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addLesson(validLesson);

        assertCommandSuccess(prepareCommand(validLesson, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validLesson), expectedModel);
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson lessonInList = new Lesson(model.getAddressBook().getLessonList().get(0));
        assertCommandFailure(prepareCommand(lessonInList, model), model, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code lesson} into the {@code model}.
     */
    private AddCommand prepareCommand(Lesson lesson, Model model) {
        AddCommand command = new AddCommand(lesson);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
