package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BatchCommand;
import seedu.address.logic.commands.CalendarViewCommand;
import seedu.address.logic.commands.CancelAppointmentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CopyCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DuplicatesCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListAscendingNameCommand;
import seedu.address.logic.commands.ListByBloodtypeCommand;
import seedu.address.logic.commands.ListByTagCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListDescendingNameCommand;
import seedu.address.logic.commands.ListFailureCommand;
import seedu.address.logic.commands.ListReverseCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RelationshipCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ToggleTagColorCommand;
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
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case DuplicatesCommand.COMMAND_WORD:
        case DuplicatesCommand.COMMAND_ALIAS:
            return new DuplicatesCommand();

        case BatchCommand.COMMAND_WORD:
        case BatchCommand.COMMAND_ALIAS:
            return new BatchCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case CopyCommand.COMMAND_WORD:
        case CopyCommand.COMMAND_ALIAS:
            return new CopyCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return listEvaluator(arguments);

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

        case ToggleTagColorCommand.COMMAND_WORD:
        case ToggleTagColorCommand.COMMAND_ALIAS:
            return new ToggleTagColorParser().parse(arguments);

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case RelationshipCommand.COMMAND_WORD:
        case RelationshipCommand.COMMAND_ALIAS:
            return new RelationshipCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_WORD:
        case AddAppointmentCommand.COMMAND_ALIAS:
            return new AddAppointmentParser().parse(arguments);

        case CancelAppointmentCommand.COMMAND_WORD:
            return new CancelAppointmentParser().parse(arguments);

        case CalendarViewCommand.COMMAND_WORD:
        case CalendarViewCommand.COMMAND_ALIAS:
            return new CalendarViewParser().parse(arguments);

        case ListByBloodtypeCommand.COMMAND_WORD:
        case ListByBloodtypeCommand.COMMAND_ALIAS:
            return new ListByBloodtypeCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@@author Jeremy

    /**
     * Returns the correct list feature based on word after list
     *
     * @param arguments full user input arguments.
     * @return the command based on arguments provided.
     * @throws ParseException If the user input does not conform the expected format.
     */
    private Command listEvaluator(String arguments) throws ParseException {
        String[] argSplit = arguments.trim().split(" ");
        String firstArg = argSplit[0];
        int firstArgLength = firstArg.length();
        Command returnThisCommand;
        switch (firstArg) {
        case "":
            returnThisCommand = new ListCommand();
            break;
        case ListByTagCommand.COMMAND_SELECTOR:
            returnThisCommand = new ListByTagCommandParser().parse(arguments.substring(firstArgLength + 1));
            break;
        case ListAscendingNameCommand.COMMAND_ALIAS:
        case ListAscendingNameCommand.COMMAND_WORD:
            returnThisCommand = (argSplit.length == 1) ? new ListAscendingNameCommand() : new ListFailureCommand();
            break;
        case ListDescendingNameCommand.COMMAND_ALIAS:
        case ListDescendingNameCommand.COMMAND_WORD:
            returnThisCommand = (argSplit.length == 1) ? new ListDescendingNameCommand() : new ListFailureCommand();
            break;
        case ListReverseCommand.COMMAND_ALIAS:
        case ListReverseCommand.COMMAND_WORD:
            returnThisCommand = (argSplit.length == 1) ? new ListReverseCommand() : new ListFailureCommand();
            break;
        default:
            returnThisCommand = new ListFailureCommand();
        }
        return returnThisCommand;
    }
    //@@author
}
