package seedu.room.logic.commands;

import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Before;
import org.junit.Test;

import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.Person;
import seedu.room.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalResidentBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        String successMessage = String.format(AddCommand.MESSAGE_SUCCESS, validPerson);
        assertCommandSuccess(prepareCommand(validPerson, model), model,
                successMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = new Person(model.getResidentBook().getPersonList().get(0));
        assertCommandFailure(prepareCommand(personInList, model), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddCommand prepareCommand(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
