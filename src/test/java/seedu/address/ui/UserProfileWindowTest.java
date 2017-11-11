package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.person.Email.MESSAGE_EMAIL_CONSTRAINTS;
import static seedu.address.model.person.Name.MESSAGE_NAME_CONSTRAINTS;
import static seedu.address.model.person.Phone.MESSAGE_PHONE_CONSTRAINTS;
import static seedu.address.testutil.TypicalUserPerson.WILLIAM;
import static seedu.address.testutil.TypicalUserPerson.getTypicalUserPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertUserProfileWindowEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertUserProfileWindowStatusLabelEquals;

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


    private UserPerson userPerson = model.getUserPerson();
    private UserProfileWindow userProfileWindow;
    private UserProfileWindowHandle userProfileWindowHandle;

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
        setNameTextField(james.getName().toString());
        setAddressTextField(james.getAddress().toString());
        setPhoneTextField(james.getPhone().toString());
        setEmailTextField(james.getEmailAsText());
        setWebLinkTextField(james.getWebLinksAsText());

        userProfileWindowHandle.clickOk();
        setUp();

        assertUserProfileWindowEquals(userProfileWindowHandle, james);
    }

    @Test
    public void updateUserPersonInvalidField() {
        userPerson = new UserPerson();
        UserPerson william = new UserPerson(WILLIAM);
        setNameTextField("");
        setAddressTextField("");
        setPhoneTextField("");
        setEmailTextField("");
        setWebLinkTextField("");

        // Invalid name
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);
        assertUserProfileWindowStatusLabelEquals(userProfileWindowHandle,
                MESSAGE_NAME_CONSTRAINTS);

        // Invalid Email
        setNameTextField(william.getName().toString());
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);
        assertUserProfileWindowStatusLabelEquals(userProfileWindowHandle,
                MESSAGE_EMAIL_CONSTRAINTS);

        // Invalid Phone
        setEmailTextField(william.getEmailAsText());
        setPhoneTextField("TTT");
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);
        assertEquals(MESSAGE_PHONE_CONSTRAINTS,
                userProfileWindowHandle.getStatusLabel().getText());

        // Invalid Email again
        setEmailTextField("abc");
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);
        assertUserProfileWindowStatusLabelEquals(userProfileWindowHandle,
                MESSAGE_EMAIL_CONSTRAINTS);

        // Address is always valid
        setEmailTextField(william.getEmailAsText());
        setAddressTextField(william.getAddress().toString());
        userProfileWindowHandle.clickOk();

        guiRobot.sleep(250);
        assertUserProfileWindowStatusLabelEquals(userProfileWindowHandle,
                MESSAGE_PHONE_CONSTRAINTS);

        // All values are now correct
        setPhoneTextField(william.getPhone().toString());
        setWebLinkTextField(william.getWebLinksAsText());
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);

        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Verify profile is now correctly saved
        assertUserProfileWindowEquals(userProfileWindowHandle, william);
    }

    @Test
    public void cancelButtonDoesNotUpdate() throws Exception {
        userPerson = new UserPerson();
        UserPerson william = new UserPerson(WILLIAM);
        setNameTextField(william.getName().toString());
        setAddressTextField(william.getAddress().toString());
        setPhoneTextField(william.getPhone().toString());
        setEmailTextField(william.getEmailAsText());
        setWebLinkTextField(william.getWebLinksAsText());

        userProfileWindowHandle.clickCancel();
        setUp();

        assertUserProfileWindowEquals(userProfileWindowHandle, userPerson);
    }

    /**
     * Sets Name Text Field in UserProfileHandle
     * @param text
     */
    private void setNameTextField(String text) {
        userProfileWindowHandle.getNameTextField().setText(text);
    }

    /**
     * Sets Address Text Field in UserProfileHandle
     * @param text
     */
    private void setAddressTextField(String text) {
        userProfileWindowHandle.getAddressTextField().setText(text);
    }

    /**
     * Sets Phone Text Field in UserProfileHandle
     * @param text
     */
    private void setPhoneTextField(String text) {
        userProfileWindowHandle.getPhoneTextField().setText(text);
    }

    /**
     * Sets Email Text Field in UserProfileHandle
     * @param text
     */
    private void setEmailTextField(String text) {
        userProfileWindowHandle.getEmailTextField().setText(text);
    }

    /**
     * Sets WebLink Text Field in UserProfileHandle
     * @param text
     */
    private void setWebLinkTextField(String text) {
        userProfileWindowHandle.getWebLinkTextField().setText(text);
    }
}
