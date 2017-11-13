package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.commands.PersonDeletedEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.RecycleBin;
import seedu.address.storage.AddressBookData;

//@@author frozventus
/**
 * Clears the recycle bin.
 */
public class BinClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "binclear";
    public static final String MESSAGE_SUCCESS = "Recycle Bin has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBookData(new AddressBook(model.getAddressBook()), new RecycleBin()));
        EventsCenter.getInstance().post(new PersonDeletedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
