# qihao27
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String MESSAGE_PERSON_NAME_ABSENT = "The person name provided is absent";
    public static final String MESSAGE_PERSON_NAME_INSUFFICIENT = "The person name provide is too short."
            + "\nRequire more than 3 characters";
```
###### \java\seedu\address\commons\events\ui\NewResultCheckEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 * And checks the validity of the entered command.
 */
public class NewResultCheckEvent extends BaseEvent {

    public final String message;
    public final boolean isError;

    public NewResultCheckEvent(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if {@code s} represents letters or numbers
     * e.g. abc, as12, gg, ..., <br>
     * Will return false for any other non-alnum string input
     * e.g. empty string, " abc " (untrimmed), "1a#" (contains special character)
     * Will return false if the input string case does not match the string stored (case sensitive)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isAlnumOnly(String s) {
        requireNonNull(s);

        try {
            return s.matches("[\\p{Alnum}][\\p{Alnum} ]*");
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} represents lower case [OPTION] String
     * e.g. -n, -p, -t, ..., <br>
     * Will return false for any other non-OPTION string input
     * e.g. empty string, " -n " (untrimmed), "- n"(contains whitespace), "- p2" (contains number),
     * "-E" (contains capital letter)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isSortOption(String s) {
        requireNonNull(s);

        try {
            return s.equals("-n") || s.equals("-p") || s.equals("-e") || s.equals("-a") || s.equals("-t");
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} represents file path
     * e.g. data/addressbook.xml, C:\addressbook.xml, ..., <br>
     * Will return false for any other non-file-path string input
     * e.g. empty string, " data/addressbook.xml " (untrimmed), "data/ addressbook.xml"(contains whitespace),
     * "data/addressbook.doc"(non xml file).
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isFilePath(String s) {
        requireNonNull(s);

        try {
            return s.matches("[\\p{Alnum}][\\p{Graph} ]*[.xml]$");
        } catch (IllegalArgumentException ipe) {
            return false;
        }
    }
```
###### \java\seedu\address\logic\commands\DeleteAltCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using its last displayed index from the address book.
 */
