//@@author shuang-yang

package seedu.address.ui;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
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
    private static final int SLOT_LENGTH = 30;
    private static final int LAST_PAGE = -1;
    private static final int NEXT_PAGE = 1;

    private static final DataFormat paneFormat = new DataFormat("DraggingPane");

    private static LocalDate currentToday;
    private static LocalDate currentStartOfWeek;
    private static LocalDate currentEndOfWeek;
    private static Timeslot startOfWeekTimeslot;
    private static Timeslot endOfWeekTimeslot;

    private final LocalTime firstSlotStart = LocalTime.of(7, 0);
    private final Duration slotLength = Duration.ofMinutes(SLOT_LENGTH);
    private final LocalTime lastSlotStart = LocalTime.of(23, 59);
    private final int lastSlotIndex = (int) MINUTES.between(firstSlotStart, lastSlotStart) / SLOT_LENGTH + 1;

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    private final TreeMap<ReadOnlyEvent, Node> addedEvents = new TreeMap<>();

    private final Logger logger = LogsCenter.getLogger(CalendarView.class);
    private final Logic logic;
    private ObservableList<ReadOnlyEvent> eventList;

    //Used to store details of dragging event
    private StackPane draggingPane;
    private int draggingPaneSpan;
    private ReadOnlyEvent draggedEvent;

    @FXML
    private VBox calendarViewPlaceHolder;

    @FXML
    private GridPane calendarView;

    @FXML
    private GridPane headers;

    public CalendarView(ObservableList<ReadOnlyEvent> eventList, Logic logic) {
        super(FXML);

        this.logic = logic;

        init(eventList);

        registerAsAnEventHandler(this);
    }


    /**
     * Initialize all values and views.
     *
     * @param eventList all existing events list
     */
    private void init(ObservableList<ReadOnlyEvent> eventList) {
        currentToday = today.get();
        currentStartOfWeek = startOfWeek.get();
        currentEndOfWeek = endOfWeek.get();

        initSlots(calendarView);
        initPageTurnerButtons(headers);
        initDateHeader(headers);
        initDateTimeHeader(calendarView);
        initEvents(calendarView, eventList, null);
        initScrollPanes();
    }


    /**
     * Initialize all timeslots
     *
     * @param calendarView gridPane of the calendar
     */
    private void initSlots(GridPane calendarView) {
        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

        for (LocalDate date = currentStartOfWeek; !date.isAfter(currentEndOfWeek);
             date = date.plusDays(1)) {
            int slotIndex = 1;

            for (LocalDateTime startTime = date.atTime(firstSlotStart);
                 !startTime.isAfter(date.atTime(lastSlotStart));
                 startTime = startTime.plus(slotLength)) {

                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                timeSlots.add(timeSlot);
                registerSelectionHandler(timeSlot, mouseAnchor);

                int rowIndex = (int) MINUTES.between(firstSlotStart, timeSlot.getStartDateTime()) / SLOT_LENGTH + 1;
                addDropHandling(timeSlot, rowIndex);

                calendarView.add(timeSlot.getView(), timeSlot.getDayOfWeek().getValue(), slotIndex);
                slotIndex++;
            }
        }
    }

    /**
     * Initialize the page-turn buttons
     *
     * @param headers gridPane of the calendar header
     */

    private void initPageTurnerButtons(GridPane headers) {
        Button lastPage = new Button();
        Button nextPage = new Button();
        lastPage.setPrefSize(80, 30);
        lastPage.setMinSize(80, 30);
        nextPage.setPrefSize(60, 30);
        nextPage.setMinSize(40, 30);
        lastPage.setStyle("-fx-background-color: transparent");
        nextPage.setStyle("-fx-background-color: transparent");
        lastPage.setGraphic(new ImageView(new Image(getClass().getResource("/images/lastPage.png").toExternalForm
                (), 25, 25, true, true)));
        nextPage.setGraphic(new ImageView(new Image(getClass().getResource("/images/nextPage.png").toExternalForm
                (), 25, 25, true, true)));

        lastPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentStartOfWeek = currentStartOfWeek.minusDays(7);
                currentEndOfWeek = currentEndOfWeek.minusDays(7);
                initSlots(calendarView);
                initDateHeader(headers);
                initEvents(calendarView, eventList, null);
            }
        });
        nextPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentStartOfWeek = currentStartOfWeek.plusDays(7);
                currentEndOfWeek = currentEndOfWeek.plusDays(7);
                initSlots(calendarView);
                initDateHeader(headers);
                initEvents(calendarView, eventList, null);
            }
        });

        headers.add(lastPage, 0, 0);
        headers.add(nextPage, 8, 0);
    }



    /**
     * Initialize header dates (horizontal axis)
     *
     * @param headers gridPane of the calendar header
     */
    private void initDateHeader(GridPane headers) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");
        StackPane[] headerPanes = new StackPane[8];

        for (LocalDate date = currentStartOfWeek; !date.isAfter(currentEndOfWeek);
             date = date.plusDays(1)) {
            int columnIndex = date.getDayOfWeek().getValue();

            Label label = new Label(date.format(dayFormatter));
            label.setPadding(new Insets(1));
            label.setTextAlignment(TextAlignment.CENTER);
            StackPane pane = new StackPane();
            pane.setPrefSize(135, 50);
            pane.setMinSize(100, 50);
            pane.setStyle("-fx-background-color:#FEDFE1");
            pane.getChildren().add(label);
            GridPane.setHalignment(label, HPos.CENTER);
            if (headers.getChildren().remove(headerPanes[columnIndex])) {
            }
            headers.add(pane, columnIndex, 0);
            headerPanes[columnIndex] = pane;
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
        for (LocalDateTime startTime = currentToday.atTime(firstSlotStart);
             !startTime.isAfter(currentToday.atTime(lastSlotStart));
             startTime = startTime.plus(slotLength)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            StackPane pane = new StackPane(label);
            pane.setPrefSize(80, 20);
            GridPane.setHalignment(label, HPos.RIGHT);
            calendarView.add(pane, 0, slotIndex);
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
        this.eventList = eventList;
        initStartEndOfWeek();
        ObservableList<ReadOnlyEvent> eventsThisWeek = extractEvents(eventList);

        clearCurrentPanes(calendarView);

        //Iteratively add the events to the calendar view
        for (ReadOnlyEvent event:eventsThisWeek) {
            if (!addedEvents.containsKey(event)) {
                StackPane eventPane = createPane(event);
                addEventPaneToCalendarView(calendarView, event, eventPane);
            }
        }

    }

    /**
     * Initialize scroll panes to wrap around calendar and headers to enable scrolling.
     */
    private void initScrollPanes() {
        ScrollPane scrollableCalendar = new ScrollPane(calendarView);
        ScrollPane scrollableHeader = new ScrollPane(headers);
        scrollableHeader.setMinHeight(80);

        calendarViewPlaceHolder.getChildren().addAll(scrollableHeader, scrollableCalendar);
        scrollableCalendar.hvalueProperty().bindBidirectional(scrollableHeader.hvalueProperty());
    }

    /**
     * Updates information of last changed event
     *
     * @param calendarView gridPane of the calendar
     * @param eventList list of all events
     * @param lastChangedEvent event edited
     * @param newlyAddedEvent new event added
     *
     */
    private void updateEvents(GridPane calendarView, ObservableList<ReadOnlyEvent> eventList, ReadOnlyEvent
            lastChangedEvent, ReadOnlyEvent newlyAddedEvent) {
        this.eventList = eventList;

        // Remove existing pane that corresponds to last changed event
        if (lastChangedEvent != null
                && lastChangedEvent.happensAfter(startOfWeekTimeslot)
                && lastChangedEvent.happensBefore(endOfWeekTimeslot)) {
            removeDuplicatedPane(calendarView, lastChangedEvent);
        }

        // Add pane for the newly added event
        if (newlyAddedEvent != null
                && newlyAddedEvent.happensAfter(startOfWeekTimeslot)
                && newlyAddedEvent.happensBefore(endOfWeekTimeslot)) {
            StackPane eventPane = createPane(newlyAddedEvent);
            addEventPaneToCalendarView(calendarView, newlyAddedEvent, eventPane);
        }
    }

    /**
     * Initialize the current start and end of week
     */
    private void initStartEndOfWeek() {
        String startOfThisWeek = CalendarView.currentStartOfWeek.toString();
        String endOfThisWeek = CalendarView.currentEndOfWeek.toString();
        String[] startofWeekTokens = startOfThisWeek.split("-");
        String[] endofWeekTokens = endOfThisWeek.split("-");

        try {
            startOfWeekTimeslot = new Timeslot(startofWeekTokens[2] + "/" + startofWeekTokens[1] + "/"
                    + startofWeekTokens[0] + " " + "0658-0659");
            endOfWeekTimeslot = new Timeslot(endofWeekTokens[2] + "/" + endofWeekTokens[1] + "/"
                    + endofWeekTokens[0] + " " + "2358-2359");
        } catch (IllegalValueException ive) {
            throw new RuntimeException("Start and end of current week are not correctly initialized.");
        }
    }


    /**
     * Extract events that are scheduled in the current week.
     * @param eventList a list of all the events in the address book
     * @return an ObservableList of events scheduled in the current week
     */
    private ObservableList<ReadOnlyEvent> extractEvents(ObservableList<ReadOnlyEvent> eventList) {
        return eventList.stream().filter(event -> event.happensBefore(endOfWeekTimeslot)).filter(event -> event
                .happensAfter(startOfWeekTimeslot)).collect(Collectors.toCollection
                (FXCollections::observableArrayList));
    }

    /**
     * When updating events, remove the existing pane first before adding a new one.
     * @param calendarView where events are added
     * @param lastChangedEvent event to be removed (if found)
     */

    private void removeDuplicatedPane(GridPane calendarView, ReadOnlyEvent lastChangedEvent) {
        if (calendarView.getChildren().remove(addedEvents.get(lastChangedEvent))) {
            addedEvents.remove(lastChangedEvent);
        }
    }

    /**
     * When initializing, remove the existing panes before adding new ones.
     * @param calendarView where events are added
     */

    private void clearCurrentPanes(GridPane calendarView) {
        calendarView.getChildren().removeAll(addedEvents.values());
        addedEvents.clear();
    }

    /**
     * Draw a stack pane that displays the information of a given event.
     * @param event Event to be shown.
     * @return the stack pane created
     */
    private StackPane createPane(ReadOnlyEvent event) {
        //Create the label
        Label eventTitle = new Label();
        eventTitle.setWrapText(true);
        eventTitle.setStyle("-fx-alignment: CENTER; -fx-font-size: 10pt; -fx-text-style: bold;");
        eventTitle.setText(event.getTitle().toString() + "\n");

        //Create the pane
        StackPane eventPane = new StackPane();
        eventPane.setMaxWidth(135.0);
        eventPane.setStyle("-fx-background-color: #81C7D4; -fx-alignment: CENTER; "
                + "-fx-border-color: " + "white");

        //Add listener to mouse-click event to show detail of the event
        eventPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    logic.execute("eventfind " + event.getTitle().toString());
                } catch (CommandException | ParseException e) {
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            }
        });

        //Add the label to the pane
        eventPane.getChildren().addAll(eventTitle);

        return eventPane;
    }

    /**
     * Add a stack pane displaying event details to the calendar view.
     * @param calendarView where events will be added
     * @param event the event to be displayed
     * @param eventPane the stack pane to be edded
     */
    private void addEventPaneToCalendarView(GridPane calendarView, ReadOnlyEvent event, StackPane eventPane) {
        LocalDate date = event.getDate().toLocalDate();
        int columnIndex = date.getDayOfWeek().getValue();
        int rowIndex = (int) MINUTES.between(firstSlotStart, event.getStartTime()) / SLOT_LENGTH + 1;
        int rowSpan = (((int) event.getDuration().toMinutes() + SLOT_LENGTH - 1)) / SLOT_LENGTH;

        //Add event pane to the corresponding slots on Calendar
        calendarView.add(eventPane, columnIndex, rowIndex, 1, rowSpan);

        //Add on drag listener
        registerDragHandler(eventPane, event, rowSpan);

        //Store events that have been added for future reference
        addedEvents.put(event, eventPane);

    }

    /**
     * Registers handlers on the time slot to manage selecting a range of
     * slots in the grid.
     *
     * @param timeSlot    selected
     * @param mouseAnchor where the mouse is at
     */

    private void registerSelectionHandler(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {
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
     * Registers dragging handlers on the time slots to enable changing event scheduling through drag-and-drop.
     *
     * @param eventPane event pane to be dragged
     * @param event event being re-scheduled
     * @param rowSpan span of the event pane in the gridpane
     */
    private void registerDragHandler(StackPane eventPane, ReadOnlyEvent event, int rowSpan) {
        eventPane.setOnDragDetected(e -> {
            Dragboard dragBoard = eventPane.startDragAndDrop(TransferMode.MOVE);
            dragBoard.setDragView(eventPane.snapshot(null, null));
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(paneFormat, " ");
            dragBoard.setContent(clipboardContent);

            //Register the detail of the dragged event pane
            draggingPane = eventPane;
            draggedEvent = event;
            draggingPaneSpan = rowSpan;
        });
    }

    /**
     * Registers dropping handlers on the time slots to enable changing event scheduling through drag-and-drop.
     *
     * @param timeSlot Starting TimeSlot to drop the event at
     * @param rowIndex row of the dropped TimeSlot
     */
    private void addDropHandling(TimeSlot timeSlot, int rowIndex) {
        StackPane pane = timeSlot.getView();
        pane.setOnDragOver(e -> {
            Dragboard dragBoard = e.getDragboard();
            if (dragBoard.hasContent(paneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        pane.setOnDragDropped(e -> {
            Dragboard dragBoard = e.getDragboard();
            if (dragBoard.hasContent(paneFormat) && rowIndex + draggingPaneSpan <= lastSlotIndex) {
                try {
                    int eventIndex = eventList.indexOf(draggedEvent) + 1;
                    String date = timeSlot.getDateAsString();
                    String startTime = timeSlot.getStartTimeAsString();
                    String endTime = timeSlot.getEndTimeAsString(draggingPaneSpan);

                    //Update event's new date and time information through an edit command
                    CommandResult commandResult = logic.execute("eventedit " + eventIndex + " t/" + date
                            + " " + startTime + "-" + endTime);
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
                } catch (CommandException | ParseException exc) {
                    raise(new NewResultAvailableEvent(exc.getMessage(), true));
                }

                e.setDropCompleted(true);

                //Dropping finished, reset the details of dragged pane
                draggingPane = null;
                draggedEvent = null;
                draggingPaneSpan = 0;
            }
        });

    }

    /**
     * Upon receiving an AddressBookChangedEvent, update the event list accordingly.
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info("LastChangedEvent is " + abce.data.getLastChangedEvent());
        logger.info("NewlyAddedEvent is " + abce.data.getNewlyAddedEvent());
        updateEvents(calendarView, abce.data.getEventList(), abce.data.getLastChangedEvent(), abce.data
                .getNewlyAddedEvent());
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

        boolean timesBetween = testSlot.getStartTime().compareTo(startSlot.getStartTime())
                * endSlot.getStartTime().compareTo(testSlot.getStartTime()) >= 0;

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
        private final StackPane view;

        private final BooleanProperty selected = new SimpleBooleanProperty();

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;
            this.duration = duration;

            view = new StackPane();
            view.setPrefSize(135, 15);
            view.setMinSize(100, 15);
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

        public LocalDateTime getStartDateTime() {
            return start;
        }

        public LocalTime getStartTime() {
            return start.toLocalTime();
        }

        public String getDateAsString() {
            LocalDate date = start.toLocalDate();
            String[] tokens = date.toString().split("-");
            return tokens[2] + "/" + tokens[1] + "/"
                    + tokens[0];
        }

        public String getStartTimeAsString() {
            LocalTime startTime = getStartTime();
            String[] tokens = startTime.toString().split(":");
            return tokens[0] + tokens[1];
        }

        public String getEndTimeAsString(int span) {
            LocalTime endTime = getStartTime().plus(SLOT_LENGTH * span, ChronoUnit.MINUTES);
            String[] tokens = endTime.toString().split(":");
            return tokens[0] + tokens[1];
        }

        public DayOfWeek getDayOfWeek() {
            return start.getDayOfWeek();
        }

        public Duration getDuration() {
            return duration;
        }

        public StackPane getView() {
            return view;
        }

    }

}
