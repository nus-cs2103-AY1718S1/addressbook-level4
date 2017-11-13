package seedu.address.ui;
//@@author pjunwei95
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

public class ClearConfirmationTest extends GuiUnitTest {

    private ClearConfirmation clearConfirmation;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> clearConfirmation = new ClearConfirmation());

        FxToolkit.showStage();
    }

    @Test
    public void display() {
        ClearConfirmation expectedClearConfirmation = clearConfirmation;
        assertEquals(expectedClearConfirmation, clearConfirmation);
    }
}
