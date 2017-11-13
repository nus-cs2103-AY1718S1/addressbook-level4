# derickjw
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String[] AUTOCOMPLETE_SUGGESTIONS = {"createDefaultAcc", "removeLogin", "changepw",
                                                             "changeuser", "convenientStation",
                                                             "visualize", "arrange", "list", "locateMrt", "edit", "find",
                                                             "findByAddress", "findByPhone", "findByEmail",
                                                             "findByTags", "delete", "locate", "select", "history",
                                                             "sort", "undo", "clear", "exit", "schedule", };
}
```
###### \java\seedu\address\commons\events\ui\NewResultAvailableEvent.java
``` java
    public final boolean isInvalid;

    public NewResultAvailableEvent(String message, boolean isInvalid) {
        this.message = message;
        this.isInvalid = isInvalid;
    }
```
###### \java\seedu\address\logic\commands\ChangePasswordCommand.java
``` java
/**
 * Changes a user's password
 */
public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "changepw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes user's password.\n"
            + "Example:" + " " + COMMAND_WORD + " " + "username" + " " + "old password" + " " + "new password";

    public static final String MESSAGE_CHANGE_SUCCESS = "Password changed successfully!";
    public static final String MESSAGE_WRONG_CREDENTIALS = "Invalid Credentials!";

    private final String username;
    private final String oldPw;
    private final String newPw;

    public ChangePasswordCommand(String username, String oldPw, String newPw) {
        this.username = username;
        this.oldPw = oldPw;
        this.newPw = newPw;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().changePassword(username, oldPw, newPw)) {
            return new CommandResult(MESSAGE_CHANGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRONG_CREDENTIALS);
        }
    }
}
```
###### \java\seedu\address\logic\commands\ChangeUsernameCommand.java
``` java
/**
 * Changes a user's username
 */
public class ChangeUsernameCommand extends Command {
    public static final String COMMAND_WORD = "changeuser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes a user's username.\n"
        + "Example:" + " " + COMMAND_WORD + " " + "old username" + " " + "new username" + " " + "password";

    public static final String MESSAGE_CHANGE_SUCCESS = "Username changed successfully!";
    public static final String MESSAGE_WRONG_CREDENTIALS = "Invalid Credentials!";

    private final String newUsername;
    private final String oldUsername;
    private final String password;

    public ChangeUsernameCommand(String oldUsername, String newUsername, String password) {
        this.oldUsername = oldUsername;
        this.newUsername = newUsername;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().changeUsername(oldUsername, newUsername, password)) {
            return new CommandResult(MESSAGE_CHANGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRONG_CREDENTIALS);
        }
    }
}
```
###### \java\seedu\address\logic\commands\CreateDefaultAccountCommand.java
``` java
/**
 * Creates a account with username "admin" and password "admin"
 */
public class CreateDefaultAccountCommand extends Command {
    public static final String COMMAND_WORD = "createDefaultAcc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a default account where username and "
            + "password is admin.\n" + "Example:" + " " + COMMAND_WORD;

    public static final String MESSAGE_CREATE_SUCCESS = "Default account created successfully!";
    public static final String MESSAGE_ACCOUNT_EXISTS = "Account already exists!";

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().checkUsername("") && model.getUserPrefs().checkPassword("")) {
            model.getUserPrefs().setDefaultUsername("admin");
            model.getUserPrefs().setDefaultPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
            return new CommandResult(MESSAGE_CREATE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_ACCOUNT_EXISTS);
        }
    }
}
```
###### \java\seedu\address\logic\commands\RemoveAccountCommand.java
``` java
/**
 * Remove the need to log in
 */
public class RemoveAccountCommand extends Command {
    public static final String COMMAND_WORD = "removeLogin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove the need to login \n" + "format:"
            + " " + COMMAND_WORD + " " + "username" + " " + "password";

    public static final String MESSAGE_REMOVE_SUCCESS = "Login removed successfully";
    public static final String MESSAGE_ACCOUNT_ALREADY_REMOVED = "Account does not exist";
    public static final String MESSAGE_INVALID_CREDENTIALS = "Invalid Credentials! Please try again.";

