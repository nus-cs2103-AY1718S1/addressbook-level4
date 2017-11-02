package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BanCommand;
import seedu.address.logic.commands.BlacklistCommand;
import seedu.address.logic.commands.BorrowCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.NearbyCommand;
import seedu.address.logic.commands.PaybackCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RepaidCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnbanCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WhitelistCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Debt;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
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

    @Test
    public void parseCommand_ban() throws Exception {
        BanCommand command = (BanCommand) parser.parseCommand(
                BanCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new BanCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unban() throws Exception {
        UnbanCommand command = (UnbanCommand) parser.parseCommand(
                UnbanCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnbanCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_borrow() throws Exception {
        String toBorrow = "5000";
        Debt debtAmount = new Debt(toBorrow);
        BorrowCommand expectedBorrowCommand = new BorrowCommand(INDEX_FIRST_PERSON, debtAmount);
        BorrowCommand borrowCommand = (BorrowCommand) parser.parseCommand(BorrowCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + toBorrow);
        assertEquals(expectedBorrowCommand, borrowCommand);
    }

    @Test
    public void parseCommand_payback() throws Exception {
        String toPayback = "500";
        Debt debtAmount = new Debt(toPayback);
        PaybackCommand expectedPaybackCommand = new PaybackCommand(INDEX_FIRST_PERSON, debtAmount);
        PaybackCommand paybackCommand = (PaybackCommand) parser.parseCommand(PaybackCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + toPayback);
        assertEquals(expectedPaybackCommand, paybackCommand);
    }

    @Test
    public void parseCommand_repaid() throws Exception {
        RepaidCommand command = (RepaidCommand) parser.parseCommand(
                RepaidCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RepaidCommand(INDEX_FIRST_PERSON), command);
    }


    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_logout() throws Exception {
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD) instanceof LogoutCommand);
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD + " 3") instanceof LogoutCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(keywords), command);
    }

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

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " name") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " debt") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " cluster") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " deadline") instanceof SortCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_blacklist() throws Exception {
        assertTrue(parser.parseCommand(BlacklistCommand.COMMAND_WORD) instanceof BlacklistCommand);
        assertTrue(parser.parseCommand(BlacklistCommand.COMMAND_WORD + " 3") instanceof BlacklistCommand);
    }

    @Test
    public void parseCommand_whitelist() throws Exception {
        assertTrue(parser.parseCommand(WhitelistCommand.COMMAND_WORD) instanceof WhitelistCommand);
        assertTrue(parser.parseCommand(WhitelistCommand.COMMAND_WORD + " 3") instanceof WhitelistCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_nearby() throws Exception {
        NearbyCommand command = (NearbyCommand) parser.parseCommand(
                NearbyCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new NearbyCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_login() throws Exception {
        LoginCommand command = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_WORD + " " + "JohnDoe" + " " + "hiIAmJohnDoe123");
        assertEquals(new LoginCommand(new Username("JohnDoe"), new Password("hiIAmJohnDoe123")), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
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

    //@@author jelneo
    @Test
    public void parseCommand_invalidLogin() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        parser.parseCommand(LoginCommand.COMMAND_WORD);
    }
}
