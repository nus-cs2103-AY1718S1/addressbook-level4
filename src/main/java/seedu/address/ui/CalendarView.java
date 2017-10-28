package seedu.address.ui;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * The calendar view to show all the scheduled events.
 */

public class CalendarView extends UiPart<Region> {

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass
            .getPseudoClass("selected");
    private static final String FXML = "CalendarView.fxml";
    private static Supplier<LocalDate> today = LocalDate::now;
    private static Supplier<LocalDate> startOfWeek = () -> today.get()
            .minusDays(today.get().getDayOfWeek().getValue() - 1);
    private static Supplier<LocalDate> endOfWeek = () -> startOfWeek.get()
            .plusDays(6);
    private static int SLOT_LENGTH = 30;
    private final LocalTime firstSlotStart = LocalTime.of(7, 0);
    private final Duration slotLength = Duration.ofMinutes(SLOT_LENGTH);
    private final LocalTime lastSlotStart = LocalTime.of(23, 59);

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    private final GridPane calendarView;

    private final HashMap<ReadOnlyEvent, Node> addedEvents = new HashMap<>();

    private final Logger logger = LogsCenter.getLogger(CalendarView.class);

    @FXML
    private StackPane calendarViewPlaceHolder;

    public CalendarView(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);

        calendarView = new GridPane();

        initSlots(calendarView);
        initDateHeader(calendarView);
        initDateTimeHeader(calendarView);

        initEvents(calendarView, eventList, null);

        ScrollPane scrollableCalendar = new ScrollPane(calendarView);

        calendarViewPlaceHolder.getChildren().add(scrollableCalendar);

