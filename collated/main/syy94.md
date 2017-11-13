# syy94
###### \java\seedu\address\commons\events\model\PersonEditedEvent.java
``` java

/**
 * Represents that a Person was edited successfully.
 */
public class PersonEditedEvent extends BaseEvent {
    private ReadOnlyPerson old;
    private ReadOnlyPerson newPerson;

    public PersonEditedEvent(ReadOnlyPerson old, ReadOnlyPerson newPerson) {
        assert assertIfNonNull(old, newPerson);
        this.old = old;
        this.newPerson = newPerson;
    }

    public ReadOnlyPerson getOldPerson() {
        return old;
    }

    public ReadOnlyPerson getNewPerson() {
        return newPerson;
    }

    private boolean assertIfNonNull(ReadOnlyPerson... persons) {
        return Arrays.stream(persons).allMatch(Objects::nonNull);
    }

    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     * <p>
     * For example, the event manager post method will call any posted event's toString and print it in the console.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\PasswordAcceptedEvent.java
``` java
/**
 * Represents the password is accepted.
 */
public class PasswordAcceptedEvent extends BaseEvent {
    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     * <p>
     * For example, the event manager post method will call any posted event's toString and print it in the console.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\util\ColorUtil.java
``` java

/**
 * From JavaFX docs, the CSS supports HSB color model instead of HSL color model.
 * <p>
 * HSL is used here as it is easier to randomise a certain color range as compared to
 * RGB.
 *
 * @see <a href="https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html#introlimitations">
 * </a>
 */
public class ColorUtil {
    private static Random random = new Random();
    private static final HashMap<String, String> USED_COLORS = new HashMap<>();

    /**
     * Generate a random Color in HSB format for CSS. This color will then be bounded to the object.
     * Calling this method with the same Object will return the existing color.
     *
     * @param object Object to get Colors for
     * @return String of color in HSB format for CSS. eg. hsb (360, 35%, 50%)
     */
    public static String getUniqueHsbColorForObject(Object object) {
        final String identifier = object.toString();
        if (!USED_COLORS.containsKey(identifier)) {
            USED_COLORS.put(identifier, getTagColor());
        }

        return USED_COLORS.get(identifier);
    }

    public static String getTagColor() {
        return "hsb(" + getHue() + "," + getSaturation() + "%,"
                + getBrightness() + "%)";
    }

    private static int getHue() {
        //0 to 360 degrees. Full spectrum of colors in 5 degrees increments.
        return random.nextInt(72) * 5;
    }

    private static int getSaturation() {
        //60 to 95% Saturation
        return random.nextInt(35) + 60;
    }

    private static int getBrightness() {
        //50 to 75% Brightness
        return random.nextInt(25) + 50;
    }
}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case and partial words are accepted.
     *   <br>examples:<pre>
     *       containsPartialTextIgnoreCase("ABc def", "abc") == true
     *       containsPartialTextIgnoreCase("ABc def", "DEF") == true
     *       containsPartialTextIgnoreCase("ABc def", "AB") == true
     *       containsPartialTextIgnoreCase("ABc def", "c de") == true
     *       containsPartialTextIgnoreCase("ABc def", "cde") == false
     *       </pre>
     * @param sentence cannot be null
     * @param text cannot be null, cannot be empty
     */
    public static boolean containsPartialTextIgnoreCase(String sentence, String text) {
        requireNonNull(sentence);
        requireNonNull(text);

        String preppedWord = text.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");

        String preppedSentence = sentence.trim().toLowerCase();

        return preppedSentence.contains(preppedWord);
    }
```
###### \java\seedu\address\logic\commands\commandmode\PasswordMode.java
``` java

/**
 * Represents the modes that PasswordCommand is able to do
 */
public abstract class PasswordMode {
    private String pass;

    public PasswordMode(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    protected boolean passExists() {
        return SecurityManager.passExists();
    }

    public abstract CommandResult execute() throws IOException, NoSuchAlgorithmException, CommandException;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordMode // instanceof handles nulls
                && this.pass.equals(((PasswordMode) other).pass)); // state check
    }
}

```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Set<CustomField> updatedFields = editPersonDescriptor.getFieldsList().orElse(personToEdit.getFields());

        final Set<Tag> updatedTags = new HashSet<>();

        if (!editPersonDescriptor.shouldClear()) {
            updatedTags.addAll(personToEdit.getTags());
        }

        editPersonDescriptor.getToAdd().ifPresent(updatedTags::addAll);
        editPersonDescriptor.getToRemove().ifPresent(updatedTags::removeAll);
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setFieldsList(Set<CustomField> fieldsList) {
            this.fieldsList = fieldsList;
        }

