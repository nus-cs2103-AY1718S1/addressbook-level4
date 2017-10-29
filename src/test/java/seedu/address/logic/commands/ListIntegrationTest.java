package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;

//@@author Jeremy
public class ListIntegrationTest {
    private Model model;
    private Model expectedModel;

    @Test
    public void executeIntegrationTest() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Name expectedFirstName;
        Name expectedLastName;
        Name actualFirstName;
        Name actualLastName;
        int listLength;

        //Ascending Reverse
        expectedModel.listNameDescending();
        model.listNameAscending();
        model.listNameReversed();
        expectedFirstName = expectedModel.getFilteredPersonList().get(0).getName();
        actualFirstName = model.getFilteredPersonList().get(0).getName();
        listLength = expectedModel.getFilteredPersonList().size();
        expectedLastName = expectedModel.getFilteredPersonList().get(listLength - 1).getName();
        assertEquals(expectedFirstName, actualFirstName);
        assertTrue(expectedFirstName.equals(actualFirstName));
        assertNotNull(actualFirstName);
        assertNotNull(expectedFirstName);
        assertFalse(actualFirstName.equals(expectedLastName));

        //Descending Reverse
        expectedModel.listNameAscending();
        model.listNameDescending();
        model.listNameReversed();
        expectedFirstName = expectedModel.getFilteredPersonList().get(0).getName();
        actualFirstName = model.getFilteredPersonList().get(0).getName();
        listLength = expectedModel.getFilteredPersonList().size();
        expectedLastName = expectedModel.getFilteredPersonList().get(listLength - 1).getName();
        assertEquals(expectedFirstName, actualFirstName);
        assertTrue(expectedFirstName.equals(actualFirstName));
        assertNotNull(actualFirstName);
        assertNotNull(expectedFirstName);
        assertFalse(actualFirstName.equals(expectedLastName));

        // (Descending vs Ascending) w Reverse
        expectedModel.listNameDescending();
        expectedModel.listNameReversed();
        expectedModel.listNameReversed();
        model.listNameAscending();
        model.listNameReversed();
        model.listNameReversed();
        listLength = expectedModel.getFilteredPersonList().size();
        actualFirstName = model.getFilteredPersonList().get(0).getName();
        actualLastName = model.getFilteredPersonList().get(listLength - 1).getName();
        expectedLastName = expectedModel.getFilteredPersonList().get(listLength - 1).getName();
        expectedFirstName = expectedModel.getFilteredPersonList().get(0).getName();
        assertFalse(actualFirstName.equals(expectedFirstName));
        assertFalse(actualLastName.equals(expectedLastName));
        assertEquals(actualFirstName, expectedLastName);
        assertEquals(actualLastName, expectedFirstName);
        model.listNameReversed();
        actualFirstName = model.getFilteredPersonList().get(0).getName();
        expectedLastName = expectedModel.getFilteredPersonList().get(listLength - 1).getName();
        expectedFirstName = expectedModel.getFilteredPersonList().get(0).getName();
        assertEquals(expectedFirstName, actualFirstName);
        assertTrue(expectedFirstName.equals(actualFirstName));
        assertNotNull(actualFirstName);
        assertNotNull(expectedFirstName);
        assertFalse(actualFirstName.equals(expectedLastName));

    }

}
