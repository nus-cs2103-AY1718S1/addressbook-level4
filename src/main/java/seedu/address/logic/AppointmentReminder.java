package seedu.address.logic;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.awt.EventQueue;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Manages appointment reminder
 */
public class AppointmentReminder {
    public AppointmentReminder(Model model) {
        checkAppointment(model);
    }

    /**
     * Constantly check for appointment
     */
    public void checkAppointment(Model model) {
        Set<Appointment> appointments = model.getAllAppointments();
        checkForAppointment(appointments);
    }

    /**
     * Show message
     */
    private void showMessage(String message) {
        EventQueue.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message);
        });
    }

    /**
     * Check for any appointment in the next 60 min
     */
    private void checkForAppointment(Set<Appointment> appointments) {
        LocalDateTime now = LocalDateTime.now();
        for (Appointment appointment : appointments) {
            if (now.until(appointment.getStart(), MINUTES) <= 60) {
                String message = String.format("You have a meeting with %1$s at %2$s",
                        appointment.getPerson().getName(), appointment.toString());
                showMessage(message);
            }
        }
    }
}
