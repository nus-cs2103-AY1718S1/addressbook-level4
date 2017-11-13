package seedu.address.model.person.exceptions;

/**
 * Signals that the person's attendance is already at a maximum (8).
 */
public class PersonMaxAttendanceException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public PersonMaxAttendanceException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public PersonMaxAttendanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
