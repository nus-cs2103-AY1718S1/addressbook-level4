// @@author ahmadalkaff
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_GER1000;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.BOB_EDITED;
import static seedu.address.testutil.TypicalPersons.CARRIE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookPersons;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class ListModuleCommandTest {
    private Model model;
    private Model expectedModel;
    private ListModuleCommand listModuleCommand;
    private ArrayList<Module> moduleList;
    private ArrayList<String> tempList;
    private String expectedMessage;

    public final String listModuleMessage = listModuleCommand.MESSAGE_SUCCESS + "\n" + "Module names: ";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookPersons(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        moduleList = new ArrayList<>();
        tempList = new ArrayList<>();
        listModuleCommand = new ListModuleCommand();
        listModuleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        for (ReadOnlyPerson person : model.getAddressBook().getPersonList()) {
            for (Module mod : person.getModules()) {
                if (!moduleList.contains(mod)) {
                    moduleList.add(mod);
                }
            }
        }
        for (Module m : moduleList) {
            tempList.add(m.moduleName);
        }
        Collections.sort(tempList);

        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");

    }

    @Test
    public void execute_showsCorrectModuleList() {
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewPersonWithNewModule_showsCorrectModuleList()
            throws DuplicatePersonException {
        model.addPerson(AMY);
        expectedModel.addPerson(AMY);
        tempList.add(VALID_MODULE_CS2101);
        Collections.sort(tempList);
        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewPersonWithNoModule_showsCorrectModList()
            throws DuplicatePersonException {
        model.addPerson(CARRIE);
        expectedModel.addPerson(CARRIE);
        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editPersonWithPresentModule_showsCorrectModuleList()
            throws DuplicatePersonException, PersonNotFoundException {
        model.addPerson(BOB);
        expectedModel.addPerson(BOB);
        tempList.add(VALID_MODULE_GER1000);
        tempList.add(VALID_MODULE_CS2101);

        model.updatePerson(BOB, BOB_EDITED);
        expectedModel.updatePerson(BOB, BOB_EDITED);
        tempList.remove(VALID_MODULE_GER1000);
        tempList.add(VALID_MODULE_CS2103);
        Collections.sort(tempList);
        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deletePersonWithUniqueModule_showsCorrectModuleList()
            throws DuplicatePersonException, PersonNotFoundException {
        model.addPerson(AMY);
        model.addPerson(BOB);
        model.addPerson(CARRIE);
        expectedModel.addPerson(AMY);
        expectedModel.addPerson(BOB);
        expectedModel.addPerson(CARRIE);
        tempList.add(VALID_MODULE_CS2101);
        tempList.add(VALID_MODULE_GER1000);

        model.deletePerson(BOB);
        expectedModel.deletePerson(BOB);
        tempList.remove(VALID_MODULE_GER1000);
        Collections.sort(tempList);
        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }
}
