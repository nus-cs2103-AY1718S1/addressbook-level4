package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_WORD_2 = "h";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    private String commandword = "";
    public HelpCommand() {}
    public HelpCommand(String args) {
        commandword = args;
    }

    @Override
    public CommandResult execute() {
        if (commandword.equals("add")) {
            return new CommandResult(AddCommand.MESSAGE_USAGE);
        } else if (commandword.equals("clear")) {
            return new CommandResult(ClearCommand.MESSAGE_USAGE);
        } else if (commandword.equals("delete")) {
            return new CommandResult(DeleteCommand.MESSAGE_USAGE);
        } else if (commandword.equals("edit")) {
            return new CommandResult(EditCommand.MESSAGE_USAGE);
        } else if (commandword.equals("exit")) {
            return new CommandResult(ExitCommand.MESSAGE_USAGE);
        } else if (commandword.equals("find")) {
            return new CommandResult(FindCommand.MESSAGE_USAGE);
        } else if (commandword.equals("history")) {
            return new CommandResult(HistoryCommand.MESSAGE_USAGE);
        } else if (commandword.equals("list")) {
            return new CommandResult(ListCommand.MESSAGE_USAGE);
        } else if (commandword.equals("redo")) {
            return new CommandResult(RedoCommand.MESSAGE_USAGE);
        } else if (commandword.equals("select")) {
            return new CommandResult(SelectCommand.MESSAGE_USAGE);
        } else if (commandword.equals("sort")) {
            return new CommandResult(SortCommand.MESSAGE_USAGE);
        } else if (commandword.equals("tagadd")) {
            return new CommandResult(TagAddCommand.MESSAGE_USAGE);
        } else if (commandword.equals("tagremove")) {
            return new CommandResult(TagRemoveCommand.MESSAGE_USAGE);
        } else if (commandword.equals("undo")) {
            return new CommandResult(UndoCommand.MESSAGE_USAGE);
        } else {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && this.commandword.equals(((HelpCommand) other).commandword)); // state check
    }
}
