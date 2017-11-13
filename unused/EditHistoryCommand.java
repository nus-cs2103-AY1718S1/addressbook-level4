package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author sofarsophie
public class EditHistoryCommand extends Command {

    public static final String COMMAND_WORD = "edit_history";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all edit history of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_RESULT = "Edit history: ";
    public static final String MESSAGE_SUCCESS = "Listed edit history";

    private final Index index;

    public EditHistoryCommand(Index index) {
        this.index = requireNonNull(index);
    }

    @Override
    public CommandResult execute() throws CommandException {
        // Temporary print for debugging.
        System.out.println(MESSAGE_RESULT);
        for(int i=0; i<model.getAddressBook().getEditHistoryMap().getMap().get(index).size(); i++) {
            System.out.println(model.getAddressBook().getEditHistoryMap().getMap().get(index).get(i).getKey() +
                    " changed to " + model.getAddressBook().getEditHistoryMap().getMap().get(index).get(i).getValue() + "\n");
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author
