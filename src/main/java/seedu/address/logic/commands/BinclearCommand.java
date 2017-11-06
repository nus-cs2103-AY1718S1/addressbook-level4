package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;

/**
 * Clears the recyclebin.
 */
public class BinclearCommand extends Command {

    public static final String COMMAND_WORD = "bin-clear";
    public static final String MESSAGE_SUCCESS = "Recyclebin has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clear all the person in the recyclebin.";


    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.resetRecyclebin(new AddressBook());
        //EventsCenter.getInstance().post(new ClearPersonListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
