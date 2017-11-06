package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_PROMPT_COMMAND;
import static seedu.address.commons.util.StringUtil.levenshteinDistance;
import static seedu.address.commons.util.StringUtil.tryParseInt;
import static seedu.address.logic.parser.CliSyntax.POSSIBLE_COMMAND_WORDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

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
     * or a {@code null} {@code String} otherwise.
     */
    public String getFormattedArgs(String closestCommand) {
        if (AddCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            // try to match arguments with ALL person models, otherwise return null (maybe use AddCommandParser?)
        } else if (EditCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            // try to match arguments with SOME person models, otherwise return null
        } else if (OpenCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || NewCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            // try test if dir exists, otherwise look for close matches?
        } else if (SelectCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)
                || DeleteCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            if (tryParseInt(arguments)) {
                return " " + arguments;
            }
        } else if (RemarkCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            // try test for index, return all arguments, checked for typos.
            String[] argArray = arguments.split(" ");
            if (argArray.length > 0 && tryParseInt(argArray[0])) {

                String retVal = arguments.replace(argArray[0], "").trim().replace(PREFIX_REMARK.toString(), "");
                // retVal = setToAmerican(retVal);
                return " " + argArray[0] + " " + PREFIX_REMARK + retVal;
            }
        } else if (FindCommand.COMMAND_WORD_ABBREVIATIONS.contains(closestCommand)) {
            // return all arguments as is. TODO: Check if args exist, move to constant.
            if (arguments.matches("^(?=\\s*\\S).*$")) {
                return " " + arguments;
            }
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
