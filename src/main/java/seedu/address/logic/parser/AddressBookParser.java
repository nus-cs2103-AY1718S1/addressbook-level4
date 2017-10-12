package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PartialFindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Enumerator list to define the types of commands.
     */
    private enum CommandType {
        ADD, CLEAR, DEL, EDIT, EXIT, FIND, PFIND, HELP, HISTORY, LIST, REDO, UNDO, SELECT, NONE
    }

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

        CommandType commandType = getCommandType(commandWord.toLowerCase());

        switch (commandType) {

        case ADD:
            return new AddCommandParser().parse(arguments);

        case EDIT:
            return new EditCommandParser().parse(arguments);

        case SELECT:
            return new SelectCommandParser().parse(arguments);

        case DEL:
            return new DeleteCommandParser().parse(arguments);

        case CLEAR:
            return new ClearCommand();

        case FIND:
            return new FindCommandParser().parse(arguments);

        case PFIND:
            return new PartialFindCommandParser().parse(arguments);

        case LIST:
            return new ListCommand();

        case HISTORY:
            return new HistoryCommand();

        case EXIT:
            return new ExitCommand();

        case HELP:
            return new HelpCommand();

        case UNDO:
            return new UndoCommand();

        case REDO:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Searches the entire list of acceptable command words in each command and returns the enumerated value type.
     * @param commandWord
     * @return enumerated value for the switch statement to process
     */

    private CommandType getCommandType(String commandWord) {
        for (String word : AddCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.ADD;
            }
        }
        for (String word : ClearCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.CLEAR;
            }
        }
        for (String word : DeleteCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.DEL;
            }
        }
        for (String word : EditCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.EDIT;
            }
        }
        for (String word : ExitCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.EXIT;
            }
        }
        for (String word : FindCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.FIND;
            }
        }
        for (String word : PartialFindCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.PFIND;
            }
        }
        for (String word : HelpCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.HELP;
            }
        }
        for (String word : HistoryCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.HISTORY;
            }
        }
        for (String word : ListCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.LIST;
            }
        }
        for (String word : RedoCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.REDO;
            }
        }
        for (String word : SelectCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.SELECT;
            }
        }
        for (String word : UndoCommand.COMMAND_WORDS) {
            if (commandWord.contentEquals(word)) {
                return CommandType.UNDO;
            }
        }
        return CommandType.NONE;
    }


}
