package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author Kowalski985
public class AutocompleteCommandTest {
    @Test
    public void getInstance() throws Exception {
        assertEquals(AutocompleteCommand.ADD, AutocompleteCommand.getInstance("add"));
        assertEquals(AutocompleteCommand.CLEAR, AutocompleteCommand.getInstance("clear"));
        assertEquals(AutocompleteCommand.DELETE, AutocompleteCommand.getInstance("delete"));
        assertEquals(AutocompleteCommand.DELETE_TAG, AutocompleteCommand.getInstance("deleteTag"));
        assertEquals(AutocompleteCommand.EDIT, AutocompleteCommand.getInstance("edit"));
        assertEquals(AutocompleteCommand.EXIT, AutocompleteCommand.getInstance("exit"));
        assertEquals(AutocompleteCommand.FIND, AutocompleteCommand.getInstance("find"));
        assertEquals(AutocompleteCommand.HELP, AutocompleteCommand.getInstance("help"));
        assertEquals(AutocompleteCommand.HISTORY, AutocompleteCommand.getInstance("history"));
        assertEquals(AutocompleteCommand.IMPORT, AutocompleteCommand.getInstance("import"));
        assertEquals(AutocompleteCommand.LIST, AutocompleteCommand.getInstance("list"));
        assertEquals(AutocompleteCommand.NONE, AutocompleteCommand.getInstance("none"));
        assertEquals(AutocompleteCommand.REDO, AutocompleteCommand.getInstance("redo"));
        assertEquals(AutocompleteCommand.SELECT, AutocompleteCommand.getInstance("select"));
        assertEquals(AutocompleteCommand.TAB, AutocompleteCommand.getInstance("tab"));
        assertEquals(AutocompleteCommand.UNDO, AutocompleteCommand.getInstance("undo"));
    }

}
