package seedu.address.logic.commands;
//@@author jeffreygohkw
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code PrivacyLevelCommand}.
 */
public class PrivacyLevelCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        //valid levels
        PrivacyLevelCommand onePlc = prepareCommand(1);
        PrivacyLevelCommand twoPlc = prepareCommand(2);
        PrivacyLevelCommand threePlc = prepareCommand(3);

        assertExecutionSuccess(onePlc);
        assertExecutionSuccess(twoPlc);
        assertExecutionSuccess(threePlc);
    }

    @Test
    public void execute_invalidIndex_failure() {
        //Negative level
        PrivacyLevelCommand negativePlc = prepareCommand(-1);
        //Zero level
        PrivacyLevelCommand zeroPlc = prepareCommand(0);
        //Level greater than the maximum level allowed
        PrivacyLevelCommand tooBigPlc = prepareCommand(5);

        assertExecutionFailure(negativePlc, PrivacyLevelCommand.WRONG_PRIVACY_LEVEL_MESSAGE);
        assertExecutionFailure(zeroPlc, PrivacyLevelCommand.WRONG_PRIVACY_LEVEL_MESSAGE);
        assertExecutionFailure(tooBigPlc, PrivacyLevelCommand.WRONG_PRIVACY_LEVEL_MESSAGE);
    }

    /**
     * Executes a {@code PrivacyLevelCommand} and checks that the privacy level of the model and each person has been
     * changed to that of the level as specified in the input (@code PrivacyLevelCommand)
     */
    private void assertExecutionSuccess(PrivacyLevelCommand plc) {
        CommandResult commandResult;
        try {
            commandResult = plc.execute();
            assertEquals(String.format(PrivacyLevelCommand.CHANGE_PRIVACY_LEVEL_SUCCESS, plc.getLevel()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        //Check the model's privacy level
        assertTrue(model.getPrivacyLevel() == plc.getLevel());

        //Iterate through the list of persons in the model
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            //Check the person's Privacy Level
            ReadOnlyPerson p = model.getAddressBook().getPersonList().get(i);

            //Check Privacy Level of all fields of each person that can be private
            assertTrue(p.getName().getPrivacyLevel() == plc.getLevel());
            assertTrue(p.getPhone().getPrivacyLevel() == plc.getLevel());
            assertTrue(p.getEmail().getPrivacyLevel() == plc.getLevel());
            assertTrue(p.getAddress().getPrivacyLevel() == plc.getLevel());
            assertTrue(p.getRemark().getPrivacyLevel() == plc.getLevel());
        }
    }

    /**
     * Executes a {@code PrivacyLevelCommand} and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}
     */
    private void assertExecutionFailure(PrivacyLevelCommand plc, String expectedMessage) {
        try {
            plc.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            Assert.assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code LocateCommand} with parameters {@code index}.
     */
    private PrivacyLevelCommand prepareCommand(int level) {
        PrivacyLevelCommand privacyLevelCommand = new PrivacyLevelCommand(level);
        privacyLevelCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return privacyLevelCommand;
    }
}
