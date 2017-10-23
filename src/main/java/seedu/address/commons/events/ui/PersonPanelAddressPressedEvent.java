package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates an address in a person panel is pressed
 */
public class PersonPanelAddressPressedEvent extends BaseEvent {

    private String personName;
    private String address;

    public PersonPanelAddressPressedEvent(String personName, String address) {

        this.personName = personName;
        this.address = address;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Get the address from the event
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the name of the person from the event
     */
    public String getPersonName() {
        return personName;
    }
}
