# archthegit
###### /java/seedu/address/commons/events/ui/EventsPanelSelectionChangedEvent.java
``` java

/**
 * Represents a selection change in the Events List Panel
 */
public class EventsPanelSelectionChangedEvent extends BaseEvent {


    private final EventsCard newSelection;

    public EventsPanelSelectionChangedEvent(EventsCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public EventsCard getNewSelection() {
        return newSelection;
    }
}


```
###### /java/seedu/address/commons/events/ui/PanelSwitchRequestEvent.java
``` java

/**
 * generic Event request to toggle between any two panels
 */
public class PanelSwitchRequestEvent extends BaseEvent {

    public final String wantedPanel;

    public PanelSwitchRequestEvent(String wantedPanel) {
        this.wantedPanel = wantedPanel;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

```
###### /java/seedu/address/commons/util/StringUtil.java
``` java

    /**
     * Returns true if the {@code sentence} contains part the {@code word}.
     *   Ignores case and a full word match is not required. The keyword must be in the beginning of any of the names.
     *   <br>examples:<pre>
     *       containsPartOfWord("Bernice Yeoh", "ber")=true
     *       containsPartOfWord("Irfan Ibrahim", "Ib")=true
     *
     *   </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word, has to be at least two characters long
     */
    public static boolean containsPartOfWord(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);
        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        if (preppedWord.length() >= 2) {
            for (String wordInSentence : wordsInPreppedSentence) {
                if ((wordInSentence.toLowerCase().contains(preppedWord.toLowerCase()))
                        && (wordInSentence.toLowerCase().startsWith(preppedWord.toLowerCase()))) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
```
###### /java/seedu/address/logic/commands/BirthdaysCommand.java
``` java
/**
 * Lists all persons with a birthday today
 */
public class BirthdaysCommand extends Command {
    public static final String COMMAND_WORD = "birthdays";
    public static final String COMMAND_ALIAS = "bd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays dialog box with names of contacts "
            + "who have their birthdays today.\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD;

    private CheckIfBirthday check = new CheckIfBirthday();

    public BirthdaysCommand (){}

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(check);
        return new CommandResult(getBirthdayMessageSummary(model.getFilteredPersonList().size()));
    }

}
```
###### /java/seedu/address/logic/commands/EventsCommand.java
``` java

/**
 * Command to list events for the month
 */
public class EventsCommand extends Command {
    public static final String COMMAND_WORD = "events";
    public static final String COMMAND_ALIAS = "ev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all events for the next 30 days\n"
            + "Example: " + COMMAND_WORD;

    private final EventPredicate predicate = new EventPredicate();

    public EventsCommand() { }

    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(predicate);
        EventsCenter.getInstance().post(new PanelSwitchRequestEvent(COMMAND_WORD));
        return new CommandResult(getEventsMessageSummary(model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventsCommand // instanceof handles nulls
                && this.predicate.equals(((EventsCommand) other).predicate)); // state check
    }
}


```
###### /java/seedu/address/model/event/EventPredicate.java
``` java

/**
 * Predicate class checks if a given event falls within the month
 */
public class EventPredicate implements Predicate<ReadOnlyEvent> {

    /**
     * Default constructor for EventPredicate Class
     */
    public EventPredicate() {
    }

    /**
     * Checks if event falls in the month
     * @param event
     * @return
     * @throws ParseException
     */
    public boolean checkIfEventFallsInMonth(ReadOnlyEvent event) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(event.getDate().toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return ((cal.get(Calendar.MONTH)) == Calendar.getInstance().get(Calendar.MONTH));
    }


    @Override
    public boolean test(ReadOnlyEvent event) {
        boolean val = false;
        try {
            val = checkIfEventFallsInMonth(event);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return val;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventPredicate); // instanceof handles nulls
    }
}


```
###### /java/seedu/address/model/person/Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthdays should only contain numbers and forward slashes,period or hash."
                    + " The day month and year must be valid in form dd/mm/yyyy or dd/mm/yy";

    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3"
            + "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$"
            + "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public static final String BIRTHDAY_TEMPORARY = "NIL";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        if (birthday == null) {
            this.value = BIRTHDAY_TEMPORARY;
        } else {
            String trimmedBirthday = birthday.trim();
            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX)
                || test.matches(BIRTHDAY_TEMPORARY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

```
###### /java/seedu/address/model/person/CheckIfBirthday.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday month and day} matches today.
 */
public class CheckIfBirthday implements Predicate<ReadOnlyPerson> {

    public CheckIfBirthday(){ }
    /**
     * Checks if today's day and month matches a person's birthday
     * @param person
     * @return
     * @throws ParseException
     */
    public boolean birthdayList(ReadOnlyPerson person)throws ParseException {
        String birthday = person.getBirthday().toString();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (((cal.get(Calendar.MONTH)) == Calendar.getInstance().get(Calendar.MONTH))
                && ((cal.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))));
    }
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean val = false;
        try {
            val = birthdayList(person);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return val;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckIfBirthday); // instanceof handles nulls
    }

}
```
###### /java/seedu/address/model/person/NameContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(ReadOnlyPerson person) {
        return ((keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                (person.getName().fullName, keyword))) || (keywords.stream().anyMatch(keyword ->
                StringUtil.containsPartOfWord(person.getName().fullName, keyword))));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/ui/DetailsPanel.java
