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
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD + " n/"));

        assertEquals(EditCommand.COMMAND_WORD + " ", autocomplete(EditCommand.COMMAND_WORD));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1"));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1 n"));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1 n/"));

        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD));
        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD + " n"));
        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD + " n/"));

        assertEquals(MusicCommand.COMMAND_WORD + " play", autocomplete(MusicCommand.COMMAND_WORD));
        assertEquals(MusicCommand.COMMAND_WORD + " pause", autocomplete(MusicCommand.COMMAND_WORD + " paus"));
    }
    //@@author

    @Test
    public void generate_add_hint() {
        assertHintEquals("add", " n/NAME");
        assertHintEquals("add ", "n/NAME");
        assertHintEquals("add n", "/NAME");
        assertHintEquals("add n/", "NAME");
        assertHintEquals("add n/name", " p/PHONE");
        assertHintEquals("add n/name ", "p/PHONE");
        assertHintEquals("add n/name p", "/PHONE");
        assertHintEquals("add n/name p/", "PHONE");
        assertHintEquals("add n/name p/123", " e/EMAIL");
        assertHintEquals("add n/name p/notValid", " e/EMAIL");
        assertHintEquals("add n/name p/123 ", "e/EMAIL");
        assertHintEquals("add n/name p/123 e", "/EMAIL");
        assertHintEquals("add n/name p/123 e/", "EMAIL");
        assertHintEquals("add n/name p/123 e/e@e.com", " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/notValid", " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com" , " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a" , "/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a/" , "ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a/address" , " t/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address " , "t/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t" , "/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/" , "TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag" , " ");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag    " , "");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag    bla bla" , " ");

        assertHintEquals("add p/phone", " n/NAME");
        assertHintEquals("add p/phone n", "/NAME");
        assertHintEquals("add p/phone t", "/TAG");

        //hints repeated args
        assertHintEquals("add t/tag t", "/TAG");

        //TODO: remove repeated args for unrepeatbles
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag p" , "/PHONE");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag p/" , "PHONE");
    }

    @Test
    public void generate_edit_hint() {
        assertHintEquals("edit", " index");
        assertHintEquals("edit ", "index");
        assertHintEquals("edit 12", " n/NAME");
        assertHintEquals("edit 12 ", "n/NAME");

        assertHintEquals("edit 12 p", "/PHONE");
        assertHintEquals("edit 12 p/", "PHONE");

        assertHintEquals("edit 12 n", "/NAME");
        assertHintEquals("edit 12 n/", "NAME");

        assertHintEquals("edit 12 e", "/EMAIL");
        assertHintEquals("edit 12 e/", "EMAIL");

        assertHintEquals("edit 12 a", "/ADDRESS");
        assertHintEquals("edit 12 a/", "ADDRESS");

        assertHintEquals("edit 12 t", "/TAG");
        assertHintEquals("edit 12 t/", "TAG");

        assertHintEquals("edit 12 p/123", " n/NAME");
        assertHintEquals("edit 12 p/123 ", "n/NAME");

        //TODO: change this functionality
        assertHintEquals("edit p/123", " index");
        assertHintEquals("edit p/123 ", "index");
        assertHintEquals("edit p/123 1", " index");
        assertHintEquals("edit p/123 1 ", "index");
    }

    @Test
    public void generate_find_hint() {
        assertHintEquals("find", " n/NAME");
        assertHintEquals("find ", "n/NAME");
        assertHintEquals("find", " n/NAME");

        assertHintEquals("find n", "/NAME");
        assertHintEquals("find n/", "NAME");
        assertHintEquals("find n/1", " p/PHONE");

        assertHintEquals("find p", "/PHONE");
        assertHintEquals("find p/", "PHONE");
        assertHintEquals("find p/1", " n/NAME");

        assertHintEquals("find e", "/EMAIL");
        assertHintEquals("find e/", "EMAIL");
        assertHintEquals("find e/1", " n/NAME");

        assertHintEquals("find a", "/ADDRESS");
        assertHintEquals("find a/", "ADDRESS");
        assertHintEquals("find a/1", " n/NAME");

        assertHintEquals("find t", "/TAG");
        assertHintEquals("find t/", "TAG");
        assertHintEquals("find t/1", " n/NAME");

        assertHintEquals("find r", "/REMARK");
        assertHintEquals("find r/", "REMARK");
        assertHintEquals("find r/a", " n/NAME");


    }

    @Test
    public void generate_select_hint() {
        assertHintEquals("select", " index");
        assertHintEquals("select ", "index");
        assertHintEquals("select 1", "");
        assertHintEquals("select bla 1", " index");
    }

    @Test
    public void generate_delete_hint() {
        assertHintEquals("delete", " index");
        assertHintEquals("delete ", "index");
        assertHintEquals("delete 1", "");
        assertHintEquals("delete bla 1", " index");
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
