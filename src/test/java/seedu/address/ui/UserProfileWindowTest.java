package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalUserPerson.WILLIAM;
import static seedu.address.testutil.TypicalUserPerson.getTypicalUserPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertUserProfileWindowEquals;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.UserProfileWindowHandle;
import javafx.stage.Stage;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.UserPerson;

//@@author bladerail
public class UserProfileWindowTest extends GuiUnitTest {

    private Model model = new ModelManager();

    private UserProfileWindow userProfileWindow;
    private UserProfileWindowHandle userProfileWindowHandle;

    private UserPerson userPerson = model.getUserPerson();

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> userProfileWindow = new UserProfileWindow(model.getUserPerson()));
        Stage userProfileWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(
                userProfileWindow.getRoot().getScene()));
        FxToolkit.showStage();
        userProfileWindowHandle = new UserProfileWindowHandle(userProfileWindowStage);
    }

    @Test
    public void display() {
        assertUserProfileWindowEquals(userProfileWindowHandle, userPerson);
    }

    @Test
    public void updatedDisplayIsCorrect() throws Exception {
        userProfileWindowHandle.clickOk();
        userPerson.update(WILLIAM);
        setUp();

        assertUserProfileWindowEquals(userProfileWindowHandle, userPerson);
    }

    @Test
    public void updateUserPersonSuccess() throws Exception {
        userPerson = new UserPerson();
        UserPerson james = getTypicalUserPerson();
        userProfileWindowHandle.getNameTextField().setText(james.getName().toString());
        userProfileWindowHandle.getAddressTextField().setText(james.getAddress().toString());
        userProfileWindowHandle.getPhoneTextField().setText(james.getPhone().toString());
        userProfileWindowHandle.getEmailTextField().setText(james.getEmailAsText());
        userProfileWindowHandle.getWebLinkTextField().setText(james.getWebLinksAsText());

        userProfileWindowHandle.clickOk();
        setUp();

        assertUserProfileWindowEquals(userProfileWindowHandle, james);
    }

    @Test
    public void cancelButtonDoesNotUpdate() throws Exception {
        userPerson = new UserPerson();
        UserPerson william = new UserPerson(WILLIAM);
        userProfileWindowHandle.getNameTextField().setText(william.getName().toString());
        userProfileWindowHandle.getAddressTextField().setText(william.getAddress().toString());
        userProfileWindowHandle.getPhoneTextField().setText(william.getPhone().toString());
        userProfileWindowHandle.getEmailTextField().setText(william.getEmailAsText());
        userProfileWindowHandle.getWebLinkTextField().setText(william.getWebLinksAsText());

        userProfileWindowHandle.clickCancel();
        setUp();

        assertUserProfileWindowEquals(userProfileWindowHandle, userPerson);
    }

    @Test
    public void updateUserPersonFailure() throws Exception {
        userPerson = new UserPerson();
        UserPerson william = new UserPerson(WILLIAM);
        userProfileWindowHandle.getNameTextField().setText(william.getName().toString());
        userProfileWindowHandle.getAddressTextField().setText(william.getAddress().toString());
        userProfileWindowHandle.getPhoneTextField().setText("abc");
        userProfileWindowHandle.getEmailTextField().setText(william.getEmailAsText());
        userProfileWindowHandle.getWebLinkTextField().setText(william.getWebLinksAsText());

        userProfileWindowHandle.clickOk();

        // userProfileWindowHandle.getPhoneTextField().setText(william.getPhone().toString());
        // assertUserProfileWindowNotOpen();
        // setUp();
        // assertUserProfileWindowEquals(userProfileWindowHandle, model.getUserPerson());


    }

    /**
     * Asserts that the UserProfile window isn't open.
     */
    private void assertUserProfileWindowNotOpen() {
        assertFalse("Window still open", userProfileWindowHandle.isWindowPresent());
    }



}
