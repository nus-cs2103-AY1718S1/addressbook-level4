package seedu.address.logic.autocomplete.parser;

import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * A default model stub that have all of the methods failing, used for some Autocomplete Parser tests
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
    public List<String> getAllNamesInAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public List<String> getAllPhonesInAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public List<String> getAllEmailsInAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public List<String> getAllAddressesInAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public List<String> getAllTagsInAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public List<String> getAllRemarksInAddressBook() {
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
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        fail("This method should not be called.");
    }

    @Override
    public void removeTag(Tag tag) {
        fail("This method should not be called");
    }

    @Override
    public void removeTag(Index index, Tag tag) {
        fail("This method should not be called");
    }

    @Override
    public void sortFilteredPersonList(Comparator<ReadOnlyPerson> comparator) {
        fail("This method should not be called");
    }
}
