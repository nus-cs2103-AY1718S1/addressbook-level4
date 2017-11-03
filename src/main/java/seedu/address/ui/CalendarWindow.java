package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
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
    private ArrayList<ReadOnlyPerson> internalList = new ArrayList<>();
    private Calendar calendar;

    public CalendarWindow(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);

        this.personList = personList;
        deepCopy(personList);

        calendarView = new CalendarView();

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");

        calendarView.getCalendarSources().addAll(myCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        calendar = new Calendar("Appointment");

        CalendarSource calendarSource = new CalendarSource("Appointments");

        calendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(calendarSource);

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

        setKeyBindings();
        disableViews();
    }

    private void deepCopy(ObservableList<ReadOnlyPerson> personList) {
        internalList.addAll(personList);
    }

    /**
     * Remove clutter from interface
     */
    private void disableViews() {
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showWeekPage();
    }

    private void setKeyBindings() {

        calendarView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case C:
                        event.consume();
                        showNextPage();
                        break;
                    default:
                }
            }
        });
    }

    /**
     * When user press c, the calendar will shift to the next view
     * Order of shifting: day -> week -> month -> year
     */
    public void showNextPage() {
        if (calendarView.getSelectedPage() == calendarView.getMonthPage()) {
            calendarView.showYearPage();
        } else if (calendarView.getSelectedPage() == calendarView.getDayPage()) {
            calendarView.showWeekPage();
        } else if (calendarView.getSelectedPage() == calendarView.getYearPage()) {
            calendarView.showDayPage();
        } else {
            calendarView.showMonthPage();
        }
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }

    private void setAppointments() {

        if (internalList.size() != personList.size()) {
            removeDeleted();
            deepCopy(personList);
        }

        for (ReadOnlyPerson person : personList) {
            if (person.getAppointment().getDate() == null) {
                removeEntry(person);
                continue;
            }
            LocalDateTime ldt = LocalDateTime.ofInstant(person.getAppointment().getDate().toInstant(),
                    ZoneId.systemDefault());
            LocalDateTime ldt2 = getEndTime(person, ldt);
            Entry entry = new Entry(person.getName().toString());
            entry.setInterval(new Interval(ldt, ldt2));
            removeEntry(person);
            calendar.addEntry(entry);
        }
        deepCopy(personList);
    }

    /**
     * Find the person that is in the internal list and not in the ObservedList and remove the appointment entry
     */
    private void removeDeleted() {
        for (ReadOnlyPerson person : internalList) {
            if (!personList.contains(person)) {
                removeEntry(person);
            }
        }
    }

    private LocalDateTime getEndTime(ReadOnlyPerson person, LocalDateTime ldt) {
        LocalDateTime ldt2;
        if (person.getAppointment().getEndDate() != null) {
            ldt2 = LocalDateTime.ofInstant(person.getAppointment().getEndDate().toInstant(),
                    ZoneId.systemDefault());
        } else {
            //if no end time set, default appointment is one hour
            ldt2 = ldt.plusHours(1);
        }
        return ldt2;
    }

    private void removeEntry(ReadOnlyPerson person) {
        List<Entry<?>> result = calendar.findEntries(person.getName().toString());
        calendar.removeEntries(result);
    }
}
