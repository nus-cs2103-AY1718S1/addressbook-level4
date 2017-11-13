# jacoblipech
###### \java\seedu\address\commons\events\ui\NewResultAvailableEvent.java
``` java
    public NewResultAvailableEvent(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

```
###### \java\seedu\address\logic\commands\AddBirthdayCommand.java
``` java
/**
 * Adds the birthday to the identified persons.
 */
public class AddBirthdayCommand extends UndoableCommand {

    public static final String COMMAND_WORDVAR_1 = "birthday";
    public static final String COMMAND_WORDVAR_2 = "bd";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Adds the given birthday to the person identified by the list of index numbers used in the "
            + "last person listing. The format of birthday is in DDMMYY. "
            + "Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] (index must be a positive integer) "
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY]... (birthday must be integers)\n"
            + "Example 1: "
            + COMMAND_WORDVAR_1
            + " 1 b/240594 \n"
            + "Example 2: "
            + COMMAND_WORDVAR_2.toUpperCase()
            + " 5 b/110696 \n";

    public static final String MESSAGE_ADD_BIRTHDAY_SUCCESS = "Added Birthday: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_BIRTHDAY = "A birthday already exists in the given person. "
            + "Please use edit command to make changes to it.";

    private final Index targetIndex;
    private final Birthday toAdd;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param toAdd birthday to add to given target index
     */
    public AddBirthdayCommand(Index targetIndex, Birthday toAdd) {

        requireNonNull(targetIndex);
        requireNonNull(toAdd);

        this.targetIndex = targetIndex;
        this.toAdd = toAdd;
    }

    /**
     * First checks if the target index is not out of bounds and then checks if a birthday exists in them
     * the given target index of person. If not, add the birthday to the target person.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean personsContainsBirthdayToAdd = true;

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson readOnlyPerson = lastShownList.get(targetIndex.getZeroBased());
        if (Objects.equals(readOnlyPerson.getBirthday().toString(), Birthday.DEFAULT_BIRTHDAY)) {
            personsContainsBirthdayToAdd = false;
        }

        if (personsContainsBirthdayToAdd) {
            throw new CommandException(MESSAGE_DUPLICATE_BIRTHDAY);
        }
        try {
            model.addBirthday(this.targetIndex, this.toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_ADD_BIRTHDAY_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddBirthdayCommand)) {
            return false;
        }

        // state check
        AddBirthdayCommand e = (AddBirthdayCommand) other;
        return targetIndex.equals(e.targetIndex)
                && toAdd.equals(e.toAdd);
    }
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
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sort the list of contacts by their names
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORDVAR_1 = "sort";
    public static final String COMMAND_WORDVAR_2 = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Sort all contacts by alphabetical order according to their name. "
            + "Command is case-insensitive.";

    public static final String MESSAGE_SUCCESS = "All contacts in AddressBook are sorted by name.";
    public static final String MESSAGE_ALREADY_SORTED = "The AddressBook is already sorted.";
    public static final String MESSAGE_EMPTY_LIST = "There is no contact to be sorted in AddressBook.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isNotEmpty = model.sortPersonByName(contactList);
        if (isNotEmpty == null) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        } else if (!isNotEmpty) {
            return new CommandResult(MESSAGE_ALREADY_SORTED);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


```
###### \java\seedu\address\logic\parser\AddBirthdayCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddBirthdayCommand object
 */
