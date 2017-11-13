# yanji1221
###### \java\seedu\address\commons\events\ui\NewResultAvailableEvent.java
``` java
    public NewResultAvailableEvent(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.birthday = toCopy.birthday;
            this.address = toCopy.address;
            this.profile = toCopy.profile;
            this.favorite = toCopy.favorite;
            this.tags = toCopy.tags;
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_BIRTHDAY,
                PREFIX_ADDRESS, PREFIX_PROFILEPAGE, PREFIX_TAG, PREFIX_GROUP);

        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME))
                && (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                && (!arePrefixesPresent(argMultimap, PREFIX_PHONE))
                && (!arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                && (!arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {

            Name name;
            Phone phone;
            Email email;
            Birthday birthday;
            Address address;
            ProfilePage profile;
            Favorite favorite;
            Set<Tag> tagList;

            if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            } else {
                throw new IllegalValueException(MESSAGE_NO_NAME_FORMAT);
            }

            if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
                phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            } else {
                phone = new Phone();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
                email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            } else {
                email = new Email();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY)) {
                birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
            } else {
                birthday = new Birthday();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
                address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            } else {
                address = new Address();
            }

            profile = ParserUtil.parseProfilePage(argMultimap.getValue(PREFIX_PROFILEPAGE)).get();
            favorite = new Favorite(false);
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, birthday, address, profile, favorite, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday should be in the yyyy/mm/dd format";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d{4}" + "/" + "\\d{2}" + "/" + "\\d{2}";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday address string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    public Birthday() throws IllegalValueException {
        // requireNonNull(birthday);
        String trimmedBirthday = "0000/00/00";
        /*if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }*/
        this.value = trimmedBirthday;
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
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
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getBirthday(), source.getAddress(),
                source.getProfilePage(), source.getFavorite(), source.getTags());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

```
###### \java\seedu\address\model\person\Person.java
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
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getBirthday().equals(this.getBirthday())
                && other.getAddress().equals(this.getAddress()))
                && other.getProfilePage().equals(this.getProfilePage());
    }
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Birthday("1993/11/12"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new ProfilePage("www.facebook.com"), new Favorite(false),
                    getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Birthday("1988/12/22"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new ProfilePage("www.facebook.com"), new Favorite(false),
                    getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Birthday("1987/12/12"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new ProfilePage("www.facebook.com"), new Favorite(false),
                    getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Birthday("1999/01/01"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new ProfilePage("www.facebook.com"), new Favorite(true),
                    getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Birthday("1985/03/04"), new Address("Blk 47 Tampines Street 20, #17-35"),
                        new ProfilePage("www.facebook.com"), new Favorite(false),
                    getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Birthday("1983/05/08"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                        new ProfilePage("www.facebook.com"), new Favorite(false),
                    getTagSet("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
```
###### \java\seedu\address\ui\ComingBirthdayListPanel.java
``` java
package seedu.address.ui;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ComingBirthdayPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons who are having their birthday soon.
 */
public class ComingBirthdayListPanel extends UiPart<Region> {
    private static final String FXML = "ComingBirthdayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> comingBirthdayListView;

    public ComingBirthdayListPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        ObservableList<ReadOnlyPerson> comingBirthdayList = comingBirthdayListGetter(personList);
        setConnections(comingBirthdayList);
        registerAsAnEventHandler(this);
    }

    /**
     * To get the list of person who are having their birthday soon
     */
    private ObservableList<ReadOnlyPerson> comingBirthdayListGetter(ObservableList<ReadOnlyPerson> personList) {
        List<ReadOnlyPerson> comingBirthdayList = personList.stream().collect(Collectors.toList());
        boolean isRemoved = false;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);

        if (((month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) && date == 31)
                || ((month == 4 || month == 6 || month == 9
                || month == 11) && date == 30)
                || (month == 2 && date == 29)) {
            month += 1;
            if (month == 13) {
                month = 1;
            }
            date = 0;
        }
        for (int i = 0; i < comingBirthdayList.size(); i++) {
            if (!(Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(5, 7)) == month)) {
                comingBirthdayList.remove(i);
                isRemoved = true;
            } else if ((Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(5, 7)) == month)
                    && Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(8)) < date) {
                comingBirthdayList.remove(i);
                isRemoved = true;
            }
            if (isRemoved) {
                i--;
                isRemoved = false;
            }
        }

        return FXCollections.observableArrayList(comingBirthdayList);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> comingBirthdayList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                comingBirthdayList, (person) -> new PersonCard(person, comingBirthdayList.indexOf(person) + 1));
        comingBirthdayListView.setItems(mappedList);
        comingBirthdayListView.setCellFactory(listView -> new BirthdayListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        comingBirthdayListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new ComingBirthdayPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            comingBirthdayListView.scrollTo(index);
            comingBirthdayListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class BirthdayListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard comingBirthday, boolean empty) {
            super.updateItem(comingBirthday, empty);

            if (empty || comingBirthday == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(comingBirthday.getRoot());
            }
        }
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandSuccess() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(SUCCESS_STYLE_CLASS)) {
            return;
        }

        styleClass.add(SUCCESS_STYLE_CLASS);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    public ComingBirthdayListPanel getComingBirthdayListPanel() {
        return this.comingBirthdayListPanel;
    }
    //@author
    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String[] colors = { "red", "blue", "orange", "brown", "green", "pink",
        "grey", "purple", "gold", "crimson", "navy", "darkBlue", "mediumBlue", "darkGreen",
        "teal", "darkCyan", "deepSkyBlue", "lime", "springGreen", "midnightBlue", "forestGreen",
        "seaGreen", "royalBlue", "indigo", "darkOliveGreen", "maroon", "saddleBrown", "slateBlue",
        "chocolate", "darksalmon"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
    private static int[] usedColors = new int[colors.length];
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label birthday;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Color getter for a tag
     */
    private static String colorGetterForTag(String tagValue) {
        int colorCode;
        boolean isUsedUpAllColors = true;
        for (int i = 0; i < colors.length; i++) {
            if (usedColors[i] == 0) {
                isUsedUpAllColors = false;
                break;
            }
        }
        if (isUsedUpAllColors) {
            for (int j = 0; j < colors.length; j++) {
                usedColors[j] = 0;
            }
        }

        if (!tagColors.containsKey(tagValue)) {
            do {
                colorCode = random.nextInt(colors.length);
            } while(usedColors[colorCode] == 1);
            usedColors[colorCode] = 1;
            tagColors.put(tagValue, colors[colorCode]);
        }

        return tagColors.get(tagValue);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Distribute colors for tags
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + colorGetterForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java

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
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));

        if (event.isError) {
            setStyleToIndicateCommandFailure();
        } else {
            setStyleToDefault();
        }

    }

    /**
     * Sets the {@code ResultDisplay} style to use the default style.
     */
    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the {@code ResultDisplay} style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);

    }

}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    @FXML
    private StatusBar totalPersons;
    @FXML
    private StatusBar currentDate;

    public StatusBarFooter(int totalPersons) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setTotalPersons(totalPersons);
        setCurrentDate();
        registerAsAnEventHandler(this);
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(totalPersons + " person(s) total");
    }

    private void setCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.currentDate.setText(" " + dateFormat.format(date) + "\n");
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setTotalPersons(abce.data.getPersonList().size());
    }
```
###### \resources\view\ComingBirthdayListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <children>
    <ListView fx:id="comingBirthdayListView" VBox.vgrow="ALWAYS" />
    </children>
</VBox>
```
###### \resources\view\StatusBarFooter.fxml
``` fxml
  <StatusBar styleClass="anchor-pane" fx:id="totalPersons" GridPane.columnIndex="1" />
  <StatusBar styleClass="anchor-pane" fx:id="currentDate" GridPane.columnIndex="2" nodeOrientation="RIGHT_TO_LEFT" />
```
