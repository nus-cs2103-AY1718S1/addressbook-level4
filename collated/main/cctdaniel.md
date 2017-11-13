# cctdaniel
###### \java\seedu\address\commons\events\ui\ChangeFontSizeEvent.java
``` java
/**
 * Indicates a request to change the font size of the application
 */
public class ChangeFontSizeEvent extends BaseEvent {

    public final String message;

    public ChangeFontSizeEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\SwitchThemeRequestEvent.java
``` java
/**
 * An event requesting to switch current theme.
 */
public class SwitchThemeRequestEvent extends BaseEvent {

    public final boolean isLight;

    public SwitchThemeRequestEvent(boolean isLight) {
        this.isLight = isLight;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\CustomiseCommand.java
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
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
/**
 * Switch between light and dark theme
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String LIGHT_THEME = "light";
    public static final String DARK_THEME = "dark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switch to light/dark theme. "
            + "Parameters: light/dark\n"
            + "Example: " + COMMAND_WORD + " " + LIGHT_THEME;

    public static final String SWITCH_THEME_SUCCESS_MESSAGE = "Switched theme to ";
    public static final String SWITCH_THEME_FAILURE_MESSAGE = "Please choose a different theme from the current one. ";

    public static final String LIGHT_THEME_CSS_FILE_NAME = "view/LightTheme.css";
    public static final String LIGHT_THEME_EXTENSIONS_CSS_FILE_NAME = "view/LightExtensions.css";
    public static final String DARK_THEME_CSS_FILE_NAME = "view/DarkTheme.css";
    public static final String DARK_THEME_EXTENSIONS_CSS_FILE_NAME = "view/DarkExtensions.css";


    private boolean isLight = true;

    public ThemeCommand(String theme) {
        if (theme.equalsIgnoreCase(LIGHT_THEME)) {
            isLight = true;
        } else {
            isLight = false;
        }
    }

    @Override
    public CommandResult execute() {
        switch (getCurrentThemeUnit()) {
        case THEME_LIGHT_UNIT:
            if (isLight) {
                return new CommandResult(SWITCH_THEME_FAILURE_MESSAGE);
            } else {
                setCurrentThemeUnit(THEME_DARK_UNIT);
                EventsCenter.getInstance().post(new SwitchThemeRequestEvent(isLight));
                return new CommandResult(SWITCH_THEME_SUCCESS_MESSAGE + DARK_THEME + ".");
            }
        case THEME_DARK_UNIT:
            if (!isLight) {
                return new CommandResult(SWITCH_THEME_FAILURE_MESSAGE);
            } else {
                setCurrentThemeUnit(THEME_LIGHT_UNIT);
                EventsCenter.getInstance().post(new SwitchThemeRequestEvent(isLight));
                return new CommandResult(SWITCH_THEME_SUCCESS_MESSAGE + LIGHT_THEME + ".");
            }
        default:
            break;
        }
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.isLight == (((ThemeCommand) other).isLight)); // state check
    }

    public boolean getIsLight() {
        return isLight;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case CustomiseCommand.COMMAND_WORD:
            return new CustomiseCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\CustomiseCommandParser.java
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
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> fontSize} into an {@code Optional<FontSize>} if {@code fontSize} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<FontSize> parseFontSize(Optional<String> fontSize) throws IllegalValueException {
        requireNonNull(fontSize);
        return fontSize.isPresent() ? Optional.of(new FontSize(fontSize.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns an ThemeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (isValidAttribute(trimmedArgs)) {
            return new ThemeCommand(trimmedArgs);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }

    }

    private boolean isValidAttribute(String args) {
        return args.equalsIgnoreCase(ThemeCommand.LIGHT_THEME)
                || args.equalsIgnoreCase(ThemeCommand.DARK_THEME);
    }
}
```
###### \java\seedu\address\model\font\FontSize.java
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
    public static final String FONT_SIZE_XSMALL_NUMBER = "12";
    public static final String FONT_SIZE_SMALL_NUMBER = "17";
    public static final String FONT_SIZE_NORMAL_NUMBER = "25";
    public static final String FONT_SIZE_LARGE_NUMBER = "32";
    public static final String FONT_SIZE_XLARGE_NUMBER = "40";


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
###### \java\seedu\address\model\FontSizeUnit.java
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
###### \java\seedu\address\model\ThemeUnit.java
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
###### \java\seedu\address\ui\CombinePanel.java
``` java
    /**
     * Sets the remark style to user preferred font size.
     */
    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            setFontSizeHelper("12");
            break;

        case FONT_SIZE_SMALL:
            setFontSizeHelper("17");
            break;

        case FONT_SIZE_NORMAL:
            setFontSizeHelper("25");
            break;

        case FONT_SIZE_LARGE:
            setFontSizeHelper("32");
            break;

        case FONT_SIZE_XLARGE:
            setFontSizeHelper("40");
            break;

        default:
            break;
        }
    }

    /**
     * Helper method to set font size.
     */
    private void setFontSizeHelper(String fontSize) {
        noteGrid.getChildren().forEach((node) -> {
            if (node instanceof StackPane) {
                for (Node n : ((StackPane) node).getChildren()) {
                    if (n instanceof TextArea) {
                        n.setStyle("-fx-font-size: " + fontSize + ";");
                    }
                }
            }
        });
    }

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }


    /**
     * Helper method to set font size based on FontSizeUnit.
     */
    private void setFontSizeUnit(FontSizeUnit currFontSizeUnit) {
        switch (currFontSizeUnit) {
        case FONT_SIZE_XSMALL_UNIT:
            setFontSize(FONT_SIZE_XSMALL);
            break;

        case FONT_SIZE_SMALL_UNIT:
            setFontSize(FONT_SIZE_SMALL);
            break;

        case FONT_SIZE_NORMAL_UNIT:
            setFontSize(FONT_SIZE_NORMAL);
            break;

        case FONT_SIZE_LARGE_UNIT:
            setFontSize(FONT_SIZE_LARGE);
            break;

        case FONT_SIZE_XLARGE_UNIT:
            setFontSize(FONT_SIZE_XLARGE);
            break;

        default:
            break;
        }
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Sets the command box style to user preferred font size.
     */
    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            commandTextField.setStyle("-fx-font-size: " + FONT_SIZE_XSMALL_NUMBER + ";");
            userPrefFontSize = "-fx-font-size: " + FONT_SIZE_XSMALL_NUMBER + ";";
            fontIndex = 1;
            break;

        case FONT_SIZE_SMALL:
            commandTextField.setStyle("-fx-font-size: " + FONT_SIZE_SMALL_NUMBER + ";");
            userPrefFontSize = "-fx-font-size: " + FONT_SIZE_SMALL_NUMBER + ";";
            fontIndex = 2;
            break;

        case FONT_SIZE_NORMAL:
            commandTextField.setStyle("-fx-font-size: " + FONT_SIZE_NORMAL_NUMBER + ";");
            userPrefFontSize = "-fx-font-size: " + FONT_SIZE_NORMAL_NUMBER + ";";
            fontIndex = 3;
            break;

        case FONT_SIZE_LARGE:
            commandTextField.setStyle("-fx-font-size: " + FONT_SIZE_LARGE_NUMBER + ";");
            userPrefFontSize = "-fx-font-size: " + FONT_SIZE_LARGE_NUMBER + ";";
            fontIndex = 4;
            break;

        case FONT_SIZE_XLARGE:
            commandTextField.setStyle("-fx-font-size: " + FONT_SIZE_XLARGE_NUMBER + ";");
            userPrefFontSize = "-fx-font-size: " + FONT_SIZE_XLARGE_NUMBER + ";";
            fontIndex = 5;
            break;

        default:
            break;
        }
    }
