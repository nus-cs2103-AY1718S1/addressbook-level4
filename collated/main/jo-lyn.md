# jo-lyn
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
###### \java\seedu\address\ui\CommandBox.java
``` java
    private static final int MILLISECONDS_TIME_SINCE_TYPING = 300;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    private Image keyboardIdle;
    private Image keyboardTyping;
    private Image keyboardError;
    private PauseTransition pause;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        // within CommandBox constructor
        loadKeyboardIcons();
        keyboardIcon.setImage(keyboardIdle);
        pause = new PauseTransition(Duration.millis(MILLISECONDS_TIME_SINCE_TYPING));
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
     * Load images for keyboard icons in the command box.
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
     * Sets the command box style to use the default style.
     * {@code keyboardTyping} icon changes to {@code keyboardIdle} when there is no change
     * to text field after some time.
     */
    private void updateKeyboardIconAndStyle() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        keyboardIcon.setImage(keyboardTyping);

        pause.setOnFinished(event -> {
            if (!styleClass.contains(ERROR_STYLE_CLASS)) {
                keyboardIcon.setImage(keyboardIdle);
            }
        });
        pause.playFromStart();

        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Sets the command box style to indicate a failed command.
     */
    public void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
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
     * Handles key press events
     */
    public void handleKeyPress() {

        mainNode.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            handleCommandBoxEvents(event);
            executeKeyEvent(event);
        });
    }
```
###### \java\seedu\address\ui\KeyListener.java
``` java
    /**
     * Executes the key event.
     * Matches {@code keyEvent} with non-command events before handling keys mapped to command words.
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
            executeCommand(OpenCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_NEW_FILE.match(keyEvent)) {
            executeCommand(NewCommand.COMMAND_WORD);

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
     * Handles execution of command
     */
    private void executeCommand(String command) {
        if (command.equals(OpenCommand.COMMAND_WORD) || command.equals(NewCommand.COMMAND_WORD)) {
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
     * Set key listeners for handling keyboard shortcuts.
     */
    protected void setKeyListeners() {
        KeyListener keyListener = new KeyListener(getRoot(), resultDisplay, personListPanel, commandBox);
        keyListener.handleKeyPress();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        PersonDetailPanel personDetailPanel = new PersonDetailPanel();
        personDetailPlaceholder.getChildren().add(personDetailPanel.getRoot());
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Proportions the split pane divider position according to window size
     */
    private void setSplitPaneDividerProperty() {

        primaryStage.showingProperty().addListener((observable, oldValue, newValue) ->
                splitPane.setDividerPositions(0.35f));

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) ->
                splitPane.setDividerPositions(0.35f));
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label initial;
    @FXML
    private Circle avatar; // TODO: Implement support for uploading picture from local directory
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initialise the person card with the person details.
     */
    private void initialisePerson(ReadOnlyPerson person, int displayedIndex) {
        id.setText(Integer.toString(displayedIndex));

        initial.setText(Avatar.getInitial(person.getName().fullName));
        avatar.setFill(Paint.valueOf(Avatar.getColor(person.getName().fullName)));

        setTags(person);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
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

    public static final String PERSON_PHONE_ICON = "☎  ";
    public static final String PERSON_ADDRESS_ICON = "\uD83C\uDFE0  ";
    public static final String PERSON_EMAIL_ICON = "\uD83D\uDCE7  ";

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
    private FlowPane tags;

    public PersonDetailPanel() {
        super(FXML);
        initialise();
        registerAsAnEventHandler(this);
    }

    /**
     * Initialize the panel with empty fields
     */
    private void initialise() {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        initial.setText("");
        avatar.setFill(Color.TRANSPARENT);
    }

    /**
     * Shows the details of the person on the panel
     */
    private void showPersonDetails(ReadOnlyPerson person) {

        initial.setText(Avatar.getInitial(person.getName().fullName));
        avatar.setFill(Paint.valueOf(Avatar.getColor(person.getName().fullName)));

        setTextFields(person);
        setTags(person);
    }

    private void setTextFields(ReadOnlyPerson person) {
        name.setText(person.getName().toString());
        phone.setText(PERSON_PHONE_ICON + person.getPhone().toString());
        address.setText(PERSON_ADDRESS_ICON + person.getAddress().toString());
        email.setText(PERSON_EMAIL_ICON + person.getEmail().toString());
    }

    private void setTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Shows person details on the panel when a person is selected
     */
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }
}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    public void setFocus() {
        personListView.requestFocus();
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
            mainWindow.setKeyListeners();
```
###### \java\seedu\address\ui\util\Avatar.java
``` java
/**
 * Stores the information of all person avatars
 */
public class Avatar {
    private static HashMap<String, String> avatarColors = new HashMap<>();
    private static Random random = new Random();
    private static String[] colors = ColorsUtil.getColors();

    private Avatar() {}

    public static String getColor(String name) {
        if (!avatarColors.containsKey(name)) {
            avatarColors.put(name, colors[random.nextInt(colors.length)]);
        }
        return avatarColors.get(name);
    }

    public static String getInitial(String name) {
        return name.substring(0, 1);
    }
}
```
###### \java\seedu\address\ui\util\ColorsUtil.java
``` java
/**
 * A utility class for colors used in UI.
 */
public class ColorsUtil {
    public static final String COLORS_RED = "#d06651";
    public static final String COLORS_YELLOW = "#f1c40f";
    public static final String COLORS_BLUE = "#3498db";
    public static final String COLORS_TEAL = "#1abc9c";
    public static final String COLORS_GREEN = "#2ecc71";
    public static final String COLORS_PURPLE = "#9b59b6";

    private ColorsUtil() {} // prevents instantiation

    public static String[] getColors() {
        return new String[] { COLORS_RED, COLORS_YELLOW, COLORS_BLUE, COLORS_TEAL, COLORS_GREEN, COLORS_PURPLE };
    }
}
```
###### \java\seedu\address\ui\util\KeyListenerUtil.java
``` java
/**
 * A utility class for mapping key events.
 */
public class KeyListenerUtil {

    public static final KeyCombination KEY_COMBINATION_FOCUS_PERSON_LIST = KeyCombination.valueOf("Esc");
    public static final KeyCombination KEY_COMBINATION_FOCUS_PERSON_LIST_ALT = KeyCombination.valueOf("Ctrl+Left");
    public static final KeyCombination KEY_COMBINATION_FOCUS_COMMAND_BOX = KeyCombination.valueOf("Enter");
    public static final KeyCombination KEY_COMBINATION_FOCUS_RESULT_DISPLAY = KeyCombination.valueOf("Ctrl+Right");
    public static final KeyCombination KEY_COMBINATION_DELETE_SELECTION = KeyCombination.valueOf("Ctrl+D");
    public static final KeyCombination KEY_COMBINATION_CLEAR = KeyCombination.valueOf(ClearCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_HISTORY = KeyCombination.valueOf(HistoryCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_UNDO = KeyCombination.valueOf(UndoCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_REDO = KeyCombination.valueOf(RedoCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_LIST = KeyCombination.valueOf(ListCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_OPEN_FILE = KeyCombination.valueOf(OpenCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_NEW_FILE = KeyCombination.valueOf(NewCommand.COMMAND_HOTKEY);
```
###### \java\seedu\address\ui\util\KeyListenerUtil.java
``` java
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
```
###### \resources\view\LightTheme.css
``` css
.root {
    -fx-background-color: #f7f5f4;
    -fx-accent: derive(#f7f5f4, -10%);
    -fx-focus-color: derive(#f7f5f4, -10%);
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: gray;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: gray;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
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
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: gray;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}
```
###### \resources\view\LightTheme.css
``` css
.split-pane:horizontal .split-pane-divider {
    -fx-background-color: transparent;
    -fx-border-color: transparent;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: transparent;
}
```
###### \resources\view\LightTheme.css
``` css
.list-cell:filled:even {
    -fx-background-color: #ffffff;
}

.list-cell:filled:odd {
    -fx-background-color: #ffffff;
}

.list-cell:filled:selected {
    -fx-background-color: derive(#f7f5f4, -5%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: transparent;
    -fx-border-width: 1;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #444344;
}

.cell_id_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: derive(gray, 35%);
    -fx-font-size: 15px;
}

.person_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 26px;
    -fx-text-fill: #444344;
}

.person_small_label {
    -fx-font-family: "Lucida Grande", "Segoe UI", Optima;
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
     -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
}

.result-text-area {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 12pt;
    -fx-text-fill: gray;
}

.person-detail-panel {
    -fx-background-color: #ffffff;
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
}

.person-list-panel {
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, -2, 2);
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
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: derive(#716f70, -10%);
    -fx-opacity: 0.9;
}
```
###### \resources\view\LightTheme.css
``` css
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
    -fx-padding: 5 3 5 3;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 3 5 3 5;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#initial_small {
    -fx-font-family: "Roboto";
    -fx-text-fill: #ffffff;
    -fx-font-size: 25px;
}

#initial_big {
    -fx-font-family: "Roboto";
    -fx-text-fill: #ffffff;
    -fx-font-size: 45px;
}

#commandTextField {
    -fx-background-color: transparent;
    -fx-background-insets: 0;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: gray;
}
```
###### \resources\view\LightTheme.css
``` css
#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-font-family: "Segoe UI", Optima;
    -fx-text-fill: derive(gray, 20%);
    -fx-padding: 1 0 1 0;
    -fx-font-size: 14px;
}
```
