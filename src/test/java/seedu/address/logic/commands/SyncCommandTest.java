package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PersonMetadata;
import com.google.api.services.people.v1.model.PhoneNumber;
import com.google.api.services.people.v1.model.Source;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;


//@@author derrickchua
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SyncCommand}.
 */
public class SyncCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addSync_failure() throws Exception {
        SyncCommand syncCommand = prepareCommand();

        String expectedMessage = String.format(SyncCommand.MESSAGE_FAILURE);
        assertCommandFailure(syncCommand, model, expectedMessage);
    }

    @Test
    public void check_equalPerson() {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        Person aliceGoogle = prepareAliceGoogle();

        assertTrue(syncCommand.equalPerson(model.getFilteredPersonList().get(0), aliceGoogle));
    }

    @Test
    public void test_convertAPerson() {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        ReadOnlyPerson aliceAbc = model.getFilteredPersonList().get(0);
        Person person = syncCommand.convertAPerson(aliceAbc);

        assertTrue(syncCommand.equalPerson(aliceAbc, person));
    }

    @Test
    public void test_convertGPerson() throws Exception {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        ReadOnlyPerson aliceAbc = model.getFilteredPersonList().get(0);
        Person aliceGoogle = prepareAliceGoogle();
        seedu.address.model.person.Person converted = syncCommand.convertGooglePerson(aliceGoogle, aliceAbc);

        assertEquals(converted, aliceAbc);
    }

    @Test
    public void test_getLastUpdated() {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        Person aliceGoogle = prepareAliceGoogle();

        assertTrue(syncCommand.getLastUpdated(aliceGoogle).equals("2017-11-12T16:29:49.398001Z"));
    }

    @Test
    public void test_linkedContact() throws Exception {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        ReadOnlyPerson aliceAbc =  model.getFilteredPersonList().get(0);
        Person aliceGoogle = prepareAliceGoogle();

        syncCommand.linkContacts(aliceAbc, aliceGoogle);

        aliceAbc =  model.getFilteredPersonList().get(0);

        assertTrue(aliceAbc.getId().getValue().equals("alice")
                && aliceAbc.getLastUpdated().getValue().equals("2017-11-12T16:29:49.398001Z"));
    }

    @Test
    public void test_addAContact() throws Exception {
        SyncCommand syncCommand = prepareCommand();
        Person aliceGoogle = prepareAliceGoogle();

        // Delete Alice from address book
        model.deletePerson(model.getFilteredPersonList().get(0));

        syncCommand.addAContact(aliceGoogle);
        seedu.address.model.person.Person aliceAbc = syncCommand.convertGooglePerson(aliceGoogle);
        model.sort("name");

        assertEquals(model.getFilteredPersonList().get(0), aliceAbc);
    }

    @Test
    public void save_load_success() throws Exception {
        SyncCommand syncCommand = prepareCommand();
        HashSet<String> syncedId = new HashSet<String>();
        syncedId.add("test");
        syncCommand.setSyncedId(syncedId);
        syncCommand.saveStatus(syncedId);
        HashSet<String> expected = (HashSet<String>) syncCommand.loadStatus();

        assertTrue(expected.size() == 1 && expected.contains("test"));
    }

    @Test
    public void test_hashing() throws Exception {
        showFirstPersonOnly(model);
        SyncCommand syncCommand = prepareCommand();

        Person alice = prepareAliceGoogle();
        seedu.address.model.person.Person aliceAbc = syncCommand.convertGooglePerson(alice);
        seedu.address.model.person.ReadOnlyPerson actualAlice =
                syncCommand.getHashKey(model.getFilteredPersonList().get(0));

        assertTrue(syncCommand.equalPerson(actualAlice, alice));
        assertTrue(actualAlice.equals(aliceAbc));
        assertEquals(aliceAbc.hashCode(), actualAlice.hashCode());
    }

    @Test
    public void equals() {
        SyncCommand syncFirstCommand = new SyncCommand();
        SyncCommand syncSecondCommand = new SyncCommand();

        // same object -> returns true
        assertTrue(syncFirstCommand.equals(syncFirstCommand));

        // different types -> returns false
        assertFalse(syncFirstCommand.equals(1));

        // null -> returns false
        assertFalse(syncFirstCommand.equals(null));

        // returns true
        assertTrue(syncFirstCommand.equals(syncSecondCommand));
    }

    /**
     * Returns a {@code SyncCommand}
     */
    private SyncCommand prepareCommand() {
        SyncCommand synccommand = new SyncCommand();
        synccommand.setData(model, new CommandHistory(), new UndoRedoStack());
        synccommand.setSyncedId(new HashSet<String>());
        return synccommand;
    }

    /** Prepares a Google Person which is the equivalent of the ABC Person ALICE for testing
     *
     * @return
     */
    private Person prepareAliceGoogle() {
        Person result  = new Person();
        PersonMetadata metadata = new PersonMetadata();
        List<Name>  name = new ArrayList<>();
        List<Address> address = new ArrayList<Address>();
        List<EmailAddress> email = new ArrayList<>();
        List<PhoneNumber> phone = new ArrayList<>();
        List<Source> source = new ArrayList<>();


        name.add(new Name().setGivenName("Alice Pauline"));
        address.add(new Address().setFormattedValue("123, Jurong West Ave 6, #08-111"));
        email.add(new EmailAddress().setValue("alice@example.com"));
        phone.add(new PhoneNumber().setValue("85355255"));
        source.add(new Source().setUpdateTime("2017-11-12T16:29:49.398001Z"));
        metadata.setSources(source);

        result.setEmailAddresses(email)
                .setNames(name)
                .setPhoneNumbers(phone)
                .setAddresses(address)
                .setResourceName("alice")
                .setMetadata(metadata);

        return result;
    }


}
