package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.logic.commands.ChangeProfilePictureCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteMultipleCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNumberCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RecentlyDeletedCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
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

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);
        //@@author nguyenvanhoang7398
        case AddMultipleByTsvCommand.COMMAND_WORD:
        case AddMultipleByTsvCommand.COMMAND_ALIAS:
            return new AddMultipleByTsvCommandParser().parse(arguments);

        case ChangeProfilePictureCommand.COMMAND_WORD:
        case ChangeProfilePictureCommand.COMMAND_ALIAS:
            return new ChangeProfilePictureCommandParser().parse(arguments);
        //@@author
        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);
        //@@author TravisPhey
        case DeleteMultipleCommand.COMMAND_WORD:
        case DeleteMultipleCommand.COMMAND_ALIAS:
            return new DeleteMultipleCommandParser().parse(arguments);
        //@@author
        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case FindTagCommand.COMMAND_WORD:
        case FindTagCommand.COMMAND_ALIAS:
            return new FindTagCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommand();

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();
        //@@author TravisPhey
        case FindNumberCommand.COMMAND_WORD:
        case FindNumberCommand.COMMAND_ALIAS:
            return new FindNumberCommandParser().parse(arguments);
        //@@author vmlimshimin
        case RecentlyDeletedCommand.COMMAND_WORD:
        case RecentlyDeletedCommand.COMMAND_ALIAS:
            return new RecentlyDeletedCommand();

        case ThemeCommand.COMMAND_WORD:
        case ThemeCommand.COMMAND_ALIAS:
            return new ThemeCommand();
        //@@author
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
