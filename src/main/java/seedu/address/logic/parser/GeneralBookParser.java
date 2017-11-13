package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TabPane;
import seedu.address.commons.core.AliasSettings;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CurrentUserCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.OrderCommand;
import seedu.address.logic.commands.OrderEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveUserCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelectEventCommand;
import seedu.address.logic.commands.SetAliasCommand;
import seedu.address.logic.commands.SetThemeCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.TransferCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewAliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * Parses user input.
 */
public class GeneralBookParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static UserPrefs userPrefs;
    private TabPane tabPane;

    public GeneralBookParser(UserPrefs userPref) {
        this.userPrefs = userPref;
    }

    /**
     * Used for initial separation of command word and args.
     */

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        AliasSettings aliasSettings = userPrefs.getAliasSettings();

        if (commandWord.equals(LockCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getLockCommand().getAlias())) {
            return new LockCommandParser().parse(arguments);
        } else if (commandWord.equals(RemoveUserCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getRemoveUserCommand().getAlias())) {
            return new RemoveUserCommandParser().parse(arguments);
        } else if (commandWord.equals(LoginCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getLoginCommand().getAlias())) {
            return new LoginCommandParser().parse(arguments);
        } else if (commandWord.equals(LogoutCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getLoginCommand().getAlias())) {
            return new LogoutCommand();
        } else if (commandWord.equals(CurrentUserCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getCurrentUserCommand().getAlias())) {
            return new CurrentUserCommand();
        } else if (commandWord.equals(AddCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getAddCommand().getAlias())) {
            return new AddCommandParser().parse(arguments);
        } else if (commandWord.equals(EditCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getEditCommand().getAlias())) {
            return new EditCommandParser().parse(arguments);
        } else if (commandWord.equals(SelectCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getSelectCommand().getAlias())) {
            return new SelectCommandParser().parse(arguments);
        } else if (commandWord.equals(OrderCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getOrderCommand().getAlias())) {
            return new OrderCommandParser().parse(arguments);
        } else if (commandWord.equals(GroupCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getGroupCommand().getAlias())) {
            return new GroupCommandParser().parse(arguments);
        } else if (commandWord.equals(DeleteCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getDeleteCommand().getAlias())) {
            return new DeleteCommandParser().parse(arguments);
        } else if (commandWord.equals(ClearCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getClearCommand().getAlias())) {
            return new ClearCommand();
        } else if (commandWord.equals(FindCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getFindCommand().getAlias())) {
            return new FindCommandParser().parse(arguments);
        } else if (commandWord.equals(ListCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getListCommand().getAlias())) {
            return new ListCommandParser().parse(arguments);
        } else if (commandWord.equals(ViewAliasCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getViewAliasCommand().getAlias())) {
            return new ViewAliasCommand();
        } else if (commandWord.equals(SetAliasCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getSetAliasCommand().getAlias())) {
            return new SetAliasCommandParser().parse(arguments);
        } else if (commandWord.equals(HistoryCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getHistoryCommand().getAlias())) {
            return new HistoryCommand();
        } else if (commandWord.equals(RemarkCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getRemarkCommand().getAlias())) {
            return new RemarkCommandParser().parse(arguments);
        } else if (commandWord.equals(ExitCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getExitCommand().getAlias())) {
            return new ExitCommand();
        } else if (commandWord.equals(HelpCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getHelpCommand().getAlias())) {
            return new HelpCommand();
        } else if (commandWord.equals(UndoCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getUndoCommand().getAlias())) {
            return new UndoCommand();
        } else if (commandWord.equals(RedoCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getRedoCommand().getAlias())) {
            return new RedoCommand();
        } else if (commandWord.equals(AddEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getAddEventCommand().getAlias())) {
            return new AddEventCommandParser().parse(arguments);
        } else if (commandWord.equals(DeleteEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getDeleteEventCommand().getAlias())) {
            return new DeleteEventCommandParser().parse(arguments);
        } else if (commandWord.equals(EditEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getEditEventCommand().getAlias())) {
            return new EditEventCommandParser().parse(arguments);
        } else if (commandWord.equals(ListEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getListEventCommand().getAlias())) {
            return new ListEventCommand();
        } else if (commandWord.equals(OrderEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getOrderEventCommand().getAlias())) {
            return new OrderEventCommandParser().parse(arguments);
        } else if (commandWord.equals(FindEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getFindEventCommand().getAlias())) {
            return new FindEventCommandParser().parse(arguments);
        } else if (commandWord.equals(SetThemeCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getSetThemeCommand().getAlias())) {
            return new SetThemeCommandParser().parse(arguments);
        } else if (commandWord.equals(SwitchCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getSwitchCommand().getAlias())) {
            return new SwitchCommand(tabPane);
        } else if (commandWord.equals(SelectEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getSelectEventCommand().getAlias())) {
            return new SelectEventCommandParser().parse(arguments);
        } else if (commandWord.equals(ExportCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getExportCommand().getAlias())) {
            return new ExportCommandParser().parse(arguments);
        } else if (commandWord.equals(TransferCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getTransferCommand().getAlias())) {
            return new TransferCommand();
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Used for passing the UI TabPane
     */
    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
}
