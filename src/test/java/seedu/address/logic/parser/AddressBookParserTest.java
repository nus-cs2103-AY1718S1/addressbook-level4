package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_PARENT_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.AliasTokenBuilder.DEFAULT_KEYWORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DisableParentModeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ParentModeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.alias.AliasCommand;
import seedu.address.logic.commands.alias.UnaliasCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.FindPinnedCommand;
import seedu.address.logic.commands.person.HideCommand;
import seedu.address.logic.commands.person.ListAliasCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.person.ListPinCommand;
import seedu.address.logic.commands.person.PinCommand;
import seedu.address.logic.commands.person.RemarkCommand;
import seedu.address.logic.commands.person.SelectCommand;
import seedu.address.logic.commands.person.SortCommand;
import seedu.address.logic.commands.person.UnpinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.AliasToken;
import seedu.address.model.alias.Keyword;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonHasKeywordsPredicate;
import seedu.address.model.person.Remark;
import seedu.address.testutil.AliasTokenBuilder;
import seedu.address.testutil.AliasTokenUtil;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Before
    public void setParentMode() {
        parser.enableParentToggle();
    }

    @Test
    public void parseCommandAdd() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommandDisableParentMode() throws Exception {
        assertTrue(parser.parseCommand(DisableParentModeCommand.COMMAND_WORD) instanceof DisableParentModeCommand);
        assertTrue(parser.parseCommand(DisableParentModeCommand.COMMAND_WORD + " 8")
                instanceof DisableParentModeCommand);
    }

    @Test
    public void parseCommandClear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommandDelete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandHide() throws Exception {
        HideCommand command = (HideCommand) parser.parseCommand(
                HideCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new HideCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandPin() throws Exception {
        PinCommand command = (PinCommand) parser.parseCommand(
                PinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PinCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandParentMode() throws Exception {
        assertTrue(parser.parseCommand(ParentModeCommand.COMMAND_WORD) instanceof ParentModeCommand);
        assertTrue(parser.parseCommand(ParentModeCommand.COMMAND_WORD + " 3") instanceof ParentModeCommand);
    }

    @Test
    public void parseCommandUnpin() throws Exception {
        UnpinCommand command = (UnpinCommand) parser.parseCommand(
                UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnpinCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandListPin() throws Exception {
        assertTrue(parser.parseCommand(ListPinCommand.COMMAND_WORD) instanceof ListPinCommand);
        assertTrue(parser.parseCommand(ListPinCommand.COMMAND_WORD + " 3") instanceof ListPinCommand);
    }

    @Test
    public void parseCommandListAlias() throws Exception {
        assertTrue(parser.parseCommand(ListAliasCommand.COMMAND_WORD) instanceof ListAliasCommand);
        assertTrue(parser.parseCommand(ListAliasCommand.COMMAND_WORD + " 3") instanceof ListAliasCommand);
    }

    @Test
    public void parseCommandEdit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommandExit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommandFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonHasKeywordsPredicate(keywords, false)), command);
    }

    @Test
    public void parseCommandFindPinned() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPinnedCommand command = (FindPinnedCommand) parser.parseCommand(
                FindPinnedCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPinnedCommand(new PersonHasKeywordsPredicate(keywords, true)), command);
    }

    @Test
    public void parseCommandRemark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }


    @Test
    public void parseCommandSort() throws Exception {
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + "name");
        assertEquals(new SortCommand("name"), command);
    }

    @Test
    public void parseCommandHelp() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommandHistory() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_PARENT_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommandList() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommandSelect() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAlias() throws Exception {
        AliasToken aliasToken = new AliasTokenBuilder().build();
        AliasCommand command = (AliasCommand) parser.parseCommand(AliasTokenUtil.getAliasCommand(aliasToken));
        assertEquals(new AliasCommand(aliasToken), command);
    }

    @Test
    public void parseCommandUnalias() throws Exception {
        UnaliasCommand command = (UnaliasCommand) parser.parseCommand(
                UnaliasCommand.COMMAND_WORD + " " + "k/" + DEFAULT_KEYWORD);
        assertEquals(new UnaliasCommand(new Keyword(DEFAULT_KEYWORD)), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommandUndoCommandWordReturnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommandUnrecognisedInputThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommandUnknownCommandThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_PARENT_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
