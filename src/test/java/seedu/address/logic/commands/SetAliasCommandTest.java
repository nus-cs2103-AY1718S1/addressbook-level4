//@@author keloysiusmak
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ALIAS;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.alias.exceptions.UnknownCommandException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.ReadOnlyUser;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.user.exceptions.UserNotFoundException;
import seedu.address.storage.Storage;
import seedu.address.testutil.AliasBuilder;
import seedu.address.ui.UiManager;

public class SetAliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullCommandAndAlias_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetAliasCommand(null, null);
    }

    @Test
    public void execute_aliasAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAliasSet modelStub = new ModelStubAcceptingAliasSet();
        Alias validAlias = new AliasBuilder().build();

        CommandResult commandResult = getSetAliasCommand(validAlias, modelStub).execute();

        assertEquals(String.format(SetAliasCommand.MESSAGE_SUCCESS, validAlias), commandResult.feedbackToUser);
    }

    @Test
    public void execute_throwsDuplicateAliasException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAliasException();
        Alias validAlias = new AliasBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_ALIAS);

        getSetAliasCommand(validAlias, modelStub).execute();
    }

    @Test
    public void execute_throwsUnknownCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingUnknownCommandException();
        Alias validAlias = new AliasBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);

        getSetAliasCommand(validAlias, modelStub).execute();
    }

    @Test
    public void equals() {
        SetAliasCommand addAliceAlias = new SetAliasCommand("help", "alice");
        SetAliasCommand addBobAlias = new SetAliasCommand("help", "bob");

        // same object -> returns true
        assertTrue(addAliceAlias.equals(addAliceAlias));

        // same values -> returns true
        SetAliasCommand addAliceAliasCopy = new SetAliasCommand("help", "alice");
        assertTrue(addAliceAlias.equals(addAliceAliasCopy));

        // different types -> returns false
        assertFalse(addAliceAlias.equals(1));

        // null -> returns false
        assertFalse(addAliceAlias.equals(null));

        // different alias -> returns false
        assertFalse(addAliceAlias.equals(addBobAlias));
    }

    @Test
    public void execute() throws Exception {

        ModelStub model;
        Alias testAlias;

        //set valid alias
        model = new ModelStubAcceptingAliasSet();
        testAlias = new Alias("help", "h");
        getSetAliasCommand(testAlias, model).execute();
        assertEquals("h", model.getAliasForCommand("help"));

        //change to a new valid alias
        testAlias = new Alias("help", "x");
        getSetAliasCommand(testAlias, model).execute();
        assertEquals("x", model.getAliasForCommand("help"));

        //set invalid alias
        testAlias = new Alias("help", "help");
        getSetAliasCommand(testAlias, model).execute();
        assertEquals("x", model.getAliasForCommand("help"));

        //set duplicate alias
        testAlias = new Alias("list", "x");
        getSetAliasCommand(testAlias, model).execute();
        assertEquals("list", model.getAliasForCommand("list"));

        //set valid alias
        testAlias = new Alias("list", "y");
        getSetAliasCommand(testAlias, model).execute();
        assertEquals("y", model.getAliasForCommand("list"));

        //set alias for invalid command
        testAlias = new Alias("nonsense", "y");
        getSetAliasCommand(testAlias, model).execute();
        assertEquals(null, model.getAliasForCommand("nonsense"));
    }

    /**
     * Generates a new SetAliasCommand with the details of the given alias.
     */
    private SetAliasCommand getSetAliasCommand(Alias alias, Model model) {
        SetAliasCommand command = new SetAliasCommand(alias.getCommand(), alias.getAlias());
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
        public void orderList(String parameter) {
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
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
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
        public void emptyPersonList(ObservableList<ReadOnlyPerson> list) throws PersonNotFoundException,
                IOException, DataConversionException {
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
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called");
        }

        @Override
        public ArrayList<ArrayList<String>> getCommands() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public String getAliasForCommand(String commandName) {
            fail("This method should not be called.");
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
            fail("This method should not be called.");
        }

        @Override
        public void orderEventList(String parameter)
                throws seedu.address.model.event.exceptions.UnrecognisedParameterException {
            fail("This method should not be called.");
        }

        @Override
        public void setAlias(String command, String alias) throws DuplicateAliasException, UnknownCommandException {
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
     * A Model stub that always throw a DuplicateAliasException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateAliasException extends ModelStub {

        public void setAlias(String command, String alias) throws DuplicateAliasException {
            throw new DuplicateAliasException(MESSAGE_DUPLICATE_ALIAS);
        }
    }

    /**
     * A Model stub that always throw a UnknownCommandException when trying to add a person.
     */
    private class ModelStubThrowingUnknownCommandException extends ModelStub {

        public void setAlias(String command, String alias) throws UnknownCommandException {
            throw new UnknownCommandException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * A Model stub that always accept the alias being set.
     */
    private class ModelStubAcceptingAliasSet extends ModelStub {
        private HashSet<Alias> aliases = new HashSet<Alias>();

        public ModelStubAcceptingAliasSet() {
            ArrayList<String> functions = new ArrayList<String>(Arrays.asList("add", "currentuser", "delete", "edit",
                    "exit", "find", "group", "help", "history", "lock", "list", "login", "logout", "order", "redo",
                    "remark", "remove", "select", "setalias", "undo", "transfer", "viewalias", "addevent",
                    "deleteevent", "editevent", "listevent", "orderevent", "findevent", "settheme", "switch",
                    "selectevent", "export"));
            for (String alias : functions) {
                aliases.add(new Alias(alias, alias));
            }
        }

        @Override
        public void setAlias(String command, String name) {
            Alias newAlias = new Alias(command, name);
            int valid = 0;
            for (Alias a : aliases) {
                //check for valid command
                if (a.getCommand().equals(newAlias.getCommand())) {
                    valid = 1;
                }
                //check for duplicate alias
                if (a.getAlias().equals(newAlias.getAlias())) {
                    valid = 0;
                    break;
                } else if (a.getCommand().equals(newAlias.getAlias())) {
                    //check for protected alias
                    valid = 0;
                    break;
                }
            }
            if (valid == 1) {
                Alias remove = null;
                for (Alias a : aliases) {
                    if (a.getCommand().equals(newAlias.getCommand())) {
                        remove = a;
                    }
                }
                aliases.remove(remove);
                aliases.add(newAlias);
            }
        }

        @Override
        public String getAliasForCommand(String commandName) {
            for (Alias a : aliases) {
                if (a.getCommand() == commandName) {
                    return a.getAlias();
                }
            }
            return null;
        }
    }
}
