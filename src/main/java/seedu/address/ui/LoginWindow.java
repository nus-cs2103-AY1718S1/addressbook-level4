package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.Model;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Controller for a login dialogue
 */
public class LoginWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(LoginWindow.class);
    private static final String ICON = "/images/login_icon.png";
    private static final String FXML = "LoginWindow.fxml";
    private static final String TITLE = "Login";

    @FXML
    private WebView browser;

    @FXML
    private TextField usernameTextField;
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
     * Sets the login dialogue style to use the default style.
     */
    private void setStyleToDefault() {

    }

    /**
     * Sets the login dialogue style to indicate a failed login
     */
    private void setMotionToindicateLoginFailure() {

    }

    /**
    public void performLoginAttempt() {
        try {

        } catch () {

        }
    }*/
}
