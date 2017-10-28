package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class holds collections of commands for the Addressbook
 */
public class CommandCollection {

    //@@author grantcm
    private static Set<String> commandSet = Stream.of(
        AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD, RemarkCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD, GroupCommand.COMMAND_WORD, FilterGroupCommand.COMMAND_WORD
    ).collect(Collectors.toSet());

    private static Map<String, String> commandMap;
    static {
        commandMap = new HashMap<String, String> ();
        commandMap.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_PARAMETERS);
        commandMap.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_PARAMETERS);
        commandMap.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_PARAMETERS);
        commandMap.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_PARAMETERS);
        commandMap.put(RemarkCommand.COMMAND_WORD, RemarkCommand.MESSAGE_PARAMETERS);
        commandMap.put(SelectCommand.COMMAND_WORD, SelectCommand.MESSAGE_PARAMETERS);
        commandMap.put(GroupCommand.COMMAND_WORD, GroupCommand.MESSAGE_PARAMETERS);
        commandMap.put(FilterGroupCommand.COMMAND_WORD, FilterGroupCommand.MESSAGE_PARAMETERS);
    }

    public CommandCollection (){}

    public Map<String, String> getCommandMap() {
        return commandMap;
    }

    public Set<String> getCommandSet() {
        return commandSet;
    }

    //@@author
}