    private final String username;
    private final String password;

    public RemoveAccountCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().checkUsername("") || model.getUserPrefs().checkPassword("")) {
            return new CommandResult(MESSAGE_ACCOUNT_ALREADY_REMOVED);
        } else {
            if (!model.getUserPrefs().checkUsername(username) || !model.getUserPrefs().checkPassword(password)) {
                return new CommandResult(MESSAGE_INVALID_CREDENTIALS);
            } else if (model.getUserPrefs().checkUsername(username) && model.getUserPrefs().checkPassword(password)) {
                model.getUserPrefs().setDefaultUsername("");
                model.getUserPrefs().setDefaultPassword("");
            }
            return new CommandResult(MESSAGE_REMOVE_SUCCESS);
        }
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    private boolean isLoggedInUsername = false;
    private boolean isLoggedInPassword = false;
```
###### \java\seedu\address\logic\LogicManager.java
``` java
        isCorrectPassword("");
        isCorrectUsername("");
```
###### \java\seedu\address\logic\LogicManager.java
``` java
        if (!isLoggedInUsername) {
            if (isCorrectUsername(commandText)) {
                result = new CommandResult("Please enter your password");
                return result;
            } else {
                result = new CommandResult("Username does not exist. Please try again!");
                return result;
            }
        }

        if (!isLoggedInPassword) {
            if (isCorrectPassword(commandText)) {
                result = new CommandResult("Log in successful! Welcome to H.M.U v1.4!");
                return result;
            } else {
                result = new CommandResult("Invalid Credentials. Please try again!");
                return result;
            }
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    /**
     *
     * @param commandText
     * isLoggedInPassword = true if password is valid
     * isLoggedInPassword = false if password is invalid
     */
    private boolean isCorrectPassword(String commandText) {
        if (model.getUserPrefs().checkPassword(commandText)) {
            isLoggedInPassword = true;
            return true;
        } else {
            isLoggedInPassword = false;
            return false;
        }
    }

    /**
     *
     * @param commandText
     * isLoggedInUsername = true if username is valid
     * isLoggedInUsername = false if username is invalid
     */
    private boolean isCorrectUsername(String commandText) {
        if (model.getUserPrefs().checkUsername(commandText)) {
            isLoggedInUsername = true;
            return true;
        } else {
            isLoggedInUsername = false;
            return false;
        }
    }

    /**
     * Setter method for username
     */
    private void setDefaultUsername() {
        model.getUserPrefs().setDefaultUsername("admin");
    }

    /**
     * Setter method for password
     */
    private void setDefaultPassword() {
        model.getUserPrefs().setDefaultPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
    }

```
###### \java\seedu\address\logic\parser\RemoveAccountCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */

public class RemoveAccountCommandParser implements Parser<RemoveAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand
     * and returns an ChangePasswordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveAccountCommand parse(String args) throws ParseException {
        try {
            String[] commandTokenized = args.split(" ");
            if (commandTokenized.length != 3) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RemoveAccountCommand.MESSAGE_USAGE));
            } else {
                return new RemoveAccountCommand(commandTokenized[1], commandTokenized[2]);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns the User Preferences */
    UserPrefs getUserPrefs();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    private final UserPrefs userPrefs;
```
###### \java\seedu\address\model\ModelManager.java
``` java
        this.userPrefs = userPrefs;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    private String password = "";
    private String username = "";
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    /**
     *
     * @param input (Password typed in command line)
     * @return true if password is valid
     */
    public boolean checkPassword(String input) {
        return (("").equals(password)) || password.equals(hashBySha256(input));
    }

    /**
     *
     * @param input
     * @return true if username is valid
     */
    public boolean checkUsername(String input) {
        return username.equals(input);
    }

    /**
     *
     * @param user
     * @param oldPw
     * @param newPw
     * @return true if password is changed successfully
     */
    public boolean changePassword(String user, String oldPw, String newPw) {
        if (checkPassword(oldPw) && checkUsername(user)) {
            password = hashBySha256(newPw);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param oldUsername
     * @param newUsername
     * @param password
     * @return true if username is changes successfully
     */
    public boolean changeUsername(String oldUsername, String newUsername, String password) {
        if (checkPassword(password) && checkUsername(oldUsername)) {
            username = newUsername;
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param input
     * @return a String that is hashed using SHA-256
     */
    public String hashBySha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());

            byte[] mdBytes = md.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < mdBytes.length; i++) {
                hexString.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            return "No Such Algorithm Exception";
        }
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        TextFields.bindAutoCompletion(commandTextField, Messages.AUTOCOMPLETE_SUGGESTIONS);
```
###### \java\seedu\address\ui\CommandBox.java
``` java
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
```
###### \java\seedu\address\ui\CommandBox.java
``` java
            raise(new NewResultAvailableEvent(e.getMessage(), true));
```
###### \java\seedu\address\ui\MapPanel.java
``` java

/**
 * The Map Panel of the App.
 */
public class MapPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String FACEBOOK_SEARCH_URL_PREFIX = "https://www.facebook.com/search/people/?q=";
    public static final String GOOGLE_MAP_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    public static final String GOOGLE_MRT_URL_SUFFIX = "+MRT";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public MapPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(FACEBOOK_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+"));
    }

```
###### \java\seedu\address\ui\MapPanel.java
``` java

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String[] colors = {"red", "blue", "orange", "green", "black", "grey" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    public static final String ERROR_STYLE_CLASS = "error";
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
        if (event.isInvalid) {
            setStyleToIndicateCommandFailure();
        } else {
            setStyleToDefault();
        }

    }

    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }
}

```
###### \resources\view\BlueTheme.css
``` css
*/

.background {
    -fx-background-color: derive(#ffffff, 20%);
    background-color: #ffffff; /* Used in the default.html file */
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
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
            transparent
            transparent
            derive(-fx-base, 80%)
            transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#2b2b2b, 20%);
    -fx-border-color: transparent transparent rgba(0, 175, 201, 0) #2b2b2b;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#2b2b2b, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: rgba(0, 144, 178, 0.99);
}

.list-cell:filled:odd {
    -fx-background-color: rgba(63, 77, 176, 0.89);
}

.list-cell:filled:selected {
    -fx-background-color: #424d5f;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: white;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
    -fx-background-color: derive(#fff6dc, 20%);
}

.pane-with-border {
    -fx-background-color: derive(#279fa8, 20%);
    -fx-border-color: derive(#279fa8, 10%);
    -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: #00fff6;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
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
    -fx-background-color: derive(#0085ff, 20%);
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
    -fx-text-fill: #000bff;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, #279fa8, 10, 0, 0, 0);
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
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
###### \resources\view\LoginWindow.fxml
``` fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.Font?>

<AnchorPane prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" fx:controller="view.LoginWindow">
   <children>
      <Label alignment="CENTER" layoutX="14.0" layoutY="14.0" prefHeight="46.0" prefWidth="600.0" style="-fx-background-color: black; -fx-text-fill: white;" text="H.M.U v1.2 Login" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
         <font>
            <Font size="18.0" />
         </font>
      </Label>
      <TextField fx:id="usernamePrompt" layoutX="14.0" layoutY="73.0" prefHeight="25.0" prefWidth="565.0" promptText="Username">
         <font>
            <Font size="16.0" />
         </font>
      </TextField>
      <PasswordField fx:id="passwordPrompt" layoutX="14.0" layoutY="142.0" prefHeight="25.0" prefWidth="565.0" promptText="Password">
         <font>
            <Font size="16.0" />
         </font>
      </PasswordField>
      <Button layoutX="151.0" layoutY="223.0" mnemonicParsing="false" onAction="#handleLoginButtonAction" style="-fx-background-color: blue; -fx-text-fill: white;" text="Login">
         <font>
            <Font size="15.0" />
         </font>
      </Button>
      <Button layoutX="383.0" layoutY="223.0" mnemonicParsing="false" onAction="#handleExitButtonAction" prefHeight="31.0" prefWidth="57.0" style="-fx-background-color: blue; -fx-text-fill: white;" text="Exit">
         <font>
            <Font size="15.0" />
         </font>
      </Button>
   </children>
</AnchorPane>
```
###### \resources\view\PersonDetails.fxml
``` fxml

<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox style="-fx-background-color: white;" styleClass="card" stylesheets="@BlueTheme.css" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <GridPane prefHeight="581.0" prefWidth="994.0" VBox.vgrow="ALWAYS">
            <columnConstraints>
                <ColumnConstraints halignment="LEFT" minWidth="10.0" prefWidth="100.0" />
            </columnConstraints>
            <rowConstraints>
                <RowConstraints minHeight="10.0" prefHeight="165.0" valignment="CENTER" vgrow="SOMETIMES" />
                <RowConstraints minHeight="10.0" prefHeight="74.0" valignment="CENTER" vgrow="SOMETIMES" />
                <RowConstraints minHeight="10.0" prefHeight="139.0" valignment="CENTER" vgrow="SOMETIMES" />
            </rowConstraints>
            <children>
                <GridPane alignment="CENTER" prefHeight="430.0" prefWidth="740.0" GridPane.halignment="CENTER" GridPane.hgrow="ALWAYS" GridPane.rowIndex="1" GridPane.valignment="CENTER">
                    <columnConstraints>
                        <ColumnConstraints halignment="CENTER" maxWidth="1.7976931348623157E308" minWidth="10.0" />
                  <ColumnConstraints halignment="CENTER" maxWidth="232.0" minWidth="0.0" prefWidth="0.0" />
                    </columnConstraints>
                    <rowConstraints>
                        <RowConstraints maxHeight="1.7976931348623157E308" valignment="CENTER" vgrow="SOMETIMES" />
                        <RowConstraints maxHeight="1.7976931348623157E308" valignment="CENTER" vgrow="SOMETIMES" />
                        <RowConstraints maxHeight="1.7976931348623157E308" valignment="CENTER" vgrow="SOMETIMES" />
                  <RowConstraints maxHeight="1.7976931348623157E308" valignment="CENTER" vgrow="SOMETIMES" />
                  <RowConstraints maxHeight="0.0" minHeight="0.0" prefHeight="0.0" valignment="CENTER" vgrow="SOMETIMES" />
                    </rowConstraints>
                    <children>
                        <Label fx:id="phone" alignment="CENTER" prefHeight="83.0" prefWidth="994.0" style="-fx-font-size: 30;" text="Phone" textAlignment="JUSTIFY" GridPane.rowIndex="1">
                            <font>
                                <Font size="18.0" />
                            </font>
                        </Label>
                        <Label fx:id="address" alignment="CENTER" prefHeight="45.0" prefWidth="947.0" style="-fx-font-size: 30;" text="Address" GridPane.rowIndex="2">
                            <font>
                                <Font size="18.0" />
                            </font>
                        </Label>
                  <Label fx:id="mrt" alignment="CENTER" prefHeight="45.0" prefWidth="995.0" style="-fx-font-size: 30;" text="MRT" textAlignment="JUSTIFY" GridPane.rowIndex="3">
                     <font>
                        <Font size="18.0" />
                     </font>
                  </Label>
                        <Label fx:id="email" alignment="CENTER" prefHeight="45.0" prefWidth="917.0" style="-fx-font-size: 30;" text="Email" textAlignment="JUSTIFY" GridPane.hgrow="ALWAYS" GridPane.valignment="CENTER" GridPane.vgrow="ALWAYS">
                            <font>
                                <Font size="18.0" />
                            </font>
                        </Label>
                    </children>
                </GridPane>
                  <Label fx:id="name" alignment="CENTER" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="165.0" prefWidth="290.0" style="-fx-font-size: 60;" text="Name" textAlignment="JUSTIFY">
                      <font>
                          <Font size="53.0" />
                      </font>
                  </Label>
            <Label prefHeight="233.0" prefWidth="290.0" style="-fx-font-size: 60;" />
            </children>
        </GridPane>
    </children>
</VBox>
```
