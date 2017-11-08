# Haozhe321
###### /java/seedu/room/ui/CalendarBoxPanel.java
``` java

/**
 * Panel containing the calendar
 */
public class CalendarBoxPanel extends UiPart<Region> {
    private static final String FXML = "CalendarBox.fxml";

    @FXML
    private Pane calendarPane;

    public CalendarBoxPanel() {
        super(FXML);
        calendarPane.getChildren().add(new CalendarBox(YearMonth.now()).getView());
    }

    public void freeResources() {
        calendarPane = null;
    }
}
```
###### /java/seedu/room/ui/AnchorPaneNode.java
``` java
/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private final Background focusBackground = new Background(new BackgroundFill(
            Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background todayBackground = new Background(new BackgroundFill(
            Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background unfocusBackground = new Background(new BackgroundFill(
            Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked


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
        this.backgroundProperty().setValue(todayBackground);
    }


}
```
###### /java/seedu/room/ui/MainWindow.java
``` java
        calandarBoxPanel = new CalendarBoxPanel();
        calendarPlaceholder.getChildren().add(calandarBoxPanel.getRoot());
```
###### /java/seedu/room/ui/PersonCard.java
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
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);

        });
    }
```
###### /java/seedu/room/logic/parser/DeleteByTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteByTagCommandParser implements Parser<DeleteByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
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
###### /java/seedu/room/logic/parser/ParserUtil.java
``` java
    public static Optional<Timestamp> parseTimestamp(Optional<String> timestamp) throws IllegalValueException,
            NumberFormatException {
        return timestamp.isPresent() ? Optional.of(new Timestamp(Long.parseLong(timestamp.get()))) : Optional.empty();
    }
```
###### /java/seedu/room/logic/commands/AddCommand.java
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
###### /java/seedu/room/logic/commands/DeleteByTagCommand.java
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
            + "Example: " + COMMAND_WORD + "friends";

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
###### /java/seedu/room/model/person/Timestamp.java
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
###### /java/seedu/room/model/person/Person.java
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
###### /java/seedu/room/model/ResidentBook.java
``` java
    public void removeByTag(Tag tag) throws IllegalValueException, CommandException {
        persons.removeByTag(tag);
    }
```
###### /java/seedu/room/model/ModelManager.java
``` java
    /**
     * delete temporary persons on start up of the app
     */
    public synchronized void deleteTemporary(ResidentBook residentBook) throws PersonNotFoundException {
        UniquePersonList personsList = residentBook.getUniquePersonList();
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
###### /java/seedu/room/model/ModelManager.java
``` java
    @Override
    public synchronized void deleteByTag(Tag tag) throws IllegalValueException, CommandException {
        residentBook.removeByTag(tag);
        indicateResidentBookChanged();
    }
```
###### /java/seedu/room/model/Model.java
``` java
    /**
     * Delete all persons with the given tag
     */
    void deleteByTag(Tag tag) throws IllegalValueException, CommandException;
```
