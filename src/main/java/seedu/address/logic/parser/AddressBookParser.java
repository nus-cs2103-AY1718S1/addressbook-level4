package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangePwdCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.logic.commands.FindFavCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SendCommand;
import seedu.address.logic.commands.SortAscendingCommand;
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

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case FavCommand.COMMAND_WORD:
        case FavCommand.COMMAND_ALIAS:
            return new FavCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case FindEmailCommand.COMMAND_WORD:
            return new FindEmailCommandParser().parse(arguments);

        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);

        case FindFavCommand.COMMAND_WORD:
        case FindFavCommand.COMMAND_ALIAS:
            return new FindFavCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

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

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case SendCommand.COMMAND_WORD:
        case SendCommand.COMMAND_ALIAS:
            return new SendCommandParser().parse(arguments);

        case SortAscendingCommand.COMMAND_WORD:
            return new SortAscendingCommand();

        case ChangePwdCommand.COMMAND_WORD:
        case ChangePwdCommand.COMMAND_ALIAS:
            return new ChangePwdCommandParser().parse(arguments);

        case ChangeThemeCommand.COMMAND_WORD:
        case ChangeThemeCommand.COMMAND_ALIAS:
            return new ChangeThemeCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@@author moomeowroar
    /**
     * Searches for all commands that contains the input text
     * @param text
     * @return ArrayList of command words
     */

    public ArrayList<String> filterCommand(String text, boolean isExact) throws IllegalValueException {
        String[] array = ParserUtil.parseByDelimiter(text, " ");
        ArrayList<String> result = new ArrayList<String>();
        HashMap<String, String> commandList = new HashMap<String, String>();
        commandList.put(AddCommand.COMMAND_WORD, AddCommand.COMMAND_HELP);
        commandList.put(ChangePwdCommand.COMMAND_WORD, ChangePwdCommand.COMMAND_HELP);
        commandList.put(EditCommand.COMMAND_WORD, EditCommand.COMMAND_HELP);
        commandList.put(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_HELP);
        commandList.put(FavCommand.COMMAND_WORD, FavCommand.COMMAND_HELP);
        commandList.put(FindFavCommand.COMMAND_WORD, FindFavCommand.COMMAND_HELP);
        commandList.put(FindFavCommand.COMMAND_ALIAS, FindFavCommand.COMMAND_HELP);
        commandList.put(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_HELP);
        commandList.put(FindCommand.COMMAND_WORD, FindCommand.COMMAND_HELP);
        commandList.put(FindTagCommand.COMMAND_WORD, FindTagCommand.COMMAND_HELP);
        commandList.put(FindEmailCommand.COMMAND_WORD, FindEmailCommand.COMMAND_HELP);
        commandList.put(ListCommand.COMMAND_WORD, ListCommand.COMMAND_HELP);
        commandList.put(HistoryCommand.COMMAND_WORD, HistoryCommand.COMMAND_HELP);
        commandList.put(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_HELP);
        commandList.put(UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_HELP);
        commandList.put(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_HELP);
        commandList.put(SendCommand.COMMAND_WORD, SendCommand.COMMAND_HELP);
        commandList.put(RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_HELP);
        commandList.put(SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_HELP);
        commandList.put(ChangeThemeCommand.COMMAND_WORD, ChangeThemeCommand.COMMAND_HELP);
        for (HashMap.Entry<String, String> entry : commandList.entrySet()) {
            if (isExact) {
                if (entry.getKey().equals(array[0])) {
                    result.add(entry.getKey());
                }
            } else {
                if (entry.getKey().contains(array[0])) {
                    result.add(entry.getValue());
                }
            }
        }
        return result;
    }

}
