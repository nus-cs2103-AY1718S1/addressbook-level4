package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.BusCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindModuleCommand;
import seedu.address.logic.commands.GetModuleCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListModuleCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SummaryCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.VenueCommand;
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
        //@@author jshoung
        switch (commandWord.toLowerCase()) {
        //@@author

        //@@author viviantan95
        case AddPhotoCommand.COMMAND_WORD:
        case AddPhotoCommand.COMMAND_ALIAS:
            return new AddPhotoCommandParser().parse(arguments);
        //@@author

        case AddCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case AddCommand.COMMAND_ALIAS:
            // @@author
            return new AddCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
        case AddTaskCommand.COMMAND_ALIAS:
            return new AddTaskCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        // @@author ahmadalkaff
        case EditTaskCommand.COMMAND_WORD:
        case EditTaskCommand.COMMAND_ALIAS:
            return new EditTaskCommandParser().parse(arguments);
        // @@author

        case SelectCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case SelectCommand.COMMAND_ALIAS:
            // @@author
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case DeleteCommand.COMMAND_ALIAS:
            // @@author
            return new DeleteCommandParser().parse(arguments);

        // @@author ahmadalkaff
        case DeleteTaskCommand.COMMAND_WORD:
        case DeleteTaskCommand.COMMAND_ALIAS:
            return new DeleteTaskCommandParser().parse(arguments);
            // @@author

        case ClearCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case ClearCommand.COMMAND_ALIAS:
            // @@author
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case FindCommand.COMMAND_ALIAS:
            // @@author
            return new FindCommandParser().parse(arguments);
        // @@author tanchc
        case FindModuleCommand.COMMAND_WORD:
        case FindModuleCommand.COMMAND_ALIAS:
            return new FindModuleCommandParser().parse(arguments);
        // @@author
        case ListCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case ListCommand.COMMAND_ALIAS:
            // @@author
            return new ListCommand();

        // @@author ahmadalkaff
        case ListModuleCommand.COMMAND_WORD:
        case ListModuleCommand.COMMAND_ALIAS:
            return new ListModuleCommand();
            // @@author

        case HistoryCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case HistoryCommand.COMMAND_ALIAS:
            // @@author
            return new HistoryCommand();

        // @@author ahmadalkaff
        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommand();
            // @@author

        case ExitCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case ExitCommand.COMMAND_ALIAS:
            // @@author
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        // @@author jshoung
        case HelpCommand.COMMAND_ALIAS:
            // @@author
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case UndoCommand.COMMAND_ALIAS:
            // @@author
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        // @@author ahmadalkaff
        case RedoCommand.COMMAND_ALIAS:
            // @@author
            return new RedoCommand();

        // @@author jshoung
        case BusCommand.COMMAND_WORD:
        case BusCommand.COMMAND_ALIAS:
            return new BusCommand();
            // @@author

        // @@author jshoung
        case MapCommand.COMMAND_WORD:
        case MapCommand.COMMAND_ALIAS:
            return new MapCommand();
            // @@author

        // @@author jshoung
        case GetModuleCommand.COMMAND_WORD:
        case GetModuleCommand.COMMAND_ALIAS:
            return new GetModuleCommandParser().parse(arguments);
            // @@author

        // @@author jshoung
        case SummaryCommand.COMMAND_WORD:
        case SummaryCommand.COMMAND_ALIAS:
            return new SummaryCommand();
            // @@author

        // @@author jshoung
        case VenueCommand.COMMAND_WORD:
        case VenueCommand.COMMAND_ALIAS:
            return new VenueCommandParser().parse(arguments);
            // @@author



        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
