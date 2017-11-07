package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.HintParser.autocomplete;
import static seedu.address.logic.parser.HintParser.generateHint;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MusicCommand;

public class HintParserTest {

    //@@author goweiwen
    @Test
    public void autocomplete_emptyInput_returnsEmpty() {
        assertEquals("", autocomplete(""));
    }

    @Test
    public void autocomplete_invalidCommand_returnsItself() {
        assertEquals(
                "should-not-complete-to-any-commands",
                autocomplete("should-not-complete-to-any-commands"));
    }

    @Test
    public void autocomplete_incompleteCommand_returnsFullCommandAndTrailingSpace() {
        assertEquals(
                AddCommand.COMMAND_WORD + " ",
                autocomplete(AddCommand.COMMAND_WORD.substring(0, AddCommand.COMMAND_WORD.length() - 1)));
        assertEquals(
                ListCommand.COMMAND_WORD + " ",
                autocomplete(ListCommand.COMMAND_WORD.substring(0, ListCommand.COMMAND_WORD.length() - 1)));
    }

    @Test
    public void autocomplete_validCommands_returnsParameters() {
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD));
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD + " "));
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD + " n"));
        assertEquals(AddCommand.COMMAND_WORD + " p/", autocomplete(AddCommand.COMMAND_WORD + " n/"));

        assertEquals(EditCommand.COMMAND_WORD + " 1", autocomplete(EditCommand.COMMAND_WORD));
        assertEquals(EditCommand.COMMAND_WORD + " 2", autocomplete(EditCommand.COMMAND_WORD + " 1"));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1 "));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1 n"));
        assertEquals(EditCommand.COMMAND_WORD + " 1 p/", autocomplete(EditCommand.COMMAND_WORD + " 1 n/"));

        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD));
        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD + " n"));
        assertEquals(FindCommand.COMMAND_WORD + " p/", autocomplete(FindCommand.COMMAND_WORD + " n/"));

        assertEquals(MusicCommand.COMMAND_WORD + " play", autocomplete(MusicCommand.COMMAND_WORD));
        assertEquals(MusicCommand.COMMAND_WORD + " pause", autocomplete(MusicCommand.COMMAND_WORD + " paus"));
    }
    //@@author

    //@@author nicholaschuayunzhi
    @Test
    public void generate_add_hint() {
        assertHintEquals("add", " n/name");
        assertHintEquals("add ", "n/name");
        assertHintEquals("add n", "/name");
        assertHintEquals("add n/", "name");
        assertHintEquals("add n/name", " p/phone");
        assertHintEquals("add n/name ", "p/phone");
        assertHintEquals("add n/name p", "/phone");
        assertHintEquals("add n/name p/", "phone");
        assertHintEquals("add n/name p/123", " e/email");
        assertHintEquals("add n/name p/notValid", " e/email");
        assertHintEquals("add n/name p/123 ", "e/email");
        assertHintEquals("add n/name p/123 e", "/email");
        assertHintEquals("add n/name p/123 e/", "email");
        assertHintEquals("add n/name p/123 e/e@e.com", " a/address");
        assertHintEquals("add n/name p/123 e/notValid", " a/address");
        assertHintEquals("add n/name p/123 e/e@e.com" , " a/address");
        assertHintEquals("add n/name p/123 e/e@e.com a" , "/address");
        assertHintEquals("add n/name p/123 e/e@e.com a/" , "address");
        assertHintEquals("add n/name p/123 e/e@e.com a/address" , " t/tag");
        assertHintEquals("add n/name p/123 e/e@e.com a/address " , "t/tag");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t" , "/tag");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/" , "tag");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag" , " i/avatar file path");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag i/avatar file path   " , "");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag i/avatar file path   bla bla" , " ");

        assertHintEquals("add p/phone", " n/name");
        assertHintEquals("add p/phone n", "/name");
        assertHintEquals("add p/phone t", "/tag");

        //tag is not completed twice
        //TODO: update to account for this
        assertHintEquals("add t/tag t", " n/name");
    }

    @Test
    public void generate_edit_hint() {
        assertHintEquals("edit", " 1 index");
        assertHintEquals("edit ", "1 index");
        assertHintEquals("edit 1", " index");
        assertHintEquals("edit 12", " index");
        assertHintEquals("edit 12 ", "n/name");

        assertHintEquals("edit 12 p", "/phone");
        assertHintEquals("edit 12 p/", "phone");

        assertHintEquals("edit 12 n", "/name");
        assertHintEquals("edit 12 n/", "name");

        assertHintEquals("edit 12 e", "/email");
        assertHintEquals("edit 12 e/", "email");

        assertHintEquals("edit 12 a", "/address");
        assertHintEquals("edit 12 a/", "address");

        assertHintEquals("edit 12 t", "/tag");
        assertHintEquals("edit 12 t/", "tag");

        assertHintEquals("edit 12 p/123", " n/name");
        assertHintEquals("edit 12 p/123 ", "n/name");

        //TODO: change this functionality
        assertHintEquals("edit p/123", " 1 index");
        assertHintEquals("edit p/123 ", "1 index");
        assertHintEquals("edit p/123 1", " 1 index");
        assertHintEquals("edit p/123 1 ", "1 index");
    }

    @Test
    public void generate_find_hint() {
        assertHintEquals("find", " n/name");
        assertHintEquals("find ", "n/name");
        assertHintEquals("find", " n/name");

        assertHintEquals("find n", "/name");
        assertHintEquals("find n/", "name");
        assertHintEquals("find n/1", " p/phone");

        assertHintEquals("find p", "/phone");
        assertHintEquals("find p/", "phone");
        assertHintEquals("find p/1", " n/name");

        assertHintEquals("find e", "/email");
        assertHintEquals("find e/", "email");
        assertHintEquals("find e/1", " n/name");

        assertHintEquals("find a", "/address");
        assertHintEquals("find a/", "address");
        assertHintEquals("find a/1", " n/name");

        assertHintEquals("find t", "/tag");
        assertHintEquals("find t/", "tag");
        assertHintEquals("find t/1", " n/name");

        assertHintEquals("find r", "/remark");
        assertHintEquals("find r/", "remark");
        assertHintEquals("find r/a", " n/name");


    }

    @Test
    public void generate_select_hint() {
        assertHintEquals("select", " 1 index");
        assertHintEquals("select ", "1 index");
        assertHintEquals("select 1", " index");
        assertHintEquals("select bla 1", " 1 index");
    }

    @Test
    public void generate_delete_hint() {
        assertHintEquals("delete", " 1 index");
        assertHintEquals("delete ", "1 index");
        assertHintEquals("delete 1", " index");
        assertHintEquals("delete bla 1", " 1 index");
    }

    @Test
    public void generate_standard_hint() {
        assertHintEquals("history", " show command history");
        assertHintEquals("exit", " exits the app");
        assertHintEquals("clear", " clears address book");
        assertHintEquals("help", " shows user guide");
        assertHintEquals("undo", " undo command");
        assertHintEquals("redo", " redo command");
        assertHintEquals("unknown", " type help for guide");

        //TODO: to change
        assertHintEquals("alias", " creates an alias");
    }

    public void assertHintEquals(String userInput, String expected) {
        assertEquals(expected, generateHint(userInput));
    }

}
