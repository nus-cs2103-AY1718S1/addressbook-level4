package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

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

    private CommandBox commandBoxForTest;
    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        CommandBox commandBox = new CommandBox(logic);
        //@@author viviantan95
        commandBoxForTest = commandBox;
        //@@author
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    //@@author viviantan95
    @Test
    public void handleKeyPress_escape() {
        //empty command box
        guiRobot.push(KeyCode.ESCAPE);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //enter text in command box
        guiRobot.write("Test");
        //check if command box has correct input
        assertTrue("Test".equals(commandBoxHandle.getInput()));
        //push ESCAPE and check if command box is empty
        guiRobot.push(KeyCode.ESCAPE);
        assertFalse("Test".equals(commandBoxHandle.getInput()));
        assertTrue("".equals(commandBoxHandle.getInput()));
    }

    @Test
    public void handleKeyPress_alt() {
        //gets text field
        TextField textField = commandBoxForTest.getCommandTextField();
        //insert text for testing
        guiRobot.write("Test");
        assertTrue("Test".equals(textField.getText()));

        //check if myTextField text cursor is same length as text in command box
        assertTrue(textField.getCaretPosition() == commandBoxHandle.getInput().length());

        //check if text cursor is flushed to the right after move left once and alt is pushed
        guiRobot.push(KeyCode.LEFT);
        guiRobot.push(KeyCode.ALT);
        assertFalse(textField.getCaretPosition() == 3);
        assertTrue(textField.getCaretPosition() == 4);

        //check if text cursor is flushed to the right if initial text cursor is to the extreme left
        textField.positionCaret(0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(textField.getCaretPosition() == 4);
        assertFalse(textField.getCaretPosition() == 0);
    }

    @Test
    public void handleKeyPress_delete() {
        TextField textField = commandBoxForTest.getCommandTextField();

        //empty command box
        assertTrue(textField.getCaretPosition() == 0);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //caret at extreme left but with chunk of words on the right
        guiRobot.write("Test");
        textField.positionCaret(0);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("Test".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //caret at extreme left but with chunk of spaces on the right
        guiRobot.write("    ");
        textField.positionCaret(0);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("    ".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //chunk of words on the left of text cursor
        guiRobot.write("Test");
        textField.positionCaret(textField.getLength());
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //chunk of spaces on the left of text cursor
        guiRobot.write("    ");
        textField.positionCaret(textField.getLength());
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //chunk of space before chunk of words on the left of text cursor
        guiRobot.write("    "); //4 spaces
        guiRobot.write("Test"); //4 letters
        textField.positionCaret(textField.getLength());
        guiRobot.push(KeyCode.DELETE);
        assertNotNull(textField.getCaretPosition());
        assertTrue(textField.getCaretPosition() == commandBoxHandle.getInput().length());
        assertTrue("    ".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //chunk of words before chunk of spaces on the left of text cursor
        guiRobot.write("Test"); //4 letters
        guiRobot.write("    "); //4 spaces
        textField.positionCaret(8);
        guiRobot.push(KeyCode.DELETE);
        assertNotNull(textField.getCaretPosition());
        assertTrue(textField.getCaretPosition() == commandBoxHandle.getInput().length());
        assertTrue("Test".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //test to remove words between 2 chunks of spaces
        guiRobot.write("    "); //4 spaces
        guiRobot.write("Test"); //4 letters
        guiRobot.write("    "); //4 spaces
        textField.positionCaret(8);
        guiRobot.push(KeyCode.DELETE);
        assertNotNull(textField.getCaretPosition());
        assertFalse(textField.getCaretPosition() == commandBoxHandle.getInput().length());
        assertTrue(textField.getCaretPosition() == 4);
        assertTrue("        ".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //test to remove spaces between 2 chunks of words
        guiRobot.write("Test"); //4 letters
        guiRobot.write("    "); //4 spaces
        guiRobot.write("Test"); //4 letters
        textField.positionCaret(8);
        guiRobot.push(KeyCode.DELETE);
        assertNotNull(textField.getCaretPosition());
        assertFalse(textField.getCaretPosition() == commandBoxHandle.getInput().length());
        assertTrue(textField.getCaretPosition() == 4);
        assertTrue("TestTest".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //test for text cursor in between chunk of words
        guiRobot.write("Test");
        textField.positionCaret(2);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("st".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //test for text cursor in between chunk of spaces
        guiRobot.write("    ");
        textField.positionCaret(2);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("  ".equals(commandBoxHandle.getInput()));
    }

    @Test
    public void handleKeyPress_insert() {
        TextField textField = commandBoxForTest.getCommandTextField();
        String correctTextField = "add " + PREFIX_NAME + " " + PREFIX_PHONE + " " + PREFIX_BIRTHDAY + " "
                + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_MODULE;

        //empty command box
        guiRobot.push(KeyCode.INSERT);
        assertTrue(correctTextField.equals(textField.getText()));

        //with text in command box
        guiRobot.write("Test");
        //push INSERT and checks if command box is replaced with the correct text field of AddCommand shortcut
        guiRobot.push(KeyCode.INSERT);
        assertTrue(correctTextField.equals(textField.getText()));
    }
    //@@author

    @Test
    public void handleKeyPress_startingWithUp() {
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
    public void handleKeyPress_startingWithDown() {
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
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
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
