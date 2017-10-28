package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;
import seedu.address.model.person.Appointment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

\
/**
 * The Browser Panel of the App.
 */
public class CalendarView extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    @FXML
    private BorderPane root;

    private VCalendar vCalendar;
    public CalendarView() {
        super(FXML);

        CalendarView calendarView = new Ca
        vCalendar = new VCalendar();
        ICalendarAgenda agenda = new ICalendarAgenda(vCalendar);

        root = new BorderPane();
        root.setCenter(agenda);

        setAppointment(new Appointment("john", Calendar.getInstance()));
    }

    public BorderPane getRoot() {
        return this.root;
    }

    public void setAppointment(Appointment appointment) {
        final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatter("yyyy-MM-dd'T'HH:mm:ss.SSS zzz");
        LocalDate date = appointment.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String publishMessage = "BEGIN:VCALENDAR" + System.lineSeparator()
                + "METHOD:PUBLISH" + System.lineSeparator()
                + "BEGIN:VEVENT" + System.lineSeparator()
                + "ORGANIZER:mailto:a@example.com" + System.lineSeparator()
                + "DTSTART;TZID=Asia/Singapore:"
                + date.format() + System.lineSeparator()
                + "DTEND;TZID=Asia/Singapore:20171027T220000Z" + System.lineSeparator()
                + "SUMMARY:Friday meeting with Joe" + System.lineSeparator()
                + "UID:0981234-1234234-23@example.com" + System.lineSeparator()
                + "END:VEVENT" + System.lineSeparator()
                + "END:VCALENDAR";
        vCalendar.processITIPMessage(publishMessage);

    }

}
