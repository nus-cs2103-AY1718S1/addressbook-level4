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
            this.profile= toCopy.profile;
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
                PREFIX_ADDRESS, PREFIX_PROFILEPAGE, PREFIX_TAG);

        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME))
                &&(!arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                &&(!arePrefixesPresent(argMultimap, PREFIX_PHONE))
                &&(!arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                &&(!arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY))
                &&(!arePrefixesPresent(argMultimap, PREFIX_PROFILEPAGE))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {

            Name name;
            Phone phone;
            Email email;
            Birthday birthday;
            Address address;
            ProfilePage profile;
            Set<Tag> tagList;

            if(arePrefixesPresent(argMultimap, PREFIX_NAME))
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            else {throw new IllegalValueException(MESSAGE_NO_NAME_FORMAT);}

            if(arePrefixesPresent(argMultimap, PREFIX_PHONE))
                phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            else {phone=new Phone();}

            if(arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            else {email=new Email();}

            if(arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY))
                birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
            else {birthday=new Birthday();}

            if(arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            else {address=new Address();}

            if(arePrefixesPresent(argMultimap, PREFIX_PROFILEPAGE))
              profile = ParserUtil.parseProfilePage(argMultimap.getValue(PREFIX_PROFILEPAGE)).get();
            else {profile=new ProfilePage();}

            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, birthday, address, profile, tagList);


            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
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
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday should be in the yyyy/mm/dd format";
    public static final String BIRTHDAY_VALIDATION_REGEX = "[\\d{4}\\.]+/[\\d{2}\\.]+/[\\d{2]+";

    public String value;

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
        String trimmedBirthday = "00/00/00";
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
                source.getProfilePage(), source.getTags());
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
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || other.getPhone().equals(this.getPhone())
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getEmail().equals(this.getEmail())
                && other.getBirthday().equals(this.getBirthday())
                && other.getAddress().equals(this.getAddress()))
                && other.getProfilePage().equals(this.getProfilePage());
    }
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Birthday("1993/11/12"), new Address("Blk 30 Geylang Street 29, #06-40"), new ProfilePage("www.facebook.com"),
                    getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Birthday("1988/12/22"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new ProfilePage("www.facebook.com"),
                    getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Birthday("1987/12/12"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new ProfilePage("www.facebook.com"),
                    getTagSet("neighbours")),
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String birthday;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        birthday = source.getBirthday().value;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Birthday birthday = new Birthday(this.birthday);
```
###### \java\seedu\address\ui\CommandBox.java
``` java
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
```
###### \java\seedu\address\ui\CommandBox.java
``` java
            raise(new NewResultAvailableEvent(e.getMessage(), true));
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
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String[] colors = { "red", "blue", "orange", "brown", "green", "pink", "black",
        "grey", "purple" , "gold", "crimson", "navy", "darkBlue", "mediumBlue", "darkGreen",
        "teal", "darkCyan", "deepSkyBlue", "lime", "springGreen", "midnightBlue", "forestGreen",
        "seaGreen", "royalBlue", "indigo", "darkOliveGreen" };
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
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
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
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    private static final String SUCCESS_STYLE_CLASS = "success";
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
        if (event.isError) {
            setStyleToIndicateCommandFailure();
        }
        else {
            setStyleToIndicateCommandSuccess();
        }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    /**
     * Sets the {@code ResultDisplay} style to indicate a success command.
     */
    private void setStyleToIndicateCommandSuccess() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(SUCCESS_STYLE_CLASS)) {
            return;
        }

        styleClass.add(SUCCESS_STYLE_CLASS);

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
```
###### \resources\view\DarkTheme.css
``` css
    -fx-text-fill: yellow;
    /*author*/
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
}

.context-menu {
    -fx-background-color: derive(#1d1d1d, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #383838, transparent, #383838;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
```
###### \resources\view\DarkTheme.css
``` css
    -fx-text-fill: ivory;
```
###### \resources\view\Extensions.css
``` css
.success {
    -fx-text-fill: #10b22a !important;
}
```
###### \resources\view\StatusBarFooter.fxml
``` fxml
  <StatusBar styleClass="anchor-pane" fx:id="currentDate" GridPane.columnIndex="2" nodeOrientation="RIGHT_TO_LEFT" />
```
