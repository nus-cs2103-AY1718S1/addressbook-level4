package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCalendarCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddFavouriteCommand;
import seedu.address.logic.commands.AddPersonToGroupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveFavouriteCommand;
import seedu.address.logic.commands.RemovePersonFromGroupCommand;
import seedu.address.logic.commands.ResetPictureCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetPictureCommand;
import seedu.address.logic.commands.SortCommand;
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

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALT:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALT:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALT:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALT:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALT:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALT:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALT:
            return new ListCommandParser().parse(arguments);
        //@@author cjianhui
        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALT:
            return new SortCommandParser().parse(arguments);

        case CreateGroupCommand.COMMAND_WORD:
        case CreateGroupCommand.COMMAND_ALT:
            return new CreateGroupCommandParser().parse(arguments);

        case DeleteGroupCommand.COMMAND_WORD:
        case DeleteGroupCommand.COMMAND_ALT:
            return new DeleteGroupCommandParser().parse(arguments);

        case AddPersonToGroupCommand.COMMAND_WORD:
        case AddPersonToGroupCommand.COMMAND_ALT:
            return new AddPersonToGroupCommandParser().parse(arguments);

        case RemovePersonFromGroupCommand.COMMAND_WORD:
        case RemovePersonFromGroupCommand.COMMAND_ALT:
            return new RemovePersonFromGroupCommandParser().parse(arguments);
        //@@author

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALT:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALT:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALT:
            return new RedoCommand();
        //@@author nassy93
        case AddFavouriteCommand.COMMAND_WORD:
        case AddFavouriteCommand.COMMAND_ALT:
            return new AddFavouriteCommandParser().parse(arguments);

        case RemoveFavouriteCommand.COMMAND_WORD:
        case RemoveFavouriteCommand.COMMAND_ALT:
            return new RemoveFavouriteCommandParser().parse(arguments);
        //@@author cjianhui
        case AddCalendarCommand.COMMAND_WORD:
        case AddCalendarCommand.COMMAND_ALT:
            return new AddScheduleCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
        case AddEventCommand.COMMAND_ALT:
            return new AddEventCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
        case DeleteEventCommand.COMMAND_ALT:
            return new DeleteEventCommandParser().parse(arguments);

        //@@author nassy93
        case ResetPictureCommand.COMMAND_WORD:
        case ResetPictureCommand.COMMAND_ALT:
            return new ResetPictureCommandParser().parse(arguments);

        case SetPictureCommand.COMMAND_WORD:
        case SetPictureCommand.COMMAND_ALT:
            return new SetPictureCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
