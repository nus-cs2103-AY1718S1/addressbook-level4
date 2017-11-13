# HouDenghao
###### \java\seedu\address\logic\commands\FindEventCommand.java
``` java
/**
 * Finds and lists all events in event list whose name contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "findE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meeting sports exam";

    private final EventNameContainsKeywordsPredicate predicate;

    public FindEventCommand(EventNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(predicate);
        return new CommandResult(getMessageForEventListShownSummary(model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && this.predicate.equals(((FindEventCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListEventCommand.java
``` java
/**
 * Lists all events in the event list to the user.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "listE";

    public static final String MESSAGE_SUCCESS = "Listed all events";

    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ShowParticipantsCommand.java
``` java
/**
 * Shows all the participants of an event.
 */
public class ShowParticipantsCommand extends Command {

    public static final String COMMAND_WORD = "showP";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the event identified by the index number used in the last event listing "
            + "and displays all participants the event has involved.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_PARTICIPANTS_SUCCESS = "Show all the participants of %1$s";

    private final Index targetIndex;
    private ReadOnlyEvent eventToShow;

    public ShowParticipantsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        eventToShow = lastShownList.get(targetIndex.getZeroBased());

        String name = eventToShow.getEventName().fullEventName;

        PersonJoinsEventsPredicate predicate = new PersonJoinsEventsPredicate(name);

        model.updateFilteredPersonList(predicate);

