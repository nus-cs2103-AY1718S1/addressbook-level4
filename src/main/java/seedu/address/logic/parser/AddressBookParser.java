package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddLifeInsuranceCommand;
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
import seedu.address.logic.commands.PrintCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WhyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    //@@author Juxarius
    /**
     * Enumerator list to define the types of commands.
     */
    private enum CommandType {
        ADD,
        ADDLI,
        CLEAR,
        DEL,
        EDIT,
        EXIT,
        FIND,
        PFIND,
        HELP,
        HISTORY,
        LIST,
        PRINT,
        REDO,
        UNDO,
        SELECT,
        WHY,
        NONE
    }

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static HashMap<String, CommandType> commandWordMap = new HashMap<>();

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

        case ADDLI:
            return new AddLifeInsuranceCommandParser().parse(arguments);

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

        case PRINT:
            return new PrintCommandParser().parse(arguments);

        case WHY:
            return new WhyCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Set up a map for O(1) command word to commandType access
     */
    private void setUpCommandWordMap() {
        Arrays.stream(AddCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.ADD));
        Arrays.stream(AddLifeInsuranceCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.ADDLI));
        Arrays.stream(ClearCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.CLEAR));
        Arrays.stream(DeleteCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.DEL));
        Arrays.stream(EditCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.EDIT));
        Arrays.stream(ExitCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.EXIT));
        Arrays.stream(FindCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.FIND));
        Arrays.stream(PartialFindCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.PFIND));
        Arrays.stream(HelpCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.HELP));
        Arrays.stream(HistoryCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.HISTORY));
        Arrays.stream(ListCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.LIST));
        Arrays.stream(PrintCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.PRINT));
        Arrays.stream(RedoCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.REDO));
        Arrays.stream(UndoCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.UNDO));
        Arrays.stream(SelectCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.SELECT));
        Arrays.stream(WhyCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.WHY));
    }


    /**
     * Searches the map for the commandWord
     * @param commandWord
     * @return enumerated value for the switch statement to process
     */

    private CommandType getCommandType(String commandWord) {
        if (commandWordMap.isEmpty()) {
            setUpCommandWordMap();
        }
        return commandWordMap.getOrDefault(commandWord, CommandType.NONE);
    }
    //@@author

}
