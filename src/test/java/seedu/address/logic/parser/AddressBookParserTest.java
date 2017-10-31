package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocateCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.CountryContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.ScheduleContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ScheduleBuilder;
import seedu.address.testutil.ScheduleUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        AddCommand commandUsingAlias = (AddCommand) parser.parseCommand(
                PersonUtil.getAddCommandUsingAlias(person));
        assertEquals(new AddCommand(person), command);
        assertEquals(new AddCommand(person), commandUsingAlias);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        DeleteCommand commandUsingAlias = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);

        assertEquals(new DeleteCommand(indices), command);
        assertEquals(new DeleteCommand(indices), commandUsingAlias);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        EditCommand commandUsingAlias = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), commandUsingAlias);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findName() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "n/" + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + "n/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }

    @Test
    public void parseCommand_findEmail() throws Exception {
        List<String> keywords = Arrays.asList("yahoo", "gmail", "google");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "e/" + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + "e/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new EmailContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new EmailContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }

    @Test
    public void parseCommand_findAddress() throws Exception {
        List<String> keywords = Arrays.asList("123", "Bishan", "Ave", "6", "#08-111");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "a/123 Bishan Ave 6 #08-111");
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "a/123 Bishan Ave 6 #08-111");
        assertEquals(new FindCommand(new AddressContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new AddressContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }

    @Test
    public void parseCommand_findPhone() throws Exception {
        List<String> keywords = Arrays.asList("9898", "4554", "0145");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "p/98984554 0145");
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "p/98984554 0145");
        assertEquals(new FindCommand(new PhoneContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new PhoneContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }


    @Test
    public void parseCommand_findCountry() throws Exception {
        List<String> keywords = Arrays.asList("Paris", "China");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "c/Paris China");
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "c/Paris China");
        assertEquals(new FindCommand(new CountryContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new CountryContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }


    @Test
    public void parseCommand_findTag() throws Exception {
        List<String> keywords = Arrays.asList("friends", "teachers", "schoolmates");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "t/ " + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + "t/ " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new TagContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new TagContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }

    @Test
    public void parseCommand_findSchedule() throws Exception {
        List<String> keywords = Arrays.asList("interview", "meeting", "party");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "act/ " + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + "act/ " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new ScheduleContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new ScheduleContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }


    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + "      ") instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " "
                + DeleteCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " "
                + DeleteCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + "     ") instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " "
                + DeleteCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " "
                + DeleteCommand.COMMAND_ALIAS) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        Schedule schedule = new ScheduleBuilder().build();
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(ScheduleUtil.getScheduleCommand(
                schedule, INDEX_FIRST_PERSON));
        ScheduleCommand commandUsingAlias = (ScheduleCommand) parser.parseCommand(ScheduleUtil
                .getScheduleCommandUsingAlias(schedule, INDEX_FIRST_PERSON));

        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);

        assertEquals(new ScheduleCommand(indices, schedule), command);
        assertEquals(new ScheduleCommand(indices, schedule), commandUsingAlias);
    }

    @Test
    public void parseCommand_select() throws Exception {
        LocateCommand command = (LocateCommand) parser.parseCommand(
                LocateCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        LocateCommand commandUsingAlias = (LocateCommand) parser.parseCommand(
                LocateCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LocateCommand(INDEX_FIRST_PERSON), command);
        assertEquals(new LocateCommand(INDEX_FIRST_PERSON), commandUsingAlias);
    }

    @Test
    public void parseCommand_calendar() throws Exception {
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_WORD) instanceof CalendarCommand);
    }

    @Test
    public void parseEmail_calendar() throws Exception {
        assertTrue(parser.parseCommand(EmailCommand.COMMAND_WORD) instanceof EmailCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 1") instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
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
}
