package seedu.address.commons.exceptions;

//@@author zacharytang
/**
 * Signals that there is no internet connection for reading from web APIs
 */
public class NoInternetConnectionException extends Exception {
    public NoInternetConnectionException(String message) {
        super(message);
    }
}
