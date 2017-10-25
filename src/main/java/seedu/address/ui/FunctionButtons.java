package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.email.Email;
import seedu.address.logic.Logic;

public class FunctionButtons extends UiPart<Region> {
    private static final String FXML = "FunctionButtons.fxml";
    private Logic logic;

    @FXML
    private StackPane loginPane;
    @FXML
    private Button loginButton;
    @FXML
    private StackPane sendPane;
    @FXML
    private Button sendButton;
    @FXML
    private StackPane checkPane;
    @FXML
    private Button checkButton;

    public FunctionButtons(Logic logic) {
        super(FXML);
        this.logic = logic;
    }

    /**
     * Open the email login window
     */
    @FXML
    private void openEmailLoginWindow() {
        loginButton.setDisable(true);
        EmailLoginWindow emailLoginWindow = new EmailLoginWindow(loginButton, logic);
        emailLoginWindow.show();
    }

    /**
     * Open the email send window
     */
    @FXML
    private void openEmailSendWindow() {
        sendButton.setDisable(true);
        EmailSendWindow emailSendWindow = new EmailSendWindow(sendButton, logic);
        emailSendWindow.show();
    }

    /**
     * Open the email check window
     */
    @FXML
    private void openEmailCheckWindow() {

    }
}
