package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.AliasSettings;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AccountChangedEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.EventBookChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.encryption.FileEncryptor;
import seedu.address.commons.util.encryption.SaveToEncryptedFile;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.OrderCommand;
import seedu.address.logic.commands.OrderEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelectEventCommand;
import seedu.address.logic.commands.SetAliasCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.TransferCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewAliasCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ConfigMissingException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.alias.exceptions.UnknownCommandException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.UnrecognisedParameterException;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.ReadOnlyUser;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.user.exceptions.UserNotFoundException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final EventBook eventBook;
    private final FilteredList<ReadOnlyEvent> filteredEvents;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final ArrayList<ArrayList<String>> viewAliases;
    private final Account account;
    private final Config config;
    private UserPrefs userPref;
    private Storage userStorage;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyEventBook eventBook, UserPrefs userPrefs,
                        ReadOnlyAccount account, Config config) {
        super();
        requireAllNonNull(addressBook, eventBook, userPrefs);
        logger.fine("Initializing with address book: " + addressBook + ", event book: " + eventBook
                + " and user prefs " + userPrefs + " and account " + account);

        this.addressBook = new AddressBook(addressBook);
        this.eventBook = new EventBook(eventBook);
        this.userPref = userPrefs;
        this.account = new Account(account);
        this.config = config;

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.eventBook.getEventList());

        //@@author keloysiusmak
        ArrayList<ArrayList<String>> commandList = new ArrayList<ArrayList<String>>();

        //Add Command
        commandList.add(new ArrayList<String>(Arrays.asList("Add", AddCommand.getCommandWord())));

        //Add Event Command
        commandList.add(new ArrayList<String>(Arrays.asList("Add Event", AddEventCommand.getCommandWord())));

        //Clear Command
        commandList.add(new ArrayList<String>(Arrays.asList("Clear", ClearCommand.getCommandWord())));

        //Delete Command
        commandList.add(new ArrayList<String>(Arrays.asList("Delete", DeleteCommand.getCommandWord())));

        //Delete Event Command
        commandList.add(new ArrayList<String>(Arrays.asList("Delete Event", DeleteEventCommand.getCommandWord())));

        //Edit Command
        commandList.add(new ArrayList<String>(Arrays.asList("Edit", EditCommand.getCommandWord())));

        //Edit Event Command
        commandList.add(new ArrayList<String>(Arrays.asList("Edit Event", EditEventCommand.getCommandWord())));

        //Exit Command
        commandList.add(new ArrayList<String>(Arrays.asList("Exit", ExitCommand.getCommandWord())));

        //Export Command
        commandList.add(new ArrayList<String>(Arrays.asList("Export", ExportCommand.getCommandWord())));

        //Find Command
        commandList.add(new ArrayList<String>(Arrays.asList("Find", FindCommand.getCommandWord())));

        //Find Command
        commandList.add(new ArrayList<String>(Arrays.asList("Find Event", FindEventCommand.getCommandWord())));

        //Help Command
        commandList.add(new ArrayList<String>(Arrays.asList("Help", HelpCommand.getCommandWord())));

        //History Command
        commandList.add(new ArrayList<String>(Arrays.asList("History", HistoryCommand.getCommandWord())));

        //List Command
        commandList.add(new ArrayList<String>(Arrays.asList("List", ListCommand.getCommandWord())));

        //List Event Command
        commandList.add(new ArrayList<String>(Arrays.asList("List Event", ListEventCommand.getCommandWord())));

        //Lock Command
        commandList.add(new ArrayList<String>(Arrays.asList("Lock", LockCommand.getCommandWord())));

        //Login Command
        commandList.add(new ArrayList<String>(Arrays.asList("Log in", LoginCommand.getCommandWord())));

        //Order Command
        commandList.add(new ArrayList<String>(Arrays.asList("Order", OrderCommand.getCommandWord())));

        //OrderEvent Command
        commandList.add(new ArrayList<String>(Arrays.asList("Order Event", OrderEventCommand.getCommandWord())));

        //Redo Command
        commandList.add(new ArrayList<String>(Arrays.asList("Redo", RedoCommand.getCommandWord())));

        //Remark Command
        commandList.add(new ArrayList<String>(Arrays.asList("Remark", RemarkCommand.getCommandWord())));

        //Select Command
        commandList.add(new ArrayList<String>(Arrays.asList("Select", SelectCommand.getCommandWord())));

        //Select Event Command
        commandList.add(new ArrayList<String>(Arrays.asList("Select Event", SelectEventCommand.getCommandWord())));

        //Set Alias Command
        commandList.add(new ArrayList<String>(Arrays.asList("Set Alias", SetAliasCommand.getCommandWord())));

        //Switch Command
        commandList.add(new ArrayList<String>(Arrays.asList("Switch", SwitchCommand.getCommandWord())));

        //Transfer Command
        commandList.add(new ArrayList<String>(Arrays.asList("Transfer", TransferCommand.getCommandWord())));

        //Undo Command
        commandList.add(new ArrayList<String>(Arrays.asList("Undo", UndoCommand.getCommandWord())));

        //View Alias Command
        commandList.add(new ArrayList<String>(Arrays.asList("View Alias", ViewAliasCommand.getCommandWord())));

        viewAliases = commandList;
        //@@author
    }

    public ModelManager() {
        this(new AddressBook(), new EventBook(), new UserPrefs(), new Account(), new Config());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void exportAddressBook() throws ParserConfigurationException,
            IOException, SAXException, TransformerException {

        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = userStorage.readAddressBook();

            if (!addressBookOptional.isPresent()) {
                logger.info("File Created : " + userPref.getAddressBookFilePath());
                userStorage.saveAddressBook(new AddressBook());
            }
        } catch (DataConversionException dce) {
            logger.info(dce.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            userStorage.exportAddressBook();
        }
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAccountChanged() {
        raise(new AccountChangedEvent(account));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void orderList(String parameter) throws UnrecognisedParameterException {
        addressBook.orderList(parameter);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = newPerson.getTags();
            newTags.remove(tag);
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
        }
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return addressBook.getGroupList();
    }

    public ArrayList<ArrayList<String>> getCommands() {
        return viewAliases;
    }

    //@@author keloysiusmak
    @Override
    public String getAliasForCommand(String command) {
        AliasSettings aliasSettings = userPref.getAliasSettings();

        if (command.equals(AddCommand.getCommandWord())) {
            return aliasSettings.getAddCommand().getAlias();
        } else if (command.equals(ClearCommand.getCommandWord())) {
            return aliasSettings.getClearCommand().getAlias();
        } else if (command.equals(DeleteCommand.getCommandWord())) {
            return aliasSettings.getDeleteCommand().getAlias();
        } else if (command.equals(EditCommand.getCommandWord())) {
            return aliasSettings.getEditCommand().getAlias();
        } else if (command.equals(ExitCommand.getCommandWord())) {
            return aliasSettings.getExitCommand().getAlias();
        } else if (command.equals(FindCommand.getCommandWord())) {
            return aliasSettings.getFindCommand().getAlias();
        } else if (command.equals(HelpCommand.getCommandWord())) {
            return aliasSettings.getHelpCommand().getAlias();
        } else if (command.equals(HistoryCommand.getCommandWord())) {
            return aliasSettings.getHistoryCommand().getAlias();
        } else if (command.equals(ListCommand.getCommandWord())) {
            return aliasSettings.getListCommand().getAlias();
        } else if (command.equals(OrderCommand.getCommandWord())) {
            return aliasSettings.getOrderCommand().getAlias();
        } else if (command.equals(RedoCommand.getCommandWord())) {
            return aliasSettings.getRedoCommand().getAlias();
        } else if (command.equals(RemarkCommand.getCommandWord())) {
            return aliasSettings.getRemarkCommand().getAlias();
        } else if (command.equals(SelectCommand.getCommandWord())) {
            return aliasSettings.getSelectCommand().getAlias();
        } else if (command.equals(SetAliasCommand.getCommandWord())) {
            return aliasSettings.getSetAliasCommand().getAlias();
        } else if (command.equals(UndoCommand.getCommandWord())) {
            return aliasSettings.getUndoCommand().getAlias();
        } else if (command.equals(ViewAliasCommand.getCommandWord())) {
            return aliasSettings.getViewAliasCommand().getAlias();
        } else if (command.equals(AddEventCommand.getCommandWord())) {
            return aliasSettings.getAddEventCommand().getAlias();
        } else if (command.equals(DeleteEventCommand.getCommandWord())) {
            return aliasSettings.getDeleteEventCommand().getAlias();
        } else if (command.equals(EditEventCommand.getCommandWord())) {
            return aliasSettings.getEditEventCommand().getAlias();
        } else if (command.equals(ListEventCommand.getCommandWord())) {
            return aliasSettings.getListEventCommand().getAlias();
        } else if (command.equals(OrderEventCommand.getCommandWord())) {
            return aliasSettings.getOrderEventCommand().getAlias();
        } else if (command.equals(FindEventCommand.getCommandWord())) {
            return aliasSettings.getFindEventCommand().getAlias();
        } else if (command.equals(SwitchCommand.getCommandWord())) {
            return aliasSettings.getSwitchCommand().getAlias();
        } else if (command.equals(SelectEventCommand.getCommandWord())) {
            return aliasSettings.getSelectEventCommand().getAlias();
        } else if (command.equals(ExportCommand.getCommandWord())) {
            return aliasSettings.getExportCommand().getAlias();
        } else if (command.equals(TransferCommand.getCommandWord())) {
            return aliasSettings.getTransferCommand().getAlias();
        } else if (command.equals(LoginCommand.getCommandWord())) {
            return aliasSettings.getLoginCommand().getAlias();
        } else if (command.equals(LockCommand.getCommandWord())) {
            return aliasSettings.getLockCommand().getAlias();
        } else {
            return "Not Set";
        }
    }

    @Override
    public void setAlias(String commandName, String alias) throws DuplicateAliasException, UnknownCommandException {
        try {
            this.userPref.setAlias(commandName, alias);
        } catch (DuplicateAliasException e) {
            throw e;
        } catch (UnknownCommandException e) {
            throw e;
        }
    }
    //@@author

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(null);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //========================================================================================================

    @Override
    public void resetEventData(ReadOnlyEventBook newData) {
        eventBook.resetData(newData);
        indicateEventBookChanged();
    }

    @Override
    public ReadOnlyEventBook getEventBook() {
        return eventBook;
    }

    @Override
    public void exportEventBook() throws FileNotFoundException, ParserConfigurationException,
            IOException, SAXException, TransformerException {

        try {
            Optional<ReadOnlyEventBook> addressBookOptional = userStorage.readEventBook();

            if (!addressBookOptional.isPresent()) {
                logger.info("File Created : " + userPref.getEventBookFilePath());
                userStorage.saveEventBook(new EventBook());
            }
        } catch (DataConversionException dce) {
            logger.info(dce.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            userStorage.exportEventBook();
        }
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateEventBookChanged() {
        raise(new EventBookChangedEvent(eventBook));
    }

    @Override
    public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        eventBook.removeEvent(target);
        indicateEventBookChanged();
    }

    @Override
    public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        eventBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventBookChanged();
    }

    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);

        eventBook.updateEvent(target, editedEvent);
        indicateEventBookChanged();
    }

    //=========== Filtered Event List Accessors =============================================================
    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    //===================== Account Operations =========================

    @Override
    public void persistUserAccount(ReadOnlyUser user) throws DuplicateUserException {
        account.addUser(user);
        indicateAccountChanged();
    }

    @Override
    public String retrieveSaltFromStorage(String userId) throws UserNotFoundException {
        return account.getSalt(userId);
    }

    @Override
    public User getUserFromIdAndPassword(String userName, String password) throws UserNotFoundException {
        return account.getUserFromIdAndPassword(userName, password);
    }

    @Override
    public void deleteUser(String userName, String saltedPasswordHex) throws UserNotFoundException {
        User user = getUserFromIdAndPassword(userName, saltedPasswordHex);
        account.removeUser(user);
        indicateAccountChanged();
    }

    @Override
    public void setUserStorage(Storage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public void orderEventList(String parameter)
            throws seedu.address.model.event.exceptions.UnrecognisedParameterException {
        eventBook.orderList(parameter);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventBookChanged();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

    //@@author keloysiusmak

    @Override
    public void transferData() throws ConfigMissingException {
        ArrayList<String> fileList = new ArrayList<String>();
        fileList.add(userPref.getDataFilePath());
        fileList.add(config.getUserPrefsFilePath());
        fileList.add(config.DEFAULT_CONFIG_FILE);
        fileList.add("help.txt");

        try {
            FileOutputStream fos = new FileOutputStream("TunedIn.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            addFileIntoZip(zos, fileList);

            zos.closeEntry();
            zos.close();

        } catch (IOException e) {
            throw new ConfigMissingException("Missing file");
        }
    }

    @Override
    public void transferDataWithDefault() throws IOException {
        ArrayList<String> fileList = new ArrayList<String>();
        fileList.add(userPref.getDataFilePath());
        fileList.add(config.getUserPrefsFilePath());
        fileList.add(config.DEFAULT_CONFIG_FILE);
        fileList.add("help.txt");

        try {
            FileOutputStream fos = new FileOutputStream("TunedIn.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            Optional<ReadOnlyAddressBook> addressBookOptional;
            Optional<ReadOnlyAccount> accountOptional;
            Optional<ReadOnlyEventBook> eventBookOptional;

            addressBookOptional = userStorage.readAddressBook();
            accountOptional = userStorage.readAccount();
            eventBookOptional = userStorage.readEventBook();

            if (!addressBookOptional.isPresent()) {
                System.out.println("File Created : " + fileList.get(0));
                userStorage.saveAddressBook(new AddressBook());
            }
            if (!accountOptional.isPresent()) {
                System.out.println("File Created : " + fileList.get(2));
                userStorage.saveAccount(new Account());
            }
            if (!eventBookOptional.isPresent()) {
                System.out.println("File Created : " + fileList.get(1));
                userStorage.saveEventBook(new EventBook());
            }

            addFileIntoZip(zos, fileList);

            zos.closeEntry();
            zos.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataConversionException d) {
            d.printStackTrace();
        }
    }

    /**
     * Adds specified files into ZIP, as well as recursively looks through the data folder, and add everything into
     * the ZIP as well.
     */
    private void addFileIntoZip(ZipOutputStream zos, ArrayList<String> fileList) throws IOException {

        byte[] buffer = new byte[1024];

        for (String file : fileList) {

            System.out.println("File Added Into Zip (With Defaults) : " + file);
            ZipEntry ze = new ZipEntry(file);
            zos.putNextEntry(ze);

            File thisFile = new File(file);

            if (thisFile.isFile()) {
                FileInputStream in = new FileInputStream(file);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
            } else if (thisFile.isDirectory()) {
                String[] newFileList = thisFile.list();
                ArrayList<String> dirFiles = new ArrayList<String>();
                for (String filename : newFileList) {
                    dirFiles.add("data/" + filename);
                }
                addFileIntoZip(zos, dirFiles);
            }
        }
    }

    //@@author quanle1994

    @Override
    public ReadOnlyAccount getAccount() {
        return this.account;
    }

    @Override
    public void deleteEncryptedContacts(String fileName) {
        File file = new File("data/" + fileName + ".encrypted");
        file.delete();
    }

    @Override
    public void releaseEncryptedContacts(String fileName) throws DataConversionException, IOException {
        File file = new File("data/" + fileName + ".encrypted");
        file.delete();
        refreshAddressBook();
    }

    @Override
    public UserPrefs getUserPrefs() {
        return userPref;
    }

    @Override
    public void refreshAddressBook() throws IOException, DataConversionException {
        AddressBook temp = new AddressBook(userStorage.readAddressBook().orElseGet
                (SampleDataUtil::getSampleAddressBook));
        for (ReadOnlyPerson p : temp.getPersonList()) {
            Person newP = new Person(p);
            try {
                addressBook.addPerson(newP);
            } catch (DuplicatePersonException dpe) {
                dpe.getStackTrace();
            }
        }
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void emptyPersonList(ObservableList<ReadOnlyPerson> list) throws PersonNotFoundException {
        for (ReadOnlyPerson p : list) {
            Person newP = new Person(p);
            addressBook.removePerson(newP);
        }
        indicateAddressBookChanged();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getListLength() throws IOException, DataConversionException {
        AddressBook temp = new AddressBook(userStorage.readAddressBook().orElseGet
                (SampleDataUtil::getSampleAddressBook));
        return temp.getPersonList();
    }

    @Override
    public void encrypt(String userId, String pass, boolean emptyFile) throws Exception {
        FileEncryptor.encryptFile(userId, pass, emptyFile);
    }

    @Override
    public void decrypt(String fileName, String pass) throws Exception {
        FileEncryptor.decryptFile(fileName, pass);
    }

    @Override
    public void encryptPublic(boolean isLockCommand) throws CommandException {
        FileEncryptor.encryptPublicFile(isLockCommand);
    }

    @Override
    public void saveToEncryptedFile() {
        SaveToEncryptedFile.save();
    }
}
