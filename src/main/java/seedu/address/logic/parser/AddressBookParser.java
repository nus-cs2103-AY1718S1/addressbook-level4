package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_PLATFORM;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_PLATFORM;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AccessCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.BirthdaysCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EventsCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FavouriteListCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SwitchThemeCommand;
import seedu.address.logic.commands.ThemeListCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author DarrenCzen
/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used to control lock mechanism for different commands between person and events platforms.
     */
    private static Boolean personListActivated = true;

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
            if (personListActivated) {
                return new AddCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case AccessCommand.COMMAND_WORD:
            if (personListActivated) {
                return new AccessCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case EditCommand.COMMAND_WORD: case EditCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new EditCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case SelectCommand.COMMAND_WORD: case SelectCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new SelectCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case DeleteCommand.COMMAND_WORD: case DeleteCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new DeleteCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case ClearCommand.COMMAND_WORD: case ClearCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new ClearCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case FindCommand.COMMAND_WORD: case FindCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new FindCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case FindTagCommand.COMMAND_WORD: case FindTagCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new FindTagCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case LocationCommand.COMMAND_WORD:
            if (personListActivated) {
                return new LocationCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case SortCommand.COMMAND_WORD:
            if (personListActivated) {
                return new SortCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case ListCommand.COMMAND_WORD: case ListCommand.COMMAND_ALIAS:
            personListActivated = true;
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD: case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD: case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD: case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD: case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD: case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case ThemeListCommand.COMMAND_WORD:
            return new ThemeListCommand();

        case SwitchThemeCommand.COMMAND_WORD: case SwitchThemeCommand.COMMAND_ALIAS:
            return new SwitchThemeCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD: case AddEventCommand.COMMAND_ALIAS:
            if (!personListActivated) {
                return new AddEventCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_EVENT_PLATFORM);
            }
        case DeleteEventCommand.COMMAND_WORD: case DeleteEventCommand.COMMAND_ALIAS:
            if (!personListActivated) {
                return new DeleteEventCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_EVENT_PLATFORM);
            }
        case EventsCommand.COMMAND_WORD:
            personListActivated = false;
            return new EventsCommand();

        case FavouriteCommand.COMMAND_WORD: case FavouriteCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new FavouriteCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case UnfavouriteCommand.COMMAND_WORD: case UnfavouriteCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new UnfavouriteCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case FavouriteListCommand.COMMAND_WORD:
            if (personListActivated) {
                return new FavouriteListCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case BirthdaysCommand.COMMAND_WORD: case BirthdaysCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new BirthdaysCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
