package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CheckCommandsParserTest {

    private final CheckCommandsParser parser1 = new CheckCommandsParser();

    @Test
    public void checkCommand_add() {
        // Check if the synonyms  is equals to add
        assertEquals(parser1.matchCommand("input"), "add");
        assertEquals(parser1.matchCommand("insert"), "add");
        assertEquals(parser1.matchCommand("a"), "add");
        assertEquals(parser1.matchCommand("create"), "add");
        assertEquals(parser1.matchCommand("add"), "add");
        assertNotEquals(parser1.matchCommand("plus"), "add");
    }

    @Test
    public void checkCommand_clear() {
        // Check if the synonyms  is equals to clear
        assertEquals(parser1.matchCommand("c"), "clear");
        assertEquals(parser1.matchCommand("empty"), "clear");
        assertEquals(parser1.matchCommand("clean"), "clear");
        assertEquals(parser1.matchCommand("clear"), "clear");
        assertNotEquals(parser1.matchCommand("cl"), "clear");
    }

    @Test
    public void checkCommand_edit() {
        // Check if the synonyms  is equals to edit
        assertEquals(parser1.matchCommand("e"), "edit");
        assertEquals(parser1.matchCommand("modify"), "edit");
        assertEquals(parser1.matchCommand("change"), "edit");
        assertEquals(parser1.matchCommand("revise"), "edit");
        assertEquals(parser1.matchCommand("edit"), "edit");
        assertNotEquals(parser1.matchCommand("ed"), "edit");
    }

    @Test
    public void checkCommand_delete() {
        // Check if the synonyms  is equals to delete
        assertEquals(parser1.matchCommand("d"), "delete");
        assertEquals(parser1.matchCommand("remove"), "delete");
        assertEquals(parser1.matchCommand("throw"), "delete");
        assertEquals(parser1.matchCommand("erase"), "delete");
        assertEquals(parser1.matchCommand("delete"), "delete");
        assertNotEquals(parser1.matchCommand("away"), "delete");
    }

    @Test
    public void checkCommand_exit() {
        // Check if the synonyms  is equals to exit
        assertEquals(parser1.matchCommand("quit"), "exit");
        assertEquals(parser1.matchCommand("exit"), "exit");
        assertNotEquals(parser1.matchCommand("out"), "exit");
    }

    @Test
    public void checkCommand_find() {
        // Check if the synonyms  is equals to find
        assertEquals(parser1.matchCommand("search"), "find");
        assertEquals(parser1.matchCommand("look"), "find");
        assertEquals(parser1.matchCommand("check"), "find");
        assertEquals(parser1.matchCommand("f"), "find");
        assertEquals(parser1.matchCommand("find"), "find");
        assertNotEquals(parser1.matchCommand("looked"), "find");
    }

    @Test
    public void checkCommand_help() {
        // Check if the synonyms  is equals to help
        assertEquals(parser1.matchCommand("info"), "help");
        assertEquals(parser1.matchCommand("help"), "help");
        assertNotEquals(parser1.matchCommand("helps"), "help");
    }

    @Test
    public void checkCommand_history() throws Exception {
        // Check if the synonyms  is equals to history
        assertEquals(parser1.matchCommand("past"), "history");
        assertEquals(parser1.matchCommand("history"), "history");
        assertEquals(parser1.matchCommand("h"), "history");
        assertNotEquals(parser1.matchCommand("his"), "history");
    }

    @Test
    public void checkCommand_list() {
        // Check if the synonyms  is equals to list
        assertEquals(parser1.matchCommand("display"), "list");
        assertEquals(parser1.matchCommand("l"), "list");
        assertEquals(parser1.matchCommand("show"), "list");
        assertEquals(parser1.matchCommand("list"), "list");
        assertNotEquals(parser1.matchCommand("showme"), "list");
    }

    @Test
    public void checkCommand_redo() {
        // Check if the synonyms  is equals to redo
        assertEquals(parser1.matchCommand("r"), "redo");
        assertEquals(parser1.matchCommand("redo"), "redo");
        assertNotEquals(parser1.matchCommand("again"), "redo");
    }

    @Test
    public void checkCommand_select() {
        // Check if the synonyms  is equals to select
        assertEquals(parser1.matchCommand("s"), "select");
        assertEquals(parser1.matchCommand("pick"), "select");
        assertEquals(parser1.matchCommand("choose"), "select");
        assertEquals(parser1.matchCommand("select"), "select");
        assertNotEquals(parser1.matchCommand("pickthis"), "select");
    }

    @Test
    public void checkCommand_undo() {
        // Check if the synonyms  is equals to redo
        assertEquals(parser1.matchCommand("u"), "undo");
        assertEquals(parser1.matchCommand("undo"), "undo");
        assertNotEquals(parser1.matchCommand("repeat"), "undo");
    }
}
