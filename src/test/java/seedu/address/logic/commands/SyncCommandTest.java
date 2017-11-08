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

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalGoogleContactsList;

//@@author PhuaJunJie
public class SyncCommandTest {
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
     * Checks if Login is authenticated. In this case it is not.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new ImportCommand();
    }

    /**
     * Test for normal Syncing of a single google contact
     */
    @Test
    public void execute_syncSuccess() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYSYNGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        Model modelStub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelStub.addPerson(TypicalGoogleContactsList.FREDDYSYNADDRESSBOOK);
        String expectedMessage = "1 contact/s Synced!     0 contact/s failed to Sync!";

        assertCommandSuccess(command, expectedMessage, modelStub);
    }

    /**
     * Test for invalid Syncing of a google contact due to invalid attributes
     */
    @Test
    public void execute_syncFailure_contactInvalidFormat() throws Exception {

        model.addPerson(TypicalGoogleContactsList.MAYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.MAYGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        String expectedMessage = "0 contact/s Synced!     1 contact/s failed to Sync!" + "\n"
                + "Please check the format of the following google contacts : May";

        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for syncing a contact that is of no difference than the one in the addressbook
     */
    @Test
    public void execute_commandFailure_contactSimilar() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        String expectedMessage = "0 contact/s Synced!     0 contact/s failed to Sync!";
        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for syncing a contact that is no longer in the addressbook. The contact will lose its google contacts
     * status
     */
    @Test
    public void execute_commandFailure_contactNoLongerExists() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        SyncCommand command = prepareCommand(googleList, model);


        String expectedMessage = "1 contact/s Synced!     0 contact/s failed to Sync!";
        Model modelStub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelStub.addPerson(TypicalGoogleContactsList.FREEDYNOTGOOGLEADDRESSBOOK);

        assertCommandFailure(command, expectedMessage, modelStub);
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private SyncCommand prepareCommand(List<Person> personList, Model model) throws CommandException {
        SyncCommand command = new SyncCommand(personList);
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
