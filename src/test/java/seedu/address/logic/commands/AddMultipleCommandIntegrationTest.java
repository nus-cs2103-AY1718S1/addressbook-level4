package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.GuiUnitTest;

//@@author KhorSL
/**
 * Contains integration tests (interaction with the Model) for {@code AddMultipleCommand}.
 */
public class AddMultipleCommandIntegrationTest extends GuiUnitTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ArrayList<ReadOnlyPerson> validPersonArrayList = new ArrayList<>();
        Person validPerson1 = new PersonBuilder().withName("Alice").build();
        Person validPerson2 = new PersonBuilder().withName("Bob").build();
        Person validPerson3 = new PersonBuilder().withName("Casey").build();
        StringBuilder successMessage = new StringBuilder();

        // single person
        validPersonArrayList.add(validPerson1);

        for (ReadOnlyPerson validPerson : validPersonArrayList) {
            expectedModel.addPerson(validPerson);
        }

        for (ReadOnlyPerson personToAdd: validPersonArrayList) {
            successMessage.append(System.lineSeparator());
            successMessage.append(personToAdd);
        }

        assertCommandSuccess(prepareCommand(validPersonArrayList, model), model,
                String.format(AddMultipleCommand.MESSAGE_SUCCESS, successMessage), expectedModel);

        // multiple persons
        validPersonArrayList = new ArrayList<>();
        validPersonArrayList.add(validPerson3);
        validPersonArrayList.add(validPerson2);

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        for (ReadOnlyPerson validPerson : validPersonArrayList) {
            expectedModel.addPerson(validPerson);
        }

        successMessage = new StringBuilder();
        for (ReadOnlyPerson personToAdd: validPersonArrayList) {
            successMessage.append(System.lineSeparator());
            successMessage.append(personToAdd);
        }

        assertCommandSuccess(prepareCommand(validPersonArrayList, model), model,
                String.format(AddMultipleCommand.MESSAGE_SUCCESS, successMessage), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        // 1 duplicate person (boundary case)
        Person firstPersonInList = new Person(model.getAddressBook().getPersonList().get(0));
        ArrayList<ReadOnlyPerson> personArrayList = new ArrayList<>();
        personArrayList.add(firstPersonInList);
        assertCommandFailure(prepareCommand(personArrayList, model), model,
                AddMultipleCommand.MESSAGE_DUPLICATE_PERSON);

        // more than 1 duplicate person
        Person secondPersonInList = new Person(model.getAddressBook().getPersonList().get(1));
        personArrayList = new ArrayList<>();
        personArrayList.add(firstPersonInList);
        personArrayList.add(secondPersonInList);
        assertCommandFailure(prepareCommand(personArrayList, model), model,
                AddMultipleCommand.MESSAGE_DUPLICATE_PERSON);

        // first person not duplicate, second person is duplicate
        Person validPerson = new PersonBuilder().withName("Alice").build();
        personArrayList = new ArrayList<>();
        personArrayList.add(validPerson);
        personArrayList.add(firstPersonInList);
        assertCommandFailure(prepareCommand(personArrayList, model), model,
                AddMultipleCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_emptyPersonList() {
        ArrayList<ReadOnlyPerson> personArrayList = new ArrayList<>();
        try {
            prepareCommand(personArrayList, model);
        } catch (AssertionError e) {
            return; // short circuit test here as assertion error should be caught
        }
        Assert.fail("Fails to catch Assertion Error");
    }

    /**
     * Generates a new {@code AddMultipleCommand} which upon execution,
     * adds persons in {@code personArrayList} into the {@code model}.
     */
    private AddMultipleCommand prepareCommand(ArrayList<ReadOnlyPerson> personArrayList, Model model) {
        AddMultipleCommand command = new AddMultipleCommand(personArrayList);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), null);
        return command;
    }
}
