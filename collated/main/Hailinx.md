# Hailinx
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String MESSAGE_IS_ENCRYPTD = "The address book is encrypted. "
            + "Please unlock it first!";
```
###### \java\seedu\address\commons\events\model\ReloadAddressBookEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the request to reload Address Book.
 */
public class ReloadAddressBookEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Request to reload Address Book.";
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeSearchEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for changing search mode for browser
 */
public class ChangeSearchEvent extends BaseEvent {

    public final int searchMode;

    public ChangeSearchEvent(int searchMode) {
        this.searchMode = searchMode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ClearListSelectionEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for clearing list selection
 */
public class ClearListSelectionEvent extends BaseEvent {

    public ClearListSelectionEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowAllTodoItemsEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for displaying all todoItems
 */
public class ShowAllTodoItemsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowPersonTodoEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates a request for displaying todoItems of given person
 */
public class ShowPersonTodoEvent extends BaseEvent {

    public final ReadOnlyPerson person;

    public ShowPersonTodoEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\SwitchDisplayEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for switching between todoList and browser
 */
public class SwitchDisplayEvent extends BaseEvent {

    public final int mode;

    public SwitchDisplayEvent(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\exceptions\EncryptOrDecryptException.java
``` java
package seedu.address.commons.exceptions;

/**
 * Signals an error caused by encryption and decryption.
 * The reason can be wrong keyword.
 */
public class EncryptOrDecryptException extends Exception {
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    /* Option prefix definitions */
    public static final String PREFIX_FIND_IN_DETAIL = PREFIX_OPTION_INDICATOR + "d";
    public static final String PREFIX_FIND_FUZZY_FIND = PREFIX_OPTION_INDICATOR + "u";
    public static final String PREFIX_FIND_BY_NAME = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are matched\n"
            + "Format: KEYWORD [OPTION] ARGUMENTS...\n"
            + "\tDefault: " + COMMAND_WORD + " ARGUMENT [ARGUMENTS]\n"
            + "\t\tFinds all persons whose names contain any of the specified keywords (case-sensitive)"
            + "\t\tand displays them as a list with index numbers.\n"
            + "\t\tExample: " + COMMAND_WORD + " alice bob charlie\n"
            + "\tOptions:\n"
            + "\t  " + PREFIX_FIND_IN_DETAIL + " [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAGS]\n"
            + "\t\tFinds all persons who match the given details\n"
            + "\t\tExample: " + COMMAND_WORD + " " + PREFIX_FIND_IN_DETAIL + " n/Bob p/999 t/friend t/classmate\n"
            + "\t  " + PREFIX_FIND_FUZZY_FIND + " ARGUMENT\n"
            + "\t\tFuzzy search for people whose details contain the argument\n"
            + "\t\tExample: " + COMMAND_WORD + " " + PREFIX_FIND_FUZZY_FIND + " @u.nus.edu";
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    /**
     * Stores the details to find the person with. Each non-empty field value will be used to compare
     * to contacts in the list.
     */
    public static class FindDetailDescriptor {
        private String name;
        private String phone;
        private String email;
        private String address;
        private Set<Tag> tags = new HashSet<>();

        public FindDetailDescriptor() {
        }

        /**
         * @return true if any attribute not null.
         */
        public boolean isValidDescriptor() {
            return getName().isPresent()
                    || getPhone().isPresent()
                    || getEmail().isPresent()
                    || getAddress().isPresent()
                    || !tags.isEmpty();
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Optional<String> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Optional<String> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindDetailDescriptor)) {
                return false;
            }

            // state check
            FindDetailDescriptor e = (FindDetailDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }

        @Override
        public String toString() {
            return "FindDetailDescriptor{"
                    + "name='" + name + '\''
                    + ", phone='" + phone + '\''
                    + ", email='" + email + '\''
                    + ", address='" + address + '\''
                    + ", tags=" + tags
                    + '}';
        }
    }
