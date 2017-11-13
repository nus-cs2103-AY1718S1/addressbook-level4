# moomeowroar
###### /java/seedu/address/logic/commands/ChangePwdCommand.java
``` java
/**
 * Sends a message to the contact through email
 */

public class ChangePwdCommand extends Command {

    public static final String COMMAND_WORD = "changepwd";
    public static final String COMMAND_ALIAS = "cpwd";
    public static final String COMMAND_HELP = "changepwd NEW_PASSWORD OLD_PASSWORD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes password. \n"
            + COMMAND_WORD + " <new_password> <old_password>";


    public static final String MESSAGE_NO_SETTINGS = "No available settings.";
    public static final String MESSAGE_CHANGE_SUCCESS = "Settings changed successfully.";

    private final String oldPwd;
    private final String newPwd;

    /**
     * @param String Name of setting to change
     */
    public ChangePwdCommand(String oldPwd, String newPwd) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().changePwd(oldPwd, newPwd)) {
            return new CommandResult(MESSAGE_CHANGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_NO_SETTINGS);
    }


}
```
###### /java/seedu/address/logic/commands/ChangeThemeCommand.java
``` java
/**
 * Change theme
 */

public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";
    public static final String COMMAND_HELP = "theme THEME_NAME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes theme. \n"
            + COMMAND_WORD + " <dark/light/youtube>";


    public static final String MESSAGE_NO_THEME = "Invalid theme.";
    public static final String MESSAGE_CHANGE_SUCCESS = "Theme changed successfully.";

    private final String themeName;

    /**
     * @param String Name of theme to change
     */
    public ChangeThemeCommand(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeThemeEvent(themeName));
        return new CommandResult(MESSAGE_CHANGE_SUCCESS);
    }

}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    /**
     * Retrieves all available commands according to user input
     * @param commandText
     * @return output of available commands as a String
     */

    public String liveHelp(String commandText) throws IllegalValueException {
        String finalString = "";
        ArrayList<String> result = addressBookParser.filterCommand(commandText, false);
        for (String string: result) {
            finalString += string + "\n";
        }
        return finalString;
    }

    public String getCommandWord(String commandText) throws IllegalValueException {
        ArrayList<String> result = addressBookParser.filterCommand(commandText, true);
        if (result.isEmpty() || result.size() > 1) {
            return "nil";
        } else {
            return result.get(0);
        }
    }

    /**
     * Check if password is correct and set lock
     * @param commandText
     * @return
     */
    public boolean isPassword(String password) {
        if (model.getUserPrefs().checkPassword(password)) {
            isLock = false;
            return true;
        } else {
            isLock = true;
            return false;
        }
    }

    public boolean isAddressBookLock() {
        return isLock;
    }
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
    /**
     * Searches for all commands that contains the input text
     * @param text
     * @return ArrayList of command words
     */

    public ArrayList<String> filterCommand(String text, boolean isExact) throws IllegalValueException {
        String[] array = ParserUtil.parseByDelimiter(text, " ");
        ArrayList<String> result = new ArrayList<String>();
        HashMap<String, String> commandList = new HashMap<String, String>();
        commandList.put(AddCommand.COMMAND_WORD, AddCommand.COMMAND_HELP);
        commandList.put(ChangePwdCommand.COMMAND_WORD, ChangePwdCommand.COMMAND_HELP);
        commandList.put(EditCommand.COMMAND_WORD, EditCommand.COMMAND_HELP);
        commandList.put(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_HELP);
        commandList.put(FavCommand.COMMAND_WORD, FavCommand.COMMAND_HELP);
        commandList.put(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_HELP);
        commandList.put(FindCommand.COMMAND_WORD, FindCommand.COMMAND_HELP);
        commandList.put(FindTagCommand.COMMAND_WORD, FindTagCommand.COMMAND_HELP);
        commandList.put(FindEmailCommand.COMMAND_WORD, FindEmailCommand.COMMAND_HELP);
        commandList.put(ListCommand.COMMAND_WORD, ListCommand.COMMAND_HELP);
        commandList.put(HistoryCommand.COMMAND_WORD, HistoryCommand.COMMAND_HELP);
        commandList.put(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_HELP);
        commandList.put(UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_HELP);
        commandList.put(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_HELP);
        commandList.put(SendCommand.COMMAND_WORD, SendCommand.COMMAND_HELP);
        commandList.put(RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_HELP);
        commandList.put(SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_HELP);
        commandList.put(ChangeThemeCommand.COMMAND_WORD, ChangeThemeCommand.COMMAND_HELP);
        for (HashMap.Entry<String, String> entry : commandList.entrySet()) {
            if (isExact) {
                if (entry.getKey().equals(array[0])) {
                    result.add(entry.getKey());
                }
            } else {
                if (entry.getKey().contains(array[0])) {
                    result.add(entry.getValue());
                }
            }
        }
        return result;
    }

}
```
###### /java/seedu/address/logic/parser/ChangePwdCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangePwdCommand object
 */
