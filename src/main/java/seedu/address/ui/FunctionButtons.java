//@@author Hoang
package seedu.address.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EmailLogoutCommand;
import seedu.address.logic.commands.GetEmailCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * A panel for function button on the main window
 */
public class FunctionButtons extends UiPart<Region> {
    private static final String FXML = "FunctionButtons.fxml";
    private Logic logic;
    private Stage stage;
    private MainWindow mainWindow;
    private UserPrefs prefs;

    @FXML
    private StackPane loginPane;
    @FXML
    private Button loginButton;
    @FXML
    private StackPane sendPane;
    @FXML
    private Button sendButton;
    @FXML
    private TextField loginStatus;

    public FunctionButtons(Logic logic, Stage stage, MainWindow mainWindow, UserPrefs prefs) {
        super(FXML);
        this.logic = logic;
        this.stage = stage;
        this.mainWindow = mainWindow;
        this.prefs = prefs;

        updateLoginStatus();
    }

    /**
     * Open the email login window
     */
    @FXML
    private void openEmailLoginWindow() {
        boolean isLoggedIn = false;
        try {
            if (!(logic.execute("email_address")).feedbackToUser.equals("Not logged in")) {
                isLoggedIn = true;
            }
        } catch (ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (CommandException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
        }

        if (!isLoggedIn && loginButton.getText().equals("Login")) {
            EmailLoginWindow emailLoginWindow = new EmailLoginWindow(logic, stage, this, prefs);
            emailLoginWindow.show();
        } else if (isLoggedIn && loginButton.getText().equals("Login")) {
            loginButton.setText("Logout");
            updateLoginStatus();
        } else if (!isLoggedIn && loginButton.getText().equals("Logout")) {
            loginButton.setText("Login");
            updateLoginStatus();
        } else {
            try {
                CommandResult commandResult = logic.execute(EmailLogoutCommand.COMMAND_WORD);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
                toggleLoginLogout();
                updateLoginStatus();
            } catch (CommandException e) {
                raise(new NewResultAvailableEvent(e.getMessage()));
            } catch (ParseException e) {
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
        }
    }

    /**
     * Open the email send window
     */
    @FXML
    private void openEmailSendWindow() {
        ArrayList<PersonCard> tickedPersons = mainWindow.getPersonListPanel().getTickedPersons();
        String recipients = new String();
        ArrayList<PersonCard> cardWithOutEmail = new ArrayList<PersonCard>();
        for (PersonCard card : tickedPersons) {
            if (card.isTicked()) {
                if (card.getEmail() != null) {
                    recipients += card.getEmail() + ";";
                } else {
                    cardWithOutEmail.add(card);
                }
            }
        }

        String feedbackPersonsWithoutEmail = "";

        if (cardWithOutEmail.size() != 0) {
            feedbackPersonsWithoutEmail = "The following person(s) do not have emails or have private emails:\n";

            for (PersonCard card : cardWithOutEmail) {
                feedbackPersonsWithoutEmail += card.getName() + "\n";
            }
        }

        EmailSendWindow emailSendWindow = new EmailSendWindow(logic, stage, recipients, feedbackPersonsWithoutEmail,
                prefs);
        emailSendWindow.show();
    }

    /**
     * update the current login status label
     */
    public void updateLoginStatus() {
        try {
            CommandResult commandResult = logic.execute(GetEmailCommand.COMMAND_WORD);
            if (commandResult.feedbackToUser.equals(GetEmailCommand.MESSAGE_NOT_LOGGED_IN)) {
                loginStatus.setText("Currently not logged in");
            } else {
                loginStatus.setText("Currently logged in as " + commandResult.feedbackToUser);
            }
        } catch (CommandException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * toggle state of login / logout button
     */
    public void toggleLoginLogout() {
        if (loginButton.getText().equals("Login")) {
            loginButton.setText("Logout");
        } else {
            loginButton.setText("Login");
        }
    }
}
//@@author Hoang
