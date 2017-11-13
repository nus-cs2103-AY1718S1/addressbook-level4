package seedu.address.ui;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarSelectionChangedEvent;
import seedu.address.commons.events.ui.ScheduleUpdateEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.event.Event;

//@@author eldriclim

/**
 * An UI component that displays a clickable-Calendar.
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private AnchorPane box;

    private DatePicker datePicker;
    private ObservableList<Event> eventList;

    public CalendarView(ObservableList<Event> eventList) {
        super(FXML);

        this.eventList = eventList;

        initCalendar(this.eventList);
        initListener();

        registerAsAnEventHandler(this);
    }

    /**
     * Initialise the calendar and highlight dates with Event.
     *
     * @param masterEventList
     */
    private void initCalendar(ObservableList<Event> masterEventList) {

        box.getChildren().clear();
        datePicker = new DatePicker(LocalDate.now());

        HashMap<LocalDate, ArrayList<String>> eventsByDate = new HashMap<>();
        for (Event event : masterEventList) {
            LocalDate pointerDay = event.getEventTime().getStart().toLocalDate();
            LocalDate endDay = event.getEventTime().getEnd().toLocalDate().plusDays(1);

            do {
                ArrayList<String> eventsInPointerDay = eventsByDate.computeIfAbsent(pointerDay, k -> new ArrayList<>());
                eventsInPointerDay.add(event.getEventName().toString());

                pointerDay = pointerDay.plusDays(1);
            } while (!pointerDay.isEqual(endDay));

        }

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (eventsByDate.containsKey(item)) {
                            setTooltip(new Tooltip(
                                    StringUtil.multiStringPrint(eventsByDate.get(item), "\n")));

                            setStyle("-fx-background-color: #a7a7a7; -fx-text-fill: #ffffff;");


                        }
                    }
                };
            }
        };


        datePicker.setDayCellFactory(dayCellFactory);


        DatePickerContent calendarView = (DatePickerContent) datePickerSkin.getPopupContent();

        calendarView.minWidthProperty().setValue(box.minWidthProperty().getValue());

        AnchorPane.setTopAnchor(calendarView, 0.0);
        AnchorPane.setLeftAnchor(calendarView, 0.0);
        AnchorPane.setRightAnchor(calendarView, 1.0);
        AnchorPane.setBottomAnchor(calendarView, 1.0);

        box.getChildren().add(calendarView);

    }

    /**
     * Add listener to register users mouse click.
     */
    private void initListener() {

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate selectedDate = datePicker.getValue();
            logger.fine("Selection in calendar: '" + selectedDate + "'");

            raise(new CalendarSelectionChangedEvent(selectedDate));
        });


    }

    @Subscribe
    private void handleScheduleUpdateEvent(ScheduleUpdateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        initCalendar(event.getEvents());
        initListener();
    }
}