public class ChangePwdCommandParser implements Parser<ChangePwdCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an ChangePwdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePwdCommand parse(String args) throws ParseException {
        try {
            String[] array = ParserUtil.parseByDelimiter(args, " ");
            if (array.length == 2) {
                return new ChangePwdCommand("", array[1]);
            } else if (array.length == 3) {
                return new ChangePwdCommand(array[2], array[1]);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePwdCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePwdCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/ChangeThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangePwdCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    public static final int NUM_OF_PARAM = 2; //Inclusive of command word

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an ChangePwdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeThemeCommand parse(String args) throws ParseException {
        try {
            String[] array = ParserUtil.parseByDelimiter(args, " ");
            if (array.length == NUM_OF_PARAM) {
                switch (array[1]) {
                case "light":
                    return new ChangeThemeCommand("LightTheme");
                case "dark":
                    return new ChangeThemeCommand("DarkTheme");
                case "youtube":
                    return new ChangeThemeCommand("YoutubeTheme");
                default:
                    break;
                }
            }
            throw new ParseException(
                    String.format(ChangeThemeCommand.MESSAGE_NO_THEME, ChangeThemeCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyReleased(KeyEvent keyEvent) throws IllegalValueException {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();
            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        default:
            if (keyEvent.getCode() == KeyCode.ENTER) {
                commandWord.setVisible(false);
            } else if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                commandText = logic.getCommandWord(commandTextField.getText());
                if (commandText.equals("nil")) {
                    commandWord.setVisible(false);
                } else {
                    commandWord.setVisible(true);
                    commandWord.setText(commandText);
                }
            }
            if (commandText.equals("find") || commandText.equals("findemail") || commandText.equals("findtag")) {
                handleCommandInputChanged();
                raise(new NewResultAvailableEvent("", false));
            } else {
                raise(new NewResultAvailableEvent(logic.liveHelp(commandTextField.getText()), false));
            }
            if (commandTextField.getText().equals("")) {
                raise(new NewResultAvailableEvent("", false));
            }
            break;
        }
    }

```
###### /java/seedu/address/ui/LockScreen.java
``` java
/**
 * The Lock Screen Window.
 * Provides a password field to enter the password.
 */
public class LockScreen extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "LockScreen.fxml";
    private static final int MIN_HEIGHT = 100;
    private static final int MIN_WIDTH = 300;
    private int x = 0;
    private int y = 0;
    private String theme = "";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private Stage primaryStage;
    private MainWindow mainWindow;

    @FXML
    private StackPane pwdBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private VBox vBox;

    public LockScreen(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle("Login");
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize();
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        initThemeFromSettings();
    }

    /**
     * Loads the passwordField into the stage.
     */
    void fillInnerParts() {
        PasswordField pwBox = new PasswordField();
        pwBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (logic.isPassword(pwBox.getText())) {
                    loadMainWindow();
                } else {
                    setTitle("Wrong Password!");
                    pwBox.setStyle("-fx-text-box-border: #f44b42; -fx-focus-color: #f44b42;");
                    shakeStage();
                }
                pwBox.clear();
            }
        });
        pwdBoxPlaceholder.getChildren().add(pwBox);
    }

    private void initThemeFromSettings() {
        theme = prefs.getGuiSettings().getTheme();
        vBox.getStylesheets().remove(0);
        vBox.getStylesheets().add("/view/" + theme + ".css");
    }

    void loadMainWindow() {
        mainWindow = new MainWindow(primaryStage, config, prefs, logic);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
    }

    /**
     * Show the stage.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Hide the stage.
     */
    void hide() {
        if (mainWindow != null) {
            prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
            mainWindow.releaseResources();
        }
        primaryStage.hide();
    }

    public Stage getMainWindowPrimaryStage() {
        return mainWindow.getPrimaryStage();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize() {
        primaryStage.setHeight(MIN_HEIGHT);
        primaryStage.setWidth(MIN_WIDTH);
    }

    /**
     * Sets the window minimum size.
     */
    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Function to animate the stage for wrong password.
     */
    private void shakeStage() {
        Timeline timelineX = new Timeline(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (x == 0) {
                    primaryStage.setX(primaryStage.getX() + 10);
                    x = 1;
                } else {
                    primaryStage.setX(primaryStage.getX() - 10);
                    x = 0;
                }
            }
        }));

        timelineX.setCycleCount(4);
        timelineX.setAutoReverse(false);
        timelineX.play();

        Timeline timelineY = new Timeline(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (y == 0) {
                    primaryStage.setY(primaryStage.getY() + 10);
                    y = 1;
                } else {
                    primaryStage.setY(primaryStage.getY() - 10);
                    y = 0;
                }
            }
        }));

        timelineY.setCycleCount(4);
        timelineY.setAutoReverse(false);
        timelineY.play();
    }
}
```
###### /resources/view/CommandBox.fxml
``` fxml
  <Button fx:id="commandWord" mnemonicParsing="false" text="Find" textAlignment="right" StackPane.alignment="CENTER_LEFT" mouseTransparent="true" visible="false" minHeight="50" maxHeight="50"/>
```
###### /resources/view/LightTheme.css
``` css

#commandWord {
    -fx-background-color: #61594a;
    -fx-background-insets: 0px 20px 2px 2px;
    -fx-background-radius: 3px;
    -fx-border-insets: 0 0px 0 0px;
    -fx-border-width: 0 0 0 0;
    -fx-border-color: #d0d0d0;
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #b8c975;
}
```
###### /resources/view/LockScreen.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" styleClass="background" fx:id="vBox">
  <stylesheets>
    <URL value="@DarkTheme.css" />
    <URL value="@Extensions.css" />
  </stylesheets>

  <StackPane VBox.vgrow="NEVER" fx:id="pwdBoxPlaceholder" styleClass="pwd-field">
    <padding>
      <Insets top="5" right="10" bottom="5" left="10" />
    </padding>
  </StackPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### /resources/view/YoutubeTheme.css
``` css

#commandWord {
    -fx-background-color: #FFFFFF;
    -fx-background-insets: 0px 20px 2px 2px;
    -fx-background-radius: 3px;
    -fx-border-insets: 0 0px 0 0px;
    -fx-border-width: 0 0 0 0;
    -fx-border-color: #d0d0d0;
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #4f7df6;
}
```
