package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BirthdayAlarmCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ThemeCommand;
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

        case AddCommand.COMMAND_ALIAS:
        case AddCommand.COMMAND_SHORT:
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_ALIAS:
        case EditCommand.COMMAND_SHORT:
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_SHORT:
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_ALIAS:
        case DeleteCommand.COMMAND_SHORT:
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_ALIAS:
        case ClearCommand.COMMAND_SHORT:
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_ALIAS:
        case FindCommand.COMMAND_SHORT:
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_SHORT:
        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_SHORT:
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        //@@author chrisboo
        case OpenCommand.COMMAND_SHORT:
        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);

        case NewCommand.COMMAND_SHORT:
        case NewCommand.COMMAND_WORD:
            return new NewCommandParser().parse(arguments);
        //@@author

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_SHORT:
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_SHORT:
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        //@@author Jemereny
        case ThemeCommand.COMMAND_WORD:
        case ThemeCommand.COMMAND_SHORT:
            return new ThemeCommandParser().parse(arguments);
        //@@author

        //@@author chilipadiboy
        case BirthdayAlarmCommand.COMMAND_WORD:
        case BirthdayAlarmCommand.COMMAND_SHORT:
            return new BirthdayAlarmCommand();
        //@@author
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
