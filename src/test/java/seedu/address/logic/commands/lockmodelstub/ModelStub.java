package seedu.address.logic.commands.lockmodelstub;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.alias.exceptions.UnknownCommandException;
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

/**
 * Shared ModelStub for Lock Mechanism commands
 */
public class ModelStub implements Model {
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
    public void exportAddressBook() throws ParserConfigurationException,
            IOException, SAXException, TransformerException {

    }

    @Override
    public ReadOnlyAccount getAccount() {
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
    }

    @Override
    public void releaseEncryptedContacts(String fileName) throws DataConversionException, IOException {
    }

    @Override
    public UserPrefs getUserPrefs() {
        return null;
    }

    @Override
    public void refreshAddressBook() throws IOException, DataConversionException {
    }

    @Override
    public void emptyPersonList(ObservableList<ReadOnlyPerson> list) throws PersonNotFoundException, IOException,
            DataConversionException {
        return;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getListLength() throws IOException, DataConversionException {
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
    }

    @Override
    public User getUserFromIdAndPassword(String userName, String password) throws UserNotFoundException {
        return null;
    }

    @Override
    public void deleteUser(String userName, String saltedPasswordHex) throws UserNotFoundException {
    }

    @Override
    public String retrieveSaltFromStorage(String userId) throws UserNotFoundException {
        return null;
    }

    @Override
    public void setUserStorage(Storage userStorage) {
        fail("This method should not be called.");
    }
}
