# Lenaldnwj
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        args = optionalInput(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_FORMCLASS, PREFIX_GRADES, PREFIX_POSTALCODE, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_FORMCLASS,
                PREFIX_GRADES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_ADDEDITCOMMANDREMARK_INVALID, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Remark remark = new Remark(""); //add command does not allow adding remarks right away
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            FormClass formClass = ParserUtil.parseFormClass(argMultimap.getValue(PREFIX_FORMCLASS)).get();
            Grades grades = ParserUtil.parseGrades(argMultimap.getValue(PREFIX_GRADES)).get();
            PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE)).get();
            ReadOnlyPerson person = new Person(name, phone, email, address, formClass,
                    grades, postalCode, remark, tagList);
            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    /**
     * Returns a appended string stating that specific optional inputs are not recorded if user decides to not enter
     * any of the optional inputs.
     */
    public static String optionalInput(String input) {
        if (!input.contains("a/")) {
            input = input + " a/ (Address not recorded)";
        }
        if (!input.contains("e/")) {
            input = input + " e/ (Email not recorded)";
        }
        if (!input.contains("c/")) {
            input = input + " c/ (Postal code not recorded)";
        }
        return input;
    }
}
```
###### \java\seedu\address\model\person\Email.java
``` java

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "([\\w\\.]+@[\\w\\.]+)|(\\(Email not recorded\\))";
    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = trimmedEmail;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }
```
###### \java\seedu\address\model\person\Phone.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Users are to enter their numbers in this format, p/ student/(STUDENT_NUMBER) parent/(PARENT_NUMBER)\n"
                    + "For example, p/ student/97271111 parent/97979797\n"
                    + "Phone numbers can only contain numbers, and should be exactly 8 digits";
    public static final String PHONE_VALIDATION_REGEX = "((Student: )(\\d\\d\\d\\d\\d\\d\\d\\d)"
            + "( Parent: )(\\d\\d\\d\\d\\d\\d\\d\\d))|((Parent: )(\\d\\d\\d\\d\\d\\d\\d\\d))";
    public final String value;

    /**
     * Validates the UI formatting phone number.
     *
     * @throws IllegalValueException if phone UI string is of invalid format.
     */
    public Phone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        trimmedPhone = changeToAppropriateUiFormat(trimmedPhone);
        if (!isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = trimmedPhone;
    }

    /**
     * Returns true if a given string is a valid person phone number for display in UI.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    /**
     * Converts user phone number input into an appropriate UI format by
     * replacing all occurrence of "/" with ": " and capitalising first letter of student and parent.
     */
    public static String changeToAppropriateUiFormat(String value) {

        value = value.replace("/", ": ");
        value = value.replace("s", "S");
        value = value.replace("p", "P");
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static final String FXML = "PersonListCard.fxml";

    private static HashMap<String, String> currentTagColors = new HashMap<String, String>();

    private static String assignedColor;

    private static ArrayList<String> usedColors = new ArrayList<>();

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
    private Label formClass;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initialiseTags(person);
        bindListeners(person);
    }

    /**
     * This method takes in the tagName, and returns the color associated with that tagName
     * If the if the tag has no associated color, a unique random color will be assigned to the tag.
=     *
     * @param tagName is the String name of the tag
     * @return the color associated to the tagName
     */
    public static String obtainTagColors(String tagName) {
        if (!currentTagColors.containsKey(tagName)) {
            do {
                Random random = new Random();
                final float hue = random.nextFloat();
                final float saturation = 0.65f + random.nextFloat()
                        * (0.90f - 0.65f);
                final float luminance = 0.60f + random.nextFloat()
                        * (0.90f - 0.60f);

                Color color = Color.getHSBColor(hue, saturation, luminance);

                Formatter hexRepresentation = new Formatter(new StringBuffer("#"));
                hexRepresentation.format("%02X", color.getRed());
                hexRepresentation.format("%02X", color.getGreen());
                hexRepresentation.format("%02X", color.getBlue());
                assignedColor = hexRepresentation.toString();
            } while (usedColors.contains(assignedColor));

            usedColors.add(assignedColor);
            currentTagColors.put(tagName, assignedColor);
        }
        return currentTagColors.get(tagName);
    }

    /**
     * To access private String assignedColor for testing
     */
    public String getAssignedTagColor() {
        return this.assignedColor;
    }

    /**
     * To access private ArrayList usedColor for testing
     */
    public ArrayList getUsedColor() {
        return this.usedColors;
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        formClass.textProperty().bind(Bindings.convert(person.formClassProperty()));
        tags.getChildren().clear();
        initialiseTags(person);
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
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
    /**
     * Initialise the {@code person} tags
     *
     * @param person Person to be assigned tag colour.
     */
    private void initialiseTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);

            tagLabel.setStyle("-fx-background-color: " + obtainTagColors(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

}
```
