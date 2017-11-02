//@@author aggarwalRuchir
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;


/**
 * Controller for a login dialogue
 */
public class LoginWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(LoginWindow.class);
    private static final String ICON = "/images/login_icon.png";
    private static final String FXML = "LoginWindow.fxml";
    private static final String TITLE = "Login";

    private boolean okClicked = false;

    @FXML
    private WebView browser;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    private final Stage dialogStage;

    public LoginWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (performLoginAttempt()) {
            okClicked = true;
            dialogStage.close();
        }
    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    /**
     * Sets the login dialogue style to use the default style.
     */
    private void setStyleToDefault() {
        //TODO - restore if user restarts entering details
        ;
    }

    /**
     * Sets the login dialogue style to indicate a failed login
     */
    private void setMotionToindicateLoginFailure() {
        //TODO - change look/shake of dialog if user enters wrong details
        ;
    }

    /**
     * Performs a check whether the username and password entered by the user are correct or not
     * @return true if log in details are correct, else false
     */
    public boolean performLoginAttempt() {
        boolean loginAttemptBool = false;
        try {
            String errorMessage = "";
            String correctUsername = "admin";
            String correctPassword = "password";
            String enteredUsername = usernameTextField.getText().toString();
            String enteredPassword = passwordTextField.getText().toString();

            logger.info("username entered:" + enteredUsername);
            logger.info("password entered:" + enteredPassword);

            if ((enteredUsername.equals(correctUsername)) && (enteredPassword.equals(correctPassword))) {
                //TODO - send event for closing the login dialogue
                loginAttemptBool = true;
            } else {
                //TODO - In case wrong details entered
                errorMessage += "Incorrect username or password. Please enter correct details to start the app.";
            }

            if (errorMessage.length() != 0) {
                // Show the error message.
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Incorrect Login");
                alert.setHeaderText("Error");
                alert.setContentText(errorMessage);

                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //TODO - Any exceptions?
        }

        return loginAttemptBool;
    }
}
