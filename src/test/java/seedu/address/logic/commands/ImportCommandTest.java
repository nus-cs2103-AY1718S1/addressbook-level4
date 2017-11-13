//@@author PhuaJunJie
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalGoogleContactsList;

public class ImportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private List<Person> googleList;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        googleList = new ArrayList<>();
    }

    /**
     * Checks if Login is authenticated. In this case it is not as user is not authenticated
     * and GoogleAuthException is thrown.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new ImportCommand();
    }
    /**
     * Checks if Google contacts is empty. In this case it is empty and null exception is thrown.
     */
    @Test
    public void execute_empty_googleContacts() throws Exception {
        thrown.expect(NullPointerException.class);
        new ImportCommand(googleList).executeUndoableCommand();
    }

    /**
     * Test for normal importing of a google contact
     */
    @Test
    public void execute_commandSuccess_imported() throws Exception {
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        ImportCommand command = prepareCommand(googleList, this.model);

        Model modelstub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelstub.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 1, 0) + "\n";

        assertCommandSuccess(command, expectedMessage, modelstub);
    }

    /**
     * Test for invalid importing of a google contact due to invalid attributes
     */
    @Test
    public void execute_commandFailure_contactInvalidFormat() throws Exception {
        googleList.add(TypicalGoogleContactsList.MAYGOOGLE);
        ImportCommand command = prepareCommand(googleList, this.model);

        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 0, 1) + "\n"
                + String.format(Messages.MESSAGE_IMPORT_STATUS, 0, 1) + "\n"
                + "Please check the format of the following google contacts :  May";

        assertCommandFailure(command, expectedMessage, this.model);
    }

    /**
     * Test for importing a contact that already exists in the addressbook
     */
    @Test
    public void execute_commandFailure_contactExists() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        ImportCommand command = prepareCommand(googleList, model);

        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 0, 1) + "\n"
                + String.format(Messages.MESSAGE_IMPORT_STATUS, 1, 0) + "\n";

        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private ImportCommand prepareCommand(List<Person> personList, Model model) throws CommandException {
        ImportCommand command = new ImportCommand(personList);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts if a command successfully executed
     */
    private void assertCommandSuccess(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }

    /**
     * Asserts if a command unsuccessfully executed
     */
    private void assertCommandFailure(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }
}
