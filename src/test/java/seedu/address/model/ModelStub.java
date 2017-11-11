package seedu.address.model;

import static org.junit.Assert.fail;

import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.PersonHaveParticipateException;
import seedu.address.model.event.exceptions.PersonNotParticipateException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.HaveParticipateEventException;
import seedu.address.model.person.exceptions.NotParticipateEventException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public void resetData(ReadOnlyAddressBook newAddressBook, ReadOnlyEventList newEventList) {
        fail("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ReadOnlyEventList getEventList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException, DeleteOnCascadeException {
        fail("This method should not be called.");
    }

    @Override
    public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public void addPerson(int position, ReadOnlyPerson person) {
        fail("This method should not be called.");
    }

    @Override
    public void sortPersons() {
        fail("This method should not be called.");
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public Set<Tag> extractNewTag(ReadOnlyPerson person) {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void removeTags(Set<Tag> tagList) {
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
    public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException, DeleteOnCascadeException {
        fail("This method should not be called.");
    }

    @Override
    public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        fail("This method should not be called.");
    }

    @Override
    public void addEvent(int position, ReadOnlyEvent event) {
        fail("This method should not be called.");
    }

    @Override
    public void sortEvents() {
        fail("This method should not be called.");
    }

    @Override
    public void quitEvent(Person person, Event event)
            throws PersonNotParticipateException, NotParticipateEventException {
        fail("This method should not be called.");
    }

    @Override
    public void joinEvent(Person person, Event event)
            throws PersonHaveParticipateException, HaveParticipateEventException {
        fail("This method should not be called.");
    }

    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
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
}
