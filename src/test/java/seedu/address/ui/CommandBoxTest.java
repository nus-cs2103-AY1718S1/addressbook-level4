package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

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
    public static final String STRING_REMARK = PREFIX_REMARK.toString();
    public static final String STRING_TAG = PREFIX_TAG.toString();
    public static final String STRING_DATE = PREFIX_DATE.toString();

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

    //@@author Jeremy
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
    public void handleKeyPressShiftAlt() {
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        //Setting up of sandbox environment for testing
        guiRobot.write("Test");
        assertTrue("Test".equals(mySandBox.getText()));

        assertTrue(mySandBox.getCaretPosition() == commandBoxHandle.getInput().length());
        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.SHIFT, KeyCode.ALT);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 4);
        assertTrue(mySandBox.getCaretPosition() == 0);
    }


    @Test
    public void handleKeyPressAlt() {
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 1. No input retains Caret position
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 2. Empty spaces only input shifts Caret all the way left
        guiRobot.write("         ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 3. Caret at end of word - Shifts to left side of word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 4, Caret between a word - Gets shifted to the left of current word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("101010101010");
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.ALT);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 5, Word followed by space - Shifts caret immediately to beginning of word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("101010");
        guiRobot.write("     ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 6, Spaces followed by words - Shifts caret to beginning of word but after spaces
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("     ");
        guiRobot.write("101010");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 5);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 7, Shortcut works for other non-alphabet strings
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("  (*&^%$ 9876543 <>?:{}|  ");
        assertTrue(mySandBox.getCaretPosition() == 26);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 17);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 9);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 2);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

    }

    @Test
    public void handleKeyPressShiftControl() {
        //Shift-Control shifts the caret all the way right
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        //Setting up of sandbox environment for testing
        guiRobot.write("Test");
        assertTrue("Test".equals(mySandBox.getText()));

        assertTrue(mySandBox.getCaretPosition() == commandBoxHandle.getInput().length());
        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.SHIFT, KeyCode.ALT);
        //Ensure caret is at the left
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 4);
        assertTrue(mySandBox.getCaretPosition() == 0);
        //Push caret to right
        guiRobot.push(KeyCode.SHIFT, KeyCode.CONTROL);
        //Ensure caret is at the right
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertTrue(mySandBox.getCaretPosition() == 4);
    }


    @Test
    public void handleKeyPressControl() {
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 1. No input retains Caret position
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 2. Empty spaces only input shifts Caret all the way right
        guiRobot.write("         ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 3. Caret at end of word - Nothing happens
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 4, Caret between a word - Gets shifted to the right of current word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("101010101010");
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 5, Word followed by space - Shifts right twice
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("101010");
        guiRobot.write("     ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 6);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 6, Spaces followed by words - Shifts caret end of word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("     ");
        guiRobot.write("101010");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(0);
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 7, Shortcut works for other non-alphabet strings
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("  (*&^%$ 9876543 <>?:{}|  ");
        assertTrue(mySandBox.getCaretPosition() == 26);
        mySandBox.positionCaret(0);
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 8);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 16);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 24);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 26);

    }


    @Test
    public void handleShiftDeleteTestOne() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 1: Test for empty input
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 2a: Test for blank space input caret at most left
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(0);
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 2b: Test for blank space input caret at most right
        mySandBox.positionCaret(mySandBox.getText().length());
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        assertTrue(mySandBox.getText().length() == 0);

        //Test 2c: Test for blank space input caret in the middle
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        assertFalse(mySandBox.getText().length() == 0);

        //Test 3: Test for test word input
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

    }


    @Test
    public void handleShiftDeleteTestTwo() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 4a: Test for blank space + test word input caret at most right
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("    ");
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 4b: Test for black space + test word input caret between word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("    ");
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getCaretPosition() - 2);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertFalse(mySandBox.getText().length() == 0);
        assertTrue(mySandBox.getText().length() == 2);

        //Test 5a: Test for word + blank space input, Caret at far right
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 5b: Test for word + blank space input, Caret after word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertTrue(mySandBox.getCaretPosition() == 4);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        mySandBox.positionCaret(mySandBox.getText().length());
        assertTrue(mySandBox.getCaretPosition() == 4);
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
    }


    @Test
    public void handleShiftBackSpaceTestOne() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 1: Test for empty input
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 2a: Test for blank space input caret at most left
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(0);
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 2b: Test for blank space input caret at most right
        mySandBox.positionCaret(mySandBox.getText().length());
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        assertTrue(mySandBox.getText().length() == 0);

        //Test 2c: Test for blank space input caret in the middle
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        assertFalse(mySandBox.getText().length() == 0);

        //Test 3: Test for test word input
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

    }


    @Test
    public void handleShiftBackSpaceTestTwo() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 4a: Test for blank space + test word input caret at most right
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("    ");
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 4b: Test for black space + test word input caret between word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("    ");
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getCaretPosition() - 2);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertFalse(mySandBox.getText().length() == 0);
        assertTrue(mySandBox.getText().length() == 2);

        //Test 5a: Test for word + blank space input, Caret at the end
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 5b: Test for word + blank space input, Caret after word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertTrue(mySandBox.getCaretPosition() == 4);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        mySandBox.positionCaret(mySandBox.getText().length());
        assertTrue(mySandBox.getCaretPosition() == 4);
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
    }


    @Test
    public void handleValidRightKeyPressAddLenMaxThree() {
        //This test focuses on ensuring that the key press works only for the add command
        //and shortcut triggers only when "a" or "add" is detected at the front of the statement.
        //Cases like "adda" "addy" "am" or "aa" will not trigger add command shortcut

        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "";

        //Test 1: Test for empty String
        guiRobot.write("         ");
        testString = "         ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        testString = "         ";
        assertTrue(testString.equals(mySandBox.getText()));

        //Test 2: Test for single char
        mySandBox.clear();
        testString = "";
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

        //Test 3: Test for String with len 2
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

        //Test 4: Test for String with len 3
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
    public void handleValidRightKeyPressAddLenAboveThree() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "";
        String backupTestString;

        // Input: Add -> Valid
        guiRobot.write("Add");
        testString += "Add";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));

        // Input: a Add -> Valid
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

        //Blank spaces are removed
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
        testString += " " + STRING_REMARK;
        testString += " " + STRING_DATE;
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));

    }


    @Test
    public void handleInvalidRightKeyPressAdd() {
        //Test to ensure add command shortcut does not trigger as long as
        //caret is within the text

        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        guiRobot.write("Add");
        assertTrue("Add".equals(mySandBox.getText()));

        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.SHIFT, KeyCode.ALT);
        //Ensure caret is at the left
        assertTrue(mySandBox.getCaretPosition() == 0);
        //Try to trigger add shortcut - Nothing happens, Caret + 1
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 1);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 2);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 3);

        //Trigger add shortcut - n/ is concatenated
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add n/".equals(mySandBox.getText()));

    }



    @Test
    public void handleValidRightKeyPressAddPrefixInOrder() {
        //Add Command allows users to enter the prefix in any order
        //The Add Command shortcut accounts for missing prefix or jump in prefix but for this test
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
        //Ensure that shortcut does not run if caret is not at end of line
        int currentCaretPosition = mySandBox.getCaretPosition();
        mySandBox.positionCaret(currentCaretPosition - 1);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue(testString.equals(mySandBox.getText()));
        //Return caret back to original position
        guiRobot.push(KeyCode.SHIFT, KeyCode.CONTROL);
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
        testString += " " + STRING_REMARK;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_DATE;
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
    public void handleValidRightKeyPressAddPrefixRandom() {
        //The Add Command shortcut accounts for missing prefix or jump in prefix.
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

        //Add command shortcut will detect missing p/ and concatenate it
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
    public void handleInvalidRightKeyPressEdit() {
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString;

        //Test stringToEvaluate.length() < 3 -> false
        guiRobot.write("e ");
        testString = "e ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        mySandBox.clear();
        guiRobot.write("e1");
        testString = "e1";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        mySandBox.clear();

        //Test !stringToEvaluate.contains(" ") -> false
        guiRobot.write("edit");
        testString = "edit";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        mySandBox.clear();
        guiRobot.write("edit1");
        testString = "edit1";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        mySandBox.clear();
        guiRobot.write("edit1n/jeremy");
        testString = "edit1n/jeremy";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));

    }


    @Test
    public void handleInvalidCommandWordEdit() {
        //Test for !containsEditCommand && containsOnlyNumbers -> False

        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        int counter = 0;

        // e 1 -> Valid
        guiRobot.write("e 1");
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);

        // ed 1 -> Invalid
        mySandBox.positionCaret(1);
        guiRobot.write("d");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertFalse(mySandBox.getText().length() == counter);
        counter -= 3;
        assertTrue(mySandBox.getText().length() == counter);

        // edi 1 -> Invalid
        mySandBox.positionCaret(2);
        guiRobot.write("i");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertFalse(mySandBox.getText().length() == counter);
        counter -= 3;
        assertTrue(mySandBox.getText().length() == counter);

        // edit 1 -> Valid
        mySandBox.positionCaret(3);
        guiRobot.write("t");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);
    }


    @Test
    public void handleInvalidNumberRightKeyPressEdit() {
        //Test for containsEditCommand && !containsOnlyNumbers -> False
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        int counter = 0;
        //edit 1 -> Valid
        guiRobot.write("edit 1");
        counter += 6;
        assertTrue(mySandBox.getText().length() == counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);
        //edit 10 -> Valid
        mySandBox.positionCaret(6);
        guiRobot.write("0");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);
        //edit -10 -> Invalid
        mySandBox.positionCaret(5);
        guiRobot.write("-");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertFalse(mySandBox.getText().length() == counter);


    }


    @Test
    public void handleValidRightKeyPressEditPrefixInOrder() {

        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "edit 1";

        guiRobot.write("edit 1");
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
        //Ensure that shortcut does not run if caret is not at end of line
        int currentCaretPosition = mySandBox.getCaretPosition();
        mySandBox.positionCaret(currentCaretPosition - 1);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue(testString.equals(mySandBox.getText()));
        //Return caret back to original position
        guiRobot.push(KeyCode.SHIFT, KeyCode.CONTROL);
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
        testString += " " + STRING_REMARK;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_DATE;
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
    public void handleValidRightKeyPressEditPrefixRandom() {
        //The Edit Command shortcut accounts for missing prefix or jump in prefix.
        //This functionality will be tested in this test

        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "edit 1";

        guiRobot.write("edit 1");
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

        //Add command shortcut will detect missing p/ and concatenate it
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
    //@@author

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
