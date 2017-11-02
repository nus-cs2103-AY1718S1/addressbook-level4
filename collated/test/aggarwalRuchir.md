# aggarwalRuchir
###### /java/seedu/address/logic/commands/ListCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/model/person/PhoneTest.java
``` java
    @Test
    public void isPhoneFormattingCorrect() {
        assertEquals(Phone.formatPhone("911"), "911");

        assertEquals(Phone.formatPhone("6593121534"), "65-9312-1534"); //Singapore number
        assertEquals(Phone.formatPhone("9191121444"), "91-9112-1444"); //Indian number
        assertEquals(Phone.formatPhone("17651230101"), "176-5123-0101"); //US number
        assertEquals(Phone.formatPhone("447881234567"), "4478-8123-4567"); //UK number

        assertEquals(Phone.formatPhone("124293842033123"), "124-2938-4203-3123");
    }
```
