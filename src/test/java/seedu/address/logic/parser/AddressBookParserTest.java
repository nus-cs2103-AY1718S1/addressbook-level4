package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddReminder;
import seedu.address.logic.commands.BackUpCommand;
import seedu.address.logic.commands.ChangeFontSizeCommand;
import seedu.address.logic.commands.ChangeReminderCommand;
import seedu.address.logic.commands.ChangeReminderCommand.ChangeReminderDescriptor;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearPopupCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FaceBookCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveReminderCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.SearchContainsKeywordsPredicate;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.TagContainsKeywordsPredicate;
import seedu.address.testutil.ChangeReminderDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ReminderBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();
    //@@author yangminxingnus
    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("CS2101/SEC/1");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
            + 1 + " " + PREFIX_REMARK + " " + "CS2101/SEC/1");
        assertEquals(new RemarkCommand(1, remark), command);
    }
    @Test
    public void parseCommand_logout() throws Exception {
        final LogoutCommand command = new LogoutCommand();
        assertEquals(command, command);
    }
    @Test
    public void parseCommand_email() throws Exception {
        final EmailCommand command = new EmailCommand("friends", "party");
        assertFalse(new EmailCommand("colleagues", "birthday").equals(command));
    }
    //@@author pjunwei95
    @Test
    public void parseCommand_findTag() throws Exception {

        List<String> keywords = Arrays.asList("friends");
        final FindTagCommand command = new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends")));
        assertEquals(command, new FindTagCommand(new TagContainsKeywordsPredicate(keywords)));
    }


    @Test
    public void parseCommand_backup() throws Exception {
        assertTrue(parser.parseCommand(BackUpCommand.COMMAND_WORD) instanceof BackUpCommand);
        assertTrue(parser.parseCommand(BackUpCommand.COMMAND_WORD + " 3") instanceof BackUpCommand);
    }

    //@@author RonakLakhotia
    @Test
    public void parseCommand_search() throws Exception {
        SearchCommand command = new SearchCommand(new
                SearchContainsKeywordsPredicate(Arrays.asList("Alice", "13.10.1997")));

        SearchCommand commandCheck = (SearchCommand) parser.parseCommand(SearchCommand.COMMAND_WORD + " "
                + PREFIX_NAME + "Alice" + " " + PREFIX_DOB + "13.10.1997");
        assertEquals(commandCheck, command);
    }

    @Test
    public void parseCommand_deleteTag() throws Exception {
        assertTrue(parser.parseCommand(DeleteTagCommand.COMMAND_WORD
                + " " + "1" + " " + "t/friends"
        ) instanceof DeleteTagCommand);
    }

    @Test
    public void parseCommand_clearPopup() throws Exception {
        assertTrue(parser.parseCommand(ClearPopupCommand.COMMAND_WORD) instanceof ClearPopupCommand);
        assertTrue(parser.parseCommand(ClearPopupCommand.COMMAND_WORD
                + " " + "1") instanceof ClearPopupCommand);
    }
    //@@author
    @Test
    public void parseCommand_facebook() throws Exception {
        FaceBookCommand command = (FaceBookCommand) parser.parseCommand(
                FaceBookCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new FaceBookCommand(INDEX_FIRST_PERSON), command);
    }
    @Test
    public void parseCommand_photo() throws Exception {
        String path = "src/main/resources/images/clock.png";
        PhotoCommand command = (PhotoCommand) parser.parseCommand(
                PhotoCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " " + path);
        assertEquals(new PhotoCommand(INDEX_FIRST_PERSON, path), command);
    }
    //@@author
    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }
    //@@author RonakLakhotia
    @Test
    public void parseCommand_addReminder() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        AddReminder command = (AddReminder) parser.parseCommand(AddReminder.COMMAND_WORD + " "
                + "g/CS2103T Assignment " + "p/high" + " d/12.11.2017");
        assertEquals(new AddReminder(reminder), command);
    }
    @Test
    public void parseCommand_change() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder(reminder).build();
        ChangeReminderCommand command = (ChangeReminderCommand) parser
                .parseCommand(ChangeReminderCommand.COMMAND_WORD + " "
                + "1 " + "g/CS2103T Assignment" + " p/high" + " d/12.11.2017");
        assertEquals(new ChangeReminderCommand(INDEX_FIRST_REMINDER, descriptor), command);
    }
    //@@author
    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));

        assertNotEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
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


    //@@author RonakLakhotia
    @Test
    public void parseCommand_remove() throws Exception {
        RemoveReminderCommand command = (RemoveReminderCommand) parser.parseCommand(
                RemoveReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased());
        assertEquals(new RemoveReminderCommand(INDEX_FIRST_REMINDER), command);
    }
    //@@author

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommandChangeTagColor() throws Exception {
        assertTrue(parser.parseCommand(ChangeTagColorCommand.COMMAND_WORD
                + " " + "t/friend"
                + " " + "c/red"
        ) instanceof ChangeTagColorCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_map() throws Exception {
        MapCommand command = (MapCommand) parser.parseCommand(
                MapCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new MapCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_changeFontSize() throws Exception {
        ChangeFontSizeCommand command = (ChangeFontSizeCommand) parser.parseCommand(
                ChangeFontSizeCommand.COMMAND_WORD + " " + "xl");
        assertEquals(new ChangeFontSizeCommand("xl"), command);
    }

    @Test
    public void parseCommand_changeTheme() throws Exception {
        ChangeThemeCommand command = (ChangeThemeCommand) parser.parseCommand(
                ChangeThemeCommand.COMMAND_WORD + " " + "dark");
        assertEquals(new ChangeThemeCommand("dark"), command);
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
}
