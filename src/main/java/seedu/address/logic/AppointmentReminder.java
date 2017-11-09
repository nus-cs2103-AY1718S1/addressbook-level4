//@@author namvd2709
package seedu.address.logic;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.awt.EventQueue;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
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
        showAppointmentMessage(appointments);
        Timer timer = new Timer();
        final int minute = 60000;
        timer.schedule(new TimerTask() {
            public void run() {
                showAppointmentMessage(appointments);
            }
        }, minute, minute);
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
    private void showAppointmentMessage(Set<Appointment> appointments) {
        LocalDateTime now = LocalDateTime.now();
        Set<Appointment> toRemove = new HashSet<>();
        for (Iterator<Appointment> appointmentIterator = appointments.iterator(); appointmentIterator.hasNext(); ) {
            Appointment appointment = appointmentIterator.next();
            if (now.until(appointment.getStart(), MINUTES) <= 60) {
                String message = String.format("You have a meeting with %1$s at %2$s",
                        appointment.getPerson().getName(), appointment.toString());
                showMessage(message);
                appointmentIterator.remove();
            }
        }
    }
}
