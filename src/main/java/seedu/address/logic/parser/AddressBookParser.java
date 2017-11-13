package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.commands.DetailsCommand;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.commands.NicknameCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.ToggleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewTagCommand;
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
        case AddCommand.COMMAND_ALIAS:
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddReminderCommand.COMMAND_WORD:
            return new AddReminderCommandParser().parse(arguments);

        case EditCommand.COMMAND_ALIAS:
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditReminderCommand.COMMAND_WORD:
            return new EditReminderCommandParser().parse(arguments);

        case SelectCommand.COMMAND_ALIAS:
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DetailsCommand.COMMAND_WORD:
            return new DetailsCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_ALIAS:
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteReminderCommand.COMMAND_WORD:
            return new DeleteReminderCommandParser().parse(arguments);

        case ClearCommand.COMMAND_ALIAS:
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_ALIAS:
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_ALIAS:
        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ViewTagCommand.COMMAND_WORD:
            return new ViewTagCommandParser().parse(arguments);

        case LocationCommand.COMMAND_WORD:
            return new LocationCommandParser().parse(arguments);

        case EmailCommand.COMMAND_WORD:
            return new EmailCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_ALIAS:
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_ALIAS:
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_ALIAS:
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_ALIAS:
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_ALIAS:
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        case ToggleCommand.COMMAND_WORD:
            return new ToggleCommand();

        //@@author chuaweiwen
        case NicknameCommand.COMMAND_WORD:
            return new NicknameCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommandParser().parse(arguments);
        //@@author

        //@@author edwinghy
        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);
        //@@author
        case DisplayPictureCommand.COMMAND_WORD:
            return new DisplayPictureCommandParser().parse(arguments);

        //@@author chuaweiwen
        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);
        //@@author

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
