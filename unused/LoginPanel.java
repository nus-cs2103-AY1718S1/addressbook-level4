package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoginRequestEvent;

/**
 * This code checks if the email and password is valid before posting LoginRequestEvent
 * Reason why this was unused: I dedcided to remove the login page as it was not good
 * to store passsword and username and it caused coupling with the other features.
 */

//@@author ReneeSeet

/**
 * The Login Panel of the App.
 */
public class LoginPanel extends UiPart<Region> {

    private static final String FXML = "LoginPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginPanel.class);
    private static final String DEFAULT_EMAIL = "test@gmail.com";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String INVALID_EMAIL_PASSWORD = "Invalid password and email";
    private static final String LOGGER_SUCCESSFUL_LOGIN = "SUCCESSFUL LOGIN";
    private static final String EMPTY_FIELDS = "Please enter email and password";
    private static final string LOGGER_EMPTY_FIELDS = "email textbox and password textbox are empty";

    @FXML
    private Button loginButton;

    @FXML
    private Text loginText;

    @FXML
    private TextField emailBox;

    @FXML
    private PasswordField passwordBox;

    public LoginPanel() {
        super(FXML);
    }

    /**
    * Upon ENTER, check the password and email textbox for password and email.
    * If password and email are valid , post LoginRequestEvent
    */

    @FXML
    public void onEnter(ActionEvent ae) {
        if (!emailBox.getText().equals("") && !passwordBox.getText().equals("")) {
            if(emailBox.getText().equals(DEFAULT_EMAIL) && passwordBox.getText().equals(DEFAULT_PASSWORD)) {
                EventsCenter.getInstance().post(new LoginRequestEvent());
                logger.info(LOGGER_SUCCESSFUL_LOGIN);
            }else {
                loginText.setText(INVALID_EMAIL_PASSWORD);
                logger.info(INVALID_EMAIL_PASSWORD);
            }
        } else {
            loginText.setText(EMPTY_FIELDS);
            logger.info(LOGGER_EMPTY_FIELDS);
        }
    }
}
