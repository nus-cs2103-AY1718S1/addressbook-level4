package seedu.address.ui;

import static seedu.address.logic.commands.LoginCommand.isLoggedIn;

import java.util.logging.Logger;

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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jelneo
/**
 * Displays username and password fields.
 */
public class LoginView extends UiPart<Region> {
    public static final String SEPARATOR = "|";
    public static final String GUI_LOGIN_COMMAND_FORMAT = "login " + "%1$s" + SEPARATOR + "%2$s" + SEPARATOR;

    private static final String FXML = "LoginView.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginView.class);

    private static boolean showingLoginView = false;
    private final Logic logic;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public LoginView(Logic logic) {
        super(FXML);
        this.logic = logic;
        logger.info("Showing login view...");
        addListeners();
    }

    /**
     * Adds listeners to {@code usernameField} and {@code passwordField}.
     */
    private void addListeners() {
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
        // process login inputs
        try {
            CommandResult commandResult;
            if (!containSeparatorString(usernameText) && !containSeparatorString(passwordText)) {
                commandResult = logic.execute(String.format(GUI_LOGIN_COMMAND_FORMAT, usernameText, passwordText));
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
            } else if (containSeparatorString(usernameText)) {
                throw new ParseException(Username.MESSAGE_USERNAME_CHARACTERS_CONSTRAINTS);
            } else {
                throw new ParseException(Password.MESSAGE_PASSWORD_CHARACTERS_CONSTRAINTS);
            }
        } catch (CommandException | ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
        if (isLoggedIn()) {
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    /**
     * Handles the button clicking event. Changes from the current login view to command box view.
     */
    @FXML
    private void handleBackToCommandView() {
        EventsCenter.getInstance().post(new ChangeToCommandBoxView());
    }

    public static void setShowingLoginView(boolean val) {
        showingLoginView = val;
    }

    public static boolean isShowingLoginView() {
        return showingLoginView;
    }

    /**
     * Checks if {@code input} contains {@code SEPARATOR} characters.
     * @return {@code true} if {@code input} contains {@code SEPARATOR},
     * otherwise {@code false}
     */
    private boolean containSeparatorString(String input) {
        return input.contains(SEPARATOR);
    }
}
