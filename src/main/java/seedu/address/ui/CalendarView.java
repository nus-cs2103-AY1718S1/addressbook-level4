package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

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
    private final LocalTime firstSlotStart = LocalTime.of(7, 0);
    private final Duration slotLength = Duration.ofMinutes(30);
    private final LocalTime lastSlotStart = LocalTime.of(20, 59);

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    @FXML
    private StackPane calendarViewPlaceHolder;

    public CalendarView() {
        super(FXML);

        GridPane calendarView = new GridPane();

        initSlots(calendarView);
        initDateHeader(calendarView);
        initDateTimeHeader(calendarView);

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
            view.setMinSize(80, 20);
            view.setPrefSize(120, 20);
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
}
