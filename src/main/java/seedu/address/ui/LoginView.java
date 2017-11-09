package seedu.address.ui;

//@@author jelneo

import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeToCommandBoxView;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Displays username and password fields
 */
public class LoginView extends UiPart<Region> {
    private static final String FXML = "LoginView.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginView.class);

    private final Logic logic;
    private ObjectProperty<Username> username;
    private ObjectProperty<Password> password;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public LoginView(Logic logic) {
        super(FXML);
        this.logic = logic;
        logger.info("Showing login view...");
        usernameField.textProperty().addListener((unused1, unused2, unused3) -> {
        });
        passwordField.textProperty().addListener((unused1, unused2, unused3) -> {
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleLoginInputChanged() {
        String usernameText = usernameField.getText();
        String passwordText = passwordField.getText();
        if (!usernameText.isEmpty() && !passwordText.isEmpty()) {
            // process login inputs

            try {
                CommandResult commandResult;
                commandResult = logic.execute(LoginCommand.COMMAND_WORD + " " + usernameText
                        + " " + passwordText);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
            } catch (CommandException | ParseException e) {
                raise(new NewResultAvailableEvent(e.getMessage(), true));
            }
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleBackToCommandView() {
        EventsCenter.getInstance().post(new ChangeToCommandBoxView());
    }
}