```
###### \java\seedu\address\logic\commands\LockCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.events.model.ReloadAddressBookEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.security.Security;
import seedu.address.security.SecurityManager;

/**
 * Locks the address book.
 */
public class LockCommand extends Command {

    public static final String COMMAND_WORD = "lock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts all contact with a input password."
            + "Parameters: "
            + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " mykey";

    public static final String MESSAGE_SUCCESS = "Address book is locked successfully.";
    public static final String MESSAGE_DUPLICATED_LOCK = "Address book is locked already. Cannot lock again.";
    public static final String MESSAGE_EMPTY_ADDRESSBOOK = "Address book is empty. Nothing to encrypt.";
    public static final String MESSAGE_ERROR_STORAGE_ERROR = "Meets errors during locking address book.";
    public static final String MESSAGE_ERROR_LOCK_PASSWORD = "Cannot lock address book using current password.";

    private final String password;

    public LockCommand(String password) {
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        if (isAddressBookEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_ADDRESSBOOK);
        }

        Security security = SecurityManager.getInstance();
        try {
            if (security.isEncrypted()) {
                return new CommandResult(MESSAGE_DUPLICATED_LOCK);
            }

            security.encryptAddressBook(password);
            security.raise(new ReloadAddressBookEvent());
            return new CommandResult(MESSAGE_SUCCESS);

        } catch (IOException e) {
            security.raise(new DataSavingExceptionEvent(e));
            return new CommandResult(MESSAGE_ERROR_STORAGE_ERROR);
        } catch (EncryptOrDecryptException e) {
            return new CommandResult(MESSAGE_ERROR_LOCK_PASSWORD);
        }
    }

    /**
     * @return true if address book is empty.
     */
    private boolean isAddressBookEmpty() {
        return model.getAddressBook().getPersonList().isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LockCommand // instanceof handles nulls
                && this.password.equals(((LockCommand) other).password)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java
    /* Option prefix definitions */
    public static final String PREFIX_SELECT_SEARCH_NAME = PREFIX_OPTION_INDICATOR + "n";
    public static final String PREFIX_SELECT_SEARCH_PHONE = PREFIX_OPTION_INDICATOR + "p";
    public static final String PREFIX_SELECT_SEARCH_EMAIL = PREFIX_OPTION_INDICATOR + "e";
    public static final String PREFIX_SELECT_SEARCH_ADDRESS = PREFIX_OPTION_INDICATOR + "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Format: KEYWORD [OPTION] INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SELECT_SEARCH_NAME + " 1\n"
            + "Options:\n"
            + "\t" + PREFIX_SELECT_SEARCH_NAME + "\t\tsearch name on browser\n"
            + "\t" + PREFIX_SELECT_SEARCH_PHONE + "\t\tsearch phone on browser\n"
            + "\t" + PREFIX_SELECT_SEARCH_EMAIL + "\t\tsearch email on browser\n"
            + "\t" + PREFIX_SELECT_SEARCH_ADDRESS + "\t\tshow address on google map";
```
###### \java\seedu\address\logic\commands\SwitchCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchDisplayEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Switches between TodoList and browser
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final int SWITCH_TO_TODOLIST = 1;
    public static final int SWITCH_TO_BROWSER = 2;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switch between Todo list and browser.\n"
            + "Parameters: NUMBER (1 for Todo list, 2 for browser)\n"
            + "Example: " + COMMAND_WORD + " " + SWITCH_TO_TODOLIST;

    public static final String MESSAGE_SUCCESS = "Switch to %1$s";

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

    private final int mode;

    /**
     * Creates an SwitchCommand to switch according to the argument
     */
    public SwitchCommand(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        try {
            mode = Integer.parseInt(trimmedArgs);
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new SwitchDisplayEvent(mode));
        switch (mode) {
        case SWITCH_TO_TODOLIST:
            return new CommandResult(String.format(MESSAGE_SUCCESS, "Todo list"));
        case SWITCH_TO_BROWSER:
            return new CommandResult(String.format(MESSAGE_SUCCESS, "browser"));
        default:
            throw new CommandException(PARSE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand // instanceof handles nulls
                && this.mode == ((SwitchCommand) other).mode); // state check
    }
}
```
###### \java\seedu\address\logic\commands\TodoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.optionparser.CommandOptionUtil.PREFIX_OPTION_INDICATOR;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ClearListSelectionEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowAllTodoItemsEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicateTodoItemException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.util.TimeConvertUtil;

/**
 * Manipulates the todoItems of the given person
 */
public class TodoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "todo";

    /* Option prefix definitions */
    public static final String PREFIX_TODO_ADD = PREFIX_OPTION_INDICATOR + "a";
    public static final String PREFIX_TODO_DELETE_ONE = PREFIX_OPTION_INDICATOR + "d";
    public static final String PREFIX_TODO_DELETE_ALL = PREFIX_OPTION_INDICATOR + "c";
    public static final String PREFIX_TODO_LIST = PREFIX_OPTION_INDICATOR + "l";
    public static final String PREFIX_TODO_LIST_ALL = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": manipulates the todoItems of the given person\n"
            + "Option 1: " + "Add a new todo item to the given person with INDEX\n"
            + "\t" + COMMAND_WORD + " INDEX "
            + PREFIX_TODO_ADD + " "
            + PREFIX_START_TIME + TimeConvertUtil.TIME_PATTERN + " "
            + "[" + PREFIX_END_TIME + TimeConvertUtil.TIME_PATTERN + "] "
            + PREFIX_TASK + "TASK_TO_DO\n"
            + "\tExample: " + COMMAND_WORD + " 1 " + PREFIX_TODO_ADD + " "
            + PREFIX_START_TIME + "01-11-2017 20:40 "
            + PREFIX_TASK + "Meeting\n"

            + "Option 2: " + "Delete a todo item with INDEX2 from the given person with INDEX1\n"
            + "\t" + COMMAND_WORD + " INDEX1 "
            + PREFIX_TODO_DELETE_ONE + " "
            + " INDEX2\n"
            + "\tExample: " + COMMAND_WORD + " 1 " + PREFIX_TODO_DELETE_ONE + " 1\n"

            + "Option 3: " + "Delete all todo items from the given person with INDEX\n"
            + "\t" + COMMAND_WORD + " INDEX "
            + PREFIX_TODO_DELETE_ALL + "\n"

            + "Option 4: " + "List all todo items from the given person with INDEX\n"
            + "\t" + COMMAND_WORD + " INDEX "
            + PREFIX_TODO_LIST + "\n"

            + "Option 5: " + "List all todo items from all person\n"
            + "\t" + COMMAND_WORD
            + PREFIX_TODO_LIST_ALL + "\n";

    public static final String MESSAGE_SUCCESS_ADD = "New task added: %1$s";
    public static final String MESSAGE_SUCCESS_DELETE_ONE = "Deleted todo item: %1$s ";
    public static final String MESSAGE_SUCCESS_DELETE_ALL = "Deleted all todo items of No.%1$s person ";
    public static final String MESSAGE_SUCCESS_LIST = "Listed all todo items of No.%1$s person";
    public static final String MESSAGE_SUCCESS_LIST_ALL = "Listed all todo items";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TODOITEM = "This todo item already exists in the address book.";
    public static final String MESSAGE_ERROR_PREFIX = "Undefined prefix";
    public static final String MESSAGE_INVALID_TODOITEM_INDEX = "The todo item index provided is invalid";

    private String optionPrefix;
    private Index personIndex;
    private TodoItem todoItem;
    private Index itemIndex;

    public TodoCommand(String optionPrefix, Index personIndex, TodoItem todoItem, Index itemIndex) {
        this.optionPrefix = optionPrefix;
        this.personIndex = personIndex;
        this.todoItem = todoItem;
        this.itemIndex = itemIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            switch (optionPrefix) {

            case PREFIX_TODO_ADD:
                executeTodoAdd();
                EventsCenter.getInstance().post(new JumpToListRequestEvent(personIndex));
                return new CommandResult(String.format(MESSAGE_SUCCESS_ADD, todoItem));

            case PREFIX_TODO_DELETE_ONE:
                executeTodoDeleteOne();
                EventsCenter.getInstance().post(new JumpToListRequestEvent(personIndex));
                return new CommandResult(String.format(MESSAGE_SUCCESS_DELETE_ONE, todoItem));

            case PREFIX_TODO_DELETE_ALL:
                executeTodoDeleteAll();
                EventsCenter.getInstance().post(new JumpToListRequestEvent(personIndex));
                return new CommandResult(String.format(MESSAGE_SUCCESS_DELETE_ALL, personIndex.getOneBased()));

            case PREFIX_TODO_LIST:
                executeTodoList();
                EventsCenter.getInstance().post(new JumpToListRequestEvent(personIndex));
                return new CommandResult(String.format(MESSAGE_SUCCESS_LIST, personIndex.getOneBased()));

            case PREFIX_TODO_LIST_ALL:
                EventsCenter.getInstance().post(new ClearListSelectionEvent());
                EventsCenter.getInstance().post(new ShowAllTodoItemsEvent());
                return new CommandResult(MESSAGE_SUCCESS_LIST_ALL);

            default:
                throw new CommandException(MESSAGE_ERROR_PREFIX);
            }
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DuplicateTodoItemException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TODOITEM);
        }
    }

    /**
     * Executes option: adds a new todoItem.
     */
    private void executeTodoAdd() throws CommandException,
            PersonNotFoundException, DuplicatePersonException, DuplicateTodoItemException {
        ReadOnlyPerson person = getReadOnlyPersonFromIndex();
        model.addTodoItem(person, todoItem);
    }

    /**
     * Executes option: delete a todoItem.
     */
    private void executeTodoDeleteOne() throws CommandException,
            PersonNotFoundException, DuplicatePersonException {
        ReadOnlyPerson person = getReadOnlyPersonFromIndex();
        if (itemIndex.getZeroBased() >= person.getTodoItems().size()) {
            throw new CommandException(MESSAGE_INVALID_TODOITEM_INDEX);
        }
        todoItem = person.getTodoItems().get(itemIndex.getZeroBased());
        model.deleteTodoItem(person, todoItem);
    }

    /**
     * Executes option: delete all todoItem.
     */
    private void executeTodoDeleteAll() throws CommandException,
            PersonNotFoundException, DuplicatePersonException {
        ReadOnlyPerson person = getReadOnlyPersonFromIndex();
        model.resetTodoItem(person);
    }

    /**
     * Executes option: list all todoItem.
     */
    private void executeTodoList() throws CommandException {
        getReadOnlyPersonFromIndex();
    }

    /**
     * @return a {@code ReadOnlyPerson} from {@code Index}
     */
    private ReadOnlyPerson getReadOnlyPersonFromIndex() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(personIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoCommand // instanceof handles nulls
                && optionPrefix.equals(((TodoCommand) other).optionPrefix)
                && (personIndex == null || personIndex.equals(((TodoCommand) other).personIndex))
                && (todoItem == null || todoItem.equals(((TodoCommand) other).todoItem))
                && (itemIndex == null || itemIndex.equals(((TodoCommand) other).itemIndex))
            );
    }
}
```
###### \java\seedu\address\logic\commands\UnlockCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.events.model.ReloadAddressBookEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.security.Security;
import seedu.address.security.SecurityManager;

