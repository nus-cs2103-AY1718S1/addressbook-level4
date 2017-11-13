package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author newalter
/**
 * An event indicating new information of a person to be learnt by SuggestionHeuristic
 */
public class NewPersonInfoEvent extends BaseEvent {
    private final ReadOnlyPerson person;

    public NewPersonInfoEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return "New Person Info Available";
    }
}
