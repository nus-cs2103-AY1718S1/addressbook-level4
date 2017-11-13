package seedu.address.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.AliasSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.OrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewAliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EveUtil;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class GeneralBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UserPrefs userPrefs = new UserPrefs();
    private final GeneralBookParser parser = new GeneralBookParser(userPrefs);
    private AliasSettings aliasSettings = userPrefs.getAliasSettings();

    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    public AliasSettings getAliasSettings() {
        return aliasSettings;
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().withGroup("").build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        Assert.assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(aliasSettings.getClearCommand().getAlias()) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        assertTrue(parser.parseCommand(aliasSettings.getClearCommand().getAlias() + " 3")
                instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        Assert.assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);

        //alias
        command = (DeleteCommand) parser.parseCommand(
                aliasSettings.getDeleteCommand().getAlias() + " " + INDEX_FIRST_PERSON.getOneBased());
        Assert.assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        Assert.assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);

        //alias
        command = (EditCommand) parser.parseCommand(aliasSettings.getEditCommand().getAlias() + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        Assert.assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);

        //alias
        assertTrue(parser.parseCommand(aliasSettings.getExitCommand().getAlias()) instanceof ExitCommand);
        assertTrue(parser.parseCommand(aliasSettings.getExitCommand().getAlias() + " 3")
                instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/" + keywords.stream().collect(Collectors.joining(" ")));
        Assert.assertEquals(new FindCommand(new ContainsKeywordsPredicate(keywords)), command);

        //alias
        command = (FindCommand) parser.parseCommand(
                aliasSettings.getFindCommand().getAlias() + " n/" + keywords.stream().collect(
                        Collectors.joining(" ")));
        Assert.assertEquals(new FindCommand(new ContainsKeywordsPredicate(keywords)), command);
    }

    //@@author tingtx
    @Test
    public void parseCommand_group() throws Exception {
        final String group = "TEST";
        final List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        GroupCommand command = (GroupCommand) parser.parseCommand(GroupCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_GROUP + group);
        Assert.assertEquals(new GroupCommand(indexes, group), command);

        //alias
        command = (GroupCommand) parser.parseCommand(aliasSettings.getGroupCommand().getAlias()
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_GROUP + group);
        Assert.assertEquals(new GroupCommand(indexes, group), command);
    }
    //@@author

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);

        //alias
        assertTrue(parser.parseCommand(aliasSettings.getHelpCommand().getAlias()) instanceof HelpCommand);
        assertTrue(parser.parseCommand(aliasSettings.getHelpCommand().getAlias() + " 3")
                instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        //alias
        assertTrue(parser.parseCommand(aliasSettings.getHistoryCommand().getAlias()) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(aliasSettings.getHistoryCommand().getAlias() + " 3")
                instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            Assert.fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            Assert.assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " g/TEST") instanceof ListCommand);

        //alias
        assertTrue(parser.parseCommand(aliasSettings.getListCommand().getAlias()) instanceof ListCommand);
        assertTrue(parser.parseCommand(aliasSettings.getListCommand().getAlias() + " g/TEST")
                instanceof ListCommand);
    }

    //@@author tingtx
    @Test
    public void parseCommand_order() throws Exception {
        final String parameter = "NAME";
        OrderCommand command = (OrderCommand) parser.parseCommand(OrderCommand.COMMAND_WORD + " "
                + parameter);
        Assert.assertEquals(new OrderCommand(parameter), command);

        //alias
        command = (OrderCommand) parser.parseCommand(aliasSettings.getOrderCommand().getAlias()
                + " " + parameter);
        Assert.assertEquals(new OrderCommand(parameter), command);
    }
    //@@author

    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        Assert.assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);

        //alias
        command = (RemarkCommand) parser.parseCommand(aliasSettings.getRemarkCommand().getAlias() + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        Assert.assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_viewalias() throws Exception {
        assertTrue(parser.parseCommand(ViewAliasCommand.COMMAND_WORD) instanceof ViewAliasCommand);
        assertTrue(parser.parseCommand(ViewAliasCommand.COMMAND_WORD + " 3") instanceof ViewAliasCommand);

        //alias
        assertTrue(parser.parseCommand(aliasSettings.getViewAliasCommand().getAlias()) instanceof ViewAliasCommand);
        assertTrue(parser.parseCommand(aliasSettings.getViewAliasCommand().getAlias() + " 3")
                instanceof ViewAliasCommand);
    }


    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        Assert.assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);

        //alias
        command = (SelectCommand) parser.parseCommand(
                aliasSettings.getSelectCommand().getAlias() + " " + INDEX_FIRST_PERSON.getOneBased());
        Assert.assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 1") instanceof RedoCommand);

        //alias
        assertTrue(parser.parseCommand(aliasSettings.getRedoCommand().getAlias()) instanceof RedoCommand);
        assertTrue(parser
                .parseCommand(aliasSettings.getRedoCommand().getAlias() + " 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);

        //alias
        assertTrue(parser.parseCommand(aliasSettings.getUndoCommand().getAlias()) instanceof UndoCommand);
        assertTrue(parser
                .parseCommand(aliasSettings.getUndoCommand().getAlias() + " 3") instanceof UndoCommand);
    }

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

    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EveUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_EVENT), command);

        //alias
        command = (DeleteEventCommand) parser.parseCommand(
                aliasSettings.getDeleteEventCommand().getAlias() + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EVENT.getOneBased() + " " + EveUtil.getEventDetails(event));
        assertEquals(new EditEventCommand(INDEX_FIRST_EVENT, descriptor), command);

        //alias
        command = (EditEventCommand) parser.parseCommand(aliasSettings.getEditEventCommand().getAlias() + " "
                + INDEX_FIRST_EVENT.getOneBased() + " " + EveUtil.getEventDetails(event));
        assertEquals(new EditEventCommand(INDEX_FIRST_EVENT, descriptor), command);
    }
}