```
###### \java\seedu\address\ui\LessonListCard.java
``` java
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }

    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            setFontSizeHelper(FONT_SIZE_XSMALL_NUMBER);
            break;

        case FONT_SIZE_SMALL:
            setFontSizeHelper(FONT_SIZE_SMALL_NUMBER);
            break;

        case FONT_SIZE_NORMAL:
            setFontSizeHelper(FONT_SIZE_NORMAL_NUMBER);
            break;

        case FONT_SIZE_LARGE:
            setFontSizeHelper(FONT_SIZE_LARGE_NUMBER);
            break;

        case FONT_SIZE_XLARGE:
            setFontSizeHelper(FONT_SIZE_XLARGE_NUMBER);
            break;

        default:
            break;
        }
    }

    private void setFontSizeHelper(String fontSize) {
        code.setStyle("-fx-font-size: " + fontSize + ";");
        id.setStyle("-fx-font-size: " + fontSize + ";");
        venue.setStyle("-fx-font-size: " + fontSize + ";");
        classType.setStyle("-fx-font-size: " + fontSize + ";");
        timeSlot.setStyle("-fx-font-size: " + fontSize + ";");
        group.setStyle("-fx-font-size: " + fontSize + ";");
        lecturers.getChildren().forEach(node -> node.setStyle("-fx-font-size: " + fontSize + ";"));
    }

    private void setFontSizeUnit(FontSizeUnit currFontSizeUnit) {
        switch (currFontSizeUnit) {
        case FONT_SIZE_XSMALL_UNIT:
            setFontSize(FONT_SIZE_XSMALL);
            break;

        case FONT_SIZE_SMALL_UNIT:
            setFontSize(FONT_SIZE_SMALL);
            break;

        case FONT_SIZE_NORMAL_UNIT:
            setFontSize(FONT_SIZE_NORMAL);
            break;

        case FONT_SIZE_LARGE_UNIT:
            setFontSize(FONT_SIZE_LARGE);
            break;

        case FONT_SIZE_XLARGE_UNIT:
            setFontSize(FONT_SIZE_XLARGE);
            break;

        default:
            break;
        }
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleSwitchThemeRequestEvent(SwitchThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchTheme(event);
    }

    /**
     * Handles SwitchThemeEvent.
     */
    @FXML
    public void handleSwitchTheme(SwitchThemeRequestEvent event) {
        if (!event.isLight) {
            sceneBox.getStylesheets().clear();
            sceneBox.getStylesheets().add(DARK_THEME_CSS_FILE_NAME);
            sceneBox.getStylesheets().add(DARK_THEME_EXTENSIONS_CSS_FILE_NAME);
        } else {
            sceneBox.getStylesheets().clear();
            sceneBox.getStylesheets().add(LIGHT_THEME_CSS_FILE_NAME);
            sceneBox.getStylesheets().add(LIGHT_THEME_EXTENSIONS_CSS_FILE_NAME);
        }
    }


}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }

    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_XSMALL_NUMBER + ";");
            break;

        case FONT_SIZE_SMALL:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_SMALL_NUMBER + ";");
            break;

        case FONT_SIZE_NORMAL:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_NORMAL_NUMBER + ";");
            break;

        case FONT_SIZE_LARGE:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_LARGE_NUMBER + ";");
            break;

        case FONT_SIZE_XLARGE:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_XLARGE_NUMBER + ";");
            break;

        default:
            break;
        }
    }

}
```
###### \resources\view\DarkExtensions.css
``` css
.error {
    -fx-text-fill: #d06651 !important; /* The error class should always override the default text-fill style */
}

