package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RelPathCommand.MESSAGE_NO_PATH;
import static seedu.address.logic.commands.RelPathCommand.MESSAGE_PATH_FOUND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON_WITH_REL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON_WITH_REL;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON_WITH_REL;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.JAMES;
import static seedu.address.testutil.TypicalPersons.KELVIN;
import static seedu.address.testutil.TypicalPersons.LISA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRelationships;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.graph.GraphWrapper;

//@@author Xenonym
/**
 * Contains integration tests (interaction with the Model) and unit tests for RelPathCommand.
 */
public class RelPathCommandTest {

    private Model model;
    private Model expectedModel;
    private GraphWrapper graph;
    private RelPathCommand relPathCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithRelationships(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        graph = GraphWrapper.getInstance();
        graph.buildGraph(model);
    }

    @Test
    public void execute_hadUndirectedPath_pathFound() {
        relPathCommand = new RelPathCommand(INDEX_FIRST_PERSON_WITH_REL, INDEX_SECOND_PERSON_WITH_REL);
        relPathCommand.setData(model, null, null, null);
        String expectedMessage = String.format(MESSAGE_PATH_FOUND, JAMES.getName().fullName,
                KELVIN.getName().fullName);

        assertCommandSuccess(relPathCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_hasDirectedPath_pathFound() {
        relPathCommand = new RelPathCommand(INDEX_SECOND_PERSON_WITH_REL, INDEX_THIRD_PERSON_WITH_REL);
        relPathCommand.setData(model, null, null, null);
        String expectedMessage = String.format(MESSAGE_PATH_FOUND, KELVIN.getName().fullName,
                LISA.getName().fullName);

        assertCommandSuccess(relPathCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_hasNoPath_pathNotFound() {
        relPathCommand = new RelPathCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        relPathCommand.setData(model, null, null, null);
        String expectedMessage = String.format(MESSAGE_NO_PATH, ALICE.getName().fullName, BENSON.getName().fullName);

        assertCommandSuccess(relPathCommand, model, expectedMessage, expectedModel);
    }
}