        return new CommandResult(String.format(MESSAGE_SHOW_PARTICIPANTS_SUCCESS, eventToShow.getEventName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowParticipantsCommand // instanceof handles nulls
                && this.targetIndex.equals(((ShowParticipantsCommand) other).targetIndex)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts all persons in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons and events";

    @Override
    public CommandResult execute() {
        model.sortPersons();
        model.sortEvents();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ShowParticipantsCommand.COMMAND_WORD:
            return new ShowParticipantsCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindEventCommand.COMMAND_WORD:
            return new FindEventCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ListEventCommand.COMMAND_WORD:
            return new ListEventCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

```
###### \java\seedu\address\logic\parser\FindEventCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindEventCommandParser implements Parser<FindEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns an FindEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\ShowParticipantsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ShowParticipantsCommandParser implements Parser<ShowParticipantsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowParticipantsCommand
     * and returns an ShowParticipantsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowParticipantsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShowParticipantsCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowParticipantsCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the person list.
     */
    public void sortPersons() {
        persons.sort();
    }

    //// person-level operations

```
###### \java\seedu\address\model\event\EventNameContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyEvent}'s {@code Name} matches any of the keywords given.
 */
public class EventNameContainsKeywordsPredicate implements Predicate<ReadOnlyEvent> {
    private final List<String> keywords;

    public EventNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getEventName().fullEventName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventNameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EventNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\EventList.java
``` java
    /**
     * Sorts the event list.
     */
    public void sortEvents() {
        events.sort();
    }

    //// util methods

```
###### \java\seedu\address\model\Model.java
``` java
    /** Sorts the person list */
    void sortPersons();

```
###### \java\seedu\address\model\Model.java
``` java
    /** Sorts the event list */
    void sortEvents();

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void sortPersons() {
        addressBook.sortPersons();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void sortEvents() {
        eventList.sortEvents();
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

```
###### \java\seedu\address\model\person\PersonJoinsEventsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Participation} matches the event given.
 */
public class PersonJoinsEventsPredicate implements Predicate<ReadOnlyPerson> {
    private final String keywords;

    public PersonJoinsEventsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Boolean isSelected = false;
        if (!person.getParticipation().isEmpty()) {
            for (ReadOnlyEvent event: person.getParticipation()) {
                if (!isSelected) {
                    isSelected = keywords.equals(event.getEventName().fullEventName);
                }
            }
        }
        return isSelected;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonJoinsEventsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonJoinsEventsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\ui\EventCard.java
``` java
/**
 * An UI component that displays information of a {@code Person}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyEvent event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label time;
    @FXML
    private Label timer;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        name.textProperty().bind(Bindings.convert(event.eventNameProperty()));
        description.textProperty().bind(Bindings.convert(event.descriptionProperty()));
        time.textProperty().bind(Bindings.convert(event.timeProperty()));
        if (event.getEventTime().getDays() == 0) {
            timer.textProperty().bind(Bindings.convert(new SimpleObjectProperty<>("Today!")));
        } else {
            timer.textProperty().bind(Bindings.convert(event.daysProperty()));
        }
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
```
###### \java\seedu\address\ui\EventListPanel.java
``` java
/**
 * Panel containing the list of persons.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<EventCard> eventListView;
    @FXML
    private Label title;

    public EventListPanel(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        title.textProperty().bind(new SimpleStringProperty("Event List"));
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        ObservableList<EventCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class EventListViewCell extends ListCell<EventCard> {

        @Override
        protected void updateItem(EventCard event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(event.getRoot());
            }
        }
    }

}
```
###### \java\seedu\address\ui\InformationBoard.java
``` java
/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class InformationBoard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(InformationBoard.class);
    private static final String FXML = "InformationBoard.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea informationBoard;

    @FXML
    private Label title;

    public InformationBoard() {
        super(FXML);
        title.textProperty().bind(new SimpleStringProperty("Information Board"));
        informationBoard.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.message.startsWith("Details: ")) {
            Platform.runLater(() -> displayed.setValue(event.message));
        }
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        InformationBoard informationBoard = new InformationBoard();

```
###### \java\seedu\address\ui\MainWindow.java
``` java
        eventListPanel = new EventListPanel(logic.getFilteredEventList());

```
###### \resources\view\DarkTheme.css
``` css
.text-area {
    -fx-background-color: transparent;
    -fx-background-radius: 18px;
    -fx-border-radius: 18px;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.text-area .scroll-pane {
    -fx-background-color: transparent;
    -fx-border-radius: 18px;
}

.text-area .scroll-pane .viewport{
    -fx-background-color: transparent;
}


.text-area .scroll-pane .content{
    -fx-background-color: lightgreen;
    -fx-background-radius: 18px;
}

```
###### \resources\view\DarkTheme.css
``` css
.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: transparent;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-color: transparent;
    -fx-padding: 5px;
}

.list-cell:filled:selected {
    -fx-background-color: transparent;
}

```
###### \resources\view\EventListCard.fxml
``` fxml

<HBox id="cardPane" fx:id="cardPane" prefHeight="102.0" prefWidth="200.0" style="-fx-background-color: #66B2F6; -fx-background-radius: 18px; -fx-border-color: white; -fx-border-radius: 18px; -fx-border-width: 2;" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">

  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
      <HBox prefHeight="105.0">
         <children>
          <VBox alignment="CENTER_LEFT" minHeight="105" prefHeight="105.0">
            <padding>
              <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
              <Label fx:id="id" styleClass="cell_big_label">
                <minWidth>
                  <!-- Ensures that the label text is never truncated -->
                  <Region fx:constant="USE_PREF_SIZE" />
                </minWidth>
              </Label>
              <Label fx:id="name" styleClass="cell_big_label" text="\$name" />
            </HBox>
            <FlowPane fx:id="tags" />
               <HBox>
                  <children>
                     <FontAwesomeIconView glyphName="INFO" />
                  <Label fx:id="description" styleClass="cell_small_label" text="\$description">
                        <HBox.margin>
                           <Insets left="4.0" />
                        </HBox.margin>
                     </Label>
                  </children>
               </HBox>
               <HBox>
                  <children>
                     <FontAwesomeIconView glyphName="CALENDAR" />
                  <Label fx:id="time" styleClass="cell_small_label" text="\$time">
                        <HBox.margin>
                           <Insets left="4.0" />
                        </HBox.margin>
                     </Label>
                  </children>
               </HBox>
          </VBox>
            <Label fx:id="timer" alignment="CENTER_RIGHT" contentDisplay="RIGHT" prefHeight="105.0" style="-fx-font-size: 35;" text="\$timer">
               <HBox.margin>
                  <Insets right="50.0" />
               </HBox.margin></Label>
         </children>
      </HBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```
###### \resources\view\EventListPanel.fxml
``` fxml

<VBox style="-fx-background-color: #99CCFF; -fx-background-radius: 18;" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <Label fx:id="title" contentDisplay="CENTER" style="-fx-text-fill: white; -fx-font-size: 18" text="\$title">
      <VBox.margin>
         <Insets left="20.0" />
      </VBox.margin>
      <font>
         <Font size="18.0" />
      </font></Label>
  <ListView fx:id="eventListView" style="-fx-background-color: #99CCFF; -fx-background-radius: 18px;" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \resources\view\InformationBoard.fxml
``` fxml
<StackPane fx:id="placeHolder" prefHeight="800.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <VBox style="-fx-background-color: lightgreen; -fx-background-radius: 18px; -fx-border-radius: 18px;">
      <children>
         <Label fx:id="title" contentDisplay="CENTER" style="-fx-text-fill: white; -fx-font-size: 20" text="\$title">
            <VBox.margin>
               <Insets left="20.0" />
            </VBox.margin></Label>
          <TextArea fx:id="informationBoard" editable="false" prefHeight="800.0" prefWidth="538.0" styleClass="text-area" />
      </children>
   </VBox>
</StackPane>
```
###### \resources\view\PersonListCard.fxml
``` fxml

<HBox id="cardPane" fx:id="cardPane" style="-fx-background-color: #FF5A5A; -fx-background-radius: 18px; -fx-border-color: white; -fx-border-radius: 18px; -fx-border-width: 2;" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
     <GridPane HBox.hgrow="ALWAYS">
       <columnConstraints>
         <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
       </columnConstraints>
        <children>
            <HBox prefHeight="100.0" prefWidth="200.0">
               <children>
                <VBox alignment="CENTER_LEFT" minHeight="105">
                  <padding>
                    <Insets bottom="5" left="15" right="5" top="5" />
                  </padding>
                   <children>
                     <HBox alignment="CENTER_LEFT" spacing="5">
                        <children>
                          <Label fx:id="id" styleClass="cell_big_label">
                            <minWidth>
                              <!-- Ensures that the label text is never truncated -->
                              <Region fx:constant="USE_PREF_SIZE" />
                            </minWidth>
                          </Label>
                          <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
                        </children>
                     </HBox>
                     <FlowPane fx:id="tags" />
                        <HBox prefHeight="100.0" prefWidth="200.0">
                           <children>
                              <FontAwesomeIconView glyphName="PHONE" />
                           <Label fx:id="phone" styleClass="cell_small_label" text="\$phone">
                                 <HBox.margin>
                                    <Insets left="4.0" />
                                 </HBox.margin></Label>
                           </children>
                        </HBox>
                        <HBox prefHeight="100.0" prefWidth="200.0">
                           <children>
                              <FontAwesomeIconView glyphName="HOME" />
                           <Label fx:id="address" styleClass="cell_small_label" text="\$address">
                                 <HBox.margin>
                                    <Insets left="4.0" />
                                 </HBox.margin></Label>
                           </children>
                        </HBox>
                        <HBox prefHeight="100.0" prefWidth="200.0">
                           <children>
                              <FontAwesomeIconView glyphName="ENVELOPE" />
                           <Label fx:id="email" styleClass="cell_small_label" text="\$email">
                                 <HBox.margin>
                                    <Insets left="4.0" />
                                 </HBox.margin></Label>
                           </children>
                        </HBox>
                        <HBox prefHeight="100.0" prefWidth="200.0">
                           <children>
                              <FontAwesomeIconView glyphName="BIRTHDAY_CAKE" />
                           <Label fx:id="birthday" styleClass="cell_small_label" text="\$birthday">
                                 <HBox.margin>
                                    <Insets left="4.0" />
                                 </HBox.margin></Label>
                           </children>
                        </HBox>
                   </children>
                </VBox>
```
###### \resources\view\PersonListCard.fxml
``` fxml
               </children>
            </HBox>
        </children>
         <rowConstraints>
            <RowConstraints />
         </rowConstraints>
     </GridPane>
   </children>
</HBox>
```
###### \resources\view\PersonListPanel.fxml
``` fxml
<VBox style="-fx-background-color: #FF7676; -fx-background-radius: 18px;" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <Label fx:id="title" contentDisplay="CENTER" text="\$title" style="-fx-text-fill: white; -fx-font-size: 18">
      <VBox.margin>
         <Insets left="20.0" />
      </VBox.margin></Label>
  <ListView fx:id="personListView" style="-fx-background-color: #FF7676; -fx-background-radius: 18px;" VBox.vgrow="ALWAYS" />
</VBox>
```
