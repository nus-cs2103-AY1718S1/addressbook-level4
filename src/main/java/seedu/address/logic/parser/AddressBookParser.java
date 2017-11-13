package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindLettersCommand;
import seedu.address.logic.commands.FindPersonsWithTagsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTagsCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SendEmailCommand;
import seedu.address.logic.commands.SyncCommand;

import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
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
     * Note: the switch-case implementation below should not be changed frivolously as it provides a layer of defence
     * against having duplicate command words
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, CommandException, GoogleAuthException {
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

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        //@@author PokkaKiyo
        case FindPersonsWithTagsCommand.COMMAND_WORD:
        case FindPersonsWithTagsCommand.COMMAND_ALIAS1:
        case FindPersonsWithTagsCommand.COMMAND_ALIAS2:
            return new FindPersonsWithTagsCommandParser().parse(arguments);
        //@@author

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        //@@author PokkaKiyo
        case ListTagsCommand.COMMAND_WORD:
        case ListTagsCommand.COMMAND_WORD_ALIAS1:
        case ListTagsCommand.COMMAND_WORD_ALIAS2:
            return new ListTagsCommand();
        //@@author

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        //@@author srishag
        case SendEmailCommand.COMMAND_WORD:
        case SendEmailCommand.COMMAND_ALIAS:
            return new SendEmailCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
        case AddTaskCommand.COMMAND_ALIAS:
            return new AddTaskCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
        case EditTaskCommand.COMMAND_ALIAS:
            return new EditTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
        case DeleteTaskCommand.COMMAND_ALIAS:
            return new DeleteTaskCommandParser().parse(arguments);
        //@@author

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommand();


        case ImportCommand.COMMAND_WORD:
            return new ImportCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommand();

        case SyncCommand.COMMAND_WORD:
            return new SyncCommand();

        case FindLettersCommand.COMMAND_WORD:
            return new FindLettersCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
