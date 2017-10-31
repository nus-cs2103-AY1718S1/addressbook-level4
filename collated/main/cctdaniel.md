# cctdaniel
###### /java/seedu/address/logic/commands/CustomiseCommand.java
``` java
/**
 * Customise the look of the Address Book application.
 */
public class CustomiseCommand extends Command {
    public static final String COMMAND_WORD = "customise";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Customise the look of the Address Book application. "
            + "Parameters: "
            + PREFIX_FONT_SIZE + "FONT-SIZE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FONT_SIZE + "xsmall\n";
    public static final String MESSAGE_SUCCESS = "Changed font size to ";
    public static final String FONT_SIZE_XSMALL = "xsmall";
    public static final String FONT_SIZE_SMALL = "small";
    public static final String FONT_SIZE_NORMAL = "normal";
    public static final String FONT_SIZE_LARGE = "large";
    public static final String FONT_SIZE_XLARGE = "xlarge";

    private final FontSize fontSize;

    public CustomiseCommand(FontSize fontSize) {
        this.fontSize = fontSize;
    };

    @Override
    public CommandResult execute() {
        switch (fontSize.value) {
        case FONT_SIZE_XSMALL:
            FontSizeUnit.setCurrentFontSizeUnit(FONT_SIZE_XSMALL_UNIT);
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(FONT_SIZE_XSMALL));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_XSMALL + ".");

        case FONT_SIZE_SMALL:
            FontSizeUnit.setCurrentFontSizeUnit(FONT_SIZE_SMALL_UNIT);
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(FONT_SIZE_SMALL));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_SMALL + ".");

        case FONT_SIZE_NORMAL:
            FontSizeUnit.setCurrentFontSizeUnit(FONT_SIZE_NORMAL_UNIT);
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(FONT_SIZE_NORMAL));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_NORMAL + ".");

        case FONT_SIZE_LARGE:
            FontSizeUnit.setCurrentFontSizeUnit(FONT_SIZE_LARGE_UNIT);
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(FONT_SIZE_LARGE));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_LARGE + ".");

        case FONT_SIZE_XLARGE:
            FontSizeUnit.setCurrentFontSizeUnit(FONT_SIZE_XLARGE_UNIT);
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(FONT_SIZE_XLARGE));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_XLARGE + ".");

        default:
            break;
        }
        return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_NORMAL + ".");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomiseCommand // instanceof handles nulls
                && fontSize.equals(((CustomiseCommand) other).fontSize));
    }

}
```
###### /java/seedu/address/logic/commands/SwitchThemeCommand.java
``` java
/**
 * Toggle between light and dark theme.
 */
public class SwitchThemeCommand extends Command {

    public static final String COMMAND_WORD = "swt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggle between light and dark theme.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SWITCH_THEME_MESSAGE = "Switched theme.";

    private boolean isLight = true;

    @Override
    public CommandResult execute() {
        toggle();
        EventsCenter.getInstance().post(new SwitchThemeRequestEvent(isLight));
        return new CommandResult(SHOWING_SWITCH_THEME_MESSAGE);
    }

    /**
     * Toggle between light and dark theme
     */
    private void toggle() {
        switch (getCurrentThemeUnit()) {
        case THEME_LIGHT_UNIT:
            isLight = false;
            setCurrentThemeUnit(THEME_DARK_UNIT);
            break;

        case THEME_DARK_UNIT:
            isLight = true;
            setCurrentThemeUnit(THEME_LIGHT_UNIT);
            break;

        default:
            break;
        }

    }
}
```
###### /java/seedu/address/logic/parser/CustomiseCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CustomiseCommand object
 */
public class CustomiseCommandParser implements Parser<CustomiseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CustomiseCommand
     * and returns an CustomiseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomiseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FONT_SIZE);

        if (!arePrefixesPresent(argMultimap, PREFIX_FONT_SIZE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomiseCommand.MESSAGE_USAGE));
        }

        try {
            FontSize fontSize = ParserUtil.parseFontSize(argMultimap.getValue(PREFIX_FONT_SIZE)).get();

            return new CustomiseCommand(fontSize);
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

}
```
###### /java/seedu/address/model/font/FontSize.java
``` java
/**
 * Represents the font size of the AddressBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidFontSize(String)}
 */
public class FontSize {

    public static final String MESSAGE_FONT_SIZE_CONSTRAINTS =
            "Font size can only be either \"xsmall\", \"small\","
                    + " \"normal\", \"large\",  or \"xlarge\" (case-sensitive).";
    public static final String[] FONT_SIZE_VALIDATION = {"xsmall", "small", "normal", "large", "xlarge"};
    public final String value;

