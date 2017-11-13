package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RetagCommand;
import seedu.address.logic.commands.RetrieveCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ReminderBuilder;
import seedu.address.testutil.ReminderUtil;

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
    public void parseCommand_addReminder() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        AddReminderCommand command = (AddReminderCommand) parser.parseCommand(
                ReminderUtil.getAddReminderCommand(reminder));
        assertEquals(new AddReminderCommand(reminder), command);
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
    public void parseCommand_deleteReminder() throws Exception {
        DeleteReminderCommand command = (DeleteReminderCommand) parser.parseCommand(
                DeleteReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased());
        assertEquals(new DeleteReminderCommand(INDEX_FIRST_REMINDER), command);
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
    public void parseCommand_birthday() throws Exception {
        final Birthday birthday = new Birthday("01/01/1991");
        BirthdayCommand command = (BirthdayCommand) parser.parseCommand(BirthdayCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_BIRTHDAY + " " + birthday.value);
        assertEquals(new BirthdayCommand(INDEX_FIRST_PERSON, birthday), command);
    }

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

    //@@author duyson98
    @Test
    public void parseCommand_retrieve() throws Exception {
        RetrieveCommand command = (RetrieveCommand) parser.parseCommand(RetrieveCommand.COMMAND_WORD + " " + "friends");
        assertEquals(new RetrieveCommand(new TagContainsKeywordPredicate(new Tag("friends"))), command);
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

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    //@@author duyson98
    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ViewCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author duyson98
    @Test
    public void parseCommand_tag() throws Exception {
        TagCommand command = (TagCommand) parser.parseCommand(TagCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + ","
                + INDEX_SECOND_PERSON.getOneBased() + ","
                + INDEX_THIRD_PERSON.getOneBased() + " " + "friends");
        assertEquals(new TagCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                new Tag("friends")), command);
    }

    @Test
    public void parseCommand_untag() throws Exception {
        UntagCommand command = (UntagCommand) parser.parseCommand(UntagCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + ","
                + INDEX_SECOND_PERSON.getOneBased() + ","
                + INDEX_THIRD_PERSON.getOneBased() + " " + "friends/enemies");
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("enemies");
        assertEquals(new UntagCommand(false, Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON), Arrays.asList(secondTag, firstTag)), command);

        command = (UntagCommand) parser.parseCommand(UntagCommand.COMMAND_WORD + " 1,2,3");
        assertEquals(new UntagCommand(false, Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON), Collections.emptyList()), command);

        command = (UntagCommand) parser.parseCommand(UntagCommand.COMMAND_WORD + " -all " + "friends/enemies");
        assertEquals(new UntagCommand(true, Collections.emptyList(),
                Arrays.asList(secondTag, firstTag)), command);

        command = (UntagCommand) parser.parseCommand(UntagCommand.COMMAND_WORD + " -all");
        assertEquals(new UntagCommand(true, Collections.emptyList(), Collections.emptyList()), command);
    }

    @Test
    public void parseCommand_retag() throws Exception {
        RetagCommand command = (RetagCommand) parser.parseCommand(RetagCommand.COMMAND_WORD + " "
                + "enemies" + " " + "friends");
        assertEquals(new RetagCommand(new Tag("enemies"), new Tag("friends")), command);
    }
    //@@author

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
}
