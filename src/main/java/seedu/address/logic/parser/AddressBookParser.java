package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.logic.commands.ChangePrivacyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DismissCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FavouriteListCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FontSizeCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocateCommand;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.NavigateCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.PrivacyLevelCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SaveAsCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetCompleteCommand;
import seedu.address.logic.commands.SetIncompleteCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TagListCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.commands.ViewAssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    public static final String MESSAGE_NO_ACCESS = "Not allowed! You must unlock before"
            + " making any changes.\n" + UnlockCommand.MESSAGE_USAGE;

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, boolean lockState) throws ParseException {
        // Check lock state
        logger.info("Parsing command. Lock state is currently: " + Boolean.toString(lockState));

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        Command result = processNonCrudCommands(commandWord, arguments);
        if (result != null) {
            return result;
        }

        // Test for lock. If locked, return no access message
        if (lockState) {
            throw new ParseException(MESSAGE_NO_ACCESS);
        }

        // Cases for CRUD related functionality
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case AssignCommand.COMMAND_WORD:
        case AssignCommand.COMMAND_ALIAS:
            return new AssignTaskCommandParser().parse(arguments);

        case DismissCommand.COMMAND_WORD:
        case DismissCommand.COMMAND_ALIAS:
            return new DismissTaskCommandParser().parse(arguments);

        case ChangePrivacyCommand.COMMAND_WORD:
        case ChangePrivacyCommand.COMMAND_ALIAS:
            return new ChangePrivacyCommandParser().parse(arguments);

        case PrivacyLevelCommand.COMMAND_WORD:
        case PrivacyLevelCommand.COMMAND_ALIAS:
            return new PrivacyLevelCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_WORD:
        case ThemeCommand.COMMAND_ALIAS:
            return new ThemeCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SetCompleteCommand.COMMAND_WORD:
        case SetCompleteCommand.COMMAND_ALIAS:
            return new SetTaskCompleteCommandParser().parse(arguments);

        case SetIncompleteCommand.COMMAND_WORD:
        case SetIncompleteCommand.COMMAND_ALIAS:
            return new SetTaskIncompleteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommandParser().parse(arguments);
        //@@author wangyiming1019
        case FavouriteCommand.COMMAND_WORD:
        case FavouriteCommand.COMMAND_ALIAS:
            return new FavouriteCommandParser().parse(arguments);

        case FavouriteListCommand.COMMAND_WORD:
        case FavouriteListCommand.COMMAND_ALIAS:
            return new FavouriteListCommand();

        case UnfavouriteCommand.COMMAND_WORD:
        case UnfavouriteCommand.COMMAND_ALIAS:
            return new UnfavouriteCommandParser().parse(arguments);

        case TagListCommand.COMMAND_WORD:
        case TagListCommand.COMMAND_ALIAS:
            return new TagListCommand();
        //@@author
        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommandParser().parse(arguments);

        case ChangePasswordCommand.COMMAND_WORD:
        case ChangePasswordCommand.COMMAND_ALIAS:
            return new ChangePasswordCommandParser().parse(arguments);

        case ViewAssignCommand.COMMAND_WORD:
        case ViewAssignCommand.COMMAND_ALIAS:
            return new ViewAssignCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    //@@author charlesgoh
    /**
     * Checks for non crud command words or aliases and returns the relevant commmand if there is one.
     * Otherwise control is returned back to the original parseCommand method.
     * Note: Code was refactored due to codacy's recommendation that there was a problem with parseCommand's
     * NPath complexity (i.e. it the code was too long. Makes it less readable and prone to errors)
     * @param commandWord
     * @param arguments
     */
    private Command processNonCrudCommands(String commandWord, String arguments) throws ParseException {
        // Cases for CRUD related functionality and for locking and unlocking
        switch (commandWord) {
        case OpenCommand.COMMAND_WORD:
        case OpenCommand.COMMAND_ALIAS:
            return new OpenCommand();

        case SaveAsCommand.COMMAND_WORD:
        case SaveAsCommand.COMMAND_ALIAS:
            return new SaveAsCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case LocateCommand.COMMAND_WORD:
        case LocateCommand.COMMAND_ALIAS:
            return new LocateCommandParser().parse(arguments);

        case NavigateCommand.COMMAND_WORD:
        case NavigateCommand.COMMAND_ALIAS:
            return new NavigateCommandParser().parse(arguments);

        case BackupCommand.COMMAND_WORD:
        case BackupCommand.COMMAND_ALIAS:
            return new BackupCommandParser().parse(arguments);

        case FontSizeCommand.COMMAND_WORD:
        case FontSizeCommand.COMMAND_ALIAS:
            return new FontSizeCommandParser().parse(arguments);

        case LockCommand.COMMAND_WORD:
        case LockCommand.COMMAND_ALIAS:
            return new LockCommandParser().parse(arguments);

        case UnlockCommand.COMMAND_WORD:
        case UnlockCommand.COMMAND_ALIAS:
            return new UnlockCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        default:
            return null;
        }
    }
    //@@author
}
