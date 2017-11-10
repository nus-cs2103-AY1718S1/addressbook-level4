# Haozhe321
###### \java\seedu\room\logic\commands\AddCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            if (toAdd.getTimestamp().getExpiryTime() != null) {
                String successMessage = String.format(MESSAGE_SUCCESS, toAdd);
                return new CommandResult(successMessage, MESSAGE_TEMPORARY_PERSON);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }
```
###### \java\seedu\room\logic\commands\DeleteByTagCommand.java
``` java
/**
 * Deletes a person identified by a tag supplied
 */
public class DeleteByTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletebytag";
    public static final String COMMAND_ALIAS = "dbt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the tag supplied in this argument\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Persons with the following tag: %1$s";

    private final Tag toRemove;

    public DeleteByTagCommand(String tagName) throws IllegalValueException {
        this.toRemove = new Tag(tagName);
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            model.deleteByTag(toRemove);
        } catch (IllegalValueException e) {
            assert false : "Tag provided must be valid";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, toRemove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByTagCommand // instanceof handles nulls
                && this.toRemove.equals(((DeleteByTagCommand) other).toRemove)); // state check
    }
}


```
###### \java\seedu\room\logic\parser\DeleteByTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteByTagCommand object
 */
public class DeleteByTagCommandParser implements Parser<DeleteByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByTagCommand
     * and returns an DeleteByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByTagCommand parse(String args) throws ParseException {
        try {
            return new DeleteByTagCommand(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByTagCommand.MESSAGE_USAGE));
        }
    }

}

```
###### \java\seedu\room\logic\parser\ParserUtil.java
``` java
    public static Optional<Timestamp> parseTimestamp(Optional<String> timestamp) throws IllegalValueException,
            NumberFormatException {
        return timestamp.isPresent() ? Optional.of(new Timestamp(Long.parseLong(timestamp.get()))) : Optional.empty();
    }
```
###### \java\seedu\room\model\Model.java
``` java
    /**
     * Delete all persons with the given tag
     */
    void deleteByTag(Tag tag) throws IllegalValueException, CommandException;
```
###### \java\seedu\room\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteByTag(Tag tag) throws IllegalValueException, CommandException {
        residentBook.removeByTag(tag);
        indicateResidentBookChanged();
    }
```
###### \java\seedu\room\model\person\Person.java
``` java
    @Override
    public ObjectProperty<Timestamp> timestampProperty() {
        return timestamp;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp.get();
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp.set(requireNonNull(timestamp));
    }

```
###### \java\seedu\room\model\person\Timestamp.java
``` java
/**
 Create a timestamp in each person to record the time created and time that this temporary person will expire
 */
public class Timestamp {

    public static final String MESSAGE_TIMESTAMP_CONSTRAINTS =
            "Days to expire cannot be negative";

    private LocalDateTime creationTime = null;
    private LocalDateTime expiryTime = null; //after construction, a null expiryTime means this person will not expire
    private long daysToLive;

    public Timestamp(long day) throws IllegalValueException {
        creationTime = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0);
        if (!isValidTimestamp(day)) {
            throw new IllegalValueException(MESSAGE_TIMESTAMP_CONSTRAINTS);
        }
        if (day > 0) {
            expiryTime = creationTime.plusDays(day).withNano(0).withSecond(0).withMinute(0);
        }
        daysToLive = day;
    }

    public Timestamp(String expiry) {
        expiryTime = LocalDateTime.parse(expiry);
        expiryTime = expiryTime.withNano(0).withSecond(0).withMinute(0);
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public long getDaysToLive() {
        return daysToLive;
    }

    /**
     * following method returns null if this person does not expiry
     * @return time of expiry
     */
    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    /**
     *
     * @return the expiry time of the timestamp in String
     */
    public String toString() {
        if (expiryTime == null) {
            return "null";
        } else {
            return expiryTime.toString();
        }
    }


    /**
     * Returns true if a given long is a valid timestamp.
     */
    public static boolean isValidTimestamp(long test) {
        return (test >= 0);
    }

}
```
###### \java\seedu\room\model\ResidentBook.java
``` java
    /**
     * delete temporary persons on start up of the app
     */
    public void deleteTemporary() {
        UniquePersonList personsList = this.getUniquePersonList();

        Iterator<Person> itr = personsList.iterator(); //iterator to iterate through the persons list
        while (itr.hasNext()) {
            Person person = itr.next();
            LocalDateTime personExpiry = person.getTimestamp().getExpiryTime();
            LocalDateTime current = LocalDateTime.now();
            if (personExpiry != null) { //if this is a temporary contact
                if (current.compareTo(personExpiry) == 1) { //if current time is past the time of expiry
                    itr.remove();
                }
            }
        }
    }
