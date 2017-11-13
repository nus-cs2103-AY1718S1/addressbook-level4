package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ClearListSelectionEvent;
import seedu.address.commons.events.ui.ShowAllTodoItemsEvent;
import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        // clear address boob
        model.resetData(new AddressBook());
        // clear todoList
        EventsCenter.getInstance().post(new ClearListSelectionEvent());
        EventsCenter.getInstance().post(new ShowAllTodoItemsEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
