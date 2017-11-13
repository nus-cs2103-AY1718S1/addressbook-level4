# ChenXiaoman
###### \java\seedu\address\commons\core\GuiSettings.java
``` java
package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

import seedu.address.model.font.FontSize;
import seedu.address.model.theme.Theme;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;
    private static final String DEFAULT_FONT_SIZE = FontSize.FONT_SIZE_M_LABEL;
    private static final String DEFAULT_THEME = Theme.BRIGHT_THEME;

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;
    private String fontSize;
    private String theme;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
        this.fontSize = DEFAULT_FONT_SIZE;
        this.theme = DEFAULT_THEME;
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition, String fontSize,
                       String theme) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
        this.fontSize = fontSize;
        this.theme = theme;
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
        this.fontSize = DEFAULT_FONT_SIZE;
        this.theme = DEFAULT_THEME;
    }

    public Double getWindowWidth() {
        return windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getTheme() {
        return theme;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowCoordinates.x, o.windowCoordinates.x)
                && Objects.equals(windowCoordinates.y, o.windowCoordinates.y)
                && Objects.equals(fontSize, o.fontSize)
                && Objects.equals(theme, o.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, fontSize, theme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates + "\n");
        sb.append("Font size: " + fontSize + "\n");
        sb.append("Theme: " + theme);
        return sb.toString();
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeFontSizeEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the font size of the application
 */
public class ChangeFontSizeEvent extends BaseEvent {

    public final String message;
    private String fontSize;

    public ChangeFontSizeEvent(String message, String fontSize) {
        this.fontSize = fontSize;
        this.message = message;
    }

    public String getFontSize() {
        return fontSize;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ChangeTagColorEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the tag color of the application
 */
public class ChangeTagColorEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeThemeEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the theme of the application
 */
public class ChangeThemeEvent extends BaseEvent {
    private String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getTheme() {
        return theme;
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowPersonAddressEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to show a person's address in Google Map
 */
public class ShowPersonAddressEvent extends BaseEvent {
    private String address;

    public ShowPersonAddressEvent(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Get the address from the event
     */
    public String getAddress() {
        return address;
    }
}
```
###### \java\seedu\address\logic\commands\ChangeFontSizeCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FONT_SIZE;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.font.FontSize;

/**
 * Customise the font size of the application.
 */
public class ChangeFontSizeCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "fs";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Customize the font size "
            + "Parameters: "
            + PREFIX_FONT_SIZE + "[FONT SIZE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FONT_SIZE + "xs\n";
    public static final String MESSAGE_SUCCESS = "Changed font size to ";

    public static final String INCREASE_FONT_SIZE_COMMAND = ChangeFontSizeCommand.COMMAND_WORD + " +";
    public static final String DECREASE_FONT_SIZE_COMMAND = ChangeFontSizeCommand.COMMAND_WORD + " -";

    private final String fontSize;

    public ChangeFontSizeCommand(String fontSize) {
        this.fontSize = fontSize;
    };

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String newFontSize = fontSize;

        // Font size is valid
        if (FontSize.isValidFontSize(newFontSize)) {
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(MESSAGE_SUCCESS + newFontSize + ".",
                    newFontSize));
            FontSize.setCurrentFontSizeLabel(newFontSize);
            return new CommandResult(MESSAGE_SUCCESS + newFontSize + ".");
        } else {
            throw new CommandException(FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeFontSizeCommand // instanceof handles nulls
                && fontSize.equals(((ChangeFontSizeCommand) other).fontSize));
    }
}
```
###### \java\seedu\address\logic\commands\ChangeTagColorCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeTagColorEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

/**
 * Change color of tags
 */
public class ChangeTagColorCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "color";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the color of a tag or list of tags. "
            + "Existing color will be overwritten by the input.\n"
            + "Parameters: " + COMMAND_WORD + " "
            + PREFIX_TAG + "[TAG] "
            + PREFIX_COLOR + "[COLOR]\n"
            + "Example: " + COMMAND_WORD + " t/friend "
            + "c/red";

    public static final String MESSAGE_NOT_EXISTING_TAGS = "Cannot change color of not existing tags: %1$s";

    public static final String MESSAGE_FAILED = "Change tag color command failed";

    public static final String MESSAGE_CHANGE_TAG_COLOR_SUCCESS = "Change tag color of %1$s to %2$s";

    private final Set<Tag> tagList;
    private final TagColor color;

    /**
     * Constructor
     * @param tagList to edit the color
     * @param color of the tagList
     */
    public ChangeTagColorCommand(Set<Tag> tagList, TagColor color) {
        requireNonNull(tagList);
        requireNonNull(color);

        this.tagList = tagList;
        this.color = color;
    }

    /**
     * Check whether a given tag exists in current database.
     * @param t
     * @return whether a given tag exists
     */
    private boolean isExistingTagName(Tag t) {
        for (Tag tag : model.getAddressBook().getTagList()) {
            if (tag.tagName.equals(t.tagName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        // Store all non existing tags.
        Set<Tag> nonExistingTagList = new HashSet<>();

        // Check whether tags in the tagList are not existing tags.
        for (Tag tag: tagList) {
            if (!isExistingTagName(tag)) {
                nonExistingTagList.add(tag);
            }
        }

        // There are tags that are not existing tags.
        if (nonExistingTagList.size() != 0) {
            throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS, nonExistingTagList));
        }

        try {
            model.updateTagColorPair(tagList, color);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_FAILED);
        }

        EventsCenter.getInstance().post(new ChangeTagColorEvent());

        return new CommandResult(String.format(MESSAGE_CHANGE_TAG_COLOR_SUCCESS, tagList, color.tagColorName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeTagColorCommand)) {
            return false;
        }

        // state check
        ChangeTagColorCommand e = (ChangeTagColorCommand) other;
        return tagList.equals(e.tagList)
                && color.equals(e.color);
    }
}
```
###### \java\seedu\address\logic\commands\ChangeThemeCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.theme.Theme.ALL_THEME_NAMES;
import static seedu.address.model.theme.Theme.BRIGHT_THEME;
import static seedu.address.model.theme.Theme.DARK_THEME;
import static seedu.address.model.theme.Theme.isValidThemeName;

