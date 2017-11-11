package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InformationBoardHandle;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

// @@author HouDenghao
public class InformationBoardTest extends GuiUnitTest {

    private static final NewResultAvailableEvent FIRST_RESULT_EVENT_STUB = new NewResultAvailableEvent("Stub");
    private static final NewResultAvailableEvent SECOND_RESULT_EVENT_STUB = new NewResultAvailableEvent("Details: ");

    private InformationBoardHandle informationBoardHandle;

    @Before
    public void setUp() {
        InformationBoard informationBoard = new InformationBoard();
        uiPartRule.setUiPart(informationBoard);

        informationBoardHandle = new InformationBoardHandle(getChildNode(informationBoard.getRoot(),
                InformationBoardHandle.INFORMATION_BOARD_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", informationBoardHandle.getText());

        //no result received
        postNow(FIRST_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals("", informationBoardHandle.getText());

        // new result received
        postNow(SECOND_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(SECOND_RESULT_EVENT_STUB.message, informationBoardHandle.getText());
    }
}
