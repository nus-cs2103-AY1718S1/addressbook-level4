package seedu.room.logic;

import static org.junit.Assert.assertTrue;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.ReadOnlyPerson;

public class AutoCompleteTest {

    private AutoComplete autoComplete;
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Before
    public void setUp() {
        autoComplete = new AutoComplete(model);
    }

    @Test
    public void getBaseCommands() {
        String[] actualBaseCommands = { "add", "edit", "select", "delete", "clear",
            "backup", "find", "list", "history", "exit", "help", "undo", "redo" };
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(actualBaseCommands, baseCommands));
    }

    @Test
    public void updateAutoCompleteList() {
        List<ReadOnlyPerson> persons = model.getFilteredPersonList();
        autoComplete.updateAutoCompleteList("find");
        String[] updatedList = autoComplete.getAutoCompleteList();
        for (int i = 0; i < persons.size(); i++) {
            String findPersonString = "find " + persons.get(i).getName().toString();
            assertTrue(findPersonString.equals(updatedList[i]));
        }
    }

    @Test
    public void resetAutoCompleteList() {
        String[] actualBaseCommands = { "add", "edit", "select", "delete", "clear",
            "backup", "find", "list", "history", "exit", "help", "undo", "redo" };
        autoComplete.updateAutoCompleteList("");
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(actualBaseCommands, baseCommands));
    }
}
