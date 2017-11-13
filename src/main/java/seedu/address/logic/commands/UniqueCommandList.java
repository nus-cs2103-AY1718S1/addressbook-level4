package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.TreeSet;

//@@author CindyTsai1
/**
 * A list of all command words, including alias and secondary word
 */
public class UniqueCommandList {
    private static ArrayList<String> commandList;
    private static TreeSet<String> possibleCommandList;

    public static ArrayList<String> getCommandList() {
        commandList = new ArrayList<>();
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(AddCommand.COMMAND_ALIAS);
        commandList.add(AddCommand.COMMAND_SECONDARY);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_ALIAS);
        commandList.add(ClearCommand.COMMAND_SECONDARY);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_ALIAS);
        commandList.add(DeleteCommand.COMMAND_SECONDARY);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_ALIAS);
        commandList.add(EditCommand.COMMAND_SECONDARY_ONE);
        commandList.add(EditCommand.COMMAND_SECONDARY_TWO);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_ALIAS);
        commandList.add(ExitCommand.COMMAND_SECONDARY_ONE);
        commandList.add(ExitCommand.COMMAND_SECONDARY_TWO);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_ALIAS);
        commandList.add(FindCommand.COMMAND_SECONDARY);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_ALIAS);
        commandList.add(HistoryCommand.COMMAND_SECONDARY);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_ALIAS);
        commandList.add(ListCommand.COMMAND_SECONDARY_ONE);
        commandList.add(ListCommand.COMMAND_SECONDARY_TWO);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_ALIAS);
        commandList.add(RemarkCommand.COMMAND_WORD);
        commandList.add(RemarkCommand.COMMAND_ALIAS);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_ALIAS);
        commandList.add(SelectCommand.COMMAND_SECONDARY);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_ALIAS);
        commandList.add(GMapsCommand.COMMAND_WORD);
        commandList.add(GMapsCommand.COMMAND_ALIAS);
        commandList.add(GMapsCommand.COMMAND_SECONDARY_ONE);
        commandList.add(GMapsCommand.COMMAND_SECONDARY_TWO);
        commandList.add(PhotoCommand.COMMAND_WORD);
        commandList.add(PhotoCommand.COMMAND_ALIAS);
        commandList.add(ThemeCommand.COMMAND_WORD);
        commandList.add(ThemeCommand.COMMAND_ALIAS);
        commandList.add(ThemeCommand.COMMAND_SECONDARY);
        commandList.add(TimetableCommand.COMMAND_WORD);
        commandList.add(TimetableCommand.COMMAND_ALIAS);
        commandList.add(TimetableCommand.COMMAND_SECONDARY);
        return commandList;
    }

    public static TreeSet<String> getPossibleCommandList(String command) {
        possibleCommandList = new TreeSet<>();

        // Swapping i with i+1
        for (int i = 1; i < command.length() - 1; i++) {
            possibleCommandList.add(command.substring(0, i) + command.charAt(i + 1)
                    + command.charAt(i) + command.substring(i + 2));
        }

        // deleting one char, skipping i
        for (int i = 0; i < command.length(); i++) {
            possibleCommandList.add(command.substring(0, i) + command.substring(i + 1));
        }

        // inserting one char
        for (int i = 0; i < command.length() + 1; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                possibleCommandList.add(command.substring(0, i) + j + command.substring(i));
                // replacing one char
                if (i < command.length()) {
                    possibleCommandList.add(command.substring(0, i) + j + command.substring(i + 1));
                }
            }
        }
        return possibleCommandList;
    }
}
