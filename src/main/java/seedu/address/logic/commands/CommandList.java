//@@author A0143832J
package seedu.address.logic.commands;

import java.util.ArrayList;

/**
 * Compile a list of command words and return an array list of {@COMMAND_WORD}
 */
public class CommandList {
    private final ArrayList<String> commandList = new ArrayList<>();

    public CommandList() {
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(DeleteTagCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(FavoriteCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(ListAlphabetCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(RemarkCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(GroupingCommand.COMMAND_WORD);
        commandList.add(ListGroupsCommand.COMMAND_WORD);
        commandList.add(DeleteGroupCommand.COMMAND_WORD);
        commandList.add(ViewGroupCommand.COMMAND_WORD);
        commandList.add(EditGroupCommand.COMMAND_WORD);
    }

    public ArrayList<String> getCommandList() {
        return this.commandList;
    }
}
//@@author
