package seedu.address.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.hints.Hint;
import seedu.address.logic.parser.HintParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

//@@author goweiwen
/**
 * Utility class for  parsing user input into autocompletions.
 */
public class Autocomplete {
    public static final ArrayList<String> COMMAND_LIST = new ArrayList<String>(Arrays.asList(
            "add", "alias", "clear", "delete", "edit", "exit", "find", "help", "history", "list",
            "music", "redo", "remark", "select", "unalias", "undo", "radio", "share"
    ));

    /**
     * Parses {@code String input} and returns an appropriate autocompletion
     */
    public static String autocomplete(String input) {
        String[] command;

        try {
            command = ParserUtil.parseCommandAndArguments(input);
        } catch (ParseException e) {
            return "";
        }

        String commandWord = command[0];
        String arguments = command[1];

        Hint hint = HintParser.generateHint(input, arguments, commandWord);
        hint.requireFieldsNonNull();
        return hint.autocomplete();
    }

    /**
     * Parses {@code String command} and returns the closest matching command word, or null if nothing
     * matches.
     */
    public static String autocompleteCommand(String command) {
        List<String> commands = new ArrayList<String>();
        // We add from COMMAND_LIST first because we want to autocomplete them first.
        commands.addAll(COMMAND_LIST);
        commands.addAll(UserPrefs.getInstance().getAliases().getAllAliases());
        String[] list = commands.toArray(new String[commands.size()]);
        return autocompleteFromList(command, list);
    }

    /**
     * Parses {@code String input} and returns the closest matching string in {@code String[] strings},
     * or null if nothing matches.
     */
    public static String autocompleteFromList(String input, String[] strings) {

        for (String string : strings) {
            if (string.startsWith(input)) {
                return string;
            }
        }
        return null;
    }
}
