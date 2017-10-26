package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Pop up window for email login
 */
public class EmailLoginWindow extends UiPart<Region> {
    private static final String FXML = "EmailLoginWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(EmailLoginWindow.class);
    private Stage primaryStage;
    private Logic logic;

    @FXML
    private Text loginText;

    @FXML
    private Text passwordText;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button emailWindowLoginButton;

    @FXML
    private Label feedbackLabel;

    public EmailLoginWindow(Logic logic, Stage parentStage) {
        super(FXML);

        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());
        this.primaryStage.setScene(scene);
        this.primaryStage.initOwner(parentStage);
        this.primaryStage.initModality(Modality.WINDOW_MODAL);
        this.logic = logic;

        setOnCloseEvent();
    }

    /**
     * When the login button is pressed
     */
    @FXML
    private void onLoginButtonPressed() {
        String emailString = emailField.getText();
        String passwordString = passwordField.getText();

        try {
            CommandResult commandResult = logic.execute("email_login "
                                    + "\"" + emailString + "\"" + " "
                                    + "\"" + passwordString + "\"");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            feedbackLabel.setText(commandResult.feedbackToUser);
        } catch (CommandException e) {
            feedbackLabel.setText(e.getMessage());
        } catch (ParseException e) {
            feedbackLabel.setText(e.getMessage());
        }
    }

    /**
     * Enable the login / logout button when this window closes
     */
    public void setOnCloseEvent() {

    }

    /**
     * Show the window
     */
    public void show() {
        primaryStage.show();
    }
}
