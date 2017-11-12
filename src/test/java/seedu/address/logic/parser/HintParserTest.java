package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.HintParser.generateHint;

import org.junit.Test;

public class HintParserTest {

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
        assertHintEquals("add n/name p/123 e/e@e.com a/address" , " r/remark (optional)");
        assertHintEquals("add n/name p/123 e/e@e.com a/address " , "r/remark (optional)");
        assertHintEquals("add n/name p/123 e/e@e.com a/address r" , "/remark (optional)");
        assertHintEquals("add n/name p/123 e/e@e.com a/address r/" , "remark (optional)");
        assertHintEquals("add n/name p/123 e/e@e.com a/address r/remark " , "t/tag (optional)");
        assertHintEquals("add n/name p/123 e/e@e.com a/address r/remark t/tag" , " i/avatar file path (optional)");
        assertHintEquals("add n/name p/123 e/e@e.com a/address r/remark t/tag i/avatar file path " , "");
        assertHintEquals("add n/name p/123 e/e@e.com a/address r/remark t/tag i/avatar file path   bla bla" , " ");

        assertHintEquals("add p/phone", " n/name");
        assertHintEquals("add p/phone n", "/name");
        assertHintEquals("add p/phone t", "/tag (optional)");

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

        assertHintEquals("edit 12 r", "/remark");
        assertHintEquals("edit 12 r/", "remark");

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
        assertHintEquals("history", " shows command history");
        assertHintEquals("exit", " exits the application");
        assertHintEquals("clear", " clears all contacts");
        assertHintEquals("help", " shows user guide");
        assertHintEquals("undo", " undo previous command");
        assertHintEquals("redo", " redo command");
        assertHintEquals("unknown", " type help for user guide");
        assertHintEquals("list", " lists all contacts");

    }

    public void assertHintEquals(String userInput, String expected) {
        assertEquals(expected, generateHint(userInput));
    }

}
