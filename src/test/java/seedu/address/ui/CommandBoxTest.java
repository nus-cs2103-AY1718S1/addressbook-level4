package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;
    private CommandBox commandBox;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);
        StackPane commandBoxHelper = new StackPane();
        SplitPane settingsPane = new SplitPane();

        commandBox = new CommandBox(logic, commandBoxHelper, settingsPane);
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
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void handleKeyPress_startingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(KeyCode.DOWN, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_SUCCEEDS); //command box helper working as intended

        // two commands (latest command is failure)
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.A, "alist");
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.UP, thirdCommand);
        assertInputHistory(KeyCode.A, "lista");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.A, "lista");
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_SUCCEEDS); //command box helper working as intended
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
     * Testing the command box helper
     */
    @Test
    public void commandBoxHelperTest() {
        // Testing populating command box
        guiRobot.push(KeyCode.A);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, AddCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing DOWN arrow key on command box helper
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing UP arrow key on command box helper
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.UP);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing whether command box helper disappears or appears appropriately
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.A);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, "ea");
        clearCommandBox();

        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.D);
        guiRobot.push(KeyCode.I);
        guiRobot.push(KeyCode.T);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();
    }

    /**
     * Clears the text in current command field
     */
    private void clearCommandBox() {
        while (!commandBoxHandle.getInput().isEmpty()) {
            guiRobot.push(KeyCode.BACK_SPACE);
        }
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
