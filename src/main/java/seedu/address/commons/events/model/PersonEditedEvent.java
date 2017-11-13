package seedu.address.commons.events.model;

import java.util.Arrays;
import java.util.Objects;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author syy94

/**
 * Represents that a Person was edited successfully.
 */
public class PersonEditedEvent extends BaseEvent {
    private ReadOnlyPerson old;
    private ReadOnlyPerson newPerson;

    public PersonEditedEvent(ReadOnlyPerson old, ReadOnlyPerson newPerson) {
        assert assertIfNonNull(old, newPerson);
        this.old = old;
        this.newPerson = newPerson;
    }

    public ReadOnlyPerson getOldPerson() {
        return old;
    }

    public ReadOnlyPerson getNewPerson() {
        return newPerson;
    }

    private boolean assertIfNonNull(ReadOnlyPerson... persons) {
        return Arrays.stream(persons).allMatch(Objects::nonNull);
    }

    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     * <p>
     * For example, the event manager post method will call any posted event's toString and print it in the console.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
