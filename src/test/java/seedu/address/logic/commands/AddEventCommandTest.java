package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.alias.exceptions.UnknownCommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.UnrecognisedParameterException;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.ReadOnlyUser;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.user.exceptions.UserNotFoundException;
import seedu.address.storage.Storage;
import seedu.address.testutil.EventBuilder;
import seedu.address.ui.UiManager;

//@@author kaiyu92
public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        AddEventCommandTest.ModelStub modelStub = new AddEventCommandTest.ModelStubThrowingDuplicateEventException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        getAddEventCommandForEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event test1 = new EventBuilder().withTitle("Test1").build();
        Event test2 = new EventBuilder().withTitle("Test2").build();
        AddEventCommand addTest1Command = new AddEventCommand(test1);
        AddEventCommand addTest2Command = new AddEventCommand(test2);

        // same object -> returns true
        assertTrue(addTest1Command.equals(addTest1Command));

        // same values -> returns true
        AddEventCommand addTest1CommandCopy = new AddEventCommand(test1);
        assertTrue(addTest1Command.equals(addTest1CommandCopy));

        // different types -> returns false
        assertFalse(addTest1Command.equals(1));

        // null -> returns false
        assertFalse(addTest1Command.equals(null));

        // different event -> returns false
        assertFalse(addTest1Command.equals(addTest2Command));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForEvent(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void exportAddressBook() throws FileNotFoundException, ParserConfigurationException,
                IOException, SAXException, TransformerException {

        }

        @Override
        public ReadOnlyAccount getAccount() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void transferData() {
            fail("This method should not be called.");
        }

        @Override
        public void transferDataWithDefault() {
            fail("This method should not be called.");
        }

        @Override
        public void deleteEncryptedContacts(String substring) {
            fail("This method should not be called.");
        }

        @Override
        public void releaseEncryptedContacts(String fileName) throws DataConversionException, IOException {
            fail("This method should not be called.");
        }

        @Override
        public UserPrefs getUserPrefs() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void refreshAddressBook() throws IOException, DataConversionException {
            fail("This method should not be called.");
        }

        @Override
        public void emptyPersonList(ObservableList<ReadOnlyPerson> list) throws PersonNotFoundException, IOException,
                DataConversionException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getListLength() throws IOException, DataConversionException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void encrypt(String userId, String pass, boolean emptyFile) throws Exception {

        }

        @Override
        public void decrypt(String fileName, String pass) throws Exception {

        }

        @Override
        public void encryptPublic(boolean isLockCommand) throws CommandException {

        }

        @Override
        public void saveToEncryptedFile() {

        }

        @Override
        public void orderList(String parameter) throws UnrecognisedParameterException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Group> getGroupList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ArrayList<ArrayList<String>> getCommands() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public String getAliasForCommand(String commandName) {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void resetEventData(ReadOnlyEventBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void exportEventBook() throws FileNotFoundException, ParserConfigurationException,
                IOException, SAXException, TransformerException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException,
                EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyEvent> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
            fail("This method should not be called");
        }

        @Override
        public void orderEventList(String parameter)
                throws seedu.address.model.event.exceptions.UnrecognisedParameterException {
            fail("This method should not be called.");
        }

        @Override
        public void setAlias(String command, String alias) throws UnknownCommandException, DuplicateAliasException {
            fail("This method should not be called.");
        }

        @Override
        public void persistUserAccount(ReadOnlyUser user) throws DuplicateUserException {
            fail("This method should not be called.");
        }

        @Override
        public User getUserFromIdAndPassword(String userName, String password) throws UserNotFoundException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteUser(String userName, String saltedPasswordHex) throws UserNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public String retrieveSaltFromStorage(String userId) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setUserStorage(Storage userStorage) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateEventException when trying to add a event.
     */
    private class ModelStubThrowingDuplicateEventException extends AddEventCommandTest.ModelStub {
        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends AddEventCommandTest.ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            eventsAdded.add(new Event(event));
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
