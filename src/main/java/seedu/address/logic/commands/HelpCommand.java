package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.model.commandidentifier.CommandIdentifier;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String COMMAND_ALIAS = "h";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ": Shows the usage of the specific command"
            + " identified. If no command is specified, opens help window.\n"
            + "Parameters: "
            + "[COMMAND_IDENTIFIER] (must be a valid command word)\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private final CommandIdentifier commandIdentifier;

    public HelpCommand(CommandIdentifier targetCommandIdentifier) {
        this.commandIdentifier = targetCommandIdentifier;
    }

    @Override
    public CommandResult execute() {
        String commandResult;

        switch(commandIdentifier.value) {

        case AddCommand.COMMAND_ALIAS:
            //Fallthrough

        case AddCommand.COMMAND_WORD:
            commandResult = AddCommand.MESSAGE_USAGE;
            break;

        case ClearCommand.COMMAND_ALIAS:
            //Fallthrough

        case ClearCommand.COMMAND_WORD:
            commandResult = ClearCommand.MESSAGE_USAGE;
            break;

        case DeleteCommand.COMMAND_ALIAS:
            //Fallthrough

        case DeleteCommand.COMMAND_WORD:
            commandResult = DeleteCommand.MESSAGE_USAGE;
            break;

        case EditCommand.COMMAND_ALIAS:
            //Fallthrough

        case EditCommand.COMMAND_WORD:
            commandResult = EditCommand.MESSAGE_USAGE;
            break;

        case ExitCommand.COMMAND_ALIAS:
            //Fallthrough

        case ExitCommand.COMMAND_WORD:
            commandResult = ExitCommand.MESSAGE_USAGE;
            break;

        case FindCommand.COMMAND_ALIAS:
            //Fallthrough

        case FindCommand.COMMAND_WORD:
            commandResult = FindCommand.MESSAGE_USAGE;
            break;

        case HelpCommand.COMMAND_ALIAS:
            //Fallthrough

        case HelpCommand.COMMAND_WORD:
            commandResult = HelpCommand.MESSAGE_USAGE;
            break;

        case HistoryCommand.COMMAND_ALIAS:
            //Fallthrough

        case HistoryCommand.COMMAND_WORD:
            commandResult = HistoryCommand.MESSAGE_USAGE;
            break;

        case ListCommand.COMMAND_ALIAS:
            //Fallthrough

        case ListCommand.COMMAND_WORD:
            commandResult = ListCommand.MESSAGE_USAGE;
            break;

        case RedoCommand.COMMAND_ALIAS:
            //Fallthrough

        case RedoCommand.COMMAND_WORD:
            commandResult = RedoCommand.MESSAGE_USAGE;
            break;

        case ScheduleCommand.COMMAND_ALIAS:
            //Fallthrough

        case ScheduleCommand.COMMAND_WORD:
            commandResult = ScheduleCommand.MESSAGE_USAGE;
            break;

        case LocateCommand.COMMAND_ALIAS:
            //Fallthrough

        case LocateCommand.COMMAND_WORD:
            commandResult = LocateCommand.MESSAGE_USAGE;
            break;

        case UndoCommand.COMMAND_ALIAS:
            //Fallthrough

        case UndoCommand.COMMAND_WORD:
            commandResult = UndoCommand.MESSAGE_USAGE;
            break;

        default:
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            commandResult = SHOWING_HELP_MESSAGE;
            break;
        }

        return new CommandResult(commandResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && this.commandIdentifier.equals(((HelpCommand) other).commandIdentifier)); // state check
    }
}
