package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StartUpPanelHandle;
import seedu.address.logic.commands.LoginCommand;

//@@author jelneo
public class StartUpPanelTest extends GuiUnitTest {
    private static final String START_UP_MESSAGE = "Welcome to CodiiLog in using the command box above."
            + "Format: " + LoginCommand.MESSAGE_LOGIN_FORMAT;
    private StartUpPanel startUpPanel;
    private StartUpPanelHandle startUpPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> startUpPanel = new StartUpPanel());
        uiPartRule.setUiPart(startUpPanel);

        startUpPanelHandle = new StartUpPanelHandle(startUpPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default welcome text
        guiRobot.pauseForHuman();

        assertEquals(START_UP_MESSAGE, startUpPanelHandle.getText());
    }
}
