package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nbriannl
/** Indicates a person's address as a map is to be displayed*/
public class PersonAddressDisplayDirectionsEvent extends BaseEvent {

    public final ReadOnlyPerson person;
    public final Address address;
    public final int targetIndex;

    public PersonAddressDisplayDirectionsEvent(ReadOnlyPerson person, Address address, int targetIndex) {
        this.person = person;
        this.address = address;
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return "Displaying location of " + person.getName() + ": " + person.getAddress()
                + " from " + address.toString();
    }
}