public class AddBirthdayCommandParser implements Parser<AddBirthdayCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddBirthdayCommand
     * and returns a AddBirthdayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBirthdayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BIRTHDAY);

        if (!isPrefixPresent(argMultimap, PREFIX_BIRTHDAY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBirthdayCommand.MESSAGE_USAGE));
        }

        try {
            String enteredIndex = argMultimap.getPreamble();
            Index index = ParserUtil.parseIndex(enteredIndex);

            String birthdayName = argMultimap.getValue(PREFIX_BIRTHDAY).orElse("");
            Birthday toAdd = new Birthday(birthdayName);

            return new AddBirthdayCommand(index, toAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the prefix does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
```
###### \java\seedu\address\logic\parser\ArgumentMultimap.java
``` java
    /**
     * Returns the last address value of the field entered {@code prefix}.
     * Returns default address value when address is entered.
     */
    public Optional<String> getAddressOptionalValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.of(Address.DEFAULT_ADDRESS) : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns the last email value of the {@code prefix}.
     * Returns default email value when no email is entered.
     */
    public Optional<String> getEmailOptionalValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.of(Email.DEFAULT_EMAIL) : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns the last birthday value of {@code prefix}.
     * Returns default birthday value when no birthday is entered.
     */
    public Optional<String> getBirthdayOptionalValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.of(Birthday.DEFAULT_BIRTHDAY) : Optional.of(values.get(values.size() - 1));
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
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given birthday to a person */
    void addBirthday(Index targetIndex, Birthday toAdd) throws PersonNotFoundException,
            DuplicatePersonException;

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Sort the given list according to alphabetical order
     * @throws NullPointerException if {@code contactList} is null.
     */
    Boolean sortPersonByName(ArrayList<ReadOnlyPerson> contactList);

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addBirthday(Index targetIndex, Birthday toAdd) throws PersonNotFoundException,
            DuplicatePersonException {

        ReadOnlyPerson oldPerson = this.getFilteredPersonList().get(targetIndex.getZeroBased());

        Person newPerson = new Person(oldPerson);

        newPerson.setBirthday(toAdd);

        addressBook.updatePerson(oldPerson, newPerson);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Boolean sortPersonByName(ArrayList<ReadOnlyPerson> contactList) {

        if (filteredPersons.size() == 0) {
            return null;
        }
        contactList.addAll(filteredPersons);

        Collections.sort(contactList, Comparator.comparing(p -> p.toString().toLowerCase()));

        if (contactList.equals(filteredPersons)) {
            return false;
        }

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
        return true;
    }

    //=========== Filtered Person List Accessors =============================================================

```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a birthday field in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidBirthdayFormat(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Birthdays should be numeric in the format "
            + "DD/MM/YY or DD/MM/YYYY.";

    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3"
            + "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$"
            + "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public static final String DEFAULT_BIRTHDAY = "No Birthday Added";

    private final String birthdayNumber;

    public Birthday () {
        this.birthdayNumber = DEFAULT_BIRTHDAY; //default value
    }

    /**
     * Validates given birthday input.
     *
     * @throws IllegalValueException if the given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthdayFormat(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.birthdayNumber = trimmedBirthday;
    }

    /**
     * Returns the string value of the birthday
     */
    public String getBirthdayNumber() {
        return birthdayNumber;
    }

    /**
     * Returns true if a given string is a valid birthday number.
     */
    public static boolean isValidBirthdayFormat(String test) {

        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.equalsIgnoreCase(DEFAULT_BIRTHDAY);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.birthdayNumber.equals(((Birthday) other).birthdayNumber)); // state check
    }

    @Override
    public int hashCode() {
        return birthdayNumber.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return birthdayNumber;
    }
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
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String birthday;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        birthday = source.getBirthday().getBirthdayNumber();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Birthday birthday = new Birthday(this.birthday);
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, favourite, birthday, tags);
    }
}
```
###### \java\seedu\address\ui\ExtendedPersonDisplay.java
``` java
/**
 * Extended person card show more details about a certain contact
 */
public class ExtendedPersonDisplay extends UiPart<Region> {

    private static final String FXML = "ExtendedPersonDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label birthday;

    public ExtendedPersonDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);

    }

    /**
     * Updates the contact details displayed on the extended person display
     */
    private void loadMorePersonDetails (ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        address.setText(person.getAddress().toString());
    }

    /**
     * Binds the birthday string together for each contact to display in a better format
     * so that it is clearer for the user.
     */
    private void initBirthdayLabel(ReadOnlyPerson person) {
        String initialBirthday = person.getBirthday().getBirthdayNumber();
        String birthdayToDisplay;

        if (!initialBirthday.equals(Birthday.DEFAULT_BIRTHDAY)) {
            birthdayToDisplay = generateBirthdayMsg(initialBirthday);
            birthday.setText(birthdayToDisplay);
        } else {
            birthday.setText(initialBirthday);
        }
    }

    /**
     * Returns the birthday in the format needed for display.
     */
    private String generateBirthdayMsg (String initialBirthday) {
        HashMap<String, String> findSelectedMonth = initializeMonthHashMap();
        String [] splitDates = initialBirthday.split("/", 5);
        String birthdayToDisplay;

        birthdayToDisplay = splitDates[0] + " " + findSelectedMonth.get(splitDates[1]) + " " + splitDates[2];

        return birthdayToDisplay;
    }

    /**
     * Initialize a @HashMap for every integer to the correct month.
     */
    private HashMap<String, String> initializeMonthHashMap () {

        HashMap<String, String> monthFormat = new HashMap<>();
        monthFormat.put("01", "Jan");
        monthFormat.put("02", "Feb");
        monthFormat.put("03", "Mar");
        monthFormat.put("04", "Apr");
        monthFormat.put("05", "May");
        monthFormat.put("06", "Jun");
        monthFormat.put("07", "Jul");
        monthFormat.put("08", "Aug");
        monthFormat.put("09", "Sep");
        monthFormat.put("10", "Oct");
        monthFormat.put("11", "Nov");
        monthFormat.put("12", "Dec");

        return monthFormat;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        initBirthdayLabel(event.getNewSelection().person);
        loadMorePersonDetails(event.getNewSelection().person);
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    public ExtendedPersonDisplay getExtendedPersonDisplay() {
        return this.extendedPersonDisplay;
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String[] colors = { "red", "blue", "orange", "brown", "purple", "black", "gray", "maroon", "coral",
        "blueviolet", "slategrey", "darkseagreen", "darkturquoise", "darkkhaki", "firebrick", "darkcyan" };
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static int tagNumber = 0;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

```
###### \java\seedu\address\ui\PersonCard.java
``` java
        initTags(person);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Binds the individual tags shown for each contact to a different color
     * so that it is clearer for the user.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach((Tag tag) -> {
            Label tagLabel = new Label(tag.tagName);
            if (tag.tagName.equalsIgnoreCase("friends") || tag.tagName.equalsIgnoreCase("friend")) {
                tagLabel.setStyle("-fx-background-color: green");
            } else {
                tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            }
            tags.getChildren().add(tagLabel);
        });
    }

    private String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[tagNumber]);
            tagNumber++;
        }
        if (tagNumber >= colors.length) {
            tagNumber = 0;
        }

        return tagColors.get(tagValue);
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
     * Sets the {@code ResultDisplay} style to indicate failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the {@code ResultDisplay} style to use the default style.
     */
    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    /**
     * Display the total number of people in the address book
     * @param totalPeople
     * @ return a string of the displayed text
     */
    public static String getTotalPeopleText(int totalPeople) {
        String displayText = totalPeople + " person(s) in UniCity";

        return displayText;
    }

    public void setTotalPeople(int totalPeople) {

        this.totalPeople.setText(getTotalPeopleText(totalPeople));
    }
}
```
###### \resources\view\ExtendedPersonDisplay.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.VBox?>

<VBox id="cardPane" fx:id="cardPane" minHeight="120" minWidth="150" prefHeight="416.0" prefWidth="160.0" xmlns="http://javafx.com/javafx/9" xmlns:fx="http://javafx.com/fxml/1">
    <children>
    <Label fx:id="name" styleClass="cell_big_label" text="\$name">
        <graphic>
            <ImageView fitHeight="20.0" fitWidth="20.0">
                <image>
                    <Image url="@../images/name(white).png" />
                </image>
            </ImageView>
        </graphic>
        <padding>
            <Insets bottom="5.5" />
        </padding>
    </Label>
    <Label fx:id="phone" styleClass="cell_small_label" text="\$phone">
      <graphic>
              <ImageView fitHeight="20.0" fitWidth="20.0">
                  <image>
                      <Image url="@../images/phone(white).png" />
                  </image>
              </ImageView>
      </graphic>
        <padding>
            <Insets bottom="5.5" />
        </padding>
    </Label>
    <Label fx:id="email" styleClass="cell_small_label" text="\$email">
      <graphic>
              <ImageView fitHeight="20.0" fitWidth="20.0">
                  <image>
                      <Image url="@../images/email(white).png" />
                  </image>
              </ImageView>
      </graphic>
        <padding>
            <Insets bottom="5.5" />
        </padding>
    </Label>
    <Label fx:id="address" styleClass="cell_small_label" text="\$address">
      <graphic>
              <ImageView fitHeight="20.0" fitWidth="20.0">
                  <image>
                      <Image url="@../images/address(white).png" />
                  </image>
              </ImageView>
      </graphic>
        <padding>
            <Insets bottom="5.5" />
        </padding>
    </Label>
    <Label fx:id="birthday" styleClass="cell_small_label" text="\$birthday">
      <graphic>
              <ImageView fitHeight="20.0" fitWidth="20.0">
                  <image>
                      <Image url="@../images/birthday(white).png" />
                  </image>
              </ImageView>
      </graphic>
        <padding>
            <Insets bottom="5.5" />
        </padding>
    </Label>
    </children>
</VBox>
```
###### \resources\view\MainWindow.fxml
``` fxml
      <SplitPane orientation="VERTICAL" prefHeight="200.0" prefWidth="80.0">
        <items>
            <StackPane fx:id="extendedPersonDisplayPlaceholder" prefHeight="150.0" prefWidth="50.0">
              <padding>
                <Insets bottom="10" left="0" right="0" top="10" />
              </padding>
            </StackPane>
        </items>
      </SplitPane>

```
