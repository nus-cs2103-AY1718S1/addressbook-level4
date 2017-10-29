package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
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
    private Calendar calendar;
    public CalendarWindow(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);

        this.personList = personList;
        calendarView = new CalendarView();

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");

        calendarView.getCalendarSources().addAll(myCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        calendar = new Calendar("Appointment");

        CalendarSource calendarSource = new CalendarSource("Appointments");

        calendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(calendarSource);
        //Disabling views to make the calendar more simplistic
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPageSwitcher(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowToolBar(false);
        calendarView.showWeekPage();
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                        setAppointments();
                    });

                    try {
                        // update every 1 second
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }

    public void setAppointments() {
        for (ReadOnlyPerson person : personList) {
            if (person.getAppointment().getDate() == null) {
                continue;
            }
            LocalDateTime ldt = LocalDateTime.ofInstant(person.getAppointment().getDate().toInstant(),
                    ZoneId.systemDefault());
            Entry entry = new Entry(person.getName().toString());
            entry.setInterval(new Interval(ldt, ldt.plusHours(1)));
            List<Entry<?>> result = calendar.findEntries(person.getName().toString());
            calendar.removeEntries(result);
            calendar.addEntry(entry);
        }
    }

}