```
###### \java\seedu\room\model\ResidentBook.java
``` java
    public void removeByTag(Tag tag) throws IllegalValueException, CommandException {
        persons.removeByTag(tag);
    }
```
###### \java\seedu\room\ui\AnchorPaneNode.java
``` java
/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    public final Color yellow = Color.web("#CA9733");
    public final Color green = Color.web("#336D1C");
    // Date associated with this pane
    private LocalDate date;
    private final Background focusBackground = new Background(new BackgroundFill(
            green, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background todayBackground = new Background(new BackgroundFill(
            Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background unfocusBackground = new Background(new BackgroundFill(
            yellow, CornerRadii.EMPTY, Insets.EMPTY));


    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setBackgroundUnfocused();
        this.setStyle("-fx-border-width: 2;");
        this.setStyle("-fx-border-color: white;");

        this.setOnMouseClicked((e) -> {
            if (this.getBackground() == focusBackground) {
                this.revertBackground();
            } else {
                this.focusGrid();
            }
        });

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    /**
     *Focus on the Grid when the mouse clicks on it
     */

    public void focusGrid() {
        if (this.getBackground() != todayBackground) {
            this.requestFocus();
            this.backgroundProperty().bind(Bindings
                    .when(this.focusedProperty())
                    .then(focusBackground)
                    .otherwise(unfocusBackground)
            );
        }

    }

    public void setBackgroundUnfocused() {
        this.backgroundProperty().unbind();
        this.backgroundProperty().setValue(unfocusBackground);
    }

    /**
     * Put the background to it's original state
     */
    public void revertBackground() {
        this.backgroundProperty().unbind();
        this.backgroundProperty().setValue(unfocusBackground);
    }


    /**
     *Make the Anchorpane that represents today's date light up
     */
    public void lightUpToday() {
        this.backgroundProperty().unbind();
        this.backgroundProperty().setValue(todayBackground);
    }


}
```
###### \java\seedu\room\ui\CalendarBox.java
``` java

/**
 * Create a CalendarBox Object to pass to the CalendarBoxPanel to be displayed
 */
public class CalendarBox {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private final Color yellow = Color.web("#CA9733");
    private Logic logic;


    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public CalendarBox(YearMonth yearMonth, Logic logic) {
        this.logic = logic;
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(500, 450);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 90);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
            new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
            new Text("Saturday") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            txt.setFill(Color.WHITE);
            AnchorPane ap = new AnchorPane();
            ap.setId("calendarDaysPane");
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            txt.setTextAlignment(TextAlignment.CENTER);
            ap.setStyle("-fx-text-align: center;");
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        calendarTitle.setFill(yellow);
        calendarTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        Button previousMonth = new Button(" PREV ");
        previousMonth.setOnAction(e -> previousMonth());

        Button nextMonth = new Button(" NEXT ");
        nextMonth.setOnAction(e -> nextMonth());

        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        HBox.setMargin(previousMonth, new Insets(0, 13, 0, 13));
        HBox.setMargin(nextMonth, new Insets(0, 13, 0, 13));

        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        logic.getFilteredEventList();
        populateCalendar(yearMonth, logic.getFilteredEventList());
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        VBox.setMargin(titleBar, new Insets(0, 0, 10, 0));
    }


    /**
     * Set the days of the calendar to correspond to the appropriate date, with the events populated
     * @param yearMonth year and month of month to render
     * @param eventList list of events to populate
     */
    public void populateCalendar(YearMonth yearMonth, ObservableList<ReadOnlyEvent> eventList) {
        HashMap<LocalDate, ArrayList<ReadOnlyEvent>> hashEvents = new HashMap<LocalDate, ArrayList<ReadOnlyEvent>>();
        hashEvents = eventsHashMap(eventList);

        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }

        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            ap.setId("calendarCell");
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }

            ap.getChildren().clear();
            //make today's date light up
            if (calendarDate.equals(LocalDate.now())) {
                ap.lightUpToday();
            } else {
                ap.revertBackground();
            }
            addDates(calendarDate, ap);

            if (hashEvents.containsKey(calendarDate)) {
                ArrayList<ReadOnlyEvent> eventInADay = hashEvents.get(calendarDate);

                int numEvents = 0;
                String allEventTitle = "";
                //go through the list of events and add them to the grid
                for (ReadOnlyEvent actualEvent: eventInADay) {

                    //if number of events is already more than 2, populate only 2 and tell users there are more events
                    if (numEvents == 2) {
                        allEventTitle = allEventTitle + "and more...";
                        break;
                    }
                    String eventTitle = actualEvent.getTitle().toString();
                    if (eventTitle.length() > 8) {
                        eventTitle = eventTitle.substring(0, 8) + "...";
                    }
                    allEventTitle = allEventTitle + eventTitle + "\n";
                    numEvents++;
                }
                Text eventText = new Text(allEventTitle);
                addEventName(ap, eventText);

            }
            calendarDate = calendarDate.plusDays(1);

        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Create a HashMap of LocalDate and Arraylist of ReadOnlyEvent to use for populating events on calendar
     * @param eventList
     * @return HashMap of LocalDate and Arraylist of ReadOnlyEvent
     */
    public HashMap<LocalDate, ArrayList<ReadOnlyEvent>> eventsHashMap(ObservableList<ReadOnlyEvent> eventList) {
        HashMap<LocalDate, ArrayList<ReadOnlyEvent>> hashEvents = new HashMap<LocalDate, ArrayList<ReadOnlyEvent>>();
        for (ReadOnlyEvent event: eventList) {
            if (hashEvents.containsKey(event.getDatetime().getLocalDateTime().toLocalDate())) {
                hashEvents.get(event.getDatetime().getLocalDateTime().toLocalDate()).add(event);
            } else {
                ArrayList<ReadOnlyEvent> newEventList = new ArrayList<ReadOnlyEvent>();
                newEventList.add(event);
                hashEvents.put(event.getDatetime().getLocalDateTime().toLocalDate(), newEventList);
            }
        }

        return hashEvents;
    }

    /**
     * Add the event's name on the calendar grid
     * @param ap AnchorPaneNode that we are adding the event to
     * @param eventText Text for the event(s)
     */
    public void addEventName(AnchorPaneNode ap, Text eventText) {
        ap.setBottomAnchor(eventText, 5.0);
        ap.setLeftAnchor(eventText, 5.0);
        ap.getChildren().add(eventText);
    }

    /**
     * add the date number to the grids
     */
    public void addDates(LocalDate calendarDate, AnchorPaneNode ap) {
        Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
        ap.setDate(calendarDate);
        ap.setTopAnchor(txt, 10.0);
        ap.setLeftAnchor(txt, 5.0);
        ap.getChildren().add(txt);
    }


    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth, logic.getFilteredEventList());
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth, logic.getFilteredEventList());
    }



    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}

