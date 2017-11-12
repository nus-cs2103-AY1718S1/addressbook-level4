package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@@author Eric
/**
 * A list of appointments of a person
 */
public class AppointmentList {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty appointment list
     */
    public AppointmentList() {

    }

    /**
     * Contructs an appointment list with new appointments
     */
    public AppointmentList(List<Appointment> appointments) {
        requireAllNonNull(appointments);
        sortAppointmentsInChronologicalOrder(appointments);
        internalList.addAll(appointments);

    }

    /**
     * @param appointments list must be sorted
     */
    private void requireAllSorted(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            assert !appointments.get(i + 1).getDate().before(appointments.get(i).getDate());
        }
    }

    /**
     * Sorts all the appointments in the list before adding it to the internal list
     */
    private void sortAppointmentsInChronologicalOrder(List<Appointment> appointment) {
        requireNonNull(appointment);

        appointment.sort((o1, o2) -> {
            if (o1.getDate().toInstant().isBefore(o2.getDate().toInstant())) {
                return -1;
            } else {
                return 1;
            }
        });

        requireAllSorted(appointment);
    }

    /**
     * Returns true if list contains and equivalent appointment
     */
    public boolean contains (Appointment appointment) {
        return internalList.contains(appointment);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentList // instanceof handles nulls
                && this.internalList.equals(((AppointmentList) other).internalList));
    }

    /**
     * Returns all appointments in this list as a list.
     * This List is mutable and change-insulated against the internal list.
     */
    public List<Appointment> toList() {
        return new ArrayList<>(internalList);
    }

    @Override
    public String toString() {
        if (internalList.isEmpty()) {
            return "No appointment set";
        } else {
            return internalList.size() == 1 ? "" + internalList.size() + " appointment set"
                    : "" + internalList.size() + " appointments set";
        }
    }

}
