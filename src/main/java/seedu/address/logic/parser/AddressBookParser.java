package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FuzzyfindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CommandBox;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final String MISSING_AUTOCOMPLETEFILE = "Autocomplete.xml may be missing";
    public static final String COMMAND_WORD = "commandWord";
    public static final String ARGUMENTS = "arguments";
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    //@@author justintkj
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string, adds suggestion to Autocomplete.xml if valid command
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);

        switch (commandWord.toLowerCase()) {

        case EmailCommand.COMMAND_WORD:
            Command emailCommand = new EmailCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return emailCommand;

        case SortCommand.COMMAND_WORD:
            Command sortCommand = new SortCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return sortCommand;

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            Command addCommand = new AddCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return addCommand;

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            Command editCommand = new EditCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return editCommand;

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            Command selectCommand = new SelectCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return selectCommand;

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            Command deleteCommand = new DeleteCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return deleteCommand;

        case RemoveTagCommand.COMMAND_WORD:
            Command removeTagCommand = new RemoveTagCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return removeTagCommand;

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            Command findCommand = new FindCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return findCommand;

        case FuzzyfindCommand.COMMAND_WORD:
        case FuzzyfindCommand.COMMAND_ALIAS:
            Command fuzzyfindCommand = new FuzzyfindCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return fuzzyfindCommand;

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
            Command undoCommand = new UndoCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return undoCommand;

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            Command redoCommand = new RedoCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return redoCommand;

        case RemarkCommand.COMMAND_WORD:
            Command remarkCommand = new RemarkCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return remarkCommand;

        case BirthdayCommand.COMMAND_WORD:
            Command birthdayCommand = new BirthdayCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return birthdayCommand;

        case MapCommand.COMMAND_WORD:
            Command mapCommand = new MapCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return mapCommand;

        case ImageCommand.COMMAND_WORD:
            Command imageCommand = new ImageCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return imageCommand;
        case FavouriteCommand.COMMAND_WORD:
            Command favouriteCommand = new FavouriteCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return favouriteCommand;

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Updates Storage for autocomplete with the valid command
     * @param userInput User's command
     * @throws ParseException storagefile is missing
     */
    private void addValidInputToAutocomplete(String userInput) throws ParseException {
        try {
            CommandBox.setAddSuggestion(userInput);
        } catch (Exception ex) {
            throw new ParseException(MISSING_AUTOCOMPLETEFILE);
        }
    }

}
