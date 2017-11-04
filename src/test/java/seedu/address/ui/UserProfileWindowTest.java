package seedu.address.ui;

import static seedu.address.ui.testutil.GuiTestAssert.assertUserProfileWindowEquals;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.UserProfileWindowHandle;
import javafx.stage.Stage;
import seedu.address.model.person.UserPerson;

//@@author bladerail
public class UserProfileWindowTest extends GuiUnitTest {

    private UserProfileWindow userProfileWindow;
    private UserProfileWindowHandle userProfileWindowHandle;

    private UserPerson userPerson = new UserPerson();

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> userProfileWindow = new UserProfileWindow(userPerson));
        Stage userProfileWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(
                userProfileWindow.getRoot().getScene()));
        FxToolkit.showStage();
        userProfileWindowHandle = new UserProfileWindowHandle(userProfileWindowStage);
    }

    @Test
    public void display() {
        assertUserProfileWindowEquals(userProfileWindowHandle, userPerson);
    }

}
