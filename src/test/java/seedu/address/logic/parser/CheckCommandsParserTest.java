package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CheckCommandsParserTest {

    private final CheckCommandsParser parser1 = new CheckCommandsParser();
    @Test
    public void checkCommand_add() {
        // Check if the synonyms  is equals to add
        assertEquals(CheckCommandsParser.matchCommand("input"), "add");
        assertEquals(CheckCommandsParser.matchCommand("insert"), "add");
        assertEquals(CheckCommandsParser.matchCommand("a"), "add");
        assertEquals(CheckCommandsParser.matchCommand("create"), "add");
        assertEquals(CheckCommandsParser.matchCommand("add"), "add");
        assertNotEquals(CheckCommandsParser.matchCommand("plus"), "add");
    }
    @Test
    public void checkCommand_clear() {
        // Check if the synonyms  is equals to clear
        assertEquals(CheckCommandsParser.matchCommand("c"), "clear");
        assertEquals(CheckCommandsParser.matchCommand("empty"), "clear");
        assertEquals(CheckCommandsParser.matchCommand("clean"), "clear");
        assertEquals(CheckCommandsParser.matchCommand("clear"), "clear");
        assertNotEquals(CheckCommandsParser.matchCommand("cl"), "clear");
    }

    @Test
    public void checkCommand_edit() {
        // Check if the synonyms  is equals to edit
        assertEquals(CheckCommandsParser.matchCommand("e"), "edit");
        assertEquals(CheckCommandsParser.matchCommand("modify"), "edit");
        assertEquals(CheckCommandsParser.matchCommand("change"), "edit");
        assertEquals(CheckCommandsParser.matchCommand("revise"), "edit");
        assertEquals(CheckCommandsParser.matchCommand("edit"), "edit");
        assertNotEquals(CheckCommandsParser.matchCommand("ed"), "edit");
    }
    @Test
    public void checkCommand_delete() {
        // Check if the synonyms  is equals to delete
        assertEquals(CheckCommandsParser.matchCommand("d"), "delete");
        assertEquals(CheckCommandsParser.matchCommand("remove"), "delete");
        assertEquals(CheckCommandsParser.matchCommand("throw"), "delete");
        assertEquals(CheckCommandsParser.matchCommand("erase"), "delete");
        assertEquals(CheckCommandsParser.matchCommand("delete"), "delete");
        assertNotEquals(CheckCommandsParser.matchCommand("away"), "delete");
    }
    @Test
    public void checkCommand_exit() {
        // Check if the synonyms  is equals to exit
        assertEquals(CheckCommandsParser.matchCommand("quit"), "exit");
        assertEquals(CheckCommandsParser.matchCommand("exit"), "exit");
        assertNotEquals(CheckCommandsParser.matchCommand("out"), "exit");
    }
    @Test
    public void checkCommand_find() {
        // Check if the synonyms  is equals to find
        assertEquals(CheckCommandsParser.matchCommand("search"), "find");
        assertEquals(CheckCommandsParser.matchCommand("look"), "find");
        assertEquals(CheckCommandsParser.matchCommand("check"), "find");
        assertEquals(CheckCommandsParser.matchCommand("f"), "find");
        assertEquals(CheckCommandsParser.matchCommand("find"), "find");
        assertNotEquals(CheckCommandsParser.matchCommand("looked"), "find");
    }
    @Test
    public void checkCommand_help() {
        // Check if the synonyms  is equals to help
        assertEquals(CheckCommandsParser.matchCommand("info"), "help");
        assertEquals(CheckCommandsParser.matchCommand("help"), "help");
        assertNotEquals(CheckCommandsParser.matchCommand("helps"), "help");
    }
    @Test
    public void checkCommand_history() throws Exception {
        // Check if the synonyms  is equals to history
        assertEquals(CheckCommandsParser.matchCommand("past"), "history");
        assertEquals(CheckCommandsParser.matchCommand("history"), "history");
        assertEquals(CheckCommandsParser.matchCommand("h"), "history");
        assertNotEquals(CheckCommandsParser.matchCommand("his"), "history");
    }
    @Test
    public void checkCommand_list() {
        // Check if the synonyms  is equals to list
        assertEquals(CheckCommandsParser.matchCommand("display"), "list");
        assertEquals(CheckCommandsParser.matchCommand("l"), "list");
        assertEquals(CheckCommandsParser.matchCommand("show"), "list");
        assertEquals(CheckCommandsParser.matchCommand("list"), "list");
        assertNotEquals(CheckCommandsParser.matchCommand("showme"), "list");
    }
    @Test
    public void checkCommand_redo() {
        // Check if the synonyms  is equals to redo
        assertEquals(CheckCommandsParser.matchCommand("r"), "redo");
        assertEquals(CheckCommandsParser.matchCommand("redo"), "redo");
        assertNotEquals(CheckCommandsParser.matchCommand("again"), "redo");
    }
    @Test
    public void checkCommand_select() {
        // Check if the synonyms  is equals to select
        assertEquals(CheckCommandsParser.matchCommand("s"), "select");
        assertEquals(CheckCommandsParser.matchCommand("pick"), "select");
        assertEquals(CheckCommandsParser.matchCommand("choose"), "select");
        assertEquals(CheckCommandsParser.matchCommand("select"), "select");
        assertNotEquals(CheckCommandsParser.matchCommand("pickthis"), "select");
    }
    @Test
    public void checkCommand_undo() {
        // Check if the synonyms  is equals to redo
        assertEquals(CheckCommandsParser.matchCommand("u"), "undo");
        assertEquals(CheckCommandsParser.matchCommand("undo"), "undo");
        assertNotEquals(CheckCommandsParser.matchCommand("repeat"), "undo");
    }
}
