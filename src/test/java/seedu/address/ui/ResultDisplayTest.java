package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.ResultDisplay.WELCOME_TEXT;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

public class ResultDisplayTest extends GuiUnitTest {

    private static final NewResultAvailableEvent NEW_RESULT_EVENT_STUB = new NewResultAvailableEvent("Stub");

    private ResultDisplayHandle resultDisplayHandle;
    private ResultDisplay resultDisplay;

    @Before
    public void setUp() {
        resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));
    }

    @Test
    public void display() {
        // prompt text
        assertEquals(WELCOME_TEXT, resultDisplayHandle.getPromptText());

        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());

        // new result received
        postNow(NEW_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(NEW_RESULT_EVENT_STUB.message, resultDisplayHandle.getText());
    }

    @Test
    public void setFocus() {
        resultDisplay.setFocus();
        assertTrue(resultDisplayHandle.isFocused());
    }
}
