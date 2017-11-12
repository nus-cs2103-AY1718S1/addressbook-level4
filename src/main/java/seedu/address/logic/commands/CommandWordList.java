package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;


//@@author newalter
/**
 * This class stores a list of all command words.
 */
public class CommandWordList {

    public static final List<String> COMMAND_WORD_LIST = Arrays.asList(AddCommand.COMMAND_WORD,
            AddTagCommand.COMMAND_WORD, AddMeetingCommand.COMMAND_WORD, BackupCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, DeleteTagCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            LoginCommand.COMMAND_WORD, NoteCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
            ResizeCommand.COMMAND_WORD, RestoreBackupCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
            SortCommand.COMMAND_WORD, SyncCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
            DeleteMeetingCommand.COMMAND_WORD, LogoutCommand.COMMAND_WORD);
}