    /**
     * Validates given font size.
     *
     * @throws IllegalValueException if given font size is invalid.
     */
    public FontSize(String fontSize) throws IllegalValueException {
        requireNonNull(fontSize);
        if (!isValidFontSize(fontSize)) {
            throw new IllegalValueException(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
        this.value = fontSize;
    }

    /**
     * Returns true if a given string is a valid font size.
     */
    public static boolean isValidFontSize(String test) {
        for (String s : FONT_SIZE_VALIDATION) {
            if (test.equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other.equals(this.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/FontSizeUnit.java
``` java
/**
 * A Enumeration class that consists of all possible FontSize
 * Unit in the panel.
 */
public enum FontSizeUnit {
    FONT_SIZE_XSMALL_UNIT, FONT_SIZE_SMALL_UNIT, FONT_SIZE_NORMAL_UNIT, FONT_SIZE_LARGE_UNIT, FONT_SIZE_XLARGE_UNIT;

    private static FontSizeUnit currentFontSizeUnit = FONT_SIZE_NORMAL_UNIT;

    /** Get current FontSize unit */
    public static FontSizeUnit getCurrentFontSizeUnit() {
        return currentFontSizeUnit;
    }

    /** Reset FontSize unit in the panel with the new FontSizeUnit */
    public static void setCurrentFontSizeUnit(FontSizeUnit unit) {
        currentFontSizeUnit = unit;
    }
}
```
###### /java/seedu/address/model/ThemeUnit.java
``` java
/**
 * A Enumeration class that consists of all possible Theme
 * Unit in the panel.
 */
public enum ThemeUnit {
    THEME_LIGHT_UNIT, THEME_DARK_UNIT;

    private static ThemeUnit currentThemeUnit = THEME_LIGHT_UNIT;

    /** Get current Theme unit */
    public static ThemeUnit getCurrentThemeUnit() {
        return currentThemeUnit;
    }

    /** Reset Theme unit in the panel with the new ThemeUnit */
    public static void setCurrentThemeUnit(ThemeUnit unit) {
        currentThemeUnit = unit;
    }
}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Sets the command box style to user preferred font size.
     */
    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            commandTextField.setStyle("-fx-font-size: x-small;");
            userPrefFontSize = "-fx-font-size: x-small;";
            fontIndex = 1;
            break;

        case FONT_SIZE_SMALL:
            commandTextField.setStyle("-fx-font-size: small;");
            userPrefFontSize = "-fx-font-size: small;";
            fontIndex = 2;
            break;

        case FONT_SIZE_NORMAL:
            commandTextField.setStyle("-fx-font-size: normal;");
            userPrefFontSize = "-fx-font-size: normal;";
            fontIndex = 3;
            break;

        case FONT_SIZE_LARGE:
            commandTextField.setStyle("-fx-font-size: x-large;");
            userPrefFontSize = "-fx-font-size: x-large;";
            fontIndex = 4;
            break;

        case FONT_SIZE_XLARGE:
            commandTextField.setStyle("-fx-font-size: xx-large;");
            userPrefFontSize = "-fx-font-size: xx-large;";
            fontIndex = 5;
            break;

        default:
            break;
        }
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Handles SwitchThemeEvent.
     */
    @FXML
    public void handleSwitchTheme(SwitchThemeRequestEvent event) {
        if (!event.isLight) {
            sceneBox.getStylesheets().clear();
            sceneBox.getStylesheets().add("/view/DarkTheme.css");
            sceneBox.getStylesheets().add("/view/DarkExtensions.css");
            resultDisplayPlaceholder.getStylesheets().clear();
            resultDisplayPlaceholder.getStylesheets().add("/view/DarkTheme.css");
            resultDisplayPlaceholder.getStylesheets().add("/view/DarkExtensions.css");
            browserPlaceholder.getStylesheets().clear();
            browserPlaceholder.getStylesheets().add("/view/DarkTheme.css");
            browserPlaceholder.getStylesheets().add("/view/DarkExtensions.css");


        } else {
            sceneBox.getStylesheets().clear();
            sceneBox.getStylesheets().add("/view/LightTheme.css");
            sceneBox.getStylesheets().add("/view/LightExtensions.css");
            resultDisplayPlaceholder.getStylesheets().clear();
            resultDisplayPlaceholder.getStylesheets().add("/view/LightTheme.css");
            resultDisplayPlaceholder.getStylesheets().add("/view/LightExtensions.css");
            browserPlaceholder.getStylesheets().clear();
            browserPlaceholder.getStylesheets().add("/view/LightTheme.css");
            browserPlaceholder.getStylesheets().add("/view/LightExtensions.css");
        }
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public LessonListPanel getLessonListPanel() {
        return this.lessonListPanel;
    }

    void releaseResources() {
        combinePanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleSwitchThemeRequestEvent(SwitchThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchTheme(event);
    }


}
```
###### /java/seedu/address/ui/ResultDisplay.java
``` java
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }

    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            resultDisplay.setStyle("-fx-font-size: x-small;");
            break;

        case FONT_SIZE_SMALL:
            resultDisplay.setStyle("-fx-font-size: small;");
            break;

        case FONT_SIZE_NORMAL:
            resultDisplay.setStyle("-fx-font-size: normal;");
            break;

        case FONT_SIZE_LARGE:
            resultDisplay.setStyle("-fx-font-size: x-large;");
            break;

        case FONT_SIZE_XLARGE:
            resultDisplay.setStyle("-fx-font-size: xx-large;");
            break;

        default:
            break;
        }
    }

}
```
