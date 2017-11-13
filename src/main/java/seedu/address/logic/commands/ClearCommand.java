package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.commands.PersonDeletedEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.RecycleBin;
import seedu.address.storage.AddressBookData;

/**
 * Resets the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Application data has been reset!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        EventsCenter.getInstance().post(new PersonDeletedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