``` java

/**
 * The Details Panel of the App that displays full information of a {@code Person}.
 */
public class DetailsPanel extends UiPart<Region> {

    private static final String FXML = "DetailsPanel.fxml";
    private static final String PREFIX_ADDRESS_FIELD = "Address: ";
    private static final String PREFIX_PHONE_FIELD = "Phone: ";
    private static final String PREFIX_HOME_PHONE_FIELD = "Home Phone: ";
    private static final String PREFIX_EMAIL_FIELD = "Email: ";
    private static final String PREFIX_SCHOOL_EMAIL_FIELD = "School Email: ";
    private static final String PREFIX_BIRTHDAY_FIELD = "Birthday: ";
    private static final String PREFIX_WEBSITE_FIELD = "Website: ";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Logic logic;

    @FXML
    private Pane pane;
    @FXML
    private Label name;
    @FXML
    private Text phoneField;
    @FXML
    private Label phone;
    @FXML
    private Text addressField;
    @FXML
    private Label address;
    @FXML
    private Text emailField;
    @FXML
    private Label email;
    @FXML
    private Text schoolEmailField;
    @FXML
    private Label schoolEmail;
    @FXML
    private Text birthdayField;
    @FXML
    private Label birthday;
    @FXML
    private Text websiteField;
    @FXML
    private Label website;
    @FXML
    private Text homePhoneField;
    @FXML
    private Label homePhone;
    @FXML
    private FlowPane tags;


    public DetailsPanel() {
        super(FXML);
        this.logic = logic;
        loadBlankPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the full info of the person
     * @param person the selected person to display the full info of.
     */
    public void loadPersonInfo(ReadOnlyPerson person) {
        phoneField.setText(PREFIX_PHONE_FIELD);
        addressField.setText(PREFIX_ADDRESS_FIELD);
        emailField.setText(PREFIX_EMAIL_FIELD);
        schoolEmailField.setText(PREFIX_SCHOOL_EMAIL_FIELD);
        birthdayField.setText(PREFIX_BIRTHDAY_FIELD);
        websiteField.setText(PREFIX_WEBSITE_FIELD);
        homePhoneField.setText(PREFIX_HOME_PHONE_FIELD);
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        schoolEmail.textProperty().bind(Bindings.convert(person.schEmailProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        website.textProperty().bind(Bindings.convert(person.websiteProperty()));
        homePhone.textProperty().bind(Bindings.convert(person.homeNumberProperty()));
        tags.getChildren().clear();
        initTags(person);
    }

    /**
     * Initializes and styles tags belonging to each person.
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-font-size:" + "15px");
            tags.getChildren().add(tagLabel);
        });
        logger.finest("All tags for " + person.getName().toString() + " initialized in info");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailsPanel)) {
            return false;
        }

        DetailsPanel detailsPanel = (DetailsPanel) other;
        return name.getText().equals(detailsPanel.name.getText())
                && phone.getText().equals(detailsPanel.phone.getText())
                && address.getText().equals(detailsPanel.address.getText())
                && email.getText().equals(detailsPanel.email.getText())
                && schoolEmail.getText().equals(detailsPanel.schoolEmail.getText())
                && birthday.getText().equals(detailsPanel.birthday.getText())
                && website.getText().equals(detailsPanel.website.getText())
                && homePhone.getText().equals(detailsPanel.homePhone.getText())
                && tags.getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .map(Label::getText)
                .collect(Collectors.toList())
                .equals(detailsPanel.tags.getChildrenUnmodifiable()
                        .stream()
                        .map(Label.class::cast)
                        .map(Label::getText)
                        .collect(Collectors.toList()));
    }
    /**
     * Sets all info fields to not display anything when the app is just started.
     */
    private void loadBlankPage() {
        Label label;
        Text text;
        for (Node node: pane.getChildren()) {
            if (node instanceof Label) {
                label = (Label) node;
                label.setText("");
            } else if (node instanceof Text) {
                text = (Text) node;
                text.setText("");
            } else if (node instanceof TextFlow) {
                for (Node subNode: ((TextFlow) node).getChildren()) {
                    if (subNode instanceof Text) {
                        text = (Text) subNode;
                        text.setText("");
                    }
                    if (subNode instanceof Label) {
                        label = (Label) subNode;
                        label.setText("");
                    }
                }
            }
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInfo(event.getNewSelection().person);
    }
}


```
###### /java/seedu/address/ui/EventsCard.java
``` java

/**
 * Event card ui to hold event name, date and address
 */
public class EventsCard extends UiPart<Region> {
    private static final String FXML = "EventsListCard.fxml";

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
    private Label address;
    @FXML
    private Label date;

    public EventsCard(ReadOnlyEvent event, int displayedIndex) {
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
        name.textProperty().bind(Bindings.convert(event.nameProperty()));
        date.textProperty().bind(Bindings.convert(event.dateProperty()));
        address.textProperty().bind(Bindings.convert(event.addressProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        EventsCard card = (EventsCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}

```
###### /java/seedu/address/ui/EventsListPanel.java
``` java

/**
 * Panel containing the list of events.
 */
public class EventsListPanel extends UiPart<Region> {

    private static final String FXML = "EventsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventsListPanel.class);

    @FXML
    private ListView<EventsCard> eventListView;

    public EventsListPanel(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        ObservableList<EventsCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventsCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new EventsPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
    * Scrolls to the {@code EventsCard} at the {@code index} and selects it.
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
    * Custom {@code ListCell} that displays the graphics of a {@code EventsCard}.
    */
    class EventListViewCell extends ListCell<EventsCard> {

        @Override
        protected void updateItem(EventsCard event, boolean empty) {
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
###### /resources/view/DetailsPanel.fxml
``` fxml