        public Optional<Set<CustomField>> getFieldsList() {
            return Optional.ofNullable(fieldsList);
        }

        public Optional<Set<Tag>> getToAdd() {
            return Optional.ofNullable(toAdd);
        }

        public Optional<Set<Tag>> getToRemove() {
            return Optional.ofNullable(toRemove);
        }

        public boolean shouldClear() {
            return clearTags;
        }

        public void setClearTags(boolean clearTags) {
            this.clearTags = clearTags;
        }

        public void setToAdd(Set<Tag> toAdd) {
            this.toAdd = toAdd;
        }

        public void setToRemove(Set<Tag> toRemove) {
            this.toRemove = toRemove;
        }
```
###### \java\seedu\address\logic\commands\PasswordCommand.java
``` java

/**
 * Adds, removes or edit password for the application.
 */
public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "password";
    public static final String COMMAND_ALIAS = "pwd";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds, removes or changes the password required to use "
            + "the application.\n"
            + "Set Password: " + COMMAND_WORD + " " + PREFIX_PASS + "PASS\n"
            + "Change Password: " + COMMAND_WORD + " " + PREFIX_PASS + "PASS" + " " + PREFIX_NEW_PASS + "NEW_PASS\n"
            + "Clear Password: " + COMMAND_WORD + " " + PREFIX_PASS + "PASS" + " " + PREFIX_CLEAR_PASS;

    public static final String MESSAGE_FILE_NOT_FOUND = "Password file not found.";
    public static final String MESSAGE_WRONG_PASS = "Wrong password.";
    public static final String MESSAGE_CHANGED_PASS = "Password Changed";
    public static final String MESSAGE_PASS_EXISTS = "Password Exists. To change password, use " + COMMAND_WORD + " "
            + PREFIX_PASS + "[existing password] "
            + PREFIX_NEW_PASS + "[new password] ";
    public static final String MESSAGE_CLEARED_PASS = "Password Cleared";
    public static final String MESSAGE_SET_PASS = "Password Set";
    public static final String MESSAGE_PASS_NOT_CHANGED = "Password not changed.";
    public static final String MESSAGE_NO_PASS_TO_CLEAR = "No existing password to clear";
    public static final String MESSAGE_NO_PASS_TO_CHANGE = "No existing password to change";
    public static final String MESSAGE_NO_SUCH_ALGORITHM = "NoSuchAlgorithmException should not be reached. \n"
            + "Contact the developers at: \n"
            + "https://github.com/CS2103AUG2017-F11-B1/main/issues";

    final PasswordMode mode;

