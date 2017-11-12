package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MusicCommand;
import seedu.address.logic.commands.RadioCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShareCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.hints.AddCommandHint;
import seedu.address.logic.commands.hints.AliasCommandHint;
import seedu.address.logic.commands.hints.ClearCommandHint;
import seedu.address.logic.commands.hints.CommandHint;
import seedu.address.logic.commands.hints.DeleteCommandHint;
import seedu.address.logic.commands.hints.EditCommandHint;
import seedu.address.logic.commands.hints.ExitCommandHint;
import seedu.address.logic.commands.hints.FindCommandHint;
import seedu.address.logic.commands.hints.HelpCommandHint;
import seedu.address.logic.commands.hints.Hint;
import seedu.address.logic.commands.hints.HistoryCommandHint;
import seedu.address.logic.commands.hints.ListCommandHint;
import seedu.address.logic.commands.hints.MusicCommandHint;
import seedu.address.logic.commands.hints.RadioCommandHint;
import seedu.address.logic.commands.hints.RedoCommandHint;
import seedu.address.logic.commands.hints.ShareCommandHint;
import seedu.address.logic.commands.hints.UnaliasCommandHint;
import seedu.address.logic.commands.hints.UndoCommandHint;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author nicholaschuayunzhi
/**
 * Class that is responsible for generating hints based on user input
 * Contains one public method generateHint which returns an appropriate hint based on input
 */
public class HintParser {

    /**
     * Parses {@code String input} and returns an appropriate hint
     */
    public static String generateHint(String input) {
        String[] command;

        try {
            command = ParserUtil.parseCommandAndArguments(input);
        } catch (ParseException e) {
            return " type help for guide";
        }

        String userInput = input;
        String commandWord = command[0];
        String arguments = command[1];
        Hint hint = generateParsedHint(userInput, arguments, commandWord);
        if (hint == null) {
            return " type help for user guide";
        }

        return hint.getArgumentHint() + hint.getDescription();
    }

    /**
     * returns an appropriate hint based on commandWord and arguments
     * userInput and arguments are referenced to decide whether whitespace should be added to
     * the front of the hint
     */
    private static Hint generateHint(String userInput, String arguments, String commandWord) {

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandHint(userInput, arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandHint(userInput, arguments);
        case FindCommand.COMMAND_WORD:
            return new FindCommandHint(userInput, arguments);
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandHint(userInput, arguments);
        case ShareCommand.COMMAND_WORD:
            return new ShareCommandHint(userInput, arguments);
        case ClearCommand.COMMAND_WORD:
            return new ClearCommandHint(userInput);
        case ListCommand.COMMAND_WORD:
            return new ListCommandHint(userInput);
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommandHint(userInput);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommandHint(userInput);
        case UndoCommand.COMMAND_WORD:
            return new UndoCommandHint(userInput);
        case RedoCommand.COMMAND_WORD:
            return new RedoCommandHint(userInput);
        case HelpCommand.COMMAND_WORD:
            return new HelpCommandHint(userInput);
        case MusicCommand.COMMAND_WORD:
            return new MusicCommandHint(userInput, arguments);
        case RadioCommand.COMMAND_WORD:
            return new RadioCommandHint(userInput, arguments);
        case AliasCommand.COMMAND_WORD:
            return new AliasCommandHint(userInput, arguments);
        case UnaliasCommand.COMMAND_WORD:
            return new UnaliasCommandHint(userInput, arguments);
        default:
            return new CommandHint(userInput, commandWord);
        }
    }

    /**
     * returns a parsedHint if generated hint is non null, else return null
     */
    public static Hint generateParsedHint(String userInput, String arguments, String commandWord) {
        Hint generatedHint = generateHint(userInput, arguments, commandWord);
        if (generatedHint != null) {
            generatedHint.parse();
        }
        return generatedHint;
    }
}
