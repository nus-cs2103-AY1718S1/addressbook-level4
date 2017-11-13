# qihao27
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
###### \java\seedu\address\logic\commands\DeleteByNameCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using its name from the address book.
 */
public class DeleteByNameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name.\n"
            + "Parameters: [NAME]\n"
            + "Example: " + COMMAND_WORD + " john";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_MULTIPLE_PERSON_FOUND = "Multiple contacts with specified name found!\n"
        + "Please add more details for distinction or use the following command:\n" + DeleteCommand.MESSAGE_USAGE;
    public static final String MESSAGE_PERSON_NAME_ABSENT = "The person's name provided can not be found.";

    private final Predicate<ReadOnlyPerson> predicate;

    public DeleteByNameCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<ReadOnlyPerson> predicateList = model.getFilteredPersonList();

        if (predicateList.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_PERSON_FOUND);
        } else if (predicateList.size() == 0) {
            throw new CommandException(MESSAGE_PERSON_NAME_ABSENT);
        } else {
            ReadOnlyPerson personToDelete = predicateList.get(0);

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                throw new CommandException(MESSAGE_PERSON_NAME_ABSENT);
            }

            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByNameCommand // instanceof handles nulls
                && this.predicate.equals(((DeleteByNameCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;

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
        + "Example: " + COMMAND_WORD + " docs/MyAcquaiNote.xml"
        + "Example: " + COMMAND_WORD + " D:\\MyAcquaiNote.xml";

    public static final String MESSAGE_FILE_EXPORTED = "Addressbook exported : ";
    public static final String MESSAGE_STORAGE_ERROR = "Error exporting address book.";

    private final String userPrefsFilePath;

    public ExportCommand(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);

        ReadOnlyAddressBook localAddressBook = model.getAddressBook();

        File file = new File(userPrefsFilePath);

        try {
            FileUtil.createIfMissing(file);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_STORAGE_ERROR);
        }

        try {
            XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(localAddressBook));
        } catch (FileNotFoundException e) {
            return new CommandResult(MESSAGE_STORAGE_ERROR);
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons.\n"
            + "Parameters: [OPTION] (must be one of the available field and a String)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_NAME + " (sort by name)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_PHONE + " (sort by phone number)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_EMAIL + " (sort by email address)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_ADDRESS + " (sort by address)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_TAG + " (sort by tag)\n";

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
        }

        return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
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
        case DeleteByNameCommand.COMMAND_WORD:
            return new DeleteByNameCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
        case ExportCommand.COMMAND_ALIAS:
            return new ExportCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\DeleteByNameCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DeleteByNameCommand object
 */
public class DeleteByNameCommandParser implements Parser<DeleteByNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByNameCommand
     * and returns an DeleteByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByNameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new DeleteByNameCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
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
    public static final String MESSAGE_INVALID_OPTION = "String does not contain hyphen and lower case alphabet only.";
    public static final String MESSAGE_INVALID_FILE_PATH =
            "String does not contain \".xml\" as suffix or contains invalid file path.";
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
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
    /**
     * Sorts the list by the specified @param parameter.
     * @throws NoPersonFoundException if no persons found in this {@code AddressBook}.
     */
    public void sortPerson(String parameter) throws NoPersonFoundException {
        persons.sort(parameter);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<TodoItem> getTodoList() {
        return todos.asObservableList();
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
                Collections.sort(internalList, (a, b) ->
                    String.valueOf(b.getFavourite()).compareToIgnoreCase(String.valueOf(a.getFavourite())));
        break;
        case PREFIX_SORT_BY_PHONE: Collections.sort(internalList, (a, b) ->
                a.getPhone().toString().compareToIgnoreCase(b.getPhone().toString()));
                Collections.sort(internalList, (a, b) ->
                    String.valueOf(b.getFavourite()).compareToIgnoreCase(String.valueOf(a.getFavourite())));
        break;
        case PREFIX_SORT_BY_EMAIL: Collections.sort(internalList, (a, b) ->
                a.getEmail().toString().compareToIgnoreCase(b.getEmail().toString()));
                Collections.sort(internalList, (a, b) ->
                    String.valueOf(b.getFavourite()).compareToIgnoreCase(String.valueOf(a.getFavourite())));
        break;
        case PREFIX_SORT_BY_ADDRESS: Collections.sort(internalList, (a, b) ->
                a.getAddress().toString().compareToIgnoreCase(b.getAddress().toString()));
                Collections.sort(internalList, (a, b) ->
                    String.valueOf(b.getFavourite()).compareToIgnoreCase(String.valueOf(a.getFavourite())));
        break;
        case PREFIX_SORT_BY_TAG: Collections.sort(internalList, (a, b) ->
                a.getTags().toString().compareToIgnoreCase(b.getTags().toString()));
                Collections.sort(internalList, (a, b) ->
                    String.valueOf(b.getFavourite()).compareToIgnoreCase(String.valueOf(a.getFavourite())));
        break;
        default: break;
        }
    }
```
###### \java\seedu\address\model\person\UniqueTodoList.java
``` java
package seedu.address.model.person;

import java.util.HashSet;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of TodoItems.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see TodoItem#equals(Object)
 */
public class UniqueTodoList implements Iterable<TodoItem> {

    private final ObservableList<TodoItem> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TodoList.
     */
    public UniqueTodoList() {}

    @Override
    public Iterator<TodoItem> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TodoItem> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueTodoList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTodoList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueTodoList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the todo list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<TodoItem> getTodoList();
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
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        todoItems = new ArrayList<>();
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        todoItems.addAll(src.getTodoList().stream().map(XmlAdapterTodoItem::new).collect(Collectors.toList()));
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<TodoItem> getTodoList() {
        final ObservableList<TodoItem> todoItems = this.todoItems.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(todoItems);
    }
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
                Integer.toString(logic.getFilteredPersonList().size()));
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        todoButton = new Button();
        browserButton = new Button();
        todoButton.setOnAction(event -> handleTodoButton());
        browserButton.setOnAction(event -> handleBrowserButton());
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Toggle to todolist view.
     */
    @FXML
    private void handleTodoButton() {
        todoButton = new Button();
        switchPlaceholderDisplay(1);
    }

    /**
     * Toggle to browser view.
     */
    @FXML
    private void handleBrowserButton() {
        browserButton = new Button();
        switchPlaceholderDisplay(2);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static final String tagColor = "#dc143c";
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private ImageView favouriteIcon;
    @FXML
    private ImageView todo;
    @FXML
    private Label totalTodo;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        addFavouriteStar(person);
        addTodoCount(person);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initialise the favourited contacts with star
     */
    private void addFavouriteStar(ReadOnlyPerson person) {
        if (person.getFavourite()) {
            favouriteIcon.setId("favouriteStar");
        }
    }

    /**
     * Initialise contacts with todolist(s) count
     */
    private void addTodoCount(ReadOnlyPerson person) {
        if (person.getTodoItems().size() > 0) {
            totalTodo.setText(Integer.toString(person.getTodoItems().size()));
            todo.setId("todoBackground");
        } else {
            totalTodo.setText("");
        }
    }

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
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    private void setTotalPersons(String totalPersons) {
        Platform.runLater(() -> this.totalPersons.setText(totalPersons + " person(s) total"));
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
        setTotalPersons(Integer.toString(abce.data.getPersonList().size()));
```
###### \java\seedu\address\ui\UiManager.java
``` java
        alert.getDialogPane().getStylesheets().add("view/LightTheme.css");
```
###### \resources\view\LightTheme.css
``` css
/* modified base on DarkTheme.css */
.background {
    -fx-background-color: derive(#f3e4c6, 20%);
    background-color: #f3e4c6; /* Used in the default.html file */
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
    -fx-base: #fff2d6;
    -fx-control-inner-background: #f3e4c6;
    -fx-background-color: #f3e4c6;
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
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#f3e4c6, 20%);
    -fx-border-color: transparent transparent transparent #fff0cc;
}

.split-pane:vertical .split-pane-divider {
    -fx-background-color: derive(#f3e4c6, 20%);
    -fx-border-color: transparent transparent transparent #fff0cc;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#f3e4c6, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-border-color: derive(#824424, 100%);
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #ffdece;
    -fx-background-radius: 5;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);
}

.list-cell:filled:odd {
    -fx-background-color: #dee8e4;
    -fx-background-radius: 5;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);
}

.list-cell:filled:selected {
    -fx-background-color: #f4b770;
    -fx-background-radius: 5;
}

.list-cell:filled:selected #cardPane {
    -fx-border-radius: 5;
    -fx-border-color: #8F4F06;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
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
     -fx-background-color: derive(#f3e4c6, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#f3e4c6, 20%);
     -fx-border-color: transparent;
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#f3e4c6, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: white !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#f3e4c6, 30%);
    -fx-border-color: derive(#f3e4c6, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#f3e4c6, 30%);
    -fx-border-color: derive(#f3e4c6, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#f3e4c6, 30%);
}

.context-menu {
    -fx-background-color: derive(#f3e4c6, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#f3e4c6, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: white;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 0 0 0 0;
    -fx-border-color: derive(#824424, 70%);
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: derive(#824424, 100%);
    -fx-font-weight: bold;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 12pt;
    -fx-text-fill: black;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
    -fx-border-color: transparent;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: derive(-fx-focus-color, 30%);
  -fx-text-fill: black;
}

.button:focused {
    -fx-background-color: derive(#824424, 70%);
    -fx-border-color: derive(#824424, 70%);
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #f3e4c6;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #000000;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #f3e4c6;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #f3e4c6;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#f3e4c6, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(#824424, 75%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#824424, 100%);
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
    -fx-padding: 1 6 1 6;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 6 1 6 1;
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
    -fx-background-radius: 7;
    -fx-border-color: derive(#824424, 100%);
    -fx-border-insets: 0;
    -fx-border-radius: 7;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: #f3e4c6;
    -fx-background-radius: 5;
    -fx-border-color: derive(#824424, 100%);
    -fx-border-radius: 5;
    -fx-border-width: 1;
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

#favouriteStar {
    -fx-image: url("../images/favStar.png");
}

#todoBackground {
    -fx-image: url("../images/todo.png");
}

.list-cell:filled .split-pane:vertical .split-pane-divider {
    -fx-background-color: transparent;
    -fx-border-color: transparent transparent transparent transparent;
}

.list-cell .split-pane {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-background-color: transparent;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
        <URL value="@LightTheme.css" />
```
###### \resources\view\PersonListCard.fxml
``` fxml
  <SplitPane dividerPositions="0.5" orientation="VERTICAL" prefHeight="50.0" prefWidth="50.0" style="-fx-background-color: transparent;">
    <items>
      <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="50.0" prefWidth="50.0" stylesheets="@LightTheme.css">
        <children>
          <Label fx:id="favourite" alignment="TOP_RIGHT" text="" />
          <ImageView fx:id="favouriteIcon" fitHeight="30" fitWidth="30" layoutY="10.0" preserveRatio="true" />
        </children>
      </AnchorPane>
      <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="50.0" prefWidth="158.0">
        <children>
          <Label fx:id="totalTodo" alignment="BOTTOM_RIGHT" text="\$totalTodo">
            <graphic>
              <ImageView fx:id="todo" fitHeight="30" fitWidth="30" preserveRatio="true" />
            </graphic>
              <font>
                <Font name="System Bold" size="20.0" />
              </font>
          </Label>
        </children>
      </AnchorPane>
    </items>
  </SplitPane>
</HBox>
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