    public PasswordCommand(PasswordMode mode) {
        this.mode = mode;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() throws CommandException {
        try {
            return mode.execute();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (NoSuchAlgorithmException e) {
            throw new CommandException(MESSAGE_NO_SUCH_ALGORITHM);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordCommand // instanceof handles nulls
                && this.mode.equals(((PasswordCommand) other).mode)); // state check
    }

    /**
     * PasswordMode to set Password
     */
    public static class SetPass extends PasswordMode {
        public SetPass(String pass) {
            super(pass);
        }

        @Override
        public CommandResult execute() throws IOException, NoSuchAlgorithmException, CommandException {
            if (passExists()) {
                throw new CommandException(MESSAGE_PASS_EXISTS);
            } else {
                SecurityManager.savePass(getPass());
                return new CommandResult(MESSAGE_SET_PASS);
            }
        }
    }

    /**
     * PasswordMode to clear Password
     */
    public static class ClearPass extends PasswordMode {
        public ClearPass(String pass) {
            super(pass);
        }

        @Override
        public CommandResult execute() throws IOException, NoSuchAlgorithmException, CommandException {
            if (passExists()) {
                try {
                    SecurityManager.removePass(getPass());
                } catch (WrongPasswordException e) {
                    throw new CommandException(MESSAGE_WRONG_PASS);
                }
                return new CommandResult(MESSAGE_CLEARED_PASS);
            } else {
                throw new CommandException(MESSAGE_NO_PASS_TO_CLEAR);
            }
        }
    }

    /**
     * PasswordMode to change Password
     */
    public static class ChangePass extends PasswordMode {

        private String newPass;

        public ChangePass(String pass, String newPass) {
            super(pass);
            this.newPass = newPass;
        }

        @Override
        public CommandResult execute() throws IOException, NoSuchAlgorithmException, CommandException {
            if (passExists()) {
                if (SecurityManager.checkPass(getPass())) {
                    SecurityManager.savePass(newPass);
                    return new CommandResult(MESSAGE_CHANGED_PASS);
                } else {
                    throw new CommandException(MESSAGE_WRONG_PASS + "\n" + MESSAGE_PASS_NOT_CHANGED);
                }
            } else {
                throw new CommandException(MESSAGE_NO_PASS_TO_CHANGE);
            }
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof ChangePass // instanceof handles nulls
                    && this.newPass.equals(((ChangePass) other).newPass)
                    && super.equals(other)); // state check
        }
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java

    /**
     * Takes in a password and checks with SecurityManager if the application should be unlocked.
     */
    private void tryUnlock(String commandText) throws CommandException {
        try {
            isLocked = !SecurityManager.unlock(commandText);
        } catch (IOException e) {
            throw new CommandException("Unable to open password file");
        } catch (NoSuchAlgorithmException e) {
            throw new CommandException(PasswordCommand.MESSAGE_NO_SUCH_ALGORITHM);
        }
    }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case PasswordCommand.COMMAND_WORD:
        case PasswordCommand.COMMAND_ALIAS:
            return new PasswordCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_ADD_TAG = new Prefix("+t/");
    public static final Prefix PREFIX_REMOVE_TAG = new Prefix("-t/");
    public static final Prefix PREFIX_CUSTOM_FIELD = new Prefix("c/");
    public static final Prefix PREFIX_CLEAR_TAG = new Prefix("clearTag/");
    public static final Prefix PREFIX_PASS = new Prefix("pwd/");
    public static final Prefix PREFIX_NEW_PASS = new Prefix("new/");
    public static final Prefix PREFIX_CLEAR_PASS = new Prefix("clearPwd/");
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Parses {@code List<String> fields} into a {@code List<CustomField>} if {@code fields} is non-empty.
     * If {@code fields} contain only one element which is an empty string, it will be parsed into a
     * {@code List<String>} containing zero CustomField.
     */
    private Optional<List<String>> parseCustomFieldsForSearch(List<String> fields) throws IllegalValueException {
        assert fields != null;

        if (fields.isEmpty()) {
            return Optional.empty();
        }
        List<String> fieldList = fields.size() == 1 && fields.contains("")
                ? Collections.emptyList() : fields;
        return Optional.of(fieldList);
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     *
     * Parses {@code Collection<String> fields} into a {@code Set<{@link CustomField}>}.
     */
    public static Set<CustomField> parseCustomFields(Collection<String> fields) throws IllegalValueException {
        requireNonNull(fields);

        final Set<CustomField> fieldSet = new HashSet<>();
        for (String field : fields) {
            fieldSet.add(parseCustomField(field));
        }
        return fieldSet;
    }

    private static CustomField parseCustomField(String field) throws IllegalValueException {
        requireNonNull(field);
        return new CustomField(field);
    }
```
###### \java\seedu\address\logic\parser\PasswordCommandParser.java
``` java
/**
 * Parses input arguments and creates a new PasswordCommand object
 */
public class PasswordCommandParser implements Parser<PasswordCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public PasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PASS, PREFIX_NEW_PASS, PREFIX_CLEAR_PASS);

        if (!arePrefixesPresent(argMultimap, PREFIX_PASS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NEW_PASS)) {
            final String newPass = argMultimap.getValue(PREFIX_NEW_PASS).get();

            requireNonNull(newPass);

            if (newPass.length() == 0) {
                throw new ParseException("New Password Cannot be empty!");
            }

            return new PasswordCommand(new PasswordCommand.ChangePass(
                    argMultimap.getValue(PREFIX_PASS).get(),
                    newPass
            ));
        } else if (arePrefixesPresent(argMultimap, PREFIX_CLEAR_PASS)) {
            return new PasswordCommand(new PasswordCommand.ClearPass(argMultimap.getValue(PREFIX_PASS).get()));
        } else {
            return new PasswordCommand(new PasswordCommand.SetPass(argMultimap.getValue(PREFIX_PASS).get()));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\customfields\CustomField.java
``` java
/**
 * Represents a field as created by the user in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidField(String)}
 */
public class CustomField {
    public static final String MESSAGE_FIELD_CONSTRAINTS =
            "Custom fields should be 2 alphanumeric strings separated by ':'";
    public static final String FIELD_VALIDATION_REGEX = "(\\p{Alnum}+ *)+: *(\\p{Alnum}+ *)+";

    public final String key;
    public final String value;

    public CustomField(String field) throws IllegalValueException {
        requireAllNonNull(field);
        final String trimmedField = field.trim();
        if (!isValidField(trimmedField)) {
            throw new IllegalValueException(MESSAGE_FIELD_CONSTRAINTS);
        }
        final String[] fieldData = trimmedField.split(":", 2);
        requireAllNonNull(fieldData[0], fieldData[1]);
        this.key = fieldData[0].trim().toUpperCase();
        this.value = fieldData[1].trim();
    }

    /**
     * Returns if a given string is a valid custom field.
     */
    public static boolean isValidField(String test) {
        return test.matches(FIELD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CustomField)) {
            return false;
        }

        // state check
        final CustomField other = (CustomField) obj;
        return key.equals(other.key) && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }

    /**
     * returns a String representation of custom fields as a sentence.
     *
     * @see seedu.address.model.person.PersonContainsKeywordsPredicate#test(ReadOnlyPerson)
     */
    public String asData() {
        return key + " " + value;
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setFields(Set<CustomField> replacement) {
        fieldsList.set(new CustomFieldsList(replacement));
    }

    @Override
    public ObjectProperty<CustomFieldsList> fieldsListProperty() {
        return fieldsList;
    }

    @Override
    public Set<CustomField> getFields() {
        return Collections.unmodifiableSet(fieldsList.get().toSet());
    }
```
###### \java\seedu\address\storage\SecurityManager.java
``` java

/**
 * Accesses and edits the password.
 */
public class SecurityManager {
    private static String path = "data/pass";
    private static final Logger logger = LogsCenter.getLogger(SecurityManager.class);

    public static void setPasswordStoragePath(String path) {
        SecurityManager.path = path;
    }

    /**
     * Saves password
     */
    public static void savePass(String pass) throws IOException, NoSuchAlgorithmException {
        requireNonNull(pass);
        final File passFile = new File(path);

        final String trimmedPass = pass.trim();

        if (trimmedPass.length() != 0) {
            final String encodedPass = encryptPass(trimmedPass);
            FileUtil.createIfMissing(passFile);
            FileUtil.writeToFile(passFile, encodedPass);
        }
    }

    /**
     * Simple Hashing of the password
     */
    private static String encryptPass(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Checks if given password is correct and posts and event to enable the application features.
     */
    public static boolean unlock(String pass) throws IOException, NoSuchAlgorithmException {
        boolean isUnlocked = checkPass(pass);
        if (isUnlocked) {
            logger.info("----------[PASSWORD ACCEPTED]");
            EventsCenter.getInstance().post(new PasswordAcceptedEvent());
        } else {
            logger.info("----------[PASSWORD REJECTED]");
        }
        return isUnlocked;
    }

    /**
     * Checks if given password is correct.
     */
    public static boolean checkPass(String pass) throws IOException, NoSuchAlgorithmException {
        return getPass().equals(encryptPass(pass));
    }

    /**
     * Removes the need for password on application start after checking if the user knows the existing password.
     */
    public static void removePass(String existingPass) throws IOException, NoSuchAlgorithmException,
            WrongPasswordException {
        if (checkPass(existingPass)) {
            final File passFile = new File(path);
            if (FileUtil.isFileExists(passFile)) {
                passFile.delete();
            }
        } else {
            throw new WrongPasswordException();
        }
    }

    /**
     * Checks if there is a password.
     */
    public static boolean passExists() {
        final File passFile = new File(path);

        return FileUtil.isFileExists(passFile);
    }

    /**
     * Retrieves the Password.
     */
    private static String getPass() throws IOException {
        final File file = new File(path);
        return FileUtil.readFromFile(file);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedCustomField.java
``` java
/**
 * JAXB-friendly adapted version of the Field.
 */
public class XmlAdaptedCustomField {
    @XmlValue
    private String customField;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCustomField() {
    }

    public XmlAdaptedCustomField(CustomField field) {
        this.customField = field.toString();
    }

    public CustomField toModelType() throws IllegalValueException {
        return new CustomField(customField);
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Creates and add the tags belonging to the person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            final Label label = new Label(tag.tagName);
            label.setStyle("-fx-background-color: " + ColorUtil.getUniqueHsbColorForObject(tag));
            tags.getChildren().add(label);
        });
    }
```
###### \java\seedu\address\ui\PersonDetailsPanel.java
``` java

/**
 * Panel containing the details of the selected person.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";
    private static final String MESSAGE_ENTER_PASS = "Please enter the password";
    private static final String MESSAGE_SELECT_PERSON = "Select a person to view their details.";
    private static final String LOG_IGNORED = "Currently not showing edited Person. Panel not updated";
    private static final String LOG_CHANGED = "Panel updated with new Person";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label group;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox fields;

    public PersonDetailsPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        setStartMessage();
    }

    private void setStartMessage() {
        if (SecurityManager.passExists()) {
            group.setText(MESSAGE_ENTER_PASS);
        } else {
            group.setText(MESSAGE_SELECT_PERSON);
        }
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        group.textProperty().bind(Bindings.convert(person.groupProperty()));
        group.setStyle("-fx-background-color: " + ColorUtil.getUniqueHsbColorForObject(person.getGroup()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));

        person.fieldsListProperty().addListener((observable, oldValue, newValue) -> regenerateFields(person));

        person.tagProperty().addListener((observable, oldValue, newValue) -> regenerateTags(person));
    }

    /**
     * Creates and add the tags belonging to the person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            final Label label = new Label(tag.tagName);
            label.setStyle("-fx-background-color: " + ColorUtil.getUniqueHsbColorForObject(tag));
            tags.getChildren().add(label);
        });
    }

    /**
     * Creates and add the tags belonging to the person
     */
    private void initFields(ReadOnlyPerson person) {
        person.getFields().forEach(field -> {
            final Label label = new Label(field.key + ": " + field.value);
            label.getStyleClass().add("cell_small_label");
            fields.getChildren().add(label);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDetailsPanel)) {
            return false;
        }

        // state check
        PersonDetailsPanel panel = (PersonDetailsPanel) other;
        return id.getText().equals(panel.id.getText())
                && person.equals(panel.person);
    }

    private void setPerson(ReadOnlyPerson person) {
        this.person = person;
        bindListeners(person);
        regenerateTags(person);
        regenerateFields(person);
    }

    private void regenerateTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        initTags(person);
    }

    private void regenerateFields(ReadOnlyPerson person) {
        fields.getChildren().clear();
        initFields(person);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setPerson(event.getNewSelection().person);
    }

    @Subscribe
    private void handlePersonEditedEvent(PersonEditedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getOldPerson().equals(person)) {
            setPerson(event.getNewPerson());
            logger.info(LOG_CHANGED);
        } else {
            logger.info(LOG_IGNORED);
        }
    }

