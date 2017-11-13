//@@author danielbrzn
package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.ProgressWindowHandle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;

public class ProgressWindowTest extends GuiUnitTest {

    private static final double HALF_PROGRESS = 0.5;
    private ProgressWindow progWindow;
    private ProgressWindowHandle progWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> progWindow = new ProgressWindow(new SimpleDoubleProperty(HALF_PROGRESS)));
        Stage progWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(progWindow.getRoot().getScene()));
        FxToolkit.showStage();
        progWindowHandle = new ProgressWindowHandle(progWindowStage);
    }

    @Test
    public void display() {
        ReadOnlyDoubleProperty shownProgress = progWindowHandle.getProgress(progWindow);
        assertEquals(shownProgress.doubleValue(), new SimpleDoubleProperty(HALF_PROGRESS).doubleValue(), 0);
    }
}
