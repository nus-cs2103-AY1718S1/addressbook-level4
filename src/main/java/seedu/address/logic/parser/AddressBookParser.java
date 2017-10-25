package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.persons.AddCommand;
import seedu.address.logic.commands.persons.DeleteCommand;
import seedu.address.logic.commands.persons.EditCommand;
import seedu.address.logic.commands.persons.FindCommand;
import seedu.address.logic.commands.persons.ListCommand;
import seedu.address.logic.commands.persons.SelectCommand;
import seedu.address.logic.commands.tags.DetagCommand;
import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.logic.commands.tasks.DeleteTaskCommand;
import seedu.address.logic.commands.tasks.FindTaskCommand;
import seedu.address.logic.commands.tasks.ListTasksCommand;
import seedu.address.logic.commands.tasks.SelectTaskCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {


    private static final String MODE_ADDRESS_BOOK = "addressbook ab";
    private static final String MODE_TASK_MANAGER = "taskmanager tm";

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
    public Command parseCommand(String userInput, String commandMode) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        if (StringUtil.containsWordIgnoreCase(MODE_ADDRESS_BOOK, commandMode)) {
            switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case ChangeModeCommand.COMMAND_WORD:
                return new ChangeModeCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser().parse(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case DetagCommand.COMMAND_WORD:
                return new DetagCommandParser().parse(arguments);

            }
        } else if (StringUtil.containsWordIgnoreCase(MODE_TASK_MANAGER, commandMode)) {
            switch (commandWord) {

            case ChangeModeCommand.COMMAND_WORD:
                return new ChangeModeCommandParser().parse(arguments);

            case AddTaskCommand.COMMAND_WORD:
                return new AddTaskCommandParser().parse(arguments);

            case SelectTaskCommand.COMMAND_WORD:
                return new SelectTaskCommandParser().parse(arguments);

            case DeleteTaskCommand.COMMAND_WORD:
                return new DeleteTaskCommandParser().parse(arguments);

            case FindTaskCommand.COMMAND_WORD:
                return new FindTaskCommandParser().parse(arguments);

            case ListTasksCommand.COMMAND_WORD:
                return new ListTasksCommand();

            }
        }
        switch (commandWord) {
            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

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

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
