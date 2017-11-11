# jo-lyn
###### \java\seedu\address\commons\events\ui\ClearPersonDetailPanelRequestEvent.java
``` java
/**
 * Indicates a request to clear the person detail panel.
 */
public class ClearPersonDetailPanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\PersonEditedEvent.java
``` java
/**
 * Indicates that a person has been edited.
 */
public class PersonEditedEvent extends BaseEvent {

    public final ReadOnlyPerson editedPerson;
    public final int targetIndex;

    public PersonEditedEvent(ReadOnlyPerson person, Index index) {
        editedPerson = person;
        targetIndex = index.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    /**
     * Jumps to the {@code addedPerson} in the person list.
     */
    private void jumpToAddedPerson(ReadOnlyPerson addedPerson) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getIndex(addedPerson)));
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    /**
     * Raises a person edited event to update display.
     */
    private void raisePersonEditedEvent(ReadOnlyPerson editedPerson) {
        EventsCenter.getInstance().post(new PersonEditedEvent(editedPerson, model.getIndex(editedPerson)));
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Adds a remark to an existing person in the rolodex.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "note", "rmk", "comment"));

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the rolodex.";

    private final Index index;
    private final Remark remark;

    /**
     * Changes the remark of an existing person in the rolodex.
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getLatestPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Returns the success message for the remark command.
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles null
        if (!(other instanceof RemarkCommand)) {
            return false;
        }
        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object.
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     *
     * @throws ParseArgsException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseArgsException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseArgsException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
        for (ReadOnlyPerson person: rolodex.getPersonList()) {
            Person newPerson = new Person(person);

            Set<Tag> newTags = new HashSet<>(newPerson.getTags());
            newTags.remove(tag);
            newPerson.setTags(newTags);

            rolodex.updatePerson(person, newPerson);
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns the index of the given person.
     */
    public Index getIndex(ReadOnlyPerson target) {
        return Index.fromZeroBased(sortedPersons.indexOf(target));
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    @Override
    public Remark getRemark() {
        return remark.get();
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the rolodex.
 * Guarantees: immutable, is always valid
 */
public class Remark {
    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles null
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(Remark other) {
        return toString().compareTo(other.toString());
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String remark;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    public void setFocus() {
        commandTextField.requestFocus();
    }

    public boolean isFocused() {
        return commandTextField.isFocused();
    }

    /**
     * Loads images for keyboard icons in the command box.
     */
    private void loadKeyboardIcons() {
        keyboardIdle = new Image(getClass().getResourceAsStream("/images/keyboard.png"));
        keyboardTyping = new Image(getClass().getResourceAsStream("/images/keyboardTyping.png"));
        keyboardError = new Image(getClass().getResourceAsStream("/images/keyboardError.png"));
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Changes {@code Image keyboardTyping} icon to {@code Image keyboardIdle} when there is no change
     * to text field after some time.
     */
    private void updateKeyboardIcon() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        keyboardIcon.setImage(keyboardTyping);

        pause.setOnFinished(event -> {
            if (!styleClass.contains(ERROR_STYLE_CLASS)) {
                keyboardIcon.setImage(keyboardIdle);
            }
        });
        pause.playFromStart();
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    private void setErrorKeyboardIcon() {
        keyboardIcon.setImage(keyboardError);
    }
```
###### \java\seedu\address\ui\KeyListener.java
``` java
/**
 * Listens to key events in the main window.
 */
public class KeyListener {

    private Region mainNode;
    private PersonListPanel personListPanel;
    private CommandBox commandBox;
    private ResultDisplay resultDisplay;

    public KeyListener(Region mainNode, ResultDisplay resultDisplay, PersonListPanel personListPanel,
                       CommandBox commandBox) {
        this.mainNode = mainNode;
        this.personListPanel = personListPanel;
        this.commandBox = commandBox;
        this.resultDisplay = resultDisplay;
    }

    /**
     * Handles key press events from the user.
     */
    public void handleKeyPress() {
        mainNode.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (isNotScrolling(event)) {
                commandBox.processInput();
            }
            executeKeyEvent(event);
        });
    }
```
###### \java\seedu\address\ui\KeyListener.java
``` java
    /**
     * Executes the {@code keyEvent} matching an assigned {@code KeyCombination}.
     */
    private void executeKeyEvent(KeyEvent keyEvent) {

        if (KEY_COMBINATION_FOCUS_PERSON_LIST.match(keyEvent)
                || KEY_COMBINATION_FOCUS_PERSON_LIST_ALT.match(keyEvent)) {
            personListPanel.setFocus();

        } else if (KEY_COMBINATION_FOCUS_COMMAND_BOX.match(keyEvent)) {
            commandBox.setFocus();

        } else if (KEY_COMBINATION_FOCUS_RESULT_DISPLAY.match(keyEvent)) {
            resultDisplay.setFocus();

        } else if (KEY_COMBINATION_DELETE_SELECTION.match(keyEvent)) {
            deleteSelectedContact();

        } else if (KEY_COMBINATION_CLEAR.match(keyEvent)) {
            executeCommand(ClearCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_HISTORY.match(keyEvent)) {
            executeCommand(HistoryCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_UNDO.match(keyEvent)) {
            executeCommand(UndoCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_REDO.match(keyEvent)) {
            executeCommand(RedoCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_LIST.match(keyEvent)) {
            executeCommand(ListCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_OPEN_FILE.match(keyEvent)) {
            executeCommand(OpenRolodexCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_NEW_FILE.match(keyEvent)) {
            executeCommand(NewRolodexCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_ADD.match(keyEvent)) {
            displayCommandFormat(AddCommand.FORMAT);

        } else if (KEY_COMBINATION_EDIT.match(keyEvent)) {
            displayCommandFormat(EditCommand.FORMAT);

        } else if (KEY_COMBINATION_FIND.match(keyEvent)) {
            displayCommandFormat(FindCommand.FORMAT);

        } else if (KEY_COMBINATION_SELECT.match(keyEvent)) {
            displayCommandFormat(SelectCommand.FORMAT);

        } else if (KEY_COMBINATION_DELETE.match(keyEvent)) {
            displayCommandFormat(DeleteCommand.FORMAT);

        } else {
            // no key combination matches, do nothing
        }
    }

    /**
     * Executes command triggered by key presses.
     */
    private void executeCommand(String command) {
        if (command.equals(OpenRolodexCommand.COMMAND_WORD) || command.equals(NewRolodexCommand.COMMAND_WORD)) {
            commandBox.replaceText(command + " ");
        } else {
            commandBox.replaceText(command);
            commandBox.handleCommandInputChanged();
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Sets key listeners for handling keyboard shortcuts.
     */
    protected void setKeyListeners() {
        KeyListener keyListener = new KeyListener(getRoot(), resultDisplay, personListPanel, commandBox);
        keyListener.handleKeyPress();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Proportions the split pane divider position according to window size.
     */
    private void setSplitPaneDividerPosition() {

        primaryStage.showingProperty().addListener((observable, oldValue, newValue) ->
                splitPane.setDividerPositions(SPLIT_PANE_DIVIDER_POSITION));

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) ->
                splitPane.setDividerPositions(SPLIT_PANE_DIVIDER_POSITION));
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initializes the person card with the person details.
     */
    private void initializePerson(ReadOnlyPerson person, int displayedIndex) {
        id.setText(Integer.toString(displayedIndex));
        setAvatar(person);
        setTags(person);
    }

    private void setAvatar(ReadOnlyPerson person) {
        initial.setText(Avatar.getInitial(person.getName().fullName));
        avatar.setFill(Paint.valueOf(Avatar.getColor(person.getName().fullName)));
    }

    private void setTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
```
###### \java\seedu\address\ui\PersonDetailPanel.java
``` java
/**
 * The Person Detail Panel of the App.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Circle avatar; // TODO: Implement support for uploading picture from local directory
    @FXML
    private Label initial;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tagsWithBorder;
    @FXML
    private ImageView iconPhone;
    @FXML
    private ImageView iconEmail;
    @FXML
    private ImageView iconAddress;

    public PersonDetailPanel() {
        super(FXML);
        showEmptyPanel();
        registerAsAnEventHandler(this);
    }

    /**
     * Displays an empty panel.
     */
    private void showEmptyPanel() {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        remark.setText("");
        initial.setText("");
        tagsWithBorder.getChildren().clear();
        avatar.setFill(TRANSPARENT);
        hideIcons();
    }

    /**
     * Shows the details of the person on the panel.
     */
    private void showPersonDetails(ReadOnlyPerson person) {
        setAvatar(person);
        setTextFields(person);
        setTags(person);
        showIcons();
    }

    private void setAvatar(ReadOnlyPerson person) {
        initial.setText(Avatar.getInitial(person.getName().fullName));
        avatar.setFill(Paint.valueOf(Avatar.getColor(person.getName().fullName)));
    }

    private void setTextFields(ReadOnlyPerson person) {
        name.setText(person.getName().toString());
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());
        setRemark(person);
    }

    private void setTags(ReadOnlyPerson person) {
        tagsWithBorder.getChildren().clear();
        person.getTags().forEach(tag -> tagsWithBorder.getChildren().add(new Label(tag.tagName)));
    }

    private void setRemark(ReadOnlyPerson person) {
        remark.setText(person.getRemark().toString());

        if (person.getRemark().isEmpty()) {
            remark.setManaged(false);
        } else {
            remark.setManaged(true);
        }
    }

    private void hideIcons() {
        iconPhone.setVisible(false);
        iconEmail.setVisible(false);
        iconAddress.setVisible(false);
    }

    private void showIcons() {
        iconPhone.setVisible(true);
        iconEmail.setVisible(true);
        iconAddress.setVisible(true);
    }

    /**
     * Shows person details on the panel when a person is selected.
     */
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }

    /**
     * Updates the panel when the details of the selected person is changed.
     */
    @Subscribe
    private void handlePersonDetailsChangedEvent(PersonEditedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.editedPerson);
    }

    @Subscribe
    private void handlePersonListClearedEvent(ClearPersonDetailPanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showEmptyPanel();
    }
}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    public void setFocus() {
        personListView.requestFocus();
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handleJumpToListRequestEvent(PersonEditedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    public static final String WELCOME_TEXT = "Welcome to Rolodex! If you need somewhere to start, "
            + "type \"help\" to view the user guide.";
```
###### \java\seedu\address\ui\util\Avatar.java
``` java
/**
 * Stores the information of all person avatars.
 */
public class Avatar {
    private static HashMap<String, String> avatarColors = new HashMap<>();
    private static Random random = new Random();
    private static String[] colors = ColorsUtil.getColors();

    private static final int COLOR_VALUE_MAX = 255;
    private static final int COLOR_VALUE_MIN = 0;
    private static final int COLOR_OFFSET_BOUND = 50;

    private Avatar() {}

    public static String getColor(String name) {
        if (!avatarColors.containsKey(name)) {
            Color defaultColor = Color.decode(colors[random.nextInt(colors.length)]);
            String newColor = generateNewColor(defaultColor);
            avatarColors.put(name, newColor);
        }
        return avatarColors.get(name);
    }

    public static String getInitial(String name) {
        return name.substring(0, 1);
    }

    /**
     * Generates a new color with a random offset from {@code Color defaultColor}.
     *
     * @return The new color in hexadecimal {@code String} format
     */
    private static String generateNewColor(Color defaultColor) {
        int r = defaultColor.getRed();
        int g = defaultColor.getGreen();
        int b = defaultColor.getBlue();

        int newR = Math.max(COLOR_VALUE_MIN, Math.min(COLOR_VALUE_MAX, (r + generateOffset())));
        int newG = Math.max(COLOR_VALUE_MIN, Math.min(COLOR_VALUE_MAX, (g + generateOffset())));
        int newB = Math.max(COLOR_VALUE_MIN, Math.min(COLOR_VALUE_MAX, (b + generateOffset())));

        return String.format("#%02x%02x%02x", newR, newG, newB);
    }

    /**
     * Generates a random offset in the range [-1 * COLOR_OFFSET_BOUND, COLOR_OFFSET_BOUND].
     */
    private static int generateOffset() {
        return random.nextInt(COLOR_OFFSET_BOUND * 2) - COLOR_OFFSET_BOUND;
    }
}
```
###### \java\seedu\address\ui\util\ColorsUtil.java
``` java
/**
 * A utility class for colors used in UI.
 */
public class ColorsUtil {
    public static final String RED = "#d06651";
    public static final String YELLOW = "#f1c40f";
    public static final String BLUE = "#3498db";
    public static final String TEAL = "#1abc9c";
    public static final String GREEN = "#2ecc71";
    public static final String PURPLE = "#9b59b6";

    private ColorsUtil() {} // prevents instantiation

    public static String[] getColors() {
        return new String[] { RED, YELLOW, BLUE, TEAL, GREEN, PURPLE };
    }
}
```
###### \java\seedu\address\ui\util\KeyListenerUtil.java
``` java
/**
 * A utility class for mapping key events.
 */
public class KeyListenerUtil {

    // Non-command events
    public static final KeyCombination KEY_COMBINATION_FOCUS_PERSON_LIST = KeyCombination.valueOf("Esc");
    public static final KeyCombination KEY_COMBINATION_FOCUS_PERSON_LIST_ALT = KeyCombination.valueOf("Ctrl+Left");
    public static final KeyCombination KEY_COMBINATION_FOCUS_COMMAND_BOX = KeyCombination.valueOf("Enter");
    public static final KeyCombination KEY_COMBINATION_FOCUS_RESULT_DISPLAY = KeyCombination.valueOf("Ctrl+Right");
    public static final KeyCombination KEY_COMBINATION_DELETE_SELECTION = KeyCombination.valueOf("Ctrl+D");

    // Command events
    public static final KeyCombination KEY_COMBINATION_CLEAR = KeyCombination.valueOf(ClearCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_HISTORY = KeyCombination.valueOf(HistoryCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_UNDO = KeyCombination.valueOf(UndoCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_REDO = KeyCombination.valueOf(RedoCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_LIST = KeyCombination.valueOf(ListCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_OPEN_FILE = KeyCombination
            .valueOf(OpenRolodexCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_NEW_FILE = KeyCombination
            .valueOf(NewRolodexCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_ADD = KeyCombination.valueOf(AddCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_EDIT = KeyCombination.valueOf(EditCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_FIND = KeyCombination.valueOf(FindCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_SELECT = KeyCombination.valueOf(SelectCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_DELETE = KeyCombination.valueOf(DeleteCommand.COMMAND_HOTKEY);

    public static final Set<KeyCombination> POSSIBLE_KEY_COMBINATIONS =
            new HashSet<>(Arrays.asList(
                    KEY_COMBINATION_FOCUS_PERSON_LIST,
                    KEY_COMBINATION_FOCUS_PERSON_LIST_ALT,
                    KEY_COMBINATION_FOCUS_COMMAND_BOX,
                    KEY_COMBINATION_FOCUS_RESULT_DISPLAY,
                    KEY_COMBINATION_DELETE_SELECTION,
                    KEY_COMBINATION_CLEAR,
                    KEY_COMBINATION_HISTORY,
                    KEY_COMBINATION_UNDO,
                    KEY_COMBINATION_REDO,
                    KEY_COMBINATION_LIST,
                    KEY_COMBINATION_OPEN_FILE,
                    KEY_COMBINATION_NEW_FILE,
                    KEY_COMBINATION_ADD,
                    KEY_COMBINATION_EDIT,
                    KEY_COMBINATION_FIND,
                    KEY_COMBINATION_SELECT,
                    KEY_COMBINATION_DELETE
            ));
}
```
###### \resources\view\LightTheme.css
``` css
@font-face {
    src: url("fonts/segoe-ui.ttf"),
         url("fonts/segou-ui-light.ttf"),
         url("fonts/segou-ui-semibold.otf");
}

.root {
    -fx-background-color: #f7f5f4;
    -fx-accent: derive(#f7f5f4, -10%);
    -fx-focus-color: derive(#f7f5f4, -10%);
}

.label {
    -fx-font-size: 15px;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 15px;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: gray;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 42px;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: gray;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 16px;
    -fx-font-family: "Segoe UI Semibold";
}
```
###### \resources\view\LightTheme.css
``` css
.table-view {
    -fx-base: #f7f5f4;
    -fx-control-inner-background: #f7f5f4;
    -fx-background-color: #f7f5f4;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}
```
###### \resources\view\LightTheme.css
``` css
.table-view .column-header .label {
    -fx-font-size: 26px;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: gray;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: transparent;
    -fx-border-color: transparent;
}

.split-pane {
    -fx-background-color: transparent;
}
```
###### \resources\view\LightTheme.css
``` css
.list-cell:filled {
    -fx-background-color: #ffffff;
}

.list-cell:filled:selected {
    -fx-background-color: derive(#f7f5f4, -5%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: transparent;
}

.cell-name-label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #444344;
}

.cell-id-label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: derive(gray, 35%);
    -fx-font-size: 15px;
}

.person-big-label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 26px;
    -fx-text-fill: #444344;
}

.person-small-label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 15px;
    -fx-text-fill: #848484;
}

.anchor-pane {
     -fx-background-color: transparent;
}

.pane-with-border {
     -fx-background-color: transparent;
     -fx-border-top-width: 1px;
}

.command-pane {
    -fx-background-color: #ffffff;
    -fx-background-radius: 18 18 18 18;
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 1, 2);
}

.result-pane {
     -fx-background-color: #ffffff;
     -fx-background-radius: 10 10 10 10;
     -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
}

.person-detail-pane {
    -fx-background-color: #ffffff;
    -fx-background-radius: 10 10 10 10;
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
}

.status-bar {
    -fx-background-color: derive(#f7f5f4, 80%);
    -fx-padding: 0 10 0 10;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: derive(#716f70, -10%);
    -fx-font-size: 13px;
}

.status-bar-with-border {
    -fx-background-color: derive(#f7f5f4, 30%);
    -fx-border-color: derive(#f7f5f4, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: gray;
}

.grid-pane {
    -fx-background-color: derive(#f7f5f4, 30%);
    -fx-border-color: derive(#f7f5f4, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#f7f5f4, 80%);
}

.context-menu {
    -fx-background-color: derive(#f7f5f4, 50%);
}

.context-menu .label {
    -fx-text-fill: gray;
}

.menu-bar {
    -fx-background-color: derive(#f7f5f4, 80%);
}

.menu-bar .label {
    -fx-font-size: 16px;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: derive(#716f70, -10%);
    -fx-opacity: 0.9;
}
```
###### \resources\view\LightTheme.css
``` css
.dialog-pane {
    -fx-background-color: #f7f5f4;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #f7f5f4;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: gray;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#f7f5f4, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: gray;
    -fx-text-fill: gray;
}

.scroll-bar {
    -fx-background-color: derive(#f7f5f4, -5%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#f7f5f4, -25%);
    -fx-background-insets: 1;
    -fx-background-radius: 18 18 18 18;
}
```
###### \resources\view\LightTheme.css
``` css
.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 8 3 8 3;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 3 8 3 8;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#personListView {
    -fx-background-radius: 10 10 10 10;
}

#personListPanel {
    -fx-background-color: #ffffff;
    -fx-background-radius: 10 10 10 10;
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, -2, 2);
}

#initialSmall {
    -fx-font-family: "Roboto";
    -fx-text-fill: #ffffff;
    -fx-font-size: 25px;
}

#initialBig {
    -fx-font-family: "Roboto";
    -fx-text-fill: #ffffff;
    -fx-font-size: 65px;
}

#commandTextField {
    -fx-background-color: transparent;
    -fx-background-insets: 0;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 17px;
    -fx-text-fill: gray;
}
```
###### \resources\view\LightTheme.css
``` css
#resultDisplay {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 16px;
    -fx-text-fill: #746d75;
    -fx-prompt-text-fill: #746d75;
}

#resultDisplay .content {
    -fx-background-color: transparent, #ffffff, transparent, #ffffff;
    -fx-background-radius: 0;
}

#tags, #tagsWithBorder {
    -fx-hgap: 7;
    -fx-vgap: 5;
}

#tags .label {
    -fx-font-family: "Segoe UI", Optima;
    -fx-text-fill: derive(gray, 20%);
    -fx-padding: 1 0 1 0;
    -fx-font-size: 14px;
}

#tagsWithBorder .label {
    -fx-font-family: "Segoe UI", Optima;
    -fx-text-fill: derive(gray, 20%);
    -fx-padding: 1 5 1 5;
    -fx-border-color: derive(gray, 20%);
    -fx-border-width: 1;
    -fx-border-radius: 10;
    -fx-font-size: 13px;
}
```
