package seedu.address.ui;

import static guitests.guihandles.MainWindowHandle.TEST_PASSWORD;
import static guitests.guihandles.MainWindowHandle.TEST_USERNAME;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import seedu.address.commons.core.ListObserver;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.NearbyCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

//@@author jelneo
public class PreLoginCommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_IS_NOT_RECOGNIZED = "sfewgrw43";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;
    private Model model;
    private ModelManager modelManager;
    private String adminUsername;
    private String adminPassword;

    @Before
    public void setUp() {
        model = new ModelManager();
        modelManager = new ModelManager();
        ListObserver.init(model);

        Logic logic = new LogicManager(model);
        adminUsername = TEST_USERNAME;
        adminPassword = TEST_PASSWORD;
        CommandBox commandBox = new CommandBox(logic);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_successfulCommandInputs() {
        // permitted inputs that contain the command keywords: exit, help, login
        commandBoxHandle.run(ExitCommand.COMMAND_WORD);
        assertBehaviorForSuccessfulCommand(ExitCommand.COMMAND_WORD);

        commandBoxHandle.run(LoginCommand.COMMAND_WORD);
        assertBehaviorForSuccessfulCommand(LoginCommand.COMMAND_WORD);

        commandBoxHandle.run(HelpCommand.COMMAND_WORD);
        assertBehaviorForSuccessfulCommand(HelpCommand.COMMAND_WORD);
    }

    @Test
    public void commandBox_unsuccessfulCommandInputs() {
        // recognised commands which are not login, help or exit
        commandBoxHandle.run(ListCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(ListCommand.COMMAND_WORD);

        commandBoxHandle.run(ClearCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(ClearCommand.COMMAND_WORD);

        commandBoxHandle.run(AddCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(AddCommand.COMMAND_WORD);

        commandBoxHandle.run(DeleteCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(DeleteCommand.COMMAND_WORD);

        commandBoxHandle.run(EditCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(EditCommand.COMMAND_WORD);

        commandBoxHandle.run(FindCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(FindCommand.COMMAND_WORD);

        commandBoxHandle.run(HistoryCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(HistoryCommand.COMMAND_WORD);

        commandBoxHandle.run(RedoCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(RedoCommand.COMMAND_WORD);

        commandBoxHandle.run(SelectCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(SelectCommand.COMMAND_WORD);

        commandBoxHandle.run(UndoCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(UndoCommand.COMMAND_WORD);

        commandBoxHandle.run(NearbyCommand.COMMAND_WORD);
        assertBehaviorForFailedCommand(NearbyCommand.COMMAND_WORD);

        // unrecognised commands
        commandBoxHandle.run(COMMAND_THAT_IS_NOT_RECOGNIZED);
        assertBehaviorForFailedCommand(COMMAND_THAT_IS_NOT_RECOGNIZED);
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand(String commandKeyword) {
        commandBoxHandle.run(commandKeyword);
        assertEquals(commandKeyword, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand(String commandKeyword) {
        commandBoxHandle.run(commandKeyword);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }
}
