package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.room.logic.commands.AddCommand;
import seedu.room.logic.commands.BackupCommand;
import seedu.room.logic.commands.ClearCommand;
import seedu.room.logic.commands.Command;
import seedu.room.logic.commands.DeleteCommand;
import seedu.room.logic.commands.EditCommand;
import seedu.room.logic.commands.ExitCommand;
import seedu.room.logic.commands.FindCommand;
import seedu.room.logic.commands.HelpCommand;
import seedu.room.logic.commands.HistoryCommand;
import seedu.room.logic.commands.ImportCommand;
import seedu.room.logic.commands.ListCommand;
import seedu.room.logic.commands.RedoCommand;
import seedu.room.logic.commands.SelectCommand;
import seedu.room.logic.commands.SortCommand;
import seedu.room.logic.commands.SwaproomCommand;
import seedu.room.logic.commands.UndoCommand;

import seedu.room.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ResidentBookParser {

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

        //commandWord: For e.g. "add" or "edit"
        final String commandWord = matcher.group("commandWord");

        //arguments: Refers to everything that follows the commandWord. For e.g. n/John Doe p/90452133 ...
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommandParser().parse(arguments);

        case SwaproomCommand.COMMAND_WORD:
        case SwaproomCommand.COMMAND_ALIAS:
            return new SwaproomCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case BackupCommand.COMMAND_WORD:
        case BackupCommand.COMMAND_ALIAS:
            return new BackupCommand();

        case ImportCommand.COMMAND_WORD:
        case ImportCommand.COMMAND_ALIAS:
            return new ImportCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
