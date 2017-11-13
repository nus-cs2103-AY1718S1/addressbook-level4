package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.NICKNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_DATE_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_TIME_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPath.PATH_EXPORT;
import static seedu.address.testutil.TypicalPath.PATH_IMPORT;
import static seedu.address.testutil.TypicalRange.RANGE_ALL;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.commands.DetailsCommand;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.commands.NicknameCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.ToggleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.NameAndTagsContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Nickname;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsTagPredicate;
import seedu.address.model.reminders.Reminder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditReminderDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ReminderBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        AddCommand check = new AddCommand(person);
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseAliasCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddAliasCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addReminder() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        AddReminderCommand command = (AddReminderCommand) parser.parseCommand(AddReminderCommand.COMMAND_WORD
                + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE + REMINDER_DESC_TIME_COFFEE);
        assertEquals(new AddReminderCommand(reminder), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseAliasCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
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
                DeleteReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteReminderCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author edwinghy
    @Test
    public void parseCommand_sort() throws Exception {
        SortCommand command = (SortCommand) parser.parseCommand(SortCommand.COMMAND_WORD);
        assertTrue(command instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
    }
    //@@author
    @Test
    public void parseAliasCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
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
    public void parseCommand_editReminder() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(reminder).build();
        EditReminderCommand command = (EditReminderCommand) parser
                .parseCommand(EditReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE + REMINDER_DESC_TIME_COFFEE);
        assertEquals(new EditReminderCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseAliasCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseAliasCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_toggle() throws Exception {
        assertTrue(parser.parseCommand(ToggleCommand.COMMAND_WORD) instanceof ToggleCommand);
        assertTrue(parser.parseCommand(ToggleCommand.COMMAND_WORD + " 3") instanceof ToggleCommand);
    }
    //@@author tshradheya
    @Test
    public void parseCommand_viewtag() throws Exception {
        String keyword = "foo";
        ViewTagCommand command = (ViewTagCommand) parser.parseCommand(ViewTagCommand.COMMAND_WORD + " " + keyword);

        assertEquals(new ViewTagCommand(new PersonContainsTagPredicate(keyword)), command);
    }

    @Test
    public void parseCommand_email() throws Exception {
        String tag = "friends";
        Service service = new Service("gmail");
        Subject subject = new Subject("hello");
        Body body = new Body("meeting at 8pm");

        EmailCommand command = (EmailCommand) parser.parseCommand(EmailCommand.COMMAND_WORD
                + " " + PREFIX_EMAIL_SERVICE + service.service + " " + PREFIX_EMAIL_TO + tag
                + " " + PREFIX_EMAIL_SUBJECT + subject.subject + " " + PREFIX_EMAIL_BODY + body.body);

        assertEquals(new EmailCommand(new PersonContainsTagPredicate(tag), subject, body, service), command);
    }
    //@@author

    @Test
    public void parseAliasCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseAliasCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
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
    public void parseAliasCommand_history() throws Exception {
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
    }

    //@@author chuaweiwen
    @Test
    public void parseCommand_nickname() throws Exception {
        NicknameCommand command = (NicknameCommand) parser.parseCommand(
                NicknameCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + NICKNAME_DESC_AMY);
        assertEquals(new NicknameCommand(INDEX_FIRST_PERSON, new Nickname(VALID_NICKNAME_AMY)), command);
    }

    @Test
    public void parseCommand_theme() throws Exception {
        ThemeCommand command = (ThemeCommand) parser.parseCommand(
                ThemeCommand.COMMAND_WORD + " " + ThemeList.THEME_NIGHT);
        Theme theme = new Theme(ThemeList.THEME_NIGHT, ThemeList.THEME_NIGHT_PATH);
        assertEquals(new ThemeCommand(theme), command);
    }
    //@@author
    //@@author tshradheya

    @Test
    public void parseCommand_displayPicture() throws  Exception {
        final DisplayPicture displayPicture  = new DisplayPicture(Integer.toString(VALID_EMAIL_AMY.hashCode()));

        DisplayPictureCommand displayPictureCommand =
                (DisplayPictureCommand) parser.parseCommand(DisplayPictureCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + displayPicture.getPath());

        assertEquals(new DisplayPictureCommand(INDEX_FIRST_PERSON, displayPicture), displayPictureCommand);
    }
    //@@author

    @Test
    public void parseAliasCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author tshradheya

    @Test
    public void parseCommand_details() throws Exception {
        DetailsCommand command = (DetailsCommand) parser.parseCommand(
                DetailsCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DetailsCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author

    @Test
    public void parseAliasCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author tshradheya

    @Test
    public void parseCommand_location() throws Exception {
        LocationCommand command = (LocationCommand) parser.parseCommand(
                LocationCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LocationCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author

    //@@author chuaweiwen
    @Test
    public void parseCommand_filter() throws Exception {
        List<String> nameKeywords = Arrays.asList("foo", "bar", "baz");
        List<String> tagKeywords = Arrays.asList("friends");
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " n/foo bar baz t/friends");
        assertEquals(new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords)), command);
    }
    //@@author

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseAliasCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("r 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseAliasCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("u 3") instanceof UndoCommand);
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
    //@@author edwinghy
    @Test
    public void parseCommand_export() throws Exception {
        ExportCommand command = (ExportCommand) parser.parseCommand(ExportCommand.COMMAND_WORD + " "
            + PREFIX_RANGE + RANGE_ALL + " " + PREFIX_PATH + PATH_EXPORT);
        assertEquals(new ExportCommand(RANGE_ALL, PATH_EXPORT), command);
    }

    @Test
    public void parseCommand_import() throws Exception {
        ImportCommand command = (ImportCommand) parser.parseCommand(ImportCommand.COMMAND_WORD + " "
                + PREFIX_PATH + PATH_IMPORT);
        assertEquals(new ImportCommand(PATH_IMPORT), command);
    }
    //@@author
}
