package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CopyCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocateCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
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
            //Fallthrough

        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case CalendarCommand.COMMAND_WORD:
            //Fallthrough

        case CalendarCommand.COMMAND_ALIAS:
            return new CalendarCommand();

        case EditCommand.COMMAND_WORD:
            //Fallthrough

        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case EmailCommand.COMMAND_WORD:
             //Fallthrough

        case EmailCommand.COMMAND_ALIAS:
            return new EmailCommandParser().parse(arguments);

        case LocateCommand.COMMAND_WORD:
            //Fallthrough

        case LocateCommand.COMMAND_ALIAS:
            return new LocateCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            //Fallthrough

        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ScheduleCommand.COMMAND_WORD:
            //Fallthrough

        case ScheduleCommand.COMMAND_ALIAS:
            return new ScheduleCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            //Fallthrough

        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            //Fallthrough

        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            //Fallthrough

        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            //Fallthrough

        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            //Fallthrough

        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            //Fallthrough

        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            //Fallthrough

        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            //Fallthrough

        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case CopyCommand.COMMAND_WORD:
            //Fallthrough

        case CopyCommand.COMMAND_ALIAS:
            return new CopyCommandParser().parse(arguments);



        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
