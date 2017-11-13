package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.*;

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

        case AddEventTagCommand.COMMAND_WORD:
        case AddEventTagCommand.COMMAND_ALIAS:
            return new AddEventTagCommandParser().parse(arguments);

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

        case FindByAddressCommand.COMMAND_WORD:
        case FindByAddressCommand.COMMAND_ALIAS:
            return new FindByAddressCommandParser().parse(arguments);

        case FindByEmailCommand.COMMAND_WORD:
        case FindByEmailCommand.COMMAND_ALIAS:
            return new FindByEmailCommandParser().parse(arguments);

        case FindByPhoneCommand.COMMAND_WORD:
        case FindByPhoneCommand.COMMAND_ALIAS:
            return new FindByPhoneCommandParser().parse(arguments);

        case FindByNameCommand.COMMAND_WORD:
        case FindByNameCommand.COMMAND_ALIAS:
            return new FindByNameCommandParser().parse(arguments);

        case FindByTagCommand.COMMAND_WORD:
        case FindByTagCommand.COMMAND_ALIAS:
            return new FindByTagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        case LocateCommand.COMMAND_WORD:
        case LocateCommand.COMMAND_ALIAS:
            return new LocateCommandParser().parse(arguments);

        case LocateMrtCommand.COMMAND_WORD:
        case LocateMrtCommand.COMMAND_ALIAS:
            return new LocateMrtCommandParser().parse(arguments);

        case ChangePasswordCommand.COMMAND_WORD:
            return new ChangePasswordCommandParser().parse(arguments);

        case ChangeUsernameCommand.COMMAND_WORD:
            return new ChangeUsernameCommandParser().parse(arguments);

        case AddScheduleCommand.COMMAND_WORD:
        case AddScheduleCommand.COMMAND_ALIAS:
            return new AddScheduleCommandParser().parse(arguments);

        case ClearScheduleCommand.COMMAND_WORD:
        case ClearScheduleCommand.COMMAND_ALIAS:
            return new ClearScheduleCommandParser().parse(arguments);

        case ArrangeCommand.COMMAND_WORD:
        case ArrangeCommand.COMMAND_ALIAS:
            return new ArrangeCommandParser().parse(arguments);

        case VisualizeCommand.COMMAND_WORD:
        case VisualizeCommand.COMMAND_ALIAS:
            return new VisualizeCommandParser().parse(arguments);

        case CreateDefaultAccountCommand.COMMAND_WORD:
            return new CreateDefaultAccountCommand();

        case RemoveAccountCommand.COMMAND_WORD:
            return new RemoveAccountCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
