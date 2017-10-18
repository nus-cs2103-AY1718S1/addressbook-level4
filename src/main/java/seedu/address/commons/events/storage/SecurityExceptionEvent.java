package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates an exception during a file encryption/decryption.
 */
public class SecurityExceptionEvent extends BaseEvent {

    public final Exception exception;

    public SecurityExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    public SecurityExceptionEvent(String message){
        this.exception = new Exception(message);
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}