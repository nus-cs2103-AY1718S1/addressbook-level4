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
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
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
     * Logic editted: User can type abbreviated and case-insensitive commands. Comment to check travis test.
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
        if (commandWord.equalsIgnoreCase(AddCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(AddCommand.COMMAND_WORDVAR_2)) {
            return new AddCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(EditCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(EditCommand.COMMAND_WORDVAR_2)) {
            return new EditCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(SelectCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(SelectCommand.COMMAND_WORDVAR_2)) {
            return new SelectCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(DeleteCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(DeleteCommand.COMMAND_WORDVAR_2)) {
            return new DeleteCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(ClearCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(ClearCommand.COMMAND_WORDVAR_2)) {
            return new ClearCommand();
        } else if (commandWord.equalsIgnoreCase(FindCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(FindCommand.COMMAND_WORDVAR_2)) {
            return new FindCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(ListCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(ListCommand.COMMAND_WORDVAR_2)) {
            return new ListCommand();

        } else if (commandWord.equalsIgnoreCase(HistoryCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(HistoryCommand.COMMAND_WORDVAR_2)) {
            return new HistoryCommand();

        } else if (commandWord.equalsIgnoreCase(ExitCommand.COMMAND_WORD)) {
            return new ExitCommand();

        } else if (commandWord.equalsIgnoreCase(HelpCommand.COMMAND_WORD)) {
            return new HelpCommand();

        } else if (commandWord.equalsIgnoreCase(UndoCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(UndoCommand.COMMAND_WORDVAR_2)) {
            return new UndoCommand();

        } else if (commandWord.equalsIgnoreCase(RedoCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(RedoCommand.COMMAND_WORDVAR_2)) {
            return new RedoCommand();

        } else if (commandWord.equalsIgnoreCase(SortCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(SortCommand.COMMAND_WORDVAR_2)) {
            return new SortCommand();
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