/**
 * Unlocks the address book.
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts all contact with a input password."
            + "Parameters: "
            + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " mykey";

    public static final String MESSAGE_SUCCESS = "Address book is unlocked successfully.";
    public static final String MESSAGE_DUPLICATED_UNLOCK = "Address book is unlocked already. Cannot unlock again.";
    public static final String MESSAGE_ERROR_STORAGE_ERROR = "Meets errors during unlocking address book.";
    public static final String MESSAGE_ERROR_LOCK_PASSWORD = "Cannot unlock address book using current password.";

    private final String password;

    public UnlockCommand(String password) {
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        Security security = SecurityManager.getInstance();
        try {
            if (!security.isEncrypted()) {
                return new CommandResult(MESSAGE_DUPLICATED_UNLOCK);
            }

            security.decryptAddressBook(password);
            security.raise(new ReloadAddressBookEvent());
            return new CommandResult(MESSAGE_SUCCESS);

        } catch (IOException e) {
            security.raise(new DataSavingExceptionEvent(e));
            return new CommandResult(MESSAGE_ERROR_STORAGE_ERROR);
        } catch (EncryptOrDecryptException e) {
            return new CommandResult(MESSAGE_ERROR_LOCK_PASSWORD);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnlockCommand // instanceof handles nulls
                && this.password.equals(((UnlockCommand) other).password)); // state check
    }

}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    // prefix for TodoCommand
    public static final Prefix PREFIX_START_TIME = new Prefix("f/");
    public static final Prefix PREFIX_END_TIME = new Prefix("t/");
    public static final Prefix PREFIX_TASK = new Prefix("d/");
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.optionparser.CommandOptionUtil;
import seedu.address.logic.parser.optionparser.FindOptionByName;
import seedu.address.logic.parser.optionparser.FindOptionFuzzy;
import seedu.address.logic.parser.optionparser.FindOptionInDetail;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code args} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        String optionPrefix = CommandOptionUtil.getOptionPrefix(trimmedArgs);
        String optionArgs = CommandOptionUtil.getOptionArgs(optionPrefix, trimmedArgs);

        // returns FindCommand by parsing {@code optionPrefix}
        switch (optionPrefix) {
        case FindCommand.PREFIX_FIND_IN_DETAIL:
            return new FindOptionInDetail(optionArgs).parse();
        case FindCommand.PREFIX_FIND_FUZZY_FIND:
            return new FindOptionFuzzy(optionArgs).parse();
        case FindCommand.PREFIX_FIND_BY_NAME:
            return new FindOptionByName(optionArgs).parse();
        default:
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }

}
```
###### \java\seedu\address\logic\parser\LockCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityUtil;

/**
 * Parses input arguments and creates a new LockCommand object
 */
public class LockCommandParser implements Parser<LockCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code args} of arguments in the context of the LockCommand
     * and returns an LockCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LockCommand parse(String userInput) throws ParseException {
        if (!SecurityUtil.isValidPassword(userInput)) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new LockCommand(userInput.trim());
    }

}
```
###### \java\seedu\address\logic\parser\optionparser\CommandOption.java
``` java
package seedu.address.logic.parser.optionparser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a command mode of {@code T} command.
 */
public abstract class CommandOption<T extends Command> {

    protected String optionArgs;

    CommandOption(String optionArgs) {
        this.optionArgs = optionArgs.trim();
    }

    /**
     * Parses {@code optionArgs} into a command and returns it.
     * @throws ParseException if {@code optionArgs} does not conform the expected format.
     */
    public abstract T parse() throws ParseException;

