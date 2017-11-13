//@@author Hoang
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
import seedu.address.model.UserPrefs;

/**
 * Pop up window for email login
 */
public class EmailLoginWindow extends UiPart<Region> {
    private static final String FXML = "EmailLoginWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(EmailLoginWindow.class);
    private Stage primaryStage;
    private Logic logic;
    private FunctionButtons functionButtonsPanel;
    private UserPrefs prefs;

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
    private TextArea feedbackLabel;

    public EmailLoginWindow(Logic logic, Stage parentStage, FunctionButtons functionButtonsPanel, UserPrefs prefs) {
        super(FXML);

        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());

        if (prefs.getCurrentUserTheme().equals("DarkTheme")) {
            scene.getStylesheets().add("view/CSS/EmailLoginWindowDark.css");
        } else {
            scene.getStylesheets().add("view/CSS/EmailLoginWindowLight.css");
        }

        this.primaryStage.setScene(scene);
        this.primaryStage.initOwner(parentStage);
        this.primaryStage.initModality(Modality.WINDOW_MODAL);
        this.primaryStage.setResizable(false);
        this.primaryStage.getIcons().add(new Image("/images/address_book_32.png"));
        this.primaryStage.setTitle("Login");
        this.logic = logic;
        this.functionButtonsPanel = functionButtonsPanel;

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
            functionButtonsPanel.updateLoginStatus();
            feedbackLabel.setText(commandResult.feedbackToUser);

            if (commandResult.feedbackToUser.contains("Success")) {
                functionButtonsPanel.toggleLoginLogout();
            }
        } catch (CommandException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
            feedbackLabel.setText(e.getMessage());
        } catch (ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
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
//@@author Hoang