import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.theme.Theme;

/**
 * Change the theme of the application
 */
public class ChangeThemeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "theme";

    public static final String CHENG_TO_DARK_THEME_COMMAND = COMMAND_WORD + " " + DARK_THEME;
    public static final String CHENG_TO_BRIGHT_THEME_COMMAND = COMMAND_WORD + " " + BRIGHT_THEME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the theme of the application. "
            + "Parameters: " + COMMAND_WORD + " "
            + "[THEME]\n"
            + "Example: " + COMMAND_WORD + " bright";

    public static final String MESSAGE_INVALID_THEME_NAME = "The theme %1$s is not supported. "
            + "Supported themes are: "
            + Arrays.toString(ALL_THEME_NAMES);

    public static final String MESSAGE_CHANGE_THEME_SUCCESS = "Changed theme to %1$s.";

    private final String theme;

    /**
     * Constructor
     * @param theme the new theme
     */
    public ChangeThemeCommand(String theme) {
        requireNonNull(theme);
        this.theme = theme;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        // Check whether a given theme name is valid
        if (!isValidThemeName(theme)) {
            throw new CommandException(String.format(MESSAGE_INVALID_THEME_NAME, theme));
        }

        Theme.setCurrentTheme(theme);

        EventsCenter.getInstance().post(new ChangeThemeEvent(theme));

        return new CommandResult(String.format(MESSAGE_CHANGE_THEME_SUCCESS, theme));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && this.theme.equals(((ChangeThemeCommand) other).theme)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\MapCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowPersonAddressEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Shows a person's address identified using person's last displayed index from the list.
 */
public class MapCommand extends Command {
    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the address of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Showing the address of Person: %1$s";

    private final Index targetIndex;

    public MapCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        // The index is invalid because it is larger than the size of entire person list.
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Get the peron with the index.
        ReadOnlyPerson person = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        // Post an event to show the address.
        EventsCenter.getInstance().post(new ShowPersonAddressEvent(person.getAddress().value));

        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, person.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.targetIndex.equals(((MapCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\UndoableCommand.java
``` java

        //Revert font size
        if (this instanceof ChangeFontSizeCommand) {
            FontSize.setCurrentFontSizeLabel(previousFontSize);
            EventsCenter.getInstance().post(new ChangeFontSizeEvent("", previousFontSize));
        }

        //Revert theme
        if (this instanceof ChangeThemeCommand) {
            Theme.setCurrentTheme(previousTheme);
            EventsCenter.getInstance().post(new ChangeThemeEvent(previousTheme));
        }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ChangeTagColorCommand.COMMAND_WORD:
            return new ChangeTagColorCommandParser().parse(arguments);

        case ChangeFontSizeCommand.COMMAND_WORD:
            return new ChangeFontSizeCommandParser().parse(arguments);

        case ChangeThemeCommand.COMMAND_WORD:
            return new ChangeThemeCommandParser().parse(arguments);

        case MapCommand.COMMAND_WORD:
            return new MapCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\ChangeFontSizeCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FONT_SIZE;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeFontSizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.font.FontSize;

/**
 * Parses input arguments and creates a new ChangeFontSizeCommand object
 */
public class ChangeFontSizeCommandParser implements Parser<ChangeFontSizeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeFontSizeCommand
     * and returns an ChangeFontSizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeFontSizeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FONT_SIZE);

        if (!arePrefixesPresent(argMultimap, PREFIX_FONT_SIZE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeFontSizeCommand.MESSAGE_USAGE));
        }

        try {
            FontSize fontSize = ParserUtil.parseFontSize(argMultimap.getValue(PREFIX_FONT_SIZE)).get();

            return new ChangeFontSizeCommand(fontSize.getValue());
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
###### \java\seedu\address\logic\parser\ChangeTagColorCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

/**
 * Parses input arguments and creates a new ChangeTagColorCommand object
 */
public class ChangeTagColorCommandParser implements Parser<ChangeTagColorCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeTagColorCommand
     * and returns an ChangeTagColorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeTagColorCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + args, PREFIX_TAG, PREFIX_COLOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG, PREFIX_COLOR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeTagColorCommand.MESSAGE_USAGE));
        }

        try {

            TagColor tagColor = ParserUtil.parseTagColor(argMultimap.getValue(PREFIX_COLOR)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            return new ChangeTagColorCommand(tagList, tagColor);
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
###### \java\seedu\address\logic\parser\ChangeThemeCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangeThemeCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeThemeCommand
     * and returns an ChangeThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ChangeThemeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }
        return new ChangeThemeCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\logic\parser\MapCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MapCommand object
 */
public class MapCommandParser implements Parser<MapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns an MapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MapCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Update the tag color pair in storage
     * @param modifyingTagList tags that need to be changed color
     * @param color
     * @throws IllegalValueException
     */
    public void updateTagColorPair(Set<Tag> modifyingTagList, TagColor color) throws IllegalValueException {
        // Set the list of tags to new list of tags
        setTags(getUpdatedTagColorPair(modifyingTagList, tags.toSet(), color));

        updateTagColorInEveryPerson(modifyingTagList, color);
    }

    /**
     * Get a list of given tags with updated color
     * @param modifyingTagList
     * @param existingTagList
     * @param color
     * @return list of updated tags
     * @throws IllegalValueException
     */
    private Set<Tag> getUpdatedTagColorPair(Set<Tag> modifyingTagList, Set<Tag> existingTagList, TagColor color)
            throws IllegalValueException {
        // To store updated list of tags
        Set<Tag> updatedTags = new HashSet<>();

        for (Tag existingTag: existingTagList) {
            for (Tag modifyingTag: modifyingTagList) {

                // Check whether a tag needs to be changed its color
                if (modifyingTag.equals(existingTag)) {

                    // Change the color of the tag
                    updatedTags.add(new Tag(modifyingTag.tagName, color.tagColorName));

                }
            }

            // This tag doesn't need to be changed
            if (!updatedTags.contains(existingTag)) {
                // Remain unchanged
                updatedTags.add(existingTag);
            }
        }

        return updatedTags;

    }

    /**
     * Update tag and color pair in every person
     *
     * @param modifyingTagList
     * @param tagColor
     * @throws IllegalValueException
     */
    private void updateTagColorInEveryPerson(Set<Tag> modifyingTagList, TagColor tagColor)
            throws IllegalValueException {
        for (Person person : persons) {
            Set<Tag> updatedTagList = getUpdatedTagColorPair(modifyingTagList, person.getTags(), tagColor);
            person.setTags(updatedTagList);
        }
    }
```
###### \java\seedu\address\model\font\FontSize.java
``` java
package seedu.address.model.font;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the font size of the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidFontSize(String)}
 */
public class FontSize {

    public static final String MESSAGE_FONT_SIZE_CONSTRAINTS =
            "Font size can only be either \"xs\", \"s\","
                    + " \"m\", \"l\",  or \"xl\"";
    public static final String MESSAGE_FONT_SIZE_IS_LARGEST =
            "The current font size is the largest one.";
    public static final String MESSAGE_FONT_SIZE_IS_SMALLEST =
            "The current font size is the smallest one.";

    public static final String[] FONT_SIZE_LIST = {"xs", "s", "m", "l", "xl"};
    public static final String[] FONT_SIZE_CHANGE_SYMBOL = {"+", "-"};

    public static final String FONT_SIZE_XS_LABEL = "xs";
    public static final String FONT_SIZE_S_LABEL = "s";
    public static final String FONT_SIZE_M_LABEL = "m";
    public static final String FONT_SIZE_L_LABEL = "l";
    public static final String FONT_SIZE_XL_LABEL = "xl";

    private static final String JAVAFX_FONT_SIZE_PREFIX = "-fx-font-size: ";
    private static final String JAVAFX_FONT_SIZE_XS = "small";
    private static final String JAVAFX_FONT_SIZE_S = "medium";
    private static final String JAVAFX_FONT_SIZE_M = "large";
    private static final String JAVAFX_FONT_SIZE_L = "x-large";
    private static final String JAVAFX_FONT_SIZE_XL = "xx-large";

    private static final String NAME_LABEL_SIZE_XS = "15";
    private static final String NAME_LABEL_SIZE_S = "20";
    private static final String NAME_LABEL_SIZE_M = "25";
    private static final String NAME_LABEL_SIZE_L = "30";
    private static final String NAME_LABEL_SIZE_XL = "35";

    private static final int IMAGE_SIZE_XS = 15;
    private static final int IMAGE_SIZE_S = 20;
    private static final int IMAGE_SIZE_M = 25;
    private static final int IMAGE_SIZE_L = 30;
    private static final int IMAGE_SIZE_XL = 35;

    private static final int PHOTO_SIZE_XS = 45;
    private static final int PHOTO_SIZE_S = 50;
    private static final int PHOTO_SIZE_M = 55;
    private static final int PHOTO_SIZE_L = 60;
    private static final int PHOTO_SIZE_XL = 65;

    private static String currentFontSizeLabel = FONT_SIZE_M_LABEL;

    private final String value;

    /**
     * Validates given font size.
     *
     * @param fontSize
     * @throws IllegalValueException if given font size is invalid.
     */
    public FontSize(String fontSize) throws IllegalValueException {
        requireNonNull(fontSize);
        if (isValidChangeFontSizeSymbol(fontSize)) {

            // Get the new font size from "+" or "-" symbol base on current font size
            fontSize = getFontSizeFromChangeSymbol(fontSize);

        } else if (!isValidFontSize(fontSize)) {
            throw new IllegalValueException(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
        this.value = fontSize;
    }

    /**
     * Get the String value of the font size
     */
    public String getValue() {
        return value;
    }

    /**
     * Get a increased/decreased font size from "+" or "-"  symbol
     *
     * @param symbol
     * @return increased/decreased font size based on current font size
     * @throws IllegalValueException
     */
    private String getFontSizeFromChangeSymbol(String symbol) throws IllegalValueException {
        int fontSizeListLength = FONT_SIZE_LIST.length;

        // Increase the font size
        if (symbol.equals("+")) {

            // Find which is the current font size except the largest one
            for (int i = 0; i < fontSizeListLength - 1; i++) {

                // The current font size is at the ith place in the list
                if (currentFontSizeLabel.equals(FONT_SIZE_LIST[i])) {

                    // Get the next largest font size
                    return FONT_SIZE_LIST[i + 1];
                }
            }

            // Current font size is the largest font size
            throw new IllegalValueException(MESSAGE_FONT_SIZE_IS_LARGEST);

        } else {
            // Decrease the font size

            // Find which is the current font size except the smallest one
            for (int i = 1; i < fontSizeListLength; i++) {

                // The current font size is at the ith place in the list
                if (currentFontSizeLabel.equals(FONT_SIZE_LIST[i])) {
                    // Get the next smaller size
                    return FONT_SIZE_LIST[i - 1];
                }
            }

            // Current font size is the smallest font size
            throw new IllegalValueException(MESSAGE_FONT_SIZE_IS_SMALLEST);
        }
    }

    /**
     * Check whether the change symbol is valid
     *
     * @param symbol
     * @return validity of a symbol
     */
    private boolean isValidChangeFontSizeSymbol(String symbol) {
        for (String s : FONT_SIZE_CHANGE_SYMBOL) {
            if (symbol.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a given string is a valid font size
     *
     * @param test
     * @return validity of a the String
     */
    public static boolean isValidFontSize(String test) {
        for (String s : FONT_SIZE_LIST) {
            if (test.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the current font size
     *
     * @return the current font size
     */
    public static String getCurrentFontSizeLabel() {
        return currentFontSizeLabel;
    }

    /**
     * Set the current font size to a given new font size
     *
     * @param newFontSizeLabel
     */
    public static void setCurrentFontSizeLabel(String newFontSizeLabel) {
        requireNonNull(newFontSizeLabel);

        if (isValidFontSize(newFontSizeLabel)) {
            currentFontSizeLabel = newFontSizeLabel;
        }
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


    /**
     * Get the associate JavaFX format String for a give font size
     *
     * @param inputFontSize
     */
    public static String getAssociateFxFontSizeString(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        String fxFontSizeString = JAVAFX_FONT_SIZE_PREFIX;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_XS;
            break;

        case FONT_SIZE_S_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_S;
            break;

        case FONT_SIZE_M_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_M;
            break;

        case FONT_SIZE_L_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_L;
            break;

        case FONT_SIZE_XL_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_XL;
            break;

        default:
            fxFontSizeString += JAVAFX_FONT_SIZE_M;
        }

        fxFontSizeString += ";";
        return fxFontSizeString;
    }

    /**
     * Get the associate JavaFX format String for a give font size of name
     *
     * @param inputFontSize
     * @return JavaFX format String
     */
    public static String getAssociateFxFontSizeStringForName(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        String fxFontSizeString = JAVAFX_FONT_SIZE_PREFIX;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_XS;
            break;

        case FONT_SIZE_S_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_S;
            break;

        case FONT_SIZE_M_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_M;
            break;

        case FONT_SIZE_L_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_L;
            break;

        case FONT_SIZE_XL_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_XL;
            break;

        default:
            fxFontSizeString += NAME_LABEL_SIZE_M;
        }

        fxFontSizeString += ";";
        return fxFontSizeString;
    }

    /**
     * Get associate image size from a given font size
     *
     * @param inputFontSize
     * @return image size
     */
    public static int getAssociateImageSizeFromFontSize(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        int imageSize;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            imageSize = IMAGE_SIZE_XS;
            break;

        case FONT_SIZE_S_LABEL:
            imageSize = IMAGE_SIZE_S;
            break;

        case FONT_SIZE_M_LABEL:
            imageSize = IMAGE_SIZE_M;
            break;

        case FONT_SIZE_L_LABEL:
            imageSize = IMAGE_SIZE_L;
            break;

        case FONT_SIZE_XL_LABEL:
            imageSize = IMAGE_SIZE_XL;
            break;

        default:
            imageSize = IMAGE_SIZE_M;
        }
        return imageSize;
    }

    /**
     * Get associate photo size from a given font size
     *
     * @param inputFontSize
     * @return photo size
     */
    public static int getAssociatePhotoSizeFromFontSize(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        int photoSize;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            photoSize = PHOTO_SIZE_XS;
            break;

        case FONT_SIZE_S_LABEL:
            photoSize = PHOTO_SIZE_S;
            break;

        case FONT_SIZE_M_LABEL:
            photoSize = PHOTO_SIZE_M;
            break;

        case FONT_SIZE_L_LABEL:
            photoSize = PHOTO_SIZE_L;
            break;

        case FONT_SIZE_XL_LABEL:
            photoSize = PHOTO_SIZE_XL;
            break;

        default:
            photoSize = PHOTO_SIZE_M;
        }
        return photoSize;
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Update color of tags
     * @param tagList
     * @param color
     */
    void updateTagColorPair(Set<Tag> tagList, TagColor color) throws IllegalValueException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void updateTagColorPair(Set<Tag> tagList, TagColor color) throws IllegalValueException {
        addressBook.updateTagColorPair(tagList, color);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag of a person.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;
    public final TagColor tagColor;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = trimmedName;
        this.tagColor = new TagColor(TagColor.DEFAULT_TAG_COLOR);
    }

    /**
     * Validates given tag name and color
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name, String color) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = trimmedName;
        this.tagColor = new TagColor(color);
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
```
###### \java\seedu\address\model\tag\TagColor.java
``` java
package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represent a color of a tag
 */
public class TagColor {

    public static final String[] VALID_TAG_COLOR = {"red", "blue", "green", "teal", "aqua",
                                                    "black", "gray", "lime", "maroon", "navy",
                                                    "orange", "purple", "silver", "olive",
                                                    "white", "yellow", "transparent"};

    public static final String MESSAGE_TAG_COLOR_CONSTRAINTS = "Valid colors are: "
            + Arrays.toString(VALID_TAG_COLOR);

    public static final String DEFAULT_TAG_COLOR = "orange";

    public final String tagColorName;

    /**
     * Validates given tagColor name.
     *
     * @param name
     * @throws IllegalValueException if the given tagColor name string is invalid.
     */
    public TagColor(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagColorName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_COLOR_CONSTRAINTS);
        }
        this.tagColorName = trimmedName;
    }

    /**
     * Check if a given string is a valid tag color name.
     *
     * @param test
     * @return validity of a tag color name
     */
    public static boolean isValidTagColorName(String test) {
        return Arrays.asList(VALID_TAG_COLOR).contains(test);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagColor // instanceof handles nulls
                && this.tagColorName.equals(((TagColor) other).tagColorName)); // state check
    }

    @Override
    public int hashCode() {
        return tagColorName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return tagColorName;
    }
}
```
###### \java\seedu\address\model\theme\Theme.java
``` java
/**
 * Represent a theme of an application
 * Guarantees: immutable; name is valid as declared in {@link #isValidThemeName(String)}
 */
public class Theme {

    public static final String DARK_THEME = "dark";
    public static final String BRIGHT_THEME = "bright";
    public static final String[] ALL_THEME_NAMES = {DARK_THEME, BRIGHT_THEME};
    public static final String DARK_THEME_CSS_FILE_NAME = "view/DarkTheme.css";
    public static final String BRIGHT_THEME_CSS_FILE_NAME = "view/BrightTheme.css";

    private static String currentTheme;

    /**
     * Change the current theme
     */
    public static void setCurrentTheme(String currentTheme) {
        requireNonNull(currentTheme);
        assert (isValidThemeName(currentTheme));
        if (isValidThemeName(currentTheme)) {
            Theme.currentTheme = currentTheme;
        }
    }

    /**
     * Get the current theme
     */
    public static String getCurrentTheme() {
        return currentTheme;
    }

    /**
     * Returns true if a given string is a valid theme name.
     */
    public static boolean isValidThemeName(String test) {
        return Arrays.asList(ALL_THEME_NAMES).contains(test);
    }

    /**
     * Change the theme to the given theme name
     * @param primaryStage
     * @param newTheme
     */
    public static void changeTheme(Stage primaryStage, String newTheme) {
        isValidThemeName(currentTheme);

        if (isValidThemeName(newTheme)) {
            Scene scene = primaryStage.getScene();

            // Clear the original theme
            scene.getStylesheets().clear();

            // Add new theme to scene
            String cssFileName = null;

            // Get the associate CSS file path for theme
            switch (newTheme) {
            case DARK_THEME:
                cssFileName = DARK_THEME_CSS_FILE_NAME;
                break;
            case BRIGHT_THEME:
                cssFileName = BRIGHT_THEME_CSS_FILE_NAME;
                break;
            default:
                cssFileName = DARK_THEME_CSS_FILE_NAME;
                break;
            }

            // Set the theme to scene
            scene.getStylesheets().add(cssFileName);
            primaryStage.setScene(scene);
        }
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java

    /**
     * Handles the key released event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyReleased(KeyEvent keyEvent) {

        String userInput = commandTextField.getText();

        // If the user has not type in anything yet, there is no need to show error message
        if (userInput.length() != 0) {

            // Parse the user input while user is typing and show the error message if the command is invalid
            parseInput(keyEvent.getCode(), userInput);
        }
    }

    /**
     * Parse user input and raise event to show corresponding message
     */
    private void parseInput(KeyCode keyCode, String userInput) {
        logger.info("Parsing user input: " + userInput);
        try {
            // Try to parse the command to check whether the command is valid
            addressBookParser.parseCommand(userInput);

            if (!keyCode.equals(KeyCode.ENTER)) {

                // If the command is valid, show format valid message
                // If user presses enter key to execute the command, don't show parse message
                raise(new NewResultAvailableEvent("Command format is valid", false));
            }
        } catch (ParseException e) {

            // If user is entering invalid command, shows error message
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
    }

    /**
     * Sets the command box style to user preferred font size.
     */
    private void setFontSize(String newFontSize) {
        String fxFormatFontSize = FontSize.getAssociateFxFontSizeString(newFontSize);
        commandTextField.setStyle(fxFormatFontSize);
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
package seedu.address.ui;

import static seedu.address.model.font.FontSize.getAssociateFxFontSizeString;
import static seedu.address.model.font.FontSize.getAssociateFxFontSizeStringForName;

import java.io.File;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.ChangeTagColorEvent;
import seedu.address.commons.events.ui.ShowPersonAddressEvent;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.font.FontSize;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private Label address;
    @FXML
    private Label date;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;
    @FXML
    private ImageView image;
    @FXML
    private ImageView imagePhone;
    @FXML
    private ImageView imageEmail;
    @FXML
    private ImageView imageAddress;
    @FXML
    private ImageView imageBirth;
    @FXML
    private ImageView imageRemark;



    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        bindListeners(person);
        registerAsAnEventHandler(this);
        String currentFontSize = FontSize.getCurrentFontSizeLabel();
        setFontSize(currentFontSize);
        setSizeForAllImagesAccordingToFontSize(currentFontSize);
        setSizeForPhotosAccordingToFontSize(currentFontSize);
        initTags(person, currentFontSize);
    }

    /**
     * Get the label address
     */
    public Label getAddressLabel() {
        return address;
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        date.textProperty().bind(Bindings.convert(person.dateOfBirthProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();

            initTags(person, FontSize.getCurrentFontSizeLabel());

        });
        try {
            assignImageToPerson(person.getImage().getFilePath());
        }
        catch (ParseException pe) {
            new AssertionError("Invalid input");
        }

    }

    @Subscribe
    private void handleChangeTagColorEvent(ChangeTagColorEvent event) {
        initTags(person, FontSize.getCurrentFontSizeLabel());
    }

    /**
     * Initialize tag color and font size for each tag
     *
     * @param person
     */
    private void initTags(ReadOnlyPerson person, String fontSizeLabel) {
        tags.getChildren().clear();

        String fxFormatFontSize = FontSize.getAssociateFxFontSizeString(fontSizeLabel);

        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle(fxFormatFontSize + "-fx-background-color: " + tag.tagColor.tagColorName);
            tags.getChildren().add(tagLabel);
        });
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

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        String newFontSize = event.getFontSize();
        initTags(person, newFontSize);
        setFontSize(newFontSize);
        setSizeForAllImagesAccordingToFontSize(newFontSize);
        setSizeForPhotosAccordingToFontSize(newFontSize);
    }

    private void setFontSize(String newFontSize) {
        assert (FontSize.isValidFontSize(newFontSize));

        String fxFormatFontSize = getAssociateFxFontSizeString(newFontSize);
        String fxFormatFontSizeForName = getAssociateFxFontSizeStringForName(newFontSize);

        setFontSizeForAllAttributesExceptTag(fxFormatFontSizeForName, fxFormatFontSize);
    }


    private void setFontSizeForAllAttributesExceptTag(String nameFontSize, String fontSize) {
        name.setStyle(nameFontSize);
        id.setStyle(nameFontSize);
        phone.setStyle(fontSize);
        address.setStyle(fontSize);
        email.setStyle(fontSize);
        date.setStyle(fontSize);
        remark.setStyle(fontSize);
    }

    private void setSizeForAllImagesAccordingToFontSize(String fontSize) {
        int newImageSize = FontSize.getAssociateImageSizeFromFontSize(fontSize);

        imagePhone.setFitHeight(newImageSize);
        imagePhone.setFitWidth(newImageSize);

        imageAddress.setFitHeight(newImageSize);
        imageAddress.setFitWidth(newImageSize);

        imageEmail.setFitHeight(newImageSize);
        imageEmail.setFitWidth(newImageSize);

        imageBirth.setFitHeight(newImageSize);
        imageBirth.setFitWidth(newImageSize);

        imageRemark.setFitHeight(newImageSize);
        imageRemark.setFitWidth(newImageSize);
    }

    private void setSizeForPhotosAccordingToFontSize(String fontSize) {
        int newImageSize = FontSize.getAssociatePhotoSizeFromFontSize(fontSize);
        image.setFitWidth(newImageSize);
        image.setFitHeight(newImageSize);
    }

    @FXML
    private void handleAddressClick() {
        raise(new ShowPersonAddressEvent(address.getText()));
    }

}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
    }

    private void setFontSize(String fontSize) {
        String fxFomatString = FontSize.getAssociateFxFontSizeString(fontSize);
        resultDisplay.setStyle(fxFomatString);
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
    }

    private void setFontSize(String fontSize) {
        String fxFomatString = FontSize.getAssociateFxFontSizeString(fontSize);
        syncStatus.setStyle(fxFomatString);
        saveLocationStatus.setStyle(fxFomatString);
        totalPersons.setStyle(fxFomatString);
    }
}
```
###### \resources\view\MainWindow.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">

    <stylesheets>
        <URL value="@Extensions.css"/>
    </stylesheets>

    <SplitPane dividerPositions="0.5, 0.5" prefHeight="0.0" prefWidth="690.0">
        <items>
            <MenuBar fx:id="menuBar" prefHeight="29.0">
                <Menu mnemonicParsing="false" text="File">
                    <MenuItem id="logout" mnemonicParsing="false" onAction="#handleLogoutEvent" text="Logout"/>
                    <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit"/>
                </Menu>
                <Menu mnemonicParsing="false" text="Help">
                    <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help"/>
                </Menu>
                <Menu mnemonicParsing="false" text="Theme">
                    <MenuItem fx:id="brightTheme" mnemonicParsing="false" onAction="#handleChangeBrightTheme"
                              text="Bright"/>
                    <MenuItem fx:id="darkTheme" mnemonicParsing="false" onAction="#handleChangeDarkTheme" text="Dark"/>
                </Menu>
            </MenuBar>

            <Button alignment="TOP_RIGHT" onAction="#handleIncreaseFontSize" text="+" wrapText="true"/>

            <Button alignment="TOP_RIGHT" onAction="#handleDecreaseFontSize" text="-"
                    wrapText="true"/>
        </items>
    </SplitPane>
    <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
        <padding>
            <Insets bottom="5" left="10" right="10" top="5" />
        </padding>
    </StackPane>

    <StackPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="pane-with-border" VBox.vgrow="NEVER">
        <padding>
            <Insets bottom="5" left="10" right="10" top="5" />
        </padding>
    </StackPane>

    <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
        <VBox fx:id="personList" maxWidth="350.0" minWidth="159.0" prefWidth="350.0" SplitPane.resizableWithParent="false">
            <padding>
                <Insets bottom="10" left="10" right="10" top="10" />
            </padding>
            <StackPane fx:id="personListPanelPlaceholder" maxWidth="400.0" prefWidth="375.0" VBox.vgrow="ALWAYS" />
        </VBox>

        <StackPane fx:id="browserPlaceholder" prefWidth="700.0" SplitPane.resizableWithParent="false">
            <padding>
                <Insets bottom="10" left="10" right="10" top="10" />
            </padding>

        </StackPane>
        <VBox fx:id="reminders" maxWidth="340.0" prefWidth="320.0">
            <children>
                <StackPane fx:id="reminderListPlaceholder" layoutX="20.0" layoutY="20.0" prefHeight="590.0" />
            </children>
        </VBox>
    </SplitPane>


    <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />

</VBox>
```
###### \resources\view\PersonListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" prefHeight="71.0" prefWidth="130.0" spacing="5.0" HBox.hgrow="ALWAYS">
                <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" HBox.hgrow="ALWAYS">
                    <children>
                        <HBox alignment="CENTER_LEFT">
                            <children>
                                <Label fx:id="id" alignment="BOTTOM_LEFT" styleClass="cell_big_label">
                                    <minWidth>
                                        <!-- Ensures that the label text is never truncated -->
                                        <Region fx:constant="USE_PREF_SIZE" />
                                    </minWidth>
                                </Label>
                                <Label fx:id="name" alignment="BOTTOM_LEFT" styleClass="cell_big_label" text="\$first" />
                            </children>
                     <padding>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                     </padding>
                        </HBox>
                        <FlowPane fx:id="tags">
                     <VBox.margin>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                     </VBox.margin>
                        </FlowPane>
                    </children>
               <padding>
                  <Insets top="5.0" />
               </padding>
                </VBox>
```
###### \resources\view\PersonListCard.fxml
``` fxml
                <padding>
                    <Insets top="5.0" />
                </padding>

            </HBox>
            <HBox alignment="CENTER_LEFT" spacing="5.0">
                <ImageView fx:id="imagePhone" pickOnBounds="true" preserveRatio="true">
                    <image>
                        <Image url="/images/user.png" />
                    </image>
                </ImageView>
                <Label fx:id="phone" styleClass="cell_small_label" text="\$phone">
                    <padding>
                        <Insets top="5.0" />
                    </padding>
                </Label>
                <padding>
                    <Insets top="5.0" />
                </padding>
            </HBox>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <ImageView fx:id="imageAddress" onMousePressed="#handleAddressClick" pickOnBounds="true" preserveRatio="true">
                    <image>
                        <Image url="/images/address.png" />
                    </image>
                </ImageView>
                <Label fx:id="address" onMousePressed="#handleAddressClick" styleClass="cell_small_label" text="\$address">
                    <padding>
                        <Insets top="5.0" />
                    </padding>
                </Label>
                <padding>
                    <Insets top="5.0" />
                </padding>
            </HBox>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <ImageView fx:id="imageEmail" pickOnBounds="true" preserveRatio="true">
                    <image>
                        <Image url="/images/email.png" />
                    </image>
                </ImageView>
                <Label fx:id="email" styleClass="cell_small_label" text="\$email">
                    <padding>
                        <Insets top="5.0" />
                    </padding>
                </Label>
                <padding>
                    <Insets top="5.0" />
                </padding>
            </HBox>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <ImageView fx:id="imageBirth" pickOnBounds="true" preserveRatio="true">
                    <image>
                        <Image url="/images/birthday.png" />
                    </image>
                </ImageView>
                <Label fx:id="date" styleClass="cell_small_label" text="\$dateOfBirth">
                    <padding>
                        <Insets top="5.0" />
                    </padding>
                </Label>
                <padding>
                    <Insets top="5.0" />
                </padding>
            </HBox>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <ImageView fx:id="imageRemark" pickOnBounds="true" preserveRatio="true">
                    <image>
                        <Image url="/images/remark.png" />
                    </image>
                </ImageView>
                <Label fx:id="remark" styleClass="cell_small_label" text="\$remark">
                    <padding>
                        <Insets top="5.0" />
                    </padding>
                </Label>
                <padding>
                    <Insets top="5.0" />
                </padding>
            </HBox>
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