    /**
     * Checks whether {@code optionArgs} is valid.
     * @return true if {@code optionArgs} is not empty by default.
     */
    public boolean isValidOptionArgs() {
        return !optionArgs.isEmpty();
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\CommandOptionUtil.java
``` java
package seedu.address.logic.parser.optionparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains utility methods used for parsing mode prefix and mode arguments.
 */
public class CommandOptionUtil {

    // No mode means that prefix is empty
    public static final String DEFAULT_OPTION_PREFIX = "";
    // All modes start with prefix "-"
    public static final String PREFIX_OPTION_INDICATOR = "-";

    private static final Pattern OPTION_PATTERN = Pattern.compile("^(-\\w+)");

    private static final int DEFAULT_PATTERN_GROUP = 0;

    public static String getOptionPrefix(String args) {
        try {
            Matcher matcher = OPTION_PATTERN.matcher(args);
            if (matcher.find()) {
                return matcher.group(DEFAULT_PATTERN_GROUP);
            }
        } catch (Exception e) {
            return DEFAULT_OPTION_PREFIX;
        }
        return DEFAULT_OPTION_PREFIX;
    }

    public static String getOptionArgs(String optionPrefix, String args) {
        return args.replace(optionPrefix, "").trim();
    }

}
```
###### \java\seedu\address\logic\parser\optionparser\FindOptionByName.java
``` java
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;
import static seedu.address.logic.parser.optionparser.CommandOptionUtil.PREFIX_OPTION_INDICATOR;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds contacts by name.
 */
public class FindOptionByName extends CommandOption<FindCommand> {

    public FindOptionByName(String optionArgs) {
        super(optionArgs);
    }

    @Override
    public FindCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        String[] nameKeywords = optionArgs.split("\\s+");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    @Override
    public boolean isValidOptionArgs() {
        return !optionArgs.isEmpty() && !optionArgs.contains(PREFIX_OPTION_INDICATOR);
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\FindOptionFuzzy.java
``` java
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FuzzySearchPredicate;

/**
 * Finds contacts in fuzzy search.
 */
public class FindOptionFuzzy extends CommandOption<FindCommand> {

    public FindOptionFuzzy(String optionArgs) {
        super(optionArgs);
    }

    @Override
    public FindCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new FindCommand(new FuzzySearchPredicate(optionArgs));
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\FindOptionInDetail.java
``` java
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DetailsContainsPredicate;

/**
 * Finds contacts by adding details.
 */
public class FindOptionInDetail extends CommandOption<FindCommand> {

    public FindOptionInDetail(String optionArgs) {
        super(optionArgs);
    }

    @Override
    public FindCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                " " + optionArgs, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        FindCommand.FindDetailDescriptor descriptor = new FindCommand.FindDetailDescriptor();
        try {
            argMultimap.getValue(PREFIX_NAME).ifPresent(descriptor::setName);
            argMultimap.getValue(PREFIX_PHONE).ifPresent(descriptor::setPhone);
            argMultimap.getValue(PREFIX_EMAIL).ifPresent(descriptor::setEmail);
            argMultimap.getValue(PREFIX_ADDRESS).ifPresent(descriptor::setAddress);
            Optional.of(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG))).ifPresent(descriptor::setTags);

            if (!descriptor.isValidDescriptor()) {
                throw new ParseException(PARSE_EXCEPTION_MESSAGE);
            }

            return new FindCommand(new DetailsContainsPredicate(descriptor));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
```
###### \java\seedu\address\logic\parser\optionparser\TodoOptionAdd.java
``` java
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.TodoCommandParser.PARSE_EXCEPTION_MESSAGE;
import static seedu.address.model.util.TimeConvertUtil.convertStringToTime;

import java.time.LocalDateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TodoItem;

/**
 * Adds a new TodoItem.
 */
public class TodoOptionAdd extends CommandOption<TodoCommand> {

    private final Index index;

    public TodoOptionAdd(String optionArgs, Index index) {
        super(optionArgs);
        this.index = index;
    }

    @Override
    public TodoCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                " " + optionArgs, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_TASK);

        TodoItem todoItem;
        try {
            LocalDateTime startTime = convertStringToTime(argMultimap.getValue(PREFIX_START_TIME).get());

            LocalDateTime endTime = null;
            if (argMultimap.getValue(PREFIX_END_TIME).isPresent()) {
                endTime = convertStringToTime(argMultimap.getValue(PREFIX_END_TIME).get());
            }

            String task = argMultimap.getValue(PREFIX_TASK).get();

            todoItem = new TodoItem(startTime, endTime, task);
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new TodoCommand(TodoCommand.PREFIX_TODO_ADD, index, todoItem, null);
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\TodoOptionDeleteAll.java
``` java
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.TodoCommandParser.PARSE_EXCEPTION_MESSAGE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Deletes all TodoItem from given person.
 */
public class TodoOptionDeleteAll extends CommandOption<TodoCommand> {

    private final Index index;

    public TodoOptionDeleteAll(String optionArgs, Index index) {
        super(optionArgs);
        this.index = index;
    }

    @Override
    public TodoCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new TodoCommand(TodoCommand.PREFIX_TODO_DELETE_ALL, index, null, null);
    }

    /**
     * No option parameter for this command.
     */
    @Override
    public boolean isValidOptionArgs() {
        return optionArgs.isEmpty();
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\TodoOptionDeleteOne.java
``` java
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.TodoCommandParser.PARSE_EXCEPTION_MESSAGE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Deletes a TodoItem from given person.
 */
public class TodoOptionDeleteOne extends CommandOption<TodoCommand> {

    private static final int MATCH_INDEX_GROUP = 1;

    private final Index index;

    public TodoOptionDeleteOne(String optionArgs, Index index) {
        super(optionArgs);
        this.index = index;
    }

    @Override
    public TodoCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        Index todoItemIndex;
        try {
            Matcher matcher = Pattern.compile("^(\\d+)$").matcher(optionArgs);
            if (matcher.find()) {
                String oneBasedIndex = matcher.group(MATCH_INDEX_GROUP);
                todoItemIndex = ParserUtil.parseIndex(oneBasedIndex);
                return new TodoCommand(TodoCommand.PREFIX_TODO_DELETE_ONE, index, null, todoItemIndex);
            }
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        throw new ParseException(PARSE_EXCEPTION_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\TodoOptionList.java
``` java
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.TodoCommandParser.PARSE_EXCEPTION_MESSAGE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Lists all TodoItem from given person.
 */
public class TodoOptionList extends CommandOption<TodoCommand> {

    private final Index index;

    public TodoOptionList(String optionArgs, Index index) {
        super(optionArgs);
        this.index = index;
    }

    @Override
    public TodoCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new TodoCommand(TodoCommand.PREFIX_TODO_LIST, index, null, null);
    }

    /**
     * No option parameter for this command.
     */
    @Override
    public boolean isValidOptionArgs() {
        return optionArgs.isEmpty();
    }
}
```
###### \java\seedu\address\logic\parser\SelectCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_ADDRESS;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_EMAIL;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_NAME;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.optionparser.CommandOptionUtil;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        String optionPrefix = CommandOptionUtil.getOptionPrefix(trimmedArgs);
        String optionArgs = CommandOptionUtil.getOptionArgs(optionPrefix, trimmedArgs);

        int searchMode;
        switch (optionPrefix) {
        case SelectCommand.PREFIX_SELECT_SEARCH_NAME:
            searchMode = GOOGLE_SEARCH_NAME;
            break;
        case SelectCommand.PREFIX_SELECT_SEARCH_PHONE:
            searchMode = GOOGLE_SEARCH_PHONE;
            break;
        case SelectCommand.PREFIX_SELECT_SEARCH_EMAIL:
            searchMode = GOOGLE_SEARCH_EMAIL;
            break;
        case SelectCommand.PREFIX_SELECT_SEARCH_ADDRESS:
            searchMode = GOOGLE_SEARCH_ADDRESS;
            break;
        default:
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        try {
            Index index = ParserUtil.parseIndex(optionArgs);
            return new SelectCommand(index, searchMode);
        } catch (IllegalValueException ive) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }
}
```
###### \java\seedu\address\logic\parser\TodoCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.optionparser.CommandOptionUtil;
import seedu.address.logic.parser.optionparser.TodoOptionAdd;
import seedu.address.logic.parser.optionparser.TodoOptionDeleteAll;
import seedu.address.logic.parser.optionparser.TodoOptionDeleteOne;
import seedu.address.logic.parser.optionparser.TodoOptionList;

/**
 * Parses input arguments and creates a new TodoCommand object
 */
public class TodoCommandParser implements Parser<TodoCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE);

    private static final String REGEX = "^(\\d+)";
    private static final int FIRST_INDEX_GROUP = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the TodoCommand
     * and returns an TodoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new TodoCommand(TodoCommand.PREFIX_TODO_LIST_ALL, null, null, null);
        }

