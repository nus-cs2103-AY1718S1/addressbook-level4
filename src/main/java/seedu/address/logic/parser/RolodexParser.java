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
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StarWarsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RolodexParser {

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        if (AddCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new AddCommandParser().parse(arguments);
        } else if (EditCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (SelectCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (DeleteCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (ClearCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new ClearCommand();
        } else if (FindCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (ListCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new ListCommandParser().parse(arguments);
        } else if (HistoryCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new HistoryCommand();
        } else if (ExitCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new ExitCommand();
        } else if (HelpCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new HelpCommand();
        } else if (UndoCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new UndoCommand();
        } else if (RedoCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new RedoCommand();
        } else if (OpenCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new OpenCommandParser().parse(arguments);
        } else if (NewCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new NewCommandParser().parse(arguments);
        } else if (RemarkCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new RemarkCommandParser().parse(arguments);
        }  else if (StarWarsCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new StarWarsCommand();
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
