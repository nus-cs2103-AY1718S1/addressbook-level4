# hthjthtrh
###### /java/seedu/address/logic/parser/EditGroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditGroupCommand;

public class EditGroupCommandParserTest {

    private static final String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT + EditGroupCommand.MESSAGE_USAGE;

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    public void parse_validArgument_success() {
        // indicate by group name
        assertParseSuccess(parser, "  testGrp1 gn TestGrp1!",
                new EditGroupCommand("testGrp1", null, "gn", "TestGrp1!", false));

        // indicate by index
        assertParseSuccess(parser, "  2    gn       TestGrp1!   ",
                new EditGroupCommand(null, Index.fromOneBased(2), "gn", "TestGrp1!", true));

        // weird group name
        assertParseSuccess(parser, "  1!wieirdName!1    gn       ST@@lWeird   ",
                new EditGroupCommand("1!wieirdName!1", null, "gn", "ST@@lWeird", false));

        assertParseSuccess(parser, "  test   add   1  ",
                new EditGroupCommand("test", null, "add", Index.fromOneBased(1), false));

        assertParseSuccess(parser, "  2    add    2   ",
                new EditGroupCommand(null, Index.fromOneBased(2), "add", Index.fromOneBased(2), true));


        assertParseSuccess(parser, "  test   delete   1  ",
                new EditGroupCommand("test", null, "delete", Index.fromOneBased(1), false));

        assertParseSuccess(parser, "  2    delete    2   ",
                new EditGroupCommand(null, Index.fromOneBased(2), "delete", Index.fromOneBased(2), true));
    }

    @Test
    public void parse_invalidGroupName_failure() {
        testFailure("2   gn   123");
        testFailure("validName  gn  -1");
    }

    @Test
    public void parse_invalidOperation_failure() {
        testFailure("validName invalidOp validNameToo");
        testFailure("validName ADD 1");
        testFailure(" 1 something something");
    }

    @Test
    public void parse_invalidArgumentsCount_failure() {
        testFailure("validName grpName 1 2 3 4");
        testFailure("");
        testFailure("grpName 1");
        testFailure("test   add -1 1 2 3 4");
    }

    private void testFailure(String inputToTest) {
        assertParseFailure(parser, inputToTest, MESSAGE_PARSE_FAILURE);
    }
}
```
###### /java/seedu/address/logic/parser/GroupingCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupingCommand;
import seedu.address.testutil.TypicalIndexes;

public class GroupingCommandParserTest {
    private GroupingCommandParser parser = new GroupingCommandParser();
    private List<Index> testIndexes = new ArrayList<>();
    private String expectedMessage = MESSAGE_INVALID_COMMAND_FORMAT + GroupingCommand.MESSAGE_USAGE;

    @Test
    public void parse_allFieldsPresent_success() {
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        String testGrpName = "test";
        assertParseSuccess(parser, "test 1", new GroupingCommand(testGrpName, testIndexes));

        //duplicate indexes
        assertParseSuccess(parser, "test 1 1 1", new GroupingCommand(testGrpName, testIndexes));

        //multiple indexes
        testIndexes.add(TypicalIndexes.INDEX_THIRD_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_THIRDTEENTH_PERSON);
        assertParseSuccess(parser, "test 1 3 13", new GroupingCommand(testGrpName, testIndexes));

        assertParseSuccess(parser, "     1_Sum2Weird``Namee      1    3    13 ",
                new GroupingCommand("1_Sum2Weird``Namee", testIndexes));
    }

    @Test
    public void parse_indexFieldsMissing_failure() {
        assertParseFailure(parser, "test", expectedMessage);

        //trailing white spaces
        assertParseFailure(parser, "   test    ", expectedMessage);
    }

    @Test
    public void parse_emptyArgument_failure() {
        assertParseFailure(parser, "",  expectedMessage);
    }

    @Test
    public void parse_invalidGroupName_failure() {
        assertParseFailure(parser, "1234321 1 3 5 ", expectedMessage);
        assertParseFailure(parser, "-1 1 3 5 ", expectedMessage);
        assertParseFailure(parser, "0 1 3 5 ", expectedMessage);
    }
}
```
###### /java/seedu/address/logic/parser/ViewGroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewGroupCommand;