        String oneBasedIndex = parseIndexString(trimmedArgs);
        String option = parseStrAfterIndex(oneBasedIndex, trimmedArgs);

        String optionPrefix = CommandOptionUtil.getOptionPrefix(option);
        String optionArgs = CommandOptionUtil.getOptionArgs(optionPrefix, option);

        Index index;
        try {
            index = ParserUtil.parseIndex(oneBasedIndex);
        } catch (IllegalValueException e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        // returns TodoCommand by parsing {@code optionPrefix}
        switch (optionPrefix) {
        case TodoCommand.PREFIX_TODO_ADD:
            return new TodoOptionAdd(optionArgs, index).parse();
        case TodoCommand.PREFIX_TODO_DELETE_ONE:
            return new TodoOptionDeleteOne(optionArgs, index).parse();
        case TodoCommand.PREFIX_TODO_DELETE_ALL:
            return new TodoOptionDeleteAll(optionArgs, index).parse();
        case TodoCommand.PREFIX_TODO_LIST:
            return new TodoOptionList(optionArgs, index).parse();
        default:
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Parses the {@code Index} from argument string.
     */
    public static String parseIndexString(String trimmedArgs) throws ParseException {
        Matcher matcher = Pattern.compile(REGEX).matcher(trimmedArgs);
        try {
            if (matcher.find()) {
                return matcher.group(FIRST_INDEX_GROUP);
            }
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        throw new ParseException(PARSE_EXCEPTION_MESSAGE);
    }

    /**
     * Gets the rest of string after parsing {@code Index}
     * @see #parseIndexString(String)
     */
    public static String parseStrAfterIndex(String index, String args) {
        return args.substring(args.indexOf(index) + index.length()).trim();
    }
}
```
###### \java\seedu\address\logic\parser\UnlockCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityUtil;

/**
 * Parses input arguments and creates a new UnlockCommand object
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code args} of arguments in the context of the UnlockCommand
     * and returns an UnlockCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnlockCommand parse(String userInput) throws ParseException {
        if (!SecurityUtil.isValidPassword(userInput)) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new UnlockCommand(userInput.trim());
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    private void setUpSecurityManager(Storage storage) {
        Security securityManager = SecurityManager.getInstance(storage);
        securityManager.configSecurity(UnlockCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD);
    }
```
###### \java\seedu\address\MainApp.java
``` java
    /**
     * Restarts the app.
     */
    private void restart() {
        logger.info("============================ [ Restarting Address Book ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
            init();
            start(primaryStage);
        } catch (Exception e) {
            logger.severe("Failed to restart " + StringUtil.getDetails(e));
        }
    }

    @Subscribe
    public void handleReloadAddressBookEvent(ReloadAddressBookEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        restart();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a todoItem to target person.
     */
    public void addTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws PersonNotFoundException, DuplicatePersonException, DuplicateTodoItemException {
        requireNonNull(target);

        Person person = new Person(target);
        List<TodoItem> todoItemList = new ArrayList<>(target.getTodoItems());

        if (todoItemList.contains(todoItem)) {
            throw new DuplicateTodoItemException();
        }

        todoItemList.add(todoItem);
        Collections.sort(todoItemList);

        person.setTodoItems(todoItemList);

        persons.setPerson(target, person);
    }

    /**
     * Deletes the given todoItem from target person.
     */
    public void deleteTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws PersonNotFoundException, DuplicatePersonException {
        requireAllNonNull(target, todoItem);

        Person person = new Person(target);
        List<TodoItem> todoItemList = new ArrayList<>(target.getTodoItems());

        todoItemList.remove(todoItem);
        person.setTodoItems(todoItemList);

        persons.setPerson(target, person);
    }

    /**
     * Resets all todoItem for target person.
     */
    public void resetTodoItem(ReadOnlyPerson target)
            throws PersonNotFoundException, DuplicatePersonException {
        requireNonNull(target);
        Person person = new Person(target);
        person.setTodoItems(new ArrayList<>());

        persons.setPerson(target, person);
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given todoItem to target person */
    void addTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws DuplicatePersonException, PersonNotFoundException, DuplicateTodoItemException;

    /** Deletes the given todoItem from target person */
    void deleteTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Resets all todoItem for target person */
    void resetTodoItem(ReadOnlyPerson target)
            throws DuplicatePersonException, PersonNotFoundException;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Updates the UI to show all todoItems for all persons */
    void updateTodoItemList();

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void addTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws DuplicatePersonException, PersonNotFoundException, DuplicateTodoItemException {
        requireAllNonNull(target, todoItem);

        addressBook.addTodoItem(target, todoItem);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, todoItem);

        addressBook.deleteTodoItem(target, todoItem);
        indicateAddressBookChanged();
    }

    @Override
    public void resetTodoItem(ReadOnlyPerson target)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(target);

        addressBook.resetTodoItem(target);
        indicateAddressBookChanged();
    }

    @Override
    public void updateTodoItemList() {
        raise(new ShowAllTodoItemsEvent());
    }

```
###### \java\seedu\address\model\person\DetailsContainsPredicate.java
``` java
package seedu.address.model.person;

import java.util.Iterator;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.model.tag.Tag;

/**
 * Tests that every {@code ReadOnlyPerson}'s attribute matches corresponding keyword
 * in descriptor given except it is null.
 */
public class DetailsContainsPredicate implements Predicate<ReadOnlyPerson> {
    private FindDetailDescriptor descriptor;

    public DetailsContainsPredicate(FindDetailDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return !isNameNotMatchedIfPresent(person)
                && !isPhoneNotMatchedIfPresent(person)
                && !isEmailNotMatchedIfPresent(person)
                && !isAddressNotMatchedIfPresent(person)
                && !isTagNotMatchedIfPresent(person);
    }

    /**
     * @return true if name in {@code descriptor} present but not match name of {@code person}
     */
    private boolean isNameNotMatchedIfPresent(ReadOnlyPerson person) {
        String personName = person.getName().fullName.toLowerCase();
        return descriptor.getName().isPresent()
                && !personName.contains(descriptor.getName().get().toLowerCase());
    }

    /**
     * @return true if phone in {@code descriptor} present but not match phone of {@code person}
     */
    private boolean isPhoneNotMatchedIfPresent(ReadOnlyPerson person) {
        String personPhone = person.getPhone().value;
        return descriptor.getPhone().isPresent()
                && !personPhone.contains(descriptor.getPhone().get());
    }

    /**
     * @return true if email in {@code descriptor} present but not match email of {@code person}
     */
    private boolean isEmailNotMatchedIfPresent(ReadOnlyPerson person) {
        String personEmail = person.getEmail().value.toLowerCase();
        return descriptor.getEmail().isPresent()
                && !personEmail.contains(descriptor.getEmail().get().toLowerCase());
    }

    /**
     * @return true if address in {@code descriptor} present but not match address of {@code person}
     */
    private boolean isAddressNotMatchedIfPresent(ReadOnlyPerson person) {
        String personAddress = person.getAddress().value.toLowerCase();
        return descriptor.getAddress().isPresent()
                && !personAddress.contains(descriptor.getAddress().get().toLowerCase());
    }

    /**
     * @return true if tag in {@code descriptor} present but not match tag of {@code person}
     */
    private boolean isTagNotMatchedIfPresent(ReadOnlyPerson person) {
        if (!descriptor.getTags().isPresent()) {
            return false;
        }
        Iterator<Tag> descriptorIterator = descriptor.getTags().get().iterator();
        Iterator<Tag> personIterator = person.getTags().iterator();
        while (descriptorIterator.hasNext()) {
            String tagInDescriptor = descriptorIterator.next().tagName.toLowerCase();
            if (isPersonContainTag(tagInDescriptor, personIterator)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the given tag string not present in {@code personIterator}
     */
    private boolean isPersonContainTag(String tagInDescriptor, Iterator<Tag> personIterator) {
        boolean isContain = false;
        while (personIterator.hasNext()) {
            String tagInPerson = personIterator.next().tagName.toLowerCase();
            if (tagInPerson.contains(tagInDescriptor)) {
                isContain = true;
            }
        }
        return !isContain;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsContainsPredicate // instanceof handles nulls
                && this.descriptor.equals(((DetailsContainsPredicate) other).descriptor)); // state check
    }

}
```
###### \java\seedu\address\model\person\exceptions\DuplicateTodoItemException.java
``` java
package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate TodoItem objects.
 */
public class DuplicateTodoItemException extends DuplicateDataException {
    public DuplicateTodoItemException() {
        super("Operation would result in duplicate todoItem");
    }
}
```
###### \java\seedu\address\model\person\FuzzySearchPredicate.java
``` java
package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Conducts fuzzy test on whether a {@code ReadOnlyPerson}'s attribute matches any of the keywords given.
 */
public class FuzzySearchPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public FuzzySearchPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(person.getPhone())
                .append(person.getEmail())
                .append(person.getAddress());
        person.getTags().forEach(builder::append);
        String personToString = builder.toString();
        return personToString.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FuzzySearchPredicate // instanceof handles nulls
                && this.keyword.equals(((FuzzySearchPredicate) other).keyword)); // state check
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public List<TodoItem> getTodoItems() {
        return Collections.unmodifiableList(todoItems);
    }

    public void setTodoItems(List<TodoItem> newItems) {
        todoItems = newItems;
    }
```
###### \java\seedu\address\model\person\TodoItem.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.TimeConvertUtil.convertTimeToString;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a TodoItem in the address book.
 */
public class TodoItem implements Comparable<TodoItem> {

    public static final String MESSAGE_TODOITEM_CONSTRAINTS =
            "The end time should be later than start time. Task cannot be empty.";

    public final LocalDateTime start;
    public final LocalDateTime end;
    public final String task;

    /**
     * Validates given time and task.
     * @param start : the start time of the task, cannot be null
     * @param end : the end time of the task, can be null
     * @param task : task in string, cannot be null
     */
    public TodoItem(LocalDateTime start, LocalDateTime end, String task)
            throws IllegalValueException {
        requireNonNull(start, task);
        if (!isValidTodoItem(start, end, task.trim())) {
            throw new IllegalValueException(MESSAGE_TODOITEM_CONSTRAINTS);
        }
        this.start = start;
        this.end = end;
        this.task = task.trim();
    }

    /**
     * Checks whether the inputs are valid.
     */
    private boolean isValidTodoItem(LocalDateTime start, LocalDateTime end, String task) {
        return !(task.isEmpty() || end != null && end.compareTo(start) < 0);
    }

    /**
     * @return formatted time string
     */
    public String getTimeString() {
        String timeStr;
        String startTimeStr = convertTimeToString(start);
        if (end != null) {
            String endTime = convertTimeToString(end);
            timeStr = "From: " + startTimeStr + "   To: " + endTime;
        } else {
            timeStr = "From: " + startTimeStr;
        }
        return timeStr;
    }

    @Override
    public String toString() {
        return "From:" + convertTimeToString(start)
                + " To:" + convertTimeToString(end)
                + " Task:" + task;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoItem // instanceof handles nulls
                && this.toString().equals(other.toString())); // state check
    }

    @Override
    public int compareTo(TodoItem other) {
        return this.start.compareTo(other.start);
    }
}
```
###### \java\seedu\address\model\util\TimeConvertUtil.java
``` java
package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Contains utility methods for converting date and time.
 */
public class TimeConvertUtil {

    public static final String EMPTY_STRING = "";

    public static final String TIME_PATTERN = "dd-MM-yyyy HH:mm";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * Converts a {@code LocalDateTime} to string.
     * Returns a empty string if time is null.
     */
    public static String convertTimeToString(LocalDateTime time) throws DateTimeParseException {
        if (time == null) {
            return EMPTY_STRING;
        }
        return time.format(DATE_TIME_FORMATTER);
    }

    /**
     * Converts a {@code String} to {@code LocalDateTime}.
     * Returns null if timeStr is null.
     * @throws DateTimeParseException when timeStr does not match {@code DATE_TIME_FORMATTER}
     */
    public static LocalDateTime convertStringToTime(String timeStr) throws DateTimeParseException {
        if (timeStr == null || timeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(timeStr, DATE_TIME_FORMATTER);
    }
}
```
###### \java\seedu\address\security\Security.java
``` java
package seedu.address.security;

import seedu.address.commons.events.BaseEvent;
import seedu.address.storage.SecureStorage;

/**
 * API of the Security component
 */
public interface Security extends SecureStorage {

    /**
     * Sets up commands that are allowed to execute when the system is secured.
     * @param permittedCommands the commandWord of those commands.
     */
    void configSecurity(String... permittedCommands);

    /**
     * Checks whether the system is secured.
     */
    boolean isSecured();

    /**
     * Sends message to UI by {@param event}.
     */
    void raise(BaseEvent event);

    /**
     * Checks whether the given command has permission for execution.
     */
    boolean isPermittedCommand(String commandWord);

}
```
###### \java\seedu\address\security\SecurityManager.java
``` java
package seedu.address.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;
import seedu.address.storage.Storage;

/**
 * Manages the data security of AddressBook.
 */
public class SecurityManager extends ComponentManager implements Security {

    private static Security instance;

    private Storage storage;
    private List<String> permittedCommandList;

    private SecurityManager(Storage storage) {
        this.storage = storage;
        permittedCommandList = new ArrayList<>();
    }

    public static Security getInstance() {
        return instance;
    }

    public static Security getInstance(Storage storage) {
        if (instance == null) {
            instance = new SecurityManager(storage);
        }
        return instance;
    }

    /**
     * Is used for testing.
     */
    public static void setInstance(Security security) {
        instance = security;
    }

    @Override
    public void configSecurity(String... permittedCommands) {
        permittedCommandList.addAll(Arrays.asList(permittedCommands));
    }

    @Override
    public boolean isSecured() {
        try {
            return isEncrypted();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void raise(BaseEvent event) {
        super.raise(event);
    }

    @Override
    public boolean isPermittedCommand(String commandName) {
        return permittedCommandList.contains(commandName);
    }

    @Override
    public boolean isEncrypted() throws IOException {
        return storage.isEncrypted();
    }

    @Override
    public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        storage.encryptAddressBook(password);
    }

    @Override
    public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        storage.decryptAddressBook(password);
    }
}
```
###### \java\seedu\address\security\SecurityUtil.java
``` java
package seedu.address.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.commons.exceptions.EncryptOrDecryptException;

/**
 * Helper functions for AES encryption and decryption.
 */
public class SecurityUtil {

    public static final int MIN_KEY_LENGTH = 4;

    public static final String XML_STARTER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    private static final String TEMP_FILE_PATH = "temp.xml";

    public static boolean isValidPassword(String password) {
        return password != null && password.trim().length() >= MIN_KEY_LENGTH;
    }

    /**
     * Returns SecretKeySpec by given key string
     */
    private static SecretKeySpec getKeySpec(String inputKey) {
        byte[] keyByte;
        MessageDigest sha;
        try {
            keyByte = inputKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            keyByte = sha.digest(keyByte);
            keyByte = Arrays.copyOf(keyByte, 16);
            return new SecretKeySpec(keyByte, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Encrypts the file
     * @throws IOException if the file cannot be found.
     * @throws EncryptOrDecryptException if the file cannot be encrypted.
     */
    public static void encrypt(File file, String inputKey)
            throws IOException, EncryptOrDecryptException {
        try {
            SecretKeySpec keySpec = getKeySpec(inputKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputByte = new byte[(int) file.length()];
            inputStream.read(inputByte);
            inputStream.close();

            byte[] outputByte = cipher.doFinal(inputByte);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(outputByte);
            outputStream.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException
                | NoSuchPaddingException
                | BadPaddingException
                | InvalidKeyException e) {
            throw new EncryptOrDecryptException();
        }
    }

    /**
     * Decrypts the file
     * @throws IOException if the file cannot be found.
     * @throws EncryptOrDecryptException if the file cannot be decrypted.
     */
    public static void decrypt(File file, String inputKey)
            throws IOException, EncryptOrDecryptException {
        File tempFile = new File(file.getParent(), TEMP_FILE_PATH);

        try {
            SecretKeySpec keySpec = getKeySpec(inputKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputByte = new byte[(int) file.length()];
            inputStream.read(inputByte);
            inputStream.close();

            byte[] outputByte = cipher.doFinal(inputByte);

            // write to a temp file to attempt decryption
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            outputStream.write(outputByte);
            outputStream.close();

            if (isEncrypted(tempFile)) {
                throw new EncryptOrDecryptException();
            } else {
                outputStream = new FileOutputStream(file);
                outputStream.write(outputByte);
                outputStream.close();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException
                | NoSuchPaddingException
                | BadPaddingException
                | InvalidKeyException e) {
            throw new EncryptOrDecryptException();
        } finally {
            // delete the temp file
            tempFile.delete();
        }
    }

    /**
     * @return true if the file is encrypted.
     */
    public static boolean isEncrypted(File file) throws IOException {
        char[] charset = new char[XML_STARTER.length()];

        FileReader reader = new FileReader(file);
        reader.read(charset, 0, XML_STARTER.length());
        reader.close();

        return !new String(charset).equals(XML_STARTER);
    }

}
```
###### \java\seedu\address\storage\SecureStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;

import seedu.address.commons.exceptions.EncryptOrDecryptException;

/**
 * Represents a secure storage which security methods.
 */
public interface SecureStorage {

    /**
     * @see #isEncrypted()
     */
    boolean isEncrypted() throws IOException;

    /**
     * @see #encryptAddressBook(String)
     */
    void encryptAddressBook(String password)
            throws IOException, EncryptOrDecryptException;

    /**
     * @see #decryptAddressBook(String)
     */
    void decryptAddressBook(String password)
            throws IOException, EncryptOrDecryptException;
}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public boolean isEncrypted() throws IOException {
        return addressBookStorage.isEncrypted();
    }

    @Override
    public void encryptAddressBook(String password)
            throws IOException, EncryptOrDecryptException {
        addressBookStorage.encryptAddressBook(password);
    }

    @Override
    public void decryptAddressBook(String password)
            throws IOException, EncryptOrDecryptException {
        addressBookStorage.decryptAddressBook(password);
    }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement
    private List<XmlAdapterTodoItem> xmlTodoItems = new ArrayList<>();
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        for (TodoItem todoItem : source.getTodoItems()) {
            xmlTodoItems.add(new XmlAdapterTodoItem(todoItem));
        }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final List<TodoItem> todoItems = new ArrayList<>();
        for (XmlAdapterTodoItem todoItem : xmlTodoItems) {
            todoItems.add(todoItem.toModelType());
        }
```
###### \java\seedu\address\storage\XmlAdapterTodoItem.java
``` java
package seedu.address.storage;

import static seedu.address.model.util.TimeConvertUtil.convertStringToTime;
import static seedu.address.model.util.TimeConvertUtil.convertTimeToString;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.TodoItem;

/**
 * JAXB-friendly adapted version of the TodoItem.
 */
public class XmlAdapterTodoItem {

    @XmlElement(required = true)
    private String startTimeStr;
    @XmlElement
    private String endTimeStr;
    @XmlElement(required = true)
    private String task;

    /**
     * Constructs an XmlAdapterTodoItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdapterTodoItem() {}

    /**
     * Converts a given TodoItem into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdapterTodoItem
     */
    public XmlAdapterTodoItem(TodoItem source) {
        startTimeStr = convertTimeToString(source.start);
        endTimeStr = convertTimeToString(source.end);
        task = source.task;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's TodoItem object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted todoItem
     */
    public TodoItem toModelType() throws IllegalValueException {
        LocalDateTime end = null;
        if (endTimeStr != null && !endTimeStr.isEmpty()) {
            end = convertStringToTime(endTimeStr);
        }

        return new TodoItem(convertStringToTime(startTimeStr), end, task);
    }
}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public boolean isEncrypted() throws IOException {
        return isEncrypted(filePath);
    }

    /**
     * Similar to {@link #isEncrypted()}
     *
     * @param filePath location of the data. Cannot be null
     */
    public boolean isEncrypted(String filePath) throws IOException {
        requireNonNull(filePath);

        File file = new File(filePath);
        return SecurityUtil.isEncrypted(file);
    }

    @Override
    public void encryptAddressBook(String password)
            throws IOException, EncryptOrDecryptException {
        encryptAddressBook(filePath, password);
    }

    /**
     * Similar to {@link #encryptAddressBook(String)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void encryptAddressBook(String filePath, String password)
            throws IOException, EncryptOrDecryptException {
        requireNonNull(filePath);

        File file = new File(filePath);
        SecurityUtil.encrypt(file, password);
    }

    @Override
    public void decryptAddressBook(String password)
            throws IOException, EncryptOrDecryptException {
        decryptAddressBook(filePath, password);
    }

    /**
     * Similar to {@link #decryptAddressBook(String)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void decryptAddressBook(String filePath, String password)
            throws IOException, EncryptOrDecryptException {
        requireNonNull(filePath);

        File file = new File(filePath);
        SecurityUtil.decrypt(file, password);
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * @return the url string according to {@code searchMode}.
     */
    public String getUrl(ReadOnlyPerson person) {
        switch (searchMode) {
        case GOOGLE_SEARCH_NAME:
            return GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX;
        case GOOGLE_SEARCH_PHONE:
            return GOOGLE_SEARCH_URL_PREFIX + person.getPhone().value.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX;
        case GOOGLE_SEARCH_EMAIL:
            return GOOGLE_SEARCH_URL_PREFIX + person.getEmail().value.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX;
        case GOOGLE_SEARCH_ADDRESS:
            return GOOGLE_MAP_URL_PREFIX + person.getAddress().value.replaceAll(" ", "+");
        default:
            return GOOGLE_SEARCH_URL_PREFIX + GOOGLE_SEARCH_URL_SUFFIX;
        }
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleChangeSearchEvent(ChangeSearchEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        searchMode = event.searchMode;
    }
```
###### \java\seedu\address\ui\BrowserSearchMode.java
``` java
package seedu.address.ui;

/**
 * Contains constants for selecting browser search mode.
 */
public class BrowserSearchMode {

    public static final int GOOGLE_SEARCH_NAME = 0;
    public static final int GOOGLE_SEARCH_PHONE = 1;
    public static final int GOOGLE_SEARCH_EMAIL = 2;
    public static final int GOOGLE_SEARCH_ADDRESS = 3;
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        todoPanel = new TodoPanel(logic.getFilteredPersonList());

        switchablePlaceholder.getChildren().add(browserPanel.getRoot());
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Switches the display between TodoList and browser in {@code switchablePlaceholder}.
     */
    private void switchPlaceholderDisplay(int mode) {
        switchablePlaceholder.getChildren().clear();
        switch (mode) {
        case SwitchCommand.SWITCH_TO_TODOLIST:
            switchablePlaceholder.getChildren().add(todoPanel.getRoot());
            break;
        case SwitchCommand.SWITCH_TO_BROWSER:
            switchablePlaceholder.getChildren().add(browserPanel.getRoot());
            break;
        default:
            // Only two modes, no default option here.
            break;
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleSwitchDisplayEvent(SwitchDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchPlaceholderDisplay(event.mode);
    }
}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handleClearListSelectionEvent(ClearListSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(CANCEL_SELECTION_INDEX);
    }
```
###### \java\seedu\address\ui\TodoCard.java
``` java
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.TodoItem;

/**
 * An UI component that displays information of a {@code TodoItem}.
 */
public class TodoCard extends UiPart<Region> {
    private static final String FXML = "TodoCard.fxml";

    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label task;

    public TodoCard(TodoItem item, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        bindListeners(item);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code List<TodoItem>} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(TodoItem item) {
        time.textProperty().bind(Bindings.convert(new SimpleObjectProperty<>(item.getTimeString())));
        task.textProperty().bind(Bindings.convert(new SimpleObjectProperty<>(item.task)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoCard)) {
            return false;
        }

        // state check
        TodoCard card = (TodoCard) other;
        return id.getText().equals(card.id.getText())
                && time.getText().equals(card.time.getText())
                && task.getText().equals(card.task.getText());
    }
}
```
###### \java\seedu\address\ui\TodoPanel.java
``` java
package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowAllTodoItemsEvent;
import seedu.address.commons.events.ui.ShowPersonTodoEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;

/**
 * Panel containing the list of {@code TodoItem}.
 */
public class TodoPanel extends UiPart<Region> {
    private static final String FXML = "TodoPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TodoPanel.class);

    @FXML
    private ListView<TodoCard> todoCardList;

    private List<ReadOnlyPerson> personList;

    public TodoPanel(List<ReadOnlyPerson> personList) {
        super(FXML);
        this.personList = personList;
        setAllConnections();
        registerAsAnEventHandler(this);
    }

    private void setAllConnections() {
        List<TodoItem> todoItemList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            todoItemList.addAll(person.getTodoItems());
        }

        setConnections(todoItemList);
    }

    private void setConnections(List<TodoItem> todoItemList) {
        List<TodoCard> cardList = new ArrayList<>();
        for (int i = 0; i < todoItemList.size(); i++) {
            cardList.add(new TodoCard(todoItemList.get(i), i + 1));
        }

        ObservableList<TodoCard> mappedList = FXCollections.observableList(cardList);
        todoCardList.setItems(mappedList);
        todoCardList.setCellFactory(listView -> new TodoPanel.TodoListViewCell());
    }

    @Subscribe
    private void handleShowPersonTodoEvent(ShowPersonTodoEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(event.person.getTodoItems());
    }

    @Subscribe
    private void handleShowAllTodoItemsEvent(ShowAllTodoItemsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setAllConnections();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(personList.get(event.targetIndex).getTodoItems());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TodoCard}.
     */
    class TodoListViewCell extends ListCell<TodoCard> {

        @Override
        protected void updateItem(TodoCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
```
###### \resources\view\TodoCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox>
         <children>
            <Label fx:id="id" text="id">
               <HBox.margin>
                  <Insets bottom="10.0" left="10.0" top="10.0" />
               </HBox.margin>
            </Label>
            <Label fx:id="time" lineSpacing="5.0" text="dd-MM-yyyy">
               <HBox.margin>
                  <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
               </HBox.margin>
            </Label>
         </children>
      </HBox>
      <Label fx:id="task" text="Label">
         <VBox.margin>
            <Insets bottom="10.0" left="10.0" right="10.0" />
         </VBox.margin>
      </Label>
   </children>
</VBox>
```
###### \resources\view\TodoPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="todoCardList" minWidth="450.0" style="-fx-background-color: #f3e4c6;" VBox.vgrow="ALWAYS" />
</VBox>
```
