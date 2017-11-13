package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author zacharytang
/**
 * Represents a request to display timetables in the UI
 */
public class TimetableDisplayEvent extends BaseEvent {

    public final List<ReadOnlyPerson> personsToDisplay;

    public TimetableDisplayEvent(List<ReadOnlyPerson> personList) {
        this.personsToDisplay = personList;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();

        msg.append("Timetables displayed for the selected people: ");

        for (ReadOnlyPerson person : personsToDisplay) {
            msg.append("[");
            msg.append(person.getName().toString());
            msg.append("] ");
        }

        return msg.toString();
    }
}
