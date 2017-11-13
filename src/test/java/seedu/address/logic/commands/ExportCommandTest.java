//@@author PhuaJunJie
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.BERNICE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.GoogleContactsBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalGoogleContactsList;

public class ExportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private List<Person> googleList;

    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        googleList = new ArrayList<>();
    }

    /**
     * Checks if Login is authenticated. In this case it is not and GoogleAuthException is thrown.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new ExportCommand();
    }

    /**
     * Test for exporting an empty addressbook. Throws Command exception for empty addressbook
     */
    @Test
    public void execute_commandFailure_noContacts() throws Exception {
        thrown.expect(CommandException.class);
        ExportCommand command = prepareCommand(this.model);
        command.execute();
    }

    /**
     * Test for exporting an empty addressbook when authentication is lost.
     */
    @Test
    public void execute_commandFailure_noAuthenticationToExport() throws Exception {
        thrown.expect(GoogleAuthException.class);
        model.addPerson(BERNICE);
        ExportCommand command = prepareCommand(this.model);
        command.execute();
    }


    /**
     * Test for no importing of contacts to addressbook as all contacts in addressbook are already in google contacts.
     */
    @Test
    public void execute_commandFailure_allGoogleContacts() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);

        ExportCommand command = prepareCommand(this.model);
        String expectedMessage = "0 contact/s exported!     All contacts can be now found in google contact";
        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for converting a addressbook contact into a google contact
     */
    @Test
    public void execute_updateAddressBookContactToGoogle() throws Exception {
        model.addPerson(BERNICE);

        ExportCommand command = prepareCommand(this.model);
        Person aliceGoogleContact = command.createGoogleContact(BERNICE);
        assertEquals(aliceGoogleContact, TypicalGoogleContactsList.BERNICE);
    }
    /**
     * Test for changes in contact after being exported to google
     */
    @Test
    public void execute_exportContactChanges() throws Exception {
        ExportCommand command = prepareCommand(this.model);
        ReadOnlyPerson editedAlice =
                command.getNewAddressBookContact(BERNICE, TypicalGoogleContactsList.BERNICEWITHGOOGLEID);
        assertEquals(editedAlice, TypicalGoogleContactsList.BERNICEADDRESSBOOK);
    }

    /**
     * Test detailed messages for export command.
     */
    @Test
    public void execute_exportDetailedMessages() throws Exception {
        ExportCommand command = prepareCommand(this.model);
        String messageSuccess = command.setCommandMessage(1, 0);
        String messageFailure = command.setCommandMessage(0, 1);
        assertEquals(messageSuccess, "1 contact/s exported!     "
                + "All contacts can be now found in google contact");
        assertEquals(messageFailure, "0 contact/s exported!     1 contact/s failed to export!");
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private ExportCommand prepareCommand(Model model) throws CommandException {
        GoogleContactsBuilder builder = null;
        ExportCommand command = new ExportCommand(builder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
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
