package seedu.address.commons.events.ui;

import java.util.ArrayList;

import seedu.address.commons.events.BaseEvent;

//@@author zameschua
/**
 * Indicates a request for SmsCommand
 */
public class SmsCommandRequestEvent extends BaseEvent {

    private final ArrayList<String> phoneNumbers;

    public SmsCommandRequestEvent(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
