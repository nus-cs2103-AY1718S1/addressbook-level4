//@@author Hoang
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
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
 * A pop up Window for email sending
 */
public class EmailSendWindow extends UiPart<Region> {
    private static final String FXML = "EmailSendWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(EmailSendWindow.class);

    private Stage primaryStage;
    private Logic logic;
    private UserPrefs prefs;

    @FXML
    private Label recipientsLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label bodyLabel;

    @FXML
    private TextField recipientsField;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea bodyField;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea feedbackLabel;

    public EmailSendWindow(Logic logic, Stage parentStage, String recipients, String feedback, UserPrefs prefs) {
        super(FXML);

        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());

        if (prefs.getCurrentUserTheme().equals("DarkTheme")) {
            scene.getStylesheets().add("view/CSS/EmailSendWindowDark.css");
        } else {
            scene.getStylesheets().add("view/CSS/EmailSendWindowLight.css");
        }

        this.primaryStage.setScene(scene);
        this.primaryStage.initOwner(parentStage);
        this.primaryStage.initModality(Modality.WINDOW_MODAL);
        this.primaryStage.setResizable(false);
        this.primaryStage.getIcons().add(new Image("/images/address_book_32.png"));
        this.primaryStage.setTitle("Send Emails");
        this.logic = logic;
        recipientsField.setText(recipients);
        feedbackLabel.setText(feedback);
        setOnCloseEvent();
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

    /**
     * action for clicking send button
     */
    @FXML
    private void onSendButtonClicked() {
        String recipients = recipientsField.getText();
        String title = titleField.getText();
        String body = bodyField.getText();

        try {
            CommandResult commandResult = logic.execute("email_send "
                    + "\"" + recipients + "\"" + " "
                    + "\"" + title + "\"" + " "
                    + "\"" + body + "\"");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            feedbackLabel.setText(commandResult.feedbackToUser);
        } catch (ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
            feedbackLabel.setText(e.getMessage());
        } catch (CommandException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
            feedbackLabel.setText(e.getMessage());
        }
    }
}
//@@author Hoang