```
###### \java\seedu\room\ui\CalendarBoxPanel.java
``` java

/**
 * Panel containing the calendar
 */
public class CalendarBoxPanel extends UiPart<Region> {
    private static final String FXML = "CalendarBox.fxml";

    @FXML
    private Pane calendarPane;

    private CalendarBox calendarBox;
    private Logic logic;

    public CalendarBoxPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        calendarBox = new CalendarBox(YearMonth.now(), logic);
        calendarPane.getChildren().add(calendarBox.getView());
    }

    @Subscribe
    public void handleCalenderBoxPanelChange() {
        calendarBox.populateCalendar(YearMonth.now(), this.logic.getFilteredEventList());
    }

    public CalendarBox getCalendarBox() {
        return calendarBox;
    }


    public void freeResources() {
        calendarPane = null;
    }
}
```
###### \java\seedu\room\ui\MainWindow.java
``` java
        calandarBoxPanel = new CalendarBoxPanel(this.logic);
        calendarPlaceholder.getChildren().add(calandarBoxPanel.getRoot());
```
###### \java\seedu\room\ui\PersonCard.java
``` java
    //following method gets the color related to a specified tag
    private static String getColorForTag(String tag) {
        if (!tagColor.containsKey(tag)) { //if the hashmap does not have this tag
            String chosenColor = colors.get(random.nextInt(colors.size()));
            tagColor.put(tag, chosenColor); //put the tag and color in
            /*after this color is chosen, remove from the available list of colors to avoid
            repeating */
        }
        return tagColor.get(tag);
    }

    /**
     * initialise the tag with the colors and the tag name
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            String randomizedTagColor = getColorForTag(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + randomizedTagColor);
            tag.setTagColor(randomizedTagColor);
            tags.getChildren().add(tagLabel);

        });
    }
```
###### \resources\view\CalendarBox.fxml
``` fxml
<Pane fx:id="calendarPane"
      prefHeight="447.0"
      prefWidth="600.0"
      xmlns="http://javafx.com/javafx/8.0.60"
      xmlns:fx="http://javafx.com/fxml/1"
/>

```
