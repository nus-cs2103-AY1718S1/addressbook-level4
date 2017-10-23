package seedu.address.model.association;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.association.exceptions.ParticipationNotFoundException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

public class UniqueParticipantList {

    private final ObservableList<PersonEventPair> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list has stored a same participation information.
     */
    public boolean contains(PersonEventPair toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Add a participation (person, event) into the list.
     */
    public void add(PersonEventPair participation) {

    }

    public void delete(PersonEventPair participation) throws ParticipationNotFoundException {
        requireNonNull(participation);
        if (!contains(participation)) {
            throw new ParticipationNotFoundException();
        }

        internalList.remove(participation);
    }


}