    @Subscribe
    private void handlePasswordAcceptedEvent(PasswordAcceptedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        group.setText(MESSAGE_SELECT_PERSON);
    }
}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
        this.personList = personList;
        if (!SecurityManager.passExists()) {
            init();
        }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    private void init() {
        setConnections(personList);
        registerAsAnEventHandler(this);
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handlePasswordAcceptedEvent(PasswordAcceptedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Password Accepted. Showing Persons"));
        init();
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
        if (SecurityManager.passExists()) {
            displayed.setValue("Enter Password:");
        }
```
###### \resources\view\DarkTheme.css
``` css
#group {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 10 1 10;
    -fx-border-radius: 500;
    -fx-background-radius: 500;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
    <VBox>
        <StackPane fx:id="personDetailsPlaceholder" prefWidth="340">
          <padding>
            <Insets top="10" right="10" bottom="10" left="10" />
          </padding>
        </StackPane>

      <StackPane fx:id="browserPlaceholder" prefWidth="340" maxHeight="400">
        <padding>
          <Insets top="10" right="10" bottom="10" left="10" />
        </padding>
      </StackPane>
    </VBox>
```
###### \resources\view\PersonDetailsPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<ScrollPane minHeight="250" VBox.vgrow="ALWAYS" xmlns="http://javafx.com/javafx/8.0.121"
            xmlns:fx="http://javafx.com/fxml/1">

    <VBox>
        <padding>
            <Insets bottom="5" left="15" right="5" top="5"/>
        </padding>
        <HBox alignment="CENTER_LEFT" spacing="5">
            <Label fx:id="name" styleClass="cell_big_label"/>
            <Label fx:id="group" styleClass="cell_big_label" text="Select a person to view their details."/>
            <VBox.margin>
                <Insets/>
            </VBox.margin>
        </HBox>
        <FlowPane fx:id="tags">
            <padding>
                <Insets bottom="4.0" top="4.0"/>
            </padding>
        </FlowPane>
        <Label fx:id="phone" styleClass="cell_small_label"/>
        <Label fx:id="address" styleClass="cell_small_label"/>
        <Label fx:id="email" styleClass="cell_small_label"/>
        <!--fields are generated and added in the codes as they are of a variable number-->
        <VBox fx:id="fields" styleClass="cell_small_label"/>
    </VBox>

</ScrollPane>
```
