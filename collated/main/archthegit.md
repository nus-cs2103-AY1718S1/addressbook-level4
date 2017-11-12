# archthegit
###### /java/seedu/address/commons/core/EventsCenter.java
``` java
    public void unregisterHandler(Object handler) {
        eventBus.unregister(handler);
    }

```
###### /java/seedu/address/commons/events/ui/PersonPanelUnselectEvent.java
``` java
/**
 * Represents an unselection in the Person List Panel
 */
public class PersonPanelUnselectEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/PersonSelectionChangedEvent.java
``` java

/**
 * Represents a selection change in Person
 */

public class PersonSelectionChangedEvent extends BaseEvent {

    private final ReadOnlyPerson newSelection;

    public PersonSelectionChangedEvent(ReadOnlyPerson newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getNewSelection() {
        return newSelection;
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

        if ((preppedWord.length() >= 2) && isWordInName(preppedWord, wordsInPreppedSentence)) {
                return true;
        }
        return false;
    }

    /**
     * Returns true if keyword is in the starting part of the name ignoring cases.
     * @param preppedWord
     * @param wordsInPreppedSentence
     * @return
     */
    private static boolean isWordInName(String preppedWord, String[] wordsInPreppedSentence) {
        for (String wordInSentence : wordsInPreppedSentence) {
            if ((wordInSentence.toLowerCase().contains(preppedWord.toLowerCase()))
                    && (wordInSentence.toLowerCase().startsWith(preppedWord.toLowerCase()))) {
                return true;
            }
        }
        return false;
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
    public static final String MESSAGE_SUCCESS = "Listed all people with birthdays";

    private CheckIfBirthday check = new CheckIfBirthday();

    public BirthdaysCommand (){}

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(check);
        return new CommandResult(getBirthdayMessageSummary(model.getFilteredPersonList().size()));
    }


}
```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
        if (model.ifSelectedPerson()) {
            model.unselectPerson();
        }
```
###### /java/seedu/address/logic/LogicManager.java
``` java

    @Override
    public void updateSelectedPerson(ReadOnlyPerson person) {
        model.updateSelectedPerson(person);
    }

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateSelectedPerson(ReadOnlyPerson person) {
        this.person = person;
        flag = 1;
    }

    @Override
    public void unselectPerson() {
        this.person = null;
    }

    @Override
    public boolean ifSelectedPerson() {
        if (flag == 1) {
            return true;
        }
        return false;
    }

    @Override
    public ReadOnlyPerson getSelectedPerson() {
        return person;
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
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
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
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday} matches today.
 */
public class CheckIfBirthday implements Predicate<ReadOnlyPerson> {

    public CheckIfBirthday(){ }

    /**
     * Method checks if month and day match
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
###### /java/seedu/address/model/person/Person.java
``` java

    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
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
    public void loadBlankPage() {
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

    @Subscribe
    private void handleUnselectOfPersonCardEvent(PersonPanelUnselectEvent event) {
        unregisterAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePersonSelectionChangedEvent(PersonSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInfo(event.getNewSelection());
    }


}


```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleUnselectOfPersonCardEvent(PersonPanelUnselectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        detailsPanel = new DetailsPanel();
        detailsPanelPlaceholder.getChildren().clear();
        detailsPanelPlaceholder.getChildren().add(detailsPanel.getRoot());
    }
```
###### /java/seedu/address/ui/UiPart.java
``` java
    protected void unregisterAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().unregisterHandler(handler);
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
                <Text fx:id="phoneField" styleClass="window_small_text" text="Phone: " />
                <Label fx:id="phone" styleClass="window_small_label" text="\$phone" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="100.0">
            <children>
                <Text fx:id="homePhoneField" styleClass="window_small_text" text="Home Phone: " />
                <Label fx:id="homePhone" styleClass="window_small_label" text="\$homePhone" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="addressField" styleClass="window_small_text" text="Address: " />
                <Label fx:id="address" alignment="TOP_LEFT" maxHeight="800.0" styleClass="window_small_label" text="\$address" wrapText="true" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="emailField" styleClass="window_small_text" text="Email: " />
                <Label fx:id="email" styleClass="window_small_label" text="\$email" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="schoolEmailField" styleClass="window_small_text" text="School Email: " />
                <Label fx:id="schoolEmail" styleClass="window_small_label" text="\$schoolEmail" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="birthdayField" styleClass="window_small_text" text="Birthday: " />
                <Label fx:id="birthday" styleClass="window_small_label" text="\$birthday" />
            </children>
        </TextFlow>
        <TextFlow prefHeight="50.0" prefWidth="400.0">
            <children>
                <Text fx:id="websiteField" styleClass="window_small_text" text="Website: " />
                <Label fx:id="website" styleClass="window_small_label" text="\$website" />
            </children>
        </TextFlow>
    </children>
</VBox>
</StackPane>
```
