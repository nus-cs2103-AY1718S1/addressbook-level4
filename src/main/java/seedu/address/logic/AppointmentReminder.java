package seedu.address.logic;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.awt.EventQueue;
import java.time.LocalDateTime;
import java.util.Set;

import javax.swing.JOptionPane;

import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Manages appointment reminder
 */
public class AppointmentReminder {
    public AppointmentReminder(Model model) {
        LocalDateTime now = LocalDateTime.now();
        Set<Appointment> appointments = model.getAllAppointments();
        for (Appointment appointment: appointments) {
            if (now.until(appointment.getStart(), MINUTES) <= 60) {
                showMessage("You have a meeting with %1$s");
            }
        }
    }

    /**
     * Show message
     */
    private void showMessage(String message) {
        EventQueue.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message);
        });
    }
}
