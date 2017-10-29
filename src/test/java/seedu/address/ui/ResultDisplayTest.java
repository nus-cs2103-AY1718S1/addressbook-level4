package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

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
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());

        // new result received
        postNow(NEW_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(NEW_RESULT_EVENT_STUB.message, resultDisplayHandle.getText());
    }

    //@@author Alim95
    @Test
    public void resultDisplayHighlight() {
        resultDisplay.highlight();
        assertEquals(resultDisplayHandle.getStyle(), "-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    @Test
    public void resultDisplayUnhighlight() {
        resultDisplay.unhighlight();
        assertEquals(resultDisplayHandle.getStyle(), "");
    }
}
