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
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CommandBoxTest extends GuiUnitTest {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_BLOODTYPE = new Prefix("b/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final String STRING_NAME = PREFIX_NAME.toString();
    public static final String STRING_PHONE = PREFIX_PHONE.toString();
    public static final String STRING_EMAIL = PREFIX_EMAIL.toString();
    public static final String STRING_ADDRESS = PREFIX_ADDRESS.toString();
    public static final String STRING_BLOODTYPE = PREFIX_BLOODTYPE.toString();
    public static final String STRING_TAG = PREFIX_TAG.toString();

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
    public void handleValidRightKeyPressLenMaxThree() {
        //This test focuses on ensuring that the key press works only for the add command
        //and hack triggers only when "a" or "add" is detected at the front of the statement.
        //Cases like "adda" "addy" "am" or "aa" will not trigger add command hack

        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "";

        //Test 1: Test for single char
        guiRobot.write(AddCommand.COMMAND_ALIAS);
        testString += AddCommand.COMMAND_ALIAS;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("      ");
        testString += "      ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));

        //Test 2: Test for String with len 2
        //String with len 2 will always fail. Even if first char is 'a', it is joined
        //with another char
        mySandBox.clear();
        testString = "";
        guiRobot.write("Ab");
        testString += "Ab";
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("     ");
        testString += "     ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));

        //Test 3: Test for String with len 3
        mySandBox.clear();
        testString = "";
        guiRobot.write(AddCommand.COMMAND_WORD);
        testString += AddCommand.COMMAND_WORD;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("     ");
        testString += "     ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));

        mySandBox.clear();
        testString = "";
        guiRobot.write("b a");
        testString += "b " + AddCommand.COMMAND_ALIAS;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("     ");
        testString += "     ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));

        mySandBox.clear();
        testString = "";
        guiRobot.write("a b");
        testString += AddCommand.COMMAND_ALIAS + " b";
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("     ");
        testString += "     ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));
    }

    @Test
    public void handleValidRightKeyPressLenAboveThree() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "";
        String backupTestString = "";

        guiRobot.write("Add");
        testString += "Add";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        guiRobot.write("a");
        testString = "a" + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.push(KeyCode.RIGHT);
        backupTestString = testString;
        testString += " " + STRING_PHONE;
        assertFalse(testString.equals(mySandBox.getText()));
        testString = backupTestString;
        mySandBox.positionCaret(1);
        guiRobot.write(" ");
        testString = Character.toString(testString.charAt(0))
                + " " + testString.substring(1);
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        guiRobot.write("    ");
        testString = "    " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_PHONE;
        testString += " " + STRING_EMAIL;
        testString += " " + STRING_ADDRESS;
        testString += " " + STRING_BLOODTYPE;
        testString += " " + STRING_TAG;
        testString += " " + STRING_TAG;
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));

    }

    @Test
    public void handleInvalidRightKeyPress() {
        //Test to ensure add command hack does not trigger as long as
        //caret is within the text

        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        guiRobot.write("Add");
        assertTrue("Add".equals(mySandBox.getText()));

        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.ALT);
        //Ensure caret is at the left
        assertTrue(mySandBox.getCaretPosition() == 0);
        //Try to trigger add hack - Nothing happens, Caret + 1
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 1);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 2);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 3);

        //Trigger add hack - n/ is concatenated
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add n/".equals(mySandBox.getText()));

    }


    @Test
    public void handleValidRightKeyPressPrefixInOrder() {
        //Add Command allows users to enter the prefix in any order
        //The Add Command hack accounts for missing prefix or jump in prefix but for this test
        //it will focus on testing under the assumption that the prefix in the right order: n p e a b t

        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "add";

        guiRobot.write("add");
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue((testString).equals(mySandBox.getText()));
        //Even if user did not enter any input, p/ will be automatically added
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_PHONE;
        assertTrue((testString).equals(mySandBox.getText()));
        //Ensure that caret is set to far right after concatenation
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
        //Ensure that hack does not run if caret is not at end of line
        int currentCaretPosition = mySandBox.getCaretPosition();
        mySandBox.positionCaret(currentCaretPosition - 1);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue(testString.equals(mySandBox.getText()));
        //Return caret back to original position
        guiRobot.push(KeyCode.CONTROL);
        //Simulate User Input
        guiRobot.write("98765432");
        testString += "98765432";
        //Continue pushing
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_EMAIL;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_ADDRESS;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_BLOODTYPE;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));
        //Ensure that even though there is a tag input, more tag are added if user requires
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));
        //Final assurance that caret is at far right
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

    }

    @Test
    public void handleValidRightKeyPressPrefixRandom() {
        //The Add Command hack accounts for missing prefix or jump in prefix.
        //This functionality will be tested in this test

        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "add";

        guiRobot.write("add");
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue((testString).equals(mySandBox.getText()));

        //Assume user skips the input of phone prefix : p/
        guiRobot.write(" ");
        guiRobot.write(STRING_EMAIL);
        guiRobot.write("jeremylsw@u.nus.edu");
        testString += " " + STRING_EMAIL + "jeremylsw@u.nus.edu";
        assertTrue((testString).equals(mySandBox.getText()));

        //Add command hack will detect missing p/ and concatenate it
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_PHONE;
        assertTrue((testString).equals(mySandBox.getText()));
        //Ensure caret positioning
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Assume user jumps straight to tags, missing out address and bloodtype prefixes
        guiRobot.write(" ");
        guiRobot.write(STRING_TAG);
        guiRobot.write(" ");
        guiRobot.write(STRING_TAG);
        testString += " " + STRING_TAG + " " + STRING_TAG;
        assertTrue((testString).equals(mySandBox.getText()));

        //Subsequent concatenation will detect missing address and bloodtype prefixes and fix it
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_ADDRESS + " " + STRING_BLOODTYPE;
        assertTrue((testString).equals(mySandBox.getText()));

        //Final assurance that caret is at far right
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
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
