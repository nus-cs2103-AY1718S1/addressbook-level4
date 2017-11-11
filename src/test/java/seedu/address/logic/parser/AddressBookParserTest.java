package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
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
import seedu.address.logic.commands.BinclearCommand;
import seedu.address.logic.commands.BindeleteCommand;
import seedu.address.logic.commands.BinrestoreCommand;
import seedu.address.logic.commands.BirthdayAddCommand;
import seedu.address.logic.commands.BirthdayRemoveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MapRouteCommand;
import seedu.address.logic.commands.MapShowCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SwitchThemeCommand;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagAddCommand.TagAddDescriptor;
import seedu.address.logic.commands.TagFindCommand;
import seedu.address.logic.commands.TagRemoveCommand;
import seedu.address.logic.commands.TagRemoveCommand.TagRemoveDescriptor;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagMatchingKeywordPredicate;
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
    //@@author Pengyuz
    @Test
    public void parseCommand_create() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getCreateCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_put() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getPutCommand(person));
        assertEquals(new AddCommand(person), command);
    }
    //@@author
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " I/" + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(todelete), command);
    }
    //@@author Pengyuz
    @Test
    public void parseCommand_remove() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_2 + " I/" + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(todelete), command);
    }

    @Test
    public void parseCommand_minus() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_3 + " I/" + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(todelete), command);
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

    @Test
    public void parseCommand_modify() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD_3 + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_update() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD_2 + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_tagAdd() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("friend"));

        TagAddDescriptor descriptor = new TagAddDescriptor(person);
        descriptor.setTags(tagSet);
        TagAddCommand command = (TagAddCommand) parser.parseCommand(TagAddCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " friend");
        assertEquals(new TagAddCommand(singlePersonIndexList, descriptor), command);
    }

    @Test
    public void parseCommand_looseTagFind() throws Exception {
        boolean looseFind = true;
        TagMatchingKeywordPredicate predicate = new TagMatchingKeywordPredicate("friend", looseFind);
        TagFindCommand command = (TagFindCommand) parser.parseCommand(TagFindCommand.COMMAND_WORD
                + " friend");
        assertEquals(new TagFindCommand(predicate), command);
    }

    @Test
    public void parseCommand_tagRemove() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("friend"));

        TagRemoveDescriptor descriptor = new TagRemoveDescriptor(person);
        descriptor.setTags(tagSet);
        TagRemoveCommand command = (TagRemoveCommand) parser.parseCommand(TagRemoveCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " friend");
        assertEquals(new TagRemoveCommand(singlePersonIndexList, descriptor), command);
    }

    @Test
    public void parseCommand_theme() throws Exception {
        String themeChoice1 = "1";
        String themeChoice2 = "Twilight";
        SwitchThemeCommand command1 = new SwitchThemeCommand(themeChoice1);
        assertEquals(new SwitchThemeCommand(themeChoice1), command1);

        SwitchThemeCommand command2 = new SwitchThemeCommand(themeChoice2);
        assertEquals(new SwitchThemeCommand(themeChoice2), command2);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    //@@author dalessr
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("n/", "foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_birthdayAdd() throws Exception {
        List<String> keywords = Arrays.asList("1", "01/01/2000");
        BirthdayAddCommand command = (BirthdayAddCommand) parser.parseCommand(
                BirthdayAddCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        Index firstIndex = new Index(0);
        Birthday birthday = new Birthday("01/01/2000");
        assertEquals(new BirthdayAddCommand(firstIndex, birthday), command);
    }

    @Test
    public void parseCommand_birthdayRemove() throws Exception {
        List<String> keywords = Arrays.asList("1");
        BirthdayRemoveCommand command = (BirthdayRemoveCommand) parser.parseCommand(
                BirthdayRemoveCommand.COMMAND_WORD + " " + keywords.get(0) + "");
        Index firstIndex = new Index(0);
        assertEquals(new BirthdayRemoveCommand(firstIndex), command);
    }

    //@@author Pengyuz
    @Test
    public void parseCommand_search() throws Exception {
        List<String> keywords = Arrays.asList("n/", "foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD_2 + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_get() throws Exception {
        List<String> keywords = Arrays.asList("n/", "foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD_3 + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
    //@@author
    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_second() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD_2) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD_2 + " 3") instanceof HelpCommand);
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
    public void parseCommand_record() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD_2) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD_2 + " 3") instanceof HistoryCommand);

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
    public void parseCommand_show() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_2) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_2 + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_all() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_3) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_3 + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_choose() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD_2 + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author dalessr
    @Test
    public void parseCommand_map_show() throws Exception {
        MapShowCommand command = (MapShowCommand) parser.parseCommand(
                MapShowCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new MapShowCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_map_route() throws Exception {
        String startLocation = "Clementi Street";
        MapRouteCommand command = (MapRouteCommand) parser.parseCommand(
                MapRouteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_ADDRESS + startLocation);
        assertEquals(new MapRouteCommand(INDEX_FIRST_PERSON, startLocation), command);
    }
    //@@author Pengyuz
    @Test
    public void parseCommand_binclear() throws Exception {
        assertTrue(parser.parseCommand(BinclearCommand.COMMAND_WORD) instanceof BinclearCommand);
        assertTrue(parser.parseCommand(BinclearCommand.COMMAND_WORD + " 3") instanceof BinclearCommand);
    }

    @Test
    public void parseCommand_bindelete() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        BindeleteCommand command = (BindeleteCommand) parser.parseCommand(
                BindeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new BindeleteCommand(todelete), command);
    }

    @Test
    public void parseCommand_bindresotre() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        BinrestoreCommand command = (BinrestoreCommand) parser.parseCommand(
                BinrestoreCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new BinrestoreCommand(todelete), command);
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
