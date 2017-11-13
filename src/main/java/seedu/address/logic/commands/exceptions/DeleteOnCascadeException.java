package seedu.address.logic.commands.exceptions;

/**
 * A signal that deleting a person or an event fails because it connects to event or person, respectively
 */
public class DeleteOnCascadeException extends Exception {
}
