package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.CalendarViewEvent;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Eric
/**
 * The Browser Panel of the App.
 */
public class CalendarWindow extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    @FXML
    private CalendarView calendarView;

    private ObservableList<ReadOnlyPerson> personList;

    public CalendarWindow(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);

        this.personList = personList;

        calendarView = new CalendarView();
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        updateCalendar();
        disableViews();
        registerAsAnEventHandler(this);
    }

    /**
     * Remove clutter from interface
     */
    private void disableViews() {
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showDayPage();
    }

    /**
     * Changes calendar view accordingly
     */
    private void showPage(Character c) {
        switch(c) {
        case ('d'):
            calendarView.showDayPage();
            return;
        case ('w'):
            calendarView.showWeekPage();
            return;
        case ('m'):
            calendarView.showMonthPage();
            return;
        case ('y'):
            calendarView.showYearPage();
            return;
        default:
            //should not reach here
            assert (false);
        }
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }

    @Subscribe
    private void handleNewAppointmentEvent(AddressBookChangedEvent event) {
        personList = event.data.getPersonList();
        Platform.runLater(
                this::updateCalendar
        );

    }

    @Subscribe
    private void handleCalendarViewEvent(CalendarViewEvent event) {
        Character c = event.c;
        Platform.runLater(() -> showPage(c));
    }

    /**
     * Creates a new a calendar with the update information
     */
    private void updateCalendar() {
        setTime();
        CalendarSource calendarSource = new CalendarSource("Appointments");
        int styleNum = 0;
        for (ReadOnlyPerson person : personList) {
            Calendar calendar = getCalendar(styleNum, person);
            calendarSource.getCalendars().add(calendar);
            ArrayList<Entry> entries = getEntries(person);
            styleNum++;
            styleNum = styleNum % 5;
            for (Entry entry : entries) {
                calendar.addEntry(entry);
            }
        }
        calendarView.getCalendarSources().add(calendarSource);
    }

    private Calendar getCalendar(int styleNum, ReadOnlyPerson person) {
        Calendar calendar = new Calendar(person.getName().toString());
        calendar.setStyle(Calendar.Style.getStyle(styleNum));
        calendar.setLookAheadDuration(Duration.ofDays(365));
        return calendar;
    }

    private void setTime() {
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.getCalendarSources().clear();
    }

    private ArrayList<Entry> getEntries(ReadOnlyPerson person) {
        ArrayList<Entry> entries = new ArrayList<>();
        for (Appointment appointment : person.getAppointments()) {
            LocalDateTime ldtstart = LocalDateTime.ofInstant(appointment.getDate().toInstant(),
                    ZoneId.systemDefault());
            LocalDateTime ldtend = LocalDateTime.ofInstant(appointment.getEndDate().toInstant(),
                    ZoneId.systemDefault());

            entries.add(new Entry(appointment.getDescription() + " with " + person.getName(),
                    new Interval(ldtstart, ldtend)));
        }
        return entries;
    }

}