        registerAsAnEventHandler(this);
    }

    /**
     * Registers handlers on the time slot to manage selecting a range of
     * slots in the grid.
     *
     * @param timeSlot    selected
     * @param mouseAnchor where the mouse is at
     */

    private void registerDragHandlers(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {
        timeSlot.getView().setOnDragDetected(event -> {
            mouseAnchor.set(timeSlot);
            timeSlot.getView().startFullDrag();
            timeSlots.forEach(slot ->
                    slot.setSelected(slot == timeSlot));
        });

        timeSlot.getView().setOnMouseDragEntered(event -> {
            TimeSlot startSlot = mouseAnchor.get();
            timeSlots.forEach(slot ->
                    slot.setSelected(isBetween(slot, startSlot, timeSlot)));
        });

        timeSlot.getView().setOnMouseReleased(event -> mouseAnchor.set(null));
    }

    /**
     * Initialize all timeslots
     *
     * @param calendarView gridPane of the calendar
     */
    private void initSlots(GridPane calendarView) {
        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

        for (LocalDate date = startOfWeek.get(); !date.isAfter(endOfWeek.get());
             date = date.plusDays(1)) {
            int slotIndex = 1;

            for (LocalDateTime startTime = date.atTime(firstSlotStart);
                 !startTime.isAfter(date.atTime(lastSlotStart));
                 startTime = startTime.plus(slotLength)) {


                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                timeSlots.add(timeSlot);

                registerDragHandlers(timeSlot, mouseAnchor);

                calendarView.add(timeSlot.getView(), timeSlot.getDayOfWeek().getValue(), slotIndex);
                slotIndex++;
            }
        }
    }

    /**
     * Initialize header dates (horizontal axis)
     *
     * @param calendarView gridPane of the calendar
     */
    private void initDateHeader(GridPane calendarView) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");

        for (LocalDate date = startOfWeek.get(); !date.isAfter(endOfWeek.get());
             date = date.plusDays(1)) {
            Label label = new Label(date.format(dayFormatter));
            label.setPadding(new Insets(1));
            label.setTextAlignment(TextAlignment.CENTER);
            GridPane.setHalignment(label, HPos.CENTER);
            calendarView.add(label, date.getDayOfWeek().getValue(), 0);
        }
    }

    /**
     * Initialize header time (vertical axis)
     *
     * @param calendarView gridPane of the calendar
     */
    private void initDateTimeHeader(GridPane calendarView) {
        int slotIndex = 1;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (LocalDateTime startTime = today.get().atTime(firstSlotStart);
             !startTime.isAfter(today.get().atTime(lastSlotStart));
             startTime = startTime.plus(slotLength)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            GridPane.setHalignment(label, HPos.RIGHT);
            calendarView.add(label, 0, slotIndex);
            slotIndex++;
        }
    }

    /**
     * Select events that take place in the current week and place them on the calendar accordingly.
     *
     * @param calendarView gridPane of the calendar
     * @param eventList list of all events
     */
    private void initEvents(GridPane calendarView, ObservableList<ReadOnlyEvent> eventList, ReadOnlyEvent
            lastChangedEvent) {
        String endOfThisWeek = CalendarView.endOfWeek.get().toString();
        String[] tokens = endOfThisWeek.split("-");
        try {
            Timeslot endOfWeek = new Timeslot(tokens[2] + "/" + tokens[1] + "/" + tokens[0] + " " + "2358-2359");
            ObservableList<ReadOnlyEvent> eventsThisWeek = eventList.stream().filter(event -> event.happensBefore
                    (endOfWeek)).collect(Collectors.toCollection(FXCollections::observableArrayList));

            //In cases of updating events, remove the existing node first before adding a new one
            if (calendarView.getChildren().contains(addedEvents.get(lastChangedEvent))) {
                calendarView.getChildren().remove(addedEvents.get(lastChangedEvent));
            }

            //Iteratively add the events
            for (ReadOnlyEvent event:eventsThisWeek) {
                LocalDate date = event.getDate().toLocalDate();

                Label eventTitle = new Label();
                eventTitle.setWrapText(true);
                eventTitle.setStyle( "-fx-alignment: CENTER; -fx-font-size: 12pt; -fx-text-style: bold;");
                eventTitle.setText(event.getTitle().toString() + "\n");
                StackPane eventPane = new StackPane();
                eventPane.setMaxWidth(150.0);
                eventPane.setStyle( "-fx-background-color: #FEDFE1; -fx-alignment: CENTER; -fx-border-color: white");
                eventPane.getChildren().addAll(eventTitle);

                int columnIndex = date.getDayOfWeek().getValue();
                int rowIndex = (int) MINUTES.between(firstSlotStart, event.getStartTime()) / SLOT_LENGTH + 1;
                int rowSpan = (((int) event.getDuration().toMinutes() + SLOT_LENGTH - 1) )/ SLOT_LENGTH;

                logger.info("Event " + event.getTiming().toString() + " with duration " + event.getDuration().toMinutes() +
                        " " +
                        "row span = " + rowSpan);

                calendarView.add(eventPane, columnIndex, rowIndex, 1, rowSpan);
                addedEvents.put(event, eventPane);
            }
        } catch (IllegalValueException ive) {
            throw new RuntimeException("Calendar is not correctly initialized.");
        }
    }

    /**
     * Upon receiving an AddressBookChangedEvent, update the event list accordingly.
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        initEvents(calendarView, abce.data.getEventList(), abce.data.getLastChangedEvent());
    }

    // Utility method that checks if testSlot is "between" startSlot and endSlot
    // Here "between" means in the visual sense in the grid: i.e. does the time slot
    // lie in the smallest rectangle in the grid containing startSlot and endSlot
    //
    // Note that start slot may be "after" end slot (either in terms of day, or time, or both).

    // The strategy is to test if the day for testSlot is between that for startSlot and endSlot,
    // and to test if the time for testSlot is between that for startSlot and endSlot,
    // and return true if and only if both of those hold.

    // Finally we note that x <= y <= z or z <= y <= x if and only if (y-x)*(z-y) >= 0.

    /**
     * Check whether a time slot is between the other two time slots
     *
     * @param testSlot  slot used for testing
     * @param startSlot starting time slot
     * @param endSlot   ending time slot
     */
    private boolean isBetween(TimeSlot testSlot, TimeSlot startSlot, TimeSlot endSlot) {

        boolean daysBetween = testSlot.getDayOfWeek().compareTo(startSlot.getDayOfWeek())
                * endSlot.getDayOfWeek().compareTo(testSlot.getDayOfWeek())
                >= 0;

        boolean timesBetween = testSlot.getTime().compareTo(startSlot.getTime())
                * endSlot.getTime().compareTo(testSlot.getTime()) >= 0;

        return daysBetween && timesBetween;
    }

    /**
     * Class representing a time interval, or "Time Slot", along with a view.
     * View is just represented by a region with minimum size, and style class.
     * Has a selected property just to represent selection.
     */

    public static class TimeSlot {

        private final LocalDateTime start;
        private final Duration duration;
        private final Region view;

        private final BooleanProperty selected = new SimpleBooleanProperty();

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;
            this.duration = duration;

            view = new Region();
            view.setMinSize(80, 30);
            view.setPrefSize(120, 30);
            view.setMaxSize(150,40);
            view.getStyleClass().add("time-slot");

            selectedProperty().addListener((obs, wasSelected, isSelected) ->
                    view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));

        }

        public final BooleanProperty selectedProperty() {
            return selected;
        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalTime getTime() {
            return start.toLocalTime();
        }

        public DayOfWeek getDayOfWeek() {
            return start.getDayOfWeek();
        }

        public Duration getDuration() {
            return duration;
        }

        public Node getView() {
            return view;
        }

    }

    /**
     * Class representing the coordinates on a gridpane.
     */
    private class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof Coordinate)
                    && this.x == ((Coordinate) other).getX()
                    && this.y == ((Coordinate) other).getY();
        }
    }
}
