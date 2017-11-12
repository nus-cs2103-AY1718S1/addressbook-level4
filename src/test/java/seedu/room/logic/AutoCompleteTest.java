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

//@@author shitian007
public class AutoCompleteTest {

    private AutoComplete autoComplete;
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Before
    public void setUp() {
        autoComplete = new AutoComplete(model);
    }

    @Test
    public void assert_baseCommandsMatchUponCreation_success() {
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(AutoComplete.BASE_COMMANDS, baseCommands));
    }

    @Test
    public void assert_autoCompleteListNamesUpdate_success() {
        List<ReadOnlyPerson> residents = model.getFilteredPersonList();
        autoComplete.updateAutoCompleteList("find");
        String[] updatedList = autoComplete.getAutoCompleteList();

        for (int i = 0; i < residents.size(); i++) {
            String findPersonString = "find " + residents.get(i).getName().toString();
            assertTrue(findPersonString.equals(updatedList[i]));
        }
    }

    @Test
    public void assert_autoCompleteListIndexUpdate_success() {
        List<ReadOnlyPerson> residents = model.getFilteredPersonList();
        autoComplete.updateAutoCompleteList("edit");
        String[] updatedList = autoComplete.getAutoCompleteList();

        for (int i = 0; i < residents.size(); i++) {
            String editResidentIndex = "edit " + (i + 1);
            assertTrue(editResidentIndex.equals(updatedList[i]));
        }
    }

    @Test
    public void assert_resetAutoCompleteListMatchBaseCommands_success() {
        autoComplete.resetAutocompleteList();
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(AutoComplete.BASE_COMMANDS, baseCommands));
    }

    @Test
    public void assert_autoCompleteListResetOnEmptyStringInput_success() {
        autoComplete.updateAutoCompleteList("");
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(AutoComplete.BASE_COMMANDS, baseCommands));
    }
}
