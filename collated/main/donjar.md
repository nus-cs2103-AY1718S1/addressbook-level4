# donjar
###### /java/seedu/address/ui/ResultDisplay.java
``` java
    @Subscribe
    private void handleFontSizeChangeEvent(FontSizeChangeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        fontSizeChange = event.sizeChange;
        refreshFontSizes();
    }

    /**
     * Updates the font sizes of all components of this component with the {@code fontSizeChange} given.
     */
    private void refreshFontSizes() {
        resultDisplay.setStyle("-fx-font-size: " + (DEFAULT_FONT_SIZE + fontSizeChange));
    }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    @Subscribe
    private void handleFontSizeChangeEvent(FontSizeChangeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        fontSizeChange = event.sizeChange;
        refreshFontSizes();
    }

    /**
     * Updates the font size of this card.
     */
    private void refreshFontSizes() {
        commandTextField.setStyle("-fx-font-size: " + (DEFAULT_FONT_SIZE + fontSizeChange));
    }
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    @Subscribe
    private void handleFontSizeChangeEvent(FontSizeChangeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        fontSizeChange = event.sizeChange;
        refreshFontSizes();
    }

    /**
     * Updates the font size of this card.
     */
    private void refreshFontSizes() {
        name.setStyle("-fx-font-size: " + (DEFAULT_BIG_FONT_SIZE + fontSizeChange));
        id.setStyle("-fx-font-size: " + (DEFAULT_BIG_FONT_SIZE + fontSizeChange));

        for (Label l : new Label[] { phone, address, email, socialMedia, remark, accesses }) {
            l.setStyle("-fx-font-size: " + (DEFAULT_SMALL_FONT_SIZE + fontSizeChange));
        }

        for (Node tag : tags.getChildren()) {
            tag.setStyle("-fx-font-size: " + (DEFAULT_TAG_FONT_SIZE + fontSizeChange));
        }
    }
```
###### /java/seedu/address/model/person/Name.java
``` java
    public static final String NAME_VALIDATION_REGEX = "[\\p{L}\\p{Digit}]+( [\\p{L}\\p{Digit}]+)*";
```
###### /java/seedu/address/model/person/Phone.java
``` java
    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, dashes, parentheses, and spaces. Also, there should be at "
                    + "least 3 numbers in the string.";
    public static final String PHONE_REPLACEMENT_REGEX = "[() -]";
```
###### /java/seedu/address/model/person/Phone.java
``` java
    /**
     * Returns a phone number from the given string, by stripping certain special characters.
     */
    public static String extractPhone(String phoneNumber) throws IllegalValueException {
        String strippedPhoneNumber = phoneNumber.replaceAll(PHONE_REPLACEMENT_REGEX, "");
        if (!isValidPhone(strippedPhoneNumber)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        return strippedPhoneNumber;
    }
```
###### /java/seedu/address/model/person/NameMatchesRegexPredicate.java
``` java

package seedu.address.model.person;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameMatchesRegexPredicate implements Predicate<ReadOnlyPerson> {
    private final Pattern pattern;

    public NameMatchesRegexPredicate(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return pattern.matcher(person.getName().fullName).find();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof NameMatchesRegexPredicate) {
            String thisPattern = pattern.toString();
            String otherPattern = ((NameMatchesRegexPredicate) other).pattern.toString();
            return thisPattern.equals(otherPattern);
        }
        return false;
    }

}
```
###### /java/seedu/address/model/FontSizeOutOfBoundsException.java
``` java

package seedu.address.model;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the font size change given is out of bounds.
 */
public class FontSizeOutOfBoundsException extends IllegalValueException {
    public final int previousFontSize;
    public final int newFontSize;

    public FontSizeOutOfBoundsException(int previousFontSize, int newFontSize) {
        super("Changing the font size by this amount will result in an out of bounds font size.");
        this.previousFontSize = previousFontSize;
        this.newFontSize = newFontSize;
    }
}
```
###### /java/seedu/address/model/Model.java
``` java
    /** Upper and lower bounds for the font size change */
    int FONT_SIZE_LOWER_BOUND = -5;
    int FONT_SIZE_UPPER_BOUND = 5;
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Resets the font size of the model to its defaults.
     */
    void resetFontSize();

    /**
     * Resets the font size of the model to its defaults. Returns the new change of the font size.
     */
    int updateFontSize(int change) throws FontSizeOutOfBoundsException;
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void resetFontSize() {
        fontSizeChange = 0;
        indicateFontSizeChanged();
    }

    @Override
    public int updateFontSize(int change) throws FontSizeOutOfBoundsException {
        int newFontSizeChange = fontSizeChange + change;

        if (newFontSizeChange < FONT_SIZE_LOWER_BOUND || newFontSizeChange > FONT_SIZE_UPPER_BOUND) {
            throw new FontSizeOutOfBoundsException(fontSizeChange, newFontSizeChange);
        }

        fontSizeChange = newFontSizeChange;
        indicateFontSizeChanged();

        return fontSizeChange;
    }

    /**
     * Raises an event to indicate the font size has changed.
     */
    private void indicateFontSizeChanged() {
        raise(new FontSizeChangeRequestEvent(fontSizeChange));
    }
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
    private final int amount;

    public UndoCommand(int amount) {
        this.amount = amount;
    }
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
        int commandsUndoed = 0;
        while (undoRedoStack.canUndo() && commandsUndoed < amount) {
            undoRedoStack.popUndo().undo();
            commandsUndoed++;
        }
        return new CommandResult(getSuccessMessage(commandsUndoed));
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && this.amount == ((UndoCommand) other).amount); // state check
    }

    /**
     * Constructs the success message from the given amount. This takes into account that the number of commands
     * undone influences the singular/plural form of "command".
     */
    public static String getSuccessMessage(int amount) {
        if (amount == 1) {
            return "1 command undoed.";
        } else {
            return String.format("%1$s commands undoed.", amount);
        }
    }
```
###### /java/seedu/address/logic/commands/SizeCommand.java
``` java

package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FontSizeOutOfBoundsException;
import seedu.address.model.Model;

/**
 * Changes the font size.
 */
public class SizeCommand extends Command {

    public static final String COMMAND_WORD = "size";
    public static final String COMMAND_ALIAS = "si";
    public static final String MESSAGE_RESET_FONT_SUCCESS = "Font size successfully reset!";
    public static final String MESSAGE_CHANGE_FONT_SUCCESS = "Font size %1$s by %2$s! Current change is %3$s.";
    public static final String MESSAGE_FAILURE = "New font size out of bounds! Current change is %1$s, the command "
            + "will change it into %2$s, which is outside [" + Model.FONT_SIZE_LOWER_BOUND + ", "
            + Model.FONT_SIZE_UPPER_BOUND + "].";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the font size. If no arguments are supplied, resets the font size to its default.\n"
            + "Parameters: [SIZE_CHANGE] (must be an integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final boolean isReset;
    private final int sizeChange;

    public SizeCommand() {
        isReset = true;
        sizeChange = 0;
    }

    public SizeCommand(int sizeChange) {
        isReset = false;
        this.sizeChange = sizeChange;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (isReset) {
            model.resetFontSize();
            return new CommandResult(MESSAGE_RESET_FONT_SUCCESS);
        } else {
            try {
                int newChange = model.updateFontSize(sizeChange);

                if (sizeChange >= 0) {
                    return new CommandResult(String.format(MESSAGE_CHANGE_FONT_SUCCESS, "increased",
                                                           sizeChange, newChange));
                } else {
                    return new CommandResult(String.format(MESSAGE_CHANGE_FONT_SUCCESS, "decreased",
                                                           -1 * sizeChange, newChange));
                }
            } catch (FontSizeOutOfBoundsException e) {
                throw new CommandException(String.format(MESSAGE_FAILURE, e.previousFontSize, e.newFontSize));
            }
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SizeCommand // instanceof handles nulls
                && this.sizeChange == ((SizeCommand) other).sizeChange
                && this.isReset == ((SizeCommand) other).isReset); // state check
    }
}
```
###### /java/seedu/address/logic/commands/RedoCommand.java
``` java
    private final int amount;

    public RedoCommand(int amount) {
        this.amount = amount;
    }
```
###### /java/seedu/address/logic/commands/RedoCommand.java
``` java
        int commandsRedoed = 0;
        while (undoRedoStack.canRedo() && commandsRedoed < amount) {
            undoRedoStack.popRedo().redo();
            commandsRedoed++;
        }
        return new CommandResult(getSuccessMessage(commandsRedoed));
```
###### /java/seedu/address/logic/commands/RedoCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && this.amount == ((RedoCommand) other).amount); // state check
    }

    /**
     * Constructs the success message from the given amount. This takes into account that the number of commands
     * undone influences the singular/plural form of "command".
     */
    public static String getSuccessMessage(int amount) {
        if (amount == 1) {
            return "1 command redoed.";
        } else {
            return String.format("%1$s commands redoed.", amount);
        }
    }
