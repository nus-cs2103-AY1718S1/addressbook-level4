package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AddressBookImportEvent;
import seedu.address.model.person.NameComparator;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;


/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
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

    //@@author aver0214
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
    public void filterImportantTag() throws PersonNotFoundException, DuplicatePersonException {
        ArrayList<ReadOnlyPerson> notImportantPersons = new ArrayList<ReadOnlyPerson>();
        ArrayList<ReadOnlyPerson> importantPersons = new ArrayList<ReadOnlyPerson>();

        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);

            Set<Tag> currTag = oldPerson.getTags();
            List<Tag> taglist = currTag.stream().collect(Collectors.toList());

            String keyword = "[important]";

            if (!taglist.stream().anyMatch(tag -> keyword.contains(tag.toString().toLowerCase()))) {
                notImportantPersons.add(oldPerson);
            } else {
                importantPersons.add(oldPerson);
            }
        }

        /**
         * If any tags in addressbook contains the {@code keyword}
         * then proceed to insert the contact(s) in {@code importantPersons} to the top of the addressbook
         */

        if (importantPersons.size() != 0) {
            for (int j = 0; j < notImportantPersons.size(); j++) {
                importantPersons.add(notImportantPersons.get(j));
            }

            /** Clear all of the addressbook contacts. */
            this.addressBook.resetData(new AddressBook());

            for (int s = 0; s < importantPersons.size(); s++) {
                addressBook.addPerson(importantPersons.get(s));
            }

            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            indicateAddressBookChanged();
        }
    }

    @Override
    public void sortAllPersons() throws DuplicatePersonException {
        ArrayList<ReadOnlyPerson> toBeSortedPersonList = new ArrayList<ReadOnlyPerson>();

        toBeSortedPersonList.addAll(addressBook.getPersonList());
        Collections.sort(toBeSortedPersonList, new NameComparator());

        AddressBook newAb = new AddressBook();

        for (int j = 0; j < toBeSortedPersonList.size(); j++) {
            newAb.addPerson(toBeSortedPersonList.get(j));
        }

        this.addressBook.resetData(newAb);

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
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

    /**
     * @param predicate
     * @return the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    //@@author Choony93
    @Override
    public List<ReadOnlyPerson> getPersonListByPredicate(Predicate<ReadOnlyPerson> predicate) {
        FilteredList<ReadOnlyPerson> filteredList = new FilteredList<>(filteredPersons);
        filteredList.setPredicate(predicate);
        return FXCollections.unmodifiableObservableList(filteredList);
    }
    //@@author

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author Choony93
    @Override
    @Subscribe
    public void handleAddressBookImportEvent(AddressBookImportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data added from file-> "
                + event.filePath));
        for (ReadOnlyPerson person : event.importedBook.getPersonList()) {
            if (!addressBook.getPersonList().contains(person)) {
                try {
                    if (addressBook.getPersonList().stream().noneMatch(p -> p.getName().equals(person.getName()))) {
                        addPerson(person);
                    } else {
                        updatePerson(addressBook.getPersonList().stream()
                                .filter(p -> p.getName().equals(person.getName())).findFirst().get(), person);
                    }
                } catch (DuplicatePersonException dpe) {
                    logger.info(LogsCenter.getEventHandlingLogMessage(event, "Person name [" + person.getName()
                            + "] already, not added."));
                } catch (PersonNotFoundException pnfe) {
                    logger.info(LogsCenter.getEventHandlingLogMessage(event, "Person name [" + person.getName()
                            + "] not found, not modified."));
                }
            }
        }
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
                && filteredPersons.equals(other.filteredPersons);
    }

}
