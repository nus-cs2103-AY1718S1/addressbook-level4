package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_PROMPT_COMMAND;
import static seedu.address.commons.util.StringUtil.levenshteinDistance;
import static seedu.address.logic.parser.CliSyntax.POSSIBLE_COMMAND_WORDS;
import static seedu.address.logic.parser.ParserUtil.isParsableFilePath;
import static seedu.address.logic.parser.ParserUtil.isParsableIndex;
import static seedu.address.logic.parser.ParserUtil.parseFirstFilePath;
import static seedu.address.logic.parser.ParserUtil.parseFirstIndex;
import static seedu.address.model.ModelManager.getLastRolodexSize;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.EmailCommandParser;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.RemarkCommandParser;

/**
 * Suggests another command that could be used for execution.
 */
public class Suggestion {

    public static final String COMMAND_WORD = "y";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "yes", "k", "ok", "yea", "yeah"));

    private static final int COMMAND_TYPO_TOLERANCE = 3;

    private final String commandWord;
    private final String arguments;

    public Suggestion(String commandWord, String arguments) {
        this.commandWord = commandWord.trim();
        this.arguments = arguments.trim();
    }

    /**
     * Returns an instructional message that suggests the actual command to be executed.
     */
    public String getPromptMessage() {
        return String.format(MESSAGE_PROMPT_COMMAND, getCommandString());
    }

    /**
     * Returns a formatted command string in the format
     * in which users would typically enter into the command box.
     */
    public String getCommandString() {
        String closestCommand = getClosestCommandWord();
        return closestCommand + getFormattedArgs(closestCommand);
    }

    /**
     * Returns {@code true} if the arguments can be converted
     * into proper arguments for the given {@code closestCommand}.
     */
    private boolean isFormattableArgs(String closestCommand) {
        String formattedArgs = getFormattedArgs(closestCommand);
        return closestCommand != null
                && formattedArgs != null;
    }

    /**
     * Returns the formatted arguments given a {@code closestCommand}
     * or a {@code null} {@code String} if not formattable.
     */
    public String getFormattedArgs(String closestCommand) {
        // Custom parser for AddCommand.
        if (AddCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            return AddCommandParser.parseArguments(arguments);

        // Custom parser for EditCommand.
        } else if (EditCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            return EditCommandParser.parseArguments(commandWord, arguments);

        // Custom parser for EmailCommand.
        } else if (EmailCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            return EmailCommandParser.parseArguments(commandWord, arguments);

        // Custom parser for RemarkCommand.
        } else if (RemarkCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            return RemarkCommandParser.parseArguments(commandWord, arguments);

        // Custom parser for FindCommand.
        } else if (FindCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            return FindCommandParser.parseArguments(arguments);

        // Commands with directory-type arguments.
        } else if (OpenRolodexCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || NewRolodexCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            if (isParsableFilePath(arguments)) {
                return " " + parseFirstFilePath(arguments);
            }

        // Commands with simple index-type arguments.
        } else if (SelectCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || DeleteCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            if (isParsableIndex(arguments, getLastRolodexSize())) {
                return " " + Integer.toString(parseFirstIndex(arguments, getLastRolodexSize()));
            } else if (isParsableIndex(commandWord, getLastRolodexSize())) {
                return " " + Integer.toString(parseFirstIndex(commandWord, getLastRolodexSize()));
            }

        // Commands with no arguments.
        } else if (ClearCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || ListCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || HistoryCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || ExitCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || HelpCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || UndoCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || RedoCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            return "";
        }
        return null;
    }

    /**
     * Returns the command word closest to the entered command
     * (in terms of levenshteinDistance).
     *
     * Returns a {@code null} {@code String} if no possible
     * command word exists within the tolerance level specified
     * by {@code COMMAND_TYPO_TOLERANCE}.
     */
    public String getClosestCommandWord() {
        int min = COMMAND_TYPO_TOLERANCE;
        String word = null;
        for (String possibleCommand : POSSIBLE_COMMAND_WORDS) {
            int distance = levenshteinDistance(commandWord, possibleCommand);
            if (distance < min) {
                min = distance;
                word = possibleCommand;
            }
        }
        return word;
    }

    /**
     * Returns {@code true} if the {@code commandWord} and
     * {@code arguments} can be successfully converted
     * into a {@code Command}, {@code false} otherwise.
     */
    public boolean isSuggestible() {
        String closestCommand = getClosestCommandWord();
        return closestCommand != null
                && isFormattableArgs(closestCommand);
    }
}
