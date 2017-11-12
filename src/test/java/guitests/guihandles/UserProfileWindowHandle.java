package guitests.guihandles;

import guitests.GuiRobot;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

//@@author bladerail
/**
 * Provides a handle for the UserProfileWindow
 */
public class UserProfileWindowHandle extends StageHandle {
    public static final String USERPROFILE_WINDOW_TITLE = "User Profile";

    private static final String NAME_FIELD_ID = "#nameTextField";
    private static final String PHONE_FIELD_ID = "#phoneTextField";
    private static final String ADDRESS_FIELD_ID = "#addressTextField";
    private static final String EMAIL_FIELD_ID = "#emailTextField";
    private static final String WEBLINK_FIELD_ID = "#webLinkTextField";
    private static final String okButton_ID = "#okButton";
    private static final String cancelButton_ID = "#cancelButton";
    private static final String statusLabel_ID = "#statusLabel";

    private final TextField nameTextField;
    private final TextField phoneTextField;
    private final TextField addressTextField;
    private final TextField emailTextField;
    private final TextField webLinkTextField;
    private final Button okButton;
    private final Button cancelButton;
    private final Label statusLabel;

    public UserProfileWindowHandle(Stage userProfileWindowStage) {
        super(userProfileWindowStage);

        this.nameTextField = getChildNode(NAME_FIELD_ID);
        this.phoneTextField = getChildNode(PHONE_FIELD_ID);
        this.addressTextField = getChildNode(ADDRESS_FIELD_ID);
        this.emailTextField = getChildNode(EMAIL_FIELD_ID);
        this.webLinkTextField = getChildNode(WEBLINK_FIELD_ID);
        this.okButton = getChildNode(okButton_ID);
        this.cancelButton = getChildNode(cancelButton_ID);
        this.statusLabel = getChildNode(statusLabel_ID);
    }

    /**
     * Returns true if the UserProfile window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(USERPROFILE_WINDOW_TITLE);
    }

    /**
     * Closes the {@code UserProfileWindow} by pressing the shortcut key associated
     * with the cancel button in {@code UserProfileWindow}.
     */
    public void closeUserProfileWindowUsingCancelAccelerator() {
        guiRobot.push(KeyCode.ESCAPE);
    }

    /**
     * Closes the {@code UserProfileWindow} by pressing the shortcut key associated
     * with the ok button in {@code UserProfileWindow}.
     */
    public void closeUserProfileWindowUsingOkAccelerator() {
        guiRobot.push(KeyCode.ENTER);
    }

    public String getName() {
        return nameTextField.getText();
    }

    public String getPhone() {
        return phoneTextField.getText();
    }

    public String getAddress() {
        return addressTextField.getText();
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public String getWebLink() {
        return webLinkTextField.getText();
    }

    public TextField getAddressTextField() {
        return addressTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getPhoneTextField() {
        return phoneTextField;
    }

    public TextField getWebLinkTextField() {
        return webLinkTextField;
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    /**
     * Click the ok button
     */
    public void clickOk() {
        Platform.runLater(() -> {
            okButton.fire();
        });
    }

    /**
     * Click the cancel button
     */
    public void clickCancel() {
        Platform.runLater(() -> cancelButton.fire());
    }
}
