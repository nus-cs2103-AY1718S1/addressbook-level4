package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Account;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalIndexes;
import seedu.address.ui.UiManager;

//@@author tingtx
public class GroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new
            Account(), new Config());

    @Test
    public void execute_showAllGroups_success() throws Exception {
        List<Index> indexes = new ArrayList<>();
        String showAll = "SHOWALL";

        GroupCommand groupCommand = prepareCommand(indexes, showAll);

        String expectedMessage = "Groups:  " + model.getGroupList().toString().replaceAll("\\[", "")
                .replaceAll("\\]", "");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.getGroupList();

        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_groupOnePerson_success() throws Exception {
        Person groupedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased()))
                .withGroup("JUNITTest").build();
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);

        GroupCommand groupCommand = prepareCommand(indexes, groupedPerson.getGroup().value);

        String expectedMessage = GroupCommand.MESSAGE_GROUP_PERSON_SUCCESS + "JUNITTest";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), groupedPerson);

        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_groupMultiPersons_success() throws Exception {
        Person groupedPersonOne = new PersonBuilder(model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased()))
                .withGroup("JUNITTest").build();
        Person groupedPersonTwo = new PersonBuilder(model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_SECOND_PERSON.getZeroBased()))
                .withGroup("JUNITTest").build();
        Person groupedPersonThree = new PersonBuilder(model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_THIRD_PERSON.getZeroBased()))
                .withGroup("JUNITTest").build();

        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        indexes.add(INDEX_THIRD_PERSON);

        GroupCommand groupCommand = prepareCommand(indexes, groupedPersonOne.getGroup().value);

        String expectedMessage = GroupCommand.MESSAGE_GROUP_PERSON_SUCCESS + "JUNITTest";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), groupedPersonOne);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), groupedPersonTwo);
        expectedModel.updatePerson(model.getFilteredPersonList().get(2), groupedPersonThree);


        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_ungroupOnePerson_success() throws Exception {
        Person groupedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased()))
                .withGroup("").build();
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);

        GroupCommand groupCommand = prepareCommand(indexes, "");

        String expectedMessage = GroupCommand.MESSAGE_UNGROUP_PERSON_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), groupedPerson);

        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_ungroupMultiPersons_success() throws Exception {
        Person groupedPersonOne = new PersonBuilder(model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased()))
                .withGroup("").build();
        Person groupedPersonTwo = new PersonBuilder(model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_SECOND_PERSON.getZeroBased()))
                .withGroup("").build();
        Person groupedPersonThree = new PersonBuilder(model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_THIRD_PERSON.getZeroBased()))
                .withGroup("").build();
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        indexes.add(INDEX_THIRD_PERSON);

        GroupCommand groupCommand = prepareCommand(indexes, groupedPersonOne.getGroup().value);

        String expectedMessage = GroupCommand.MESSAGE_UNGROUP_PERSON_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), groupedPersonOne);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), groupedPersonTwo);
        expectedModel.updatePerson(model.getFilteredPersonList().get(2), groupedPersonThree);


        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList()
                .get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        Person groupedPerson = new PersonBuilder(personInFilteredList).withGroup("JUNITTest").build();
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);

        GroupCommand groupCommand = prepareCommand(indexes, groupedPerson.getGroup().value);

        String expectedMessage = GroupCommand.MESSAGE_GROUP_PERSON_SUCCESS + "JUNITTest";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), groupedPerson);

        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> indexes = new ArrayList<>();
        indexes.add(outOfBoundIndex);

        GroupCommand groupCommand = prepareCommand(indexes, VALID_GROUP_AMY);

        assertCommandFailure(groupCommand, model,
                "Index " + outOfBoundIndex.toString() + " is invalid!");
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        List<Index> indexes = new ArrayList<>();
        indexes.add(outOfBoundIndex);

        //ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GroupCommand groupCommand = prepareCommand(indexes, VALID_GROUP_AMY);

        assertCommandFailure(groupCommand, model,
                "Index " + outOfBoundIndex.toString() + " is invalid!");
    }


    @Test
    public void equals() {
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        final GroupCommand standardCommand = new GroupCommand(indexes, ALICE.getGroup().value);


        // same values -> returns true
        GroupCommand commandWithSameValues = new GroupCommand(indexes,  ALICE.getGroup().value);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> return false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_AMY))));

        // different index -> returns false
        List<Index> tempIndexes = new ArrayList<>();
        tempIndexes.add(INDEX_SECOND_PERSON);
        GroupCommand commandWithDiffIndex = new GroupCommand(tempIndexes, ALICE.getGroup().value);
        assertFalse(standardCommand.equals(commandWithDiffIndex));

        // different descriptor -> returns false
        tempIndexes.remove(INDEX_SECOND_PERSON);
        tempIndexes.add(INDEX_FIRST_PERSON);
        GroupCommand commandWithNoDesc = new GroupCommand(tempIndexes, BENSON.getGroup().value);
        assertFalse(standardCommand.equals(commandWithNoDesc));
    }

    /**
     * Returns an {@code GroupCommand} with parameters {@code index} and {@code Group}
     */
    private GroupCommand prepareCommand(List<Index> index, String group) {
        GroupCommand groupCommand = new GroupCommand(index, group);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        groupCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return groupCommand;
    }
}
