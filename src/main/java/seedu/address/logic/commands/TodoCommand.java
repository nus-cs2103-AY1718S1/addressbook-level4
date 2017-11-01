package seedu.address.logic.commands;

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

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.optionparser.CommandOptionUtil.PREFIX_OPTION_INDICATOR;

public class TodoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "todo";

    /* Option prefix definitions */
    public static final String PREFIX_TODO_ADD = PREFIX_OPTION_INDICATOR + "a";
    public static final String PREFIX_TODO_DELETE_ONE = PREFIX_OPTION_INDICATOR + "d";
    public static final String PREFIX_TODO_DELETE_ALL = PREFIX_OPTION_INDICATOR + "c";
    public static final String PREFIX_TODO_LIST = PREFIX_OPTION_INDICATOR + "l";
    public static final String PREFIX_TODO_LIST_ALL = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": todo ";

    public static final String MESSAGE_SUCCESS_ADD = "New task added: %1$s";
    public static final String MESSAGE_SUCCESS_DELETE_ONE = "Deleted todo item: %1$s ";
    public static final String MESSAGE_SUCCESS_DELETE_ALL = "Deleted all todo items of No.%1$s person ";
    public static final String MESSAGE_SUCCESS_LIST = "Listed all todo items of No.%1$s person";
    public static final String MESSAGE_SUCCESS_LIST_ALL = "Listed all todo items";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TODOITEM = "This todo item already exists in the address book.";
    public static final String MESSAGE_ERROR_PREFIX = "Undefined prefix";

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

    private void executeTodoAdd() throws CommandException,
            PersonNotFoundException, DuplicatePersonException, DuplicateTodoItemException {
        ReadOnlyPerson person = getReadOnlyPersonFromIndex();
        model.addTodoItem(person, todoItem);
    }

    private void executeTodoDeleteOne() throws CommandException {
        ReadOnlyPerson person = getReadOnlyPersonFromIndex();
        todoItem = person.getTodoItems().get(itemIndex.getZeroBased());
        model.deleteTodoItem(person ,todoItem);
    }

    private void executeTodoDeleteAll() throws CommandException,
            PersonNotFoundException, DuplicatePersonException {
        ReadOnlyPerson person = getReadOnlyPersonFromIndex();
        model.resetTodoItem(person);
    }

    private void executeTodoList() throws CommandException {
        getReadOnlyPersonFromIndex();
    }

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
                && todoItem.equals(((TodoCommand) other).todoItem));
    }
}
