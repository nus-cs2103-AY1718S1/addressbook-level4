package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteProfilePicCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnknownCommand;
import seedu.address.logic.commands.UpdateProfilePicCommand;
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
     * Variables used for Unknown command parsing
     */
    private boolean correctionPrompted = false;
    private UnknownCommand unknownCommand = null;

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

        //@@author philemontan
        /**
         * Executes if the system has prompted the user for a typo-correction suggestion previously,
         * If the user accepts the suggestion with a response of "yes" or "y", we will execute the generated suggested
         * command.
         * Upon any other response, we will reset the unknownCommand instance, and parse the command normally with the
         * switch
         */
        if (correctionPrompted) {
            if (userAcceptsSuggestion(commandWord)) {
                Command suggestedCommand = unknownCommand.getSuggestedCommand();
                resetCorrectionChecker();
                return suggestedCommand;
            } else {
                resetCorrectionChecker();
            }
        }
        //@@author

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditEventCommand.COMMAND_WORD:
            return new EditEventCommandParser().parse(arguments);

        case UpdateProfilePicCommand.COMMAND_WORD:
            return new UpdateProfilePicCommandParser().parse(arguments);

        case DeleteProfilePicCommand.COMMAND_WORD:
            return new DeleteProfilePicCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case LocationCommand.COMMAND_WORD:
            return new LocationCommandParser().parse(arguments);

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

        case RemoveTagCommand.COMMAND_WORD:
            return new RemoveTagCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommand();

        case EmailCommand.COMMAND_WORD:
            return new EmailCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        //@@author philemontan
        /**
         * The default case will attempt to guess the intended user input
         */
        default:
            unknownCommand = new UnknownCommand(commandWord, arguments);

            if (unknownCommand.suggestionFound()) {
                correctionPrompted = true;
                return unknownCommand;
            } else {
                unknownCommand = null;
            }

            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        //@@author
    }

    //@@author philemontan
    private boolean userAcceptsSuggestion(String commandWord) {
        return commandWord.equals("yes") || commandWord.equals("y");
    }

    private void resetCorrectionChecker() {
        unknownCommand = null;
        correctionPrompted = false;
    }
    //@@author

}
