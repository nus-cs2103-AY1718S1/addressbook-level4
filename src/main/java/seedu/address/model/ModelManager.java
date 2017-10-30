package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.JaroWinklerDistance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.NameContainsKeywordsPredicate;
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

    //@@author vivekscl
    /* JaroWinklerDistance method uses double values ranging from 0 to 1. Set initial value to match very similar
     * names only as setting the value to any value less than or equal to 0 will match the first name in filteredPersons
     */
    private final double initialToleranceValue = 0.5;

    //@@author
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

    /** Raises an event to indicate the model has changed */
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
    public synchronized void addBirthday(Index targetIndex, Birthday toAdd) throws PersonNotFoundException,
            DuplicatePersonException {

        ReadOnlyPerson oldPerson = this.getFilteredPersonList().get(targetIndex.getZeroBased());

        Person newPerson = new Person(oldPerson);

        newPerson.setBirthday(toAdd);

        addressBook.updatePerson(oldPerson, newPerson);
        indicateAddressBookChanged();
    }

    //@@author vivekscl
    /**
     * Removes given tag from the given indexes of the target persons shown in the last person listing.
     */
    @Override
    public synchronized void removeTag(ArrayList<Index> targetIndexes, Tag toRemove) throws PersonNotFoundException,
            DuplicatePersonException {

        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson oldPerson = this.getFilteredPersonList().get(targetIndex);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<Tag>(newPerson.getTags());
            newTags.remove(toRemove);
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
            indicateAddressBookChanged();
        }
    }

    /**
     * Adds given tag to the given indexes of the target persons shown in the last person listing.
     */
    @Override
    public synchronized void addTag(ArrayList<Index> targetIndexes, Tag toAdd) throws PersonNotFoundException,
            DuplicatePersonException {

        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson oldPerson = this.getFilteredPersonList().get(targetIndex);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<Tag>(newPerson.getTags());
            newTags.add(toAdd);
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
            indicateAddressBookChanged();
        }
    }

    //@@author
    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //@@author vivekscl
    @Override
    public String getClosestMatchingName(NameContainsKeywordsPredicate predicate) {

        requireNonNull(predicate);
        ArrayList<String> allNames = getListOfAllFirstAndLastNames(predicate);
        List<String> keywords = predicate.getKeywords();
        return keywords.size() == 1 ? getClosestMatchingNameForOneKeyword(keywords, allNames, initialToleranceValue)
                : getClosestMatchingNameForMultipleKeywords(keywords, allNames, initialToleranceValue);
    }

    /**
     * This helper method gets a list of all the names, separates them and returns a list of first and last names.
     */
    private ArrayList<String> getListOfAllFirstAndLastNames(NameContainsKeywordsPredicate predicate) {

        updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> fullList = getFilteredPersonList();
        ArrayList<String> allNames = fullList.stream().map(p -> p.getName().toString().split(" "))
                .flatMap(Arrays::stream).collect(Collectors.toCollection(ArrayList::new));
        // switches filteredPersonList back from showing all persons to original according to the given predicate
        updateFilteredPersonList(predicate);
        return allNames;
    }

    /**
     * If there is only one keyword given, this helper method gets the closest matching name from that keyword.
     */
    private String getClosestMatchingNameForOneKeyword(List<String> keywords,
                                                       ArrayList<String> allNames, double maxDistance) {

        JaroWinklerDistance currentJaroWinklerDistance = new JaroWinklerDistance();
        String target = keywords.get(0);
        String result = "";
        for (String s : allNames) {
            if (maxDistance < currentJaroWinklerDistance.apply(target, s)) {
                maxDistance = currentJaroWinklerDistance.apply(target, s);
                result = s;
            }
        }
        return result;
    }

    /**
     * If there are multiple keywords given, this helper method gets the closest matching list of names from the
     * keywords and converts them into a readable string.
     */
    private String getClosestMatchingNameForMultipleKeywords(List<String> keywords,
                                                             ArrayList<String> allNames, double maxDistance) {

        JaroWinklerDistance currentJaroWinklerDistance = new JaroWinklerDistance();
        ArrayList<String> result = new ArrayList<String>();
        for (String target : keywords) {
            for (String s : allNames) {
                if (maxDistance < currentJaroWinklerDistance.apply(target, s)) {
                    maxDistance = currentJaroWinklerDistance.apply(target, s);
                    if (!result.contains(s)) {
                        result.add(s);
                    }
                }
            }
        }
        return String.join(" OR ", result);
    }

    @Override
    public Boolean sortPersonByName(ArrayList<ReadOnlyPerson> contactList) {

        if (filteredPersons.size() == 0) {
            return null;
        }
        contactList.addAll(filteredPersons);

        Collections.sort(contactList, Comparator.comparing(p -> p.toString().toLowerCase()));

        if (contactList.equals(filteredPersons)) {
            return false;
        }

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
        return true;
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
