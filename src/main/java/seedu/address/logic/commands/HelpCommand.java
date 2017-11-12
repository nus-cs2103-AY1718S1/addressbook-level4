package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

//@@author Pengyuz
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
        if ("theme".equals(commandword)) {
            return new CommandResult(SwitchThemeCommand.MESSAGE_USAGE);
        } else if ("bin-fresh".equals(commandword)) {
            return new CommandResult(BinclearCommand.MESSAGE_USAGE);
        } else if ("bin-delete".equals(commandword)) {
            return new CommandResult(BindeleteCommand.MESSAGE_USAGE);
        } else if ("bin-restore".equals(commandword)) {
            return new CommandResult(BinrestoreCommand.MESSAGE_USAGE);
        } else if ("export".equals(commandword)) {
            return new CommandResult(ExportCommand.MESSAGE_USAGE);
        } else if ("add".equals(commandword)) {
            return new CommandResult(AddCommand.MESSAGE_USAGE);
        } else if ("clear".equals(commandword)) {
            return new CommandResult(ClearCommand.MESSAGE_USAGE);
        } else if ("delete".equals(commandword)) {
            return new CommandResult(DeleteCommand.MESSAGE_USAGE);
        } else if ("edit".equals(commandword)) {
            return new CommandResult(EditCommand.MESSAGE_USAGE);
        } else if ("exit".equals(commandword)) {
            return new CommandResult(ExitCommand.MESSAGE_USAGE);
        } else if ("find".equals(commandword)) {
            return new CommandResult(FindCommand.MESSAGE_USAGE);
        } else if ("history".equals(commandword)) {
            return new CommandResult(HistoryCommand.MESSAGE_USAGE);
        } else if ("list".equals(commandword)) {
            return new CommandResult(ListCommand.MESSAGE_USAGE);
        } else if ("redo".equals(commandword)) {
            return new CommandResult(RedoCommand.MESSAGE_USAGE);
        } else if ("select".equals(commandword)) {
            return new CommandResult(SelectCommand.MESSAGE_USAGE);
        } else if ("sort".equals(commandword)) {
            return new CommandResult(SortCommand.MESSAGE_USAGE);
        } else if ("tagadd".equals(commandword)) {
            return new CommandResult(TagAddCommand.MESSAGE_USAGE);
        } else if ("tagremove".equals(commandword)) {
            return new CommandResult(TagRemoveCommand.MESSAGE_USAGE);
        } else if ("tagfind".equals(commandword)) {
            return new CommandResult(TagFindCommand.MESSAGE_USAGE);
        } else if ("birthdayadd".equals(commandword)) {
            return new CommandResult(BirthdayAddCommand.MESSAGE_USAGE);
        } else if ("birthdayremove".equals(commandword)) {
            return new CommandResult(BirthdayRemoveCommand.MESSAGE_USAGE);
        } else if ("mapshow".equals(commandword)) {
            return new CommandResult(MapShowCommand.MESSAGE_USAGE);
        } else if ("maproute".equals(commandword)) {
            return new CommandResult(MapRouteCommand.MESSAGE_USAGE);
        } else if ("scheduleadd".equals(commandword)) {
            return new CommandResult(ScheduleAddCommand.MESSAGE_USAGE);
        } else if ("scheduleremove".equals(commandword)) {
            return new CommandResult(ScheduleRemoveCommand.MESSAGE_USAGE);
        } else if ("undo".equals(commandword)) {
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