<StackPane fx:id="detailsPanel" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
<VBox fx:id="pane" prefHeight="600.0" prefWidth="800.0">
    <children>
        <Label fx:id="name" alignment="TOP_LEFT" prefHeight="50.0" prefWidth="360.0" styleClass="window_big_label" text="\$name" wrapText="true" />
        <FlowPane fx:id="tags" prefHeight="50.0" prefWidth="774.0" />
        <TextFlow prefHeight="50.0" prefWidth="100.0">
            <children>
                <Text fx:id="phoneField" fill="gray" styleClass="window_small_label" text="Phone: " />
                <Label fx:id="phone" styleClass="window_small_label" text="\$phone" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="100.0">
            <children>
                <Text fx:id="homePhoneField" fill="gray" styleClass="window_small_label" text="Home Phone: " />
                <Label fx:id="homePhone" styleClass="window_small_label" text="\$homePhone" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="addressField" fill="gray" styleClass="window_small_label" text="Address: " />
                <Label fx:id="address" alignment="TOP_LEFT" maxHeight="800.0" styleClass="window_small_label" text="\$address" wrapText="true" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="emailField" fill="gray" styleClass="window_small_label" text="Email: " />
                <Label fx:id="email" styleClass="window_small_label" text="\$email" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="schoolEmailField" fill="gray" styleClass="window_small_label" text="School Email: " />
                <Label fx:id="schoolEmail" styleClass="window_small_label" text="\$schoolEmail" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="birthdayField" fill="gray" styleClass="window_small_label" text="Birthday: " />
                <Label fx:id="birthday" styleClass="window_small_label" text="\$birthday" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="websiteField" fill="gray" styleClass="window_small_label" text="Website: " />
                <Label fx:id="website" styleClass="window_small_label" text="\$website" />
            </children>
        </TextFlow>
    </children>
</VBox>
</StackPane>
```
###### /resources/view/EventsListCard.fxml
``` fxml

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15" />
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
            </HBox>

            <Label fx:id="date" styleClass="cell_small_label" text="\$date" />
            <Label fx:id="address" styleClass="cell_small_label" text="\$address" />
        </VBox>

    </GridPane>
</HBox>
```