.list-cell:empty {
    /* Empty cells will not have alternating colours */
    -fx-background: #383838;
}

.tag-selector {
    -fx-border-width: 1;
    -fx-border-color: white;
    -fx-border-radius: 3;
    -fx-background-radius: 3;
}

.tooltip-text {
    -fx-text-fill: white;
}
```
###### \resources\view\DarkTheme.css
``` css
.background {
    -fx-background-color: yellow;
    background-color: yellow; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: monospace;
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: monospace;
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: monospace;
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: monospace;
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
    -fx-font-family: monospace;
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#1d1d1d, 20%);
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
    -fx-background-color: #3c3e3f;
}

.list-cell:filled:odd {
    -fx-background-color: #515658;
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
    -fx-font-family: monospace;
    -fx-font-size: 24px;
    -fx-text-fill: white;
}

.cell_small_label {
    -fx-font-family: monospace;
    -fx-font-size: 16px;
    -fx-text-fill: white;
}

.anchor-pane {
    -fx-background-color: derive(#1d1d1d, 60%);
}

.pane-with-border {
     -fx-background-color: derive(#1d1d1d, 20%);
     -fx-border-color: derive(#1d1d1d, 20%);
     -fx-border-width: 10px;
}

.pane-without-border {
    -fx-background-color: derive(#1d1d1d, 50%);
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: white;
}

.result-display {
    -fx-background-color: yellow;
    -fx-font-family: monospace;
    -fx-font-size: 13pt;
    -fx-font-weight: bolder;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: white !important;
}

.status-bar .label {
    -fx-font-family: monospace;
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
    -fx-background-color: derive(#1d1d1d, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: monospace;
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

.keyword-label-default {
    -fx-font-family: monospace;
    -fx-font-size: 25;
    -fx-text-fill: white;
}

.keyword-label-xsmall {
        -fx-font-family: monospace;
        -fx-font-size: 12;
        -fx-text-fill: white;
}

.keyword-label-small {
        -fx-font-family: monospace;
        -fx-font-size: 17;
        -fx-text-fill: white;
}

.keyword-label-large {
        -fx-font-family: monospace;
        -fx-font-size: 32;
        -fx-text-fill: white;
}

.keyword-label-xlarge {
        -fx-font-family: monospace;
        -fx-font-size: 40;
        -fx-text-fill: white;
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

```
###### \resources\view\LightExtensions.css
``` css
.error {
    -fx-text-fill: #d06651 !important; /* The error class should always override the default text-fill style */
}

.list-cell:empty {
    /* Empty cells will not have alternating colours */
    -fx-background: derive(#d4cbb3, 50%);
}

.tag-selector {
    -fx-border-width: 1;
    -fx-border-color: white;
    -fx-border-radius: 3;
    -fx-background-radius: 3;
}

.tooltip-text {
    -fx-text-fill: white;
}

```
###### \resources\view\LightTheme.css
``` css
.background {
    -fx-background-color: derive(#d4cbb3, 50%);
    background-color: derive(#d4cbb3, 50%); /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: monospace;
    -fx-text-fill: #6B4A40;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: monospace;
    -fx-text-fill: #6B4A40;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: monospace;
    -fx-text-fill: #6B4A40;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: monospace;
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
    -fx-border-color: #d4cbb3;

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
    -fx-font-family: monospace;
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#d4cbb3, 50%);
    -fx-border-color: transparent transparent transparent transparent;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-border-top-width: 1px;;
    -fx-background-color: derive(#d4cbb3, 50%);
}

.split-pane-no-color {
 -fx-background-color: transparent;
 -fx-border-color: transparent transparent transparent transparent;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
    -fx-background-insets: 0px, 2px;
}

.list-cell:filled:even {
    -fx-background-color: derive(#d4cbb3, 30%);
}

.list-cell:filled:odd {
    -fx-background-color: derive(#6B4A40, 80%);
}

.list-cell:filled:selected {
    -fx-border-color: yellow;
    -fx-border-width: 1;
    -fx-background-color: #ffff99;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: monospace;
    -fx-font-size: 24px;
    -fx-text-fill: #6B4A40;
}

.cell_small_label {
    -fx-font-family: monospace;
    -fx-font-size: 16px;
    -fx-text-fill: #6B4A40;
}

.anchor-pane {
    -fx-background-color: #d3d5d7;
}

.pane-with-border {
    -fx-background-color: derive(#d4cbb3, 20%);
    -fx-border-color: derive(#d4cbb3, 20%);
    -fx-border-width: 10px;
}

.pane-without-border {
    -fx-background-color: derive(#d4cbb3, 50%);
}

.pane-browser {
    -fx-border-width: 1;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: derive(#d4cbb3, 50%);
    -fx-font-family: monospace;
    -fx-font-size: 13pt;
    -fx-font-weight: bolder;
    -fx-text-fill: #6B4A40;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: monospace;
    -fx-text-fill: #6B4A40;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: #6B4A40;
}

.grid-pane {
    -fx-background-color: derive(#d4cbb3, 50%);
    -fx-border-color: transparent;
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: #d4cbb3;
    -fx-text-fill: #edafb8;
}

.context-menu {
    -fx-background-color: #d4cbb3;
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: #d4cbb3;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: monospace;
    -fx-text-fill: #6B4A40;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

.keyword-label-default {
    -fx-font-family: monospace;
    -fx-font-size: 25;
    -fx-text-fill: white;
}

.keyword-label-xsmall {
    -fx-font-family: monospace;
    -fx-font-size: 12;
    -fx-text-fill: white;
}

.keyword-label-small {
    -fx-font-family: monospace;
    -fx-font-size: 17;
    -fx-text-fill: white;
}

.keyword-label-large {
    -fx-font-family: monospace;
    -fx-font-size: 32;
    -fx-text-fill: white;
}

.keyword-label-xlarge {
    -fx-font-family: monospace;
    -fx-font-size: 40;
    -fx-text-fill: white;
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
    -fx-font-family: monospace;
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
    -fx-background-color: derive(#d4cbb3, 50%);;
}

.scroll-bar .thumb {
    -fx-background-color: #6B4A40;
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
    -fx-background-color: derive(#d4cbb3, 20%);
    -fx-background-insets: 0;
    -fx-border-color: derive(#d4cbb3, 20%);
    -fx-border-insets: 0;
    -fx-border-width: 2;
    -fx-font-family: monospace;
    -fx-font-weight: bolder;
    -fx-font-size: 25;
    -fx-text-fill: #6B4A40;
}

#commandTextFieldKeyword {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: transparent #383838 transparent #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: monospace;
    -fx-font-size: 25;
    -fx-text-fill: #6B4A40;
}

#commandTextDefault {
    -fx-font-family: monospace;
    -fx-font-size: 25;
    -fx-text-fill: #6B4A40;
}

#commandTextXsmall{
    -fx-font-family: monospace;
    -fx-font-size: 12;
    -fx-text-fill: #6B4A40;
}

#commandTextSmall{
    -fx-font-family: monospace;
    -fx-font-size: 17;
    -fx-text-fill: #6B4A40;
}

#commandTextLarge{
    -fx-font-family: monospace;
    -fx-font-size: 32;
    -fx-text-fill: #6B4A40;
}

#commandTextXLarge{
    -fx-font-family: monospace;
    -fx-font-size: 40;
    -fx-text-fill: #6B4A40;
}


#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, derive(#d4cbb3, 50%), transparent, derive(#d4cbb3, 50%);
    -fx-background-radius: 0;
}

#lecturers {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#lecturers .label {
    -fx-text-fill: derive(#d4cbb3, 50%);
    -fx-background-color: #6B4A40;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 18;
}

#lessonNode {
    -fx-font-family: monospace;
    -fx-font-size: large;
    -fx-font-weight: bolder;
    -fx-control-inner-background: #6B4A40;
    -fx-text-fill: derive(#d4cbb3, 50%);
    -fx-text-alignment: center;
}

#stickyNote {
    -fx-background-color: transparent;
    -fx-padding:5px 5px 5px;
    -fx-font:20px monospace;
    -fx-line-height:1.5;
}

#stickyNote .scroll-pane {
    -fx-background-color: transparent;
}

#stickyNote .scroll-pane .viewport{
    -fx-background-color: transparent;
}


#stickyNote .scroll-pane .content{
    -fx-background-color: transparent;
}

#header {
    -fx-font-size: 20px;
}

```
