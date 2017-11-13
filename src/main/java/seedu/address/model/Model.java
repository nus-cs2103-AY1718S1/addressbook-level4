package seedu.address.model;

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
import seedu.address.logic.commands.exceptions.ConfigMissingException;
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
 * The API of the Model component.
 */
public interface Model {
    //==================================AddressBook Components=============================================
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<ReadOnlyUser> PREDICATE_SHOW_ALL_USERS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyEvent> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Export the Addressbook
     */
    void exportAddressBook() throws FileNotFoundException, ParserConfigurationException, IOException,
            SAXException, TransformerException;

    /**
     * Returns the Account
     */
    ReadOnlyAccount getAccount();

    /**
     * Deletes the given person
     */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Order the list based on a parameter
     */
    void orderList(String parameter) throws UnrecognisedParameterException;

    /**
     * Adds the given person
     */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /**
     * Delete the given tag on every person in the Addressbook.
     */
    void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException;

    /**
     * Returns an unmodifiable view of the filtered person list.
     */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the UniqueGroupList
     */
    ObservableList<Group> getGroupList();

    /**
     * Returns a list of commands
     */
    ArrayList<ArrayList<String>> getCommands();

    /**
     * Returns the set alias for command, null otherwise
     */
    String getAliasForCommand(String commandName);

    /**
     * Updates the filter of the filtered person list to show all persons
     */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //=====================================================================================================

    //==================================EventBook Components=============================================


    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetEventData(ReadOnlyEventBook newData);

    /**
     * Returns the EventBook
     */
    ReadOnlyEventBook getEventBook();

    /**
     * Export the EventBook
     */
    void exportEventBook() throws FileNotFoundException, ParserConfigurationException, IOException,
            SAXException, TransformerException;

    /**
     * Deletes the given event.
     */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;

    /**
     * Adds the given event
     */
    void addEvent(ReadOnlyEvent event) throws DuplicateEventException;

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the event's details causes the person to be equivalent to
     *                                 another existing event in the list.
     * @throws EventNotFoundException  if {@code target} could not be found in the list.
     */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered event list
     */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);

    /**
     * Order the event list based on a parameter
     */
    void orderEventList(String parameter) throws seedu.address.model.event.exceptions.UnrecognisedParameterException;
    //===================================================================================================

    /**
     * Updates the alias of the given function with the given {@code alias}.
     *
     * @throws NullPointerException if {@code alias} is null.
     */
    void setAlias(String command, String alias) throws UnknownCommandException, DuplicateAliasException;

    void persistUserAccount(ReadOnlyUser user) throws DuplicateUserException;

    User getUserFromIdAndPassword(String userName, String password) throws UserNotFoundException;

    void deleteUser(String userName, String saltedPasswordHex) throws UserNotFoundException;

    String retrieveSaltFromStorage(String userId) throws UserNotFoundException;

    void setUserStorage(Storage userStorage);

    void transferData() throws ConfigMissingException;

    void transferDataWithDefault() throws IOException, DataConversionException;

    //@@author quanle1994

    void deleteEncryptedContacts(String substring);

    void releaseEncryptedContacts(String fileName) throws DataConversionException, IOException;

    UserPrefs getUserPrefs();

    void refreshAddressBook() throws IOException, DataConversionException;

    void emptyPersonList(ObservableList<ReadOnlyPerson> list) throws PersonNotFoundException, IOException,
            DataConversionException;

    ObservableList<ReadOnlyPerson> getListLength() throws IOException, DataConversionException;

    void encrypt(String userId, String pass, boolean emptyFile) throws Exception;

    void decrypt(String fileName, String pass) throws Exception;

    void encryptPublic(boolean isLockCommand) throws CommandException;

    void saveToEncryptedFile();
}
