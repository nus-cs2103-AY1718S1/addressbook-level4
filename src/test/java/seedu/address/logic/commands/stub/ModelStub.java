package seedu.address.logic.commands.stub;

import static org.junit.Assert.fail;

import java.util.function.Predicate;
import java.util.regex.PatternSyntaxException;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.tag.Tag;

/**
 * The most basic stub used to replace model (that have all of the methods failing) when testing the logic component.
 */
public class ModelStub implements Model {
    @Override
    public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        fail("This method should not be called.");
    }

    @Override
    public void deleteEvent(ReadOnlyEvent event) throws EventNotFoundException {
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
    public void addProperty(String shortName, String fullName, String message, String regex)
            throws DuplicatePropertyException, PatternSyntaxException {
        fail("This method should not be called.");
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
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
        throws DuplicateEventException {
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
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void updateFilteredEventsList(Predicate<ReadOnlyEvent> predicate) {
        fail("This method should not be called.");
    }
    @Override
    public void removeTag(Tag tags) throws DuplicatePersonException, PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public boolean hasTag(Tag tag) {
        fail("This method should not be called.");
        return false;
    }

    @Override
    public void setTagColor(Tag tag, String color) {
        fail("This method should not be called.");
    }
}
