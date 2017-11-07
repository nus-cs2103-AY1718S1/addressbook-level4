package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_PROMPT_COMMAND;
import static seedu.address.commons.util.StringUtil.levenshteinDistance;
import static seedu.address.logic.parser.CliSyntax.POSSIBLE_COMMAND_WORDS;
import static seedu.address.logic.parser.ParserUtil.parseFirstFilePath;
import static seedu.address.logic.parser.ParserUtil.parseFirstInt;
import static seedu.address.logic.parser.ParserUtil.tryParseFilePath;
import static seedu.address.logic.parser.ParserUtil.tryParseInt;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.RemarkCommandParser;

/**
 * Suggests another command that could be used for execution.
 */
public class Suggestion {

    public static final String COMMAND_WORD = "y";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "yes", "ok", "yea", "yeah"));

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
            // TODO: v1.5 try to match arguments with ALL person models, otherwise return null

        // Custom parser for EditCommand.
        } else if (EditCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            // TODO: v1.5 try to match arguments with SOME person models, otherwise return null

        // Custom parser for RemarkCommand.
        } else if (RemarkCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            return RemarkCommandParser.parseArguments(commandWord, arguments);

        // Custom parser for FindCommand.
        } else if (FindCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            return FindCommandParser.parseArguments(arguments);

        // Commands with directory-type arguments.
        } else if (OpenCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || NewCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            if (tryParseFilePath(arguments)) {
                return " " + parseFirstFilePath(arguments);
            }

        // Commands with simple index-type arguments.
        } else if (SelectCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || DeleteCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            if (tryParseInt(arguments)) {
                return " " + Integer.toString(parseFirstInt(arguments));
            } else if (tryParseInt(commandWord)) {
                return " " + Integer.toString(parseFirstInt(commandWord));
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

    private String setToAmerican(String input) {
        String output = input;
        JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
        try {
            List<RuleMatch> matches = langTool.check(output);
            while (!matches.isEmpty()) {
                RuleMatch match = matches.iterator().next();
                output = output.substring(0, match.getFromPos())
                        + match.getSuggestedReplacements().iterator().next()
                        + output.substring(match.getToPos());
                matches = langTool.check(output);
            }
        } catch (IOException e) {
            return null;
        }
        return output;
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