public class DeleteAltCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name.\n"
            + "Parameters: NAME (must be alphabetical letters)\n"
            + "Example: " + COMMAND_WORD + " john";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final String targetName;

    public DeleteAltCommand(String targetName) {
        this.targetName = targetName.toLowerCase();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        int index = 0;

        for (ReadOnlyPerson p : lastShownList) {
            if (p.getName().toString().toLowerCase().contains(targetName) && targetName.length() > 3) {
                index = lastShownList.indexOf(p);
                break;
            } else {
                index = -1;
            }
        }

        if (index >= lastShownList.size() || index == -1) {
            if (targetName.length() <= 3) {
                throw new CommandException(Messages.MESSAGE_PERSON_NAME_INSUFFICIENT);
            } else {
                throw new CommandException(Messages.MESSAGE_PERSON_NAME_ABSENT);
            }
        }

        ReadOnlyPerson personToDelete = lastShownList.get(index);

        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAltCommand // instanceof handles nulls
                && this.targetName.equals(((DeleteAltCommand) other).targetName)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Export a copy of the address book to a user-specified filepath.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "x";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Export the AddressBook into preferred location with customizable file name.\n"
        + "New folder will be created if the specified folder is not present in the directory.\n"
        + "Parameters: FILEPATH (must be a xml file path)\n"
        + "Example: " + COMMAND_WORD + " docs/MyAddressBook.xml"
        + "Example: " + COMMAND_WORD + " C:\\MyAddressBook.xml";

    public static final String MESSAGE_FILE_EXPORTED = "Addressbook exported : ";

    private final String userPrefsFilePath;

    public ExportCommand(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ReadOnlyAddressBook localAddressBook = model.getAddressBook();
        AddressBookStorage exportStorage = new XmlAddressBookStorage("data/addressbook.xml");

        try {
            exportStorage.saveAddressBook(localAddressBook, userPrefsFilePath);
        } catch (IOException e) {
            assert false : "IO error";
        }

        return new CommandResult(MESSAGE_FILE_EXPORTED + userPrefsFilePath);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportCommand // instanceof handles nulls
            && this.userPrefsFilePath.equals(((ExportCommand) other).userPrefsFilePath)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.optionparser.CommandOptionUtil.PREFIX_OPTION_INDICATOR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.NoPersonFoundException;

/**
 * Format full help instructions for every command for display.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String PREFIX_SORT_BY_NAME = PREFIX_OPTION_INDICATOR + "n";
    public static final String PREFIX_SORT_BY_PHONE = PREFIX_OPTION_INDICATOR + "p";
    public static final String PREFIX_SORT_BY_EMAIL = PREFIX_OPTION_INDICATOR + "e";
    public static final String PREFIX_SORT_BY_ADDRESS = PREFIX_OPTION_INDICATOR + "a";
    public static final String PREFIX_SORT_BY_TAG = PREFIX_OPTION_INDICATOR + "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons."
            + "Parameters: [OPTION] (must be one of the available field and a String)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_NAME;

    public static final String MESSAGE_SUCCESS_BY_NAME = "Persons sorted by name.";
    public static final String MESSAGE_SUCCESS_BY_PHONE = "Persons sorted by phone.";
    public static final String MESSAGE_SUCCESS_BY_EMAIL = "Persons sorted by email.";
    public static final String MESSAGE_SUCCESS_BY_ADDRESS = "Persons sorted by address.";
    public static final String MESSAGE_SUCCESS_BY_TAG = "Persons sorted by tag.";
    public static final String NO_PERSON_FOUND = "Empty list.";

    private final String option;

    public SortCommand(String option) {
        this.option = option;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.sortPerson(option);
        } catch (NoPersonFoundException npf) {
            throw new CommandException(NO_PERSON_FOUND);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (option.contains("-n")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_NAME);
        } else if (option.contains("-p")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_PHONE);
        } else if (option.contains("-e")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_EMAIL);
        } else if (option.contains("-a")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_ADDRESS);
        } else if (option.contains("-t")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_TAG);
        } else {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.option.equals(((SortCommand) other).option)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
import seedu.address.logic.commands.DeleteAltCommand;
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
import seedu.address.logic.commands.ExportCommand;
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
import seedu.address.logic.commands.SortCommand;
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DeleteAltCommand.COMMAND_WORD:
            return new DeleteAltCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
        case ExportCommand.COMMAND_ALIAS:
            return new ExportCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\DeleteAltCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteAltCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAltCommand object
 */
public class DeleteAltCommandParser implements Parser<DeleteAltCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAltCommand
     * and returns an DeleteAltCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAltCommand parse(String args) throws ParseException {
        try {
            String name = ParserUtil.parseString(args);
            return new DeleteAltCommand(name);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAltCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\ExportCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        try {
            String filePath = ParserUtil.parseFilePath(args);
            return new ExportCommand(filePath);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    public static final String MESSAGE_INVALID_STRING = "String does not contain alphanum only.";
    public static final String MESSAGE_INVALID_OPTION = "String does not contain hyphen and lower case alphabet only.";
    public static final String MESSAGE_INVALID_FILE_PATH = "String does not contain \".xml\" as suffix.";
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a String of letters into a {@code trimmedString} and returns it. Leading and trailing whitespaces
     * will be trimmed.
     * @throws IllegalValueException if the specified string is invalid (not letters only).
     */
    public static String parseString(String str) throws IllegalValueException {
        String trimmedString = str.trim();
        if (!StringUtil.isAlnumOnly(trimmedString) || trimmedString.length() < 3) {
            throw new IllegalValueException(MESSAGE_INVALID_STRING);
        }
        return trimmedString;
    }

    /**
     * Parses a String of option into a {@code trimmedString} and returns it. Leading and trailing whitespaces
     * will be trimmed.
     * @throws IllegalValueException if the specified option is invalid (not in "-[lower case]" format).
     */
    public static String parseOption(String str) throws IllegalValueException {
        String trimmedString = str.trim();
        if (!StringUtil.isSortOption(trimmedString)) {
            throw new IllegalValueException(MESSAGE_INVALID_OPTION);
        }
        return trimmedString;
    }

    /**
     * Parses a String of file path into a {@code trimmedString} and returns it. Leading and trailing whitespaces
     * will be trimmed.
     * @throws IllegalValueException if the specified file path is invalid (not a xml file).
     */
    public static String parseFilePath(String filePath) throws IllegalValueException {
        String trimmedString = filePath.trim();
        if (!StringUtil.isFilePath(trimmedString)) {
            throw new IllegalValueException(MESSAGE_INVALID_FILE_PATH);
        }
        return trimmedString;
    }
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            String option = ParserUtil.parseOption(args);
            return new SortCommand(option);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
import seedu.address.model.person.exceptions.NoPersonFoundException;
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the list by the specified @param parameter.
     * @throws NoPersonFoundException if no persons found in this {@code AddressBook}.
     */
    public void sortPerson(String parameter) throws NoPersonFoundException {
        persons.sort(parameter);
    }
```
###### \java\seedu\address\model\Model.java
``` java
import seedu.address.model.person.exceptions.NoPersonFoundException;
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Sorts the list by the specified @param parameter.
     * @throws NoPersonFoundException if no persons found in {@code AddressBook}.
     */
    void sortPerson (String option) throws NoPersonFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
import seedu.address.model.person.exceptions.NoPersonFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortPerson(String option) throws NoPersonFoundException {
        requireNonNull(option);

        addressBook.sortPerson(option);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\exceptions\NoPersonFoundException.java
``` java
package seedu.address.model.person.exceptions;

/**
 * Signals that the operation found no person.
 */
public class NoPersonFoundException extends Exception {}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
import static seedu.address.logic.commands.SortCommand.PREFIX_SORT_BY_ADDRESS;
import static seedu.address.logic.commands.SortCommand.PREFIX_SORT_BY_EMAIL;
import static seedu.address.logic.commands.SortCommand.PREFIX_SORT_BY_NAME;
import static seedu.address.logic.commands.SortCommand.PREFIX_SORT_BY_PHONE;
import static seedu.address.logic.commands.SortCommand.PREFIX_SORT_BY_TAG;

import java.util.Collection;
import java.util.Collections;
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
import seedu.address.model.person.exceptions.NoPersonFoundException;
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts persons by specified parameter.
     *
     * @throws NoPersonFoundException if no persons found in the list
     */
    public void sort(String option) throws NoPersonFoundException {
        requireNonNull(option);

        if (internalList.size() < 1) {
            throw new NoPersonFoundException();
        }

        switch (option) {
        case PREFIX_SORT_BY_NAME: Collections.sort(internalList, (a, b) ->
                a.getName().toString().compareToIgnoreCase(b.getName().toString()));
        break;
        case PREFIX_SORT_BY_PHONE: Collections.sort(internalList, (a, b) ->
                a.getPhone().toString().compareToIgnoreCase(b.getPhone().toString()));
        break;
        case PREFIX_SORT_BY_EMAIL: Collections.sort(internalList, (a, b) ->
                a.getEmail().toString().compareToIgnoreCase(b.getEmail().toString()));
        break;
        case PREFIX_SORT_BY_ADDRESS: Collections.sort(internalList, (a, b) ->
                a.getAddress().toString().compareToIgnoreCase(b.getAddress().toString()));
        break;
        case PREFIX_SORT_BY_TAG: Collections.sort(internalList, (a, b) ->
                a.getTags().toString().compareToIgnoreCase(b.getTags().toString()));
        break;
        default: break;
        }
    }
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * @see #backupAddressBook(ReadOnlyAddressBook)
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.saveAddressBook(addressBook,
                addressBookStorage.getAddressBookFilePath().concat("backup.fxml"));
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, "backup.fxml");
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
import seedu.address.commons.events.ui.NewResultCheckEvent;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
            raise(new NewResultCheckEvent(commandResult.feedbackToUser, false));
```
###### \java\seedu\address\ui\CommandBox.java
``` java
            raise(new NewResultCheckEvent(e.getMessage(), true));
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getFilteredPersonList().size());
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static final String tagColor = "#5AC0FB";
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        addFavouriteStar(person);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
            tagLabel.setStyle("-fx-background-color: " + tagColor);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private void addFavouriteStar(ReadOnlyPerson person) {
        if (person.getFavourite()) {
            favourite.setId("favouriteStar");
        }
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
import javafx.collections.ObservableList;
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
import seedu.address.commons.events.ui.NewResultCheckEvent;
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    private static final String ERROR_STYLE_CLASS = "error";
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleNewResultCheckEvent(NewResultCheckEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));

        if (event.isError) {
            setStyleToIndicateCommandFailure();
        } else {
            setStyleDefault();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    @FXML
    private StatusBar totalPersons;


    public StatusBarFooter(String saveLocation, int totalPersons) {
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
        setTotalPersons(totalPersons);
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    private void setTotalPersons(int totalPersons) {
        Platform.runLater(() -> this.totalPersons.setText(totalPersons + " person(s) total"));
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
        setTotalPersons(abce.data.getPersonList().size());
```
###### \java\seedu\address\ui\UiManager.java
``` java
        alert.getDialogPane().getStylesheets().add("view/LightTheme.css");
```
###### \resources\view\Extensions.css
``` css
    -fx-background: transparent;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 20%);
    background-color: #f3e4c6; /* Used in the default.html file */
```
###### \resources\view\LightTheme.css
``` css
    -fx-base: #fff2d6;
    -fx-control-inner-background: #f3e4c6;
    -fx-background-color: #f3e4c6;
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 20%);
    -fx-border-color: transparent transparent transparent #fff0cc;
}

.split-pane:vertical .split-pane-divider {
    -fx-background-color: derive(#f3e4c6, 20%);
    -fx-border-color: transparent transparent transparent #fff0cc;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 20%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: #ffdece;
    -fx-background-radius: 5;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: #dee8e4;
    -fx-background-radius: 5;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: #f4b770;
    -fx-background-radius: 5;
```
###### \resources\view\LightTheme.css
``` css
    -fx-border-radius: 5;
    -fx-border-color: #8F4F06;
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
     -fx-background-color: derive(#f3e4c6, 20%);
```
###### \resources\view\LightTheme.css
``` css
     -fx-background-color: derive(#f3e4c6, 20%);
     -fx-border-color: derive(#f3e4c6, 10%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 20%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: white !important;
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 30%);
    -fx-border-color: derive(#f3e4c6, 25%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 30%);
    -fx-border-color: derive(#f3e4c6, 30%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 30%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 50%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 20%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: white;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: #f3e4c6;
```
###### \resources\view\LightTheme.css
``` css
  -fx-text-fill: #f3e4c6;
```
###### \resources\view\LightTheme.css
``` css
    -fx-border-color: black, black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: #f3e4c6;
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: #000000;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: #f3e4c6;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: #f3e4c6;
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#f3e4c6, 25%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#824424, 75%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: derive(#824424, 100%);
```
###### \resources\view\LightTheme.css
``` css
    -fx-padding: 1 6 1 6;
```
###### \resources\view\LightTheme.css
``` css
    -fx-padding: 6 1 6 1;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-radius: 7;
    -fx-border-color: #383838 #383838 #383838 #383838;
```
###### \resources\view\LightTheme.css
``` css
    -fx-border-radius: 7;
```
###### \resources\view\LightTheme.css
``` css
    -fx-text-fill: black;
```
###### \resources\view\LightTheme.css
``` css
    -fx-background-color: transparent, #f3e4c6, transparent, #f3e4c6;
    -fx-background-radius: 5;
```
###### \resources\view\LightTheme.css
``` css
#favouriteStar {
    -fx-image: url("../images/favStar.png");
}
```
###### \resources\view\MainWindow.fxml
``` fxml
        <URL value="@LightTheme.css" />
```
###### \resources\view\ResultDisplay.fxml
``` fxml
  <TextArea fx:id="resultDisplay" editable="false" style="-fx-background-color: #fff7e3;" stylesheets="@LightTheme.css" />
```
###### \resources\view\StatusBarFooter.fxml
``` fxml
  <StatusBar styleClass="anchor-pane" fx:id="saveLocationStatus" GridPane.columnIndex="2" nodeOrientation="LEFT_TO_RIGHT" />
  <StatusBar styleClass="anchor-pane" fx:id="totalPersons" GridPane.columnIndex="1" nodeOrientation="LEFT_TO_RIGHT" />
```
