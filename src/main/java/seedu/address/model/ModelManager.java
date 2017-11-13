package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.commands.ImportAnalysis;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyAddressBookException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.AddressBookData;
import seedu.address.storage.ExportCsvFile;
import seedu.address.storage.ExportVCardFile;
import seedu.address.storage.ImportVCardFile;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final RecycleBin recycleBin;
    private FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyPerson> filteredBin;
    private final FilteredList<ReadOnlyPerson> filteredAddresses;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, recycleBin, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.recycleBin = new RecycleBin(recycleBin);
        filteredAddresses = new FilteredList<>(this.addressBook.getPersonList());
        filteredBin = new FilteredList<>(this.recycleBin.getPersonList());
        filteredPersons = filteredAddresses;
    }

    public ModelManager() {
        this(new AddressBook(), new RecycleBin(), new UserPrefs());
    }

    //@@author frozventus
    @Override
    public void resetData(AddressBookData newData) {
        addressBook.resetData(newData.getAddressBook());
        recycleBin.resetData(newData.getRecycleBin());
        indicateAddressBookChanged();
    }

    //@@author stan789
    @Override
    public void executeSort(String sortType) throws EmptyAddressBookException {
        addressBook.executeSort(sortType);
        indicateAddressBookChanged();
    }

    //@@author frozventus-reused
    @Override
    public void executeBinSort(String sortType) throws EmptyAddressBookException {
        recycleBin.executeSort(sortType);
        indicateAddressBookChanged();
    }

    //@@author stan789
    @Override
    public void importFile(Path fileLocation, ImportAnalysis importAnalysis) throws IOException {
        Integer count = 0;
        ImportVCardFile importFile = new ImportVCardFile(fileLocation, importAnalysis);
        ArrayList<Person> person = importFile.getPersonFromFile();
        for (Person p : person) {
            try {
                addPerson(p);
                count++;
            } catch (DuplicatePersonException e) {
                importAnalysis.setDuplicateContacts(true);
            }
        }
        importAnalysis.setNumContacts(count);
    }

    //@@author stan789
    @Override
    public void exportFile(String fileLocation, String extension) throws IOException {
        ObservableList<ReadOnlyPerson> person = getAddressBook().getPersonList();
        if (extension.equals("vcf")) {
            ExportVCardFile exportVCardFile = new ExportVCardFile(fileLocation);
            exportVCardFile.createVCardFile(person);
        } else {
            ExportCsvFile exportCsvFile = new ExportCsvFile(fileLocation);
            exportCsvFile.createCsvFile(person);
        }

    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyAddressBook getRecycleBin() {
        return recycleBin;
    }

    //@@author frozventus
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(new AddressBookData(addressBook, recycleBin)));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException,
                                                                        DuplicatePersonException {
        addressBook.removePerson(target);
        recycleBin.addPerson(target);
        indicateAddressBookChanged();
    }

    //@@author
    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author frozventus
    @Override
    public synchronized void restorePerson(ReadOnlyPerson target) throws PersonNotFoundException,
                                                                         DuplicatePersonException {
        addressBook.addPerson(target);
        recycleBin.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteFromBin(ReadOnlyPerson target) throws PersonNotFoundException {
        recycleBin.removePerson(target);
        indicateAddressBookChanged();
    }

    //@@author
    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

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
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author frozventus
    @Override
    public void setListDisplay() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        this.filteredPersons = filteredAddresses;
    }

    @Override
    public void setBinDisplay() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        this.filteredPersons = filteredBin;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getAddressBookList() {
        return filteredAddresses;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getRecycleBinList() {
        return filteredBin;
    }

    //@@author
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
                && recycleBin.equals(other.recycleBin)
                && filteredPersons.equals(other.filteredPersons);
    }

}
