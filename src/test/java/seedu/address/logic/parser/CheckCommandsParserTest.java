//@@author huiyiiih
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CheckCommandsParserTest {

    private final CheckCommandsParser parser = new CheckCommandsParser();

    @Test
    public void checkCommand_add() {
        // Check if the synonyms  is equals to add
        assertEquals(parser.matchCommand("input"), "add");
        assertEquals(parser.matchCommand("a"), "add");
        assertNotEquals(parser.matchCommand("plus"), "add");
    }

    @Test
    public void checkCommand_clear() {
        // Check if the synonyms  is equals to clear
        assertEquals(parser.matchCommand("c"), "clear");
        assertEquals(parser.matchCommand("empty"), "clear");
        assertNotEquals(parser.matchCommand("cl"), "clear");
    }

    @Test
    public void checkCommand_edit() {
        // Check if the synonyms  is equals to edit
        assertEquals(parser.matchCommand("e"), "edit");
        assertEquals(parser.matchCommand("modify"), "edit");
        assertNotEquals(parser.matchCommand("ed"), "edit");
    }

    @Test
    public void checkCommand_delete() {
        // Check if the synonyms  is equals to delete
        assertEquals(parser.matchCommand("d"), "delete");
        assertEquals(parser.matchCommand("remove"), "delete");
        assertNotEquals(parser.matchCommand("away"), "delete");
    }

    @Test
    public void checkCommand_exit() {
        // Check if the synonyms  is equals to exit
        assertEquals(parser.matchCommand("quit"), "exit");
        assertEquals(parser.matchCommand("exit"), "exit");
        assertNotEquals(parser.matchCommand("out"), "exit");
    }

    @Test
    public void checkCommand_find() {
        // Check if the synonyms  is equals to find
        assertEquals(parser.matchCommand("search"), "find");
        assertEquals(parser.matchCommand("f"), "find");
        assertNotEquals(parser.matchCommand("looked"), "find");
    }

    @Test
    public void checkCommand_help() {
        // Check if the synonyms  is equals to help
        assertEquals(parser.matchCommand("info"), "help");
        assertEquals(parser.matchCommand("help"), "help");
        assertNotEquals(parser.matchCommand("helps"), "help");
    }

    @Test
    public void checkCommand_history() throws Exception {
        // Check if the synonyms  is equals to history
        assertEquals(parser.matchCommand("past"), "history");
        assertEquals(parser.matchCommand("h"), "history");
        assertNotEquals(parser.matchCommand("his"), "history");
    }

    @Test
    public void checkCommand_list() {
        // Check if the synonyms  is equals to list
        assertEquals(parser.matchCommand("display"), "list");
        assertEquals(parser.matchCommand("show"), "list");
        assertNotEquals(parser.matchCommand("showme"), "list");
    }

    @Test
    public void checkCommand_redo() {
        // Check if the synonyms  is equals to redo
        assertEquals(parser.matchCommand("r"), "redo");
        assertEquals(parser.matchCommand("redo"), "redo");
        assertNotEquals(parser.matchCommand("again"), "redo");
    }

    @Test
    public void checkCommand_select() {
        // Check if the synonyms  is equals to select
        assertEquals(parser.matchCommand("s"), "select");
        assertEquals(parser.matchCommand("pick"), "select");
        assertNotEquals(parser.matchCommand("pickthis"), "select");
    }

    @Test
    public void checkCommand_undo() {
        // Check if the synonyms  is equals to redo
        assertEquals(parser.matchCommand("u"), "undo");
        assertEquals(parser.matchCommand("undo"), "undo");
        assertNotEquals(parser.matchCommand("repeat"), "undo");
    }
    @Test
    public void checkCommand_checkschedule() {
        // Check if the synonyms  is equals to check schedule
        assertEquals(parser.matchCommand("checkschedule"), "thisweek");
        assertEquals(parser.matchCommand("cs"), "thisweek");
        assertNotEquals(parser.matchCommand("s"), "thisweek");
    }
    @Test
    public void checkCommand_addevents() {
        // Check if the synonyms  is equals to add events
        assertEquals(parser.matchCommand("addevent"), "eventadd");
        assertEquals(parser.matchCommand("ea"), "eventadd");
        assertNotEquals(parser.matchCommand("adde"), "eventadd");
    }
    @Test
    public void checkCommand_deleteevents() {
        // Check if the synonyms  is equals to find delete events
        assertEquals(parser.matchCommand("delevent"), "eventdel");
        assertEquals(parser.matchCommand("de"), "eventdel");
        assertNotEquals(parser.matchCommand("deletee"), "eventdel");
    }
    @Test
    public void checkCommand_editevents() {
        // Check if the synonyms  is equals to edit events
        assertEquals(parser.matchCommand("editevent"), "eventedit");
        assertEquals(parser.matchCommand("ee"), "eventedit");
        assertNotEquals(parser.matchCommand("edite"), "eventedit");
    }
    @Test
    public void checkCommand_findevents() {
        // Check if the synonyms  is equals to find events
        assertEquals(parser.matchCommand("findevent"), "eventfind");
        assertEquals(parser.matchCommand("fe"), "eventfind");
        assertNotEquals(parser.matchCommand("finde"), "eventfind");
    }
    @Test
    public void checkCommand_setrelationships() {
        // Check if the synonyms  is equals to set relationship
        assertEquals(parser.matchCommand("rel"), "set");
        assertEquals(parser.matchCommand("setrel"), "set");
        assertNotEquals(parser.matchCommand("s"), "set");
    }
    @Test
    public void checkCommand_timetable() {
        assertEquals(parser.matchCommand("tt"), "timetable");
        assertNotEquals(parser.matchCommand("time"), "timetable");
    }
    @Test
    public void checkCommand_updatephoto() {
        assertEquals(parser.matchCommand("up"), "updatephoto");
        assertNotEquals(parser.matchCommand("update"), "updatephoto");
    }
}
//@@author

