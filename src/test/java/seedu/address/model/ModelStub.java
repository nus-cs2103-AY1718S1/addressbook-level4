package seedu.address.model;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * A default model stub that have all of the methods failing.
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
    public void deletePerson(ReadOnlyPerson... target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public void addGroup(ReadOnlyGroup group) throws DuplicateGroupException {
        fail("This method should not be called.");
    }

    @Override
    public void deleteGroup(ReadOnlyGroup group) throws GroupNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public void pinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public void unpinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public void setTagColour(String tag, String colour) throws IllegalValueException {
        fail("This method should not be called.");
    }

    @Override
    public HashMap<Tag, String> getTagColours() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ObservableList<ReadOnlyGroup> getGroupList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        fail("This method should not be called.");
    }

    @Override
    public void updateFilteredGroupList(Predicate<ReadOnlyGroup> predicate) {
        fail("This method should not be called.");
    }

    public void sort(String sortType) throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    public Predicate<ReadOnlyPerson> getPredicateForTags(String arg) {
        fail("This method should not be called.");
        return null;
    }
}
