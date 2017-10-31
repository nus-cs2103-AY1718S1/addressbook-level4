package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResizeCommand;
import seedu.address.logic.commands.RestoreBackupCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SyncCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

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
        switch (commandWord) {

        case AddCommand.COMMAND_WORD: case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case AddTagCommand.COMMAND_WORD: case AddTagCommand.COMMAND_ALIAS:
            return new AddTagCommandParser().parse(arguments);

        case BackupCommand.COMMAND_WORD: case BackupCommand.COMMAND_ALIAS:
            return new BackupCommand();

        case ClearCommand.COMMAND_WORD: case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case DeleteCommand.COMMAND_WORD: case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD: case DeleteTagCommand.COMMAND_ALIAS:
            return new DeleteTagCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD: case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD: case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case FindCommand.COMMAND_WORD: case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD: case FilterCommand.COMMAND_ALIAS:
            return new FilterCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD: case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case HistoryCommand.COMMAND_WORD: case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ListCommand.COMMAND_WORD: case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case ResizeCommand.COMMAND_WORD: case ResizeCommand.COMMAND_ALIAS:
            return new ResizeCommandParser().parse(arguments);

        case NoteCommand.COMMAND_WORD: case NoteCommand.COMMAND_ALIAS:
            return new NoteCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD: case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case RestoreBackupCommand.COMMAND_WORD: case RestoreBackupCommand.COMMAND_ALIAS:
            return new RestoreBackupCommand();

        case SelectCommand.COMMAND_WORD: case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD: case SortCommand.COMMAND_ALIAS:
            return new SortCommandParser().parse(arguments);

        case SyncCommand.COMMAND_WORD: case SyncCommand.COMMAND_ALIAS:
            return new SyncCommand();

        case UndoCommand.COMMAND_WORD: case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
