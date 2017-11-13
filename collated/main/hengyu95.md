# hengyu95
###### \java\seedu\address\commons\events\model\BackupAddressBookEvent.java
``` java
/** Indicates that a backup command was used*/
public class BackupAddressBookEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public BackupAddressBookEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
```
###### \java\seedu\address\logic\commands\BackupCommand.java
``` java
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves a backup copy of Augustine data.\n";

    public static final String MESSAGE_SUCCESS = "Data backed up at \"/data/addressbook-backup.xml\"!";

    public BackupCommand() {
    }

    @Override
    public CommandResult execute() {
        model.backupAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\InstaCommand.java
``` java
public class InstaCommand extends Command {

    public static final String COMMAND_WORD = "insta";
    public static final String COMMAND_ALIAS = "i";
    public static final int INSTA_TAB = 2;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the Instagram account of the person identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s (%2$s)";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS2 = "\nUser ID is: ";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS3 = "\nUser ID is unavailable, redirecting to Instagram "
            + "home page...";




    private final Index targetIndex;

    public InstaCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new BrowserPanelChangeActiveTabEvent(INSTA_TAB));

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));

        if (personToEdit.getUserId().value.equals("-")) {
            return new CommandResult(new StringBuilder()
                    .append(String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                            lastShownList.get(targetIndex.getZeroBased()).getName().fullName,
                            targetIndex.getOneBased()))
                    .append(MESSAGE_SELECT_PERSON_SUCCESS3).toString());
        } else {
            return new CommandResult(new StringBuilder()
                    .append(String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                            lastShownList.get(targetIndex.getZeroBased()).getName().fullName,
                            targetIndex.getOneBased()))
                    .append(MESSAGE_SELECT_PERSON_SUCCESS2)
                    .append(personToEdit.getUserId().value).toString());
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InstaCommand // instanceof handles nulls
                && this.targetIndex.equals(((InstaCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the contacts and if specified, sort by tags "
            + "or by the names in alphabetical order\n"
            + "Parameters: "
            + "[ " + PREFIX_SORT + "<name|tag|email|address> ]\n"
            + "Examples:\n"
            + "1) " + COMMAND_WORD + "\n"
            + "2) " + COMMAND_WORD + " " + PREFIX_SORT + "tag";

    private int sortOrder = 0;

    public ListCommand() {
        new ListCommand(0);
    }

    public ListCommand(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortFilteredPersons(sortOrder);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\InstaCommandParser.java
``` java
public class InstaCommandParser implements Parser<InstaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InstaCommand
     * and returns an InstaCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InstaCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new InstaCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InstaCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook} sorted by upcoming birthday
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonListBirthdate() {
        return FXCollections.unmodifiableObservableList(sortedPersonsListBirthdate);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns a sorted unmodifiable view of the list {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook} sorted by upcoming birthdays
     */
    public void sortBirthdate() {

        Comparator<ReadOnlyPerson> sort = new Comparator<ReadOnlyPerson>() {

            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                String birthdate1 = o1.getBirthdate().value;

                String birthdate2 = o2.getBirthdate().value;
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate today = LocalDate.now();
                LocalDate date1;
                LocalDate date2;

                try {
                    date1 = LocalDate.parse(birthdate1, format).withYear(today.getYear());
                } catch (DateTimeParseException e) {
                    date1 = LocalDate.of(9999, 12, 30);
                }

                try {
                    date2 = LocalDate.parse(birthdate2, format).withYear(today.getYear());
                } catch (DateTimeParseException e) {
                    date2 = LocalDate.of(9999, 12, 30);
                }

                if (date1.isBefore(today)) {
                    date1 = date1.withYear(date1.getYear() + 1);
                }

                if (date2.isBefore(today)) {
                    date2 = date2.withYear(date2.getYear() + 1);
                }

                return date1.compareTo(date2);
            }
        };

        sortedPersonsListBirthdate.setComparator(sort);
    }
```
###### \java\seedu\address\model\person\Birthdate.java
``` java
/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthdate(String)}
 */
public class Birthdate {


    public static final String MESSAGE_BIRTHDATE_CONSTRAINTS =
            "A valid date entry is in the form of dd/mm/yyyy";

    public static final String BIRTHDATE_VALIDATION_REGEX = "(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((1[6-9]|"
            + "[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((1[6-9]|[2-9]\\d)\\d{2}))"
            + "|((0[1-9]|1\\d|2[0-8])\\/02\\/((1[6-9]|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)"
            + "(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Birthdate(String birthdate) throws IllegalValueException {
        requireNonNull(birthdate);
        String trimmedBirthdate = birthdate.trim();

        if (!trimmedBirthdate.equals("-")) {
            if (!isValidBirthdate(trimmedBirthdate)) {
                throw new IllegalValueException(MESSAGE_BIRTHDATE_CONSTRAINTS);
            }
        }

        this.value = trimmedBirthdate;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */

    public static boolean isValidBirthdate(String test) {
        return test.matches(BIRTHDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthdate // instanceof handles nulls
                && this.value.equals(((Birthdate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<Birthdate> birthdateProperty() {
        return birthdate;
    }

    @Override
    public Birthdate getBirthdate() {
        return birthdate.get();
    }

    public void setBirthdate(Birthdate birthdate) {
        this.birthdate.set(requireNonNull(birthdate));
    }

    @Override
    public ObjectProperty<UserId> userIdProperty() {
        return id;
    }

    @Override
    public UserId getUserId() {
        return id.get();
    }

    public void setUserId(UserId id) {
        this.id.set(requireNonNull(id));
    }
```
###### \java\seedu\address\model\person\UserId.java
``` java
/**
 * Represents a Person's Instagram account name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserId(String)}
 */
public class UserId {

    public static final String MESSAGE_USERID_CONSTRAINTS = "ID should contain only letters and numbers.";

    public static final String USERNAME_VALIDATION_REGEX = "(^[a-zA-Z0-9_]*$)";

    public final String value;

    public UserId(String id) throws IllegalValueException {
        requireNonNull(id);
        String trimmedId = id.trim();

        if (!trimmedId.equals("-")) {
            if (!isValidUserId(trimmedId)) {
                throw new IllegalValueException(MESSAGE_USERID_CONSTRAINTS);
            }
        }

        this.value = trimmedId;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */

    public static boolean isValidUserId(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserId // instanceof handles nulls
                && this.value.equals(((UserId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}






```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        String path = addressBookStorage.getAddressBookFilePath();
        saveAddressBook(addressBook, path.substring(0, path.indexOf(".xml")) + "-backup.xml");
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleBackupAddressBookEvent(BackupAddressBookEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Backup requested, saving to file"));
        try {
            backupAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads Instagram page on Instagram tab
     */
    public void loadInsta(ReadOnlyPerson person) {

        if (person.getUserId().value.equals("-")) {
            Platform.runLater(() -> instaBrowser.getEngine().load("https://www.instagram.com/"));
        } else {
            Platform.runLater(() -> instaBrowser.getEngine().load(new StringBuilder()
                    .append("https://www.instagram.com/").append(person.getUserId()).toString()));
        }

    }

    @Subscribe
    private void handlePersonPanelDeselectionEvent(BrowserPanelChangeActiveTabEvent event) {
        activeTab = event.targetTab;
        setActiveTab();
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
        leftDisplayPanel = new LeftDisplayPanel(logic.getFilteredPersonList(), logic.getFilteredPersonListBirthdate());
        leftDisplayPanelPlacedholder.getChildren().add(leftDisplayPanel.getRoot());
```
###### \java\seedu\address\ui\PersonCardBirthday.java
``` java
public class PersonCardBirthday extends UiPart<Region> {

    private static final String FXML = "PersonListCardBirthday.fxml";
    private static HashMap<String, String> tagColors = new HashMap<String, String>();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label emailAddress;
    @FXML
    private Label birthdate;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView photo;

    public PersonCardBirthday(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText("");
        initTags(person);
        bindListeners(person);

    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        emailAddress.textProperty().bind(Bindings.convert(person.emailAddressProperty()));
        birthdate.textProperty().bind(Bindings.convert(person.birthdateProperty()));

        setColor(person);

        try {
            StringExpression filePath = Bindings.convert(person.photoProperty());
            FileInputStream imageInputStream = new FileInputStream(filePath.getValue());
            Image image = new Image(imageInputStream, 100, 200, true, true);
            photo.setImage(image);
            imageInputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        getPhoto();

        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    private void setColor(ReadOnlyPerson person) {

        LocalDate date1;
        LocalDate now = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            date1 = LocalDate.parse(person.getBirthdate().value, format).withYear(now.getYear());
        } catch (DateTimeParseException e) {
            date1 = LocalDate.of(9999, 12, 30);
        }

        if (date1.equals(now)) {
            cardPane.setStyle("-fx-background-color: #336699;");
        }
    }
```
###### \java\seedu\address\ui\PersonListBirthdatePanel.java
``` java
/**
 * Panel containing the list of persons sorted by birthdate
 */
public class PersonListBirthdatePanel extends UiPart<Region> {
    private static final String FXML = "PersonListBirthdatePanel.fxml";
    private static final int SCROLL_INCREMENT = 11;
    private final Logger logger = LogsCenter.getLogger(PersonListBirthdatePanel.class);

    @FXML
    private ListView<PersonCardBirthday> personListBirthdateView;

    @FXML
    private ScrollBar personListViewScrollBar;

    public PersonListBirthdatePanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);

        setPersonListViewScrollBar();
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    /**
     * Scrolls one page down
     */
    public void scrollDown() {
        Platform.runLater(() -> {
            if (personListViewScrollBar == null) {
                setPersonListViewScrollBar();
            }

            for (int i = 0; i < SCROLL_INCREMENT; i++) {
                personListViewScrollBar.increment();
            }
        });
    }

    /**
     * Scrolls one page up
     */
    public void scrollUp() {
        Platform.runLater(() -> {
            if (personListViewScrollBar == null) {
                setPersonListViewScrollBar();
            }

            for (int i = 0; i < SCROLL_INCREMENT; i++) {
                personListViewScrollBar.decrement();
            }
        });
    }

    /**
     * Initializes personListViewScrollBar and assigns personListView's scrollbar to it
     */
    private void setPersonListViewScrollBar() {
        Set<Node> set = personListBirthdateView.lookupAll(".scroll-bar");
        for (Node node: set) {
            ScrollBar bar = (ScrollBar) node;
            if (bar.getOrientation() == Orientation.VERTICAL) {
                personListViewScrollBar = bar;
            }
        }
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCardBirthday> mappedList = EasyBind.map(
                personList, (person) -> new PersonCardBirthday(person, personList.indexOf(person) + 1));
        personListBirthdateView.setItems(mappedList);
        personListBirthdateView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Scrolls to the {@code PersonCardBirthday} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListBirthdateView.scrollTo(index);
            personListBirthdateView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCardBirthday}.
     */
    class PersonListViewCell extends ListCell<PersonCardBirthday> {

        @Override
        protected void updateItem(PersonCardBirthday person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }
}
```
###### \resources\view\BrowserPanel.fxml
``` fxml

<TabPane fx:id="browserPanel" minWidth="450" prefWidth="450" tabClosingPolicy="UNAVAILABLE" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <tabs>
        <Tab fx:id="nusModsTab" text="NUSMods">
            <content>
                <VBox fx:id="personList" minWidth="450" prefWidth="450">
                    <children>
                        <StackPane xmlns:fx="http://javafx.com/fxml/1">
                            <WebView fx:id="browser"/>
                        </StackPane>
                    </children>
                </VBox>
            </content>
        </Tab>
        <Tab fx:id="instaTab" text="Instagram">
            <content>
                <VBox fx:id="personList" minWidth="450" prefWidth="450">
                    <children>
                        <StackPane xmlns:fx="http://javafx.com/fxml/1">
                            <WebView fx:id="instaBrowser"/>
                        </StackPane>
                    </children>
                </VBox>
            </content>
        </Tab>
    </tabs>
</TabPane>
```
###### \resources\view\PersonListCardBirthday.fxml
``` fxml

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" minWidth="420">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="200" prefWidth="200" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets top="5" right="5" bottom="5" left="15" />
      </padding>
      <HBox spacing="0" alignment="CENTER_LEFT">
        <Label fx:id="id" styleClass="cell_big_label">
          </Label>
        <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
      </HBox>
      <FlowPane fx:id="tags" />
      <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
      <Label fx:id="emailAddress" styleClass="cell_small_label" text="\$emailAddress" />
	  <Label fx:id="birthdate" styleClass="cell_small_label" text="\$birthdate" style="-fx-font-size: 16; -fx-font-weight: bold;" />
    </VBox>
    <VBox alignment="CENTER_RIGHT" minHeight="105" GridPane.columnIndex="1">
      <padding>
        <Insets top="5" right="5" bottom="5" left="15" />
      </padding>
      <HBox spacing = "5" alignment = "CENTER_RIGHT" >
          <ImageView fx:id="photo" fitWidth = "100" fitHeight = "200" preserveRatio="true" smooth ="true" />
      </HBox>
    </VBox>
  </GridPane>
</HBox>
```
