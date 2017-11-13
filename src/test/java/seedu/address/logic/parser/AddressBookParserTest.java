package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_PLATFORM;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_PLATFORM;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AccessCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EventsCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FavouriteListCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SwitchThemeCommand;
import seedu.address.logic.commands.ThemeListCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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

    //@@author DarrenCzen
    @Test
    public void parseCommand_access() throws Exception {
        AccessCommand command = (AccessCommand) parser.parseCommand(
                AccessCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new AccessCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author
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

    //@@author DarrenCzen
    @Test
    public void parseCommand_location() throws Exception {
        LocationCommand command = (LocationCommand) parser.parseCommand(
                LocationCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LocationCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }

    //@@author archthegit

    @Test
    public void parseCommand_favourite() throws Exception {
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(
                FavouriteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new FavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unfavourite() throws Exception {
        UnfavouriteCommand command = (UnfavouriteCommand) parser.parseCommand(
                UnfavouriteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnfavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_favouriteList() throws Exception {
        assertTrue(parser.parseCommand(FavouriteListCommand.COMMAND_WORD) instanceof FavouriteListCommand);
    }

    @Test
    public void parseCommand_birthdays() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    }

    @Test
    public void parseCommand_themesList() throws Exception {
        assertTrue(parser.parseCommand(ThemeListCommand.COMMAND_WORD) instanceof ThemeListCommand);
        assertTrue(parser.parseCommand(ThemeListCommand.COMMAND_WORD + " 3") instanceof ThemeListCommand);
    }

    @Test
    public void parseCommand_switch() throws Exception {
        SwitchThemeCommand command = (SwitchThemeCommand) parser.parseCommand(
                SwitchThemeCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SwitchThemeCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author
    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
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

    //@@author DarrenCzen
    @Test
    public void parseCommand_addevent_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_EVENT_PLATFORM);
        parser.parseCommand(AddEventCommand.COMMAND_WORD
                + " n/dwedsa d/13/11/2017 a/qedwe");

    }

    @Test
    public void parseCommand_deleteevent_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_EVENT_PLATFORM);
        parser.parseCommand(DeleteEventCommand.COMMAND_WORD + " 1");
    }

    @Test
    public void parseCommand_personCommandsInEventsMode() throws Exception {
        assertTrue(parser.parseCommand(EventsCommand.COMMAND_WORD) instanceof EventsCommand);

        //commands that should not work in Events Mode
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(AddCommand.COMMAND_WORD + " n/Aaron Chen p/83323322 e/aaron@hotmail.com");

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " p/83333322");

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(AccessCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(LocationCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(ClearCommand.COMMAND_WORD);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(FindCommand.COMMAND_WORD + " " + "Emil");

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(FindTagCommand.COMMAND_WORD + " " + "friends");

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_PLATFORM);
        parser.parseCommand(SortCommand.COMMAND_WORD);

        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    }
    //@@author

    //@@author chernghann
    @Test
    public void parseCommand_events() throws Exception {
        assertTrue(parser.parseCommand(EventsCommand.COMMAND_WORD) instanceof EventsCommand);
        assertTrue(parser.parseCommand(EventsCommand.COMMAND_WORD + " 3") instanceof EventsCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    }

    @Test
    public void parseCommand_addevent() throws Exception {
        assertTrue(parser.parseCommand(EventsCommand.COMMAND_WORD) instanceof EventsCommand);
        assertTrue(parser.parseCommand(AddEventCommand.COMMAND_WORD
                + " n/dwedsa d/13/11/2017 a/qedwe") instanceof AddEventCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    }

    @Test
    public void parseCommand_deleteevent() throws Exception {
        assertTrue(parser.parseCommand(EventsCommand.COMMAND_WORD) instanceof EventsCommand);
        assertTrue(parser.parseCommand(DeleteEventCommand.COMMAND_WORD + " 1")
                instanceof DeleteEventCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    }

    //@@author
}
