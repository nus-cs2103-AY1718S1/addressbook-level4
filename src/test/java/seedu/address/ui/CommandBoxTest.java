package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBox commandBoxForTesting;
    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        CommandBox commandBox = new CommandBox(logic);
        commandBoxForTesting = commandBox;
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBoxStartingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBoxStartingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBoxHandleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void handleKeyPressEscape() {
        //Command Box text field should contain nothing the first time
        guiRobot.push(KeyCode.ESCAPE);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //Enter text in command box field
        guiRobot.write("Testing input");
        //Confirms that text has been written
        assertTrue("Testing input".equals(commandBoxHandle.getInput()));
        //Ensures that text has not been removed by .getInput method
        assertTrue("Testing input".equals(commandBoxHandle.getInput()));
        //Deletes text and ensure text is reset
        guiRobot.push(KeyCode.ESCAPE);
        assertFalse("Testing input".equals(commandBoxHandle.getInput()));
        assertTrue("".equals(commandBoxHandle.getInput()));
    }

    @Test
    public void handleKeyPressAlt() {
        //Alt shifts the caret all the way left
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        //Setting up of sandbox environment for testing
        guiRobot.write("Test");
        assertTrue("Test".equals(mySandBox.getText()));

        assertTrue(mySandBox.getCaretPosition() == commandBoxHandle.getInput().length());
        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.ALT);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 4);
        assertTrue(mySandBox.getCaretPosition() == 0);
    }

    @Test
    public void handleKeyPressControl() {
        //Alt shifts the caret all the way right
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        //Setting up of sandbox environment for testing
        guiRobot.write("Test");
        assertTrue("Test".equals(mySandBox.getText()));

        assertTrue(mySandBox.getCaretPosition() == commandBoxHandle.getInput().length());
        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.ALT);
        //Ensure caret is at the left
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 4);
        assertTrue(mySandBox.getCaretPosition() == 0);
        //Push caret to right
        guiRobot.push(KeyCode.CONTROL);
        //Ensure caret is at the right
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertTrue(mySandBox.getCaretPosition() == 4);
    }

    @Test
    public void testGetTextField() {
        TextField myTextField = commandBoxForTesting.getCommandTextField();
        guiRobot.write("Same TextField Test");
        //Same text field -> Returns true
        assertTrue(myTextField.getText().equals(commandBoxForTesting.getCommandTextField().getText()));
        //Other values -> Returns false
        assertNotNull(myTextField);
        assertFalse(myTextField.equals(1));
    }

    @Test
    public void handleKeyPressStartingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(KeyCode.DOWN, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");

        // two commands (latest command is failure)
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.UP, thirdCommand);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPressStartingWithDown() {
        // empty history
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // two commands
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, thirdCommand);
    }

    /**
     * Runs a command that fails, then verifies that <br>
     * - the text remains <br>
     * - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     * - the text is cleared <br>
     * - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }
}
