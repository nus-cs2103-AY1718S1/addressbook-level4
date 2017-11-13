package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddReminder;
import seedu.address.logic.commands.BackUpCommand;
import seedu.address.logic.commands.ChangeFontSizeCommand;
import seedu.address.logic.commands.ChangeReminderCommand;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearPopupCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FaceBookCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveReminderCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
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

        switch (commandWord.toLowerCase()) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ChangeReminderCommand.COMMAND_WORD:
            return new ChangeReminderCommandParser().parse(arguments);

        case AddReminder.COMMAND_WORD:
            return new AddReminderParser().parse(arguments);

        case RemoveReminderCommand.COMMAND_WORD:
            return new RemoveCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case EmailCommand.COMMAND_WORD:
            return new EmailCommandParser().parse(arguments);

        case PhotoCommand.COMMAND_WORD:
            return new PhotoCommandParser().parse(arguments);

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case FaceBookCommand.COMMAND_WORD:
            return new FaceBookCommandParser().parse(arguments);

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommandParser().parse(arguments);

        //@@author pjunwei95
        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case ClearPopupCommand.COMMAND_WORD: {
            return new ClearPopupCommand();
        }

        case BackUpCommand.COMMAND_WORD:
            return new BackUpCommand();

        //@@author ChenXiaoman
        case ChangeTagColorCommand.COMMAND_WORD:
            return new ChangeTagColorCommandParser().parse(arguments);

        case ChangeFontSizeCommand.COMMAND_WORD:
            return new ChangeFontSizeCommandParser().parse(arguments);

        case ChangeThemeCommand.COMMAND_WORD:
            return new ChangeThemeCommandParser().parse(arguments);

        case MapCommand.COMMAND_WORD:
            return new MapCommandParser().parse(arguments);

        //@@author

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
