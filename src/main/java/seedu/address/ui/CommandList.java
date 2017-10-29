package seedu.address.ui;

import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.persons.AddCommand;
import seedu.address.logic.commands.persons.DeleteCommand;
import seedu.address.logic.commands.persons.EditCommand;
import seedu.address.logic.commands.persons.FindCommand;
import seedu.address.logic.commands.persons.ListCommand;
import seedu.address.logic.commands.persons.SelectCommand;
import seedu.address.logic.commands.tags.DetagCommand;
import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.logic.parser.CliSyntax;

/**
 * Contains a list of Command Line Interface (CLI) command words.
 */
public class CommandList {

    public static final String[] COMMANDS = {
        ChangeModeCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TM_MODE.getPrefix(),
        ChangeModeCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_AB_MODE.getPrefix(),
        AddCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_NAME,
        AddTaskCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        DetagCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        HistoryCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD
    };
}
