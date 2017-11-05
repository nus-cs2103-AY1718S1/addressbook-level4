package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed*/
public class NewAppointmentEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public NewAppointmentEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "New Appointment added";
    }
}