```
###### /java/seedu/address/logic/commands/FindRegexCommand.java
``` java

package seedu.address.logic.commands;

import seedu.address.model.person.NameMatchesRegexPredicate;

/**
 * Finds and lists all persons in address book whose name is matched by the regex given.
 * Keyword matching is case sensitive.
 */
public class FindRegexCommand extends Command {

    public static final String COMMAND_WORD = "findregex";
    public static final String COMMAND_ALIAS = "fr";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names match "
            + "the specified regex and displays them as a list with index numbers.\n"
            + "Parameters: REGEX\n"
            + "Example: " + COMMAND_WORD + " ^Joh?n$";

    private final NameMatchesRegexPredicate predicate;

    public FindRegexCommand(NameMatchesRegexPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindRegexCommand // instanceof handles nulls
                && this.predicate.equals(((FindRegexCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/parser/RedoCommandParser.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RedoCommand object
 */
public class RedoCommandParser implements Parser<RedoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RedoCommand
     * and returns an RedoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        if (args.equals("")) {
            return new RedoCommand(1);
        }

        try {
            int amount = ParserUtil.parsePositiveInteger(args);
            return new RedoCommand(amount);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code num} into a positive integer and returns it. Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the specified argument is invalid, or is not a positive integer.
     */
    public static int parsePositiveInteger(String num) throws IllegalValueException {
        try {
            int amount = Integer.parseInt(num.trim());
            if (amount <= 0) {
                throw new IllegalValueException(MESSAGE_INVALID_NUMBER);
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_INVALID_NUMBER);
        }
    }
```
###### /java/seedu/address/logic/parser/SizeCommandParser.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SizeCommand object
 */
public class SizeCommandParser implements Parser<SizeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SizeCommand
     * and returns an SizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SizeCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new SizeCommand();
        }

        try {
            int parsed = Integer.parseInt(args.trim());
            return new SizeCommand(parsed);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SizeCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/UndoCommandParser.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        if (args.equals("")) {
            return new UndoCommand(1);
        }

        try {
            int amount = ParserUtil.parsePositiveInteger(args);
            return new UndoCommand(amount);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/FindRegexCommandParser.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REGEX;

import java.util.regex.PatternSyntaxException;

import seedu.address.logic.commands.FindRegexCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesRegexPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindRegexCommandParser implements Parser<FindRegexCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRegexCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        try {
            return new FindRegexCommand(new NameMatchesRegexPredicate(trimmed));
        } catch (PatternSyntaxException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_REGEX, FindRegexCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/commons/events/ui/FontSizeChangeRequestEvent.java
``` java

package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class FontSizeChangeRequestEvent extends BaseEvent {

    public final int sizeChange;

    public FontSizeChangeRequestEvent(int sizeChange) {
        this.sizeChange = sizeChange;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