public class ViewGroupCommandParserTest {

    private static final String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT + ViewGroupCommand.MESSAGE_USAGE;

    private ViewGroupCommandParser parser = new ViewGroupCommandParser();

    @Test
    public void parse_groupName_success() {
        assertParseSuccess(parser, "testGroupName", new ViewGroupCommand("testGroupName"));
        assertParseSuccess(parser, "     testGroupName      ", new ViewGroupCommand("testGroupName"));
        assertParseSuccess(parser, "        1GrpNameWithNumber  ", new ViewGroupCommand("1GrpNameWithNumber"));
    }

    @Test
    public void parse_index_success() {
        Index testIdx = Index.fromOneBased(1);
        assertParseSuccess(parser, "1", new ViewGroupCommand(testIdx));
        assertParseSuccess(parser, "  1      ", new ViewGroupCommand(testIdx));
        testIdx = Index.fromOneBased(999);
        assertParseSuccess(parser, "   999    ", new ViewGroupCommand(testIdx));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, " -10  ", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, " 0  ", MESSAGE_PARSE_FAILURE);
    }

    @Test
    public void parse_missingArgument_failure() {
        assertParseFailure(parser, "", MESSAGE_PARSE_FAILURE);
    }

    @Test
    public void parse_multipleArguments_failure() {
        assertParseFailure(parser, "hi lol", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "hi 1 blah blah", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "1 lol blah blahh", MESSAGE_PARSE_FAILURE);
    }
}
```
###### /java/seedu/address/logic/parser/DeleteGroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupCommand;

public class DeleteGroupCommandParserTest {

    private static final String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT
            + DeleteGroupCommand.MESSAGE_USAGE;
    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();


    @Test
    public void parse_correctGrpNameFormat_success() {
        assertParseSuccess(parser, "testGroupName", new DeleteGroupCommand("testGroupName", false));
        assertParseSuccess(parser, "     testGroupName      ", new DeleteGroupCommand("testGroupName", false));
        assertParseSuccess(parser, "1GrpNameWithNumber", new DeleteGroupCommand("1GrpNameWithNumber", false));
    }

    @Test
    public void parse_correctIndexFormat_success() {
        assertParseSuccess(parser, "   1  ", new DeleteGroupCommand(Index.fromOneBased(1), true));
    }

    @Test
    public void parse_multipleArgument_failure() {
        testFailure("blahh blahhh");
        testFailure("blahh 1 2");
        testFailure("  1 2 3 4");
    }

    @Test
    public void parse_invalidGrpNameFormat_failure() {
        testFailure("-1");
        testFailure("0");
    }


    private void testFailure(String inputToTest) {
        assertParseFailure(parser, inputToTest, MESSAGE_PARSE_FAILURE);
    }
}
```
###### /java/seedu/address/logic/parser/DeleteCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VALUE_ARGUMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.TypicalIndexes;
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private ArrayList<Index> testIndexes = new ArrayList<Index>();

    private DeleteCommandParser parser = new DeleteCommandParser();
    private final String deleteParseEmptyArg = MESSAGE_INVALID_COMMAND_FORMAT + DeleteCommand.MESSAGE_USAGE;
    private final String deleteParseInvalidValue = MESSAGE_INVALID_VALUE_ARGUMENT + DeleteCommand.MESSAGE_USAGE;

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new DeleteCommand(testIndexes));

        testIndexes.clear();
        testIndexes.add(TypicalIndexes.INDEX_FIFTH_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_EIGHTH_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_THIRDTEENTH_PERSON);
        assertParseSuccess(parser, "5 8 13", new DeleteCommand(testIndexes));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", deleteParseEmptyArg);
        assertParseFailure(parser, " ", deleteParseEmptyArg);
        assertParseFailure(parser, "a", deleteParseInvalidValue);
        assertParseFailure(parser, "1 3 a", deleteParseInvalidValue);

    }
}
```
###### /java/seedu/address/logic/commands/EditGroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_ADD_PERSON_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_CHANGE_NAME_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_GROUP_NONEXISTENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TypicalGroups;
import seedu.address.testutil.TypicalPersons;

public class EditGroupCommandTest {

    private Model model;
    private Model expectedModel;
    private EditGroupCommand editGrpCmd;
    private List<ReadOnlyPerson> typicalPersons = TypicalPersons.getTypicalPersons();

    @Before
    public void setUp() {
        resetModel();
    }

    private void resetModel() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    private void prepareCommand(String grpName, Index grpIndex, String op, String newName, boolean indicateByIdx) {
        editGrpCmd = new EditGroupCommand(grpName, grpIndex, op, newName, indicateByIdx);
        editGrpCmd.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    private void prepareCommand(String grpName, Index grpIndex, String op, Index personIdx, boolean indicateByIdx) {
        editGrpCmd = new EditGroupCommand(grpName, grpIndex, op, personIdx, indicateByIdx);
        editGrpCmd.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_changeNameByName_success() throws DuplicateGroupException {
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        Group testGroup = testGrps.get(0);
        expectedModel.setGrpName(testGroup, "validName");

        // by group name
        prepareCommand("TestGrp1", null, "gn", "validName", false);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_CHANGE_NAME_SUCCESS, "TestGrp1", "validName"),
                expectedModel);
    }

    @Test
    public void execute_changeNameByIndex_success() throws DuplicateGroupException {
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        Group testGroup = testGrps.get(0);
        expectedModel.setGrpName(testGroup, "validName");

        // by index
        prepareCommand(null, Index.fromOneBased(1), "gn", "validName", true);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_CHANGE_NAME_SUCCESS, "TestGrp1", "validName"),
                expectedModel);
    }


    @Test
    public void execute_addDeletePerson_success() throws DuplicatePersonException, PersonNotFoundException {

        // by group name
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        Group testGroup = testGrps.get(0);
        ReadOnlyPerson person = typicalPersons.get(4);
        expectedModel.addPersonToGroup(testGroup, person);

        prepareCommand("TestGrp3", null, "add", Index.fromOneBased(5), false);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_ADD_PERSON_SUCCESS, "TestGrp3",
                person.toString()),
                expectedModel);

        // deleting the person that was added
        expectedModel.removePersonFromGroup(testGroup, person);
        prepareCommand("TestGrp3", null, "delete", Index.fromOneBased(4), false);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_DELETE_PERSON_SUCCESS, "TestGrp3",
                person.toString()),
                expectedModel);


        // by index
        expectedModel.addPersonToGroup(testGroup, person);

        prepareCommand(null, Index.fromOneBased(2), "add", Index.fromOneBased(5), true);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_ADD_PERSON_SUCCESS, "TestGrp2",
                person.toString()),
                expectedModel);

        // deleting the person that was added
        expectedModel.removePersonFromGroup(testGroup, person);
        prepareCommand(null, Index.fromOneBased(2), "delete", Index.fromOneBased(3), true);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_DELETE_PERSON_SUCCESS, "TestGrp2",
                person.toString()),
                expectedModel);
    }


    @Test
    public void execute_groupNonExistent_failure() {
        // change name op
        prepareCommand("nonExistentGroup", null, "gn", "validName", false);
        assertCommandFailure(editGrpCmd, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_GROUP_NONEXISTENT);

        // add / delete op
        prepareCommand("nonExistentGroup", null, "add", Index.fromOneBased(1), false);
        assertCommandFailure(editGrpCmd, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_GROUP_NONEXISTENT);


        prepareCommand(null, Index.fromOneBased(20), "gn", "validName", true);
        assertCommandFailure(editGrpCmd, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }


    @Test
    public void execute_addDeleteOutOfRangeIndex_failure() {
        prepareCommand("TestGrp3", null, "add", Index.fromOneBased(20), false);
        // exception header for this exception is null
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        prepareCommand("TestGrp1", null, "delete", Index.fromOneBased(20), false);
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        prepareCommand(null, Index.fromOneBased(1), "add", Index.fromOneBased(20), true);
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addDuplicatePerson_failure() {
        prepareCommand("TestGrp3", null, "add", Index.fromOneBased(1), false);
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + MESSAGE_DUPLICATE_PERSON);
    }


    @Test
    public void equals() {
        EditGroupCommand editGroupCommand1 = new EditGroupCommand("testName1", null,
                "add", Index.fromOneBased(2), false);
        EditGroupCommand editGroupCommand2 = new EditGroupCommand("testName1", null,
                "add", Index.fromOneBased(2), false);
        EditGroupCommand editGroupCommand3 = new EditGroupCommand("DiffName", null,
                "gn", "AnotherName", false);

        // same object -> returns true
        assertTrue(editGroupCommand1.equals(editGroupCommand1));

        // same value -> return true
        assertTrue(editGroupCommand1.equals(editGroupCommand2));

        // different argument type -> return false
        assertFalse(editGroupCommand1.equals(editGroupCommand3));

        // different types -> returns false
        assertFalse(editGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(editGroupCommand1.equals(null));
    }
}
```
###### /java/seedu/address/logic/commands/DeleteCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ArrayList<Index> testIndexes = new ArrayList<>();

    @Test
    public void execute_validIndexUnfilteredListSinglePerson_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        testIndexes.clear();
        testIndexes.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = prepareCommand(testIndexes);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        ArrayList<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        personsToDelete.add(personToDelete);

        assertCommandSuccess(deleteCommand, model,
                DeleteCommand.getSb(personsToDelete), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredListSingleOutOfBound_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        testIndexes.clear();
        testIndexes.add(outOfBoundIndex);

        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        assertCommandFailure(deleteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        testIndexes.clear();
        testIndexes.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        ArrayList<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        personsToDelete.add(personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model,
                DeleteCommand.getSb(personsToDelete), expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        testIndexes.clear();
        testIndexes.add(outOfBoundIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        assertCommandFailure(deleteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
    }

    @Test
    public void equals() {

        testIndexes.clear();
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(testIndexes);
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(testIndexes);

        ArrayList<Index> testIndexes2 = new ArrayList<>();
        testIndexes2.add(TypicalIndexes.INDEX_SECOND_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(testIndexes2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(ArrayList<Index> index) {
        //testIndexes.clear();
        DeleteCommand deleteCommand = new DeleteCommand(testIndexes);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### /java/seedu/address/logic/commands/DeleteGroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_INDEXOUTOFBOUND_GROUP;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_NONEXISTENT_GROUP;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.TypicalGroups;

public class DeleteGroupCommandTest {

    private Model model;
    private Model expectedModel;
    private DeleteGroupCommand deleteGroupCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    private void prepareCommand(String grpName) {
        deleteGroupCommand = new DeleteGroupCommand(grpName, false);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    private void prepareCommand(Index idx) {
        deleteGroupCommand = new DeleteGroupCommand(idx, true);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_grpNameExist_success() {
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        expectedModel.deleteGroup(testGrps.get(2));

        prepareCommand("TestGrp3");
        assertCommandSuccess(deleteGroupCommand, model,
                String.format(MESSAGE_DELETE_GROUP_SUCCESS, "TestGrp3"), expectedModel);
    }

    @Test
    public void execute_grpIndexValid_success() {
        Index testIdx = Index.fromOneBased(4);
        expectedModel.deleteGroup(new TypicalGroups().getTypicalGroups().get(testIdx.getZeroBased()));
        prepareCommand(testIdx);
        assertCommandSuccess(deleteGroupCommand, model, String.format(MESSAGE_DELETE_GROUP_SUCCESS, "TestGrp4"),
                expectedModel);
    }

    @Test
    public void execute_grpNameNonExistent_failure() {
        String expectedFailureMessage = MESSAGE_EXECUTION_FAILURE
                + String.format(MESSAGE_NONEXISTENT_GROUP, "Non_Existent");
        prepareCommand("Non_Existent");
        assertCommandFailure(deleteGroupCommand, model, expectedFailureMessage);

        expectedFailureMessage = MESSAGE_EXECUTION_FAILURE + String.format(MESSAGE_NONEXISTENT_GROUP, "12Wot");
        prepareCommand("12Wot");
        assertCommandFailure(deleteGroupCommand, model, expectedFailureMessage);
    }

    @Test
    public void execute_grpIndexInvalid_failure() {
        String expectedFailureMessage = MESSAGE_EXECUTION_FAILURE + MESSAGE_INDEXOUTOFBOUND_GROUP;
        prepareCommand(Index.fromOneBased(10));
        assertCommandFailure(deleteGroupCommand, model, expectedFailureMessage);

        prepareCommand(Index.fromOneBased(5));
        assertCommandFailure(deleteGroupCommand, model, expectedFailureMessage);
    }

    @Test
    public void equals() {
        DeleteGroupCommand deleteGroupCommand1 = new DeleteGroupCommand("HOT", false);
        DeleteGroupCommand deleteGroupCommand2 = new DeleteGroupCommand("HOT", false);
        DeleteGroupCommand deleteGroupCommand3 = new DeleteGroupCommand("Not_So_Hot", false);

        // same object -> returns true
        assertTrue(deleteGroupCommand1.equals(deleteGroupCommand1));

        // same value -> return true
        assertTrue(deleteGroupCommand1.equals(deleteGroupCommand2));

        // different argument type -> return false
        assertFalse(deleteGroupCommand1.equals(deleteGroupCommand3));

        // different types -> returns false
        assertFalse(deleteGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(deleteGroupCommand1.equals(null));
    }
}
```
###### /java/seedu/address/logic/commands/GroupingCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.person.ReadOnlyPerson;

public class GroupingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ArrayList<Index> testIndexes = new ArrayList<>();

    @Test
    public void execute_newGroup_createSuccessful() {
        List<Index> testIdx = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(3));
        testSuccess("newGroup", testIdx);
        testSuccess("##wEird##", testIdx);
    }

    @Test
    public void execute_addExistingGroup_failure() {

        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(1));
        testIndexes.add(Index.fromOneBased(3));

        GroupingCommand testCommand = prepareCommand("TestGrp1", testIndexes);

        String expectedMessage = MESSAGE_EXECUTION_FAILURE + GroupingCommand.MESSAGE_DUPLICATE_GROUP_NAME;

        assertCommandFailure(testCommand, model, expectedMessage);
    }

    @Test
    public void execute_addNewGroupNoValidIndex_failure() {
        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(9));
        testIndexes.add(Index.fromOneBased(10));

        GroupingCommand testCommand = prepareCommand("newGroup", testIndexes);

        String expectedMessage = MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_INDEX_ALL;

        assertCommandFailure(testCommand, model, expectedMessage);
    }

    @Test
    public void equals() {

        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(1));

        GroupingCommand groupFirstCommand = new GroupingCommand("sample", testIndexes);
        GroupingCommand groupFirstCommandCopy = new GroupingCommand("sample", testIndexes);
        GroupingCommand groupSecondCommand = new GroupingCommand("anotherSample", testIndexes);

        // same object -> returns true
        assertTrue(groupFirstCommand.equals(groupFirstCommand));

        // same values -> returns true
        assertTrue(groupFirstCommand.equals(groupFirstCommandCopy));

        // different types -> returns false
        assertFalse(groupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(groupFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(groupFirstCommand.equals(groupSecondCommand));

    }

    /**
     * helper function to create a grouping command
     * @param grpName
     * @param index of people to include in the group
     * @return the grouping command
     */
    private GroupingCommand prepareCommand(String grpName, List<Index> index) {
        //testIndexes.clear();
        GroupingCommand grpCommand = new GroupingCommand(grpName, index);
        grpCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return grpCommand;
    }

    /**
     * helper function to test correctness
     * @param grpName
     * @param testIndexes
     */
    private void testSuccess(String grpName, List<Index> testIndexes) {
        GroupingCommand testCommand = prepareCommand(grpName, testIndexes);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(expectedModel.getAddressBook().getPersonList().get(testIndexes.get(0).getZeroBased()));
        personList.add(expectedModel.getAddressBook().getPersonList().get(testIndexes.get(1).getZeroBased()));

        try {
            expectedModel.createGroup(grpName, personList);
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        }

        String expectedMessage = GroupingCommand.getSb(grpName, personList);

        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/ViewGroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewGroupCommand.MESSAGE_GROUPING_PERSON_SUCCESS;
import static seedu.address.logic.commands.ViewGroupCommand.MESSAGE_GROUP_NONEXISTENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.TypicalGroups;

public class ViewGroupCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewGroupCommand viewGrpCmd;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    /**
     * creates the viewgroupcommand depending on grpName and idx
     */
    private ViewGroupCommand prepareCommand(String grpName, Index idx) {
        if (grpName != null) {
            viewGrpCmd = new ViewGroupCommand(grpName);
        } else {
            viewGrpCmd = new ViewGroupCommand(idx);
        }
        viewGrpCmd.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewGrpCmd;
    }


    @Test
    public void execute_grpNameGrpExist_success() {
        TypicalGroups typicalGroups = new TypicalGroups();
        Group testGroup = typicalGroups.getTypicalGroups().get(2);

        ViewGroupCommand viewGroupCommand = prepareCommand("TestGrp3", null);
        String expectedMessage = String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                testGroup.getPersonList().size(), testGroup.getGrpName());
        assertCommandSuccess(viewGroupCommand, model, expectedMessage, expectedModel);
    }



    @Test
    public void execute_grpIndexGrpExist_success() {
        TypicalGroups typicalGroups = new TypicalGroups();
        Group testGroup = typicalGroups.getTypicalGroups().get(2);

        ViewGroupCommand viewGroupCommand = prepareCommand(null, Index.fromOneBased(3));
        String expectedMessage = String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                testGroup.getPersonList().size(), testGroup.getGrpName());
        assertCommandSuccess(viewGroupCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_grpNonExistent_failure() {
        ViewGroupCommand viewGroupCommand = prepareCommand("DoesntExist", null);
        assertCommandFailure(viewGroupCommand, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_GROUP_NONEXISTENT);
    }

    @Test
    public void execute_indexOutOfBound_failure() {
        ViewGroupCommand viewGroupCommand = prepareCommand(null, Index.fromOneBased(10));
        assertCommandFailure(viewGroupCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewGroupCommand viewGroupCommand1 = new ViewGroupCommand("GroupName");
        ViewGroupCommand viewGroupCommand2 = new ViewGroupCommand("GroupName");
        ViewGroupCommand viewGroupCommand3 = new ViewGroupCommand("Group");
        ViewGroupCommand viewGroupCommand4 = new ViewGroupCommand(Index.fromOneBased(10));
        ViewGroupCommand viewGroupCommand5 = new ViewGroupCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(viewGroupCommand1.equals(viewGroupCommand1));

        // same value -> return true
        assertTrue(viewGroupCommand1.equals(viewGroupCommand2));

        // same object -> return true
        assertTrue(viewGroupCommand4.equals(viewGroupCommand4));

        // different index value -> return false
        assertFalse(viewGroupCommand4.equals(viewGroupCommand5));

        // different grp name value -> return false
        assertFalse(viewGroupCommand1.equals(viewGroupCommand3));

        // different argument type -> return false
        assertFalse(viewGroupCommand1.equals(viewGroupCommand4));

        // different types -> returns false
        assertFalse(viewGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(viewGroupCommand1.equals(null));
    }
}
```
###### /java/seedu/address/logic/commands/UndoCommandTest.java
``` java
    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //need to refresh the Undo command since previously its step was reduced to 0
        undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack. Refresh the undo command again
        undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        assertCommandFailure(undoCommand, model, MESSAGE_EXECUTION_FAILURE + UndoCommand.MESSAGE_FAILURE);
    }
```
###### /java/seedu/address/model/UniqueGroupListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.UniqueGroupList;

public class UniqueGroupListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/testutil/AddressBookBuilder.java
``` java
    /**
     * adds a new group into the addressbook being built
     * @param grp
     * @return
     */
    public AddressBookBuilder withGroup(Group grp) {
        try {
            addressBook.addGroup(new Group(grp));
        } catch (DuplicatePersonException e) {
            throw new IllegalArgumentException("person is expected to be unique.");
        } catch (DuplicateGroupException e) {
            throw new IllegalArgumentException("group is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/TypicalGroups.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * to create typical groups
 */
public class TypicalGroups {
    private Group testGroup1;
    private Group testGroup2;
    private Group testGroup3;
    private Group testGroup4;

    public TypicalGroups() {
        testGroup1 = new Group("TestGrp1");
        try {
            testGroup1.add(TypicalPersons.HOON);
            testGroup1.add(TypicalPersons.IDA);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

        testGroup2 = new Group("TestGrp2");
        try {
            testGroup2.add(TypicalPersons.HOON);
            testGroup2.add(TypicalPersons.ALICE);
            testGroup2.add(TypicalPersons.AMY);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

        testGroup3 = new Group("TestGrp3");
        try {
            testGroup3.add(TypicalPersons.ALICE);
            testGroup3.add(TypicalPersons.DANIEL);
            testGroup3.add(TypicalPersons.CARL);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

        try {
            testGroup4 = new Group(testGroup3);
            testGroup4.setGrpName("TestGrp4");
            testGroup4.add(TypicalPersons.ELLE);
            testGroup4.add(TypicalPersons.GEORGE);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

    }

    public List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(testGroup1, testGroup2, testGroup3, testGroup4));
    }
}
```
###### /java/guitests/guihandles/GroupListPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.group.Group;
import seedu.address.ui.GroupCard;

/**
 * Provides a handle for {@code GroupListPanel} containing the list of {@code GroupCard}.
 */
public class GroupListPanelHandle extends  NodeHandle<ListView<GroupCard>> {
    public static final String GROUP_LIST_VIEW_ID = "#groupListView";

    private Optional<GroupCard> lastRememberedSelectedGroupCard;

    public GroupListPanelHandle(ListView<GroupCard> groupListPanelNode) {
        super(groupListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code GroupCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public GroupCardHandle getHandleToSelectedCard() {
        List<GroupCard> groupList = getRootNode().getSelectionModel().getSelectedItems();

        if (groupList.size() != 1) {
            throw new AssertionError("Group list size expected 1.");
        }

        return new GroupCardHandle(groupList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<GroupCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the group.
     */
    public void navigateToCard(Group group) {
        List<GroupCard> cards = getRootNode().getItems();
        Optional<GroupCard> matchingCard = cards.stream().filter(card -> card.group.equals(group)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Group does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the group card handle of a group associated with the {@code index} in the list.
     */
    public GroupCardHandle getGroupCardHandle(int index) {
        return getGroupCardHandle(getRootNode().getItems().get(index).group);
    }

    /**
     * Returns the {@code GroupCardHandle} of the specified {@code group} in the list.
     */
    public GroupCardHandle getGroupCardHandle(Group group) {
        Optional<GroupCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.group.equals(group))
                .map(card -> new GroupCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Group does not exist."));
    }

    /**
     * Selects the {@code GroupCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code GroupCard} in the list.
     */
    public void rememberSelectedGroupCard() {
        List<GroupCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedGroupCard = Optional.empty();
        } else {
            lastRememberedSelectedGroupCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code GroupCard} is different from the value remembered by the most recent
     * {@code rememberSelectedGroupCard()} call.
     */
    public boolean isSelectedGroupCardChanged() {
        List<GroupCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedGroupCard.isPresent();
        } else {
            return !lastRememberedSelectedGroupCard.isPresent()
                    || !lastRememberedSelectedGroupCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

```
###### /java/guitests/guihandles/GroupCardHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a group card in the group list panel.
 */
public class GroupCardHandle extends  NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String GROUPNAME_FIELD_ID = "#grpName";
    private static final String FIRSTP_FIELD_ID = "#firstGroup";
    private static final String SECONDP_FIELD_ID = "#secondGroup";
    private static final String THIRDP_FIELD_ID = "#thirdGroup";
    private static final String ELLIPSIS_FIELD_ID = "#ellipsis";

    private final Label idLabel;
    private final Label grpNameLabel;
    private final Label firstPLabel;
    private final Label secondPLabel;
    private final Label thirdPLabel;
    private final Label ellipsisLabel;

    public GroupCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.grpNameLabel = getChildNode(GROUPNAME_FIELD_ID);
        this.firstPLabel = getChildNode(FIRSTP_FIELD_ID);
        this.secondPLabel = getChildNode(SECONDP_FIELD_ID);
        this.thirdPLabel = getChildNode(THIRDP_FIELD_ID);
        this.ellipsisLabel = getChildNode(ELLIPSIS_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return grpNameLabel.getText();
    }

    public String getFirstPerson() {
        return firstPLabel.getText();
    }

    public String getSecondPerson() {
        return secondPLabel.getText();
    }

    public String getThirdPerson() {
        return thirdPLabel.getText();
    }

    public String getEllipsis() {
        return ellipsisLabel.getText();
    }

}
```
