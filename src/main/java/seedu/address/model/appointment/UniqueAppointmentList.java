//@@author namvd2709
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 /**
 * A list of appointments that enforces no nulls and no clash time.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Appointment#equals(Object)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AppointmentList.
     */
    public UniqueAppointmentList() {}

    /**
     * Creates a Unique Appointment List.
     * Enforces no nulls.
     */
    public UniqueAppointmentList(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.addAll(appointments);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the Appointments in this list with those in the argument appointment list.
     */
    public void setAppointments(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.setAll(appointments);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if the list contains an appointment that clashes with the one given.
     */
    public boolean hasClash(Appointment toCheck) {
        requireNonNull(toCheck);
        if (!toCheck.value.equals("")) {
            for (Appointment appointment : internalList) {
                LocalDateTime startA = appointment.getStart();
                LocalDateTime endA = appointment.getEnd();
                LocalDateTime startB = toCheck.getStart();
                LocalDateTime endB = toCheck.getEnd();
                if (startA.isBefore(endB) && endA.isAfter(startB)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a Appointment to the list.
     *
     * @throws DuplicateAppointmentException if the Appointment duplicates of an existing Appointment in the list.
     * @throws ClashAppointmentException if Appointment to add clashes with an existing Appointment in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException, ClashAppointmentException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        } else if (hasClash(toAdd)) {
            throw new ClashAppointmentException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && this.internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
    /**
     * Removes the equivalent person from the list.

     */
    public boolean remove(Appointment toRemove) {
        requireNonNull(toRemove);
        final boolean appointmentFound = internalList.remove(toRemove);
        return appointmentFound;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateAppointmentException extends DuplicateDataException {
        protected DuplicateAppointmentException() {
            super("Operation would result in duplicate appointment");
        }
    }

    /**
     * Signals that an operation would have violated the 'no clash' property of appointments.
     */
    public static class ClashAppointmentException extends CommandException {
        public ClashAppointmentException() {
            super("Operation would result in clashing appointments");
        }
    }
}
