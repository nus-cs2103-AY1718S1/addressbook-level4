package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SCHEDULE;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteScheduleCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.HomeCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TabCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TagsContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddAlias(person));
        assertEquals(new AddCommand(person), command);
    }
    //@@author

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }
    //@@author


    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }
    //@@author

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
    //@@author

    //@@author lincredibleJC
    @Test
    public void parseCommand_findtags() throws Exception {
        List<String> keywords = Arrays.asList("tag1", "tag2", "tag3");
        FindTagsCommand command = (FindTagsCommand) parser.parseCommand(
                FindTagsCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTagsCommand(new TagsContainsKeywordsPredicate(keywords)), command);
    }
    //@@author

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_findtags() throws Exception {
        List<String> keywords = Arrays.asList("tag1", "tag2", "tag3");
        FindTagsCommand command = (FindTagsCommand) parser.parseCommand(
                FindTagsCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTagsCommand(new TagsContainsKeywordsPredicate(keywords)), command);
    }
    //@@author

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
    //@@author

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }
    //@@author

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_alias_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author

    //@@author limcel
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }

    @Test
    public void parseCommand_alias_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_ALIAS) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_ALIAS + " 3") instanceof SortCommand);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-27 17:00:00"));
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_SCHEDULE
                        + "27 December 2018 at 5pm");
        assertEquals(new ScheduleCommand(INDEX_FIRST_PERSON, calendar), command);
    }

    @Test
    public void parseCommand_alias_schedule() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-25 10:00:00"));
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_SCHEDULE
                        + "25 December 2018 at 10am");
        assertEquals(new ScheduleCommand(INDEX_FIRST_PERSON, calendar), command);
    }

    @Test
    public void parseCommand_viewSchedule() throws Exception {
        assertTrue(parser.parseCommand(ViewScheduleCommand.COMMAND_WORD) instanceof ViewScheduleCommand);
        assertTrue(parser.parseCommand(ViewScheduleCommand.COMMAND_WORD + " 3") instanceof ViewScheduleCommand);
    }

    @Test
    public void parseCommand_alias_viewSchedule() throws Exception {
        assertTrue(parser.parseCommand(ViewScheduleCommand.COMMAND_ALIAS) instanceof ViewScheduleCommand);
        assertTrue(parser.parseCommand(ViewScheduleCommand.COMMAND_ALIAS + " 3") instanceof ViewScheduleCommand);
    }

    @Test
    public void parseCommand_deleteSchedule() throws Exception {
        DeleteScheduleCommand command = (DeleteScheduleCommand) parser.parseCommand(
                DeleteScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_SCHEDULE.getOneBased());
        assertEquals(new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE), command);
    }

    @Test
    public void parseCommand_alias_deleteSchedule() throws Exception {
        DeleteScheduleCommand command = (DeleteScheduleCommand) parser.parseCommand(
                DeleteScheduleCommand.COMMAND_ALIAS + " " + INDEX_FIRST_SCHEDULE.getOneBased());
        assertEquals(new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE), command);
    }
    //@@author

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("r 1") instanceof RedoCommand);
    }
    //@@author

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    //@@author lincredibleJC
    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("u 3") instanceof UndoCommand);
    }
    //@@author

    //@@author nahtanojmil
    @Test
    public void parseCommand_remarkCommandWord_returnsRemarkCommand() throws Exception {
        final Remark remarks = new Remark("I'm so done.");
        RemarkCommand testCommand = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remarks.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remarks), testCommand);
    }


    @Test
    public void parseCommand_remarkCommandAlias_returnsRemarkCommand() throws Exception {
        final Remark remarks = new Remark("I'm so done.");
        RemarkCommand testCommand = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remarks.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remarks), testCommand);
    }

    @Test
    public void parseCommand_tabCommandWord_returnsTabCommand() throws Exception {
        assertTrue(parser.parseCommand(TabCommand.COMMAND_WORD + " 1") instanceof TabCommand);
    }

    @Test
    public void parseCommand_homeCommandWord_returnsHomeCommand() throws Exception {
        assertTrue(parser.parseCommand("home") instanceof HomeCommand);
    }
    //@@author

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
