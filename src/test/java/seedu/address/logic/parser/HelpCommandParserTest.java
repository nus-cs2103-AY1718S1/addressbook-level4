package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BinclearCommand;
import seedu.address.logic.commands.BindeleteCommand;
import seedu.address.logic.commands.BinrestoreCommand;
import seedu.address.logic.commands.BirthdayAddCommand;
import seedu.address.logic.commands.BirthdayRemoveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MapRouteCommand;
import seedu.address.logic.commands.MapShowCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleAddCommand;
import seedu.address.logic.commands.ScheduleRemoveCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SwitchThemeCommand;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagFindCommand;
import seedu.address.logic.commands.TagRemoveCommand;
import seedu.address.logic.commands.UndoCommand;

//@@author Pengyuz

public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parsesuccess() {
        assertParseSuccess(parser, AddCommand.COMMAND_WORD, new HelpCommand("add"));

        assertParseSuccess(parser, AddCommand.COMMAND_WORD_2, new HelpCommand("add"));

        assertParseSuccess(parser, AddCommand.COMMAND_WORD_3, new HelpCommand("add"));

        assertParseSuccess(parser, ClearCommand.COMMAND_WORD, new HelpCommand("clear"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD, new HelpCommand("delete"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD_2, new HelpCommand("delete"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD_3, new HelpCommand("delete"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD, new HelpCommand("edit"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD_2, new HelpCommand("edit"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD_3, new HelpCommand("edit"));

        assertParseSuccess(parser, ExitCommand.COMMAND_WORD, new HelpCommand("exit"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD, new HelpCommand("find"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD_2, new HelpCommand("find"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD_3, new HelpCommand("find"));

        assertParseSuccess(parser, HistoryCommand.COMMAND_WORD, new HelpCommand("history"));

        assertParseSuccess(parser, HistoryCommand.COMMAND_WORD_2, new HelpCommand("history"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD, new HelpCommand("list"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD_2, new HelpCommand("list"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD_3, new HelpCommand("list"));

        assertParseSuccess(parser, RedoCommand.COMMAND_WORD, new HelpCommand("redo"));

        assertParseSuccess(parser, SelectCommand.COMMAND_WORD, new HelpCommand("select"));

        assertParseSuccess(parser, SelectCommand.COMMAND_WORD_2, new HelpCommand("select"));

        assertParseSuccess(parser, SortCommand.COMMAND_WORD, new HelpCommand("sort"));

        assertParseSuccess(parser, TagAddCommand.COMMAND_WORD, new HelpCommand("tagadd"));

        assertParseSuccess(parser, TagRemoveCommand.COMMAND_WORD, new HelpCommand("tagremove"));

        assertParseSuccess(parser, TagFindCommand.COMMAND_WORD, new HelpCommand("tagfind"));

        assertParseSuccess(parser, BirthdayAddCommand.COMMAND_WORD, new HelpCommand("birthdayadd"));

        assertParseSuccess(parser, BirthdayRemoveCommand.COMMAND_WORD, new HelpCommand("birthdayremove"));

        assertParseSuccess(parser, MapShowCommand.COMMAND_WORD, new HelpCommand("mapshow"));

        assertParseSuccess(parser, MapRouteCommand.COMMAND_WORD, new HelpCommand("maproute"));

        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD, new HelpCommand("scheduleadd"));

        assertParseSuccess(parser, ScheduleRemoveCommand.COMMAND_WORD, new HelpCommand("scheduleremove"));

        assertParseSuccess(parser, BinclearCommand.COMMAND_WORD, new HelpCommand("bin-fresh"));

        assertParseSuccess(parser, BindeleteCommand.COMMAND_WORD, new HelpCommand("bin-delete"));

        assertParseSuccess(parser, BinrestoreCommand.COMMAND_WORD, new HelpCommand("bin-restore"));

        assertParseSuccess(parser, SwitchThemeCommand.COMMAND_WORD, new HelpCommand("theme"));

        assertParseSuccess(parser, ExportCommand.COMMAND_WORD, new HelpCommand("export"));

        assertParseSuccess(parser, UndoCommand.COMMAND_WORD, new HelpCommand("undo"));

    }

}
