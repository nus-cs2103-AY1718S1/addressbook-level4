//@@author edwinghy
package seedu.address.logic.commands;

/**
 * Sorts the current list in alphabetical order
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the current list in alphabetical order "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "List Sorted";

    @Override
    public CommandResult execute() {

        model.sortFilteredPersonList();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@author


