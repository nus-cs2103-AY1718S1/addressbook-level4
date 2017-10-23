//@@author A0144294A
package seedu.address.logic.commands;

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " ";

    private final String keyword;
    public SortCommand(String argument) {
        this.keyword = argument;
    }

    @Override
    public CommandResult execute () {

        return new CommandResult("   ");
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.keyword.equals(((SortCommand) other).keyword)); // state check
    }
}
